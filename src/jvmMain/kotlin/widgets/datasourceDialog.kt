package widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import data.DataSource
import data.listDataSources
import data.mkDataSource
import org.jetbrains.exposed.sql.transactions.transaction

@Composable
fun datasourceDialogContent(
    source: DataSource?,
    onExit: () -> Unit,
    onAdd: (DataSource) -> Unit,
    onMod: (DataSource) -> Unit
) {
    val already = listDataSources().map { it.name }
    var name by remember { mutableStateOf(source?.name ?: "") }

    val save = {
        if(source == null) {
            onAdd(mkDataSource(name))
        }
        else {
            onMod(transaction { source.name = name; source })
        }
    }

    Column {
        TextField(name, { name = it}, label = { Text("Data Source Name") }, modifier = Modifier.fillMaxWidth())
        Row {
            Button(
                onClick = { save(); onExit() },
                enabled = !already.contains(name) && name.isNotBlank(),
                modifier = Modifier.weight(0.45f)
            ) {
                Text(if(source == null) "Add data source" else "Update data source")
            }

            Spacer(modifier = Modifier.weight(0.1f))

            Button(
                onClick = { onExit() },
                modifier = Modifier.weight(0.45f)
            ) {
                Text("Cancel")
            }
        }
    }
}

@Composable
fun datasourceDialog(
    source: DataSource?,
    onExit: () -> Unit,
    onAdd: (DataSource) -> Unit,
    onMod: (DataSource) -> Unit
) = Dialog(
    onCloseRequest = { onExit() },
    state = rememberDialogState(
        position = WindowPosition(alignment = Alignment.Center),
        size = DpSize(500.dp, 150.dp)
    )
) {
    Box(Modifier.padding(5.dp)) {
        datasourceDialogContent(source, onExit, onAdd, onMod)
    }
}

@Composable
fun addDatasourceDialog(onExit: () -> Unit, onAdd: (DataSource) -> Unit) = datasourceDialog(null, onExit, onAdd) { }

@Composable
fun updateDatasourceDialog(source: DataSource, onExit: () -> Unit, onMod: (DataSource) -> Unit) = datasourceDialog(source, onExit, { }) { onMod(it) }