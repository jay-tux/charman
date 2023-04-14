package widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import data.CreatureType
import org.jetbrains.exposed.sql.transactions.transaction

@Composable
fun creatureTypeDialogContent(
    onExit: () -> Unit,
    onAdd: (CreatureType) -> Unit
) {
    var name by remember { mutableStateOf("") }

    val save = {
        onAdd(transaction {
            CreatureType.new {
                this.name = name
            }
        })
    }

    Column {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it }
        )
        Row {
            Button({ onExit() }, Modifier.fillMaxWidth(0.45f)) {
                Text("Cancel")
            }
            Spacer(Modifier.fillMaxWidth(0.1f))
            Button({ save(); onExit() }, Modifier.fillMaxWidth(0.45f)) {
               Text("Save")
           }
        }
    }
}

@Composable
fun creatureTypeDialog(onExit: () -> Unit, onAdd: (CreatureType) -> Unit) = Dialog(
    onCloseRequest = { onExit() },
    state = rememberDialogState(
        position = WindowPosition.PlatformDefault,
        size = DpSize(600.dp, 400.dp)
    )
) {
    Box(Modifier.padding(15.dp)) {
        creatureTypeDialogContent(onExit, onAdd)
    }
}