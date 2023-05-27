package uiData

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cml.DiceVal
import cml.InstanceVal
import data.getString
import ui.widgets.BoldAndNot
import ui.widgets.spellDetails
import withSign

data class DamageKind(val name: String, val inst: InstanceVal)
data class Damage(val dice: DiceVal, val damageKind: DamageKind)

interface Action {
    @Composable
    fun render(c: Character, profTags: List<String>)

    @Composable
    fun renderFull(c: Character, scope: BoxScope, modifier: Modifier)
}

class AttackAction(
    val name: String,
    val stat: AbilityDesc,
    val reachRange: String,
    val targetDesc: String,
    val primary: Damage,
    val secondary: List<Damage>,
    val kind: String,
    val tags: List<String>
): Action {
    @Composable
    override fun render(c: Character, profTags: List<String>) {
        val dmg = remember { "${primary.dice.repr()}${c.abilityMod(stat.instance).withSign()} ${primary.damageKind.name} ${if(secondary.isNotEmpty()) "*" else ""}" }
        val mod = c.abilityMod(stat.instance) + (if(profTags.intersect(tags).isNotEmpty()) c.proficiency() else 0)
        Row {
            Text(name, Modifier.weight(0.225f), fontWeight = FontWeight.Bold)
            Text(reachRange, Modifier.weight(0.175f))
            Text("${mod.withSign()} to hit", Modifier.weight(0.15f))
            Text(dmg, Modifier.weight(0.25f))
            Text(targetDesc, Modifier.weight(0.20f))
        }
    }

    @Composable
    override fun renderFull(c: Character, scope: BoxScope, modifier: Modifier) {
        scope.apply {
            val items by c.inventory
            val item = items.toList().first { it.first.name == name.split('(')[0].trim() }.first
            Column(modifier.fillMaxHeight().fillMaxWidth(0.33f).align(Alignment.CenterEnd).padding(10.dp)) {
                Text(name, style = MaterialTheme.typography.h5)
                // TODO: add magical or not & rarity
                Text("Weapon", style = MaterialTheme.typography.subtitle2, fontStyle = FontStyle.Italic)
                Spacer(Modifier.height(5.dp))
                val prof = if(c.itemProficiencies.value.intersect(tags).isNotEmpty()) "Yes" else "No"
                BoldAndNot("Proficient: ", prof)
                BoldAndNot("Attack Type: ", kind)
                BoldAndNot("Weight: ", "${item.weight} lbs.")
                BoldAndNot("Cost: ", "${item.value.first} ${item.value.second}")
                Spacer(Modifier.height(5.dp))
                Text("Proficiency with a(n) ${item.name} allows you to add your proficiency bonus to the attack roll for any attack you make with it.")
            }
        }
    }
}

class SpellAttackAction(
    val name: String,
    val stat: AbilityDesc,
    val reachRange: String,
    val targetDesc: String,
    val damage: List<Damage>,
    val kind: String,
    val addModifier: Boolean = false,
    val tags: List<String>
): Action {
    @Composable
    override fun render(c: Character, profTags: List<String>) {
        val dmg = remember {
            val tmp = if(addModifier) c.abilityMod(stat.instance).withSign() else ""
            val tmp2 = if(damage.size > 1) " *" else ""

            if(damage.isEmpty()) ""
            else "${damage[0].dice.repr()}$tmp ${damage[0].damageKind.name}$tmp2"
        }
        Row {
            Text(name, Modifier.weight(0.225f), fontWeight = FontWeight.Bold)
            Text(reachRange, Modifier.weight(0.175f))
            Text("${(c.abilityMod(stat.instance) + c.proficiency()).withSign()} to hit", Modifier.weight(0.15f))
            Text(dmg, Modifier.weight(0.25f))
            Text(targetDesc, Modifier.weight(0.20f))
        }
    }

    @Composable
    override fun renderFull(c: Character, scope: BoxScope, modifier: Modifier) {
        scope.spellDetails(c.spells.value.first { it.name == name }, modifier)
    }
}

class SpellDCAction(
    val name: String,
    val stat: AbilityDesc,
    val reachRange: String,
    val targetDesc: String,
    val damage: List<Damage>,
    val kind: String,
    val saveAbility: InstanceVal,
    val tags: List<String>
): Action {
    @Composable
    override fun render(c: Character, profTags: List<String>) {
        val dmg = remember {
            val tmp = if(damage.size > 1) " *" else ""

            if(damage.isEmpty()) ""
            else "${damage[0].dice.repr()} ${damage[0].damageKind.name}$tmp"
        }

        val save = remember {
            saveAbility.getString("abbrev", Character.posRender).fold({ "(Invalid)" }, { it })
        }

        Row {
            Text(name, Modifier.weight(0.225f), fontWeight = FontWeight.Bold)
            Text(reachRange, Modifier.weight(0.175f))
            Row(Modifier.weight(0.15f)) {
                Text("DC ")
                Text("${c.saveDc(stat.instance)} ")
                Text(save)
            }
            Text(dmg, Modifier.weight(0.25f))
            Text(targetDesc, Modifier.weight(0.20f))
        }
    }

    @Composable
    override fun renderFull(c: Character, scope: BoxScope, modifier: Modifier) {
        scope.spellDetails(c.spells.value.first { it.name == name }, modifier)
    }
}