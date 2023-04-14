package data

import org.jetbrains.exposed.sql.transactions.transaction

fun mkDataSource(name: String): DataSource = transaction {
    DataSource.new { this.name = name }
}

fun mkCreatureType(name: String): CreatureType = transaction {
    CreatureType.new { this.name = name }
}

fun mkRace(name: String, size: CreatureSize, type: CreatureType, speed: Int, src: DataSource, traits: List<Trait>, languages: List<Language?>, chooseLanguages: Int, asi: List<Pair<Ability?, Int>>) {
    val r = transaction {
        Race.new {
            this.name = name
            this.size = size
            this.type = type
            this.baseWalkingSpeed = speed
            this.src = src
            this.chooseLanguages = chooseLanguages
        }
    }

    transaction {
        asi.forEach { (ab, increase) ->
            AbilityScoreIncrease.new {
                this.ability = ab
                this.race = r
                this.increase = increase
            }
        }

        traits.forEach {
            RaceTrait.new {
                this.race = r
                this.trait = it
            }
        }

        languages.forEach {
            RaceLanguage.new {
                this.language = it
                this.race = r
            }
        }
    }
}