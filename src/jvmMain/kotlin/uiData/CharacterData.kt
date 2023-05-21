package uiData

import CMLOut
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import cml.*
import data.getDice
import data.getString
import data.loadFromInstance
import data.requireInt
import updateGet
import kotlin.math.floor
import kotlin.math.roundToInt

class Character(
    var name: String,
    var race: Pair<String, InstanceVal>,
    var background: Pair<String, InstanceVal>,
    val classes: MutableMap<String, Pair<InstanceVal, Int>>,
    val abilities: MutableMap<String, Triple<String, InstanceVal, Int>>
) {
    val classTraits = mutableMapOf<String, Triple<String, String, InstanceVal>>()
    val racialTraits = mutableMapOf<String, Pair<String, InstanceVal>>()
    val skillProficiencies = mutableListOf<InstanceVal>()
    val saveProficiencies = mutableListOf<InstanceVal>()
    val languages = mutableMapOf<String, InstanceVal>()
    val inspiration = mutableStateOf(false)
    val hp = mutableStateOf(0)
    val damage = mutableStateOf(0)
    val tempHp = mutableStateOf(0)
    val deathSaves = mutableStateOf(Pair(0, 0))
    val speed = mutableStateOf(0)
    val ac = mutableStateOf(0)
    val initMod = mutableStateOf(0)
    val hitDice = mutableStateOf(mapOf<Int, Int>()) // (dice type, amount)

    private fun callOnAll(fn: String, args: List<Value> = listOf(), withResult: (Value) -> Unit) {
        Library.withCharacter(this) {
            race.second.type.functions[fn]?.call(args, posRender)?.let(withResult)
            classes.forEach { (_, v) ->
                v.first.type.functions[fn]?.call(args, posRender)?.let(withResult)
            }
            background.second.type.functions[fn]?.call(args, posRender)?.let(withResult)
        }.mapLeft { CMLOut.addError(it.localizedMessage) }
    }

    fun onUpdate() {
        var acM = 10
        callOnAll("modAC") {
            it.requireInt(posRender).map { v -> acM += v.value }
        }

        var initM = 0
        callOnAll("modInitiative") {
            it.requireInt(posRender).map { v -> initM += v.value }
        }

        Library.withCharacter(this) {
            val dex = Library.types()["Dexterity"]?.let { InstanceVal(it, posRender) }
            if (dex == null) CMLOut.addWarning("Ability Dexterity does not exist.")
            else {
                val mod = abilityMod(dex)
                acM += mod
                initM += mod
            }
        }.mapLeft { CMLOut.addError(it.localizedMessage) }

        ac.value = acM
        initMod.value = initM

        val hitDiceM = mutableMapOf<Int, Int>()
        classes.forEach { k, (inst, level) ->
            inst.getDice("hitDie", posRender).fold({ CMLOut.addWarning("Class $k does not have the required `hitDie' field.") }) {
                hitDiceM[it.kind] = (hitDiceM[it.kind] ?: 0) + level
            }
        }
        hitDice.value = hitDiceM
    }

    fun proficiency(): Int = when(classes.values.sumOf { it.second }) {
        in 1..4 -> 2
        in 5..8 -> 3
        in 9..12 -> 4
        in 13..16 -> 5
        else -> 6
    }

    fun hasSaveProf(prof: InstanceVal): Boolean = saveProficiencies.contains(prof)

    fun hasSkillProf(prof: InstanceVal): Boolean = skillProficiencies.contains(prof)

    fun abilityMod(ab: InstanceVal): Int {
        return ab.getString("abbrev", posRender).flatMap {
            abilities[it]?.third?.right() ?: return@flatMap CMLException.invalidAbility(it, posRender).left()
        }.fold({ l -> CMLOut.addError(l.localizedMessage); 0 }, { r -> floor((r - 10) / 2.0f).roundToInt() })
    }

    fun saveMod(ab: InstanceVal): Int {
        var base = abilityMod(ab)
        if(hasSaveProf(ab)) base += proficiency()
        return base
    }

    fun skillMod(ab: InstanceVal): Int {
        return Library.withCharacter(this) {
            when (val res = ab.type.functions["getMod"]?.call(
                listOf(BoolVal(skillProficiencies.contains(ab), posRender)),
                posRender
            )) {
                is IntVal -> res.value
                null -> throw CMLException.invalidMemberFunction(ab.type.name, "getMod", posRender)
                else -> throw CMLException.typeError("Int", res, posRender)
            }
        }.fold({ CMLOut.addError(it.localizedMessage); 0 }, { it })
    }

    fun passiveSkill(ab: InstanceVal) = 10 + skillMod(ab)

    companion object {
        val posRender = PosInfo("<runtime:character:ui>", 0, 0)
        val posInit = PosInfo("<runtime:character:init>", 0, 0)
        val posRest = PosInfo("<runtime:character:restore>", 0, 0)
    }
}

object CharacterData {
    private val _characters = mutableStateOf(listOf<Either<Pair<String, CMLException>, InstanceVal>>())
    val characters: State<List<Either<Pair<String, CMLException>, InstanceVal>>> = _characters
    private val _loadedCharacters = mutableStateOf(listOf<Either<CMLException, Character>>())
    val loadedCharacters: State<List<Either<CMLException, Character>>> = _loadedCharacters

    val runtimeLoadPos = PosInfo("<runtime:load>", 0, 0)
    val runtimeRenderPos = PosInfo("<runtime:render>", 0, 0)

    fun clear() {
        _characters.value = listOf()
    }

    fun setError(index: Int, message: String) {
        setError(index, CMLException(message))
    }

    fun setError(index: Int, message: CMLException) {
        _characters.value = _characters.value.updateGet(
            index,
            Pair(_characters.value[index].fold({ it.first }, { it.type.name }), message).left()
        )
    }

    fun loadFromLibrary() {
        _characters.value = _characters.value + Library.typesByKind("Character").map { InstanceVal(it, runtimeLoadPos).right() }
        _loadedCharacters.value = characters.value.map { pre -> pre.mapLeft { it.second }.flatMap { Character.loadFromInstance(it) } }
    }

    fun <T> handleCMLException(ex: CMLException, character: Either<CMLException, InstanceVal>, index: Int, res: T): T {
        if(character.isRight()) {
            ex.message?.let {
                CMLOut.addError(it)
            }
            setError(index, ex)
        }
        return res
    }
}