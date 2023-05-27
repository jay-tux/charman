package ui.views

import CMLOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cml.CMLException
import cml.IntVal
import cml.Library
import cml.Value
import ui.Renderer
import ui.dialogs.CurrencyDialog
import ui.dialogs.ItemDialog
import ui.dialogs.choiceDispatcher
import ui.widgets.*
import uiData.Character
import uiData.ClassDesc
import withSign

@Composable
fun CharacterView(data: Character) {
    var hasOverlay by remember { mutableStateOf(false) }
    var overlay by remember { mutableStateOf(Renderer {}) }

    val onOverlay = { r: Renderer ->
        hasOverlay = true
        overlay = r
    }

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.padding(8.dp)) {
            Row(Modifier.weight(0.10f)) { sheetTopRow(data) }

            Row(Modifier.weight(0.90f)) {
                Column(Modifier.weight(0.25f)) {
                    Row(Modifier.weight(0.75f)) {
                        sheetAbilities(data)
                        sheetProficiencies(data)
                    }
                    Column(Modifier.weight(0.25f)) {
                        sheetPassivePerception(data)
                        Row(Modifier.weight(0.75f)) {
                            sheetLanguages(data)
                            sheetItemProfs(data)
                        }
                    }
                }
                Spacer(Modifier.weight(0.02f))

                Column(Modifier.weight(0.71f)) {
                    sheetCentralNumbers(data)
                    sheetTraitsAndActions(data, onOverlay)
                }
            }
        }

        if(hasOverlay) {
            Box(Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.33f)).clickable { hasOverlay = false }) {
                overlay.rFun(this, Modifier.background(MaterialTheme.colors.background).clickable(enabled = false) {})
            }
        }
    }
}

@Composable
fun RowScope.sheetTopRow(data: Character) {
    val name by data.name
    val classes by data.classes
    val race by data.race
    val background by data.background
    var count by remember { mutableStateOf(0) }
    var options by remember { mutableStateOf(listOf<Value>()) }
    var setCallback by remember { mutableStateOf({ _: Value -> }) }
    val choiceNo = remember { mutableStateOf(0) }

    val levelUp = { cName: String, cl: ClassDesc ->
        Library.withChoices(
            c = data,
            selector = { it.classesChoices[cName] },
            render = { cnt, opts, onSet ->
                choiceNo.value++; count = cnt; options = opts; setCallback = onSet
            }
        ) {
            cl.cls.type.functions["onLevelUp"]?.call(
                listOf(IntVal(cl.level + 1, Character.posInit)),
                Character.posInit
            ) ?: CMLOut.addWarning("Cannot call onLevelUp for $cName")
            // update state
        }
    }

    Text(name, Modifier.weight(0.4f), style = MaterialTheme.typography.h3)
    Row(Modifier.weight(0.6f).fillMaxHeight()) {
        LazyScrollColumn(Modifier.weight(0.5f).align(Alignment.CenterVertically)) {
            item {
                Text("Classes", fontWeight = FontWeight.Bold)
            }
            items(classes.toList()) { (cl, lvl) ->
                indented(Modifier.clickable { levelUp(cl, lvl) }) {
                    Text("$cl (level ${lvl.level}")
                    Icon(Icons.Default.ArrowDropUp, "")
                    Text(")")
                }
            }
        }

        LazyScrollColumn(Modifier.weight(0.5f).align(Alignment.CenterVertically)) {
            item {
                boldThenNormal("Race:", race.first)
            }
            item {
                boldThenNormal("Background:", background.first)
            }
        }
    }

    if(count != 0) {
        choiceDispatcher(
            count, choiceNo.value, options, { count = 0 }, setCallback
        )
    }
}

@Composable
fun RowScope.sheetAbilities(data: Character) {
    val abilities by data.abilities
    LazyScrollColumn(Modifier.weight(0.065f)) {
        items(abilities.toList()) { (ab, stat) ->
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
            items(skillProf.toList().sortedBy { it.first }) { (name, skill) ->
                ModScoreCard("$name (${skill.third})", skill.first, skill.second)
            }
        }
    }
}

enum class Tabs(val title: String) {
    ACTIONS("Actions"), SPELLS("Spells"), TRAITS("Traits"), INVENTORY("Inventory")
}

@Composable
fun ColumnScope.sheetTraitsAndActions(data: Character, onDetails: (Renderer) -> Unit) {
    var currentTab by remember { mutableStateOf(Tabs.TRAITS) }

    Column(Modifier.weight(0.75f)) {
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
                Tabs.ACTIONS -> sheetActionsPanel(data) { r -> onDetails(r) }
                Tabs.SPELLS -> sheetSpellsPanel(data) { r -> onDetails(r) }
                Tabs.TRAITS -> sheetTraitsPanel(data)
                Tabs.INVENTORY -> sheetInventoryPanel(data)
            }
        }
    }
}

@Composable
fun BoxScope.sheetActionsPanel(data: Character, onDetails: (Renderer) -> Unit) {
    val profs by data.itemProficiencies
    LazyScrollColumn(Modifier.fillMaxSize()) {
        items(data.actions.value) { action ->
            Box(Modifier.clickable { onDetails(Renderer { action.renderFull(data, this, it) }) }) {
                action.render(data, profs)
            }
            Spacer(Modifier.height(5.dp))
        }
    }
}

@Composable
fun BoxScope.sheetSpellsPanel(data: Character, onDetails: (Renderer) -> Unit) {
    val level by data.casterLevelX6
    val specialCasting by data.specialCasting
    val used by data.usedSpellSlots
    val spells by data.spells
    val classes by data.classes

    val currentSlots = remember {
        val res = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
        val actualL = level / 6
        if(actualL > 0) {
            val ref = Character.defaultSpellSlots[actualL - 1]
            for(i in 0 until 9) {
                res[i] += ref[i]
            }
        }
        specialCasting.forEach { (n, d) ->
            val l = classes[n]?.level
            if(l == null) { CMLOut.addWarning("Spell slots have been added for class $n, but ${data.name} is not of this class.") }
            else if(l > 0) {
                val ref = d.second[l - 1]
                for(i in 0 until 9) {
                    res[i] += ref[i]
                }
            }
        }

        res
    }

    if(level == 0 && specialCasting.isEmpty() && spells.isEmpty()) {
        Box(Modifier.fillMaxSize()) {
            Text(
                "You do not have any traits that allow you to cast spells.",
                Modifier.align(Alignment.Center),
                color = MaterialTheme.colors.primaryVariant
            )
        }
    }
    else {
        LazyScrollColumn(Modifier.fillMaxSize()) {
            item {
                if (level == 0 && specialCasting.isEmpty()) {
                    Column(Modifier.fillMaxWidth()) {
                        Text(
                            "You do not have any spell slots. Any spells you can cast are cast using other traits.",
                            Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                }
            }

            item {
                Box(Modifier.fillMaxWidth().background(MaterialTheme.colors.primary.copy(alpha = 0.2f)).padding(2.dp)) {
                    Text("Cantrips", Modifier.align(Alignment.TopStart), fontWeight = FontWeight.Bold)
                    Text("At will", Modifier.align(Alignment.TopEnd), fontStyle = FontStyle.Italic)
                }
            }
            items(spells.filter { it.level == 0 }) {
                indented {
                    SpellCard(it) { onDetails(Renderer { m -> spellDetails(it, m) }) }
                }
            }

            for(i in 1..9) {
                item {
                    Box(Modifier.fillMaxWidth().background(MaterialTheme.colors.primary.copy(alpha = 0.2f)).padding(2.dp)) {
                        Text("Level $i Spells", Modifier.align(Alignment.TopStart), fontWeight = FontWeight.Bold)

                        SpellSlots(
                            amount = currentSlots[i - 1],
                            used = used[i - 1],
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) { data.useSpellSlot(i) }
                    }
                }
                items(spells.filter { it.level == i }) {
                    indented {
                        SpellCard(it) { onDetails(Renderer { m -> spellDetails(it, m) }) }
                    }
                }
            }
        }
    }
}

@Composable
fun BoxScope.sheetTraitsPanel(data: Character) {
    val race by data.race
    val background by data.background
    val racialTraits by data.racialTraits
    val classTraits by data.classTraits
    val backgroundTraits by data.backgroundTraits

    LazyScrollColumn(Modifier.fillMaxSize()) {
        items(racialTraits.toList()) { (name, desc) ->
            TraitCard(name, race.first, desc.first)
            Spacer(Modifier.height(7.dp))
        }

        items(classTraits.toList().sortedBy { it.second.second }) { (name, desc) ->
            TraitCard(name, desc.second, desc.first)
            Spacer(Modifier.height(7.dp))
        }

        items(backgroundTraits.toList()) { (name, desc) ->
            TraitCard(name, background.first, desc.first)
            Spacer(Modifier.height(7.dp))
        }
    }
}

@Composable
fun BoxScope.sheetInventoryPanel(data: Character) {
    val inventory by data.inventory
    val money by data.money

    var editingMoney by remember { mutableStateOf(false) }
    var delta by remember { mutableStateOf(0) }
    var addingItem by remember { mutableStateOf(false) }

    val stopEdit = {
        editingMoney = false
        delta = 0
    }

    Column {
        Column(Modifier.weight(0.15f)) {
            Text("Money", fontStyle = FontStyle.Italic)
            Row(Modifier.fillMaxWidth().clickable { editingMoney = true }) {
                money.toList().sortedBy { it.second.conversion }.forEach { (abbrev, desc) ->
                    Box(Modifier.weight(1.0f)) { // distribute equally
                        CurrencyWidget(abbrev, desc)
                    }
                }
            }
        }
        LazyScrollColumn(Modifier.weight(0.85f)) {
            item {
                Row {
                    Text("Items", Modifier.weight(0.9f), fontStyle = FontStyle.Italic)
                    Surface(Modifier.weight(0.1f).clickable { addingItem = true }) {
                        Icon(Icons.Default.Add, "", Modifier.background(MaterialTheme.colors.surface))
                    }
                }
            }
            items(inventory.toList()) { (item, count) ->
                Row {
                    indented(Modifier.weight(0.9f)) {
                        Text(
                            if (count == 1) item.name else "${item.name} x$count",
                            Modifier.weight(0.33f)
                        )
                        Text("${item.weight} lbs.", Modifier.weight(0.20f))
                        Text("${item.value.first} ${item.value.second}", Modifier.weight(0.20f))
                        Text(
                            item.traits.joinToString(", ") { it.first },
                            Modifier.weight(0.27f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Surface(Modifier.weight(0.1f).clickable { data.removeItem(item) }) {
                        Icon(Icons.Default.Remove, "", Modifier.background(MaterialTheme.colors.surface))
                    }
                }
            }
        }
    }

    if(editingMoney) {
        CurrencyDialog(
            currencies = money.keys.toList(),
            onClose = { stopEdit() },
            canPayExactly = { unit, amount -> data.hasCurrency(unit, amount) },
            canPay = { unit, amount -> data.canPay(unit, amount) },
            onExact = { unit, amount -> data.payExact(unit, amount); stopEdit() },
            onPay = { unit, amount -> data.pay(unit, amount); stopEdit() },
            onGain = { unit, amount -> data.earn(unit, amount); stopEdit() }
        )
    }

    if(addingItem) {
        ItemDialog(
            onClose = { addingItem = false },
            onAdd = { item, count -> data.addItem(item, count) }
        )
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
fun RowScope.sheetLanguages(data: Character) {
    Column(Modifier.weight(0.50f)) {
        Text("Languages", fontWeight = FontWeight.Bold)
        LazyScrollColumn {
            items(data.languages.toList()) { (l, _) ->
                indented {
                    Text(l)
                }
            }
        }
    }
}

@Composable
fun RowScope.sheetItemProfs(data: Character) {
    val proficiencies by data.itemProficiencies
    Column(Modifier.weight(0.50f)) {
        Text("Item Proficiencies", fontWeight = FontWeight.Bold)
        LazyScrollColumn {
            items(proficiencies.toList()) { tag ->
                indented {
                    Text(if(tag.endsWith("Armor") || tag.endsWith("s")) tag else "${tag}s")
                }
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

    Row(Modifier.weight(0.25f)) {
        Column(Modifier.weight(0.5f)) {
            HPBox("Current Hit Points", hp - damage, hp, modifier = Modifier.weight(0.25f)) { data.modHP(it) }
            HPBox("Temporary Hit Points", tempHp, healLabel = "Add Temp HP", modifier = Modifier.weight(0.25f)) {
                if(it < 0) data.modHP(it) else data.addTempHP(it)
            }
        }

        Column(Modifier.weight(0.5f)) {
            Row(Modifier.weight(0.25f)) {
                val mod = Modifier.weight(0.33f)
                CenteredBox("Armor Class", "$ac", mod)
                CenteredBox("Initiative", init.withSign(), mod)
                CenteredBox("Walking Speed", "$speed ft", mod)
            }
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
                        DeathSaveWidget("Successes", deathSaves.first) { data.deathSaves.value = data.deathSaves.value.let { it.copy(first = (it.first + 1) % 4) } }
                        DeathSaveWidget("Failures", deathSaves.second) { data.deathSaves.value = data.deathSaves.value.let { it.copy(second = (it.second + 1) % 4) } }
                    }
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
