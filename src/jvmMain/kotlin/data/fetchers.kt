package data

import org.jetbrains.exposed.sql.transactions.transaction

fun listCharacters(): List<Character> = transaction {
    Character.all().toList()
}

fun listDataSources(): List<DataSource> = transaction {
    DataSource.all().toList()
}

fun listRaces(): List<Race> = transaction {
    Race.all().toList()
}

fun listClasses(): List<DnDClass> = transaction {
    DnDClass.all().toList()
}

fun listBackgrounds(): List<Background> = transaction {
    Background.all().toList()
}

fun listItems(): List<Item> = transaction {
    Item.all().toList()
}

fun listTypes(): List<CreatureType> = transaction {
    CreatureType.all().toList()
}

fun listAbilities(): List<Ability> = transaction {
    Ability.all().toList()
}

fun traitsFor(r: Race?) : List<Trait> = transaction {
    r?.traits?.toList() ?: listOf()
}

fun listTraits(): List<Trait> = transaction {
    Trait.all().toList()
}

fun listLanguages(): List<Language> = transaction {
    Language.all().toList()
}

fun languagesFor(r: Race?) : List<Language> = transaction {
    r?.languages?.toList() ?: listOf()
}

fun listTags() : List<ItemTag> = transaction {
    ItemTag.all().toList()
}

fun listCreatureTypes() : List<CreatureType> = transaction {
    CreatureType.all().toList()
}

fun listSkills() : List<Skill> = transaction {
    Skill.all().toList()
}