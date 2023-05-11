package ui

import CMLOut
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import uiData.UIData

@Composable
fun mainUI() = MaterialTheme {
    var showConsole by remember { mutableStateOf(false) }
    val msg by UIData.messages.collectAsState()

    Scaffold(
        bottomBar = {
            BottomAppBar {
                bottomBar(msg, showConsole) { showConsole = it }
            }
        }
    ) {
        Column {
            Box(Modifier.weight(1.0f).fillMaxWidth()) {
                // TODO
            }
            if(showConsole) {
                cmlConsole()
            }
        }
    }
}

@Composable
fun bottomBar(message: String, consoleEnabled: Boolean, consoleClick: (Boolean) -> Unit) = Row {
    Row(Modifier.weight(0.8f)) {
        IconToggleButton(consoleEnabled, consoleClick, Modifier.width(120.dp)) {
            Text("CML Console")
        }
    }

    Text(message, Modifier.weight(0.2f))
}

@Composable
fun ColumnScope.cmlConsole() {
    val messages by CMLOut.stream.collectAsState()
    Surface(
        Modifier.weight(0.3f).fillMaxWidth(),
        color = MaterialTheme.colors.secondary
    ) {
        LazyColumn(Modifier.horizontalScroll(rememberScrollState())) {
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