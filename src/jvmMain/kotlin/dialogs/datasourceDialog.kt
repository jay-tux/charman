package dialogs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import data.DataSource
import data.listDataSources
import org.jetbrains.exposed.sql.transactions.transaction

@Composable
fun datasourceDialog(
    source: DataSource?,
    onExit: () -> Unit,
    onAdd: (DataSource) -> Unit,
    onMod: (DataSource) -> Unit
) {
    val invalid by remember { mutableStateOf(listDataSources().map{ it.name }) }
    DefaultStringDialog(
        base = source?.name,
        invalid = invalid,
        kind = "data source",
        onExit = onExit,
        onSave = { isNew, value ->
            if(isNew) onAdd(transaction { DataSource.new { this.name = value } })
            else onMod(transaction { source!!.name = value; source })
        }
    )
}

@Composable
fun addDatasourceDialog(onExit: () -> Unit, onAdd: (DataSource) -> Unit) = datasourceDialog(null, onExit, onAdd) { }

@Composable
fun updateDatasourceDialog(source: DataSource, onExit: () -> Unit, onMod: (DataSource) -> Unit) = datasourceDialog(source, onExit, { }) { onMod(it) }