package widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.DataSource
import org.jetbrains.exposed.sql.transactions.transaction

@Composable
fun datasourceWidget(ds: DataSource, onRemove: () -> Unit, onModified: (DataSource) -> Unit) = Column {
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
        updateDatasourceDialog(ds, { showModify = false }, onModified)
    }

    if(showRemove) {
        ConfirmRemoveDialog(
            ds.name,
            "This will also remove all races, classes, backgrounds, and items related to it.",
            onRemove
        ) { showRemove = false }
    }
}