package ui.dialogs

import CMLOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import arrow.core.flatMap
import cml.*
import data.*
import filterRight
import fold
import ui.dialogs.CreationPage.*
import ui.widgets.LazyScrollColumn
import ui.widgets.TraitCard
import ui.widgets.YesNoButton
import ui.widgets.indented
import uiData.Character
import uiData.ClassDesc
import uiData.Mutable
import withSign

enum class CreationPage {
    NAME, RACE, CLASS, BACKGROUND, ABILITIES
}

fun next(page: CreationPage) = when (page) {
    NAME -> RACE
    RACE -> CLASS
    CLASS -> BACKGROUND
    BACKGROUND -> ABILITIES
    ABILITIES -> ABILITIES
}

fun prev(page: CreationPage) = when (page) {
    NAME -> NAME
    RACE -> NAME
    CLASS -> RACE
    BACKGROUND -> CLASS
    ABILITIES -> BACKGROUND
}

@Composable
fun CharacterCreationDialog(onClose: () -> Unit) = DefaultDialog(onClose, 600.dp, 800.dp) {
    var result by remember { Mutable.stateFrom(Character.mold()) }
    var currentPage by remember { mutableStateOf(NAME) }
    var canGoNext by remember { mutableStateOf(true) }
    val choiceNo = remember { mutableStateOf(0) }
    var exception by remember { mutableStateOf("") }

    val save = {
        val mod = Library.construct("Constitution", Character.posInit)?.let {
            result.value.abilityMod(it)
        } ?: 0
        result.value.hp.value = mod + result.value.hitDice.value.firstNotNullOf { it.key }

        result.value.onUpdate()
        Scripts.saveChar(result.value)
        CMLOut.refresh(clear = false)
        onClose()
    }

    val update = { fn: (Character) -> Unit ->
        result.update(fn)
        result = result
    }

    val onError = { err: CMLException ->
        exception = "An error occurred while creating your character:\n${err.localizedMessage}"
    }

    Column(Modifier.fillMaxSize()) {
        Box(Modifier.weight(0.95f)) {
            when (currentPage) {
                NAME -> namePage(result.value, { canGoNext = it })
                RACE -> racePage(result.value, choiceNo, { canGoNext = it }, { currentPage = next(currentPage) }, { e -> onError(e) }) { fn -> update(fn) }
                CLASS -> classPage(result.value, choiceNo, { canGoNext = it }, { currentPage = next(currentPage) }, { e -> onError(e) }) { fn -> update(fn) }
                BACKGROUND -> backgroundPage(result.value, choiceNo, { canGoNext = it }, { currentPage = next(currentPage) }, { e -> onError(e) }) { fn -> update(fn) }
                ABILITIES -> abilitiesPage(result.value, { canGoNext = it }) { fn -> update(fn) }
            }
        }
        Box(Modifier.weight(0.05f)) {
            YesNoButton(
                no = if(prev(currentPage) == currentPage) "Cancel" else "Previous",
                yes = if(next(currentPage) == currentPage) "Save" else "Next",
                noEnabled = true,
                yesEnabled = canGoNext,
                onNo = { if(prev(currentPage) == currentPage) { onClose() } else { currentPage = prev(currentPage) } },
                onYes = { if(next(currentPage) == currentPage) { save() } else { currentPage = next(currentPage) } }
            )
        }
    }

    if(exception != "") {
        errorDialog(exception) {
            exception = ""
            onClose()
        }
    }
}

@Composable
fun header(text: String, subTitle: String? = null) {
    Text(text, style = MaterialTheme.typography.h5)
    if(subTitle != null) {
        indented {
            Text(subTitle, style = MaterialTheme.typography.subtitle2)
        }
    }
    Spacer(Modifier.height(2.dp))
    Divider(Modifier.fillMaxWidth(), color = Color.Black.copy(alpha = 0.33f), thickness = 1.dp)
    Spacer(Modifier.height(5.dp))
}

@Composable
fun namePage(data: Character, toggleNext: (Boolean) -> Unit) {
    var name by data.name
    toggleNext(name != "")

    Column {
        header("Step 1: Character Basics")
        OutlinedTextField(
            value = name,
            onValueChange = { n -> name = n },
            label = { Text("Character name") },
            maxLines = 1
        )
    }
}

@Composable
fun racePage(
    data: Character, choiceNo: MutableState<Int>, toggleNext: (Boolean) -> Unit, goNext: () -> Unit,
    onError: (CMLException) -> Unit, delta: ((Character) -> Unit) -> Unit
) {
    var raceV by data.race

    var selected by remember { mutableStateOf(-1) }

    var count by remember { mutableStateOf(0) }
    var options by remember { mutableStateOf(listOf<Value>()) }
    var setCallback by remember { mutableStateOf({ _: Value -> })}

    toggleNext(raceV.second.type.name != Library.phonyType().name && count == 0)

    val onSelect = { race: Pair<String, InstanceVal> ->
         delta {
             raceV = race
             it.speed.value = race.second.getInt("speed", Character.posInit).mapLeft { err ->
                 CMLOut.addWarning("Couldn't load race's base speed. Assume 30 feet.")
                 CMLOut.addError(err.localizedMessage)
                 30
             }.fold()
             Library.withChoices(
                 c = it,
                 selector = { c -> c.raceChoices },
                 render = { cnt, opts, onSet ->
                     choiceNo.value++; count = cnt; options = opts; setCallback = onSet
                 },
                 onError = { e -> onError(e) }
             ) {
                 race.second.type.functions["onSelect"]?.call(listOf(), Character.posInit)?.let { goNext() }
                     ?: CMLOut.addWarning("Cannot call onSelect for ${race.first}")
             }
         }
    }

    val validRaces = remember {
        Library.typesByKind("Race").map { race ->
            val inst = InstanceVal(race.construct(), Character.posRender)
            inst.getName(Character.posRender).map {
                val traits = inst.getList("traits", Character.posRender).map { l ->
                    l.value.map { t ->
                        t.ifInstVerifyGetName("Trait", Character.posRender).flatMap { trait ->
                            trait.second.getString("desc", Character.posRender).map { desc -> Pair(trait.first, desc) }
                        }
                    }.filterRight()
                }.fold({ ex ->
                    CMLOut.addWarning("Could not get traits for `$it'. Caused by:\n\t${ex.localizedMessage}")
                    listOf()
                }, { traits -> traits })
                Triple(it, inst, traits)
            }
        }.filterRight()
    }

    Column {
        header("Step 2: Character Race", "Select one of the races below\nSelecting might require additional choices.")
        LazyScrollColumn {
            itemsIndexed(validRaces) { index, (name, inst, traits) ->
                val expanded = selected == index || (selected == -1 && raceV.first == name)
                Column {
                    Button({ selected = index }) {
                        Box(Modifier.fillMaxWidth()) {
                            Text(name, Modifier.align(Alignment.CenterStart), style = MaterialTheme.typography.h6)
                            Icon(
                                if(expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                "",
                                Modifier.align(Alignment.CenterEnd)
                            )
                        }
                    }

                    if(expanded) {
                        Text("Below are some traits this race will give you. Others might depends on additional choices.", fontStyle = FontStyle.Italic)
                        traits.forEach { (tName, tDesc) ->
                            TraitCard(tName, name, tDesc, null, null)
                        }
                        Button({ onSelect(Pair(name, inst)) }) {
                            Spacer(Modifier.width(10.dp))
                            Box(Modifier.fillMaxWidth()) {
                                Text("Select this race", Modifier.align(Alignment.Center))
                            }
                            Spacer(Modifier.width(10.dp))
                        }
                    }
                }
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
fun classPage(
    data: Character, choiceNo: MutableState<Int>, toggleNext: (Boolean) -> Unit, goNext: () -> Unit,
    onError: (CMLException) -> Unit, delta: ((Character) -> Unit) -> Unit
) {
    var selected by remember { mutableStateOf(-1) }
    var classes by data.classes

    var count by remember { mutableStateOf(0) }
    var options by remember { mutableStateOf(listOf<Value>()) }
    var setCallback by remember { mutableStateOf({ _: Value -> })}

    toggleNext(classes.isNotEmpty() && count == 0)

    val onSelect = { classV: Pair<String, InstanceVal> ->
        delta {
            val hitDie = classV.second.getDice("hitDie", Character.posInit).fold(
                { CMLOut.addError("Hit Die not defined for class `${classV.first}."); DiceVal(1, 1, Character.posInit) },
                { k -> k }
            )
            classes += Pair(classV.first, ClassDesc(classV.second, 1, hitDie, true))
            it.hitDice.value += Pair(hitDie.kind, 1)
            it.choices.value.classesChoices[classV.first] = mutableMapOf()

            Library.withChoices(
                c = it,
                selector = { c ->
                    c.classesChoices[classV.first]
                },
                render = { cnt, opts, onSet ->
                    choiceNo.value++; count = cnt; options = opts; setCallback = onSet
                },
                onError = { e -> onError(e) }
            ) {
                classV.second.type.functions["onSelect"]?.call(listOf(), Character.posInit)?.let { goNext() }
                    ?: CMLOut.addWarning("Cannot call onSelect for ${classV.first}")
            }
        }
    }

    val validClasses = remember {
        Library.typesByKind("Class").map { race ->
            val inst = InstanceVal(race.construct(), Character.posRender)
            inst.getName(Character.posRender).map {
                Pair(it, inst)
            }
        }.filterRight()
    }

    Column {
        header("Step 3: Character Starting Class", "Select one of the classes below\nSelecting might require additional choices.")
        LazyScrollColumn {
            itemsIndexed(validClasses) { index, (name, inst) ->
                val expanded = selected == index
                Column {
                    Button({ selected = index }) {
                        Box(Modifier.fillMaxWidth()) {
                            Text(name, Modifier.align(Alignment.CenterStart), style = MaterialTheme.typography.h6)
                            Icon(
                                if(expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                "",
                                Modifier.align(Alignment.CenterEnd)
                            )
                        }
                    }

                    if(expanded) {
                        Button({ onSelect(Pair(name, inst)) }) {
                            Spacer(Modifier.width(10.dp))
                            Box(Modifier.fillMaxWidth()) {
                                Text("Select this class", Modifier.align(Alignment.Center))
                            }
                            Spacer(Modifier.width(10.dp))
                        }
                    }
                }
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
fun backgroundPage(
    data: Character, choiceNo: MutableState<Int>, toggleNext: (Boolean) -> Unit, goNext: () -> Unit,
    onError: (CMLException) -> Unit, delta: ((Character) -> Unit) -> Unit
) {
    var selected by remember { mutableStateOf(-1) }
    var background by data.background

    var count by remember { mutableStateOf(0) }
    var options by remember { mutableStateOf(listOf<Value>()) }
    var setCallback by remember { mutableStateOf({ _: Value -> })}

    toggleNext(background.second.type.name != Library.phonyType().name && count == 0)

    val onSelect = { back: Pair<String, InstanceVal> ->
        delta {
            background = back
            Library.withChoices(
                c = it,
                selector = { c -> c.backgroundChoices },
                render = { cnt, opts, onSet ->
                    choiceNo.value++; count = cnt; options = opts; setCallback = onSet
                },
                onError = { e -> onError(e) }
            ) {
                back.second.type.functions["onSelect"]?.call(listOf(), Character.posInit)?.let { goNext() }
                    ?: CMLOut.addWarning("Cannot call onSelect for ${back.first}")
            }
        }
    }

    val validBackgrounds = remember {
        Library.typesByKind("Background").map { race ->
            val inst = InstanceVal(race.construct(), Character.posRender)
            inst.getName(Character.posRender).map {
                Pair(it, inst)
            }
        }.filterRight()
    }

    Column {
        header("Step 3: Character Background", "Select one of the backgrounds below\nSelecting might require additional choices.")
        LazyScrollColumn {
            itemsIndexed(validBackgrounds) { index, (name, inst) ->
                val expanded = selected == index || (selected == -1 && background.first == name)
                Column {
                    Button({ selected = index }) {
                        Box(Modifier.fillMaxWidth()) {
                            Text(name, Modifier.align(Alignment.CenterStart), style = MaterialTheme.typography.h6)
                            Icon(
                                if(expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                "",
                                Modifier.align(Alignment.CenterEnd)
                            )
                        }
                    }

                    if(expanded) {
                        Button({ onSelect(Pair(name, inst)) }) {
                            Spacer(Modifier.width(10.dp))
                            Box(Modifier.fillMaxWidth()) {
                                Text("Select this background", Modifier.align(Alignment.Center))
                            }
                            Spacer(Modifier.width(10.dp))
                        }
                    }
                }
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
fun abilitiesPage(data: Character, toggleNext: (Boolean) -> Unit, delta: ((Character) -> Unit) -> Unit) {
    var mods by remember {
        mutableStateOf(data.abilities.value.map { (a, _) -> Pair(a, 0) }.toMap())
    }
    val originals by remember { mutableStateOf(data.abilities.value.toMap()) }

    toggleNext(mods.all { it.value in 3..18 }) // possible rolls

    Column {
        header("Step 4: Ability Scores")
        LazyScrollColumn {
            items(originals.toList()) { (abbrev, desc) ->
                Row {
                    Text("${desc.name}: ", Modifier.weight(0.4f).align(Alignment.CenterVertically))
                    OutlinedTextField(
                        if(mods[abbrev] == 0) "" else "${mods[abbrev]}",
                        { mods += Pair(abbrev, it.toIntOrNull() ?: 0) },
                        Modifier.weight(0.25f).align(Alignment.CenterVertically).onFocusChanged {
                            if(!it.hasFocus) {
                                data.abilities.value += Pair(abbrev, desc.copy(score = (mods[abbrev] ?: 0) + desc.score))
                            }
                        },
                        maxLines = 1
                    )
                    Text(
                        if(desc.score == 0) "" else desc.score.withSign(),
                        Modifier.weight(0.1f).align(Alignment.CenterVertically)
                    )
                    OutlinedTextField(
                        "${mods[abbrev]?.let { it + desc.score }}",
                        {},
                        Modifier.weight(0.25f).align(Alignment.CenterVertically),
                        enabled = false,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
fun errorDialog(msg: String, onClose: () -> Unit) = DefaultDialog({ onClose() }, 750.dp, 250.dp) {
    Column {
        Text(msg, color = MaterialTheme.colors.error)
        Button({ onClose() }, Modifier.fillMaxWidth()) {
            Text("Close")
        }
    }
}