package data

import capitalizeFirst
import org.jetbrains.exposed.dao.id.IntIdTable

enum class TraitSource {
    RACE, CLASS, BACKGROUND, ITEM, OTHER;

   override fun toString(): String = name.capitalizeFirst()
}
enum class TraitKind {
    PASSIVE, ACTION, BONUS_ACTION, REACTION, ATTACK;

    override fun toString(): String = name.split("_").joinToString("") { it.capitalizeFirst() }
}
enum class NoteKind {
    NOTE, PERSONALITY_TRAIT, BOND, FLAW, IDEAL;

    override fun toString(): String = name.split('_').joinToString(" ") { it.capitalizeFirst() }
}
enum class CreatureSize {
    TINY, SMALL, MEDIUM, LARGE, HUGE, GARGANTUAN;

    override fun toString(): String = name.capitalizeFirst()
}
enum class DiceType(val average: Int) {
    D4(3), D6(4), D8(5), D10(6), D12(7), D20(11), D100(51);

    override fun toString(): String = name.capitalizeFirst()
    fun n(n: Int): String = "${n}d${this.toString().substring(1)}"
}

object DataSources : IntIdTable() {
    val name = varchar("name", 50).uniqueIndex()
}

object Traits : IntIdTable() {
    val name = varchar("name", 64).index()
    val tSource = enumeration<TraitSource>("source")
    val kind = enumeration<TraitKind>("kind")
    val description = text("description")
}

object Abilities : IntIdTable() {
    val name = varchar("name", 64).uniqueIndex()
}

object Skills : IntIdTable() {
    val name = varchar("name", 64).uniqueIndex()
    val ability = reference("ability", Abilities)
}

object Notes : IntIdTable() {
    val character = reference("character", Characters)
    val content = text("content")
    val kind = enumeration<NoteKind>("kind")
}

object ItemTags : IntIdTable() {
    val name = varchar("name", 64).uniqueIndex()
}

object Languages : IntIdTable() {
    val name = varchar("name", 64).uniqueIndex()
}

object CreatureTypes : IntIdTable() {
    val name = varchar("name", 64).uniqueIndex()
}

object Items : IntIdTable() {
    val name = varchar("name", 64).index()
    val src = reference("source", DataSources)
}

object AbilityScoreIncreases : IntIdTable() {
    // if null -> any
    val ability = reference("ability", Abilities).nullable()
    val race = reference("race", Races)
    val increase = integer("increase")
}

object Races : IntIdTable() {
    val name = varchar("name", 64)
    val src = reference("source", DataSources)
    val size = enumeration<CreatureSize>("size")
    val type = reference("type", CreatureTypes)
    val baseWalkingSpeed = integer("base_walking_speed")
    val chooseLanguages = integer("choose_languages")

    init {
        uniqueIndex(name, src)
    }
}

object SpellcastingDetails : IntIdTable() {
    val dndClass = reference("class", Classes)
    val prerequisite = reference("prerequisite", ClassChoiceOptions)
    val cantripCount = integer("cantrip_count")
    val lvl1Slots = integer("lvl1")
    val lvl2Slots = integer("lvl2")
    val lvl3Slots = integer("lvl3")
    val lvl4Slots = integer("lvl4")
    val lvl5Slots = integer("lvl5")
    val lvl6Slots = integer("lvl6")
    val lvl7Slots = integer("lvl7")
    val lvl8Slots = integer("lvl8")
    val lvl9Slots = integer("lvl9")
    val spellsKnown = integer("spells")
    val nextLevel = integer("next_level_change").nullable()
    val onReachNext = reference("next_spellcasting", SpellcastingDetails).nullable()
}

object ClassChoices : IntIdTable() {
    val name = varchar("name", 64).uniqueIndex()
    val classN = reference("class", Classes)
    val level = integer("level")
}

object ClassChoiceOptions : IntIdTable() {
    val name = varchar("name", 64).uniqueIndex()
    val classChoice = reference("class_choice", ClassChoices)
    val src = reference("source", DataSources)
}

object Classes : IntIdTable() {
    val name = varchar("name", 64)
    val src = reference("source", DataSources)
    val hitDiceType = enumeration<DiceType>("hit_dice_type")
    val chooseSkillCount = integer("choose_skill_count")

    init {
        uniqueIndex(name, src)
    }
}

object Backgrounds : IntIdTable() {
    val name = varchar("name", 64)
    val src = reference("source", DataSources)
    val feature = reference("feature", Traits)
    val chooseSkillCount = integer("choose_skill_count")

    init {
        uniqueIndex(name, src)
    }
}

object Characters : IntIdTable() {
    val name = varchar("name", 64).index()
    val copperPieces = integer("copper_pieces")
    val race = reference("race", Races)
}