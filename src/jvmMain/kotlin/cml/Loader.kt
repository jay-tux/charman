package cml

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import parsing.CMLLexer
import parsing.CMLParser
import java.io.FileInputStream

class Scripts {
    val data = mutableMapOf<String, TopLevelDecl>()

    inline fun <reified T : Value> maybeGetVar(type: String, name: String) : T? {
        val tmp = data[type]?.fields?.getVar(name)?.value
        if(tmp !is T?) return null
        return tmp
    }

    fun maybeInvoke(type: String, func: String, args: List<Value>) {
        data[type]?.functions?.get(func)?.call(args)
    }

    fun loadFile(file: String) {
        FileInputStream(file).use { stream ->
            val lexer = CMLLexer(CharStreams.fromStream(stream))
            val tokStream = CommonTokenStream(lexer)
            val parser = CMLParser(tokStream)
            val tree = parser.program()
            val ast = AstBuilder(file).visit(tree) as TLDeclSet

            ast.declarations.forEach {
                if(data.containsKey(it.name)) TODO()
                data[it.name] = it
            }

            println(maybeGetVar<IntVal>("something", "base")?.value)
            maybeInvoke("something", "update", listOf())
            println(maybeGetVar<IntVal>("something", "base")?.value)
            maybeInvoke("something", "updateUseArg", listOf(IntVal(42, PosInfo("<repl>", 0, 0))))
            println(maybeGetVar<IntVal>("something", "base")?.value)
        }
    }
}