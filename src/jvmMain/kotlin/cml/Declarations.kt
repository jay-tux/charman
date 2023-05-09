package cml

class FunDecl(
    private val argNames: List<String>,
    private val body: List<Statement>,
    private val instance: ExecEnvironment,
    declPos: PosInfo
) : AstNode(declPos) {
    fun call(args: List<Value>) {
        if(argNames.size != args.size) TODO("Error")

        val baseEnv = ExecEnvironment(instance)
        val argEnv = ExecEnvironment.constVarEnv(baseEnv)
        argNames.zip(args).forEach { (n, v) ->
            // "declaration" of this "variable" is at top of function
            argEnv.addVar(n, v, pos)
        }

        val callEnv = ExecEnvironment.defaultEnv(argEnv)
        run returning@{
            body.forEach {
                it.execute(callEnv)
                if(callEnv.hitReturn) return@returning
            }
        }
    }

    fun argCount(): Int = argNames.size
}

abstract class TopLevelDecl(
    val name: String,
    val functions: Map<String, FunDecl>,
    fieldsPre: Map<String, Expression>,
    declPos: PosInfo
) : AstNode(declPos) {
    val fields = ExecEnvironment(functions)

    init {
        fieldsPre.forEach {
            fields.addVar(it.key, it.value.evaluate(fields), it.value.pos)
        }
    }

    abstract fun verify(): Boolean
}

class RaceDecl(
    name: String,
    functions: Map<String, FunDecl>,
    fieldsPre: Map<String, Expression>,
    declPos: PosInfo
) : TopLevelDecl(name, functions, fieldsPre, declPos) {
    override fun verify(): Boolean {
        TODO()
    }
}

class ClassDecl(
    name: String,
    functions: Map<String, FunDecl>,
    fieldsPre: Map<String, Expression>,
    declPos: PosInfo
) : TopLevelDecl(name, functions, fieldsPre, declPos) {
    override fun verify(): Boolean {
        TODO()
    }
}

class BackgroundDecl(
    name: String,
    functions: Map<String, FunDecl>,
    fieldsPre: Map<String, Expression>,
    declPos: PosInfo
) : TopLevelDecl(name, functions, fieldsPre, declPos) {
    override fun verify(): Boolean {
        TODO()
    }
}

class ItemDecl(
    name: String,
    functions: Map<String, FunDecl>,
    fieldsPre: Map<String, Expression>,
    declPos: PosInfo
) : TopLevelDecl(name, functions, fieldsPre, declPos) {
    override fun verify(): Boolean {
        TODO()
    }
}

class SpellDecl(
    name: String,
    functions: Map<String, FunDecl>,
    fieldsPre: Map<String, Expression>,
    declPos: PosInfo
) : TopLevelDecl(name, functions, fieldsPre, declPos) {
    override fun verify(): Boolean {
        TODO()
    }
}