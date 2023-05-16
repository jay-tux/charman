package uiData

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

object UIData {
    private val _messages = mutableStateOf("")
    val messages: State<String> = _messages

    fun send(message: String) { _messages.value = message }
    fun clearIf(message: String) {
        if(_messages.value == message) _messages.value = ""
    }
}