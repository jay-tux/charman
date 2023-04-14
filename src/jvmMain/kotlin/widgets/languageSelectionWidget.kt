package widgets

import addGet
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.Language
import data.listLanguages
import org.jetbrains.exposed.sql.transactions.transaction

@Composable
fun languageWidget(
    chosen: List<Language>,
    onSelect: (Int, Language) -> Unit,
    onDeselect: (Int, Language) -> Unit
) {
    var languages by remember { mutableStateOf(transaction { listLanguages() }) }

    val addLang = { l: String -> languages = languages.addGet(transaction { Language.new{ this.name = l } }) }
    val modLang = { idx: Int, l: String -> transaction{ languages[idx].name = l }; languages = listLanguages() }
    // val rmLang = { idx: Int -> transaction{ languages[idx].delete() }; languages = languages.removeGet(idx) }

    LazyColumn {
        itemsIndexed(languages) { idx, lang ->
            var checked by remember { mutableStateOf(chosen.contains(lang)) }
            var name by remember { mutableStateOf(lang.name) }
            Row {
                Checkbox(checked, { checked = it; if(it) onSelect(idx, lang) else onDeselect(idx, lang) })
                Spacer(modifier = Modifier.width(5.dp))
                OutlinedTextField(name, { name = it; modLang(idx, it) }, label = { Text("Language name") })
            }
        }
    }

    var name by remember { mutableStateOf("") }
    Column {
        TextField(name, { name = name }, label = { Text("Language name") })
        Spacer(modifier = Modifier.height(5.dp))
        Row {
            Button({ addLang(name); name = "" }) { Text("Add language") }
            Spacer(modifier = Modifier.width(5.dp))
            Button({ name = "" }) { Text("Clear") }
        }
    }
}