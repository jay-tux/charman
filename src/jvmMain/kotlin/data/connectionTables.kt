package data

import org.jetbrains.exposed.dao.id.IntIdTable

enum class Proficiency { NONE, SINGLE, DOUBLE }

object CharacterAbilityScores : IntIdTable() {
    val character = reference("character", Characters)
    val ability = reference("ability", Abilities)
    val score = integer("score")

    init {
        uniqueIndex(character, ability)
    }
}

object CharacterSkillProficiencies : IntIdTable() {
    val character = reference("character", Characters)
    val skill = reference("skill", Skills)
    val proficiency = enumeration<Proficiency>("proficiency")

    init {
        uniqueIndex(character, skill)
    }
}

object TaggedItems : IntIdTable() {
    val item = reference("item", Items)
    val tag = reference("tag", ItemTags)

    init {
        uniqueIndex(item, tag)
    }
}

object LootedItems : IntIdTable() {
    val item = reference("item", Items)
    val character = reference("character", Characters)
    val count = integer("count")

    init {
        uniqueIndex(item, character)
    }
}

object RaceTraits : IntIdTable() {
    val race = reference("race", Races)
    val trait = reference("trait", Traits)

    init {
        uniqueIndex(race, trait)
    }
}

object RaceLanguages : IntIdTable() {
    val race = reference("race", Races)
    val language = reference("language", Languages).nullable()

    init {
        uniqueIndex(race, language)
    }
}

object ClassProficiencies : IntIdTable() {
    val classN = reference("class", Classes)
    val itemTag = reference("kind", ItemTags)

    init {
        uniqueIndex(classN, itemTag)
    }
}

object BackgroundSkillProficiencies : IntIdTable() {
    val background = reference("background", Backgrounds)
    val skill = reference("skill", Skills)
}

object BackgroundToolProficiencies : IntIdTable() {
    val background = reference("background", Backgrounds)
    val tool = reference("tool", ItemTags)
}

object ClassSaveProficiencies : IntIdTable() {
    val classN = reference("class", Classes)
    val savingThrow = reference("savingThrow", Abilities)

    init {
        uniqueIndex(classN, savingThrow)
    }
}

object ClassSkillOptions : IntIdTable() {
    val classN = reference("class", Classes)
    val skill = reference("skill", Skills)

    init {
        uniqueIndex(classN, skill)
    }
}

object CharacterClassChoices : IntIdTable() {
    val character = reference("character", Characters)
    val choice = reference("choice", ClassChoiceOptions)
}

object ClassTraits : IntIdTable() {
    val classN = reference("class", Classes)
    val trait = reference("trait", Traits)
    val level = integer("level")
    // e.g. Barbarian's rage count
    val makesObsolete = reference("makes_obsolete", ClassTraits).nullable()
    // e.g. subclass choice
    val prerequisite = reference("prerequisite", ClassChoiceOptions).nullable()
}

object CharacterClasses : IntIdTable() {
    val character = reference("character", Characters)
    val classN = reference("class", Classes)
    val level = integer("level")

    init {
        uniqueIndex(character, classN, level)
    }
}