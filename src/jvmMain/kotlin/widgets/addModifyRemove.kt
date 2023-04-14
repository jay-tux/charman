package widgets

import addGet
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState
import androidx.compose.ui.window.rememberDialogState
import removeGet

@Composable
fun <T> addModifyRemoveStringWidget(
    values: List<T>,
    toText: (T) -> String = { it.toString() },
    onAdd: (String) -> T?,
    onRemove: (Int) -> Boolean,
    onModify: (Int, String) -> Boolean
) = Column {
    var data by remember { mutableStateOf(values) }
    var modifying by remember { mutableStateOf(-1) }
    var currentlyModifying by remember { mutableStateOf("") }
    var adding by remember { mutableStateOf("") }
    val nameWeight = 0.7f
    val buttonsWeight = 0.3f

    val enterModify = { idx: Int ->
        modifying = idx
        currentlyModifying = toText(data[idx])
    }

    val saveModify = { idx: Int ->
        if(onModify(idx, currentlyModifying)) modifying = -1
    }

    val cancelModify = {
        modifying = -1
    }

    val delete = { idx: Int ->
        if(onRemove(idx)) data = data.toMutableList().removeGet(idx)
    }

    val add = {
        val res = onAdd(adding)
        if(res != null) {
            adding = ""
            data = data.toMutableList().addGet(res)
        }
    }

    Column {
        LazyColumn {
            itemsIndexed(data) { idx, it ->
                Row {
                    if (modifying == idx) {
                        OutlinedTextField(
                            currentlyModifying,
                            { currentlyModifying = it },
                            Modifier.weight(nameWeight)
                        )
                    } else {
                        Text(toText(data[idx]), Modifier.weight(nameWeight))
                    }

                    Row(Modifier.weight(buttonsWeight)) {
                        if (modifying == idx) {
                            IconButton(onClick = { saveModify(idx) }) {
                                Icon(Icons.Filled.Check, contentDescription = "Save this item")
                            }
                            IconButton(onClick = { cancelModify() }) {
                                Icon(Icons.Filled.Close, contentDescription = "Cancel editing")
                            }
                        } else {
                            IconButton(onClick = { enterModify(idx) }, enabled = modifying == -1) {
                                Icon(Icons.Filled.Edit, contentDescription = "Edit this item")
                            }
                            IconButton(onClick = { delete(idx) }, enabled = modifying == -1) {
                                Icon(Icons.Filled.Delete, contentDescription = "Remove this item")
                            }
                        }
                    }
                }
            }

            item {
                Row {
                    OutlinedTextField(
                        adding,
                        { adding = it },
                        Modifier.weight(nameWeight)
                    )
                    Row(Modifier.weight(buttonsWeight)) {
                        IconButton(onClick = { add() }) {
                            Icon(Icons.Filled.Add, contentDescription = "Add a new item")
                        }
                        IconButton(onClick = { adding = "" }) {
                            Icon(Icons.Filled.Close, contentDescription = "Cancel adding")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun <T> addModifyRemoveStringDialog(
    values: List<T>,
    toText: (T) -> String = { it.toString() },
    onAdd: (String) -> T?,
    onRemove: (Int) -> Boolean,
    onModify: (Int, String) -> Boolean,
    onQuit: () -> Unit,
    modifier: Modifier = Modifier,
    state: DialogState = rememberDialogState()
) = Dialog(onCloseRequest = { onQuit() }, state = state) {
    Surface(modifier = modifier) {
        Column {
            addModifyRemoveStringWidget(values, toText, onAdd, onRemove, onModify)
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = { onQuit() }, Modifier.align(Alignment.CenterHorizontally)) {
                Text("Done")
            }
        }
    }
}