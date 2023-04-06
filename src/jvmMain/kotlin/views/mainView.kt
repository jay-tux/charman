package views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            CurrentView.DATABASE -> databaseOverview()
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

@Composable
fun databaseOverview() {
    val views = CurrentDatabaseView.values().toList()
    var index by remember { mutableStateOf(0) }

    Column(Modifier.fillMaxHeight()) {
        Row(Modifier.fillMaxWidth()) {
            Spacer(Modifier.width(10.dp))
            TabRow(index) {
                views.forEachIndexed { idx, view ->
                    Tab(
                        text = { Text(view.label) },
                        selected = index == idx,
                        onClick = { index = idx }
                    )
                }
            }
            Spacer(Modifier.width(10.dp))
        }

        when(views[index]){
            CurrentDatabaseView.SOURCES -> sourceDatabaseView()
            CurrentDatabaseView.RACES -> raceDatabaseView()
            CurrentDatabaseView.CLASSES -> classDatabaseView()
            CurrentDatabaseView.BACKGROUNDS -> backgroundDatabaseView()
            CurrentDatabaseView.ITEMS -> itemDatabaseView()
        }
    }
}