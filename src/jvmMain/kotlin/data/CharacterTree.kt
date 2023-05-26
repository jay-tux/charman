package data

import CMLOut
import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import cml.*
import mapOrEither

interface CharacterNode {
    fun serialize(): String
}

fun List<String>.withIndent(indent: Int): List<String> {
    var realIndent = ""
    for(i in 0 until indent) realIndent += "  "

    val realLines = mutableListOf<String>()
    this.forEach { it ->
        it.split("\n").forEach {
            realLines.add("$realIndent$it")
        }
    }
    return realLines
}

fun String.withIndent(indent: Int): String {
    var realIndent = ""
    for(i in 0 until indent) realIndent += "  "
    return this.split("\n").joinToString("\n") { "$realIndent$it" }
}

fun List<String>.lines() = joinToString("\n")

fun Char.isAlphaNumeric(): Boolean = (this in 'a'..'z') || (this in 'A'..'Z') || (this in '0'..'9')

fun String.toIdentifier(): String = map{ if(it == ' ') '_' else it }.filter { it.isAlphaNumeric() || it == '_' }.joinToString("")

class RootNode(val name: Identifier) : CharacterNode {
    val fields = mutableListOf(CharacterField("name", name.name.toSerializable()))

    override fun serialize(): String {
        return "data Character ${name.serialize()} {\n" +
                fields.map { it.serialize() }.withIndent(1).lines() + "\n" +
                "}"
    }
}

class CharacterField(val name: String, val value: SerializeExpression): CharacterNode {
    override fun serialize(): String = "field ${name.toIdentifier()} = ${value.serialize()};"

    fun addTo(root: RootNode) {
        root.fields.add(this)
    }
}

// region Expressions
abstract class SerializeExpression: CharacterNode {
    fun toField(name: String) = CharacterField(name, this)
}

fun Value.toSerializable(): Either<CMLException, SerializeExpression> {
    return when(this) {
        is BoolVal -> value.toSerializable().right()
        is IntVal -> value.toSerializable().right()
        is StringVal -> value.toSerializable().right()
        is ListVal -> value.toSerializableEither()
        is DictVal -> value.toSerializableEither()
        is InstanceVal -> type.toCtor().right()

        else -> CMLException.nonSerializable(this).left()
    }
}

class BoolValSer(private val value: Boolean): SerializeExpression() {
    override fun serialize(): String = "$value"
}
fun Boolean.toSerializable() = BoolValSer(this)

class IntValSer(private val value: Int): SerializeExpression() {
    override fun serialize(): String = "$value"
}
fun Int.toSerializable() = IntValSer(this)

class StringValSer(private val content: String): SerializeExpression() {
    override fun serialize() = "\"${content.filter { it != '"' }}\""
}
fun String.toSerializable() = StringValSer(this)

class CtorValSer(private val type: String): SerializeExpression() {
    override fun serialize() = ".${type.toIdentifier()}"
}
fun String.toCtor() = CtorValSer(this)
fun TopLevelDecl.toCtor() = CtorValSer(this.name)

class ListValSer(private val content: List<SerializeExpression>): SerializeExpression() {
    override fun serialize() = "[ ${content.joinToString(", ") { it.serialize() }} ]"
}
fun List<Value>.toSerializableEither() = mapOrEither { it.toSerializable() }.map { ListValSer(it) }
fun <T> List<T>.toSerializable(map: (T) -> SerializeExpression) = ListValSer(this.map(map))

class DictValSer(private val content: Map<SerializeExpression, SerializeExpression>): SerializeExpression() {
    override fun serialize(): String {
        val lines = content.map { (k, v) -> "${k.serialize()} = ${v.serialize()}".withIndent(1) }
        return "{\n" +
                lines.joinToString(",\n") + "\n" +
                "}"
    }
}
fun <K, V> Map<K, V>.toSerializable(map: (K, V) -> Pair<SerializeExpression, SerializeExpression>) =
    DictValSer(map { (k, v) -> map(k, v) }.associate { it })

fun <K, V> Map<K, V>.toSerializableEither(map: (K, V) -> Either<CMLException, Pair<SerializeExpression, SerializeExpression>>): DictValSer? {
    return mapOrEither { (k, v) -> map(k, v) }.fold({
        CMLOut.addError("Error while serializing: ${it.localizedMessage}")
        null
    }) { it }?.let { DictValSer(it) }
}

fun Map<Value, Value>.toSerializableEither() =
    mapOrEither { (k, v) ->
        k.toSerializable().flatMap { kS -> v.toSerializable().map { vS -> Pair(kS, vS) } }
    }.map { DictValSer(it) }
// endregion

class Identifier(val name: String): CharacterNode {
    override fun serialize() = name.toIdentifier()
}
fun String.toIdentifierNode() = Identifier(this)
