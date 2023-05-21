package ui.widgets

import CMLOut
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily

@Composable
fun CmlConsole() {
    val messages by CMLOut.stream
    Row(
        Modifier.fillMaxWidth().fillMaxHeight(),
    ) {
        LazyScrollColumn(modContaining = Modifier.horizontalScroll(rememberScrollState()).weight(0.8f)) {
            items(messages) { (k, m) ->
                Text(
                    m,
                    color = when (k) {
                        CMLOut.MessageKind.INFO -> Color.Blue
                        CMLOut.MessageKind.WARNING -> Color.Yellow
                        CMLOut.MessageKind.ERROR -> Color.Red
                    },
                    fontFamily = FontFamily.Monospace
                )
            }
        }
        IconButton({ CMLOut.refresh() }) {
            Icon(Icons.Default.Refresh, "Refresh all scripts and data")
        }
    }
}