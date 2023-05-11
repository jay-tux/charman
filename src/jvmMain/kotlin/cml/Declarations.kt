package cml

class ArgsDecl(pos: PosInfo) : AstNode(pos) {
    val names = mutableListOf<String>()
}

class FunDecl(
    val name: String,
    private val argNames: List<String>,
    private val body: List<Statement>,
    declPos: PosInfo
) : AstNode(declPos) {
    lateinit var parent: TopLevelDecl
    fun call(args: List<Value>): Value {
        if(argNames.size != args.size) TODO("Error")

        val baseEnv = parent.fields
        val argEnv = ExecEnvironment.constVarEnv(baseEnv)
        argNames.zip(args).forEach { (n, v) ->
            // "declaration" of this "variable" is at top of function
            argEnv.addVar(n, v, pos)
        }

        val callEnv = ExecEnvironment.defaultEnv(argEnv)
        run returning@{
            body.forEach {
                it.execute(callEnv)
                if(callEnv.hitReturn) {
                    return@returning
                }
            }
        }

        return callEnv.returnValue
    }

    fun argCount(): Int = argNames.size
}

class TopLevelDecl(
    val kind: String,
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
}

class DeclSet(pos: PosInfo) : AstNode(pos) {
    val functions = mutableListOf<FunDecl>()
    val fields = mutableListOf<VarDeclStmt>()
}

class TLDeclSet(pos: PosInfo) : AstNode(pos) {
    val declarations = mutableListOf<TopLevelDecl>()
}