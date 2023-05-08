package dialogs

import addGet
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import data.DiceType
import data.DnDClass
import data.listDataSources
import data.mkClass
import org.jetbrains.exposed.sql.transactions.transaction
import widgets.*

enum class ClassDialogPage(val pageName: String) {
    NAME_BASIC("Basic Information"),
    PROFICIENCIES("Proficiencies");

    fun previous(): ClassDialogPage {
        when(this) {
            NAME_BASIC -> return NAME_BASIC
            PROFICIENCIES -> return NAME_BASIC
        }
    }

    fun next(): ClassDialogPage {
        when(this) {
            NAME_BASIC -> return PROFICIENCIES
            PROFICIENCIES -> return PROFICIENCIES
        }
    }
}

@Composable
fun classDialogContent(
    cls: DnDClass?,
    onExit: () -> Unit,
    onAdd: (DnDClass) -> Unit,
    onMod: (DnDClass) -> Unit
) {
    var page by remember { mutableStateOf(ClassDialogPage.NAME_BASIC) }
    val sources by remember { mutableStateOf(listDataSources()) }

    val getSrcIdx = {
        when(cls) {
            null -> 0
            else -> transaction {
                sources.map{ it.name }.indexOf(cls.src.name)
            }
        }
    }

    var name by remember { mutableStateOf(cls?.name ?: "") }
    var dice by remember { mutableStateOf(DiceType.values().toList().indexOf(cls?.hitDiceType ?: DiceType.D8)) }
    var srcIdx by remember { mutableStateOf(getSrcIdx()) }
    var itemProfs by remember { mutableStateOf(
        if (cls == null) listOf()
        else transaction { cls.itemProficiencies.toList() }
    ) }
    var saveProfs by remember { mutableStateOf(
        if(cls == null) listOf()
        else transaction { cls.savingThrowProficiencies.toList() }
    ) }
    var chooseProf by remember { mutableStateOf(cls?.chooseSkillCount ?: 2) }
    var skillProf by remember { mutableStateOf(
        if (cls == null) listOf()
        else transaction { cls.skillOptions.toList() }
    ) }

    val refreshItemTags = {
        transaction {
            itemProfs.forEach { it.refresh() }
        }
    }
    val refreshAbilities = {
        transaction {
            saveProfs.forEach { it.refresh() }
        }
    }
    val refreshSkills = {
        transaction {
            skillProf.forEach { it.refresh() }
        }
    }

    val onSave = {
        if(cls == null)
            onAdd(mkClass(name, DiceType.values()[dice], saveProfs, itemProfs, sources[srcIdx], chooseProf, skillProf))
        else {
            TODO()
        }
    }

    Column {
        Text("Add class", style = MaterialTheme.typography.h4)
        Text(page.pageName, style = MaterialTheme.typography.h6)

        Box(Modifier.fillMaxHeight(0.95f)) {
            when(page) {
                ClassDialogPage.NAME_BASIC -> Column {
                    Row {
                        Text("Class Name", Modifier.weight(0.3f).align(Alignment.CenterVertically))
                        Spacer(Modifier.weight(0.1f))
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            modifier = Modifier.weight(0.6f),
                            singleLine = true
                        )
                    }
                    Row {
                        Text("Hit Dice", Modifier.weight(0.3f).align(Alignment.CenterVertically))
                        Spacer(Modifier.weight(0.1f))
                        Dropdown(
                            items = DiceType.values().toList(),
                            onSelect = { dice = it },
                            chosen = dice
                        ) {
                            v, s -> DisabledTextFieldOrText("$v", s)
                        }
                    }
                    Row {
                        Text(
                            "Data Source Containing Class",
                            Modifier.weight(0.3f).align(Alignment.CenterVertically)
                        )
                        Spacer(Modifier.weight(0.1f))
                        Dropdown(sources, { srcIdx = it }, srcIdx, "(data source)", Modifier.weight(0.6f)) {
                                item, selected -> DisabledTextFieldOrText(item.name, selected)
                        }
                    }
                }

                ClassDialogPage.PROFICIENCIES -> Column {
                    Column(Modifier.weight(0.35f)) {
                        Text("Item kind proficiencies", textDecoration = TextDecoration.Underline)
                        itemTagWidget(
                            itemProfs,
                            { add -> itemProfs = itemProfs.addGet(add) },
                            { rm -> itemProfs = itemProfs.filter { it.id != rm.id } },
                            { refreshItemTags() }
                        )
                    }

                    Spacer(Modifier.weight(0.05f))

                    Column(Modifier.weight(0.35f)) {
                        Text("Saving Throw Proficiencies", textDecoration = TextDecoration.Underline)
                        abilityWidget(
                            saveProfs,
                            { add -> saveProfs = saveProfs + add },
                            { rm -> saveProfs = saveProfs.filter { it.id != rm.id } },
                            { refreshAbilities() }
                        )
                    }

                    Spacer(Modifier.weight(0.05f))

                    Column(Modifier.weight(0.4f)) {
                        Text("Skill Proficiencies", textDecoration = TextDecoration.Underline)
                        skillWidget(
                            selected = skillProf,
                            choose = chooseProf,
                            onSelect = { add -> skillProf = skillProf + add },
                            onDeselect = { rm -> skillProf = skillProf.filter { it.id != rm.id } },
                            onChooseChange = { chooseProf = it },
                            onUpdateSkill = { refreshSkills() }
                        )
                    }
                }
            }
        }

        Row {
            val mod = Modifier.weight(0.45f)
            when(page) {
                ClassDialogPage.NAME_BASIC -> Button({ onExit() }, mod) {
                    Icon(Icons.Default.Close, "Cancel")
                    androidx.compose.material.Text("Cancel")
                }
                else -> Button({ page = page.previous() }, mod) {
                    Icon(Icons.Default.ArrowBack, "Previous")
                    androidx.compose.material.Text("Previous")
                }
            }
            Spacer(Modifier.weight(0.1f))
            when(page) {
                ClassDialogPage.PROFICIENCIES -> Button({ onSave(); onExit() }, mod) {
                    androidx.compose.material.Text("Save")
                    Icon(Icons.Default.Check, "Save")
                }
                else -> Button(
                    { page = page.next() }, mod
                ) {
                    androidx.compose.material.Text("Next")
                    Icon(Icons.Default.ArrowForward, "Next")
                }
            }
        }
    }
}

@Composable
fun classDialog(
    cls: DnDClass?,
    onExit: () -> Unit,
    onAdd: (DnDClass) -> Unit,
    onMod: (DnDClass) -> Unit
) = DefaultDialog(onExit, 600.dp, 800.dp) {
    Box(Modifier.padding(15.dp)) {
        classDialogContent(cls, onExit, onAdd, onMod)
    }
}

@Composable
fun addClassDialog(onExit: () -> Unit, onAdd: (DnDClass) -> Unit) = classDialog(null, onExit, onAdd) { }

@Composable
fun updateClassDialog(cls: DnDClass, onExit: () -> Unit, onMod: (DnDClass) -> Unit) = classDialog(cls, onExit, { }) { onMod(it) }