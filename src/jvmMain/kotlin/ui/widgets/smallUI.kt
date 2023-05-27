package ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import uiData.MoneyDesc
import withSign
import kotlin.math.abs

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
    Row(Modifier.weight(0.07f).fillMaxWidth().clickable { onToggle(!isInspired) }) {
        Icon(
            if(isInspired) Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
            "",
            Modifier.weight(0.1f)
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
fun HPBox(kind: String, value: Int, max: Int? = null, modifier: Modifier = Modifier, damageLabel: String = "Damage", healLabel: String = "Heal", onUpdate: (Int) -> Unit) {
    var showDamageUI by remember { mutableStateOf(false) }
    var delta by remember { mutableStateOf(0) }

    Box(modifier) {
        if(showDamageUI) {
            Row {
                Box(Modifier.weight(0.33f)) {
                    Column(Modifier.align(Alignment.Center)) {
                        Text(damageLabel, color = MaterialTheme.colors.error)
                        OutlinedTextField(
                            if (delta < 0) "${-delta}" else "",
                            { delta = -(it.toIntOrNull() ?: 0) }
                        )
                    }
                }
                Box(Modifier.weight(0.33f)) {
                    Column(Modifier.align(Alignment.Center)) {
                        Row {
                            Spacer(Modifier.weight(0.1f))
                            Button({ showDamageUI = false; onUpdate(delta); delta = 0 }, Modifier.weight(0.8f)) {
                                Text(
                                    "Confirm"
                                )
                            }
                            Spacer(Modifier.weight(0.1f))
                        }

                        Row {
                            Spacer(Modifier.weight(0.1f))
                            Button({ delta = 0; showDamageUI = false }, Modifier.weight(0.75f)) { Text("Cancel") }
                            Spacer(Modifier.weight(0.1f))
                        }
                    }
                }
                Box(Modifier.weight(0.33f)) {
                    Column(Modifier.align(Alignment.Center)) {
                        Text(healLabel, color = Color.Green)
                        OutlinedTextField(
                            if (delta > 0) "$delta" else "",
                            { delta = it.toIntOrNull() ?: 0 }
                        )
                    }
                }
            }
        }
        else {
            Column(Modifier.clickable { showDamageUI = true }) {
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

@Composable
fun CurrencyWidget(abbrev: String, money: MoneyDesc) {
        Box(Modifier.fillMaxSize()) {
            Row(Modifier.align(Alignment.Center)) {
                Text("${money.amount}", style = MaterialTheme.typography.h5)
                Text(" $abbrev", Modifier.align(Alignment.Bottom), fontStyle = FontStyle.Italic)
            }
        }
}

@Composable
fun CurrencyEditWidget(
    currencies: List<String>, onCancel: () -> Unit, canPayExactly: (String, Int) -> Boolean,
    canPay: (String, Int) -> Boolean, onExact: (String, Int) -> Unit, onPay: (String, Int) -> Unit,
    onGain: (String, Int) -> Unit
) {
    var selectedUnit by remember { mutableStateOf("GP") }
    var delta by remember { mutableStateOf(0) }
    var opened by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxWidth()) {
        Column(Modifier.weight(0.70f)) {
            Row(Modifier.weight(0.45f)) {
                OutlinedTextField(
                    if (delta == 0) "" else "$delta",
                    { delta = abs(it.toIntOrNull() ?: 0) },
                    Modifier.weight(0.88f).fillMaxHeight(),
                    label = { Text("Amount of money to pay or earn") }
                )
                Spacer(Modifier.weight(0.02f))
                Box(Modifier.weight(0.1f)) {
                    Button({ opened = !opened }, Modifier.fillMaxHeight()) {
                        Text(selectedUnit, Modifier.weight(0.9f))
                        Icon(Icons.Default.MoreVert, "", Modifier.weight(0.1f))
                    }

                    DropdownMenu(opened, { opened = false }) {
                        currencies.forEach {
                            DropdownMenuItem({ selectedUnit = it; opened = false }) {
                                Text(it)
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.weight(0.05f))
            Row(Modifier.weight(0.45f)) {
                Button({ onExact(selectedUnit, delta) }, Modifier.weight(0.3f).fillMaxHeight(), enabled = canPayExactly(selectedUnit, delta)) {
                    Column {
                        Text("Pay $delta $selectedUnit")
                        Text("without converting anything", Modifier.scale(0.66f))
                    }
                }
                Spacer(Modifier.weight(0.05f))
                Button({ onPay(selectedUnit, delta) }, Modifier.weight(0.3f).fillMaxHeight(), enabled = canPay(selectedUnit, delta)) {
                    Column {
                        Text("Pay $delta $selectedUnit")
                        Text("with converting if necessary", Modifier.scale(0.66f))
                    }
                }
                Spacer(Modifier.weight(0.05f))
                Button({ onGain(selectedUnit, delta) }, Modifier.weight(0.3f).fillMaxHeight()) {
                    Text("Earn $delta $selectedUnit")
                }
            }
            Spacer(Modifier.weight(0.05f))
    }

        Button({ onCancel() }, Modifier.weight(0.3f).fillMaxWidth()) {
            Text("Cancel")
        }
    }
}