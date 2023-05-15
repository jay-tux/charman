package data

import CMLOut
import ca.gosyer.appdirs.AppDirs
import cml.*
import com.jaytux.cml_parser.CMLLexer
import com.jaytux.cml_parser.CMLParser
import kotlinx.coroutines.Dispatchers
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import uiData.UIData
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.absolutePathString
import kotlin.io.path.walk

object Scripts {
    private val pos = PosInfo("<repl>", 0, 0)
    private val templates = mutableMapOf<String, TemplateDecl>()
    private val instances = mutableMapOf<String, InstanceDecl>()
    var loading = true
        private set

    lateinit var scriptCache: Path
        private set
    lateinit var characterCache: Path
        private set

    var file: String = ""
        private set

    private fun maybeConstruct(type: String): InstanceVal? {
        return Library.construct(type, pos)
    }

    private inline fun <reified T : Value> maybeGetVar(type: TopLevelDecl, name: String) : T? {
        val tmp = type.fields.getVar(name)?.value
        if(tmp !is T?) return null
        return tmp
    }

    private fun maybeInvoke(type: TopLevelDecl, func: String, args: List<Value>): Value? {
        return type.functions[func]?.call(args, pos)
    }

    private fun loadFile(f: String) {
        file = f
        FileInputStream(file).use { stream ->
            lateinit var ast: TLDeclSet
            try {
                val lexer = CMLLexer(CharStreams.fromStream(stream))
                lexer.removeErrorListeners()
                lexer.addErrorListener(AntlrError)
                val tokStream = CommonTokenStream(lexer)
                val parser = CMLParser(tokStream)
                parser.removeErrorListeners()
                parser.addErrorListener(AntlrError)
                val tree = parser.program()
                ast = AstBuilder(file).visit(tree) as TLDeclSet
            } catch(ex: AstException) {
                ex.message?.let { CMLOut.addError(it) }
                return@use
            }

            try {
                ast.declarations.forEach {
                    Library.addType(it.name, it)
                }
            } catch(ex: LibraryException) {
                ex.message?.let { CMLOut.addError(it) }
                return@use
            }

            templates.putAll(ast.templates.map { Pair(it.kind, it) })
            instances.putAll(ast.instances.map { Pair(it.name, it) })
        }
    }

    private fun instantiateAll() {
        instances.forEach { (name, instance) ->
            try {
                Library.addType(
                    name,
                    templates[instance.template]?.instantiate(instance) ?:
                        throw AstException.undefinedTemplate(instance.template, name, instance.pos)
                )
            } catch(ex: LibraryException) {
                ex.message?.let { CMLOut.addError(it) }
            } catch(ex: AstException) {
                ex.message?.let { CMLOut.addError(it) }
            } catch(ex: CMLException) {
                ex.message?.let { CMLOut.addError(it) }
            }
        }
    }

    private fun checkCacheDirs() {
        val cache = AppDirs("charman", "jay-tux").getUserCacheDir()
        val cacheP = Paths.get(cache)
        if(!Files.isDirectory(cacheP)) {
            Files.createDirectories(cacheP)
        }
        scriptCache = Paths.get(cacheP.absolutePathString(), "scripts")
        if(!Files.isDirectory(scriptCache)) {
            Files.createDirectories(scriptCache)
        }
        characterCache = Paths.get(cacheP.absolutePathString(), "chars")
        if(!Files.isDirectory(characterCache)) {
            Files.createDirectories(characterCache)
        }

        CMLOut.addInfo("Cache directory is ${cacheP.absolutePathString()}")
        CMLOut.addInfo("Scripts directory is ${scriptCache.absolutePathString()}")
        CMLOut.addInfo("data.Character cache directory is ${characterCache.absolutePathString()}")
    }

    @OptIn(ExperimentalPathApi::class)
    fun loadCache() {
        val message = "Loading script cache..."
        Dispatchers.IO.run {
            UIData.send(message)

            checkCacheDirs()
            scriptCache.walk().forEach {
                CMLOut.addInfo("  Loading script ${it.absolutePathString()}")
                loadFile(it.absolutePathString())
            }

            instantiateAll()
            instances.clear()
            try {
                Library.readyAll()
            } catch(ex: CMLException) {
                ex.message?.let { CMLOut.addError(it) }
            }


            UIData.clearIf(message)
            loading = false
        }
    }
}