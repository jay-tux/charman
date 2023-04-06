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