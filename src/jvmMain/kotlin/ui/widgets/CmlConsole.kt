package ui.widgets

import CMLOut
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
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
        LazyScrollColumn(modContaining = Modifier.horizontalScroll(rememberScrollState()).weight(0.95f)) {
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
        Column(Modifier.width(35.dp).padding(5.dp)) {
            IconButton({ CharacterData.saveAll(); CMLOut.refresh() }) {
                Box(Modifier.aspectRatio(1.0f)) {
                    Icon(Icons.Default.Refresh, "Refresh all scripts and data (while saving changes)")
                    Icon(
                        Icons.Default.Save, "",
                        Modifier.fillMaxSize(0.5f).align(Alignment.BottomEnd)
                    )
                }
            }
            IconButton({ CMLOut.refresh() }) {
                Box(Modifier.aspectRatio(1.0f)) {
                    Icon(Icons.Default.Refresh, "Refresh all scripts and data (without saving changes)")
                    Icon(
                        Icons.Default.Delete, "",
                        Modifier.fillMaxSize(0.5f).align(Alignment.BottomEnd)
                    )
                }
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