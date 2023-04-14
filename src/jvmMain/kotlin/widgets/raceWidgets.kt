package widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import capitalizeOnlyFirst
import data.*
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
fun asiWidget(
    modifier: Modifier = Modifier,
    onSave: (Map<Ability, Int>) -> Unit
) {
    val options = listAbilities()
    var set by remember { mutableStateOf(options.associateWith { 0 }) }

    Column(modifier) {
        LazyColumn {
            set.forEach { (ability, mod) ->
                item {
                    Row {
                        Text(ability.name, Modifier.weight(0.7f))
                        IntField({ it ->
                            set = set.map { (a, m) ->
                                if(a == ability) Pair(a, it)
                                else Pair(a, m)
                            }.toMap()
                        })
                    }
                }
            }
        }
        Button(onClick = { onSave(set.filter { (_, mod) -> mod != 0 }) }) {
            Text("Set Ability Score Increases")
        }
    }
}

@Composable
fun traitsWidget(
    fetcher: () -> List<Trait>,
    modifier: Modifier = Modifier,
    onAdd: (Trait) -> Unit,
    onRemove: (Int) -> Unit,
    onModify: (Int, Trait) -> Unit
) {
    var results by remember { mutableStateOf(fetcher()) }

    LazyColumn {
        itemsIndexed(results) {idx, trait ->
            var name by remember { mutableStateOf(trait.name) }
            var kind by remember { mutableStateOf(trait.kind) }
            var desc by remember { mutableStateOf(trait.description) }
            val save = { transaction {
                trait.name = name
                trait.kind = kind
                trait.description = desc
            } }
            Column {
                Row {
                    OutlinedTextField(name, { name = it }, label = { Text("Trait name") })
                    Spacer(Modifier.width(10.dp))
                    Dropdown(
                        items = TraitKind.values().toList(),
                        onSelect = { kind = TraitKind.values()[it]; },
                        unselected = "(Trait Kind)"
                    ) {
                        item, selected -> DisabledTextFieldOrText(item.name, selected)
                    }
                }
                OutlinedTextField(desc, { desc = it }, label = { Text("Description") }, singleLine = false)
                Row {
                    Button(onClick = { save(); onModify(idx, trait); results = fetcher() }) {
                        Text("Update")
                    }
                    Spacer(Modifier.width(10.dp))
                    Button(onClick = { onRemove(idx); results = fetcher() }) {
                        Text("Remove")
                    }
                }
            }
        }

        item {
            var name by remember { mutableStateOf("") }
            var kind by remember { mutableStateOf(TraitKind.PASSIVE) }
            var desc by remember { mutableStateOf("") }

            val save = { transaction {
                Trait.new { this.name = name; this.kind = kind; this.description = desc; this.tSource = TraitSource.RACE }
            } }
            Row {
                OutlinedTextField(name, { name = it }, label = { Text("Trait name") })
                Spacer(Modifier.width(10.dp))
                Dropdown(
                    items = TraitKind.values().toList(),
                    onSelect = { kind = TraitKind.values()[it] },
                    unselected = "(Trait Kind)"
                ) {
                    item, selected -> DisabledTextFieldOrText(item.name, selected)
                }
            }
            OutlinedTextField(desc, { desc = it }, label = { Text("Description") }, singleLine = false)
            Row {
                Button(onClick = { save(); onAdd(save()); results = fetcher() }) {
                    Text("Add")
                }
                Button(onClick = { name = ""; desc = ""; kind = TraitKind.PASSIVE }) {
                    Text("Clear")
                }
            }
        }
    }
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
fun raceWidget(r: Race, onRemove: () -> Unit) = Column {
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
        TODO("Show Modify on Race Widget")
    }
    if(showRemove) {
        TODO("Show Remove on Race Widget")
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