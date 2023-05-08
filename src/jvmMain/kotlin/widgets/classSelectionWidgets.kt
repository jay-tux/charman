package widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.*
import dialogs.*

@Composable
fun itemTagWidget(
    selected: List<ItemTag>,
    onSelect: (ItemTag) -> Unit,
    onDeselect: (ItemTag) -> Unit,
    onUpdateTag: () -> Unit,
    modifier: Modifier = Modifier
) = selectionWidget(
    selected = selected,
    fetcher = { listTags() },
    onSelect = onSelect,
    onDeselect = onDeselect,
    onUpdateSingle = onUpdateTag,
    onRequestAdd = { onExit, onAdd -> addItemTagDialog(onExit, onAdd) },
    onRequestMod = { tag, onExit, onMod -> updateItemTagDialog(tag, onExit, onMod) },
    modifier = modifier
) { tag, mod -> Text(tag.name, modifier = mod.align(Alignment.CenterVertically)) }

@Composable
fun abilityWidget(
    selected: List<Ability>,
    onSelect: (Ability) -> Unit,
    onDeselect: (Ability) -> Unit,
    onUpdateAbility: () -> Unit,
    modifier: Modifier = Modifier
) = selectionWidget(
    selected = selected,
    fetcher = { listAbilities() },
    onSelect = onSelect,
    onDeselect = onDeselect,
    onUpdateSingle = onUpdateAbility,
    onRequestAdd = { onExit, onAdd -> addAbilityDialog(onExit, onAdd) },
    onRequestMod = { ability, onExit, onMod -> updateAbilityDialog(ability, onExit, onMod) },
    modifier = modifier
) { ability, mod -> Text(ability.name, modifier = mod.align(Alignment.CenterVertically)) }

@Composable
fun skillWidget(
    selected: List<Skill>,
    choose: Int,
    onSelect: (Skill) -> Unit,
    onDeselect: (Skill) -> Unit,
    onChooseChange: (Int) -> Unit,
    onUpdateSkill: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Row {
            IconButton({ onChooseChange(choose - 1) }) {
                Icon(
                    Icons.Default.Remove,
                    contentDescription = null
                )
            }
            OutlinedTextField(
                value = "Choose $choose skills from among the selected skills below:",
                onValueChange = {},
                modifier = Modifier,
                enabled = false
            )
            IconButton({ onChooseChange(choose + 1) }) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
        Spacer(Modifier.height(5.dp))
        selectionWidget(
            selected = selected,
            fetcher = { listSkills() },
            onSelect = onSelect,
            onDeselect = onDeselect,
            onUpdateSingle = onUpdateSkill,
            onRequestAdd = { onExit, onAdd -> addSkillDialog(onExit, onAdd) },
            onRequestMod = { skill, onExit, onMod -> updateSkillDialog(skill, onExit, onMod) },
            modifier = modifier
        ) { ability, mod -> Text(ability.name, modifier = mod.align(Alignment.CenterVertically)) }
    }
}