package widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import data.DataSource
import org.jetbrains.exposed.sql.transactions.transaction

@Composable
fun datasourceWidget(ds: DataSource, onRemove: () -> Unit) = Column {
    var showModify by remember { mutableStateOf(false) }
    var showRemove by remember { mutableStateOf(false) }
    var copy by remember { mutableStateOf(ds) }

    val sizes = transaction {
        listOf(
            ds.races.count(),
            ds.classes.count(),
            ds.backgrounds.count(),
            ds.items.count()
        )
    }

    Column {
        expandableButton(
            { Text(ds.name, style = MaterialTheme.typography.h5) },
            {
                Row {
                    IconButton({ showModify = true }) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit this data source")
                    }
                    IconButton({ showRemove = true }) {
                        Icon(Icons.Filled.Close, contentDescription = "Delete this data source")
                    }
                }
            }
        ) {
            Row {
                Spacer(Modifier.width(25.dp))
                Column {
                    Text("${sizes[0]} race(s)", style = MaterialTheme.typography.body1)
                    Text("${sizes[1]} class(es)", style = MaterialTheme.typography.body1)
                    Text("${sizes[2]} background(s)", style = MaterialTheme.typography.body1)
                    Text("${sizes[3]} item(s)", style = MaterialTheme.typography.body1)
                }
            }
        }
    }

    if(showModify) {
        datasourceModifyWidget(ds, { transaction { ds.refresh() }; copy = ds }) { showModify = false }
    }

    if(showRemove) {
        ConfirmRemoveDialog(
            ds.name,
            "This will also remove all races, classes, backgrounds, and items related to it.",
            onRemove
        ) { showRemove = false }
//        confirmRemoveDatasourceDialog(ds, { onRemove() }) { showRemove = false }
    }
}

@Composable
fun datasourceModifyWidget(ds: DataSource, onUpdate: () -> Unit, requestExit: () -> Unit) = Dialog(
    onCloseRequest = { requestExit() },
    state = rememberDialogState(
        position = WindowPosition.Aligned(Alignment.Center),
        size = DpSize(500.dp, 350.dp)
    )
) {
    var name by remember { mutableStateOf(ds.name) }

    val save = {
        transaction {
            ds.name = name
        }
        onUpdate()
        requestExit()
    }

    Column(Modifier.fillMaxSize(0.5f)) {
        OutlinedTextField(name, { name = it }, label = { Text("Data source name") })
        Row {
            Button(onClick = { save(); requestExit() }) { Text("Update") }
            Column {
                Button(onClick = { requestExit() }, Modifier.align(Alignment.End)) {
                    Text("Cancel")
                }
            }
        }
    }
}

@Composable
fun confirmRemoveDatasourceDialog(ds: DataSource, onRemove: () -> Unit, requestExit: () -> Unit) = Dialog(
    onCloseRequest = { requestExit() },
    state = rememberDialogState(
        position = WindowPosition.PlatformDefault,
        size = DpSize(500.dp, 250.dp)
    )
) {
    Column(Modifier.fillMaxSize(0.5f)) {
        Text("Confirm removal of data source ${ds.name} and all associated data?")
        Row {
            Button(
                onClick = { onRemove(); requestExit() },
                modifier = Modifier.fillMaxWidth(0.45f)
            ) { Text("Yes") }
            Spacer(Modifier.fillMaxWidth(0.10f))
            Button(
                onClick = { requestExit() },
                modifier = Modifier.fillMaxWidth(0.45f)
            ) { Text("No") }
        }
    }
}