package ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cardinal
import uiData.SpellDesc

@Composable
fun SpellCard(spell: SpellDesc, modifier: Modifier = Modifier, getCharges: (String) -> Pair<Int, Int>?, useCharge: (String, Int) -> Unit, onClick: () -> Unit) {
    Column {
        Row(modifier.clickable { onClick() }) {
            Spacer(Modifier.weight(0.03f))
            Column(Modifier.weight(0.32f)) {
                Text(spell.name, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(spell.source, fontStyle = FontStyle.Italic)
            }
                    Text(spell.castingTime, Modifier.weight(0.20f).align(Alignment.CenterVertically))
                    Text(spell.duration, Modifier.weight(0.25f).align(Alignment.CenterVertically))
            Column(Modifier.weight(0.2f).align(Alignment.CenterVertically)) {
                Text(
                    spell.components.joinToString(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                val ch = spell.charge?.let { getCharges(it.first) }
                if(ch != null) {
                    Box(Modifier.fillMaxWidth()) {
                        Row(Modifier.align(Alignment.CenterEnd)) {
                            Text("Charges: ", fontStyle = FontStyle.Italic)
                            ChargesWidget(ch) { useCharge(spell.charge.first, spell.charge.second) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChargesWidget(pair: Pair<Int, Int>, onClick: () -> Unit) = SpellSlots(pair.second, pair.first) { onClick() }

@Composable
fun SpellSlots(amount: Int, used: Int, modifier: Modifier = Modifier, overset: (@Composable () -> Unit)? = null, onClick: () -> Unit) {
    Row(modifier.clickable { onClick() }) {
        for(i in 1..used) {
            Box {
                Icon(Icons.Default.RadioButtonChecked, "")
                if(overset != null) {
                    Box(Modifier.align(Alignment.Center)) {
                        overset()
                    }
                }
            }
        }
        for(i in used until amount) {
            Box {
                Icon(Icons.Default.RadioButtonUnchecked, "")
                if(overset != null) {
                    Box(Modifier.align(Alignment.Center)) {
                        overset()
                    }
                }
            }
        }
    }
}

@Composable
fun BoxScope.spellDetails(spell: SpellDesc, modifier: Modifier) {
    Column(modifier.fillMaxHeight().fillMaxWidth(0.33f).align(Alignment.CenterEnd).padding(10.dp)) {
        Text(spell.name, style = MaterialTheme.typography.h5)
        if(spell.level == 0) {
            Text("${spell.school} cantrip", style = MaterialTheme.typography.subtitle2, fontStyle = FontStyle.Italic)
        }
        else {
            Text("${spell.level.cardinal()}-level ${spell.school}", style = MaterialTheme.typography.subtitle2, fontStyle = FontStyle.Italic)
        }
        Spacer(Modifier.height(5.dp))
        BoldAndNot("Casting Time: ", spell.castingTime)
        BoldAndNot("Range:", spell.range)
        BoldAndNot("Components: ", spell.components.joinToString(", "))
        BoldAndNot("Duration: ", spell.duration)
        Spacer(Modifier.height(5.dp))
        Text(spell.desc)
    }
}

@Composable
fun BoxScope.noDetails(name: String, modifier: Modifier) {
    Column(modifier.fillMaxHeight().align(Alignment.CenterEnd).padding(10.dp)) {
        Text("Spell or action `$name' has no details.")
    }
}