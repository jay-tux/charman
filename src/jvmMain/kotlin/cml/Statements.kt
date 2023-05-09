package cml

abstract class Statement(pos: PosInfo) : AstNode(pos) {
    abstract fun execute(ctxt: ExecEnvironment)
}

class ExprStmt(private val e: Expression, pos: PosInfo): Statement(pos) {
    override fun execute(ctxt: ExecEnvironment) {
        e.evaluate(ctxt)
    }
}

class VarDeclStmt(
    private val name: String,
    private val init: Expression,
    pos: PosInfo
): Statement(pos) {
    override fun execute(ctxt: ExecEnvironment) {
        if(ctxt.isInThisEnv(name)) TODO("Error")
        ctxt.addVar(name, init.evaluate(ctxt), pos)
    }
}

class VarStoreStmt(
    private val name: String,
    private val upd: Expression,
    pos: PosInfo
): Statement(pos) {
    override fun execute(ctxt: ExecEnvironment) {
        if(!ctxt.isInEnv(name)) TODO("Error")
        ctxt.getVar(name)?.safeOverwrite(upd.evaluate(ctxt))
    }
}

class IfStmt(
    private val cond: Expression,
    private val bodyTrue: List<Statement>,
    private val bodyFalse: List<Statement>,
    pos: PosInfo
): Statement(pos) {
    override fun execute(ctxt: ExecEnvironment) {
        val result = cond.evaluate(ctxt)
        if(result !is BoolVal) TODO("Error")

        run returning@{
            val subScope = ExecEnvironment.defaultEnv(ctxt)
            if (result.value) {
                bodyTrue.forEach {
                    it.execute(subScope)
                    if(subScope.hitReturn) {
                        ctxt.hitReturn = true
                        return@returning
                    }
                }
            } else {
                bodyFalse.forEach {
                    it.execute(subScope)
                    if(subScope.hitReturn) {
                        ctxt.hitReturn = true
                        return@returning
                    }
                }
            }
        }
    }
}

class WhileStmt(
    private val cond: Expression,
    private val body: List<Statement>,
    pos: PosInfo
): Statement(pos) {
    override fun execute(ctxt: ExecEnvironment) {
        var value: Value = cond.evaluate(ctxt)
        if(value !is BoolVal) TODO("Error")

        run breaking@{
            while ((value as BoolVal).value) {
                val subScope = ExecEnvironment.loopEnv(ctxt)
                body.forEach {
                    it.execute(subScope)
                    if (subScope.hitBreak) return@breaking
                    if (subScope.hitReturn) {
                        ctxt.hitReturn = true
                        return@breaking
                    }
                }

                value = cond.evaluate(ctxt)
                if (value !is BoolVal) TODO("Error")
            }
        }
    }
}

class ForStmt(
    private val varN: String,
    private val range: Expression,
    private val body: List<Statement>,
    pos: PosInfo
): Statement(pos) {
    private fun <T> runLoop(check: ExecEnvironment, range: Iterable<T>, conversion: (T) -> Value) {
        run breaking@{
            range.forEach { elem ->
                check.getVar(varN)?.safeOverwrite(conversion(elem))
                val subScope = ExecEnvironment.loopEnv(check)
                body.forEach {
                    it.execute(subScope)
                    if(subScope.hitBreak) return@breaking

                    if(subScope.hitReturn) {
                        check.hitReturn = true
                        return@breaking
                    }
                }
            }
        }
    }

    override fun execute(ctxt: ExecEnvironment) {
        val rangeE = range.evaluate(ctxt)
        val checkScope = ExecEnvironment.constVarEnv(ctxt)
        // iteration "variable" is declared in for header

        when (rangeE) {
            is RangeVal -> {
                checkScope.addVar(varN, IntVal(rangeE.begin, range.pos), pos)
                runLoop(checkScope, rangeE.begin..rangeE.end) { IntVal(it, pos) }
            }

            is UntilVal -> {
                checkScope.addVar(varN, IntVal(rangeE.begin, range.pos), pos)
                runLoop(checkScope, rangeE.begin until rangeE.end) { IntVal(it, pos) }
            }

            is ListVal -> {
                if(rangeE.value.isEmpty()) return
                checkScope.addVar(varN, rangeE.value[0], pos)
                runLoop(checkScope, rangeE.value) { it }
            }

            is DictVal -> {
                if(rangeE.value.isEmpty()) return
                checkScope.addVar(varN, rangeE.value.keys.first(), pos)
                runLoop(checkScope, rangeE.value.asIterable()) { it.key }
            }

            else -> TODO("Error")
        }

        if(checkScope.hitReturn) ctxt.hitReturn = true
    }
}

class BreakStmt(pos: PosInfo): Statement(pos) {
    override fun execute(ctxt: ExecEnvironment) {
        if(ctxt.isInLoop) ctxt.hitBreak = true
        else TODO("Error")
    }
}

class ReturnStmt(pos: PosInfo): Statement(pos) {
    override fun execute(ctxt: ExecEnvironment) {
        ctxt.hitReturn = true
    }
}

class FuncCallStmt(
    private val name: String,
    private val args: List<Expression>,
    pos: PosInfo
): Statement(pos) {
    override fun execute(ctxt: ExecEnvironment) {
        if(!ctxt.functions.containsKey(name)) TODO("Error")
        ctxt.functions[name]?.call(args.map{ it.evaluate(ctxt) })
    }
}