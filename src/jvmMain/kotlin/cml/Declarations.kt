package cml

import arrow.core.Tuple4

class ArgsDecl(pos: PosInfo) : AstNode(pos) {
    val names = mutableListOf<String>()
}

class FunDecl(
    val name: String,
    val argNames: List<String>,
    val body: List<Statement>,
    declPos: PosInfo
) : AstNode(declPos) {
    var parent: TopLevelDecl? = null
    fun call(args: List<Value>, callSite: PosInfo): Value {
        if(argNames.size != args.size) throw CMLException.argCount(name, argNames.size, args.size, pos, callSite)

        val baseEnv = parent?.fields ?: ExecEnvironment(mapOf())
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

    fun copy() = FunDecl(name, argNames, body, pos)
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

    override fun equals(other: Any?): Boolean {
        if(other !is TopLevelDecl) return false
        return kind == other.kind && name == other.name && functions.keys == other.functions.keys && fields == other.fields
    }

    override fun hashCode(): Int = Tuple4(kind, name, functions, fields).hashCode()

    fun ready() {
        if(readied) return;

        readied = true
        fieldsPre.forEach {
            fields.addVar(it.key, it.value.evaluate(fields), it.value.pos)
        }
    }

    fun getField(field: String): Value? = getFieldAsVar(field)?.value

    fun getFieldAsVar(field: String): Variable? = fields.getVar(field)

    fun construct(): TopLevelDecl {
        if(!readied) ready()
        val fns = functions.map { (fn, decl) -> Pair(fn, decl.copy()) }.associate { it }
        return TopLevelDecl(kind, name, fns, fields.copy(fns), pos).also { it.functions.forEach{ (_, f) -> f.parent = it } }
    }
}

class TemplateDecl(
    val kind: String, val name: String, val argNames: List<String>, val functions: Map<String, FunDecl>,
    val fieldsPre: Map<String, Expression>, declPos: PosInfo
): AstNode(declPos) {
    constructor(
        kind: String, argNames: List<String>, functions: Map<String, FunDecl>, fieldsPre: Map<String, Expression>,
        declPos: PosInfo) : this(kind, kind, argNames, functions, fieldsPre, declPos)

    fun instantiate(target: InstanceDecl): TopLevelDecl {
        if(argNames.size != target.args.size)
            throw AstException.templateArgCount(kind, target.name, argNames.size, target.args.size, target.pos)

        val inst = argNames.zip(target.args).associate { it }
        val res = TopLevelDecl(
            kind = kind,
            name = target.name,
            functions = functions.map { (k, v) -> Pair(k, v.instantiate(inst)) }.toMap(),
            fieldsPre = fieldsPre.map { (k, v) -> Pair(k, v.instantiate(inst)) }.toMap(),
            declPos = target.pos
        )
        res.functions.forEach { (_, v) ->
            v.parent = res
        }
        return res
    }
}

class InstanceDecl(
    val template: String, val name: String, val args: List<Expression>, declPos: PosInfo
) : AstNode(declPos)

class GlobalDecl(val name: String, val data: Expression, declPos: PosInfo): AstNode(declPos) {
    fun toVar(ctxt: ExecEnvironment) = Variable(name, data.evaluate(ctxt).copy(pos), true, pos)
}

class DeclSet(pos: PosInfo) : AstNode(pos) {
    val functions = mutableListOf<FunDecl>()
    val fields = mutableListOf<VarDeclStmt>()
}

class TLDeclSet(pos: PosInfo) : AstNode(pos) {
    val declarations = mutableListOf<TopLevelDecl>()
    val templates = mutableListOf<TemplateDecl>()
    val instances = mutableListOf<InstanceDecl>()
    val freeFunctions = mutableListOf<FunDecl>()
    val globals = mutableListOf<GlobalDecl>()
}