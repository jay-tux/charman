package widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState

@Composable
fun DefaultDialog(
    onExit: () -> Unit,
    width: Dp,
    height: Dp,
    content: @Composable BoxScope.() -> Unit
) = Dialog(
    onCloseRequest = { onExit() },
    state = rememberDialogState(
        position = WindowPosition(Alignment.Center),
        size = DpSize(width, height)
    )
) {
    Box(Modifier.padding(15.dp)) {
        content()
    }
}