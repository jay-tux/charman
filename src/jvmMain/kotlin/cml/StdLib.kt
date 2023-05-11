package cml

object StdLib {
    private val functions = mutableMapOf<String, (List<Value>) -> Value>()

    fun isStd(name: String): Boolean = functions.containsKey(name)
    fun invoke(name: String, args: List<Value>): Value? = functions[name]?.let { it(args) }

    fun addFunction(name: String, callback: (List<Value>) -> Value) {
        if(functions.containsKey(name)) TODO("Std Lib Error")
        functions[name] = callback
    }

    fun allStdFunc(): Collection<String> = functions.keys
}

object Library {
    private val functions = mutableMapOf<String, FunDecl>()
    private val types = mutableMapOf<String, TopLevelDecl>()

    fun isLibFunc(name: String): Boolean = functions.containsKey(name)
    fun invoke(name: String, args: List<Value>): Value? = functions[name]?.call(args)
    fun addFunction(name: String, callback: FunDecl) {
        if(functions.contains(name)) TODO("Lib Error")
        functions[name] = callback
    }

    fun isLibType(name: String): Boolean = types.containsKey(name)
    fun construct(name: String, pos: PosInfo): InstanceVal? = types[name]?.let { InstanceVal(it, pos) }
    fun addType(name: String, type: TopLevelDecl) {
        if(types.containsKey(name)) TODO("Lib Error")
        types[name] = type
    }
}