package ui.widgets

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LazyScrollColumn(
    modContaining: Modifier = Modifier,
    modScroll: Modifier = Modifier,
    content: LazyListScope.() -> Unit
) = Box(modifier = modContaining) {
    val state = rememberLazyListState()

    LazyColumn(modifier = modScroll.fillMaxSize().padding(end = 12.dp), state) {
        content()
    }
    VerticalScrollbar(
        modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
        adapter = rememberScrollbarAdapter(state)
    )
}

@Composable
fun LazyScrollRow(
    modContaining: Modifier = Modifier,
    modScroll: Modifier = Modifier,
    content: LazyListScope.() -> Unit
) = Box(modifier = modContaining) {
    val state = rememberLazyListState()

    LazyRow(modifier = modScroll.fillMaxSize().padding(bottom = 12.dp), state) {
        content()
    }
    HorizontalScrollbar(
        modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth(),
        adapter = rememberScrollbarAdapter(state)
    )
}

