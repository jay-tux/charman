package widgets

import addGet
import addNull
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import capitalizeFirst
import data.*
import org.jetbrains.exposed.sql.transactions.transaction
import removeGet
import updateGet


enum class RaceDialogPage(val pageName: String) {
    NAME_BASIC("Basic Information"),
    ASI("Ability Score Increases"),
    TRAITS("Race Traits"),
    LANGUAGES("Languages");

    fun previous(): RaceDialogPage {
        when(this) {
            NAME_BASIC -> return NAME_BASIC
            ASI -> return NAME_BASIC
            TRAITS -> return ASI
            LANGUAGES -> return TRAITS
        }
    }

    fun next(): RaceDialogPage {
        when(this) {
            NAME_BASIC -> return ASI
            ASI -> return TRAITS
            TRAITS -> return LANGUAGES
            LANGUAGES -> return LANGUAGES
        }
    }
}

@Composable
fun addRaceDialogContent(exitRequest: () -> Unit) {

    val sources = listDataSources()
    val abilities = listAbilities()
    val types = listTypes()
    val abilityOrAny = abilities.addNull()
    var typeOrAdd = types.addNull()
    val sizes = CreatureSize.values().toList()
    var page by remember { mutableStateOf(RaceDialogPage.NAME_BASIC) }

    var name by remember { mutableStateOf("") }
    var size by remember { mutableStateOf(CreatureSize.MEDIUM) }
    var typeIdx by remember { mutableStateOf(0) }
    var speed by remember { mutableStateOf(30) }
    var speedStr by remember { mutableStateOf("30") }
    var srcIdx by remember { mutableStateOf(0) }
    var increases by remember { mutableStateOf(listOf<Pair<Int, Int>>()) }

    var traits by remember { mutableStateOf(listTraits()) }
    var selectedTraits by remember { mutableStateOf(listOf<Trait>()) }
    var languages by remember { mutableStateOf(listLanguages()) }
    var selectedLanguages by remember { mutableStateOf(listOf<Language>()) }
    var languagesToAdd by remember { mutableStateOf(listOf<String>()) }
    var languagesSelected by remember { mutableStateOf(listOf<Int>()) }
    var anyLanguages by remember { mutableStateOf(0) }

    var showTraitDialog by remember { mutableStateOf(false) }
    var traitDialogData by remember { mutableStateOf<Trait?>(null) }

    var showTypeDialog by remember { mutableStateOf(false) }

    val filterASI = {
        increases = increases.filter { (_, inc) -> inc != 0 }
    }

    val addRemoveTrait = { add: Boolean, t: Trait ->
        selectedTraits =
            if(add) selectedTraits.addGet(t)
            else selectedTraits.removeGet(selectedTraits.indexOf(t))
    }

    val addRemoveLanguage = { add: Boolean, l: Language ->
        selectedLanguages =
            if(add) selectedLanguages.addGet(l)
            else selectedLanguages.removeGet(selectedLanguages.indexOf(l))
    }

    val addRemoveTempLanguage = { add: Boolean, idx: Int ->
        languagesSelected =
            if(add) languagesSelected.addGet(idx)
            else languagesSelected.filter { it != idx }
    }

    val doubleClickMod = { trait: Trait ->
        Modifier.pointerInput(Unit) {
            detectTapGestures(
                onDoubleTap = { _ -> showTraitDialog = true; traitDialogData = trait }
            )
        }
    }

    val onRequestAddType = { t: CreatureType? ->
        if(t == null) showTypeDialog = true
        else typeIdx = typeOrAdd.indexOf(t)
    }

    val onSave = {
        val aLang = transaction {
            languagesToAdd.map { l -> Language.new { this.name = l } }
        }

        val lang = mutableListOf<Language?>()
        selectedLanguages.forEach { lang.add(it) }
        languagesSelected.forEach { lang.add(aLang[it]) }

        mkRace(
            name = name,
            size = size,
            type = typeOrAdd[typeIdx]!!,
            speed = speed,
            src = sources[srcIdx],
            traits = selectedTraits,
            languages = lang,
            chooseLanguages = anyLanguages,
            asi = increases.map { (a, i) -> Pair(abilityOrAny[a], i) }
        )
    }

    Column {
        Text("Add race", style = MaterialTheme.typography.h4)
        Text(page.pageName, style = MaterialTheme.typography.h6)

        Box(Modifier.fillMaxHeight(0.85f)) {
            when (page) {
                RaceDialogPage.NAME_BASIC -> Box {
                    Column {
                        Row {
                            Text("Race Name", Modifier.weight(0.3f).align(Alignment.CenterVertically))
                            Spacer(Modifier.weight(0.1f))
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                modifier = Modifier.weight(0.6f),
                                singleLine = true
                            )
                        }
                        Row {
                            Text("Base Walking Speed", Modifier.weight(0.3f).align(Alignment.CenterVertically))
                            Spacer(Modifier.weight(0.1f))
                            OutlinedTextField(
                                value = speedStr,
                                onValueChange = { speedStr = it; speed = it.toIntOrNull() ?: 0 },
                                isError = speed.toString() != speedStr,
                                modifier = Modifier.weight(0.6f),
                                singleLine = true
                            )
                        }
                        Row {
                            Text("Creature Size", Modifier.weight(0.3f).align(Alignment.CenterVertically))
                            Spacer(Modifier.weight(0.1f))
                            Dropdown(sizes, { size = sizes[it] }, sizes.indexOf(size), "(creature size)", Modifier.weight(0.6f)) {
                                item, selected -> DisabledTextFieldOrText(item.name.capitalizeFirst(), selected)
                            }
                        }
                        Row {
                            Text("Creature Type", Modifier.weight(0.3f).align(Alignment.CenterVertically))
                            Spacer(Modifier.weight(0.1f))
                            Dropdown(typeOrAdd, { onRequestAddType(typeOrAdd[it]) }, typeIdx, "(creature type)", Modifier.weight(0.6f)) {
                                item, selected -> DisabledTextFieldOrText(item?.name ?: "(new creature type)", selected)
                            }
                        }
                        Row {
                            Text("Data Source Containing Race", Modifier.weight(0.3f).align(Alignment.CenterVertically))
                            Spacer(Modifier.weight(0.1f))
                            Dropdown(sources, { srcIdx = it }, srcIdx, "(data source)", Modifier.weight(0.6f)) {
                                item, selected -> DisabledTextFieldOrText(item.name, selected)
                            }
                        }
                    }
                }

                RaceDialogPage.ASI -> Row {
                    LazyColumn(Modifier.fillMaxWidth(0.8f)) {
                        itemsIndexed(increases) { idx, (ab, inc) ->
                            var deltaStr by remember { mutableStateOf(if(inc > 0) "+$inc" else "$inc") }
                            var delta by remember { mutableStateOf(inc) }

                            Row {
                                Column(Modifier.weight(0.45f)) {
                                    Dropdown(
                                        items = abilityOrAny,
                                        onSelect = {
                                            increases = increases.updateGet(idx, Pair(it, inc))
                                        },
                                        chosen = ab
                                    ) { selection, selected ->
                                        DisabledTextFieldOrText(selection?.name ?: "(any ability)", selected)
                                    }
                                }
                                Spacer(Modifier.width(15.dp))
                                OutlinedTextField(
                                    value = deltaStr,
                                    onValueChange = { deltaStr = it; delta = it.toIntOrNull()?: 0 },
                                    isError = delta.toString() != deltaStr && "+$delta" != deltaStr,
                                    modifier = Modifier.onFocusChanged {
                                        if(!it.isFocused) increases = increases.updateGet(idx, Pair(ab, delta))
                                    }
                                )
                            }
                        }
                    }
                    IconButton({ increases = increases.addGet(Pair(abilityOrAny.indexOf(null), 0)) }) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add an ability score increase"
                        )
                    }
                }

                RaceDialogPage.TRAITS -> Row {
                    LazyColumn(Modifier.fillMaxWidth(0.8f)) {
                        items(traits.toList()) {trait ->
                            Row {
                                Checkbox(
                                    checked = selectedTraits.contains(trait),
                                    onCheckedChange = { addRemoveTrait(it, trait) },
                                    Modifier.weight(0.1f)
                                )
                                Text(trait.name, modifier = doubleClickMod(trait).weight(0.8f).align(Alignment.CenterVertically))
                            }
                        }
                    }
                    IconButton({ showTraitDialog = true; traitDialogData = null }) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add a new trait"
                        )
                    }
                }

                RaceDialogPage.LANGUAGES -> Row {
                    LazyColumn(Modifier.fillMaxWidth(0.8f).padding(3.dp)) {
                        items(languages) { l ->
                            Row {
                                Checkbox(
                                    selectedLanguages.contains(l),
                                    { addRemoveLanguage(it, l) },
                                    Modifier.weight(0.1f)
                                )
                                Text(l.name, modifier = Modifier.weight(0.8f).align(Alignment.CenterVertically))
                            }
                        }

                        itemsIndexed(languagesToAdd) { idx, l ->
                            Row {
                                var name by remember { mutableStateOf(l) }
                                Checkbox(
                                    languagesSelected.contains(idx),
                                    { addRemoveTempLanguage(it, idx) },
                                    Modifier.weight(0.1f)
                                )
                                OutlinedTextField(
                                    name, { name = it },
                                    Modifier.weight(0.8f).onFocusChanged {
                                        if(!it.isFocused) languagesToAdd = languagesToAdd.updateGet(idx, name)
                                    }
                                )
                            }
                        }

                        item {
                            Row {
                                IconButton({ if (anyLanguages > 0) anyLanguages -= 1 }) {
                                    Icon(
                                        Icons.Default.Remove,
                                        contentDescription = "Remove a \"Choose Any\" language"
                                    )
                                }
                                OutlinedTextField(
                                    value = "$anyLanguages languages of your choice",
                                    onValueChange = {},
                                    modifier = Modifier,
                                    enabled = false
                                )
                                IconButton({ anyLanguages += 1 }) {
                                    Icon(
                                        Icons.Default.Add,
                                        contentDescription = "Add a new \"Choose Any\" language"
                                    )
                                }
                            }
                        }
                    }

                    IconButton({ languagesToAdd = languagesToAdd.addGet("") }) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add a new language"
                        )
                    }
                }
            }
        }
        Row {
            val mod = Modifier.weight(0.45f)
            when(page) {
                RaceDialogPage.NAME_BASIC -> Button({ exitRequest() }, mod) {
                    Icon(Icons.Default.Close, "Cancel")
                    Text("Cancel")
                }
                else -> Button({ page = page.previous() }, mod) {
                    Icon(Icons.Default.ArrowBack, "Previous")
                    Text("Previous")
                }
            }
            Spacer(Modifier.weight(0.1f))
            when(page) {
                RaceDialogPage.LANGUAGES -> Button({ onSave(); exitRequest() }, mod) {
                    Text("Save")
                    Icon(Icons.Default.Check, "Save")
                }
                else -> Button(
                    { page = page.next(); filterASI() }, mod,
                    enabled = typeOrAdd[typeIdx] != null
                ) {
                    Text("Next")
                    Icon(Icons.Default.ArrowForward, "Next")
                }
            }
        }
    }

    if(showTraitDialog) {
        traitDialog(
            trait = traitDialogData,
            onExit = { showTraitDialog = false },
            onAdd = { traits = traits.addGet(it) },
            onMod = { old, new -> traits = traits.updateGet(traits.indexOf(old), new) }
        )
    }
    if(showTypeDialog) {
        creatureTypeDialog(
            onExit = { showTypeDialog = false },
            onAdd = { t -> typeOrAdd = typeOrAdd.addGet(t); typeIdx = typeOrAdd.indexOf(t) },
        )
    }
}

@Composable
fun addRaceDialog(onExit: () -> Unit) = Dialog(
    onCloseRequest = { onExit() },
    state = rememberDialogState(
        position = WindowPosition.PlatformDefault,
        size = DpSize(600.dp, 500.dp)
    )
) {
    Box(Modifier.padding(15.dp)) {
        addRaceDialogContent {
            onExit()
        }
    }
}
