package widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import data.listDataSources
import data.mkDataSource

@Composable
fun addSourceView(requestExit: () -> Unit) {
    val already = listDataSources().map { it.name }
    var name by remember { mutableStateOf("") }
    Column {
        TextField(name, { name = it}, placeholder = { Text("Data Source Name") })
        Row {
            Button(
                onClick = { mkDataSource(name); requestExit() },
                enabled = !already.contains(name),
                modifier = Modifier.fillMaxWidth(0.45f)
            ) {
                Text("Add data source")
            }

            Spacer(modifier = Modifier.fillMaxWidth(0.1f))

            Button(
                onClick = { requestExit() },
                modifier = Modifier.fillMaxWidth(0.45f)
            ) {
                Text("Cancel")
            }
        }
    }
}

@Composable
fun addDatasourceDialog(requestExit: () -> Unit) = Dialog(
    onCloseRequest = { requestExit() },
    state = rememberDialogState(
        position = WindowPosition.PlatformDefault,
        size = DpSize(400.dp, 200.dp)
    )
) {
    Box(Modifier.padding(5.dp)) {
        addSourceView { requestExit() }
    }
}