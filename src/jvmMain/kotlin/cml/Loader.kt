package cml

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import parsing.CMLLexer
import parsing.CMLParser
import java.io.FileInputStream

class Scripts {
    val pos = PosInfo("<repl>", 0, 0)

    private fun maybeConstruct(type: String): InstanceVal? {
        return Library.construct(type, pos)
    }

    private inline fun <reified T : Value> maybeGetVar(type: TopLevelDecl, name: String) : T? {
        val tmp = type.fields.getVar(name)?.value
        if(tmp !is T?) return null
        return tmp
    }

    private fun maybeInvoke(type: TopLevelDecl, func: String, args: List<Value>): Value? {
        return type.functions[func]?.call(args)
    }

    fun loadFile(file: String) {
        FileInputStream(file).use { stream ->
            val lexer = CMLLexer(CharStreams.fromStream(stream))
            val tokStream = CommonTokenStream(lexer)
            val parser = CMLParser(tokStream)
            val tree = parser.program()
            val ast = AstBuilder(file).visit(tree) as TLDeclSet

            ast.declarations.forEach {
                Library.addType(it.name, it)
            }

            maybeConstruct("something")?.let {
                println(maybeGetVar<IntVal>(it.type, "base")?.value)
                println(maybeInvoke(it.type, "update", listOf())?.repr())
                println(maybeGetVar<IntVal>(it.type, "base")?.value)
                println(maybeInvoke(
                    it.type,
                    "updateUseArg",
                    listOf(IntVal(42, PosInfo("<repl>", 0, 0)))
                )?.repr())
                println(maybeGetVar<IntVal>(it.type, "base")?.value)
                println(maybeInvoke(
                    it.type,
                    "squareAddBase",
                    listOf(IntVal(12, PosInfo("<repl>", 0, 0)))
                )?.repr())
                println(maybeInvoke(
                    it.type,
                    "construct",
                    listOf()
                )?.repr())
            }
        }
    }
}