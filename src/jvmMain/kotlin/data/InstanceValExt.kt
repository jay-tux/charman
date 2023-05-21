package data

import arrow.core.Either
import arrow.core.flatMap
import cml.*

fun InstanceVal.getInst(name: String, t: String, p: PosInfo): Either<CMLException, InstanceVal> =
    attemptFieldAs<InstanceVal>(name, "instance($t)", p)

fun InstanceVal.getString(name: String, p: PosInfo): Either<CMLException, String> =
    attemptFieldAs<StringVal>(name, "String", p).map { it.value }

fun InstanceVal.getName(p: PosInfo): Either<CMLException, String> = getString("name", p)

fun InstanceVal.getInt(name: String, p: PosInfo): Either<CMLException, Int> =
    attemptFieldAs<IntVal>(name, "Int", p).map { it.value }

fun InstanceVal.getDice(name: String, p: PosInfo): Either<CMLException, DiceVal> =
    attemptFieldAs<DiceVal>(name, "Dice", p)

fun InstanceVal.getDictV(name: String, p: PosInfo): Either<CMLException, DictVal> =
    attemptFieldAs<DictVal>(name, "Dict", p)

fun InstanceVal.getDict(name: String, p: PosInfo): Either<CMLException, MutableMap<Value, Value>> =
    getDictV(name, p).map { it.value }

fun InstanceVal.getVerifyInst(name: String, t: String, p: PosInfo): Either<CMLException, InstanceVal> =
    getInst(name, t, p).flatMap { it.verifyKind(t, p) }

fun InstanceVal.getVerifyInstAndName(name: String, t: String, p: PosInfo): Either<CMLException, Pair<String, InstanceVal>> =
    getVerifyInst(name, t, p).flatMap { it.getName(p).map { n -> Pair(n, it) } }
