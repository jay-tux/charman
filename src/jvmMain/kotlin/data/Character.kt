package data

import cml.TopLevelDecl

enum class Alignment {
    LG, NG, CG, LN, N, CN, LE, NE, CE
}

class Character(
    var name: String,
    var alignment: Alignment
) {
    lateinit var race: TopLevelDecl
    lateinit var classes: List<TopLevelDecl>
    lateinit var background: TopLevelDecl
    var abilities: Map<TopLevelDecl, Int> = mapOf()
    var skills: Map<TopLevelDecl, Int> = mapOf()
}