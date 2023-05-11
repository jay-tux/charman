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
    }
}