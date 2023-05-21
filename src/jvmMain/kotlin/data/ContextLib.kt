package data

import CMLOut
import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import cml.*
import mapOrEither
import uiData.Character

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
                c.abilities[abbr] = Triple(prev.first, prev.second, prev.third + it.value)
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
            skills.mapOrEither { elem -> elem.ifInstVerify("Skill", pos) }.map { c.skillProficiencies.addAll(it) }
        }
    }.map { }.handle(p)
}

fun addSaveProficiencies(c: Character) = { args: List<Value>, p: PosInfo ->
    argCnt("addSaveProficiencies", 1, args, p).flatMap { (pos, arg) ->
        arg[0].requireList(pos).flatMap { saves ->
            saves.mapOrEither { elem -> elem.ifInstVerify("Ability", pos) }.map { c.saveProficiencies.addAll(it) }
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
                            CMLOut.addWarning("Overwriting racial trait $k for character ${c.name}")
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