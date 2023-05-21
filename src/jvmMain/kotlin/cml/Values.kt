package cml

import arrow.core.Either

abstract class Value(pos: PosInfo) : AstNode(pos) {
    abstract fun repr(): String

    abstract fun copy(): Value

    companion object
}
abstract class BaseValue<T>(val value: T, pos: PosInfo) : Value(pos) {
    override fun hashCode(): Int {
        return value.hashCode()
    }
}

class BoolVal(value: Boolean, pos: PosInfo): BaseValue<Boolean>(value, pos) {
    override fun repr(): String = "$value"
    override fun equals(other: Any?): Boolean {
        return if(other is BoolVal) value == other.value else false
    }
    override fun copy(): Value = BoolVal(value, pos)
}
class IntVal(value: Int, pos: PosInfo) : BaseValue<Int>(value, pos) {
    override fun repr(): String = "$value"
    override fun equals(other: Any?): Boolean {
        return if(other is IntVal) value == other.value else false
    }
    override fun copy(): Value = IntVal(value, pos)
}
class StringVal(value: String, pos: PosInfo): BaseValue<String>(value, pos){
    override fun repr(): String = value
    override fun equals(other: Any?): Boolean {
        if(other is StringVal) {
            return value == other.value
        }
        return false
    }
    override fun copy(): Value = StringVal(value, pos)
}
class ListVal(value: MutableList<Value>, pos: PosInfo): BaseValue<MutableList<Value>>(value, pos) {
    override fun repr(): String = "[ ${value.joinToString(", ") { it.repr() }} ]"
    override fun equals(other: Any?): Boolean {
        return if(other is ListVal) value == other.value else false
    }
    override fun copy(): Value = ListVal(value.map { it.copy() }.toMutableList(), pos)
}
class DictVal(value: MutableMap<Value, Value>, pos: PosInfo): BaseValue<MutableMap<Value, Value>>(value, pos) {
    override fun repr(): String = "{ ${value.map { "(${it.key.repr()} = ${it.value.repr()})" }.joinToString(", ")} }"
    override fun equals(other: Any?): Boolean {
        return if(other is DictVal) value == other.value else false
    }
    override fun copy(): Value = DictVal(value.map { (k, v) -> Pair(k.copy(), v.copy()) }.associate { it }.toMutableMap(), pos)
}
class RangeVal(val begin: Int, val end: Int, pos: PosInfo): Value(pos) {
    override fun repr(): String = "(${begin}..$end)"
    override fun equals(other: Any?): Boolean {
        return if(other is RangeVal) (begin == other.begin && end == other.end) else false
    }
    override fun copy(): Value = RangeVal(begin, end, pos)
    override fun hashCode(): Int = Pair(begin, end).hashCode()
}
class UntilVal(val begin: Int, val end: Int, pos: PosInfo): Value(pos) {
    override fun repr(): String = "($begin until $end)"
    override fun equals(other: Any?): Boolean {
        return if(other is UntilVal) (begin == other.begin && end == other.end) else false
    }
    override fun copy(): Value = UntilVal(begin, end, pos)
    override fun hashCode(): Int = Pair(begin, end).hashCode()
}
class DiceVal(val count: Int, val kind: Int, pos: PosInfo): Value(pos) {
    override fun repr(): String = "${count}d$kind"
    override fun equals(other: Any?): Boolean {
        return if(other is DiceVal) (count == other.count && kind == other.kind) else false
    }
    override fun copy(): Value = DiceVal(count, kind, pos)
    override fun hashCode(): Int = Pair(count, kind).hashCode()
}
class VoidVal(pos: PosInfo): Value(pos) {
    override fun repr(): String = "(void)"
    override fun equals(other: Any?): Boolean = false
    override fun copy(): Value = VoidVal(pos)
    override fun hashCode(): Int = javaClass.hashCode()
}
class InstanceVal(val type: TopLevelDecl, pos: PosInfo): Value(pos) {
    override fun repr(): String = "(instance of ${type.name}, kind: ${type.kind})"
    // TODO: override equals, hashCode
    override fun copy(): Value = InstanceVal(type.construct(), pos)

    override fun equals(other: Any?): Boolean {
        if(other !is InstanceVal) return false
        return type == other.type
    }

    override fun hashCode(): Int = type.hashCode()

    fun verifyKind(expected: String, rPos: PosInfo): Either<CMLException, InstanceVal> {
        if(type.kind != expected) return Either.Left(CMLException.wrongKind(expected, type.kind, rPos))
        return Either.Right(this)
    }

    inline fun <reified T: Value> attemptFieldAs(field: String, typename: String, rPos: PosInfo): Either<CMLException, T> {
        return when(val variable = type.getField(field)) {
            null -> Either.Left(CMLException.invalidField(repr(), field, rPos))
            !is T -> Either.Left(CMLException.typeError(typename, variable, rPos))
            else -> Either.Right(variable)
        }
    }

    inline fun <reified T: Value> ifKindGetFieldAs(kind: String, field: String, typename: String, rPos: PosInfo): T {
        if(type.kind != kind) { throw CMLException.wrongKind(kind, type.kind, rPos) }
        return getFieldAs(field, typename, rPos)
    }

    inline fun <reified T: Value> getFieldAs(field: String, typename: String, rPos: PosInfo): T {
        val variable = type.getField(field) ?: throw CMLException.invalidField(repr(), field, rPos)
        if(variable !is T) throw CMLException.typeError(typename, variable, rPos)
        return variable
    }
}

fun typeName(v: Value): String = when(v) {
    is BoolVal -> "bool"
    is IntVal -> "int"
    is StringVal -> "string"
    is ListVal -> "list"
    is DictVal -> "dict"
    is RangeVal -> "range"
    is UntilVal -> "until"
    is DiceVal -> "dice"
    is VoidVal -> "void"
    is InstanceVal -> "instance(${v.type.name})"
    else -> throw CMLException.invalidType()
}

class Variable(val name: String, v: Value, val isImmutable: Boolean, val declPos: PosInfo) {
    var value: Value = v
        private set

    init {
        if(v is VoidVal) throw CMLException.voidVarException(name, declPos)
    }

    fun safeOverwrite(v: Value, pos: PosInfo) {
        // overwrite position is given by the value
        if(isImmutable) throw CMLException.overwriteImmutable(name, declPos, pos)
        else {
            value = v
        }
    }

    fun forceOverwrite(v: Value) {
        // SHOULD ONLY BE USED INTERNALLY!
        value = v
    }

    fun isBool() = value is BoolVal
    fun isInt() = value is IntVal
    fun isString() = value is StringVal
    fun isList() = value is ListVal
    fun isDict() = value is DictVal
    fun isRange() = value is RangeVal
    fun isUntil() = value is UntilVal
    fun isDice() = value is DiceVal

    fun copy(): Variable = Variable(name, value.copy(), isImmutable, declPos)
}

class ExecEnvironment private constructor(
    private val parent: ExecEnvironment?, ignore: Boolean
) {
    constructor(parent: ExecEnvironment) : this(parent, false) {
        functions = parent.functions
    }

    constructor(funcs: Map<String, FunDecl>) : this(null, false) {
        functions = funcs
    }

    private var functions = mapOf<String, FunDecl>()
    private val variables = mutableMapOf<String, Variable>()

    var varsAreImmutable: Boolean = false
        private set
    var isInLoop: Boolean = false
        private set
    var hitBreak = false
    var hitReturn = false
    var returnValue: Value = VoidVal(PosInfo("", 0, 0))

    override fun equals(other: Any?): Boolean {
        if(other !is ExecEnvironment) return false
        return variables == other.variables && varsAreImmutable == other.varsAreImmutable && isInLoop == other.isInLoop
    }

    override fun hashCode(): Int = Triple(variables, varsAreImmutable, isInLoop).hashCode()

    fun isInThisEnv(name: String) = variables.containsKey(name)
    fun isInEnv(name: String): Boolean = isInThisEnv(name) || (parent?.isInEnv(name) ?: false)
    fun getVar(name: String): Variable? = variables[name] ?: parent?.getVar(name)

    fun isFunction(name: String): Boolean =
        StdLib.isStd(name) || Library.isLibFunc(name) || functions.containsKey(name)

    fun invoke(name: String, args: List<Value>, callSite: PosInfo): Value? =
        StdLib.invoke(name, args, callSite) ?: Library.invoke(name, args, callSite) ?: functions[name]?.call(args, callSite)

    fun addVar(name: String, value: Value, currPos: PosInfo) {
        if(variables.containsKey(name)) throw CMLException.redeclareVar(name, variables[name]!!.declPos, currPos)
        variables[name] = Variable(name, value, varsAreImmutable, currPos)
    }

    fun copy(): ExecEnvironment {
        if(parent != null) throw CMLException.internalCopyExecEnv()
        val res = ExecEnvironment(functions)
        res.varsAreImmutable = varsAreImmutable
        res.isInLoop = isInLoop
        res.hitBreak = hitBreak
        res.hitReturn = hitReturn
        res.returnValue = returnValue
        res.variables.clear()
        variables.mapValuesTo(res.variables) { (_, v) -> v.copy() }
        return res
    }

    companion object {
        fun defaultEnv(parent: ExecEnvironment): ExecEnvironment = ExecEnvironment(parent)

        fun constVarEnv(parent: ExecEnvironment): ExecEnvironment = defaultEnv(parent).also {
            it.varsAreImmutable = true
        }

        fun loopEnv(parent: ExecEnvironment): ExecEnvironment = defaultEnv(parent).also {
            it.isInLoop = true
        }
    }
}