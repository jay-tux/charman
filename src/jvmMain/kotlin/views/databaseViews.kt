package views

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import data.*
import widgets.noneYet

@Composable
fun dbFab(onAdd: () -> Unit, name: String) = FloatingActionButton(onAdd) {
    Icon(
        Icons.Filled.Add, contentDescription = "Add $name"
    )
}

@Composable
fun <T> globalDatabaseView(empty: String, name: String, onAdd: () -> Unit, fetcher: () -> List<T>, content: @Composable (T) -> Unit) {
    Scaffold(floatingActionButton = { dbFab(onAdd, name) }) {
        val fetched = fetcher()
        if(fetched.isEmpty()) {
            noneYet("No $empty yet.")
        }
        else {
            LazyColumn {
                itemsIndexed(items = fetched) { _, it -> content(it) }
            }
        }
    }
}

@Composable
fun sourceDatabaseView() = globalDatabaseView("data sources", "data source", {}, { listDataSources() }) {
    it -> smallDataSourceView(it)
}

@Composable
fun raceDatabaseView() = globalDatabaseView("races", "race", {}, { listRaces() }) {
        it -> smallRaceView(it)
}

@Composable
fun classDatabaseView() = globalDatabaseView("classes", "class", {}, { listClasses() }) {
    it -> smallClassView(it)
}

@Composable
fun backgroundDatabaseView() = globalDatabaseView("backgrounds", "background", {}, { listBackgrounds() }) {
    it -> smallBackgroundView(it)
}

@Composable
fun itemDatabaseView() = globalDatabaseView("items", "item", {}, { listItems() }) {
    it -> smallItemView(it)
}

@Composable
fun smallDataSourceView(s: DataSource) {}

@Composable
fun smallRaceView(r: Race) {}

@Composable
fun smallClassView(c: DnDClass) {}

@Composable
fun smallBackgroundView(b: Background) {}

@Composable
fun smallItemView(i: Item) {}