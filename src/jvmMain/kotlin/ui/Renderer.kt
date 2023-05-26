package ui

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

class Renderer(val rFun: @Composable BoxScope.(Modifier) -> Unit) {
    @Composable
    fun BoxScope.render(modifier: Modifier) {
        rFun(modifier)
    }
}