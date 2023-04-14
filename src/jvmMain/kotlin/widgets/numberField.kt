package widgets

import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*

fun asInt(v: String): Int {
    return try {
        v.toInt()
    } catch (e: NumberFormatException) {
        0
    }
}

@Composable
fun IntField(onChange: (Int) -> Unit, placeholder: String? = null, default: Int = 0) {
    var v by remember { mutableStateOf(default) }
    if(placeholder!= null) {
        TextField(
            v.toString(),
            { it: String -> val tmp = asInt(it); v = tmp; onChange(tmp) },
            label = { Text(placeholder) }
        )
    }
    else {
        TextField(
            v.toString(),
            { it: String -> val tmp = asInt(it); v = tmp; onChange(tmp) }
        )
    }
}