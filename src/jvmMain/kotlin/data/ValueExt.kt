package data

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import cml.*

inline fun <reified T : Value> Value.requires(name: String, p: PosInfo) = when(this) {
    is T -> this.right()
    else -> CMLException.typeError(name, this, p).left()
}

fun Value.requireInstance(p: PosInfo): Either<CMLException, InstanceVal> =
    requires<InstanceVal>("instance(Any)", p)

fun Value.requireInt(p: PosInfo): Either<CMLException, IntVal> =
    requires<IntVal>("Int", p)

fun Value.requireString(p: PosInfo): Either<CMLException, String> =
    requires<StringVal>("String", p).map { it.value }

fun Value.requireDice(p: PosInfo): Either<CMLException, DiceVal> =
    requires<DiceVal>("Dice", p)

fun Value.requireDict(p: PosInfo): Either<CMLException, MutableMap<Value, Value>> =
    requires<DictVal>("Dict", p).map { it.value }

fun Value.requireList(p: PosInfo): Either<CMLException, List<Value>> =
    requires<ListVal>("List", p).map { it.value }

fun Value.requireBool(p: PosInfo): Either<CMLException, BoolVal> =
    requires<BoolVal>("Bool", p)

fun Value.ifInstVerify(t: String, p: PosInfo): Either<CMLException, InstanceVal> =
    requireInstance(p).flatMap { it.verifyKind(t, p) }

fun Value.ifInstVerifyGetString(field: String, t: String, p: PosInfo): Either<CMLException, Pair<String, InstanceVal>> =
    ifInstVerify(t, p).flatMap { it.getString(field, p).map { name -> Pair(name, it) } }

fun Value.ifInstVerifyGetName(t: String, p: PosInfo): Either<CMLException, Pair<String, InstanceVal>> =
    ifInstVerifyGetString("name", t, p)