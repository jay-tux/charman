package cml

abstract class Statement(pos: PosInfo) : AstNode(pos) {
    abstract fun execute(ctxt: ExecEnvironment)

    abstract fun instantiate(instantiations: Map<String, Expression>): Statement
}

class StmtSet(pos: PosInfo) : AstNode(pos) {
    val contained = mutableListOf<Statement>()
}

class ExprStmt(private val e: Expression, pos: PosInfo): Statement(pos) {
    override fun execute(ctxt: ExecEnvironment) {
        e.evaluate(ctxt)
    }

    override fun instantiate(instantiations: Map<String, Expression>): Statement =
        ExprStmt(e.instantiate(instantiations), pos)
}

class VarDeclStmt(
    val name: String,
    val init: Expression,
    pos: PosInfo
): Statement(pos) {
    override fun execute(ctxt: ExecEnvironment) {
        ctxt.addVar(name, init.evaluate(ctxt), pos)
    }

    override fun instantiate(instantiations: Map<String, Expression>): Statement =
        VarDeclStmt(name, init.instantiate(instantiations), pos)
}

class VarStoreStmt(
    private val name: LValue,
    private val upd: Expression,
    pos: PosInfo
): Statement(pos) {
    override fun execute(ctxt: ExecEnvironment) {
        val variable = name.evaluateToRef(ctxt)
        variable.safeOverwrite(upd.evaluate(ctxt), pos)
    }

    override fun instantiate(instantiations: Map<String, Expression>): Statement =
        VarStoreStmt(name, upd.instantiate(instantiations), pos)
}

class IfStmt(
    private val cond: Expression,
    private val bodyTrue: List<Statement>,
    private val bodyFalse: List<Statement>,
    pos: PosInfo
): Statement(pos) {
    override fun execute(ctxt: ExecEnvironment) {
        val result = cond.evaluate(ctxt)
        if(result !is BoolVal) throw CMLException.typeError("bool", result, pos)

        runStmtBlock(if(result.value) bodyTrue else bodyFalse, ctxt)
    }

    override fun instantiate(instantiations: Map<String, Expression>): Statement =
        IfStmt(
            cond.instantiate(instantiations),
            bodyTrue.map { it.instantiate(instantiations) },
            bodyFalse.map { it.instantiate(instantiations) },
            pos
        )
}

class WhileStmt(
    private val cond: Expression,
    private val body: List<Statement>,
    pos: PosInfo
): Statement(pos) {
    override fun execute(ctxt: ExecEnvironment) {
        var value = cond.evaluate(ctxt)
        if(value !is BoolVal) throw CMLException.typeError("bool", value, pos)

        while((value as BoolVal).value) {
            if(runStmtBlock(body, ctxt, true)) break
            value = cond.evaluate(ctxt)
            if(value !is BoolVal) throw CMLException.typeError("bool", value, pos)
        }
    }

    override fun instantiate(instantiations: Map<String, Expression>): Statement =
        WhileStmt(cond.instantiate(instantiations), body.map{ it.instantiate(instantiations) }, pos)
}

class ForStmt(
    private val varN: String,
    private val range: Expression,
    private val body: List<Statement>,
    pos: PosInfo
): Statement(pos) {
    private fun <T> runLoop(check: ExecEnvironment, range: Iterable<T>, conversion: (T) -> Value) {
        for(elem in range) {
            check.getVar(varN)?.forceOverwrite(conversion(elem))
            if(runStmtBlock(body, check, true)) break
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

            else -> throw CMLException.typeError("iterable (range/until/list/dict)", rangeE, pos)
        }

        if(checkScope.hitReturn) {
            ctxt.hitReturn = true
            ctxt.returnValue = checkScope.returnValue
        }
    }

    override fun instantiate(instantiations: Map<String, Expression>): Statement =
        ForStmt(varN, range.instantiate(instantiations), body.map { it.instantiate(instantiations) }, pos)
}

class BreakStmt(pos: PosInfo): Statement(pos) {
    override fun execute(ctxt: ExecEnvironment) {
        if(ctxt.isInLoop()) ctxt.hitBreak = true
        else throw CMLException.invalidBreak(pos)
    }

    override fun instantiate(instantiations: Map<String, Expression>): Statement = BreakStmt(pos)
}

class ReturnStmt(val value: Expression?, pos: PosInfo): Statement(pos) {
    override fun execute(ctxt: ExecEnvironment) {
        ctxt.hitReturn = true
        ctxt.returnValue = value?.evaluate(ctxt) ?: VoidVal(pos)
    }

    override fun instantiate(instantiations: Map<String, Expression>): Statement =
        ReturnStmt(value?.instantiate(instantiations), pos)
}

fun runStmtBlock(block: List<Statement>, env: ExecEnvironment, shouldBeLoopEnv: Boolean = false): Boolean {
    val subScope = if(shouldBeLoopEnv) ExecEnvironment.loopEnv(env) else ExecEnvironment.defaultEnv(env)
    for(stmt in block) {
        stmt.execute(subScope)
        if(subScope.hitReturn) {
            env.returnValue = subScope.returnValue
            env.hitReturn = true
            return true
        }
        if(subScope.hitBreak) {
            if(!shouldBeLoopEnv) env.hitBreak = true
            return true
        }
    }
    return false
}