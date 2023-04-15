package data

import org.jetbrains.exposed.sql.transactions.transaction

fun rmDataSource(ds: DataSource) = transaction {
    ds.races.forEach { it.delete() }
    ds.classes.forEach { it.delete() }
    ds.items.forEach { it.delete() }
    ds.backgrounds.forEach { it.delete() }
    ds.delete()
}

fun rmRace(r: Race) = transaction {
    r.abilityScoreIncreases.forEach { it.delete() }
    RaceTrait.find { RaceTraits.race eq r.id }.forEach { it.delete() }
    RaceLanguage.find { RaceLanguages.race eq r.id }.forEach { it.delete() }
    r.charactersWithRace.forEach { it.delete() }
    r.delete()
}