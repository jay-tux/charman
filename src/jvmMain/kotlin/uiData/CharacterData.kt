package uiData

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cml.InstanceVal
import cml.Library
import cml.PosInfo
import updateGet

data class OrError<T>(val content: T, val isError: Boolean = false, val message: String = "")

object CharacterData {
    private val _characters = mutableStateOf(listOf<OrError<InstanceVal>>())
    val characters: State<List<OrError<InstanceVal>>> = _characters
    val runtimeLoadPos = PosInfo("<runtime:load>", 0, 0)
    val runtimeRenderPos = PosInfo("<runtime:render>", 0, 0)

    fun setInvalidType(index: Int) {
        _characters.value = _characters.value.updateGet(
            index,
            OrError(
                _characters.value[index].content,
                true,
                "Character is not of kind `Character' but `${characters.value[index].content.type.kind}"
            )
        )
    }

    fun setError(index: Int, message: String) {
        _characters.value = _characters.value.updateGet(
            index,
            OrError(characters.value[index].content, true, message)
        )
    }

    fun loadFromLibrary() {
        _characters.value = _characters.value + Library.typesByKind("Character").map { OrError(InstanceVal(it, runtimeLoadPos)) }
    }
}