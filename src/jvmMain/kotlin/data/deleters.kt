package data

import org.jetbrains.exposed.sql.transactions.transaction

fun rmDataSource(ds: DataSource) = transaction {
    ds.races.forEach { it.delete() }
    ds.classes.forEach { it.delete() }
    ds.items.forEach { it.delete() }
    ds.backgrounds.forEach { it.delete() }
    ds.delete()
}

// TODO: only for race :eyes:
fun rmRace(r: Race) = transaction {
    RaceLanguage.all().forEach { it.delete() }
    RaceTrait.all().forEach { it.delete() }
    AbilityScoreIncrease.all().forEach { it.delete() }
    r.charactersWithRace.forEach { it.delete() }
    r.delete()
}