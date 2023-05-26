package ui.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cml.ChoiceScope
import cml.InstanceVal
import cml.ListVal
import cml.Value
import data.getName
import ui.widgets.LazyScrollColumn
import uiData.Character

@Composable
fun SingleChoiceDialog(
    options: List<Value>,
    onExit: () -> Unit,
    onChoice: (Value) -> Unit,
) = DefaultDialog({ /* disable closing without choosing */ }, 400.dp, 500.dp) {
    var selected by remember { mutableStateOf(-1) }
    // doesn't work with state for some reason?
    val choices = options.map { v ->
        when (v) {
            is InstanceVal -> v.getName(Character.posRender).fold({ "" }, { it })
            else -> v.repr()
        }
    }.filter { it.isNotEmpty() }

    Column {
        Text("Select one of the options below:", Modifier.weight(0.1f))
        LazyScrollColumn(Modifier.weight(0.8f)) {
            itemsIndexed(choices) { idx, option ->
                Row(Modifier.fillMaxWidth().clickable { selected = idx }) {
                    Icon(
                        if(idx == selected) Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
                        ""
                    )
                    Text(option)
                }
            }
        }

        Row(Modifier.weight(0.1f)) {
            Spacer(Modifier.weight(0.02f))
            Button({ onChoice(options[selected]); onExit() }, enabled = selected != -1) {
                Box(Modifier.fillMaxWidth()) {
                    Text("Confirm choice", Modifier.align(Alignment.Center))
                }
            }
            Spacer(Modifier.weight(0.02f))
        }
    }
}

@Composable
fun MultiChoiceDialog(
    count: Int,
    options: List<Value>,
    onExit: () -> Unit,
    onChoice: (Value) -> Unit,
) = DefaultDialog({ /* disable closing without choosing */ }, 400.dp, 500.dp) {
    var selected by remember { mutableStateOf(listOf<Int>()) }

    val append = { v: Int ->
        val upd = selected.toMutableList()
        upd.add(v)
        while(upd.size > count) upd.removeAt(0) // pop front
        selected = upd
    }

    val choices = options.map { v ->
        when(v) {
            is InstanceVal -> v.getName(Character.posRender).fold({ "" }, { it })
            else -> v.repr()
        }
    }.filter { it.isNotEmpty() }

    val confirm = {
        val res = options.filterIndexed { index, _ -> selected.contains(index) }
        onChoice(ListVal(res.toMutableList(), ChoiceScope.choicePos))
        onExit()
    }

    Column {
        Text("Select $count of the options below:", Modifier.weight(0.1f))
        LazyScrollColumn(Modifier.weight(0.8f)) {
            itemsIndexed(choices) { idx, option ->
                Row(Modifier.fillMaxWidth().clickable { append(idx) }) {
                    Icon(
                        if(selected.contains(idx)) Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
                        ""
                    )
                    Text(option)
                }
            }
        }

        Row(Modifier.weight(0.1f)) {
            Spacer(Modifier.weight(0.02f))
            Button(confirm, enabled = selected.size == count) {
                Box(Modifier.fillMaxWidth()) {
                    Text("Confirm choice", Modifier.align(Alignment.Center))
                }
            }
            Spacer(Modifier.weight(0.02f))
        }
    }
}

@Composable
fun choiceDispatcher(
    count: Int, options: List<Value>, onExit: () -> Unit, onChoice: (Value) -> Unit
) {
    if(count == 1) SingleChoiceDialog(options, onExit, onChoice)
    else MultiChoiceDialog(count, options, onExit, onChoice)
}
