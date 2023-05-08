package data

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.transactions.transaction

fun <Out: IntEntity> maybeInsert(comp1: IntEntityClass<Out>, sel: SqlExpressionBuilder.() -> Op<Boolean>, ctor: () -> Out): Out = transaction {
  val res = comp1.find(sel)
  when(res.empty()) {
      true -> ctor()
      false -> res.first()
  }
}

fun mkSkill(name: String, ability: Ability): Skill = maybeInsert<Skill>(Skill.Companion, { Skills.name eq name }) {
    Skill.new { this.name = name; this.ability = ability }
}

fun mkAbility(name: String): Ability = transaction {
    val res = Ability.find { Abilities.name eq name }
    when(res.empty()) {
        true -> Ability.new { this.name = name }
        false -> res.first()
    }
}

fun maybeInit(db: Database) {
    transaction {
        SchemaUtils.createMissingTablesAndColumns(
            Traits, Abilities, Skills, Notes, ItemTags, Languages, CreatureTypes, Items, Backgrounds,
            AbilityScoreIncreases, Races, ClassChoices, ClassChoiceOptions, Classes, Characters,
            CharacterAbilityScores, CharacterSkillProficiencies, TaggedItems, LootedItems, RaceTraits,
            RaceLanguages, ClassProficiencies, ClassSaveProficiencies, ClassSkillOptions,
            CharacterClassChoices, ClassTraits, CharacterClasses, BackgroundToolProficiencies,
            BackgroundSkillProficiencies
        )
    }

    // add abilities
    val str = mkAbility("Strength")
    val dex = mkAbility("Dexterity")
    val con = mkAbility("Constitution")
    val int = mkAbility("Intelligence")
    val wis = mkAbility("Wisdom")
    val cha = mkAbility("Charisma")

    // add skills
    val athletics = mkSkill("Athletics", str)
    val acrobatics = mkSkill("Acrobatics", dex)
    val sleightOfHand = mkSkill("Sleight of Hand", dex)
    val stealth = mkSkill("Stealth", dex)
    val arcana = mkSkill("Arcana", int)
    val history = mkSkill("History", int)
    val investigation = mkSkill("Investigation", int)
    val nature = mkSkill("Nature", int)
    val religion = mkSkill("Religion", int)
    val animalHanding = mkSkill("Animal Handling", wis)
    val insight = mkSkill("Insight", wis)
    val medicine = mkSkill("Medicine", wis)
    val perception = mkSkill("Perception", wis)
    val deception = mkSkill("Deception", cha)
    val intimidation = mkSkill("Intimidation", cha)
    val performance = mkSkill("Performance", cha)
    val persuasion = mkSkill("Persuasion", cha)
}