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
import data.*
import dialogs.addClassDialog
import dialogs.addDatasourceDialog
import dialogs.addRaceDialog
import updateGet
import widgets.classWidget
import widgets.datasourceWidget
import widgets.noneYet
import widgets.raceWidget

enum class CurrDbView(val label: String) {
    SOURCES("Data Sources"),
    RACES("Playable Races"),
    CLASSES("Classes"),
    BACKGROUNDS("Backgrounds"),
    ITEMS("Items"),
    MISC("Miscellaneous Data")
}

@Composable
fun dbView() {
    var view by remember { mutableStateOf(CurrDbView.SOURCES) }
    var index by remember { mutableStateOf(0) }
    var sourceCount by remember { mutableStateOf(listDataSources().size) }

    Column {
        TabRow(index) {
            CurrDbView.values().forEachIndexed { idx, value ->
                Tab(
                    text = { Text(value.label) },
                    selected = view == value,
                    onClick = { view = value; index = idx }
                )
            }
        }

        Box(modifier = Modifier.padding(10.dp)) {
            when (view) {
                CurrDbView.SOURCES -> singleDbView(
                    fetcher = { listDataSources() },
                    remover = { rmDataSource(it); sourceCount = listDataSources().size },
                    onAdd = { addDatasourceDialog(it) { sourceCount = listDataSources().size } })
                { src, onRemove, onEdit -> datasourceWidget(src, onRemove, onEdit) }

                CurrDbView.RACES -> singleDbView(
                    fetcher = { listRaces() },
                    remover = { rmRace(it) },
                    onAdd = { addRaceDialog(it) { } },
                    enableFab = sourceCount > 0)
                { race, onRemove, onEdit -> raceWidget(race, onRemove, onEdit) }

                CurrDbView.CLASSES -> singleDbView(
                    fetcher = { listClasses() },
                    remover = { _ -> TODO() },
                    onAdd = { addClassDialog(it) { } },
                    enableFab = sourceCount > 0)
                { cls, onRemove, onEdit -> classWidget(cls, onRemove, onEdit) }

                else -> {}
            }
        }
    }
}

@Composable
fun <T> singleDbView(
    fetcher: () -> List<T>,
    remover: (toRemove: T) -> Unit,
    onAdd: @Composable (onExit: () -> Unit) -> Unit,
    modifier: Modifier = Modifier,
    enableFab: Boolean = true,
    render: @Composable (value: T, onRemove: () -> Unit, onEdit: (T) -> Unit) -> Unit
) {
    var values by remember { mutableStateOf(fetcher()) }
    var adding by remember { mutableStateOf(false) }

    val notifyRemove = { it: T ->
        remover(it)
        values = fetcher()
    }

    Scaffold(modifier = modifier, floatingActionButton = {
        if(enableFab) {
            FloatingActionButton({ adding = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Add an item")
            }
        }
    }) {
        if (values.isEmpty()) {
            noneYet("No data of this kind has been added yet.")
        } else {
            LazyColumn {
                itemsIndexed(values) { idx, single ->
                    Column {
                        render(single, { notifyRemove(single) }) { values.updateGet(idx, it) }
                        Spacer(Modifier.height(5.dp))
                    }
                }
            }
        }
    }

    if(adding) {
        onAdd { adding = false; values = fetcher() }
    }
}