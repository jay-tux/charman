package data

import cml.DiceVal
import kotlin.random.Random
import kotlin.random.nextInt

object DiceRoller {
    fun rollOne(die: DiceVal, mod: Int) = Random.Default.nextInt(1..die.kind) + mod
    fun roll(die: DiceVal, mod: Int) = (1..die.count).sumOf { Random.nextInt(1..die.kind) } + mod
}