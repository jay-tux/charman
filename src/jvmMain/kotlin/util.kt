import androidx.compose.runtime.Composable
import arrow.core.Either
import arrow.core.left
import arrow.core.right

fun String.capitalizeFirst() = this[0].uppercase() + this.substring(1).lowercase()

fun String.capitalizeOnlyFirst() = this[0].uppercase() + this.substring(1)

fun <T> List<T>.addGet(v: T): List<T> {
    val mut = this.toMutableList()
    mut.add(v)
    return mut
}
fun <T> List<T>.removeGet(idx: Int): List<T> {
    val mut = this.toMutableList()
    mut.removeAt(idx)
    return mut
}

fun <T> List<T>.updateGet(idx: Int, v: T): List<T> {
    val mut = this.toMutableList()
    mut[idx] = v
    return mut
}

fun <T> List<T>.addNull(): List<T?> {
    val mut = mutableListOf<T?>()
    mut.addAll(this)
    mut.add(null)
    return mut
}

fun String.atLeastNLines(n: Int): String {
    val lines = this.split("\n").toMutableList()
    lines.addAll((0 until n - lines.size).map { "" })
    return lines.joinToString("\n")
}

fun <T> T.orNull(): T? {
    return this
}

fun Int.withSign(): String {
    return if(this < 0) "$this" else "+$this"
}

fun <E, K1, K2, V1, V2> Map<K1, V1>.mapOrEither(fn: (Map.Entry<K1, V1>) -> Either<E, Pair<K2, V2>>): Either<E, Map<K2, V2>> {
    return run outer@{
        this.map { entry ->
            when(val v = fn(entry)) {
                is Either.Left -> return@outer v.value.left()
                is Either.Right -> v.value
            }
        }.associate { it }.right()
    }
}

fun <E, T1, T2> List<T1>.mapOrEither(fn: (T1) -> Either<E, T2>): Either<E, List<T2>> {
    return run outer@{
        this.map { elem ->
            when(val v = fn(elem)) {
                is Either.Left -> return@outer v.value.left()
                is Either.Right -> v.value
            }
        }.right()
    }
}

@Composable
fun <E, V> Either<E, V>.compose(fnE: @Composable (E) -> Unit, fnV: @Composable (V) -> Unit) {
    when(this) {
        is Either.Left -> fnE(this.value)
        is Either.Right -> fnV(this.value)
    }
}