
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cml.Library
import data.Scripts
import uiData.CharacterData
import java.text.SimpleDateFormat
import java.util.*

object CMLOut {
    enum class MessageKind { INFO, WARNING, ERROR }

    private val _stream = mutableStateOf(listOf<Pair<MessageKind, String>>())
    val stream: State<List<Pair<MessageKind, String>>> = _stream

    fun clearStream() { _stream.value = listOf() }
    private fun add(msg: String, kind: MessageKind) {
        _stream.value = _stream.value + Pair(kind, "${formatTime()} $msg".replace("\t", "  "))
    }
    fun addInfo(msg: String) { add(msg, MessageKind.INFO) }
    fun addWarning(msg: String) { add(msg, MessageKind.WARNING) }
    fun addError(msg: String) { add(msg, MessageKind.ERROR) }

    fun refresh() {
        clearStream()
        addInfo("Reloading cache...")
        CharacterData.clear()
        Library.clear()
        Scripts.loadCache()
    }

    fun formatTime(): String {
        return SimpleDateFormat("[hh:mm:ss]:").format(Date())
    }
}