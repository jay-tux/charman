package ui.dialogs

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.onClick
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerKeyboardModifiers
import androidx.compose.ui.input.pointer.isCtrlPressed
import androidx.compose.ui.unit.dp
import ui.widgets.LazyScrollColumn
import ui.widgets.LazyScrollRow
import ui.widgets.YesNoButton
import java.io.File
import java.nio.file.Path
import kotlin.io.path.absolute

fun File.enumerate(): List<File> = walk().maxDepth(1).toList().filter{ it != this }.sortedBy { "${if(it.isDirectory) 0 else 1}${it.name}" }

fun PointerKeyboardModifiers.isCtrl(): Boolean {
    return this.isCtrlPressed
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun FileBrowserDialog(basePath: Path = Path.of(System.getProperty("user.home")), onClose: () -> Unit, onOpen: (List<Path>) -> Unit) {
    DefaultDialog(
        onExit = onClose,
        width = 600.dp,
        height = 400.dp
    ) {
        var selected by remember { mutableStateOf(listOf<Int>()) }
        var path by remember { mutableStateOf(basePath) }
        var sub by remember { mutableStateOf(basePath.toFile().enumerate().filter { !it.name.startsWith(".") }) }

        val moveTo = { pth: Path ->
            selected = listOf()
            path = pth.absolute()
            sub = pth.toFile().enumerate().filter { !it.name.startsWith(".") }
        }

        val resolveSingle = { f: File ->
            onOpen(listOf(f.toPath()))
            onClose()
        }

        val toggle = { i: Int ->
            selected = if(selected.contains(i)) selected.filter { it != i } else (selected + i)
        }

        Column {
            Box(Modifier.weight(0.15f)) {
                LazyScrollRow {
                    items((0 until path.nameCount).toList()) { i ->
                        if (i != 0) Icon(Icons.Default.NavigateNext, "")
                        Text("${path.getName(i)}", Modifier.clickable { moveTo(Path.of("/").resolve(path.subpath(0, i + 1))) })
                    }
                }
            }

            val lambda: PointerKeyboardModifiers.() -> Boolean = { this.isCtrl() }

            Box(Modifier.weight(0.7f)) {
                if(sub.isEmpty()) {
                    Box(Modifier.fillMaxSize()) {
                        Text("Folder is empty", Modifier.align(Alignment.Center), color = MaterialTheme.colors.primary)
                    }
                }
                else {
                    LazyScrollColumn {
                        itemsIndexed(sub) { idx, it ->
                            Text(
                                it.name,
                                Modifier
                                    .fillMaxWidth()
                                    .background(
                                        if (selected.contains(idx)) MaterialTheme.colors.primary.copy(0.2f) else MaterialTheme.colors.primary.copy(
                                            0f
                                        )
                                    )
                                    .onClick(onDoubleClick = {
                                        if (it.isDirectory) moveTo(it.toPath()) else resolveSingle(
                                            it
                                        )
                                    }) { selected = listOf(idx) }
                                    .onClick(keyboardModifiers = lambda) { toggle(idx) }
                            )
                        }
                    }
                }
            }
            Box(Modifier.weight(0.15f)) {
                YesNoButton(
                    no = "Cancel",
                    onNo = { onClose() },
                    yes = "Open Selected",
                    yesEnabled = selected.isNotEmpty()
                ) {
                    onOpen(sub.filterIndexed { index, _ -> selected.contains(index) }.map { it.toPath() })
                    onClose()
                }
            }
        }
    }
}