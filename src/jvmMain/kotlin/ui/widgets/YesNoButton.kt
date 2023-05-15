package ui.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun YesNoButton(
    no: String = "No",
    yes: String = "Yes",
    noEnabled: Boolean = true,
    yesEnabled: Boolean = true,
    onNo: () -> Unit,
    onYes: () -> Unit
) = Row(Modifier.fillMaxWidth()) {
    Button(onNo, Modifier.weight(1f), enabled = noEnabled) { Text(no) }
    Spacer(Modifier.weight(0.2f))
    Button(onYes, Modifier.weight(1f), enabled = yesEnabled) { Text(yes) }
}