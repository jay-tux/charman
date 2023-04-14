package widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun DisabledTextField(value: String, modifier: Modifier = Modifier) {
    TextField(
        value = value,
        onValueChange = {},
        modifier = modifier,
        enabled = false
    )
}

@Composable
fun DisabledTextFieldOrText(value: String, isTextField: Boolean, modifier: Modifier = Modifier) {
    if (isTextField) { DisabledTextField(value, modifier) }
    else { Text(value, modifier) }
}

@Composable
fun <T> Dropdown(items: List<T>, onSelect: (Int) -> Unit, chosen: Int = -1, unselected: String = "", modifier: Modifier = Modifier, renderFun: @Composable (T, Boolean) -> Any?) = Box(modifier) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Row {
            if (chosen != -1) {
                renderFun(items[chosen], true)
            }
            else {
                Text(unselected, overflow = TextOverflow.Ellipsis)
            }
            Spacer(Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Show dropdown content"
            )
        }
    }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        DropdownMenuItem({ expanded = false }, enabled = false) {
            Text(unselected)
        }
        items.forEachIndexed { idx, item ->
            DropdownMenuItem({ onSelect(idx); expanded = false }) {
                renderFun(item, false)
            }
        }
    }
}