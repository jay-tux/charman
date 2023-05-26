package uiData

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import removeGet

object InitiativeData {
    private val _state = mutableStateOf(listOf<Pair<String, Int>>())
    val state: State<List<Pair<String, Int>>> = _state

    private val _index = mutableStateOf(0)
    val index: State<Int> = _index

    fun addInitiative(name: String, init: Int) {
        _state.value = (_state.value + Pair(name, init)).sortedByDescending { it.second }
    }

    fun addAllInitiative(values: List<Pair<String, Int>>) {
        _state.value = (_state.value + values).sortedByDescending { it.second }
    }

    fun removeInitiative(index: Int) {
        _state.value = _state.value.removeGet(index)
        if(_index.value > index) _index.value -= 1
    }

    fun nextTurn() {
        _index.value = (_index.value + 1) % _state.value.size
    }
}