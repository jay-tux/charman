package data

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class CharacterAbilityScore(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CharacterAbilityScore>(CharacterAbilityScores)

    var character by Character referencedOn CharacterAbilityScores.character
    var ability by Ability referencedOn CharacterAbilityScores.ability
    var score by CharacterAbilityScores.score
}

class CharacterSkillProficiency(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CharacterSkillProficiency>(CharacterSkillProficiencies)

    var character by Character referencedOn CharacterSkillProficiencies.character
    var skill by Skill referencedOn CharacterSkillProficiencies.skill
    var proficiency by CharacterSkillProficiencies.proficiency
}

class TaggedItem(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TaggedItem>(TaggedItems)

    var item by Item referencedOn TaggedItems.item
    var tag by ItemTag referencedOn TaggedItems.tag
}

class LootedItem(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<LootedItem>(LootedItems)

    var item by Item referencedOn LootedItems.item
    var character by Character referencedOn LootedItems.character
    var count by LootedItems.count
}

class RaceTrait(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RaceTrait>(RaceTraits)

    var race by Race referencedOn RaceTraits.race
    var trait by Trait referencedOn RaceTraits.trait
}

class RaceLanguage(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RaceLanguage>(RaceLanguages)

    var race by Race referencedOn RaceLanguages.race
    var language by Language optionalReferencedOn RaceLanguages.language
}

class ClassProficiency(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ClassProficiency>(ClassProficiencies)

    var classN by DnDClass referencedOn ClassProficiencies.classN
    var itemTag by ItemTag referencedOn ClassProficiencies.itemTag
}

class ClassSaveProficiency(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ClassSaveProficiency>(ClassSaveProficiencies)

    var classN by DnDClass referencedOn ClassSaveProficiencies.classN
    var savingThrow by Ability referencedOn ClassSaveProficiencies.savingThrow
}

class ClassSkillOption(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ClassSkillOption>(ClassSkillOptions)

    var classN by DnDClass referencedOn ClassSkillOptions.classN
    var skill by Skill referencedOn ClassSkillOptions.skill
}

class CharacterClassChoice(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CharacterClassChoice>(CharacterClassChoices)

    var character by Character referencedOn CharacterClassChoices.character
    var choice by ClassChoiceOption referencedOn CharacterClassChoices.choice
}

class ClassTrait(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ClassTrait>(ClassTraits)

    var classN by DnDClass referencedOn ClassTraits.classN
    var trait by Trait referencedOn ClassTraits.trait
    var level by ClassTraits.level
    var makesObsolete by ClassTrait optionalReferencedOn ClassTraits.makesObsolete
    var prerequisite by ClassChoiceOption optionalReferencedOn ClassTraits.prerequisite
}

class CharacterClass(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CharacterClass>(CharacterClasses)

    var character by Character referencedOn CharacterClasses.character
    var classN by DnDClass referencedOn CharacterClasses.classN
    var level by CharacterClasses.level
}