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