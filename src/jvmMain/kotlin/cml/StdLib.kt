package cml

object StdLib {
    private val functions = mutableMapOf<String, (List<Value>, PosInfo) -> Value>()

    fun isStd(name: String): Boolean = functions.containsKey(name)
    fun invoke(name: String, args: List<Value>, callSite: PosInfo): Value? = functions[name]?.let { it(args, callSite) }

    fun addFunction(name: String, callback: (List<Value>, PosInfo) -> Value) {
        if(functions.containsKey(name)) throw LibraryException.stdFunAlreadyExists(name)
        functions[name] = callback
    }

    fun allStdFunc(): Collection<String> = functions.keys
}

object Library {
    private val functions = mutableMapOf<String, FunDecl>()
    private val types = mutableMapOf<String, TopLevelDecl>()

    fun isLibFunc(name: String): Boolean = functions.containsKey(name)
    fun invoke(name: String, args: List<Value>, callSite: PosInfo): Value? = functions[name]?.call(args, callSite)
    fun addFunction(name: String, callback: FunDecl) {
        if(functions.contains(name)) throw LibraryException.libFunAlreadyExists(name)
        functions[name] = callback
    }

    fun isLibType(name: String): Boolean = types.containsKey(name)
    fun construct(name: String, pos: PosInfo): InstanceVal? = types[name]?.let { InstanceVal(it, pos) }
    fun addType(name: String, type: TopLevelDecl) {
        if(types.containsKey(name)) throw LibraryException.libTypeAlreadyExists(name)
        types[name] = type
    }

    fun readyAll() {
        types.forEach { it.value.ready() }
    }
}