package cml

import CMLOut
import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import data.*
import uiData.Character
import kotlin.concurrent.thread

object StdLib {
    private val functions = mutableMapOf<String, (List<Value>, PosInfo) -> Value>(
        Pair("appendStr") { args, _ -> appendStr(args) },
        Pair("appendList") { args, p -> appendList(args, p) },
        Pair("split") { args, p -> split(args, p) },
        Pair("trim") { args, p -> trim(args, p) },
        Pair("inDict") { args, p -> inDict(args, p) },
        Pair("raise") { args, p -> raise(args, p) },
        Pair("log") { args, p -> log(args, p) },
        Pair("isInt") { args, p -> isInt(args, p) },
        Pair("empty") { args, p -> empty(args, p) },
        Pair("inList") { args, p -> inList(args, p) },
        Pair("replace") { args, p -> replace(args, p) }
    )

    fun isStd(name: String): Boolean = functions.containsKey(name)
    fun invoke(name: String, args: List<Value>, callSite: PosInfo): Value? {
        return functions[name]?.let {
            ExecutionStack.push(callSite)
            val res = it(args, callSite)
            ExecutionStack.pop()
            res
        }
    }

    fun addFunction(name: String, callback: (List<Value>, PosInfo) -> Value) {
        if(functions.containsKey(name)) throw LibraryException.stdFunAlreadyExists(name)
        functions[name] = callback
    }

    fun allStdFunc(): Collection<String> = functions.keys

    fun posInfo(func: String): PosInfo = PosInfo("<stdlib:$func>", 0, 0)
}

object Library {
    fun ctxPos(fn: String) = PosInfo("<lib:context:$fn>", 0, 0)

    private val contextFunctions = mutableMapOf<String, CharacterScope.(List<Value>, PosInfo) -> Value>(
        Pair("abilityIncrease") { args, pos -> abilityIncrease(args, pos) },
        Pair("addMaxHP") { args, pos -> addMaxHP(args, pos) },
        Pair("addRacialTraits") { args, pos -> addRacialTraits(args, pos) },
        Pair("addBackgroundTraits") { args, pos -> addBackgroundTraits(args, pos) },
        Pair("addLanguages") { args, pos -> addLanguages(args, pos) },
        Pair("addSkillProficiencies") { args, pos -> addSkillProficiencies(args, pos) },
        Pair("addSaveProficiencies") { args, pos -> addSaveProficiencies(args, pos) },
        Pair("addItemProficiencies") { args, pos -> addItemProficiencies(args, pos) },
        Pair("addClassTraits") { args, pos -> addClassTraits(args, pos) },
        Pair("getAbilityMod") { args, pos -> getAbilityMod(args, pos) },
        Pair("getAbilities") { args, pos -> getAbilities(args, pos) },
        Pair("getProficiency") { args, pos -> getProficiency(args, pos) },
        Pair("getArmor") { args, pos -> getArmor(args, pos) },
        Pair("addItem") { args, pos -> addItem(args, pos) },
        Pair("addAction") { args, pos -> addAction(args, pos) },
        Pair("addSpell") { args, pos -> addSpell(args, pos) },
        Pair("addSpellUsing") { args, pos -> addSpellUsing(args, pos) },
        Pair("setFullCaster") { args, pos -> setFullCaster(args, pos) },
        Pair("setHalfCaster") { args, pos -> setHalfCaster(args, pos) },
        Pair("setThirdCaster") { args, pos -> setThirdCaster(args, pos) },
        Pair("setSpecialCaster") { args, pos -> setSpecialCaster(args, pos) },
        Pair("setAC") { args, pos -> setAC(args, pos) },
        Pair("modAC") { args, pos -> modAC(args, pos) },
        Pair("addDCAction") { args, pos -> addDCAction(args, pos) },
        Pair("getSkills") { args, pos -> getSkills(args, pos) },
        Pair("updSkillMod") { args, pos -> updSkillMod(args, pos) },
        Pair("isProficientSkill") { args, pos -> isProficientSkill(args, pos) },
        Pair("recoverSpellSlots") { args, pos -> recoverSpellSlots(args, pos) },
        Pair("recoverSpellSlotsFor") { args, pos -> recoverSpellSlotsFor(args, pos) },
        Pair("registerCharges") { args, pos -> registerCharges(args, pos) },
        Pair("recoverCharges") { args, pos -> recoverCharges(args, pos) },
    )
    private val choiceFunctions = mutableMapOf<String, ChoiceScope.(List<Value>, PosInfo) -> Value>(
        Pair("chooseDataByKind") { args, pos -> chooseDataByKind(args, pos) },
        Pair("chooseNByKind") { args, pos -> chooseNByKind(args, pos) },
        Pair("chooseFrom") { args, pos -> chooseFrom(args, pos) },
        Pair("chooseNFrom") { args, pos -> chooseNFrom(args, pos) },
        Pair("chooseNCantrips") { args, pos -> chooseNCantrips(args, pos) },
        Pair("chooseNSpellsUpTo") { args, pos -> chooseNSpellsUpTo(args, pos) },
        Pair("chooseItem") { args, pos -> chooseItem(args, pos) },
        Pair("chooseNItems") { args, pos -> chooseNItems(args, pos) },
    )
    private val functions = mutableMapOf<String, FunDecl>()
    private val types = mutableMapOf<String, TopLevelDecl>()
    private val globals = mutableMapOf<String, Variable>()

    private var currentCharScope: CharacterScope? = null
    private var currentChoiceScope: ChoiceScope? = null

    val isInScope
        get() = currentChoiceScope != null || currentCharScope != null

    fun clear() {
        functions.clear()
        types.clear()
        globals.clear()
    }

    fun isLibFunc(name: String): Boolean =
        functions.containsKey(name) || contextFunctions.containsKey(name) || choiceFunctions.containsKey(name)
    fun invoke(name: String, args: List<Value>, callSite: PosInfo): Value? {
        val f = functions[name]
        if(f == null) {
            val cf = contextFunctions[name]
            if(cf == null) {
                val csF = choiceFunctions[name]
                if(csF == null) {
                    CMLOut.addError(CMLException.invokeNonFun(name, callSite).localizedMessage)
                    return null
                }
                if(currentChoiceScope == null) {
                    CMLOut.addError(CMLException("Cannot call function `$name' outside of a choice-scope. Function called at $callSite").localizedMessage)
                    return null
                }
                return ExecutionStack.call(callSite) {
                    csF(currentChoiceScope!!, args, callSite)
                }
            }
            if(currentCharScope == null) {
                CMLOut.addError(CMLException("Cannot call function `$name' outside of a character-scope. Function called at $callSite").localizedMessage)
                return null
            }
            return ExecutionStack.call(callSite) {
                cf(currentCharScope!!, args, callSite)
            }
        }
        return f.call(args, callSite)
    }
    fun addFunction(name: String, callback: FunDecl) {
        if(isLibFunc(name)) throw LibraryException.libFunAlreadyExists(name)
        functions[name] = callback
    }
    fun addGlobal(glob: GlobalDecl) {
        if(globals.containsKey(glob.name)) throw CMLException.redeclareGlob(glob.name, globals[glob.name]!!.value.pos, glob.pos)
        globals[glob.name] = glob.toVar(ExecEnvironment(mapOf()))
    }

    fun isLibType(name: String): Boolean = types.containsKey(name)
    fun construct(name: String, pos: PosInfo): InstanceVal? = types[name]?.let { InstanceVal(it.construct(), pos) }
    fun addType(name: String, type: TopLevelDecl) {
        if(types.containsKey(name)) throw LibraryException.libTypeAlreadyExists(name, types[name]!!.pos, type.pos)
        types[name] = type
    }

    fun types() = types
    fun typesByKind(kind: String): List<TopLevelDecl> = types.filter { it.value.kind == kind }.map { it.value }
    fun functions() = functions.keys.union(contextFunctions.keys)

    fun getGlobal(name: String): Variable? = globals[name]

    fun readyAll() {
        types.forEach { it.value.ready() }
    }

    fun <T> withCharacter(c: Character, action: () -> T): Either<CMLException, T> {
        currentCharScope = CharacterScope(c)
        return try {
            val res = action().right()
            currentCharScope = null
            res
        } catch(ex: CMLException) {
            CMLOut.addError(ex.localizedMessage)
            ex.left()
        }
    }

    fun <T> flatWithCharacter(c: Character, action: () -> Either<CMLException, T>): Either<CMLException, T> {
        return withCharacter(c, action).flatMap { res -> res }
    }

    fun <T> withChoices(
        c: Character,
        selector: (Choices) -> MutableMap<Value, Value>?,
        render: (count: Int, options: List<Value>, onSet: (Value) -> Unit) -> Unit,
        action: () -> T
    ) {
        currentChoiceScope = ChoiceScope()
        thread {
            currentChoiceScope?.runScript(
                script = { withCharacter(c, action).mapLeft { CMLOut.addError(it.localizedMessage) } },
                onRequireRender = { options, count, lock ->
                    render(count, options) { lock.update(it) }
                },
                onChoiceMade = { choice, option ->
                    val map = selector(c.choices.value)
                    if (map != null) {
                        map[StringVal(choice, Character.posInit)] = option
                    } else {
                        CMLOut.addError("Couldn't store choice `$choice' = `${option.repr()}' because selected choice cache is null.")
                    }
                }
            ) ?: throw CMLException("Choice-scope was reset before call could happen")
            currentChoiceScope = null
        }
    }

    fun phonyType() = TopLevelDecl(
        kind = "~phony",
        name = "~phony",
        functions = mapOf(),
        fieldsPre = mapOf(),
        declPos = PosInfo("<~phony~>", 0, 0)
    )

    fun phonyInstance() = InstanceVal(phonyType(), phonyType().pos)
}
