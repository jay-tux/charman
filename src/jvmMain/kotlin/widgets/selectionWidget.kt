package widgets

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun <T> selectionWidget(
    selected: List<T>,
    fetcher: () -> List<T>,
    comparator: (T, T) -> Boolean = { t1, t2 -> t1 == t2 },
    onSelect: (T) -> Unit,
    onDeselect: (T) -> Unit,
    onUpdateSingle: () -> Unit,
    onRequestAdd: @Composable (() -> Unit, (T) -> Unit) -> Unit,
    onRequestMod: @Composable (T, () -> Unit, (T) -> Unit) -> Unit,
    modifier: Modifier = Modifier,
    render: @Composable RowScope.(T, Modifier) -> Unit
) {
    var all by remember { mutableStateOf(fetcher()) }
    var openDialog by remember { mutableStateOf(false) }
    var dialogTarget by remember { mutableStateOf<T?>(null) }

    Row(modifier) {
        LazyColumn(Modifier.fillMaxWidth(0.8f)) {
            items(all) { elem ->
                Row {
                    Checkbox(
                        checked = selected.any { it -> comparator(elem, it) },
                        onCheckedChange = { if(it) onSelect(elem) else onDeselect(elem) },
                        modifier = Modifier.weight(0.1f)
                    )
                    render(elem, Modifier.pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = { _ -> openDialog = true; dialogTarget = elem }
                        )
                    }.weight(0.8f))
                }
            }
        }

        IconButton({ openDialog = true; dialogTarget = null }) {
            Icon(
                Icons.Default.Add,
                "Add"
            )
        }
    }

    if(openDialog) {
        when(dialogTarget) {
            null -> onRequestAdd({ openDialog = false }, { all = all + it })
            else -> onRequestMod(dialogTarget!!, { openDialog = false }) { _ ->
                all = fetcher()
                onUpdateSingle()
            }
        }
    }
}