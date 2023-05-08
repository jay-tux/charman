package dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import capitalizeFirst

@Composable
fun DefaultDialog(
    onExit: () -> Unit,
    width: Dp,
    height: Dp,
    content: @Composable BoxScope.() -> Unit
) = Dialog(
    onCloseRequest = { onExit() },
    state = rememberDialogState(
        position = WindowPosition(Alignment.Center),
        size = DpSize(width, height)
    )
) {
    Box(Modifier.padding(15.dp)) {
        content()
    }
}

@Composable
fun DefaultStringDialog(
    base: String?,
    invalid: List<String>,
    kind: String,
    name: String = "Name",
    onExit: () -> Unit,
    onSave: (isNew: Boolean, value: String) -> Unit,
) = DefaultDialog(
    onExit = onExit,
    width = 500.dp,
    height = 200.dp
) {
    var content by remember { mutableStateOf(base ?: "") }
    Column {
        TextField(content, { content = it }, label = { Text("${kind.capitalizeFirst()} ${name.capitalizeFirst()}") }, modifier = Modifier.fillMaxWidth())
        Row {
            Button(
                onClick = { onSave(base == null, content); onExit() },
                enabled = !invalid.contains(content),
                modifier = Modifier.weight(0.45f)
            ) { Text(if(base == null) "Add $kind" else "Update $kind") }

            Spacer(Modifier.weight(0.1f))

            Button(
                onClick = { onExit() },
                modifier = Modifier.weight(0.45f)
            ) {
                Text("Cancel")
            }
        }
    }
}