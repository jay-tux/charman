package cml

class ArgsDecl(pos: PosInfo) : AstNode(pos) {
    val names = mutableListOf<String>()
}

class FunDecl(
    val name: String,
    val argNames: List<String>,
    val body: List<Statement>,
    declPos: PosInfo
) : AstNode(declPos) {
    lateinit var parent: TopLevelDecl
    fun call(args: List<Value>, callSite: PosInfo): Value {
        if(argNames.size != args.size) throw CMLException.argCount(name, argNames.size, args.size, pos, callSite)

        val baseEnv = parent.fields
        val argEnv = ExecEnvironment.constVarEnv(baseEnv)
        argNames.zip(args).forEach { (n, v) ->
            // "declaration" of this "variable" is at top of function
            argEnv.addVar(n, v, pos)
        }

        ExecutionStack.push(callSite)
        val callEnv = ExecEnvironment.defaultEnv(argEnv)
        run returning@{
            body.forEach {
                it.execute(callEnv)
                if(callEnv.hitReturn) {
                    return@returning
                }
            }
        }
        ExecutionStack.pop()

        return callEnv.returnValue
    }

    fun argCount(): Int = argNames.size
}

open class TopLevelDecl(
    val kind: String,
    val name: String,
    val functions: Map<String, FunDecl>,
    val fieldsPre: Map<String, Expression>,
    declPos: PosInfo
) : AstNode(declPos) {
    private constructor(
        kind: String, name: String, functions: Map<String, FunDecl>,
        env: ExecEnvironment, declPos: PosInfo
    ) : this(kind, name, functions, mapOf(), declPos) {
        fields = env
    }

    var fields = ExecEnvironment(functions)
        private set
    private var readied = false

    fun ready() {
        if(readied) return;

        fieldsPre.forEach {
            fields.addVar(it.key, it.value.evaluate(fields), it.value.pos)
        }
        readied = true
    }

    fun getField(field: String): Value? = getFieldAsVar(field)?.value

    fun getFieldAsVar(field: String): Variable? = fields.getVar(field)

    fun construct() = TopLevelDecl(kind, name, functions, fields.copy(), pos)
}

class TemplateDecl(
    val kind: String, val argNames: List<String>, val functions: Map<String, FunDecl>,
    val fieldsPre: Map<String, Expression>, declPos: PosInfo
): AstNode(declPos) {
    fun instantiate(target: InstanceDecl): TopLevelDecl {
        if(argNames.size != target.args.size)
            throw AstException.templateArgCount(kind, target.name, argNames.size, target.args.size, target.pos)

        val inst = argNames.zip(target.args).associate { it }
        return TopLevelDecl(
            kind = kind,
            name = target.name,
            functions = functions.map { (k, v) -> Pair(k, v.instantiate(inst)) }.toMap(),
            fieldsPre = fieldsPre.map { (k, v) -> Pair(k, v.instantiate(inst)) }.toMap(),
            declPos = pos
        )
    }
}

class InstanceDecl(
    val template: String, val name: String, val args: List<Expression>, declPos: PosInfo
) : AstNode(declPos)

class DeclSet(pos: PosInfo) : AstNode(pos) {
    val functions = mutableListOf<FunDecl>()
    val fields = mutableListOf<VarDeclStmt>()
}

class TLDeclSet(pos: PosInfo) : AstNode(pos) {
    val declarations = mutableListOf<TopLevelDecl>()
    val templates = mutableListOf<TemplateDecl>()
    val instances = mutableListOf<InstanceDecl>()
}