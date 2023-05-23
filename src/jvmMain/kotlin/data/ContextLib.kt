package data

import CMLOut
import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import cml.*
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

fun abilityIncrease(c: Character) = { args: List<Value>, p: PosInfo ->
    argCnt("abilityIncrease", 2, args, p).flatMap { (pos, arg) ->
        arg[0].ifInstVerifyGetString("abbrev", "Ability", pos).flatMap { (abbr, _) ->
            arg[1].requireInt(pos).map {
                val prev = c.abilities[abbr] ?: throw CMLException("Ability $abbr does not exist for this character at $pos")
                c.abilities[abbr] = AbilityDesc(prev.name, prev.instance, prev.score + it.value)
            }
        }
    }.handle(p)
}

fun addMaxHP(c: Character) = { args: List<Value>, p: PosInfo ->
    argCnt("addMaxHP", 1, args, p).flatMap { (pos, arg) ->
        arg[0].requireInt(pos).map {
            c.hp.value += it.value
        }
    }.handle(p)
}

fun addRacialTraits(c: Character) = { args: List<Value>, p: PosInfo ->
    argCnt("addRacialTraits", 1, args, p).flatMap { (pos, arg) ->
        arg[0].requireList(pos).flatMap { list ->
            list.mapOrEither { elem ->
                elem.ifInstVerifyGetName("Trait", pos).flatMap { (name, inst) ->
                    inst.getString("desc", pos).map {
                        Pair(name, Pair(it, inst))
                    }
                }
            }.map {
                it.forEach{ (k, v) ->
                    if(c.racialTraits.containsKey(k)) {
                        CMLOut.addWarning("Overwriting racial trait $k for character ${c.name}")
                    }
                    c.racialTraits[k] = v
                }
            }
        }
    }.handle(p)
}

fun chooseDataByKind(c: Character) = { args: List<Value>, p: PosInfo ->
    VoidVal(Library.ctxPos("chooseDataByKind"))
}

fun chooseFrom(c: Character) = { args: List<Value>, p: PosInfo ->
    VoidVal(Library.ctxPos("chooseFrom"))
}

fun addLanguages(c: Character) = { args: List<Value>, p: PosInfo ->
    argCnt("addLanguages", 1, args, p).flatMap { (pos, arg) ->
        arg[0].requireList(pos).flatMap { skills ->
            skills.mapOrEither { elem ->
                elem.ifInstVerifyGetName("Language", pos).map { (l, inst) ->
                    if(c.languages.containsKey(l)) CMLOut.addWarning("Character ${c.name} already has language $l")
                    c.languages[l] = inst
                }
            }
        }
    }.map { }.handle(p)
    VoidVal(Library.ctxPos("addLanguages"))
}

fun addSkillProficiencies(c: Character) = { args: List<Value>, p: PosInfo ->
    argCnt("addSkillProficiencies", 1, args, p).flatMap { (pos, arg) ->
        arg[0].requireList(pos).flatMap { skills ->
            skills.mapOrEither {
                elem -> elem.ifInstVerify("Skill", pos)
            }.map {
                c.skillProficiencies.addAll(it)
            }
        }
    }.map { }.handle(p)
}

fun addSaveProficiencies(c: Character) = { args: List<Value>, p: PosInfo ->
    argCnt("addSaveProficiencies", 1, args, p).flatMap { (pos, arg) ->
        arg[0].requireList(pos).flatMap { saves ->
            saves.mapOrEither {
                elem -> elem.ifInstVerify("Ability", pos)
            }.map {
                c.saveProficiencies.addAll(it)
            }
        }
    }.map { }.handle(p)
}

fun addClassTraits(c: Character) = { args: List<Value>, p: PosInfo ->
    argCnt("addClassTraits", 2, args, p).flatMap { (pos, arg) ->
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
                        if (c.classTraits.containsKey(k)) {
                            CMLOut.addWarning("Overwriting class trait $k for character ${c.name}")
                        }
                        c.classTraits[k] = v
                    }
                }
            }
        }
    }.handle(p)
}

fun getAbilityMod(c: Character) = { args: List<Value>, p: PosInfo ->
    argCnt("getAbilityMod", 1, args, p).flatMap { (pos, arg) ->
        arg[0].ifInstVerify("Ability", pos).map { ability ->
            IntVal(c.abilityMod(ability), p)
        }
    }.handle()
}

fun getProficiency(c: Character) = { args: List<Value>, p: PosInfo ->
    argCnt("getProficiency", 0, args, p).map { _ ->
        IntVal(c.proficiency(), p)
    }.handle()
}

fun getArmor(c: Character) = { args: List<Value>, p: PosInfo ->
    argCnt("getArmor", 0, args, p).map { _ ->
        ListVal(mutableListOf(), p)
    }.handle()
}

fun addSpell(c: Character) = { args: List<Value>, p: PosInfo ->
    argCnt("addSpell", 3, args, p).flatMap { (pos, arg) ->
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
                                                        verifyAddSpellAction(c, it, args[1], pos).map { _ ->
                                                            it
                                                        }
                                                    }
                                                }.flatMap { actionsP ->
                                                    args[2].requireString(pos).map { source ->
                                                            c.spells.value = c.spells.value + SpellDesc(
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
    if(kind != "SpellHitAction" && kind != "SpellDCAction") return CMLException.typeError("instance of `SpellHitAction' or `SpellDCAction'", sa, pos).left()

    return sa.getName(pos).flatMap { name ->
        sa.getString("desc", pos).flatMap { desc ->
            // TODO: add tags
            sa.getList("baseAction", pos).flatMap { base ->
                val added = base.value + ab
                verifyBaseAction(c, name, desc, sa.type.name, added, pos).map {
                    c.actions.value += it
                    it
                }
            }
        }
    }
}

fun addAction(c: Character) = { args: List<Value>, p: PosInfo ->
    argCnt("addAction", 1, args, p).flatMap { (pos, arg) ->
        arg[0].ifInstVerify("Action", pos).flatMap { action ->
            action.getName(pos).flatMap { name ->
                action.getString("desc", pos).flatMap { desc ->
                    // TODO: add tags
                    action.getList("baseAction", pos).flatMap { base ->
                        verifyBaseAction(c, name, desc, action.type.name, base.value, pos).map { checked ->
                            c.actions.value = c.actions.value + checked
                        }
                    }
                }
            }
        }
    }.handle(p)
}

fun verifyBaseAction(c: Character, name: String, desc: String, tag: String, baseData: List<Value>, pos: PosInfo): Either<CMLException, Action> {
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
                                        AttackAction(name, ability, reachRange, target, primary, secondary, desc, kind)
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
                                    SpellAttackAction(name, ability, range, targets, damage, desc, kind, addMod.value)
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
                                    SpellDCAction(name, ability, range, targets, damage, desc, kind, save)
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
