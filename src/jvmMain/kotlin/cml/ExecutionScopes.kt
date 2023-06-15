package cml

import uiData.Character
import kotlin.concurrent.thread

class CharacterScope(val char: Character)

class ChoiceScope {
    class Wait(@Volatile private var value: Value) {
        fun get() = value

        fun update(v: Value) {
            value = v
            synchronized(this) {
                (this as Object).notifyAll()
            }
        }
    }

    private lateinit var renderCallback: (List<Value>, Int, Wait) -> Unit
    private lateinit var choiceMadeCallback: (String, Value) -> Unit

    fun <T> runScript(script: ChoiceScope.() -> T, onRequireRender: (List<Value>, Int, Wait) -> Unit, onChoiceMade: (String, Value) -> Unit): T {
        var res: T? = null
        renderCallback = onRequireRender
        choiceMadeCallback = onChoiceMade
        val t = thread {
            res = script()
        }
        t.join()
        return res!! // set by thread
    }

    fun requireChoice(name: String, count: Int, options: List<Value>, pos: PosInfo? = null): Value {
        val waiter = Wait(VoidVal(PosInfo("<scope:waiting>", 0, 0)))
        renderCallback(options, count, waiter)
        synchronized(waiter) {
            (waiter as Object).wait()
        }
        var res = waiter.get()
        if(pos != null && res !is ListVal) res = ListVal(mutableListOf(res), pos)
        choiceMadeCallback(name, res)
        return res
    }

    companion object {
        val choicePos = PosInfo("<scope:choosing>", 0, 0)
    }
}