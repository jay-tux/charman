package dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.ItemTag
import data.listDataSources
import org.jetbrains.exposed.sql.transactions.transaction

@Composable
fun itemTagDialogContent(
    tag: ItemTag?,
    onExit: () -> Unit,
    onAdd: (ItemTag) -> Unit,
    onMod: (ItemTag) -> Unit
) {
    val already = listDataSources().map { it.name }
    var name by remember { mutableStateOf(tag?.name ?: "") }

    val save = {
        if(tag == null) {
            onAdd(transaction { ItemTag.new { this.name = name } })
        }
        else {
            onMod(transaction { tag.name = name; tag })
        }
    }

    Column {
        TextField(name, { name = it}, label = { Text("Tag") }, modifier = Modifier.fillMaxWidth())
        Row {
            Button(
                onClick = { save(); onExit() },
                enabled = !already.contains(name) && name.isNotBlank(),
                modifier = Modifier.weight(0.45f)
            ) {
                Text(if(tag == null) "Add tag" else "Update tag")
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
fun itemTagDialog(
    source: ItemTag?,
    onExit: () -> Unit,
    onAdd: (ItemTag) -> Unit,
    onMod: (ItemTag) -> Unit
) = DefaultDialog(onExit, 500.dp, 200.dp) {
    Box(Modifier.padding(5.dp)) {
        itemTagDialogContent(source, onExit, onAdd, onMod)
    }
}

@Composable
fun addItemTagDialog(onExit: () -> Unit, onAdd: (ItemTag) -> Unit) = itemTagDialog(null, onExit, onAdd) { }

@Composable
fun updateItemTagDialog(tag: ItemTag, onExit: () -> Unit, onMod: (ItemTag) -> Unit) = itemTagDialog(tag, onExit, { }) { onMod(it) }