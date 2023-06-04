package cml

class AstException(msg: String) : Exception(msg) {
    companion object {
        fun unexpectedNull() = AstException("Unexpected null in AST from ANTLR.")
        fun unexpectedNull(c: String) = AstException("Unexpected null in AST from ANTLR (while parsing `$c').")
        fun invalidOperator(op: String) = AstException("Invalid operator `$op' in AST from ANTLR.")
        fun invalidNode(t: Class<*>) = AstException("Invalid node type `${t.name}' in AST from ANTLR.")
        fun undefinedTemplate(template: String, instance: String, pos: PosInfo) =
            AstException("Attempt to instantiate `$instance' as an instance of unefined template `$template' at $pos")
        fun undefinedPlaceholder(placeholder: String, pos: PosInfo) =
            AstException("Use of undefined placeholder $placeholder at $pos")
        fun templateArgCount(template: String, impl: String, expected: Int, got: Int, pos: PosInfo) =
            AstException("Instantiation of `$template' requires $expected arguments, $got given at $pos\n" +
                    "\tIn the instantiation of `$impl'")
    }
}

class LibraryException(msg: String) : Exception(msg) {
    companion object {
        fun stdFunAlreadyExists(name: String) =
            LibraryException("Standard library function `$name' already exists.")
        fun libFunAlreadyExists(name: String) =
            LibraryException("Library free function `$name' already exists.")
        fun libTypeAlreadyExists(name: String, og: PosInfo, pos: PosInfo) =
            LibraryException("Library type `$name' (declared at $og) already exists (redeclaration at $pos).")
    }
}