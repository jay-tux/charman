package uiData

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.mutableStateOf

class Mutable<T>(var value: T) {
    var mutant = false
        private set

    fun update(fn: (T) -> Unit) {
        fn(value)
        mutant = true
    }

    fun reset() { mutant = false }

    fun mutationPolicy(): SnapshotMutationPolicy<Mutable<T>> = object : SnapshotMutationPolicy<Mutable<T>> {
        override fun equivalent(a: Mutable<T>, b: Mutable<T>) = !b.mutant

        override fun merge(previous: Mutable<T>, current: Mutable<T>, applied: Mutable<T>): Mutable<T>? {
            applied.reset()
            return applied
        }
    }

    companion object {
        fun <T> stateFrom(t: T): MutableState<Mutable<T>> {
            val v = Mutable(t)
            return mutableStateOf(v, policy = v.mutationPolicy())
        }
    }
}