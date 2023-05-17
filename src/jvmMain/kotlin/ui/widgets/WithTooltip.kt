package ui.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.TooltipPlacement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WithTooltip(
    tooltip: String,
    msDelay: Int = 600,
    placement: TooltipPlacement = TooltipPlacement.CursorPoint(
        alignment = Alignment.BottomEnd,
        offset = DpOffset.Zero
    ),
    content: @Composable () -> Unit
) {
    TooltipArea(
        tooltip = {
            Surface(
                modifier = Modifier.shadow(4.dp),
                shape = RoundedCornerShape(4.dp),
            ) {
                Text(tooltip, Modifier.padding(10.dp))
            }
        },
        delayMillis = msDelay,
        tooltipPlacement = placement
    ) {
        content()
    }
}