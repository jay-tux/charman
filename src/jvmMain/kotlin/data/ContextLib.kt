package data

import CMLOut
import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import cml.*
import filterRight
import mapIndexedOrEither
import mapOrEither
import uiData.*

fun Either<CMLException, Value>.handle() = when(this){
    is Either.Left -> throw value
    is Either.Right -> value
}

fun Either<CMLException, Unit>.handle(p: PosInfo) =
    this.map { VoidVal(p) }.handle()

fun argCnt(name: String, cnt: Int, args: List<Value>, cp: PosInfo): Either<CMLException, Pair<PosInfo, List<Value>>> {
    val p = Library.ctxPos(name)
    if(args.size != cnt) return CMLException.argCount(name, cnt, args.size, p, cp).left()
    return Pair(p, args).right()
}

// region Character Scope
fun CharacterScope.abilityIncrease(args: List<Value>, p: PosInfo): Value {
    return argCnt("abilityIncrease", 2, args, p).flatMap { (pos, arg) ->
        arg[0].ifInstVerifyGetString("abbrev", "Ability", pos).flatMap { (abbr, _) ->
            arg[1].requireInt(pos).map {
                val prev = char.abilities[abbr] ?: throw CMLException("Ability $abbr does not exist for this character at $pos")
                char.abilities[abbr] = AbilityDesc(prev.name, prev.instance, prev.score + it.value)
            }
        }
    }.handle(p)
}

fun CharacterScope.addMaxHP(args: List<Value>, p: PosInfo): Value {
    return argCnt("addMaxHP", 1, args, p).flatMap { (pos, arg) ->
        arg[0].requireInt(pos).map {
            char.hp.value += it.value
        }
    }.handle(p)
}

fun CharacterScope.addRacialTraits(args: List<Value>, p: PosInfo): Value {
    return argCnt("addRacialTraits", 1, args, p).flatMap { (pos, arg) ->
        arg[0].requireList(pos).flatMap { list ->
            list.mapOrEither { elem ->
                elem.ifInstVerifyGetName("Trait", pos).flatMap { (name, inst) ->
                    inst.getString("desc", pos).map {
                        Pair(name, Pair(it, inst))
                    }
                }
            }.map {
                it.forEach{ (k, v) ->
                    if(char.racialTraits.containsKey(k)) {
                        CMLOut.addWarning("Overwriting racial trait $k for character ${char.name}")
                    }
                    char.racialTraits[k] = v
                }
            }
        }
    }.handle(p)
}

fun CharacterScope.addBackgroundTraits(args: List<Value>, p: PosInfo): Value {
    return argCnt("addBackgroundTraits", 1, args, p).flatMap { (pos, arg) ->
        arg[0].requireList(pos).flatMap { list ->
            list.mapOrEither { elem ->
                elem.ifInstVerifyGetName("Trait", pos).flatMap { (name, inst) ->
                    inst.getString("desc", pos).map {
                        Pair(name, Pair(it, inst))
                    }
                }
            }.map {
                it.forEach{ (k, v) ->
                    if(char.backgroundTraits.containsKey(k)) {
                        CMLOut.addWarning("Overwriting background trait $k for character ${char.name}")
                    }
                    char.backgroundTraits[k] = v
                }
            }
        }
    }.handle(p)
}

fun CharacterScope.addLanguages(args: List<Value>, p: PosInfo): Value {
    return argCnt("addLanguages", 1, args, p).flatMap { (pos, arg) ->
        arg[0].requireList(pos).flatMap { skills ->
            skills.mapOrEither { elem ->
                elem.ifInstVerifyGetName("Language", pos).map { (l, inst) ->
                    if(char.languages.containsKey(l)) CMLOut.addWarning("Character ${char.name} already has language $l")
                    char.languages[l] = inst
                }
            }
        }
    }.map { }.handle(p)
}

fun CharacterScope.addSkillProficiencies(args: List<Value>, p: PosInfo): Value {
    return argCnt("addSkillProficiencies", 1, args, p).flatMap { (pos, arg) ->
        arg[0].requireList(pos).flatMap { skills ->
            skills.mapOrEither {
                elem -> elem.ifInstVerify("Skill", pos)
            }.map {
                char.skillProficiencies.addAll(it)
            }
        }
    }.map { }.handle(p)
}

fun CharacterScope.addSaveProficiencies(args: List<Value>, p: PosInfo): Value {
    return argCnt("addSaveProficiencies", 1, args, p).flatMap { (pos, arg) ->
        arg[0].requireList(pos).flatMap { saves ->
            saves.mapOrEither {
                elem -> elem.ifInstVerify("Ability", pos)
            }.map {
                char.saveProficiencies.addAll(it)
            }
        }
    }.map { }.handle(p)
}

fun CharacterScope.addItemProficiencies(args: List<Value>, p: PosInfo): Value {
    return argCnt("addItemProficiencies", 1, args, p).flatMap { (pos, arg) ->
        arg[0].requireList(pos).flatMap { tags ->
            tags.mapOrEither { elem -> elem.requireString(pos) }
        }.map { tags ->
            char.itemProficiencies.value = char.itemProficiencies.value + tags
        }
    }.handle(p)
}

fun CharacterScope.addClassTraits(args: List<Value>, p: PosInfo): Value {
    return argCnt("addClassTraits", 2, args, p).flatMap { (pos, arg) ->
        arg[0].requireString(pos).flatMap { cls ->
            arg[1].requireList(pos).flatMap { list ->
                list.mapOrEither { elem ->
                    elem.ifInstVerifyGetName("LevelUpTrait", pos).flatMap { (name, inst) ->
                        inst.getString("desc", pos).map {
                            Pair(name, Triple(it, cls, inst))
                        }
                    }
                }.map {
                    it.forEach { (k, v) ->
                        if (char.classTraits.containsKey(k)) {
                            CMLOut.addWarning("Overwriting class trait $k for character ${char.name}")
                        }
                        char.classTraits[k] = v
                    }
                }
            }
        }
    }.handle(p)
}

fun CharacterScope.getAbilityMod(args: List<Value>, p: PosInfo): Value {
    return argCnt("getAbilityMod", 1, args, p).flatMap { (pos, arg) ->
        arg[0].ifInstVerify("Ability", pos).map { ability ->
            IntVal(char.abilityMod(ability), p)
        }
    }.handle()
}

fun CharacterScope.getProficiency(args: List<Value>, p: PosInfo): Value {
    return argCnt("getProficiency", 0, args, p).map { _ ->
        IntVal(char.proficiency(), p)
    }.handle()
}

fun CharacterScope.getArmor(args: List<Value>, p: PosInfo): Value {
    return argCnt("getArmor", 0, args, p).map { _ ->
        ListVal(mutableListOf(), p)
    }.handle()
}

fun CharacterScope.addSpell(args: List<Value>, p: PosInfo): Value {
    return argCnt("addSpell", 3, args, p).flatMap { (pos, arg) ->
        arg[0].requireInstance(pos).flatMap { inst ->
            inst.ifInstVerifyGetName("Spell", pos).flatMap { (name, spell) ->
                spell.getString("desc", pos).flatMap { desc ->
                    spell.getVerifyInstAndName("kind", "SpellSchool", pos).flatMap { (school, _) ->
                        spell.getInt("level", pos).flatMap { level ->
                            spell.getString("castingTime", pos).flatMap { cast ->
                                spell.getString("range", pos).flatMap { range ->
                                    spell.getList("components", pos).flatMap { comp ->
                                        comp.value.mapOrEither { c ->
                                            c.requireString(pos)
                                        }
                                    }.flatMap { components ->
                                        spell.getString("duration", pos).flatMap { duration ->
                                            spell.getList("actions", pos).flatMap { actions ->
                                                actions.value.mapOrEither { a ->
                                                    a.requireInstance(pos).flatMap {
                                                        verifyAddSpellAction(char, it, args[1], pos).map { _ ->
                                                            it
                                                        }
                                                    }
                                                }.flatMap { actionsP ->
                                                    args[2].requireString(pos).map { source ->
                                                            char.spells.value = char.spells.value + SpellDesc(
                                                                name = name,
                                                                school = school,
                                                                level = level,
                                                                castingTime = cast,
                                                                range = range,
                                                                components = components,
                                                                duration = duration,
                                                                actions = actionsP,
                                                                desc = desc,
                                                                source = source
                                                            )
                                                        }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }.handle(p)
}

fun verifyAddSpellAction(c: Character, sa: InstanceVal, ab: Value, pos: PosInfo): Either<CMLException, Action> {
    val kind = sa.type.name
    if (kind != "SpellHitAction" && kind != "SpellDCAction") return CMLException.typeError(
        "instance of `SpellHitAction' or `SpellDCAction'",
        sa,
        pos
    ).left()

    return sa.getName(pos).flatMap { name ->
        sa.getList("tags", pos).flatMap { tags ->
            tags.value.mapOrEither { tag -> tag.requireString(pos) }
        }.flatMap { tags ->
            sa.getList("baseAction", pos).flatMap { base ->
                val added = base.value + ab
                verifyBaseAction(c, name, sa.type.name, added, tags, pos).map {
                    c.actions.value += it
                    it
                }
            }
        }
    }
}

fun CharacterScope.addAction(args: List<Value>, p: PosInfo): Value {
    return argCnt("addAction", 1, args, p).flatMap { (pos, arg) ->
        arg[0].ifInstVerify("Action", pos).flatMap { action ->
            action.getName(pos).flatMap { name ->
                action.getList("tags", pos).flatMap { tags ->
                    tags.value.mapOrEither {
                        it.requireString(pos)
                    }
                }.flatMap { tags ->
                    action.getList("baseAction", pos).flatMap { base ->
                        verifyBaseAction(char, name, action.type.name, base.value, tags, pos).map { checked ->
                            char.actions.value = char.actions.value + checked
                        }
                    }
                }
            }
        }
    }.handle(p)
}

fun verifyBaseAction(c: Character, name: String, tag: String, baseData: List<Value>, tags: List<String>, pos: PosInfo): Either<CMLException, Action> {
    return when(tag) {
        "HitAction" -> {
            if(baseData.size < 6)
                return CMLException("Action type `$tag' requires 6 arguments, ${baseData.size} given at $pos").left()
            else {
                baseData[0].requireString(pos).flatMap { kind ->
                    baseData[1].ifInstVerify("Ability", pos).flatMap { ability ->
                        ability.getString("abbrev", pos).flatMap {
                            c.abilities[it]?.right() ?: CMLException.invalidAbility(it, pos).left()
                        }
                    }.flatMap { ability ->
                        baseData[2].requireString(pos).flatMap { reachRange ->
                            baseData[3].requireString(pos).flatMap { target ->
                                baseData[4].requireList(pos).flatMap { prim ->
                                    verifyDamageDesc(prim, pos)
                                }.flatMap { primary ->
                                    baseData[5].requireList(pos).flatMap { sec ->
                                        sec.mapOrEither { elem ->
                                            elem.requireList(pos).flatMap {
                                                verifyDamageDesc(it, pos)
                                            }
                                        }
                                    }.map { secondary ->
                                        AttackAction(name, ability, reachRange, target, primary, secondary, kind, tags)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        "SpellHitAction" -> {
            if(baseData.size < 6) // 6th is given by wrapper function, 5 are code-given
                return CMLException("Action type `$tag' requires 5 arguments, ${baseData.size - 1} given at $pos").left()

            baseData[0].requireString(pos).flatMap { kind ->
                baseData[1].requireString(pos).flatMap { range ->
                    baseData[2].requireString(pos).flatMap { targets ->
                        baseData[3].requireList(pos).flatMap { damage ->
                            damage.mapOrEither { d ->
                                d.requireList(pos).flatMap { verifyDamageDesc(it, pos) }
                            }
                        }.flatMap { damage ->
                            baseData[4].requireBool(pos).flatMap { addMod ->
                                baseData[5].ifInstVerifyGetString("abbrev", "Ability", pos).flatMap { (a, _) ->
                                    c.abilities[a]?.right() ?: CMLException.invalidAbility(a, pos).left()
                                }.map { ability ->
                                    SpellAttackAction(name, ability, range, targets, damage, kind, addMod.value, tags)
                                }
                            }
                        }
                    }
                }
            }
        }

        "SpellDCAction" -> {
            // kind, range/reach, target desc, save kind, damage types
            if(baseData.size < 6) // 6th is given by wrapper function, 5 are code-given
                return CMLException("Action type `$tag' requires 5 arguments, ${baseData.size - 1} given at $pos").left()

            baseData[0].requireString(pos).flatMap { kind ->
                baseData[1].requireString(pos).flatMap { range ->
                    baseData[2].requireString(pos).flatMap { targets ->
                        baseData[3].ifInstVerifyGetName("Ability", pos).flatMap { (_, i) ->
                            i.getString("abbrev", pos).map { i }
                        }.flatMap { save ->
                            baseData[4].requireList(pos).flatMap { damage ->
                                damage.mapOrEither { d ->
                                    d.requireList(pos).flatMap { verifyDamageDesc(it, pos) }
                                }
                            }.flatMap { damage ->
                                baseData[5].ifInstVerifyGetString("abbrev", "Ability", pos).flatMap { (a, _) ->
                                    c.abilities[a]?.right() ?: CMLException.invalidAbility(a, pos).left()
                                }.map { ability ->
                                    SpellDCAction(name, ability, range, targets, damage, kind, save, tags)
                                }
                            }
                        }
                    }
                }
            }
        }

        else -> CMLException("Unknown Action type `$tag' at $pos").left()
    }
}

fun verifyDamageDesc(desc: List<Value>, pos: PosInfo): Either<CMLException, Damage> {
    return if(desc.size < 2) CMLException("Damage description requires at least 2 argument, ${desc.size} given at $pos").left()
    else desc[0].requireDice(pos).flatMap { dice ->
        desc[1].ifInstVerifyGetName("DamageType", pos).map { (name, inst) ->
            Damage(dice, DamageKind(name, inst))
        }
    }
}


fun CharacterScope.setFullCaster(args: List<Value>, p: PosInfo): Value { return setBaseCaster(char, args, "setFullCaster", 1, p) }
fun CharacterScope.setHalfCaster(args: List<Value>, p: PosInfo): Value { return setBaseCaster(char, args, "setHalfCaster", 2, p) }
fun CharacterScope.setThirdCaster(args: List<Value>, p: PosInfo): Value { return setBaseCaster(char, args, "setThirdCaster", 3, p) }

fun setBaseCaster(c: Character, args: List<Value>, fName: String, div: Int, p: PosInfo): Value {
    return argCnt(fName, 1, args, p).flatMap { (pos, arg) ->
        arg[0].requireInt(pos).flatMap {
            if(it.value <= 0) CMLException("Expected level to be >= 1, got ${it.value} at $pos").left()
            else {
                c.casterLevelX6.value += it.value * 6 / div
                Unit.right()
            }
        }
    }.handle(p)
}

fun CharacterScope.setSpecialCaster(args: List<Value>, p: PosInfo): Value {
    return argCnt("setSpecialCaster", 2, args, p).flatMap { (pos, arg) ->
        arg[0].requireList(pos).flatMap { l ->
            if(l.size != 20) CMLException("Spell slots for setSpecialCaster should be a 20 (rows) x 9 (columns) matrix (${l.size} rows given) at $pos").left()
            else l.mapIndexedOrEither { idx, sub ->
                sub.requireList(pos).flatMap { subL ->
                    if(subL.size != 9) CMLException("Spell slots for setSpecialCaster should be a 20 (rows) x 9 (columns) matrix (${subL.size} columns given for row $idx) at $pos").left()
                    else subL.mapOrEither { elem ->
                        elem.requireInt(pos).map { it.value }
                    }
                }
            }
        }.flatMap { slots ->
            arg[1].requireString(pos).map { name ->
                val mod = char.specialCasting.value.toMutableMap()
                mod[name] = Pair(arg[0] as ListVal, slots)
                char.specialCasting.value = mod.toMap()
            }
        }
    }.handle(p)
}
// endregion

// region Choice Scope
fun ChoiceScope.chooseDataByKind(args: List<Value>, p: PosInfo): Value {
    return argCnt("chooseDataByKind", 2, args, p).flatMap { (pos, arg) ->
        args[0].requireString(pos).flatMap { name ->
            args[1].requireString(pos).flatMap { kind ->
                val options = Library.typesByKind(kind).map { type ->
                    InstanceVal(type, pos)
                }

                if(options.isEmpty()) {
                    CMLException("No types with kind `$kind' defined. Error thrown at $pos").left()
                }
                else {
                    requireChoice(name, 1, options).right()
                }
            }
        }
    }.handle()
}

fun ChoiceScope.chooseNByKind(args: List<Value>, p: PosInfo): Value {
    return argCnt("chooseNByKind", 3, args, p).flatMap { (pos, arg) ->
        args[0].requireString(pos).flatMap { name ->
            args[1].requireInt(pos).flatMap { count ->
                args[2].requireString(pos).flatMap { kind ->
                    val options = Library.typesByKind(kind).map { type ->
                        InstanceVal(type, pos)
                    }

                    if(options.isEmpty()) {
                        CMLException("No types with kind `$kind' defined. Error thrown at $pos").left()
                    }
                    else if(options.size < count.value) {
                        CMLException("Cannot choose ${count.value} option(s) from a list of size ${options.size}. Error thrown at $pos").left()
                    }
                    else {
                        requireChoice(name, count.value, options).right()
                    }
                }
            }
        }
    }.handle()
}

fun ChoiceScope.chooseFrom(args: List<Value>, p: PosInfo): Value {
    return argCnt("chooseFrom", 2, args, p).flatMap { (pos, arg) ->
        args[0].requireString(pos).flatMap { name ->
            args[1].requireList(pos).flatMap { options ->
                if(options.isEmpty()) {
                    CMLException("Cannot choose from an empty list. Error thrown at $pos").left()
                }
                else {
                    requireChoice(name, 1, options).right()
                }
            }
        }
    }.handle()
}

fun ChoiceScope.chooseNFrom(args: List<Value>, p: PosInfo): Value {
    return argCnt("chooseNFrom", 3, args, p).flatMap { (pos, arg) ->
        arg[0].requireString(pos).flatMap { name ->
            arg[1].requireInt(pos).flatMap { count ->
                arg[2].requireList(pos).flatMap { options ->
                    if (options.size < count.value) {
                        CMLException("Cannot choose ${count.value} option(s) from a list of size ${options.size}. Error thrown at $pos").left()
                    } else {
                        requireChoice(name, count.value, options).right()
                    }
                }
            }
        }
    }.handle()
}

fun filterSpellsByLevel(pred: (Int) -> Boolean): List<InstanceVal> {
    return Library.typesByKind("Spell").map {
        val inst = InstanceVal(it, ChoiceScope.choicePos)
        inst.getInt("level", ChoiceScope.choicePos).map{ l -> Pair(inst, l) }
    }.filterRight().filter { pred(it.second) }.map { it.first }
}

fun ChoiceScope.chooseNCantrips(args: List<Value>, p: PosInfo): Value {
    return argCnt("chooseNCantrips", 4, args, p).flatMap { (pos, arg) ->
        arg[0].requireString(pos).flatMap { name ->
            arg[1].requireInt(pos).flatMap { count ->
                arg[2].ifInstVerify("Class", pos).flatMap { cls ->
                    arg[3].requireList(pos).flatMap { spellList ->
                        val baseList = spellList.toMutableList()
                        baseList.addAll(filterSpellsByLevel { it == 0 })
                        if(baseList.size < count.value) {
                            CMLException("Cannot choose ${count.value} option(s) from a list of size ${baseList.size}. Error thrown at $pos").left()
                        }
                        else {
                            requireChoice(name, count.value, baseList).right()
                        }
                    }
                }
            }
        }
    }.handle()
}
// endregion