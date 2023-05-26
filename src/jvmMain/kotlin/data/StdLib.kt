package data

import CMLOut
import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import cml.*

fun argCntStd(name: String, cnt: Int, args: List<Value>, cp: PosInfo): Either<CMLException, Pair<PosInfo, List<Value>>> {
    val p = StdLib.posInfo(name)
    if(args.size != cnt) return CMLException.argCount(name, cnt, args.size, p, cp).left()
    return Pair(p, args).right()
}

fun appendStr(args: List<Value>): Value {
    val pos = StdLib.posInfo("appendStr")

    return StringVal(args.joinToString("") { it.repr() }, pos)
}

fun appendList(args: List<Value>, pos: PosInfo): Value {
    return argCntStd("appendList", 2, args, pos).flatMap { (p, arg) ->
        arg[0].requireList(p).map { list ->
            ListVal((list + arg[1]).toMutableList(), p)
        }
    }.handle()
}

fun split(args: List<Value>, pos: PosInfo): Value {
    return argCntStd("split", 2, args, pos).flatMap { (p, arg) ->
        arg[0].requireString(p).flatMap { string ->
            arg[1].requireString(p).map { spl ->
                ListVal(string.split(spl).map { StringVal(it, p) }.toMutableList(), p)
            }
        }
    }.handle()
}

fun trim(args: List<Value>, pos: PosInfo): Value {
    return argCntStd("trim", 1, args, pos).flatMap { (p, arg) ->
        arg[0].requireString(p).map {
            StringVal(it, p)
        }
    }.handle()
}

fun inDict(args: List<Value>, pos: PosInfo): Value {
    return argCntStd("inDict", 2, args, pos).flatMap { (p, arg) ->
        arg[0].requireDict(p).map {
            BoolVal(it.containsKey(args[1]), p)
        }
    }.handle()
}

fun empty(args: List<Value>, pos: PosInfo): Value {
    return argCntStd("empty", 1, args, pos).flatMap { (p, arg) ->
        arg[0].requireList(p).map { BoolVal(it.isEmpty(), p) }
    }.handle()
}

fun raise(args: List<Value>, pos: PosInfo): Value {
    val p = StdLib.posInfo("raise")
    if(args.size != 1) throw CMLException.argCount("raise", 2, args.size, p, pos)
    throw CMLException("${args[0].repr()} at $pos")
}

fun log(args: List<Value>, pos: PosInfo): Value {
    val p = StdLib.posInfo("log")
    if(args.size != 1) throw CMLException.argCount("log", 1, args.size, p, pos)
    CMLOut.addInfo(args[0].repr())
    return VoidVal(p)
}

fun isInt(args: List<Value>, pos: PosInfo): Value {
    val p = StdLib.posInfo("isInt")
    if(args.size != 1) throw CMLException.argCount("isInt", 1, args.size, p, pos)
    return BoolVal(args[0] is IntVal, p)
}