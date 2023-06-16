package cml

import arrow.core.Either

abstract class Value(pos: PosInfo) : AstNode(pos) {
    abstract fun repr(): String

    fun copy(): Value = copy(pos)

    abstract fun copy(newPos: PosInfo): Value

    companion object
}
abstract class BaseValue<T>(val value: T, pos: PosInfo) : Value(pos) {
    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is BaseValue<*> && other.value == value
    }
}

class BoolVal(value: Boolean, pos: PosInfo): BaseValue<Boolean>(value, pos) {
    override fun repr(): String = "$value"
    override fun copy(newPos: PosInfo): Value = BoolVal(value, newPos)
}
class IntVal(value: Int, pos: PosInfo) : BaseValue<Int>(value, pos) {
    override fun repr(): String = "$value"
    override fun copy(newPos: PosInfo): Value = IntVal(value, newPos)
}
class FloatVal(value: Float, pos: PosInfo) : BaseValue<Float>(value, pos) {
    override fun repr(): String = "$value"
    override fun copy(newPos: PosInfo): Value = FloatVal(value, newPos)
}
class StringVal(value: String, pos: PosInfo): BaseValue<String>(value, pos){
    override fun repr(): String = value
    override fun copy(newPos: PosInfo): Value = StringVal(value, newPos)
}
class ListVal(value: MutableList<Value>, pos: PosInfo): BaseValue<MutableList<Value>>(value, pos) {
    override fun repr(): String = "[ ${value.joinToString(", ") { it.repr() }} ]"
    override fun copy(newPos: PosInfo): Value = ListVal(value.map { it.copy() }.toMutableList(), newPos)
}
class DictVal(value: MutableMap<Value, Value>, pos: PosInfo): BaseValue<MutableMap<Value, Value>>(value, pos) {
    override fun repr(): String = "{ ${value.map { "(${it.key.repr()} = ${it.value.repr()})" }.joinToString(", ")} }"
    override fun copy(newPos: PosInfo): Value = DictVal(value.map { (k, v) -> Pair(k.copy(), v.copy()) }.associate { it }.toMutableMap(), newPos)
}
class RangeVal(val begin: Int, val end: Int, pos: PosInfo): Value(pos) {
    override fun repr(): String = "(${begin}..$end)"
    override fun equals(other: Any?): Boolean {
        return if(other is RangeVal) (begin == other.begin && end == other.end) else false
    }
    override fun copy(newPos: PosInfo): Value = RangeVal(begin, end, newPos)
    override fun hashCode(): Int = Pair(begin, end).hashCode()
}
class UntilVal(val begin: Int, val end: Int, pos: PosInfo): Value(pos) {
    override fun repr(): String = "($begin until $end)"
    override fun equals(other: Any?): Boolean {
        return if(other is UntilVal) (begin == other.begin && end == other.end) else false
    }
    override fun copy(newPos: PosInfo): Value = UntilVal(begin, end, newPos)
    override fun hashCode(): Int = Pair(begin, end).hashCode()
}
class DiceVal(val count: Int, val kind: Int, pos: PosInfo): Value(pos) {
    override fun repr(): String = "${count}d$kind"
    override fun equals(other: Any?): Boolean {
        return if(other is DiceVal) (count == other.count && kind == other.kind) else false
    }
    override fun copy(newPos: PosInfo): Value = DiceVal(count, kind, newPos)
    override fun hashCode(): Int = Pair(count, kind).hashCode()
}
class VoidVal(pos: PosInfo): Value(pos) {
    override fun repr(): String = "(void)"
    override fun equals(other: Any?): Boolean = false
    override fun copy(newPos: PosInfo): Value = VoidVal(newPos)
    override fun hashCode(): Int = javaClass.hashCode()
}
class InstanceVal private constructor(val type: TopLevelDecl, val fields: ExecEnvironment, pos: PosInfo): Value(pos) {
    constructor(type: TopLevelDecl, fields: Map<String, Value>, pos: PosInfo): this(type, ExecEnvironment(fields, pos), pos)

    init {
        fields.thisVar = this
    }

    override fun repr(): String = "(instance of ${type.name}, kind: ${type.kind})"
    override fun copy(newPos: PosInfo): Value = InstanceVal(type, fields, newPos)

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
        return when(val variable = fields.getVar(field)?.value) {
            null -> Either.Left(CMLException.invalidField(repr(), field, rPos))
            !is T -> Either.Left(CMLException.typeError(typename, variable, rPos))
            else -> Either.Right(variable)
        }
    }

    inline fun <reified T: Value> getFieldAs(field: String, typename: String, rPos: PosInfo): T {
        val variable = fields.getVar(field)?.value ?: throw CMLException.invalidField(repr(), field, rPos)
        if(variable !is T) throw CMLException.typeError(typename, variable, rPos)
        return variable
    }

    fun invoke(name: String, args: List<Value>, callSite: PosInfo) = type.invokeWith(name, args, fields, callSite)

    fun getFieldAsVar(field: String) = fields.getVar(field)
}

fun typeName(v: Value): String = when(v) {
    is BoolVal -> "bool"
    is IntVal -> "int"
    is FloatVal -> "float"
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

open class Variable protected constructor(allowVoid: Boolean, val name: String, v: Value, val isImmutable: Boolean, val declPos: PosInfo) {
    var value: Value = v
        private set

    init {
        if(!allowVoid && v is VoidVal) throw CMLException.voidVarException(name, declPos)
    }

    constructor(name: String, v: Value, isImmutable: Boolean, declPos: PosInfo) : this(false, name, v, isImmutable, declPos) {
    }

    override fun equals(other: Any?): Boolean {
        if(other !is Variable) return false
        return name == other.name && value == other.value && isImmutable == other.isImmutable
    }

    open fun safeOverwrite(v: Value, pos: PosInfo) {
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
    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + isImmutable.hashCode()
        result = 31 * result + declPos.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }
}

class ListIndexVariable(private val l: MutableList<Value>, private val i: Int, isImmutable: Boolean, declPos: PosInfo)
    : Variable("[index variable]", l[i], isImmutable, declPos) {
    override fun safeOverwrite(v: Value, pos: PosInfo) {
        if(isImmutable) throw CMLException.overwriteImmutable("[index into list]", declPos, pos)
        else l[i] = v
    }
}

class DictIndexVariable(private val d: MutableMap<Value, Value>, val k: Value, isImmutable: Boolean, declPos: PosInfo)
    : Variable(true, "[index variable]", d[k] ?: VoidVal(declPos), isImmutable, declPos) {
    override fun safeOverwrite(v: Value, pos: PosInfo) {
        if(isImmutable) throw CMLException.overwriteImmutable("[index into dict]", declPos, pos)
        else d[k] = v
    }
}

class ExecEnvironment private constructor(
    private val parent: ExecEnvironment?, ignore: Boolean, private val pos: PosInfo
) {
    constructor(parent: ExecEnvironment, pos: PosInfo) : this(parent, false, pos) {}

    constructor(vars: Map<String, Value>, pos: PosInfo) : this(null, false, pos) {
        variables.putAll(vars.map { Pair(it.key, Variable(it.key, it.value.copy(), false, it.value.pos)) })
    }

    var thisVar: InstanceVal? = null
    var varsAreImmutable: Boolean = false
        private set
    private var isInLoop: Boolean = false
    var hitBreak = false
    var hitReturn = false
    var returnValue: Value = VoidVal(PosInfo("", 0, 0))

    private val variables = mutableMapOf<String, Variable>()

    override fun equals(other: Any?): Boolean {
        if(other !is ExecEnvironment) return false
        return variables == other.variables && varsAreImmutable == other.varsAreImmutable && isInLoop == other.isInLoop
    }

    override fun hashCode(): Int = Triple(variables, varsAreImmutable, isInLoop).hashCode()

    fun isInLoop() = isInLoop || (parent?.isInLoop ?: false)
    fun getVar(name: String): Variable? = variables[name] ?: parent?.getVar(name) ?: Library.getGlobal(name)

//    fun isEnvFunction(name: String): Boolean =
//        thisVar?.type?.isFun(name) == true || (parent !== this && parent?.isEnvFunction(name) == true)

    fun listVars() = variables.map { it.key }

    fun isEnvFunction(name: String): Boolean {
        if(thisVar?.type?.isFun(name) == true) return true
        return parent !== this && parent?.isEnvFunction(name) == true
    }

    fun currentThis(): InstanceVal? = thisVar ?: parent?.currentThis()

    fun invoke(name: String, args: List<Value>, callSite: PosInfo): Value? {
        if(isEnvFunction(name)) {
            return currentThis()?.type?.invokeWith(name, args, this, callSite)
        }
        return if(Library.isLibFunc(name)) Library.invoke(name, args, callSite)
            else if(StdLib.isStd(name)) StdLib.invoke(name, args, callSite)
            else null
    }

    fun addVar(name: String, value: Value, currPos: PosInfo) {
        if(variables.containsKey(name)) throw CMLException.redeclareVar(name, variables[name]!!.declPos, currPos)
        variables[name] = Variable(name, value, varsAreImmutable, currPos)
    }

    companion object {
        fun defaultEnv(parent: ExecEnvironment, pos: PosInfo): ExecEnvironment = ExecEnvironment(parent, pos)

        fun constVarEnv(parent: ExecEnvironment, pos: PosInfo): ExecEnvironment = defaultEnv(parent, pos).also {
            it.varsAreImmutable = true
        }

        fun loopEnv(parent: ExecEnvironment, pos: PosInfo): ExecEnvironment = defaultEnv(parent, pos).also {
            it.isInLoop = true
        }
    }
}