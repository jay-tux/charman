package cml

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import data.*
import uiData.Character

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

    private val contextPreFunctions = mutableMapOf<String, (Character) -> ((List<Value>, PosInfo) -> Value)>(
        Pair("abilityIncrease") { c -> abilityIncrease(c) },
        Pair("addMaxHP") { c -> addMaxHP(c) },
        Pair("addRacialTraits") { c -> addRacialTraits(c) },
        Pair("chooseDataByKind") { c -> chooseDataByKind(c) },
        Pair("chooseFrom") { c -> chooseFrom(c) },
        Pair("addLanguages") { c -> addLanguages(c) },
        Pair("addSkillProficiencies") { c -> addSkillProficiencies(c) },
        Pair("addSaveProficiencies") { c -> addSaveProficiencies(c) },
        Pair("addClassTraits") { c -> addClassTraits(c) },
        Pair("getAbilityMod") { c -> getAbilityMod(c) },
        Pair("getProficiency") { c -> getProficiency(c) },
        Pair("getArmor") { c -> getArmor(c) },
        Pair("addAction") { c -> addAction(c) },
        Pair("addSpell") { c -> addSpell(c) },
        Pair("setFullCaster") { c -> setFullCaster(c) },
        Pair("setHalfCaster") { c -> setHalfCaster(c) },
        Pair("setThirdCaster") { c -> setThirdCaster(c) },
        Pair("setSpecialCaster") { c -> setSpecialCaster(c) }
    )
    private val functions = mutableMapOf<String, FunDecl>()
    private val types = mutableMapOf<String, TopLevelDecl>()
    private val globals = mutableMapOf<String, Variable>()
    private val contextFunctions = mutableMapOf<String, (List<Value>, PosInfo) -> Value>()

    fun clear() {
        functions.clear()
        types.clear()
        globals.clear()
    }

    fun isLibFunc(name: String): Boolean =
        functions.containsKey(name) || contextFunctions.containsKey(name)
    fun invoke(name: String, args: List<Value>, callSite: PosInfo): Value? =
        functions[name]?.call(args, callSite) ?: contextFunctions[name]?.let {
            ExecutionStack.push(callSite)
            val res = it(args, callSite)
            ExecutionStack.pop()
            res
        }
    fun addFunction(name: String, callback: FunDecl) {
        if(functions.containsKey(name) || contextPreFunctions.containsKey(name)) throw LibraryException.libFunAlreadyExists(name)
        functions[name] = callback
    }
    fun addGlobal(glob: GlobalDecl) {
        if(globals.containsKey(glob.name)) throw CMLException.redeclareGlob(glob.name, globals[glob.name]!!.value.pos, glob.pos)
        globals[glob.name] = glob.toVar(ExecEnvironment(mapOf()))
    }

    fun addContextualFunction(name: String, callback: (Character) -> ((List<Value>, PosInfo) -> Value)) {
        if(functions.containsKey(name) || contextPreFunctions.containsKey(name)) throw LibraryException.libFunAlreadyExists(name)
        contextPreFunctions[name] = callback
    }

    fun isLibType(name: String): Boolean = types.containsKey(name)
    fun construct(name: String, pos: PosInfo): InstanceVal? = types[name]?.let { InstanceVal(it.construct(), pos) }
    fun addType(name: String, type: TopLevelDecl) {
        if(types.containsKey(name)) throw LibraryException.libTypeAlreadyExists(name)
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
        contextPreFunctions.forEach { (k, callback) ->
            contextFunctions[k] = callback(c)
        }
        return try {
            val res = action().right()
            contextFunctions.clear()
            res
        } catch(ex: CMLException) {
            ex.left()
        }
    }

    fun <T> flatWithCharacter(c: Character, action: () -> Either<CMLException, T>): Either<CMLException, T> {
        return withCharacter(c, action).flatMap { res -> res }
    }
}
