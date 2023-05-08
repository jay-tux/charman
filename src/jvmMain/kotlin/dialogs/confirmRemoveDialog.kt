package dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ConfirmRemoveDialog(
    name: String,
    extraMsg: String? = null,
    onRemove: () -> Unit,
    requestExit: () -> Unit
) = DefaultDialog(requestExit, 600.dp, Dp.Unspecified) {
    Column(Modifier.padding(8.dp)) {
        Text("Are you sure you want to remove $name?")
        if(extraMsg != null) {
            Text(extraMsg)
        }

        Row {
            Button(
                onClick = { onRemove(); requestExit() },
                modifier = Modifier.weight(0.45f)
            ) { Text("Yes") }
            Spacer(Modifier.weight(0.10f))
            Button(
                onClick = { requestExit() },
                modifier = Modifier.weight(0.45f)
            ) { Text("No") }
        }
    }
}