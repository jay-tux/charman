package data

import CMLOut
import arrow.core.Either
import arrow.core.flatMap
import ca.gosyer.appdirs.AppDirs
import cml.*
import com.jaytux.cml_parser.CMLLexer
import com.jaytux.cml_parser.CMLParser
import kotlinx.coroutines.Dispatchers
import mapOrEither
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import uiData.Character
import uiData.CharacterData
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
        CMLOut.addInfo("Character cache directory is ${characterCache.absolutePathString()}")
    }

    @OptIn(ExperimentalPathApi::class)
    fun loadCache() {
        val message = "Loading script and character cache..."
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

            characterCache.walk().forEach {
                CMLOut.addInfo("  Loading character ${it.absolutePathString()}")
                loadFile(it.absolutePathString())
            }

            instantiateAll()
            instances.clear()
            try {
                Library.readyAll()
            } catch(ex: CMLException) {
                ex.message?.let { CMLOut.addError(it) }
            }

            CharacterData.loadFromLibrary()
            UIData.getTypesFromLibrary()
            UIData.clearIf(message)
            loading = false
        }
    }
}

class Choices {
    var raceChoices = mutableMapOf<Value, Value>() // string -> instance
    var classesChoices = mutableMapOf<String, MutableMap<Value, Value>>() // instance name -> (string -> instance)
    var backgroundChoices = mutableMapOf<Value, Value>() // string -> instance

    var racePos: PosInfo = Character.posRest
    var classesPos: PosInfo = Character.posRest
    var backgroundPos: PosInfo = Character.posRest
}

fun Character.Companion.loadFromInstance(inst: InstanceVal): Either<CMLException, Character> {
    return inst.verifyKind("Character", posInit).flatMap { valid ->
        valid.getName(posInit).flatMap { name ->
            valid.getVerifyInstAndName("race", "Race", posInit).flatMap { (rN, race) ->
                valid.getVerifyInstAndName("background", "Background", posInit).flatMap { (bN, background) ->
                    valid.getDict("classes", posInit).flatMap { cls ->
                        cls.mapOrEither { (clsI, lvlI) ->
                            clsI.ifInstVerifyGetName("Class", posInit).flatMap { (classN, classI) ->
                                lvlI.requireInt(posInit).map { lvl ->
                                    Pair(classN, Pair(classI, lvl.value))
                                }
                            }
                        }.map { v -> v.toMutableMap() }.flatMap { classes ->
                            valid.getDict("abilities", posInit).flatMap { abs ->
                                abs.mapOrEither { (ab, score) ->
                                    ab.ifInstVerifyGetString("abbrev", "Ability", posInit).flatMap { (abA, abI) ->
                                        abI.getName(posInit).flatMap { abN ->
                                            score.requireInt(posInit).map { scoreV ->
                                                Pair(abA, Triple(abN, abI, scoreV.value))
                                            }
                                        }
                                    }
                                }
                            }.map { v -> v.toMutableMap() }.map { abilities ->
                                Character(
                                    name = name,
                                    race = Pair(rN, race),
                                    background = Pair(bN, background),
                                    classes = classes,
                                    abilities = abilities
                                )
                            }
                        }
                    }
                }
            }
        }.map{ char ->
            Pair(char, Choices())
        }.flatMap { (c, ch) ->
            valid.getDictV("choicesRace", posRest).flatMap { cR ->
                ch.racePos = cR.pos
                cR.value.mapOrEither { (k, v) ->
                    k.requireString(posRest).map { key -> Pair(StringVal(key, k.pos), v) }
                }
            }.map {
                ch.raceChoices = it.toMutableMap()
                Pair(c, ch)
            }
        }.flatMap{ (c, ch) ->
            valid.getDictV("choicesBackground", posRest).flatMap { cB ->
                ch.backgroundPos = cB.pos
                cB.value.mapOrEither { (k, v) ->
                    k.requireString(posRest).map { key -> Pair(StringVal(key, k.pos), v)}
                }
            }.map {
                ch.backgroundChoices = it.toMutableMap()
                Pair(c, ch)
            }
        }.flatMap { (c, ch) ->
            valid.getDictV("choicesClasses", posRest).flatMap { cCs ->
                ch.classesPos = cCs.pos
                cCs.value.mapOrEither { (cl, conf) ->
                    cl.requireInstance(posRest).flatMap { cls ->
                        conf.requireDict(posRest).flatMap { map ->
                            map.mapOrEither { (k, v) ->
                                k.requireString(posRest).map { key -> Pair(StringVal(key, k.pos) as Value, v) }
                            }.map { it.toMutableMap() }
                        }.map { Pair(cls.type.name, it) }
                    }
                }.map {
                    ch.classesChoices = it.toMutableMap()
                    Pair(c, ch)
                }
            }
        }.flatMap { (char, choices) ->
            Library.withCharacter(char) {
                val level = IntVal(char.classes.values.sumOf { it.second }, posRest)
                char.race.second.type.functions["onRestore"]?.call(listOf(DictVal(choices.raceChoices, posRest), level), posRest)
                val raceUpd = mutableListOf<Triple<String, String, Pair<String, InstanceVal>>>()
                char.racialTraits.forEach { (k, v) ->
                    v.second.type.functions["onRestore"]?.call(listOf(DictVal(choices.raceChoices, posRest), level), posRest)?.let {
                        v.second.getString("name", posRest).flatMap { name ->
                            v.second.getString("desc", posRest).map { desc ->
                                raceUpd.add(Triple(k, name, Pair(desc, v.second)))
                            }
                        }.fold({ CMLOut.addError(it.localizedMessage) }, {})
                    }
                }
                raceUpd.forEach { (kOld, k, upd) ->
                    char.racialTraits.remove(kOld)
                    char.racialTraits[k] = upd
                }

                val altMap = mutableMapOf<String, MutableMap<Value, Value>>()
                char.classes.forEach { e ->
                    e.value.first.type.functions["onRestore"]?.call(
                        choices.classesChoices[e.key]?.let {
                            altMap[e.key] = it
                            listOf(DictVal(it, posRest), IntVal(e.value.second, posRest))
                        } ?: throw CMLException.keyError(e.key, posRest),
                        posRest
                    )
                }

                // ugly iteration hack to avoid java.util.ConcurrentModificationException
                altMap.forEach { (cls, ch) ->
                    char.classTraits.filter { it.value.second == cls }.forEach { (k, v) ->
                        val classUpd = mutableListOf<Triple<String, String, Triple<String, String, InstanceVal>>>()
                        v.third.type.functions["onRestore"]?.call(listOf(DictVal(ch, posRest), level), posRest)?.let {
                            v.third.getString("name", posRest).flatMap { name ->
                                v.third.getString("desc", posRest).map { desc ->
                                    classUpd.add(Triple(k, name, Triple(desc, v.second, v.third)))
                                }
                            }.fold({ CMLOut.addError(it.localizedMessage) }, {})
                        }
                        classUpd.forEach { (kOld, k, upd) ->
                            char.classTraits.remove(kOld)
                            char.classTraits[k] = upd
                        }
                    }
                }

                char.background.second.type.functions["onRestore"]?.call(listOf(DictVal(choices.backgroundChoices, posRest)), posRest)

                char
            }
        }.flatMap { char ->
            valid.getInt("maxHP", posInit).map { char.hp.value = it }
                .flatMap{ valid.getInt("damage", posInit) }.map { char.damage.value = it }
                .flatMap{ valid.getInt("tempHP", posInit) }.map { char.tempHp.value = it }
                .flatMap{ valid.getInt("speed", posInit) }.map { char.speed.value = it }
                .flatMap{ valid.getInt("deathSavesFailed", posInit) }.flatMap { failed ->
                    valid.getInt("deathSavesSucceeded", posInit).map { Pair(failed, it) }
                }.map { char.deathSaves.value = it }
                .map { char.onUpdate() }
                .map { char }
        }
    }
        .mapLeft {
            it.message?.let { msg -> CMLOut.addError(msg) }
            it
        }
}