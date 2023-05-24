package ui.widgets

import CMLOut
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import data.Scripts
import ui.dialogs.FileBrowserDialog
import uiData.CharacterData

@Composable
fun CmlConsole() {
    val messages by CMLOut.stream
    var showFBD by remember { mutableStateOf(false) }
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
        Column {
            IconButton({ CMLOut.refresh() }) {
                Icon(Icons.Default.Refresh, "Refresh all scripts and data")
            }
            IconButton({ showFBD = true }) {
                Icon(Icons.Default.FileOpen, "Import scripts")
            }
            IconButton({ CharacterData.saveAll() }) {
                Icon(Icons.Default.Save, "Save all characters")
            }
        }
    }

    if(showFBD) {
        FileBrowserDialog(onClose = { showFBD = false }) { Scripts.addToCache(it) }
    }
}