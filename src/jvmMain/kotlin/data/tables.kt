package data

import org.jetbrains.exposed.dao.id.IntIdTable

enum class TraitSource { RACE, CLASS, BACKGROUND, ITEM, OTHER }
enum class TraitKind { PASSIVE, ACTION, BONUS_ACTION, REACTION, ATTACK }
enum class NoteKind { NOTE, PERSONALITY_TRAIT, BOND, FLAW, IDEAL }
enum class CreatureSize { TINY, SMALL, MEDIUM, LARGE, HUGE, GARGANTUAN }
enum class DiceType { D4, D6, D8, D10, D12, D20, D100 }

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

    init {
        uniqueIndex(name, src)
    }
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