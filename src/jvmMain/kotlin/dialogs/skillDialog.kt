package dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.Skill
import data.listAbilities
import data.listSkills
import org.jetbrains.exposed.sql.transactions.transaction
import widgets.DisabledTextFieldOrText
import widgets.Dropdown

@Composable
fun skillDialog(
    skill: Skill?,
    onExit: () -> Unit,
    onAdd: (Skill) -> Unit,
    onMod: (Skill) -> Unit
) = DefaultDialog(
    onExit = onExit,
    width = 500.dp,
    height = 200.dp
) {
    var name by remember { mutableStateOf(skill?.name ?: "") }
    val invalid by remember { mutableStateOf(listSkills().map { it.name }) }
    val abilities by remember { mutableStateOf(listAbilities()) }
    var ability by remember { mutableStateOf(transaction { skill?.ability } ?: abilities[0]) }

    val onSave = {
        if (skill == null)
            onAdd(transaction { Skill.new { this.name = name; this.ability = ability } })
        else
            onMod(transaction { skill.name = name; skill.ability = ability; skill })
    }

    Column {
        TextField(name, { name = it }, label = { Text("Skill Name") }, modifier = Modifier.fillMaxWidth())
        Dropdown(
            abilities,
            { ability = abilities[it] },
            chosen = 0
        ) { selection, selected ->
            DisabledTextFieldOrText(selection.name, selected)
        }
        Row {
            Button(
                onClick = { onSave(); onExit() },
                enabled = !invalid.contains(name),
                modifier = Modifier.weight(0.45f)
            ) { Text(if(skill == null) "Add skill" else "Update skill") }

            Spacer(Modifier.weight(0.1f))

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
fun addSkillDialog(onExit: () -> Unit, onAdd: (Skill) -> Unit) = skillDialog(null, onExit, onAdd) {  }

@Composable
fun updateSkillDialog(skill: Skill, onExit: () -> Unit, onMod: (Skill) -> Unit) = skillDialog(skill, onExit, {  }) { onMod(it) }