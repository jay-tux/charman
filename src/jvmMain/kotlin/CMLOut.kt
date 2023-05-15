
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object CMLOut {
    enum class MessageKind { INFO, WARNING, ERROR }

    private val _stream = MutableStateFlow(listOf<Pair<MessageKind, String>>())
    val stream: StateFlow<List<Pair<MessageKind, String>>> = _stream

    fun clearStream() { _stream.value = listOf() }
    fun add(msg: String, kind: MessageKind) { _stream.value = _stream.value + Pair(kind, msg.replace("\t", "  ")) }
    fun addInfo(msg: String) { add(msg, MessageKind.INFO) }
    fun addWarning(msg: String) { add(msg, MessageKind.WARNING) }
    fun addError(msg: String) { add(msg, MessageKind.ERROR) }
}