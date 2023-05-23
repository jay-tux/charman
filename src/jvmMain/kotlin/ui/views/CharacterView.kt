package ui.views

import CMLOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cml.CMLException
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
                    Text("$cl (level ${lvl.level})")
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
            AbilityScoreCard(ab, stat.score, data.abilityMod(stat.instance))
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
fun RowScope.sheetProficiencies(data: Character) {
    var insp by data.inspiration
    val saveProf by data.saveMods
    val skillProf by data.skillMods

    Column(Modifier.weight(0.265f)) {
        InspirationWidget(insp) { insp = it }
        IntStringCard(data.proficiency(), "Proficiency Bonus", true)
        Spacer(Modifier.weight(0.025f))
        LazyScrollColumn(Modifier.weight(0.25f)) {
            items(saveProf.toList()) { (name, stat) ->
                ModScoreCard(name, stat.first, stat.second)
            }
        }
        Spacer(Modifier.weight(0.025f))
        LazyScrollColumn(Modifier.weight(0.55f)) {
            items(skillProf.toList()) { (name, skill) ->
                ModScoreCard("$name (${skill.third})", skill.first, skill.second)
            }
        }
    }
}

enum class Tabs(val title: String) {
    ACTIONS("Actions"), SPELLS("Spells"), TRAITS("Traits")
}

@Composable
fun RowScope.sheetTraitsAndActions(data: Character) {
    var currentTab by remember { mutableStateOf(Tabs.TRAITS) }

    Column(Modifier.weight(0.43f)) {
        LazyScrollRow(Modifier.height(45.dp)) {
            items(Tabs.values().toList()) {
                Button(
                    { currentTab = it },
                    modifier = Modifier.width(150.dp).fillMaxHeight(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = if (currentTab == it) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primary)
                ) {
                    Text(it.title)
                }
                Spacer(Modifier.width(5.dp))

            }
        }
        Box(Modifier) {
            when(currentTab) {
                Tabs.ACTIONS -> sheetActionsPanel(data)
                Tabs.SPELLS -> sheetSpellsPanel(data)
                Tabs.TRAITS -> sheetTraitsPanel(data)
            }
        }
    }
}

@Composable
fun BoxScope.sheetActionsPanel(data: Character) {
    LazyScrollColumn(Modifier.fillMaxSize()) {
        items(data.actions.value) { action ->
            Box(Modifier.clickable { /* signify to call action.renderFull(data) */ }) {
                action.render(data)
            }
            Spacer(Modifier.height(5.dp))
        }
    }
}

@Composable
fun BoxScope.sheetSpellsPanel(data: Character) {
    LazyScrollColumn(Modifier.fillMaxSize()) {
        items(data.spells.value) { spell ->
            Row(Modifier.clickable { /* somehow signify we need a details pane */ }) {
                Text("${spell.level}", Modifier.weight(0.05f).align(Alignment.CenterVertically))
                Column(Modifier.weight(0.30f)) {
                    Text(spell.name, fontWeight = FontWeight.Bold)
                    Text(spell.source, fontStyle = FontStyle.Italic)
                }
                Text(spell.castingTime, Modifier.weight(0.20f).align(Alignment.CenterVertically))
                Text(spell.duration, Modifier.weight(0.25f).align(Alignment.CenterVertically))
                Text(spell.components.joinToString(), Modifier.weight(0.20f).align(Alignment.CenterVertically))
            }
            Spacer(Modifier.height(5.dp))
        }
    }
}

@Composable
fun BoxScope.sheetTraitsPanel(data: Character) {
    LazyScrollColumn(Modifier.fillMaxSize()) {
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
    val skills by data.skillMods
    if(skills["Perception"] == null) CMLOut.addWarning("Skill Perception does not exist")
    Column(Modifier.weight(0.25f)) {
        IntStringCard(10 + (skills["Perception"]?.first ?: 0), "Passive Wisdom (Perception)")
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
    val inventory by data.inventory
    Column(Modifier.weight(0.6f)) {
        Text("Inventory", style = MaterialTheme.typography.h6)
        LazyScrollColumn {
            items(inventory) { item ->
                Row {
                    Text(item.name, Modifier.weight(0.6f))
                    Text("${item.weight} lbs.", Modifier.weight(0.4f))
                }
            }
        }
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
