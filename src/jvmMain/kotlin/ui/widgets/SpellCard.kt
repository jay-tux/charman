package ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Icon
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
import uiData.SpellDesc

@Composable
fun SpellCard(spell: SpellDesc, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(Modifier.clickable { onClick() }) {
        Spacer(Modifier.weight(0.03f))
        Column(Modifier.weight(0.32f)) {
            Text(spell.name, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(spell.source, fontStyle = FontStyle.Italic)
        }
        Text(spell.castingTime, Modifier.weight(0.20f).align(Alignment.CenterVertically))
        Text(spell.duration, Modifier.weight(0.25f).align(Alignment.CenterVertically))
        Text(spell.components.joinToString(), Modifier.weight(0.20f).align(Alignment.CenterVertically), maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
fun SpellSlots(amount: Int, used: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(modifier.clickable { onClick() }) {
        for(i in 1..used) {
            Icon(Icons.Default.RadioButtonChecked, "")
        }
        for(i in used until amount) {
            Icon(Icons.Default.RadioButtonUnchecked, "")
        }
    }
}