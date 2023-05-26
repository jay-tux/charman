package ui.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import uiData.UIData

@Composable
fun TypeList() {
    val types by UIData.allTypes
    LazyScrollColumn {
        item {
            Row {
                Text("Type Name", Modifier.weight(0.33f))
                Text("Type Kind", Modifier.weight(0.33f))
                Text("Declared At", Modifier.weight(0.33f))
            }
        }
        items(types.values.toList()) {
            Row {
                Text(it.name, Modifier.weight(0.33f))
                Text(it.kind, Modifier.weight(0.33f))
                Text(it.pos.toString(), Modifier.weight(0.33f))
            }
        }
    }
}