package ui.widgets

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

enum class ScrollDirection {
    HORIZONTAL, VERTICAL, BOTH
}

@Composable
fun ScrollBox(
    modContaining: Modifier = Modifier,
    modScroll: Modifier = Modifier,
    direction: ScrollDirection = ScrollDirection.BOTH,
    content: @Composable BoxScope.() -> Unit
) {
    val stateH = rememberScrollState()
    val stateV = rememberScrollState()

    var updMod = modScroll
    if(direction != ScrollDirection.VERTICAL) {
        updMod = updMod.scrollable(stateH, Orientation.Horizontal)
    }
    if(direction != ScrollDirection.HORIZONTAL) {
        updMod = updMod.scrollable(stateV, Orientation.Vertical)
    }

    Box(modContaining) {
        Box(updMod) {
            content()
        }

        if(direction != ScrollDirection.VERTICAL) {
            HorizontalScrollbar(
                adapter = rememberScrollbarAdapter(stateH),
                modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth()
            )
        }

        if(direction != ScrollDirection.HORIZONTAL) {
            VerticalScrollbar(
                adapter = rememberScrollbarAdapter(stateV),
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight()
            )
        }
    }
}