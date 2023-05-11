package cml

abstract class Value(pos: PosInfo) : AstNode(pos) {
    abstract fun repr(): String
}
abstract class BaseValue<T>(val value: T, pos: PosInfo) : Value(pos)

class BoolVal(value: Boolean, pos: PosInfo): BaseValue<Boolean>(value, pos) {
    override fun repr(): String = "$value"
}
class IntVal(value: Int, pos: PosInfo) : BaseValue<Int>(value, pos) {
    override fun repr(): String = "$value"
}
class StringVal(value: String, pos: PosInfo): BaseValue<String>(value, pos){
    override fun repr(): String = value
}
class ListVal(value: MutableList<Value>, pos: PosInfo): BaseValue<MutableList<Value>>(value, pos) {
    override fun repr(): String = "[ ${value.joinToString(", ") { it.repr() }} ]"
}
class DictVal(value: MutableMap<Value, Value>, pos: PosInfo): BaseValue<MutableMap<Value, Value>>(value, pos) {
    override fun repr(): String = "{ ${value.map { "(${it.key.repr()} = ${it.value.repr()})" }.joinToString(", ")} }"
}
class RangeVal(val begin: Int, val end: Int, pos: PosInfo): Value(pos) {
    override fun repr(): String = "(${begin}..$end)"
}
class UntilVal(val begin: Int, val end: Int, pos: PosInfo): Value(pos) {
    override fun repr(): String = "($begin until $end)"
}
class DiceVal(val count: Int, val kind: Int, pos: PosInfo): Value(pos) {
    override fun repr(): String = "${count}d$kind"
}
class VoidVal(pos: PosInfo): Value(pos) {
    override fun repr(): String = "(void)"
}

class Variable(val name: String, v: Value, val isImmutable: Boolean, val declPos: PosInfo) {
    var value: Value = v
        private set

    init {
        if(v is VoidVal) TODO("Big error!")
    }

    fun safeOverwrite(v: Value) {
        // overwrite position is given by the value
        if(isImmutable) TODO("Error")
        if(v.javaClass != value.javaClass) {
            TODO("Do some error stuff?")
        }
        else {
            value = v
        }
    }

    fun isBool() = value is BoolVal
    fun isInt() = value is IntVal
    fun isString() = value is StringVal
    fun isList() = value is ListVal
    fun isDict() = value is DictVal
    fun isRange() = value is RangeVal
    fun isUntil() = value is UntilVal
    fun isDice() = value is DiceVal
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

    var functions = mapOf<String, FunDecl>()
        private set
    private val variables = mutableMapOf<String, Variable>()
    var varsAreImmutable: Boolean = false
        private set
    var isInLoop: Boolean = false
        private set
    var hitBreak = false
    var hitReturn = false
    var returnValue: Value = VoidVal(PosInfo("", 0, 0))

    fun isInThisEnv(name: String) = variables.containsKey(name)
    fun isInEnv(name: String): Boolean = isInThisEnv(name) || (parent?.isInEnv(name) ?: false)
    fun getVar(name: String): Variable? = variables[name] ?: parent?.getVar(name)

    fun addVar(name: String, value: Value, currPos: PosInfo) {
        variables[name] = Variable(name, value, varsAreImmutable, currPos)
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