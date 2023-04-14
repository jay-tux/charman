package views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.listCharacters
import widgets.noneYet

enum class CurrentView(val label: String) { CHARACTERS("Characters"), DATABASE("Database Contents") }
enum class CurrentDatabaseView(val label: String) {
    SOURCES("Data Sources"), RACES("Races"), CLASSES("Classes"),
    BACKGROUNDS("Backgrounds"), ITEMS("Items")
}

@Composable
fun mainView() {
    val views = CurrentView.values().toList()
    var index by remember { mutableStateOf(0) }

    Column(Modifier.fillMaxHeight()) {
        TabRow(index) {
            views.forEachIndexed { idx, view ->
                Tab(
                    text = { Text(view.label) },
                    selected = index == idx,
                    onClick = { index = idx }
                )
            }
        }

        when(views[index]){
            CurrentView.CHARACTERS -> characterListView()
            CurrentView.DATABASE -> dbView()
        }
    }
}

@Composable
fun fab() = FloatingActionButton({ TODO("Show add character screen") }) {
    Icon(Icons.Filled.Add, contentDescription = "Add character")
}

@Composable
fun characterListView() = Scaffold(floatingActionButton = { fab() }) {
    val chars = listCharacters()
    if(chars.isEmpty()) {
        noneYet("You haven't created any characters yet.")
    }
    else {
        LazyColumn {
            itemsIndexed(items = chars) { _, it ->
                smallCharacterPanel(it)
            }
        }
    }
}