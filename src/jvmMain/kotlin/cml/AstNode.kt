package cml

data class PosInfo(
    val file: String,
    val startLine: Int,
    val startColumn: Int
) {
    override fun toString(): String = "$file:$startLine:$startColumn"
}

open class AstNode(val pos: PosInfo)