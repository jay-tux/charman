package widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState

@Composable
fun ConfirmRemoveDialog(
    name: String,
    extraMsg: String? = null,
    onRemove: () -> Unit,
    requestExit: () -> Unit
) = Dialog(
    onCloseRequest = { requestExit() },
    state = rememberDialogState(
        position = WindowPosition.PlatformDefault,
        size = DpSize(600.dp, 250.dp)
    )
) {
    Column(Modifier.padding(8.dp)) {
        Text("Are you sure you want to remove $name?")
        if(extraMsg != null) {
            Text(extraMsg)
        }

        Row {
            Button(
                onClick = { onRemove(); requestExit() },
                modifier = Modifier.fillMaxWidth(0.45f)
            ) { Text("Yes") }
            Spacer(Modifier.fillMaxWidth(0.10f / 0.55f))
            Button(
                onClick = { requestExit() },
                modifier = Modifier.fillMaxWidth()
            ) { Text("No") }
        }
    }
}