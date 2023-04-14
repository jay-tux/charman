package data

import org.jetbrains.exposed.sql.transactions.transaction

fun rmCreatureType(t: CreatureType) = transaction {
    t.delete()
    CreatureType.all().map { it -> it }
}

fun rmDataSource(ds: DataSource) = transaction {
    ds.races.forEach { it.delete() }
    ds.classes.forEach { it.delete() }
    ds.items.forEach { it.delete() }
    ds.backgrounds.forEach { it.delete() }
    ds.delete()
}