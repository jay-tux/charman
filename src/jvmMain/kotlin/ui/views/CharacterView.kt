package ui.views

import CMLOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import arrow.core.flatMap
import cml.CMLException
import cml.InstanceVal
import cml.Library
import data.getName
import data.getString
import data.getVerifyInst
import ui.widgets.*
import uiData.Character
import withSign

@Composable
fun CharacterView(data: Character) = Column(Modifier.padding(8.dp)) {
    Row(Modifier.weight(0.10f)) { sheetTopRow(data) }

    Row(Modifier.weight(0.90f)) {
        Column(Modifier.weight(0.25f)) {
            Row(Modifier.weight(0.75f)) {
                sheetAbilities(data)
                sheetProficiencies(data)
            }
            Column(Modifier.weight(0.25f)) {
                sheetPassivePerception(data)
                sheetLanguages(data)
            }
        }
        Spacer(Modifier.weight(0.02f))

        Column(Modifier.weight(0.28f)) {
            sheetCentralNumbers(data)
            sheetInventory(data)
        }
        Spacer(Modifier.weight(0.02f))

        sheetTraitsAndActions(data)
    }
}

@Composable
fun RowScope.sheetTopRow(data: Character) {
    Text(data.name, Modifier.weight(0.4f), style = MaterialTheme.typography.h3)
    Row(Modifier.weight(0.6f).fillMaxHeight()) {
        LazyScrollColumn(Modifier.weight(0.5f).align(Alignment.CenterVertically)) {
            item {
                Text("Classes", fontWeight = FontWeight.Bold)
            }
            items(data.classes.toList()) { (cl, lvl) ->
                indented {
                    Text("$cl (level ${lvl.second})")
                }
            }
        }

        LazyScrollColumn(Modifier.weight(0.5f).align(Alignment.CenterVertically)) {
            item {
                boldThenNormal("Race:", data.race.first)
            }
            item {
                boldThenNormal("Background:", data.background.first)
            }
        }
    }
}

@Composable
fun RowScope.sheetAbilities(data: Character) {
    LazyScrollColumn(Modifier.weight(0.065f)) {
        items(data.abilities.toList()) { (ab, stat) ->
            AbilityScoreCard(ab, stat.third, data.abilityMod(stat.second))
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
fun RowScope.sheetProficiencies(data: Character) {
    var insp by data.inspiration

    Column(Modifier.weight(0.265f)) {
        InspirationWidget(insp) { insp = it }
        IntStringCard(data.proficiency(), "Proficiency Bonus", true)
        Spacer(Modifier.weight(0.025f))
        LazyScrollColumn(Modifier.weight(0.25f)) {
            items(data.abilities.toList()) { (_, abStat) ->
                ModScoreCard(abStat.first, data.saveMod(abStat.second), data.hasSaveProf(abStat.second))
            }
        }
        Spacer(Modifier.weight(0.025f))
        LazyScrollColumn(Modifier.weight(0.55f)) {
            items(Library.typesByKind("Skill").map { InstanceVal(it, Character.posRender) }) {
                ModScoreCard(
                    it.getName(Character.posRender).flatMap { name ->
                        it.getVerifyInst("reliesOn", "Ability", Character.posRender).flatMap { abI ->
                            abI.getString("abbrev", Character.posRender).map { ab ->
                                "$name ($ab)"
                            }
                        }
                    }.fold({ e -> CMLOut.addError(e.localizedMessage); "Invalid Skill" }, { v -> v }),
                    data.skillMod(it),
                    data.hasSkillProf(it)
                )
            }
        }
    }
}

@Composable
fun RowScope.sheetTraitsAndActions(data: Character) {
    LazyScrollColumn(Modifier.weight(0.43f)) {
        items(data.racialTraits.toList()) { (name, desc) ->
            TraitCard(name, data.race.first, desc.first)
            Spacer(Modifier.height(7.dp))
        }

        items(data.classTraits.toList().sortedBy { it.second.second }) { (name, desc) ->
            TraitCard(name, desc.second, desc.first)
            Spacer(Modifier.height(7.dp))
        }
    }
}

@Composable
fun ColumnScope.sheetPassivePerception(data: Character) {
    val perc = remember { Library.types()["Perception"]?.let { InstanceVal(it, Character.posRender) } }
    Column(Modifier.weight(0.25f)) {
        if(perc == null) {
            CMLOut.addWarning("Skill Perception does not exist.")
            Text("Could not get passive Perception", color = MaterialTheme.colors.error)
        }
        else {
            IntStringCard(data.passiveSkill(perc), "Passive Wisdom (Perception)")
        }
    }
}

@Composable
fun ColumnScope.sheetLanguages(data: Character) {
    LazyScrollColumn(Modifier.weight(0.75f)) {
        item {
            Text("Languages", fontWeight = FontWeight.Bold)
        }
        items(data.languages.toList()) { (l, _) ->
            indented {
                Text(l)
            }
        }
    }
}

@Composable
fun ColumnScope.sheetCentralNumbers(data: Character) {
    val hp by data.hp
    val damage by data.damage
    val tempHp by data.tempHp
    val deathSaves by data.deathSaves
    val speed by data.speed
    val ac by data.ac
    val init by data.initMod
    val dice by data.hitDice

    Column(Modifier.weight(0.4f)) {
        Row(Modifier.weight(0.25f)) {
            val mod = Modifier.weight(0.33f)
            CenteredBox("Armor Class", "$ac", mod)
            CenteredBox("Initiative", init.withSign(), mod)
            CenteredBox("Walking Speed", "$speed ft", mod)
        }

        HPBox("Current Hit Points", hp - damage, hp, modifier = Modifier.weight(0.25f))
        HPBox("Temporary Hit Points", tempHp, modifier = Modifier.weight(0.25f))
        Row(Modifier.weight(0.25f)) {
            Column(Modifier.weight(0.5f)) {
                Text("Hit Dice", fontStyle = FontStyle.Italic)
                Box(Modifier.fillMaxSize()) {
                    Text(dice.toList().joinToString { (kind, count) -> "${count}d$kind" }, Modifier.align(Alignment.Center), style = MaterialTheme.typography.h6)
                }
            }
            Column(Modifier.weight(0.5f)) {
                Text("Death Saves", fontStyle = FontStyle.Italic)
                Column {
                    DeathSaveWidget("Successes", deathSaves.first)
                    DeathSaveWidget("Failures", deathSaves.second)
                }
            }
        }
    }
}

@Composable
fun ColumnScope.sheetInventory(data: Character) {
    Box(Modifier.weight(0.6f)) {
        Text("Inventory")
    }
}

@Composable
fun CharacterViewError(message: CMLException) {
    Box(Modifier.fillMaxSize()) {
        Text(
            message.message ?: "Error has no message.",
            Modifier.align(Alignment.Center),
            color = MaterialTheme.colors.error
        )
    }
}
