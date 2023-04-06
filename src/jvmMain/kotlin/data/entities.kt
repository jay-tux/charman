package data

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class DataSource(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<DataSource>(DataSources)

    var name by DataSources.name
}

class Trait(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Trait>(Traits)

    var name by Traits.name
    var tSource by Traits.tSource
    var kind by Traits.kind
    var description by Traits.description
}

class Ability(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Ability>(Abilities)

    var name by Abilities.name

    val skills by Skill referrersOn Skills.ability
    val increasedBy by Race via AbilityScoreIncreases
}

class Skill(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Skill>(Skills)

    var name by Skills.name
    var ability by Skills.ability
}

class Note(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Note>(Notes)

    var character by Character referencedOn Notes.character
    var content by Notes.content
    var kind by Notes.kind
}

class ItemTag(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ItemTag>(ItemTags)

    var name by ItemTags.name
    val matchingItems by Item via TaggedItems
}

class Language(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Language>(Languages)

    var name by Languages.name
}

class CreatureType(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CreatureType>(CreatureTypes)

    var name by CreatureTypes.name

    val matchingRaces by Race referrersOn Races.type
}

class Item(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Item>(Items)

    var name by Items.name
    var src by Items.src
    val tagsFor by ItemTag via TaggedItems
}

class AbilityScoreIncrease(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AbilityScoreIncrease>(AbilityScoreIncreases)

    var ability by Ability optionalReferencedOn AbilityScoreIncreases.ability
    var race by Race referencedOn AbilityScoreIncreases.race
    var increase by AbilityScoreIncreases.increase
}

class Race(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Race>(Races)
    var name by Races.name

    var size by Races.size
    var type by Races.type
    var baseWalkingSpeed by Races.baseWalkingSpeed
    var src by Races.src

    val abilityScoreIncreases by AbilityScoreIncrease referrersOn AbilityScoreIncreases.race
    val charactersWithRace by Character referrersOn Characters.race
    val traits by Trait via RaceTraits
    val languages by Language via RaceLanguages
}

class ClassChoice(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ClassChoice>(ClassChoices)

    var name by ClassChoices.name
    var classN by DnDClass referencedOn ClassChoices.classN
    var level by ClassChoices.level

    val options by ClassChoiceOption referrersOn ClassChoiceOptions.classChoice
}

class ClassChoiceOption(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ClassChoiceOption>(ClassChoiceOptions)

    var name by ClassChoiceOptions.name
    var classChoice by ClassChoice referencedOn ClassChoiceOptions.classChoice
    var src by ClassChoiceOptions.src
}

class DnDClass(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<DnDClass>(Classes)

    var name by Classes.name
    var hitDiceType by Classes.hitDiceType
    var chooseSkillCount by Classes.chooseSkillCount
    var src by Classes.src

    val choices by ClassChoice referrersOn ClassChoices.classN
    val itemProficiencies by ItemTag via ClassProficiencies
    val savingThrowProficiencies by Ability via ClassSaveProficiencies
    val skillOptions by Skill via ClassSkillOptions
    val traits by ClassTrait referrersOn ClassTraits.classN
    val charactersWithClass by CharacterClass referrersOn CharacterClasses.classN
}

class Background(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Background>(Backgrounds)

    var name by Backgrounds.name
    var src by Backgrounds.src
    var feature by Backgrounds.feature
    var chooseSkillCount by Backgrounds.chooseSkillCount

    val skillOptions by Skill via BackgroundSkillProficiencies
    val itemProficiencies by ItemTag via BackgroundToolProficiencies
}

class Character(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Character>(Characters)

    var name by Characters.name
    var copperPieces by Characters.copperPieces
    var race by Race referencedOn Characters.race

    val notes by Note referrersOn Notes.character
    val abilityScores by CharacterAbilityScore referrersOn CharacterAbilityScores.character
    val skillProficiencies by CharacterSkillProficiency referrersOn CharacterSkillProficiencies.character
    val equipment by LootedItem referrersOn LootedItems.character
    val classes by CharacterClass referrersOn CharacterClasses.character
}
