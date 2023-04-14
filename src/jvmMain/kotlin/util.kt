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