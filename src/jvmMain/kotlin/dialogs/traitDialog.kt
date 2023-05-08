package dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import atLeastNLines
import data.Trait
import data.TraitKind
import data.TraitSource
import org.jetbrains.exposed.sql.transactions.transaction
import widgets.DisabledTextFieldOrText
import widgets.Dropdown

@Composable
fun traitDialogContent(
    trait: Trait?, onExit: () -> Unit, onAdd: (Trait) -> Unit, onMod: (Trait, Trait) -> Unit
) {
    var editing by remember { mutableStateOf(trait == null) }
    var name by remember { mutableStateOf(trait?.name ?: "") }
    var description by remember { mutableStateOf(trait?.description?.atLeastNLines(5) ?: "\n\n\n\n") }
    var kind by remember { mutableStateOf(trait?.kind ?: TraitKind.PASSIVE) }
    var source by remember { mutableStateOf(trait?.tSource?: TraitSource.RACE) }

    val save = {
        if(trait == null) {
            onAdd(transaction {
                Trait.new {
                    this.name = name
                    this.description = description.trimEnd { it == '\n' }
                    this.kind = kind
                    this.tSource = source
                }
            })
        }
        else {
            onMod(trait, transaction {
                trait.name = name
                trait.description = description.trimEnd { it == '\n' }
                trait.kind = kind
                trait.tSource = source
                trait
            })
        }
    }

    if(!editing) {
        Column {
            Column(Modifier.fillMaxHeight(0.8f)) {
                Text(name)
                Row {
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text("$kind trait.")
                        Text("Trait originates from $source.")
                    }
                }
                Text(description)
            }
            Row {
                Button({ onExit() }, Modifier.weight(0.45f)) { Text("Close") }
                Spacer(Modifier.weight(0.1f))
                Button({ editing = true }, Modifier.weight(0.45f)) { Text("Enable editing") }
            }
        }
    }
    else {
        Column {
            Column(Modifier.fillMaxHeight(0.8f)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    singleLine = true
                )
                Row {
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Row {
                            Text("Trait kind: ", Modifier.align(Alignment.CenterVertically).weight(0.25f))
                            Spacer(Modifier.width(5.dp))
                            Dropdown(
                                items = TraitKind.values().toList(),
                                onSelect = { kind = TraitKind.values()[it] },
                                chosen = kind.ordinal
                            ) { item, selected -> DisabledTextFieldOrText(item.toString(), selected) }
                        }
                        Row {
                            Text("Trait source: ", Modifier.align(Alignment.CenterVertically).weight(0.25f))
                            Spacer(Modifier.width(5.dp))
                            Dropdown(
                                items = TraitSource.values().toList(),
                                onSelect = { source = TraitSource.values()[it] },
                                chosen = source.ordinal
                            ) { item, selected -> DisabledTextFieldOrText(item.toString(), selected) }
                        }
                    }
                }
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    singleLine = false
                )
            }
            Row {
                Button({ editing = false }, Modifier.weight(0.45f)) { Text("Discard edits") }
                Spacer(Modifier.weight(0.1f))
                Button({ editing = false; save(); onExit() }, Modifier.weight(0.45f)) { Text("Save") }
            }
        }
    }
}

@Composable
fun traitDialog(
    trait: Trait?, onExit: () -> Unit, onAdd: (Trait) -> Unit, onMod: (Trait, Trait) -> Unit
) = DefaultDialog(onExit, 600.dp, 400.dp) { traitDialogContent(trait, onExit, onAdd, onMod) }
