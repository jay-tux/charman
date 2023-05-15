package ui

import CMLOut
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import ui.widgets.InitiativeWidget
import ui.widgets.LazyScrollColumn
import uiData.UIData

enum class BottomPart(val text: String) {
    NONE(""), CONSOLE("CML Console"), INITIATIVE("Initiative Order")
}

@Composable
fun mainUI() = MaterialTheme {
    var currentBottom by remember { mutableStateOf(BottomPart.NONE) }
    val msg by UIData.messages.collectAsState()

    val toggle = { v: BottomPart ->
        currentBottom = if(currentBottom == v) BottomPart.NONE else v
    }

    Column {
        Box(Modifier.weight(if(currentBottom != BottomPart.NONE) 0.75f else 0.97f).fillMaxWidth()) {
            // TODO
        }
        if(currentBottom != BottomPart.NONE) {
            Box(Modifier.weight(0.22f).padding(top = 10.dp, start = 10.dp)) {
                when (currentBottom) {
                    BottomPart.CONSOLE -> cmlConsole()
                    BottomPart.INITIATIVE -> InitiativeWidget()
                    else -> {}
                }
            }
        }
        Box(Modifier.weight(0.03f)) {
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
fun cmlConsole() {
    val messages by CMLOut.stream.collectAsState()
    Surface(
        Modifier.fillMaxWidth().fillMaxHeight(),
        color = MaterialTheme.colors.secondary
    ) {
        LazyScrollColumn(modContaining = Modifier.horizontalScroll(rememberScrollState())) {
            items(messages) { (k, m) ->
                Text(
                    m,
                    color = when (k) {
                        CMLOut.MessageKind.INFO -> Color.Blue
                        CMLOut.MessageKind.WARNING -> Color.Yellow
                        CMLOut.MessageKind.ERROR -> Color.Red
                    },
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}