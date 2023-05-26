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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import withSign

@Composable
fun boldThenNormal(bold: String, normal: String) = Row {
    Text(bold, fontWeight = FontWeight.Bold)
    Text(" $normal")
}

@Composable
fun boldThenItalic(bold: String, italic: String) = Row {
    Text(bold, fontWeight = FontWeight.Bold)
    Text(" $italic", fontStyle = FontStyle.Italic)
}

@Composable
fun indented(content: @Composable RowScope.() -> Unit) = Row {
    Spacer(Modifier.width(10.dp))
    content()
}

@Composable
fun TraitCard(name: String, source: String, desc: String) = Column {
    boldThenItalic(name, "($source)")
    indented {
        Text(desc)
    }
}

@Composable
fun AbilityScoreCard(abbrev: String, value: Int, mod: Int) = Box(
    Modifier.fillMaxWidth().aspectRatio(0.75f)
) {
    Column(
        Modifier
            .align(Alignment.TopCenter).fillMaxWidth().fillMaxHeight(0.7f).padding(2.dp)
    ) {
        Text(
            abbrev,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h6
        )

        Box(Modifier.fillMaxSize()) {
            Text(
                "$value",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
    Box(
        Modifier.align(Alignment.BottomCenter)
    ) {
        Text(
            mod.withSign(),
            Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.subtitle2
        )
    }
}

@Composable
fun ModScoreCard(name: String, mod: Int, hasProf: Boolean = false) {
    Row {
        Icon(
            if(hasProf) Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
            "",
            Modifier.weight(0.1f)
        )
        Text(mod.withSign(), Modifier.weight(0.12f).align(Alignment.CenterVertically))
        Spacer(Modifier.weight(0.01f))
        Text(name, Modifier.weight(0.77f).align(Alignment.CenterVertically))
    }
}

@Composable
fun ColumnScope.InspirationWidget(isInspired: Boolean = false, onToggle: (Boolean) -> Unit) {
    Row(Modifier.weight(0.07f)) {
        Icon(
            if(isInspired) Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
            "",
            Modifier.weight(0.1f).clickable { onToggle(!isInspired) }
        )
        Text("Inspiration", Modifier.weight(0.9f), fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ColumnScope.IntStringCard(v: Int, s: String, withSign: Boolean = false) {
    Row(Modifier.weight(0.07f)) {
        Text(if(withSign) v.withSign() else "$v", Modifier.weight(0.10f).align(Alignment.CenterVertically), style = MaterialTheme.typography.h6)
        Text(s, Modifier.weight(0.90f).align(Alignment.CenterVertically), fontWeight = FontWeight.Bold)
    }
}

@Composable
fun CenteredBox(title: String, value: String, modifier: Modifier = Modifier) {
    Box(modifier) {
        Column(Modifier.align(Alignment.Center)) {
            Text(title, fontStyle = FontStyle.Italic, modifier = Modifier.align(Alignment.CenterHorizontally))
            Text(value, style = MaterialTheme.typography.h6, modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

@Composable
fun HPBox(kind: String, value: Int, max: Int? = null, modifier: Modifier = Modifier) {
    Box(modifier) {
        Column {
            Text(kind, fontStyle = FontStyle.Italic)
            Box(Modifier.fillMaxSize()) {
                Row(Modifier.align(Alignment.Center)) {
                    Text("$value", style = MaterialTheme.typography.h5)
                    max?.let { Text("/$it", Modifier.align(Alignment.Bottom)) }
                }
            }
        }
    }
}

@Composable
fun DeathSaveWidget(kind: String, amount: Int, max: Int = 3, modifier: Modifier = Modifier, kindWeight: Float = 0.5f, onClick: () -> Unit) {
    Row(modifier.clickable { onClick() }) {
        Text(kind, Modifier.weight(kindWeight))

        Row(Modifier.weight(1.0f - kindWeight)) {
            for(i in 0 until amount) {
                Icon(Icons.Default.RadioButtonChecked, "")
            }
            for(i in amount until max) {
                Icon(Icons.Default.RadioButtonUnchecked, "")
            }
        }
    }
}

@Composable
fun BoldAndNot(bold: String, not: String, modifier: Modifier = Modifier) {
    Row(modifier) {
        Text(bold, fontWeight = FontWeight.Bold)
        Text(not)
    }
}