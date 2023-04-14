package widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import capitalizeOnlyFirst
import data.AbilityScoreIncrease
import data.Race
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.math.abs

fun nameStr(i: Int): String = when(i) {
    1 -> "one"
    2 -> "two"
    3 -> "three"
    4 -> "four"
    5 -> "five"
    6 -> "six"
    7 -> "seven"
    8 -> "eight"
    9 -> "nine"
    10 -> "ten"
    else -> "$i"
}

@Composable
fun nameAndDesc(name: String, desc: String) = Row {
    Text(
        text = "$name. ",
        modifier = Modifier,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Bold
    )
    Text(desc)
}

@Composable
fun raceWidget(r: Race, onRemove: () -> Unit, onModified: (Race) -> Unit) = Column {
    var showModify by remember { mutableStateOf(false) }
    var showRemove by remember { mutableStateOf(false) }
    val increases by remember { mutableStateOf(transaction { r.abilityScoreIncreases.map { it } }) }
    val type by remember { mutableStateOf(transaction { r.type }) }
    val languages by remember { mutableStateOf(transaction { r.languages.map { it.name } }) }

    expandableButton(
        { Text(r.name, style = MaterialTheme.typography.h5) },
        {
            Row {
                IconButton({ showModify = true }) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit this data source")
                }
                IconButton({ showRemove = true }) {
                    Icon(Icons.Filled.Close, contentDescription = "Delete this data source")
                }
            }
        }
    ) {
        Row {
            Spacer(Modifier.width(25.dp))
            Column {
                nameAndDesc("Creature Type", "You are a ${type.name}.")
                if(increases.isNotEmpty()) nameAndDesc("Ability Score Increase", allAsi(increases))
                nameAndDesc("Size", "Your size is ${r.size}.")
                nameAndDesc("Speed", "Your base walking speed is ${r.baseWalkingSpeed} feet.")
                transaction {
                    r.traits.map { it }
                }.forEach { trait ->
                    nameAndDesc(trait.name, trait.description)
                }
                if(languages.isNotEmpty() || r.chooseLanguages > 0)
                    nameAndDesc("Languages", "You can speak, read and write ${langString(languages, r.chooseLanguages)}.")
                Spacer(Modifier.width(8.dp))
            }
        }
    }

    if(showModify) {
        updateRaceDialog(r, { showModify = false }, onModified)
    }
    if(showRemove) {
        ConfirmRemoveDialog(
            r.name,
            "This might remove characters you created.",
            onRemove
        ) { showRemove = false }
    }
}

fun langString(ls: List<String>, extra: Int): String {
    val extraS = when(extra) {
        0 -> ""
        1 -> "${nameStr(extra)} language of your choice"
        else -> "${nameStr(extra)} languages of your choice"
    }

    if(ls.isEmpty()) return extraS
    else if(extra > 0) return ls.joinToString(", ") + ", and $extraS"

    return when(ls.size) {
        0 -> ""
        1 -> ls[0]
        2 -> "${ls[0]} and ${ls[1]}"
        else -> ls.subList(0, ls.size - 2).joinToString(", ") + ", and ${ls.last()}"
    }
}

fun allAsi(asis: List<AbilityScoreIncrease>): String {
    var result = asiText(asis[0]).capitalizeOnlyFirst()

    for (i in 1 until asis.size - 1) {
        result += ", " + asiText(asis[i])
    }

    result += ", and ${asiText(asis[asis.size - 1])}."
    return result
}

fun asiText(asi: AbilityScoreIncrease): String {
    val (ability, increase) = transaction { Pair(asi.ability, asi.increase) }
    val ab = if(ability != null) "your ${ability.name}" else "one of your ability scores"

    return if(increase > 0) "$ab increases by $increase"
    else "$ab decreases by ${abs(increase)}"
}