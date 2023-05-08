package data

import org.jetbrains.exposed.sql.transactions.transaction

fun mkDataSource(name: String): DataSource = transaction {
    DataSource.new { this.name = name }
}

fun mkRace(
    name: String, size: CreatureSize, type: CreatureType, speed: Int, src: DataSource,
    traits: List<Trait>, languages: List<Language>, chooseLanguages: Int,
    asi: List<Pair<Ability?, Int>>
 ): Race {
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

    return r
}

fun mkClass(
    name: String, hitDice: DiceType, saves: List<Ability>, itemP: List<ItemTag>, src: DataSource,
    skillCount: Int, skillOptions: List<Skill>
): DnDClass {
    val cls = transaction {
        DnDClass.new {
            this.name = name
            this.hitDiceType = hitDice
            this.src = src
            this.chooseSkillCount = skillCount
        }
    }

    transaction {
        saves.forEach {
            ClassSaveProficiency.new {
                this.classN = cls
                this.savingThrow = it
            }
        }

        itemP.forEach {
            ClassProficiency.new {
                this.classN = cls
                this.itemTag = it
            }
        }

        skillOptions.forEach {
            ClassSkillOption.new {
                this.classN = cls
                this.skill = it
            }
        }
    }

    return transaction { cls.refresh(); cls }
}