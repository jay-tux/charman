package cml

// TODO: in evaluate - handle void

abstract class Expression(pos: PosInfo) : AstNode(pos) {
    abstract fun evaluate(ctxt: ExecEnvironment): Value
}

class ExpressionSet(pos: PosInfo) : AstNode(pos) {
    val values = mutableListOf<Expression>()
}

class KvpSet(pos: PosInfo) : AstNode(pos) {
    val values = mutableListOf<Pair<Expression, Expression>>()
}

class LiteralExpr(private val literal: Value, pos: PosInfo): Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        return literal
    }
}

class VarExpr(private val ident: String, pos: PosInfo): Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        return ctxt.getVar(ident)?.value ?: TODO("Error")
    }
}

class ParenExpr(private val expr: Expression, pos: PosInfo): Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        return expr.evaluate(ctxt)
    }
}

class DiceExpr(
    private val count: Expression,
    private val kind: Expression,
    pos: PosInfo
) : Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        val cnt = count.evaluate(ctxt)
        val knd = kind.evaluate(ctxt)
        if(cnt !is IntVal) { TODO("Error") }
        if(knd !is IntVal) { TODO("Error") }
        return DiceVal(cnt.value, knd.value, pos)
    }
}

class BinOperExpr(
    val left: Expression,
    val operator: Op,
    val right: Expression,
    pos: PosInfo
): Expression(pos) {
    enum class Op { ADD, SUB, MUL, DIV, MOD, EQ, NEQ, LT, GT, GEQ, LEQ, AND, OR, B_AND, B_OR, XOR }

    private fun withAsInt(l: Value, r: Value, op: (Int, Int) -> Int): Value {
        if(l !is IntVal) { TODO("Error") }
        if(r !is IntVal) { TODO("Error") }
        return IntVal(op(l.value, r.value), pos)
    }

    private fun withAsIntToBool(l: Value, r: Value, op: (Int, Int) -> Boolean): Value {
        if(l !is IntVal) { TODO("Error") }
        if(r !is IntVal) { TODO("Error") }
        return BoolVal(op(l.value, r.value), pos)
    }

    private fun withAsBool(l: Value, r: Value, op: (Boolean, Boolean) -> Boolean): Value {
        if(l !is BoolVal) { TODO("Error") }
        if(r !is BoolVal) { TODO("Error") }
        return BoolVal(op(l.value, r.value), pos)
    }

    private inline fun <reified T : Any> withAs(l: BaseValue<T>, r: Value, op: (Any, Any) -> Boolean): Value {
        if(r is BaseValue<*> && r.value is T) return BoolVal(op(l.value, r.value), pos)
        else TODO("Error")
    }

    private fun withAsSame(l: Value, r: Value, op: (Any, Any) -> Boolean): Value {
        return when(l) {
            is BoolVal -> withAs<Boolean>(l, r, op)
            is IntVal -> withAs<Int>(l, r, op)
            is StringVal -> withAs<String>(l, r, op)
            is ListVal -> withAs<MutableList<Value>>(l, r, op)
            is DictVal -> withAs<MutableMap<Value, Value>>(l, r, op)
            is RangeVal -> {
                if(r is RangeVal) BoolVal(op(l, r), pos)
                else TODO("Error")
            }
            is UntilVal -> {
                if(r is UntilVal) BoolVal(op(l, r), pos)
                else TODO("Error")
            }
            is DiceVal -> {
                if(r is DiceVal) BoolVal(op(l, r), pos)
                else TODO("Error")
            }
            else -> TODO()
        }
    }

    override fun evaluate(ctxt: ExecEnvironment): Value {
        return when(operator) {
            Op.ADD -> withAsInt(left.evaluate(ctxt), right.evaluate(ctxt)) { a, b -> a + b }
            Op.SUB -> withAsInt(left.evaluate(ctxt), right.evaluate(ctxt)) { a, b -> a - b }
            Op.MUL -> withAsInt(left.evaluate(ctxt), right.evaluate(ctxt)) { a, b -> a * b }
            Op.DIV -> withAsInt(left.evaluate(ctxt), right.evaluate(ctxt)) { a, b -> a / b }
            Op.MOD -> withAsInt(left.evaluate(ctxt), right.evaluate(ctxt)) { a, b -> a % b }
            Op.EQ -> withAsSame(left.evaluate(ctxt), right.evaluate(ctxt)) { a, b -> a == b}
            Op.NEQ -> withAsSame(left.evaluate(ctxt), right.evaluate(ctxt)) { a, b -> a != b }
            Op.LT -> withAsIntToBool(left.evaluate(ctxt), right.evaluate(ctxt)) { a, b -> a < b }
            Op.GT -> withAsIntToBool(left.evaluate(ctxt), right.evaluate(ctxt)) { a, b -> a > b }
            Op.GEQ -> withAsIntToBool(left.evaluate(ctxt), right.evaluate(ctxt)) { a, b -> a >= b }
            Op.LEQ -> withAsIntToBool(left.evaluate(ctxt), right.evaluate(ctxt)) { a, b -> a <= b }
            Op.AND -> withAsBool(left.evaluate(ctxt), right.evaluate(ctxt)) { a, b -> a && b }
            Op.OR -> withAsBool(left.evaluate(ctxt), right.evaluate(ctxt)) { a, b -> a || b }
            Op.B_AND -> withAsInt(left.evaluate(ctxt), right.evaluate(ctxt)) { a, b -> a and b }
            Op.B_OR -> withAsInt(left.evaluate(ctxt), right.evaluate(ctxt)) { a, b -> a or b }
            Op.XOR -> withAsInt(left.evaluate(ctxt), right.evaluate(ctxt)) { a, b -> a xor b }
        }
    }
}

class UnOperExpr(val oper: Op, val target: Expression, pos: PosInfo): Expression(pos) {
    enum class Op { BIN_NEG, LOG_NEG, UN_MINUS }

    private fun withAsInt(ctxt: ExecEnvironment, op: (Int) -> Int): Value {
        val res = target.evaluate(ctxt)
        if(res !is IntVal) TODO("Error")
        return IntVal(op(res.value), pos)
    }

    private fun withAsBool(ctxt: ExecEnvironment, op: (Boolean) -> Boolean): Value {
        val res = target.evaluate(ctxt)
        if(res !is BoolVal) TODO("Error")
        return BoolVal(op(res.value), pos)
    }

    override fun evaluate(ctxt: ExecEnvironment): Value {
        return when(oper) {
            Op.BIN_NEG -> withAsInt(ctxt) { it.inv() }
            Op.LOG_NEG -> withAsBool(ctxt) { !it }
            Op.UN_MINUS -> withAsInt(ctxt) { -it }
        }
    }
}

class TernaryExpr(
    val cond: Expression,
    val bTrue: Expression,
    val bFalse: Expression,
    pos: PosInfo
): Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        val rCond = cond.evaluate(ctxt)
        if(rCond !is BoolVal) { TODO("Error") }
        return when(rCond.value) {
            true -> bTrue.evaluate(ctxt)
            false -> bFalse.evaluate(ctxt)
        }
    }
}

class RangeExpr(val begin: Expression, val end: Expression, pos: PosInfo): Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        val b = begin.evaluate(ctxt)
        val e = end.evaluate(ctxt)
        if(b !is IntVal) { TODO("Error") }
        if(e !is IntVal) { TODO("Error") }
        return RangeVal(b.value, e.value, pos)
    }
}

class UntilExpr(val begin: Expression, val end: Expression, pos: PosInfo): Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        val b = begin.evaluate(ctxt)
        val e = end.evaluate(ctxt)
        if(b !is IntVal) { TODO("Error") }
        if(e !is IntVal) { TODO("Error") }
        return UntilVal(b.value, e.value, pos)
    }
}

class ListExpr(val vals: List<Expression>, pos: PosInfo): Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        return ListVal(vals.map { it.evaluate(ctxt) }.toMutableList(), pos)
    }
}

class DictExpr(val kvps: List<Pair<Expression, Expression>>, pos: PosInfo): Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        return DictVal(
            kvps.associate { Pair(it.first.evaluate(ctxt), it.second.evaluate(ctxt)) }.toMutableMap(),
            pos
        )
    }
}

class FuncCallExpr(
    private val name: String,
    private val args: List<Expression>,
    pos: PosInfo
): Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        if(!ctxt.isFunction(name)) TODO("Error")
        return ctxt.invoke(name, args.map{ it.evaluate(ctxt) }) ?: TODO("Error")
    }
}

class CtorExpr(val type: String, pos: PosInfo): Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        return Library.construct(type, pos) ?: TODO("Error")
    }
}