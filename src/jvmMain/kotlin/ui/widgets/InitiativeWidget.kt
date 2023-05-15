package ui.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import uiData.InitiativeData
import updateGet

@Composable
fun InitiativeWidget() {
    val initiatives by InitiativeData.state
    val currentInitiative by InitiativeData.index
    var showAdd by remember { mutableStateOf(false) }
    var showBulkAdd by remember { mutableStateOf(false) }

    Row {
        LazyRow(Modifier.weight(0.9f)) {
            itemsIndexed(initiatives) { index, (name, _) ->
                Button({ InitiativeData.nextTurn() }, enabled = index == currentInitiative) {
                    Text(if(index == currentInitiative) "$name\n" else name)
                    IconButton({
                        InitiativeData.removeInitiative(index)
                    }) {
                        Icon(Icons.Default.Remove, contentDescription = "Remove participant from combat")
                    }
                }
                Spacer(Modifier.width(15.dp))
            }
        }
        Column {
            IconButton({ showAdd = true }) {
                Icon(Icons.Default.PersonAdd, contentDescription = "Add a participant")
            }
            IconButton({ showBulkAdd = true }) {
                Icon(Icons.Default.GroupAdd, contentDescription = "Bulk add participants")
            }
        }
    }

    if(showAdd) {
        InitiativeDialog({ showAdd = false }) { (name, init) ->
            InitiativeData.addInitiative(name, init)
        }
    }
    if(showBulkAdd) {
        InitiativeBulkDialog({ showBulkAdd = false }) { add ->
            InitiativeData.addAllInitiative(add)
        }
    }
}

@Composable
fun InitiativeDialog(onExit: () -> Unit, onAdd: (Pair<String, Int>) -> Unit) = DefaultDialog(onExit, 400.dp, 300.dp) {
    var name by remember { mutableStateOf("") }
    var initiative by remember { mutableStateOf(0) }
    Column(Modifier.align(Alignment.Center)) {
        OutlinedTextField(
            name,
            { name = it },
            Modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text("Participant Name") }
        )
        Spacer(Modifier.height(7.dp))
        OutlinedTextField(
            if(initiative == 0) "" else "$initiative",
            { initiative = it.toIntOrNull() ?: 0 },
            Modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text("Participant Initiative") }
        )
        Spacer(Modifier.height(7.dp))
        YesNoButton(yes = "Add $name", yesEnabled = name.isNotEmpty(), no = "Cancel", onNo = onExit) {
            onAdd(Pair(name, initiative))
            onExit()
        }
    }
}

@Composable
fun InitiativeBulkDialog(onExit: () -> Unit, onAdd: (List<Pair<String, Int>>) -> Unit) = DefaultDialog(onExit, 400.dp, 500.dp) {
    var values by remember { mutableStateOf(listOf(Pair("", 0), Pair("", 0))) }
    Column(Modifier.align(Alignment.Center)) {
        LazyColumn(Modifier.weight(0.8f)) {
            itemsIndexed(values) { index, (name, init) ->
                var currName by remember { mutableStateOf(name) }
                var currInit by remember { mutableStateOf(init) }
                Row {
                    OutlinedTextField(
                        currName,
                        { currName = it },
                        Modifier.weight(0.45f).onFocusChanged { if(it.hasFocus && index == values.size - 1) values += Pair("", 0) },
                        singleLine = true,
                        label = { Text("Participant Name") }
                    )
                    Spacer(Modifier.weight(0.05f))
                    OutlinedTextField(
                        if(currInit == 0) "" else "$currInit",
                        { currInit = it.toIntOrNull() ?: 0 },
                        Modifier.weight(0.45f).onFocusChanged { if(!it.hasFocus) values = values.updateGet(index, Pair(currName, currInit)) },
                        singleLine = true,
                        label = { Text("Initiative") }
                    )
                }
            }
        }
        Spacer(Modifier.weight(0.05f))
        Box(Modifier.weight(0.15f)) {
            YesNoButton(yes = "Add all", yesEnabled = values.isNotEmpty(), no = "Cancel", onNo = onExit) {
                onAdd(values.filter { it.first != "" })
                onExit()
            }
        }
    }
}