package uiData

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cml.Library
import cml.TopLevelDecl

object UIData {
    private val _messages = mutableStateOf("")
    val messages: State<String> = _messages
    private val _allTypes = mutableStateOf(mapOf<String, TopLevelDecl>())
    val allTypes: State<Map<String, TopLevelDecl>> = _allTypes

    fun send(message: String) { _messages.value = message }
    fun clearIf(message: String) {
        if(_messages.value == message) _messages.value = ""
    }

    fun getTypesFromLibrary() {
        _allTypes.value = Library.types()
    }
}