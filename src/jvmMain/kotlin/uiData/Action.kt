package uiData

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import cml.DiceVal
import cml.InstanceVal
import data.getString
import withSign

data class DamageKind(val name: String, val inst: InstanceVal)
data class Damage(val dice: DiceVal, val damageKind: DamageKind)

interface Action {
    @Composable
    fun render(c: Character)

    @Composable
    fun renderFull(c: Character)
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
    // TODO: proficiency?

    @Composable
    override fun render(c: Character) {
        val dmg = remember { "${primary.dice.repr()}${c.abilityMod(stat.instance).withSign()} ${primary.damageKind.name} ${if(secondary.isNotEmpty()) "*" else ""}" }
        Row {
            Text(name, Modifier.weight(0.225f), fontWeight = FontWeight.Bold)
            Text(reachRange, Modifier.weight(0.175f))
            Text("${c.abilityMod(stat.instance).withSign()} to hit", Modifier.weight(0.15f))
            Text(dmg, Modifier.weight(0.25f))
            Text(targetDesc, Modifier.weight(0.20f))
        }
    }

    @Composable
    override fun renderFull(c: Character) {
        TODO("Not yet implemented")
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
    // TODO: proficiency?

    @Composable
    override fun render(c: Character) {
        val dmg = remember {
            val tmp = if(addModifier) c.abilityMod(stat.instance).withSign() else ""
            val tmp2 = if(damage.size > 1) " *" else ""

            if(damage.isEmpty()) ""
            else "${damage[0].dice.repr()}$tmp ${damage[0].damageKind.name}$tmp2"
        }
        Row {
            Text(name, Modifier.weight(0.225f), fontWeight = FontWeight.Bold)
            Text(reachRange, Modifier.weight(0.175f))
            Text("${c.abilityMod(stat.instance).withSign()} to hit", Modifier.weight(0.15f))
            Text(dmg, Modifier.weight(0.25f))
            Text(targetDesc, Modifier.weight(0.20f))
        }
    }

    @Composable
    override fun renderFull(c: Character) {
        TODO("Not yet implemented")
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
    // TODO: proficiency?

    @Composable
    override fun render(c: Character) {
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
    override fun renderFull(c: Character) {
        TODO("Not yet implemented")
    }
}