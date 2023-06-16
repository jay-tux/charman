package ui.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cml.DiceVal
import data.DiceRoller
import withSign

@Composable
fun RollDialog(
    dice: DiceVal,
    delta: Int,
    onExit: () -> Unit,
    onRolled: (Int) -> Unit
) = DefaultDialog({}, 400.dp, 250.dp) {
    var result by remember { mutableStateOf(0) }
    var overridden by remember { mutableStateOf(false) }
    val min = 1 + delta
    val max = dice.kind + delta

    Column(Modifier.fillMaxSize()) {
       Text("Please roll the following or have the system roll for you:")
        Column(Modifier.fillMaxWidth()) {
            Text(
                "${dice.repr()}${delta.withSign()}",
                Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.h5
            )
        }
        OutlinedTextField(
            if(result != 0) "$result" else "",
            { result = it.toIntOrNull() ?: 0 },
            Modifier.fillMaxWidth(),
            isError = result !in min..max,
            enabled = !overridden,
            label = { Text("Result (with modifier)") }
        )
        Row(Modifier.padding(3.dp).fillMaxWidth()) {
            Button({ onRolled(result); onExit() }, Modifier.weight(0.5f), enabled = result in min..max && !overridden) {
                Text("Confirm roll")
            }
            Button({
                if(!overridden) {
                    overridden = true
                    result = DiceRoller.roll(dice, delta)
                }
                else {
                    onRolled(result)
                    onExit()
                }
           }, Modifier.weight(0.5f)) {
                Text(
                    if(overridden) "Save and Close"
                    else "Let the system roll"
                )
            }
        }
    }
}