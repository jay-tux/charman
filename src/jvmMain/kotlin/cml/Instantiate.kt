package cml

fun ExpressionSet.instantiate(instantiations: Map<String, Expression>): ExpressionSet {
    return ExpressionSet(pos).also {  set ->
        set.values.addAll(values.map { it.instantiate(instantiations) })
    }
}

fun KvpSet.instantiate(instantiations: Map<String, Expression>): KvpSet {
    return KvpSet(pos).also { set ->
        set.values.addAll(values.map { (k, v) ->
            Pair(k.instantiate(instantiations), v.instantiate(instantiations))
        })
    }
}

fun StmtSet.instantiate(instantiations: Map<String, Expression>): StmtSet {
    return StmtSet(pos).also { set ->
        set.contained.addAll(contained.map { it.instantiate(instantiations) })
    }
}

fun FunDecl.instantiate(instantiations: Map<String, Expression>): FunDecl {
    return FunDecl(
        name = name,
        argNames = argNames,
        body = body.map{ it.instantiate(instantiations) },
        declPos = pos
    )
}