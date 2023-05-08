package widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.DnDClass
import org.jetbrains.exposed.sql.transactions.transaction

@Composable
fun classWidget(c: DnDClass, onRemove: () -> Unit, onModifier: (DnDClass) -> Unit) = Column {
    var showModify by remember { mutableStateOf(false) }
    var showRemove by remember { mutableStateOf(false) }

    val saveProf by remember { mutableStateOf(transaction{ c.savingThrowProficiencies.toList() }) }
    val itemProf by remember { mutableStateOf(transaction{ c.itemProficiencies.toList() })}
    val skillOptions by remember { mutableStateOf(transaction{ c.skillOptions.toList() })}
    val choices by remember { mutableStateOf(transaction { c.choices.toList() }) }
    val traits by remember { mutableStateOf(transaction { c.traits })}
    val spellcasting by remember { mutableStateOf(transaction { c.spellcasting })}

    expandableButton(
        { Text(c.name, style = MaterialTheme.typography.h5) },
        {
            Row {
                IconButton({ showModify = true }) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit this class")
                }
                IconButton({ showRemove = true }) {
                    Icon(Icons.Filled.Close, contentDescription = "Delete this class")
                }
            }
        }
    ) {
        Row {
            Column(Modifier.weight(0.45f)) {
                Row {
                    Text("Hit Dice: ")
                    Text("${c.hitDiceType.n(1)} per ${c.name} level")
                }
                Row {
                    Text("Hit Points at 1st level: ")
                    Text("${c.hitDiceType.name.substring(1)} + your Constitution modifier")
                }
                Row {
                    Text("Hit Points at Higher Levels: ")
                    Text("${c.hitDiceType.n(1)} (or ${c.hitDiceType.average}) + your Constitution modifier")
                }
            }

            Spacer(Modifier.weight(0.1f))

            Column(Modifier.weight(0.45f)) {
                Row {
                    Text("Saving Throw Proficiencies: ")
                    Text(saveProf.joinToString(", ") { it.name })
                }
                Row {
                    Text("Item Proficiencies: ")
                    Text(itemProf.joinToString(", ") { it.name })
                }
                Row {
                    Text("Skill Proficiencies: ")
                    Text("Choose ${c.chooseSkillCount} from ${skillOptions.joinToString(", ") { it.name }}")
                }
            }
        }
    }
}