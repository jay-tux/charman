package widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun noneYet(txt: String) = Box(Modifier.fillMaxSize()) {
    Text(
        text = txt,
        style = MaterialTheme.typography.h5,
        modifier = Modifier.align(Alignment.Center)
    )
}