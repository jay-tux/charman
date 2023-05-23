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

data class ClassDesc(val cls: InstanceVal, val level: Int, val isPrimary: Boolean)
data class AbilityDesc(val name: String, val instance: InstanceVal, val score: Int)
data class ItemDesc(val name: String, val weight: Float, val actions: List<InstanceVal>, val traits: List<InstanceVal>)
data class SpellDesc(
    val name: String, val school: String, val level: Int, val castingTime: String, val range: String,
    val components: List<String>, val duration: String, val actions: List<InstanceVal>, val desc: String,
    val source: String
)

class Character(
    var name: String,
    var race: Pair<String, InstanceVal>,
    var background: Pair<String, InstanceVal>,
    val classes: MutableMap<String, ClassDesc>,
    val abilities: MutableMap<String, AbilityDesc>
) {
    val classTraits = mutableMapOf<String, Triple<String, String, InstanceVal>>()
    val racialTraits = mutableMapOf<String, Pair<String, InstanceVal>>()
    val skillProficiencies = mutableListOf<InstanceVal>()
    val saveProficiencies = mutableListOf<InstanceVal>()

    val saveMods = mutableStateOf(mapOf<String, Pair<Int, Boolean>>())
    val skillMods = mutableStateOf(mapOf<String, Triple<Int, Boolean, String>>())

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
    val inventory = mutableStateOf(listOf<ItemDesc>())
    val spells = mutableStateOf(listOf<SpellDesc>())
    val actions = mutableStateOf(listOf<Action>())

    private fun callOnAll(fn: String, args: List<Value> = listOf(), withResult: (Value) -> Unit) {
        Library.withCharacter(this) {
            race.second.type.functions[fn]?.call(args, posRender)?.let(withResult)
            classes.forEach { (_, v) ->
                v.cls.type.functions[fn]?.call(args, posRender)?.let(withResult)
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

        val saveModsM = mutableMapOf<String, Pair<Int, Boolean>>()
        abilities.forEach { (ab, v) ->
            val mod = v.score.toMod()
            if(saveProficiencies.contains(v.instance)) saveModsM[ab] = Pair(mod + proficiency(), true)
            else saveModsM[ab] = Pair(mod, false)
        }
        saveMods.value = saveModsM

        val skillModsM = mutableMapOf<String, Triple<Int, Boolean, String>>()
        Library.typesByKind("Skill").forEach { decl ->
            val name = decl.fields.getVar("name")
            if(name == null) CMLOut.addWarning(CMLException.invalidField(decl.name, "name", posRender).localizedMessage)
            else if(name.value !is StringVal) CMLOut.addWarning(CMLException.typeError("String", name.value, posRender).localizedMessage)
            else {
                val ability = decl.fields.getVar("reliesOn")
                var mod = 0
                var ab = "Invalid"
                if(ability == null) CMLOut.addWarning(CMLException.invalidField(decl.name, "reliesOn", posRender).localizedMessage)
                else {
                    val tmp = abilities.entries.firstOrNull { it.value.instance == ability.value }
                    if(tmp == null) CMLOut.addWarning(CMLException.invalidAbility(ability.name, posRender).localizedMessage)
                    else {
                        mod = tmp.value.score.toMod()
                        ab = tmp.key
                    }
                }

                if(skillProficiencies.contains(InstanceVal(decl, posRender))) skillModsM[(name.value as StringVal).value] = Triple(mod + proficiency(), true, ab)
                else skillModsM[(name.value as StringVal).value] = Triple(mod, false, ab)
            }
        }
        skillMods.value = skillModsM
    }

    fun proficiency(): Int = when(classes.values.sumOf { it.level }) {
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
            abilities[it]?.score?.right() ?: return@flatMap CMLException.invalidAbility(it, posRender).left()
        }.fold({ l -> CMLOut.addError(l.localizedMessage); 0 }, { r -> floor((r - 10) / 2.0f).roundToInt() })
    }

    fun saveDc(ab: InstanceVal): Int {
        return 8 + proficiency() + abilityMod(ab)
    }

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

fun Int.toMod() = (Math.floorDiv(this - 10, 2))