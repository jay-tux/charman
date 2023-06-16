package data

import CMLOut
import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import ca.gosyer.appdirs.AppDirs
import cml.*
import com.jaytux.cml_parser.CMLLexer
import com.jaytux.cml_parser.CMLParser
import kotlinx.coroutines.Dispatchers
import mapOrEither
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import uiData.*
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.*

object Scripts {
    private val pos = PosInfo("<repl>", 0, 0)
    private val templates = mutableListOf<TemplateDecl>()
    private val instances = mutableListOf<InstanceDecl>()
    private val globals = mutableListOf<GlobalDecl>()
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

    private fun maybeInvoke(type: TopLevelDecl, func: String, args: List<Value>): Value? {
        return type.functions[func]?.call(args, pos)
    }

    fun loadFile(f: String) {
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
                ast.freeFunctions.forEach {
                    Library.addFunction(it.name, it)
                }
                ast.declarations.forEach {
                    Library.addType(it.name, it)
                }
            } catch(ex: LibraryException) {
                CMLOut.addError(ex.localizedMessage)
                println(ex.localizedMessage)
                return@use
            }

            globals.addAll(ast.globals)
            templates.addAll(ast.templates)
            instances.addAll(ast.instances)
        }
    }

    private fun instantiateAll() {
        instances.forEach { instance ->
            try {
                Library.addType(
                    instance.name,
                    templates.firstOrNull { it.name == instance.template }?.instantiate(instance) ?:
                        throw AstException.undefinedTemplate(instance.template, instance.name, instance.pos)
                )
            } catch(ex: LibraryException) {
                CMLOut.addError(ex.localizedMessage)
                println(ex.localizedMessage)
            } catch(ex: AstException) {
                CMLOut.addError(ex.localizedMessage)
                println(ex.localizedMessage)
            } catch(ex: CMLException) {
                CMLOut.addError(ex.localizedMessage)
                println(ex.localizedMessage)
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
        templates.clear()
        Library.clear()

        val message = "Loading script and character cache..."
        Dispatchers.IO.run {
            UIData.send(message)

            checkCacheDirs()
            scriptCache.walk().forEach {
                if(it.extension == "cml") {
                    CMLOut.addInfo("  Loading script ${it.absolutePathString()}")
                    loadFile(it.absolutePathString())
                }
            }

            try {
                globals.forEach { g -> Library.addGlobal(g) }
                globals.clear()
            } catch(ex: CMLException) {
                ex.message?.let { CMLOut.addError(it) }
            }

            instantiateAll()
            instances.clear()
            try {
                Library.readyAll()
            } catch(ex: CMLException) {
                ex.message?.let { CMLOut.addError(it) }
            }

            characterCache.walk().forEach {
                if(it.extension == "cml") {
                    CMLOut.addInfo("  Loading character ${it.absolutePathString()}")
                    loadFile(it.absolutePathString())
                }
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

    fun addToCache(paths: List<Path>) {
        paths.forEach {
            val target = scriptCache.resolve(it.name)
            if(target.exists()) {
                CMLOut.addWarning("Script ${it.name} (absolute path ${it.absolutePathString()}) already exists in the cache.")
            }
            else {
                try {
                    it.copyTo(target)
                    CMLOut.addInfo("Loading new script ${target.absolutePathString()}")
                    loadFile(target.absolutePathString())
                }
                catch(ioe: IOException) {
                    CMLOut.addError("Error while copying ${target.absolutePathString()} to cache: ${ioe.localizedMessage}")
                }
            }
        }
    }

    fun saveChar(data: Character) {
        FileOutputStream(characterCache.resolve("${data.name.value}.cml").toFile()).use { stream ->
            val serialized = data.serialize()
            stream.write(serialized.toByteArray(Charset.defaultCharset()))
            stream.flush()
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
                                lvlI.requireInt(posInit).flatMap { lvl ->
                                    if(lvl.value <= 0 || lvl.value > 20) CMLException("Class level should be at least 1 and no more than 20, ${lvl.value} given for class `$classN' at $posInit").left()
                                    else classI.getDice("hitDie", posInit).map { dice ->
                                        Pair(classN, ClassDesc(classI, lvl.value, dice, false))
                                    }
                                }
                            }
                        }.map { v -> v.toMutableMap() }.flatMap { classes ->
                            valid.getInst("primary", "Class", posInit).flatMap { cls ->
                                cls.getName(posInit).flatMap { initN ->
                                    classes[initN]?.let {
                                        classes[initN] = ClassDesc(it.cls, it.level, it.hitDice, true)
                                        Unit.right()
                                    } ?: CMLException("Primary class is not one of the character's classes at ${cls.pos}").left()
                                }.map { classes }
                            }
                        }.flatMap { classes ->
                            valid.getDict("abilities", posInit).flatMap { abs ->
                                abs.mapOrEither { (ab, score) ->
                                    ab.ifInstVerifyGetString("abbrev", "Ability", posInit).flatMap { (abA, abI) ->
                                        abI.getName(posInit).flatMap { abN ->
                                            score.requireInt(posInit).map { scoreV ->
                                                Pair(abA, AbilityDesc(abN, abI, scoreV.value))
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
        }.flatMap{ char ->
            valid.getDictV("charges", posInit).flatMap { charges ->
                charges.value.mapOrEither { (chrg, useMax) ->
                    chrg.requireString(posInit).flatMap { charge ->
                        useMax.requireListV(posInit).flatMap { uML ->
                            uML.requireSize(2).flatMap { sized ->
                                sized[0].requireInt(posInit).flatMap { used ->
                                    sized[1].requireInt(posInit).map { max ->
                                        Pair(charge, Pair(used.value, max.value))
                                    }
                                }
                            }
                        }
                    }
                }.map { chargesParsed ->
                    char.charges.value = chargesParsed
                    char
                }
            }
        }.map{ char ->
            char.onUpdate()
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
                val level = IntVal(char.classes.value.values.sumOf { it.level }, posRest)
                char.race.value.second.type.functions["onRestore"]?.call(listOf(DictVal(choices.raceChoices, posRest), level), posRest)
                char.racialTraits.value.forEach { (k, v) ->
                    v.instance.type.functions["onRestore"]?.call(listOf(DictVal(choices.raceChoices, posRest), level), posRest)
                }

                val altMap = mutableMapOf<String, MutableMap<Value, Value>>()
                char.classes.value.forEach { e ->
                    e.value.cls.type.functions["onRestore"]?.call(
                        choices.classesChoices[e.key]?.let {
                            altMap[e.key] = it
                            listOf(DictVal(it, posRest), IntVal(e.value.level, posRest), BoolVal(e.value.isPrimary, posRest))
                        } ?: throw CMLException.keyError(e.key, posRest),
                        posRest
                    )
                }

                altMap.forEach { (cls, ch) ->
                    char.classTraits.value.filter { it.value.source == cls }.forEach { (k, v) ->
                        v.instance.type.functions["onRestore"]?.call(listOf(DictVal(ch, posRest), level), posRest)
                    }
                }

                char.background.value.second.type.functions["onRestore"]?.call(listOf(DictVal(choices.backgroundChoices, posRest)), posRest)

                altMap.forEach { (cls, ch) ->
                    char.classTraits.value.filter { it.value.source == cls }.forEach { (k, v) ->
                        v.instance.type.functions["onLateRestore"]?.call(listOf(DictVal(ch, posRest), level), posRest)
                    }
                }

                char.choices.value = choices
                char.refreshData()
                char
            }
        }.flatMap { char ->
            valid.getInt("maxHP", posInit).map { char.hp.value = it }
                .flatMap{ valid.getInt("damage", posInit) }.map { char.damage.value = it }
                .flatMap{ valid.getInt("tempHP", posInit) }.map { char.tempHp.value = it }
                .flatMap{ valid.getInt("speed", posInit) }.map { char.speed.value = it }
                .flatMap{ valid.getBool("inspiration", posInit) }.map { char.inspiration.value = it }
                .flatMap{ valid.getString("notes", posInit) }.map { char.notes.value = it }
                .flatMap{ valid.getInt("deathSavesFailed", posInit) }.flatMap { failed ->
                    valid.getInt("deathSavesSucceeded", posInit).map { Pair(failed, it) }
                }.map { char.deathSaves.value = it }
                .map { char.onUpdate() }
                .map { char }
        }.flatMap { char ->
            valid.getDict("inventory", posInit).flatMap { inv ->
                inv.mapOrEither { (item, count) ->
                    loadItem(item).flatMap { (itemD, actions) ->
                        actions.mapOrEither {
                            CMLException.catching {
                                CharacterScope(char).addAction(listOf(it), posInit)
                            }
                        }.flatMap { _ ->
                            count.requireInt(posInit).map {
                                Pair(itemD, it.value)
                            }
                        }
                    }
                }
            }.map {
                char.inventory.value = it
                char.onUpdate()
                char
            }
        }.flatMap { char ->
            valid.getDict("currency", posInit).flatMap { currency ->
                val current = char.money.value.toMutableMap()

                currency.mapOrEither { (curr, amount) ->
                    curr.ifInstVerifyGetName("Currency", posInit).flatMap { (name, currInst) ->
                        currInst.getString("abbrev", posInit).flatMap { abbrev ->
                            currInst.getInt("conversionRatio", posInit).flatMap { ratio ->
                                amount.requireInt(posInit).map { amountV ->
                                    Pair(abbrev, MoneyDesc(amountV.value, name, ratio, currInst))
                                }
                            }
                        }
                    }
                }.map {
                    it.forEach { (k, v) -> current[k] = v }
                    char.money.value = current
                    char
                }
            }
        }.flatMap { char ->
            valid.getList("usedSpellSlots", posInit).flatMap { uSS ->
                uSS.requireSize(9).flatMap { slots ->
                    slots.mapOrEither { it.requireInt(posInit).map { it.value } }.map {
                        char.usedSpellSlots.value = SpellSlots.from(it)
                        char
                    }
                }
            }
        }.flatMap { char ->
            valid.getDict("usedSpellSlotsSpecial", posInit).flatMap { uSSS ->
                uSSS.mapOrEither { (clsPre, slotsPre) ->
                    clsPre.requireString(posInit).flatMap { cls ->
                        slotsPre.requireListV(posInit).flatMap { slotsUncounted ->
                            slotsUncounted.requireSize(9).flatMap { slotsUnchecked ->
                                slotsUnchecked.mapOrEither { it.requireInt(posInit).map { i -> i.value } }
                            }.map { slots ->
                                Pair(cls, SpellSlots.from(slots))
                            }
                        }
                    }
                }.map { slotData ->
                    char.usedSpellSlotsSpecial.value = slotData
                    char
                }
            }
        }
    }.mapLeft {
        it.message?.let { msg -> CMLOut.addError(msg) }
        it
    }
}

fun Character.Companion.loadItem(item: Value): Either<CMLException, Pair<ItemDesc, List<InstanceVal>>> {
    val actionsL = mutableListOf<InstanceVal>()
    return item.ifInstVerifyGetName("Item", posInit).flatMap { (name, inst) ->
        inst.getFloat("weight", posInit).flatMap { weight ->
            inst.getList("actions", posInit).flatMap { actions ->
                actions.value.mapOrEither {
                    it.ifInstVerify("Action", posInit).map { a ->
                        actionsL.add(a)
                        a
                    }
                }
            }.flatMap { actions ->
                inst.getList("additionalTraits", posInit).flatMap { traits ->
                    traits.value.mapOrEither {
                        it.ifInstVerifyGetName("ItemTrait", posInit).flatMap { (iName, iT) ->
                            iT.getString("desc", posInit).map { iDesc -> Triple(iName, iDesc, iT) }
                        }
                    }
                }.flatMap { traits ->
                    inst.getVerifyInst("value", "Currency", posInit).flatMap { cost ->
                        cost.getString("abbrev", posInit).flatMap { abbrev ->
                            cost.getInt("amount", posInit).map { count ->
                                Triple(count, abbrev, cost)
                            }
                        }
                    }.flatMap { price ->
                        inst.getList("tags", posInit).flatMap { tags ->
                            tags.value.mapOrEither { it.requireString(posInit) }
                        }.map { tags ->
                            ItemDesc(name, weight, price, actions, traits, tags, inst, inst.type.functions.containsKey("onDon"))
                        }
                    }
                }
            }
        }
    }.map { res ->
        Pair(res, actionsL)
    }
}