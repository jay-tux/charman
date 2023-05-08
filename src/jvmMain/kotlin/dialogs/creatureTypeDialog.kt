package dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.CreatureType
import data.listCreatureTypes
import org.jetbrains.exposed.sql.transactions.transaction

@Composable
fun creatureTypeDialog(
    onExit: () -> Unit,
    onAdd: (CreatureType) -> Unit
) {
    val invalid by remember { mutableStateOf(listCreatureTypes().map{ it.name }) }
    DefaultStringDialog(
        base = null,
        invalid = invalid,
        kind = "creature type",
        onExit = onExit,
        onSave = { _, value ->
            onAdd(transaction { CreatureType.new { this.name = value } })
        }
    )
}