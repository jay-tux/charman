package cml

// TODO: in evaluate - handle void

abstract class Expression(pos: PosInfo) : AstNode(pos) {
    abstract fun evaluate(ctxt: ExecEnvironment): Value
    abstract fun instantiate(instantiations: Map<String, Expression>): Expression
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

    override fun instantiate(instantiations: Map<String, Expression>): Expression =
        LiteralExpr(literal, pos)
}

class VarExpr(private val ident: String, pos: PosInfo): Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        return ctxt.getVar(ident)?.value ?: throw CMLException.undeclaredVar(ident, pos)
    }

    override fun instantiate(instantiations: Map<String, Expression>): Expression =
        VarExpr(ident, pos)
}

class FieldExpr(val base: Expression, val name: String, pos: PosInfo): Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        val baseE = base.evaluate(ctxt)
        if(baseE !is InstanceVal) throw CMLException.nonObjectVar(name, pos)
        return baseE.type.getField(name) ?: throw CMLException.invalidField(baseE.type.name, name, pos)
    }

    override fun instantiate(instantiations: Map<String, Expression>): Expression {
        return FieldExpr(base.instantiate(instantiations), name, pos)
    }
}

class IndexExpr(val base: Expression, val index: Expression, pos: PosInfo) : Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        return when(val baseE = base.evaluate(ctxt)) {
            is ListVal -> {
                val indexE = index.evaluate(ctxt)
                if(indexE !is IntVal) throw CMLException.invalidIndexType("list", "int", pos)
                if(indexE.value < 0 || indexE.value >= baseE.value.size)
                    throw CMLException.listOutOfRange(indexE.value, baseE.value.size, pos)
                baseE.value[indexE.value]
            }
            is RangeVal -> {
                val indexE = index.evaluate(ctxt)
                if(indexE !is IntVal) throw CMLException.invalidIndexType("range", "int", pos)
                if(indexE.value < 0 || indexE.value > baseE.end - baseE.begin)
                    throw CMLException.listOutOfRange(indexE.value, baseE.end - baseE.begin + 1, pos)
                IntVal(baseE.begin + 1, pos)
            }
            is UntilVal -> {
                val indexE = index.evaluate(ctxt)
                if(indexE !is IntVal) throw CMLException.invalidIndexType("until", "int", pos)
                if(indexE.value < 0 || indexE.value >= baseE.end - baseE.begin)
                    throw CMLException.listOutOfRange(indexE.value, baseE.end - baseE.begin + 1, pos)
                IntVal(baseE.begin + 1, pos)
            }
            is DictVal -> {
                val indexE = index.evaluate(ctxt)
                baseE.value[indexE] ?: throw CMLException.keyError(indexE.repr(), pos)
            }
            else -> throw CMLException.nonIndexableVar(pos)
        }
    }

    override fun instantiate(instantiations: Map<String, Expression>): Expression {
        return IndexExpr(base.instantiate(instantiations), index.instantiate(instantiations), pos)
    }

}

class ParenExpr(private val expr: Expression, pos: PosInfo): Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        return expr.evaluate(ctxt)
    }

    override fun instantiate(instantiations: Map<String, Expression>): Expression
        = ParenExpr(expr.instantiate(instantiations), pos)
}

class DiceExpr(
    private val count: Expression,
    private val kind: Expression,
    pos: PosInfo
) : Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        val cnt = count.evaluate(ctxt)
        val knd = kind.evaluate(ctxt)
        if(cnt !is IntVal) { throw CMLException.typeError("dice count", "int", cnt, pos) }
        if(knd !is IntVal) { throw CMLException.typeError("dice kind", "int", knd, pos) }
        return DiceVal(cnt.value, knd.value, pos)
    }

    override fun instantiate(instantiations: Map<String, Expression>): Expression =
        DiceExpr(count.instantiate(instantiations), kind.instantiate(instantiations), pos)
}

class BinOperExpr(
    val left: Expression,
    val operator: Op,
    val right: Expression,
    pos: PosInfo
): Expression(pos) {
    enum class Op { ADD, SUB, MUL, DIV, MOD, EQ, NEQ, LT, GT, GEQ, LEQ, AND, OR, B_AND, B_OR, XOR }

    private fun withAsInt(l: Value, r: Value, op: (Int, Int) -> Int): Value {
        if(l !is IntVal) { throw CMLException.typeError("left operand", "int", l, pos) }
        if(r !is IntVal) { throw CMLException.typeError("right operand", "int", r, pos) }
        return IntVal(op(l.value, r.value), pos)
    }

    private fun withAsIntToBool(l: Value, r: Value, op: (Int, Int) -> Boolean): Value {
        if(l !is IntVal) { throw CMLException.typeError("left operand", "int", l, pos) }
        if(r !is IntVal) { throw CMLException.typeError("right operand", "int", r, pos) }
        return BoolVal(op(l.value, r.value), pos)
    }

    private fun withAsBool(l: Value, r: Value, op: (Boolean, Boolean) -> Boolean): Value {
        if(l !is BoolVal) { throw CMLException.typeError("left operand", "bool", l, pos) }
        if(r !is BoolVal) { throw CMLException.typeError("right operand", "bool", r, pos) }
        return BoolVal(op(l.value, r.value), pos)
    }

    private inline fun <reified T : Any> withAs(l: BaseValue<T>, r: Value, op: (Any, Any) -> Boolean): Value {
        if(r is BaseValue<*> && r.value is T) return BoolVal(op(l.value, r.value), pos)
        else throw CMLException.typeError("right operand", typeName(l), r, pos)
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
                else throw CMLException.typeError("right operand", "range", r, pos)
            }
            is UntilVal -> {
                if(r is UntilVal) BoolVal(op(l, r), pos)
                else throw CMLException.typeError("right operand", "until", r, pos)
            }
            is DiceVal -> {
                if(r is DiceVal) BoolVal(op(l, r), pos)
                else throw CMLException.typeError("right operand", "dice", r, pos)
            }
            else -> throw CMLException.typeError("left operand", "comparable", l, pos)
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

    override fun instantiate(instantiations: Map<String, Expression>): Expression =
        BinOperExpr(left.instantiate(instantiations), operator, right.instantiate(instantiations), pos)
}

class UnOperExpr(val oper: Op, val target: Expression, pos: PosInfo): Expression(pos) {
    enum class Op { BIN_NEG, LOG_NEG, UN_MINUS }

    private fun withAsInt(ctxt: ExecEnvironment, op: (Int) -> Int): Value {
        val res = target.evaluate(ctxt)
        if(res !is IntVal) throw CMLException.typeError("int", res, pos)
        return IntVal(op(res.value), pos)
    }

    private fun withAsBool(ctxt: ExecEnvironment, op: (Boolean) -> Boolean): Value {
        val res = target.evaluate(ctxt)
        if(res !is BoolVal) throw CMLException.typeError("bool", res, pos)
        return BoolVal(op(res.value), pos)
    }

    override fun evaluate(ctxt: ExecEnvironment): Value {
        return when(oper) {
            Op.BIN_NEG -> withAsInt(ctxt) { it.inv() }
            Op.LOG_NEG -> withAsBool(ctxt) { !it }
            Op.UN_MINUS -> withAsInt(ctxt) { -it }
        }
    }

    override fun instantiate(instantiations: Map<String, Expression>): Expression =
        UnOperExpr(oper, target.instantiate(instantiations), pos)
}

class TernaryExpr(
    val cond: Expression,
    val bTrue: Expression,
    val bFalse: Expression,
    pos: PosInfo
): Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        val rCond = cond.evaluate(ctxt)
        if(rCond !is BoolVal) { throw CMLException.typeError("condition to ternary operator", "bool", rCond, pos) }
        return when(rCond.value) {
            true -> bTrue.evaluate(ctxt)
            false -> bFalse.evaluate(ctxt)
        }
    }

    override fun instantiate(instantiations: Map<String, Expression>): Expression =
        TernaryExpr(
            cond.instantiate(instantiations),
            bTrue.instantiate(instantiations),
            bFalse.instantiate(instantiations),
            pos
        )
}

class RangeExpr(val begin: Expression, val end: Expression, pos: PosInfo): Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        val b = begin.evaluate(ctxt)
        val e = end.evaluate(ctxt)
        if(b !is IntVal) { throw CMLException.typeError("range begin", "int", b, pos) }
        if(e !is IntVal) { throw CMLException.typeError("range end", "int", e, pos) }
        return RangeVal(b.value, e.value, pos)
    }

    override fun instantiate(instantiations: Map<String, Expression>): Expression =
        RangeExpr(begin.instantiate(instantiations), end.instantiate(instantiations), pos)
}

class UntilExpr(val begin: Expression, val end: Expression, pos: PosInfo): Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        val b = begin.evaluate(ctxt)
        val e = end.evaluate(ctxt)
        if(b !is IntVal) { throw CMLException.typeError("until begin", "int", b, pos) }
        if(e !is IntVal) { throw CMLException.typeError("until end", "int", e, pos) }
        return UntilVal(b.value, e.value, pos)
    }

    override fun instantiate(instantiations: Map<String, Expression>): Expression =
        UntilExpr(begin.instantiate(instantiations), end.instantiate(instantiations), pos)
}

class ListExpr(val vals: List<Expression>, pos: PosInfo): Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        return ListVal(vals.map { it.evaluate(ctxt) }.toMutableList(), pos)
    }

    override fun instantiate(instantiations: Map<String, Expression>): Expression =
        ListExpr(vals.map{ it.instantiate(instantiations) }, pos)
}

class DictExpr(val kvps: List<Pair<Expression, Expression>>, pos: PosInfo): Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        return DictVal(
            kvps.associate { Pair(it.first.evaluate(ctxt), it.second.evaluate(ctxt)) }.toMutableMap(),
            pos
        )
    }

    override fun instantiate(instantiations: Map<String, Expression>): Expression =
        DictExpr(kvps.map{ (k, v) -> Pair(k.instantiate(instantiations), v.instantiate(instantiations)) }, pos)
}

class FuncCallExpr(
    private val name: String,
    private val args: List<Expression>,
    pos: PosInfo
): Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        return ctxt.invoke(name, args.map{ it.evaluate(ctxt) }, pos) ?: throw CMLException.invokeNonFun(name, pos)
    }

    override fun instantiate(instantiations: Map<String, Expression>): Expression =
        FuncCallExpr(name, args.map { it.instantiate(instantiations) }, pos)
}

class CtorExpr(val type: String, pos: PosInfo): Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        return Library.construct(type, pos) ?: throw CMLException.constructNonType(type, pos)
    }

    override fun instantiate(instantiations: Map<String, Expression>): Expression =
        CtorExpr(type, pos)
}

class PlaceholderExpr(val name: String, pos: PosInfo): Expression(pos) {
    override fun evaluate(ctxt: ExecEnvironment): Value {
        throw CMLException.evaluatePlaceholder(name, pos)
    }

    override fun instantiate(instantiations: Map<String, Expression>): Expression {
        return instantiations[name] ?: throw AstException.undefinedPlaceholder(name, pos)
    }
}