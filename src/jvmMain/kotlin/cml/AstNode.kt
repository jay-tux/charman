package cml

data class PosInfo(
    val file: String,
    val startLine: Int,
    val startColumn: Int
)

open class AstNode(val pos: PosInfo)