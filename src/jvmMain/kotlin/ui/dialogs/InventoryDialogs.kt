package ui.dialogs

import CMLOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cml.InstanceVal
import cml.Library
import data.loadItem
import filterRight
import kotlinx.coroutines.launch
import ui.widgets.CurrencyEditWidget
import ui.widgets.LazyScrollColumn
import uiData.Character
import uiData.ItemDesc

@Composable
fun CurrencyDialog(
    currencies: List<String>,
    onClose: () -> Unit,
    canPayExactly: (String, Int) -> Boolean,
    canPay: (String, Int) -> Boolean,
    onExact: (String, Int) -> Unit,
    onPay: (String, Int) -> Unit,
    onGain: (String, Int) -> Unit
) = DefaultDialog(
    { onClose() }, 1000.dp, 250.dp
) {
    CurrencyEditWidget(
        currencies = currencies,
        onCancel = { onClose() },
        canPayExactly = { unit, amount -> canPayExactly(unit, amount) },
        canPay = { unit, amount -> canPay(unit, amount) },
        onExact = { unit, amount -> onExact(unit, amount); onClose() },
        onPay = { unit, amount -> onPay(unit, amount); onClose() },
        onGain = { unit, amount -> onGain(unit, amount); onClose() }
    )
}

@Composable
fun ItemDialog(onClose: () -> Unit, onAdd: (ItemDesc, Int) -> Unit) = DefaultDialog(
    { onClose() }, 450.dp, 800.dp
) {
    var searching by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf(1) }
    var selected by remember { mutableStateOf(-1) }
    val scope = rememberCoroutineScope()

    val items = remember {
        Library.typesByKind("Item").map {
            Character.loadItem(InstanceVal(it, Character.posRender))
                .map { (desc, _) -> desc }
                .mapLeft { err -> CMLOut.addError(err.localizedMessage) }
        }.filterRight().sortedBy { it.name }
    }

    var current by remember { mutableStateOf(items) }

    Column {
        Box(Modifier.weight(0.1f)) {
            OutlinedTextField(
                searching,
                {
                    searching = it
                    scope.launch { current = items.filter { item -> item.name.contains(it) } }
                }
            )
        }

        LazyScrollColumn(Modifier.weight(0.76f)) {
            itemsIndexed(current) { idx, item ->
                Box(Modifier.fillMaxWidth()) {
                    Text(item.name, Modifier.clickable { selected = idx })
                }
            }
        }

        Row(Modifier.weight(0.07f)) {
            OutlinedTextField(
                "$amount",
                {
                    var tmp = it.toIntOrNull() ?: 1
                    if(tmp < 1) tmp = 1
                    amount = tmp
                },
                Modifier.weight(0.45f)
            )
            Spacer(Modifier.weight(0.05f))
            Button(
                { onAdd(current[selected], amount); onClose() },
                Modifier.weight(0.5f),
                enabled = selected in current.indices
            ) {
                Text("Add item${if(amount > 1) "s" else ""}")
            }
        }

        Row(Modifier.weight(0.07f)) {
            Button(
                { onClose() },
                Modifier.fillMaxWidth()
            ) {
                Text("Cancel")
            }
        }
    }
}