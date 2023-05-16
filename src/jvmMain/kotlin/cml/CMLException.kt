package cml

class CMLException(msg: String) : Exception(msg) {
    companion object {
        fun voidVarException(name: String, pos: PosInfo): CMLException =
            CMLException("Attempt to declare variable `$name' as (void) at $pos")

        fun overwriteImmutable(name: String, dPos: PosInfo, pos: PosInfo): CMLException =
            CMLException("Attempt to overwrite an immutable variable `$name' at $pos'\n" +
                    "\tArguments and iteration variables are immutable\n" +
                    "\t`$name' was declared at $dPos")

        fun redeclareVar(name: String, dPos: PosInfo, pos: PosInfo): CMLException =
            CMLException("Attempt to redeclare variable `$name' at $dPos\n" +
                    "\tPrevious declaration at $pos")

        fun undeclaredVar(name: String, pos: PosInfo): CMLException =
            CMLException("Attempt to use undeclared variable `$name' at $pos")

        fun invalidType(): CMLException = CMLException("A value was detected to have an invalid type.")

        fun typeError(expected: String, got: Value, pos: PosInfo): CMLException {
            val gotT = typeName(got)
            return CMLException("Type error at $pos. Expected a(n) $expected, but got $gotT at $pos")
        }

        fun typeError(desc: String, expected: String, got: Value, pos: PosInfo): CMLException {
            val gotT = typeName(got)
            return CMLException("Type error: expected $desc to be a(n) $expected, but got $gotT at $pos")
        }

        fun invalidBreak(pos: PosInfo): CMLException = CMLException("Break statements can only be used in loops (at $pos).")

        fun argCount(fName: String, expected: Int, got: Int, declPos: PosInfo, pos: PosInfo): CMLException =
            CMLException("Function `$fName' expects $expected arguments, $got given at $pos\n" +
                    "\t`$fName' declared at $declPos")

        fun invokeNonFun(name: String, pos: PosInfo): CMLException =
            CMLException("Attempt to invoke a non-function `$name' at $pos")

        fun constructNonType(name: String, pos: PosInfo): CMLException =
            CMLException("Attempt to construct a non-type `$name' at $pos")

        fun evaluatePlaceholder(name: String, pos: PosInfo): CMLException =
            CMLException("Attempt to evaluate a non-instantiated placeholder `$name' at $pos")

        fun nonObjectVar(field: String, pos: PosInfo): CMLException =
            CMLException("Attempt to access field or member function `$field' on a non-object value at $pos")

        fun invalidField(type: String, field: String, pos: PosInfo): CMLException =
            CMLException("Type `$type' does not have a field named `$field' at $pos")

        fun nonIndexableVar(pos: PosInfo): CMLException =
            CMLException("Attempt to use array-like indexing on a type that doesn't support it at $pos")

        fun invalidIndexType(iterableT: String, desc: String, pos: PosInfo): CMLException =
            CMLException("Indexing into `$iterableT' can only be done using `$desc' at $pos")

        fun listOutOfRange(index: Int, length: Int, pos: PosInfo): CMLException =
            CMLException("Index $index is out of range for iterable (list, range, until) of length $length at $pos")

        fun keyError(keyRepr: String, pos: PosInfo): CMLException =
            CMLException("Key `$keyRepr' is not a valid key for this dict value at $pos")

        fun invalidMemberFunction(type: String, func: String, pos: PosInfo): CMLException =
            CMLException("Type `$type' is not a member function named `$func' at $pos")

        fun internalCopyExecEnv(): CMLException =
            CMLException("Internal error: Copying a non-root execution environment is not allowed")
    }
}