package widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun expandableButton(
    buttonContent: @Composable () -> Unit,
    additionalButtons: @Composable () -> Unit,
    expandContent: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Button({ expanded = !expanded }, Modifier.fillMaxWidth()) {
        Box(Modifier.weight(0.9f)) {
            buttonContent()
        }
        Box() {
            additionalButtons()
        }
    }

    if(expanded) expandContent()
}