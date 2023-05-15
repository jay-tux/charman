package uiData

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import removeGet

object InitiativeData {
    private val _state = mutableStateOf(listOf<Pair<String, Int>>(
        Pair("Test 1", 4), Pair("Test 2", 3), Pair("Test 3", 1), Pair("Test 4", 2), Pair("Test 5", 3),
        Pair("Test 6", 4), Pair("Test 7", 5), Pair("Test 8", 6), Pair("Test 9", 7), Pair("Test 10", 8),
        Pair("Test 11", 12), Pair("Test 12", 13), Pair("Test 13", 13), Pair("Test 14", 14),
        Pair("Test 15", 15), Pair("Test 16", 22), Pair("Test 17", 23), Pair("Test 18", 24),
        Pair("Test 19", 25),
    ))
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