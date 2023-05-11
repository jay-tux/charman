package uiData

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object UIData {
    private val _messages = MutableStateFlow("")
    val messages: StateFlow<String> = _messages

    fun send(message: String) { _messages.value = message }
    fun clearIf(message: String) {
        if(_messages.value == message) _messages.value = ""
    }
}