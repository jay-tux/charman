package ui.widgets

import CMLOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cml.CMLException
import cml.InstanceVal
import cml.StringVal
import uiData.CharacterData
import uiData.OrError

@Composable
fun CharacterCard(
    character: OrError<InstanceVal>,
    index: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) = Button({ onSelect(index) }, modifier) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
            Modifier.size(64.dp).clip(CircleShape)
        ) {
            // TODO: add image?
        }

        Column {
            if (character.isError) {
                Text(character.content.type.name)
                Text(character.message)
            } else if (character.content.type.kind != "Character") {
                CharacterData.setInvalidType(index)
            } else {
                val (name, nameErr) = charName(character.content, index)
                Text(name)
                if(nameErr != null) {
                    Row { Spacer(Modifier.width(10.dp)); Text(nameErr) }
                }
            }
        }
    }
}

fun charName(c: InstanceVal, index: Int): Pair<String, String?> {
    val name = c.type.fields.getVar("name")
    if(name == null) {
        val errorMsg = "Character `${c.type.name}' has no field `name'."
        CharacterData.setError(index, errorMsg)
        CMLOut.addError(CMLException.invalidField(c.type.name, "name", CharacterData.runtimeRenderPos).message ?: "")
        return Pair(c.type.name, errorMsg)
    }

    if(name.value !is StringVal) {
        val errorMsg = "Character `${c.type.name}' has a name field that is not of type `String'."
        CharacterData.setError(index, errorMsg)
        CMLOut.addError(CMLException.typeError("String", name.value, CharacterData.runtimeRenderPos).message ?: "")
        return Pair(c.type.name, errorMsg)
    }

    return Pair((name.value as StringVal).value, null)
}
