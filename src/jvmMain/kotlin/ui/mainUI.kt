package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose
import ui.dialogs.CharacterCreationDialog
import ui.views.CharacterView
import ui.views.CharacterViewError
import ui.widgets.*
import uiData.CharacterData
import uiData.UIData

enum class BottomPart(val text: String) {
    NONE(""), CONSOLE("CML Console"), TYPE_LIST("CML Type List"), INITIATIVE("Initiative Order")
}

@Composable
fun mainUI() = MaterialTheme {
    var currentBottom by remember { mutableStateOf(BottomPart.NONE) }
    val msg by UIData.messages

    val toggle = { v: BottomPart ->
        currentBottom = if(currentBottom == v) BottomPart.NONE else v
    }

    Column {
        Box(
            Modifier.weight(if(currentBottom != BottomPart.NONE) 0.76f else 0.96f).fillMaxWidth().padding(5.dp)
        ) {
            body()
        }
        if(currentBottom != BottomPart.NONE) {
            Box(Modifier.weight(0.20f).padding(top = 10.dp, start = 10.dp)) {
                when (currentBottom) {
                    BottomPart.CONSOLE -> CmlConsole()
                    BottomPart.TYPE_LIST -> TypeList()
                    BottomPart.INITIATIVE -> InitiativeWidget()
                    else -> {}
                }
            }
        }
        Box(Modifier.weight(0.04f)) {
            bottomBar(msg, currentBottom) { toggle(it) }
        }
    }
}

@Composable
fun bottomBar(message: String, enabled: BottomPart, onToggle: (BottomPart) -> Unit) = Row(Modifier.background(MaterialTheme.colors.primary)) {
    LazyRow(Modifier.weight(0.8f)) {
        items(BottomPart.values().toList().filter{ it != BottomPart.NONE }) {
            Button(
                { onToggle(it) },
                modifier = Modifier.width(250.dp).fillMaxHeight(),
                colors = ButtonDefaults.buttonColors(backgroundColor = if(enabled == it) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primary)
            ) {
                Text(it.text)
            }
            Spacer(Modifier.width(15.dp))
        }
    }
    Spacer(Modifier.width(15.dp))
    Text(message, Modifier.weight(0.2f))
}

@Composable
fun body() {
    val values by CharacterData.characters
    val mapped by CharacterData.loadedCharacters
    var current by remember { mutableStateOf(-1) }
    var showCreate by remember { mutableStateOf(false) }

    if(current >= mapped.size) current = -1

    Row {
        Column(Modifier.weight(0.2f).fillMaxHeight().background(MaterialTheme.colors.primary)) {
            LazyScrollColumn(
                Modifier.padding(top = 7.dp, start = 7.dp, bottom = 7.dp).weight(0.95f)
            ) {
                itemsIndexed(items = mapped) { index, value ->
                    CharacterCard(
                        values[index].fold({ it.first }, { it.type.name }),
                        value,
                        index,
                        { current = if (current == it) -1 else it },
                        current == index,
                        Modifier.fillMaxWidth()
                    )
                }
            }
            Box(Modifier.weight(0.05f).padding(7.dp)) {
               Button({ showCreate = true }, Modifier.align(Alignment.Center).fillMaxWidth()) {
                   Text("Create character")
               }
            }
        }

        Box(Modifier.weight(0.8f).fillMaxHeight()) {
            if(current == -1) {
                Text(
                    "Select a character to see their details.",
                    Modifier.align(Alignment.Center),
                    color = MaterialTheme.colors.primaryVariant
                )
            }
            else {
                mapped[current].compose({ CharacterViewError(it) }, { CharacterView(it) })
            }
        }
    }

    if(showCreate) {
        CharacterCreationDialog { showCreate = false }
    }
}