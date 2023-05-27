package ui.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import arrow.core.Either
import cml.CMLException
import compose
import uiData.Character

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterCard(
    typeName: String,
    character: Either<CMLException, Character>,
    index: Int,
    onSelect: (Int) -> Unit,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier
) {
    Button(
        { onSelect(index) },
        modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = if(isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primary)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.weight(0.25f)) {
                Surface(
                    Modifier.size(64.dp).clip(CircleShape),
                ) {
                    // TODO: add image?
                }
            }

            Spacer(Modifier.weight(0.05f))

            Column(Modifier.weight(0.70f)) {
                character.compose({ error ->
                    Text(typeName)
                    WithTooltip(error.message ?: "Error has no message.") {
                        Text(error.message ?: "Error has no message", color = Color.Red, overflow = TextOverflow.Ellipsis, maxLines = 1)
                    }
                }) { value ->
                    Text(value.name.value)
                    Text("Level ${value.classes.value.values.sumOf { it.level }} ${value.race.value.first}")
                }
            }
        }
    }
}