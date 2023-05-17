package ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import cml.*
import mapOrEither
import ui.widgets.LazyScrollColumn
import uiData.CharacterData
import withSign

data class CharacterViewData(
    val name: String, val classes: Map<String, Int>, val race: String, val background: String,
    val abilities: Map<String, Int>
)

@Composable
fun CharacterView(data: CharacterViewData) = Column {
    Row {
        Text(data.name, Modifier.weight(0.4f))
        Row(Modifier.weight(0.6f)) {
            LazyScrollColumn(Modifier.weight(0.5f)) {
                items(data.classes.toList()) { (cl, lvl) ->
                    Text("$cl (level $lvl)")
                }
            }

            LazyScrollColumn(Modifier.weight(0.5f)) {
                item {
                    Text("Race: ${data.race}")
                }
                item {
                    Text("Background: ${data.background}")
                }
            }
        }
    }

    Row {
        LazyScrollColumn(Modifier.weight(0.125f)) {
            items(data.abilities.toList()) { (ab, stat) ->
                Column {
                    Text(ab)
                    Row {
                        Spacer(Modifier.width(7.dp))
                        Text("$stat (${((10 - stat) / 2).withSign()})")
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterViewError(message: CMLException) {
    Box(Modifier.fillMaxSize()) {
        Text(
            message.message ?: "Error has no message.",
            Modifier.align(Alignment.Center),
            color = MaterialTheme.colors.error
        )
    }
}

fun extractCharacterViewData(base: InstanceVal): Either<CMLException, CharacterViewData> {
    val p = CharacterData.runtimeRenderPos
    return base.verifyKind("Character", p).flatMap { verified ->
        val res = verified.attemptFieldAs<StringVal>("name", "String", p).flatMap { name ->
            verified.attemptFieldAs<InstanceVal>("race", "instance(Race)", p).flatMap { rPre ->
                rPre.verifyKind("Race", p)
            }.flatMap { rPre ->
                rPre.attemptFieldAs<StringVal>("name", "String", p)
            }.flatMap { race ->
                verified.attemptFieldAs<InstanceVal>("background", "instance(Background)", p).flatMap { bPre ->
                    bPre.verifyKind("Background", p)
                }.flatMap { bPre ->
                    bPre.attemptFieldAs<StringVal>("name", "String", p)
                }.flatMap { background ->
                    verified.attemptFieldAs<DictVal>("classes", "Dict", p).flatMap { csPre ->
                        csPre.value.mapOrEither { (k, v) ->
                            when(k) {
                                is InstanceVal -> k.right()
                                else -> CMLException.typeError("instance(Class)", "key in class dict", k, p).left()
                            }.flatMap{ kVal ->
                                kVal.verifyKind("Class", p)
                            }.flatMap { kKindV ->
                                kKindV.attemptFieldAs<StringVal>("name", "String", p).map { vl -> vl.value }
                            }.flatMap { kVerified ->
                                when(v) {
                                    is IntVal -> Pair(kVerified, v.value).right()
                                    else -> CMLException.typeError("Int", "value in class dict", v, p).left()
                                }
                            }
                        }.flatMap { classes ->
                            verified.attemptFieldAs<DictVal>("abilities", "Dict", p).flatMap { abPre ->
                                abPre.value.mapOrEither { (k, v) ->
                                    when(k) {
                                        is InstanceVal -> k.right()
                                        else -> CMLException.typeError("instance(Ability)", "key in ability dict", k, p).left()
                                    }.flatMap { kVal ->
                                        kVal.verifyKind("Ability", p)
                                    }.flatMap { kKindV ->
                                        kKindV.attemptFieldAs<StringVal>("abbrev", "String", p).map { vl -> vl.value }
                                    }.flatMap { ability ->
                                        when(v) {
                                            is IntVal -> Pair(ability, v.value).right()
                                            else -> CMLException.typeError("Int", "value in ability dict", v, p).left()
                                        }
                                    }
                                }.map { abilities ->
                                    CharacterViewData(name.value, classes, race.value, background.value, abilities)
                                }
                            }
                        }
                    }
                }
            }
        }

        res
    }
}