package uiData

import CMLOut
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import cml.CMLException
import cml.InstanceVal
import cml.Library
import cml.PosInfo
import updateGet

object CharacterData {
    private val _characters = mutableStateOf(listOf<Either<Pair<String, CMLException>, InstanceVal>>())
    val characters: State<List<Either<Pair<String, CMLException>, InstanceVal>>> = _characters
    val runtimeLoadPos = PosInfo("<runtime:load>", 0, 0)
    val runtimeRenderPos = PosInfo("<runtime:render>", 0, 0)

    fun setError(index: Int, message: String) {
        setError(index, CMLException(message))
    }

    fun setError(index: Int, message: CMLException) {
        _characters.value = _characters.value.updateGet(
            index,
            Pair(_characters.value[index].fold({ it.first }, { it.type.name }), message).left()
        )
    }

    fun loadFromLibrary() {
        _characters.value = _characters.value + Library.typesByKind("Character").map { InstanceVal(it, runtimeLoadPos).right() }
    }

    fun <T> handleCMLException(ex: CMLException, character: Either<CMLException, InstanceVal>, index: Int, res: T): T {
        if(character.isRight()) {
            ex.message?.let {
                CMLOut.addError(it)
            }
            setError(index, ex)
        }
        return res
    }
}