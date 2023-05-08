package dialogs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import data.Ability
import data.listAbilities
import org.jetbrains.exposed.sql.transactions.transaction

@Composable
fun abilityDialog(
    ability: Ability?,
    onExit: () -> Unit,
    onAdd: (Ability) -> Unit,
    onMod: (Ability) -> Unit
) {
    val invalid by remember { mutableStateOf(listAbilities().map { it.name }) }
    DefaultStringDialog(
        base = ability?.name,
        invalid = invalid,
        kind = "ability",
        onExit = onExit,
        onSave = { isNew, value ->
            if(isNew) onAdd(transaction { Ability.new { this.name = value } })
            else onMod(transaction { ability!!.name = value; ability })
        }
    )
}

@Composable
fun addAbilityDialog(onExit: () -> Unit, onAdd: (Ability) -> Unit) = abilityDialog(null, onExit, onAdd) {  }

@Composable
fun updateAbilityDialog(ability: Ability, onExit: () -> Unit, onMod: (Ability) -> Unit) = abilityDialog(ability, onExit, {  }) { onMod(it) }