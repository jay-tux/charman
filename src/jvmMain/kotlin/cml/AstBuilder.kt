package cml

import com.jaytux.cml_parser.CMLBaseVisitor
import com.jaytux.cml_parser.CMLParser
import org.antlr.v4.runtime.Token

fun Token.getPos(file: String): PosInfo = PosInfo(file, this.line, this.charPositionInLine)

class AstBuilder(private val file: String) : CMLBaseVisitor<AstNode>() {
    private fun <T> nonNull(v: T?): T {
        return v ?: throw AstException.unexpectedNull()
    }

    private inline fun <T, R> nonNull(v: T?, func: (T) -> R): R {
        return v?.let { func(it) } ?: throw AstException.unexpectedNull()
    }

    private inline fun <T, T1, R> nonNull(v: T?, f1: (T) -> T1?, f2: (T1) -> R): R {
        return nonNull(v) { sub ->
            nonNull(f1(sub)) {
                f2(it)
            }
        }
    }

    override fun visitProgram(ctx: CMLParser.ProgramContext?): AstNode {
        return nonNull(ctx) {
            val res = TLDeclSet(it.start.getPos(file))
            it.topLevel().forEach { ctx ->
                when(val v = visit(ctx)) {
                    is TopLevelDecl -> res.declarations.add(v)
                    is TemplateDecl -> res.templates.add(v)
                    is InstanceDecl -> res.instances.add(v)
                    else -> throw AstException.invalidNode(v.javaClass)
                }
            }
            res
        }
    }

    // region Expressions
    override fun visitStringExpr(ctx: com.jaytux.cml_parser.CMLParser.StringExprContext?): AstNode {
        return nonNull(ctx) { ctx2 ->
            StringVal(ctx2.STRING_LIT().text, ctx2.start.getPos(file))
        }
    }

    override fun visitStringLit(ctx: com.jaytux.cml_parser.CMLParser.StringLitContext?): AstNode {
        return nonNull(ctx) { LiteralExpr(visit(it.str) as Value, it.start.getPos(file)) }
    }

    override fun visitIntLit(ctx: com.jaytux.cml_parser.CMLParser.IntLitContext?): AstNode {
        return nonNull(ctx, { it.value }) {
            LiteralExpr(IntVal(it.text.toInt(), it.getPos(file)), it.getPos(file))
        }
    }

    override fun visitBoolLit(ctx: com.jaytux.cml_parser.CMLParser.BoolLitContext?): AstNode {
        return nonNull(ctx, { it.value }) {
            LiteralExpr(BoolVal(it.text == "true", it.getPos(file)), it.getPos(file))
        }
    }

    override fun visitVarExpr(ctx: com.jaytux.cml_parser.CMLParser.VarExprContext?): AstNode {
        return nonNull(ctx, { it.value }) {
            VarExpr(it.text, it.getPos(file))
        }
    }

    override fun visitParenExpr(ctx: com.jaytux.cml_parser.CMLParser.ParenExprContext?): AstNode {
        return nonNull(ctx) { ParenExpr(visit(it.expr()) as Expression, it.start.getPos(file)) }
    }

    override fun visitDiceExpr(ctx: com.jaytux.cml_parser.CMLParser.DiceExprContext?): AstNode {
        return nonNull(ctx) {
            DiceExpr(
                count = visit(it.count) as Expression,
                kind = visit(it.dice) as Expression,
                pos = it.start.getPos(file)
            )
        }
    }

    override fun visitCtorExpr(ctx: com.jaytux.cml_parser.CMLParser.CtorExprContext?): AstNode {
        return nonNull(ctx) {
            CtorExpr(it.type?.text ?: throw AstException.unexpectedNull(), it.start.getPos(file))
        }
    }

    fun mkBinopExpr(
        left: com.jaytux.cml_parser.CMLParser.ExprContext?,
        op: String?,
        right: com.jaytux.cml_parser.CMLParser.ExprContext?,
        opPos: PosInfo?
    ): BinOperExpr {
        return BinOperExpr(
            left = visit(left) as Expression,
            operator = when (op) {
                // arithmetic
                "+" -> BinOperExpr.Op.ADD
                "-" -> BinOperExpr.Op.SUB
                "*" -> BinOperExpr.Op.MUL
                "/" -> BinOperExpr.Op.DIV
                "%" -> BinOperExpr.Op.MOD
                // bitwise
                "&" -> BinOperExpr.Op.B_AND
                "|" -> BinOperExpr.Op.B_OR
                "^" -> BinOperExpr.Op.XOR
                // logical
                "&&" -> BinOperExpr.Op.AND
                "||" -> BinOperExpr.Op.OR
                // comparison
                "==" -> BinOperExpr.Op.EQ
                "!=" -> BinOperExpr.Op.NEQ
                ">" -> BinOperExpr.Op.GT
                ">=" -> BinOperExpr.Op.GEQ
                "<" -> BinOperExpr.Op.LT
                "<=" -> BinOperExpr.Op.LEQ
                null -> throw AstException.unexpectedNull()
                else -> throw AstException.invalidOperator(op)
            },
            right = visit(right) as Expression,
            pos = nonNull(opPos)
        )
    }

    override fun visitMulDivExpr(ctx: com.jaytux.cml_parser.CMLParser.MulDivExprContext?): AstNode =
        mkBinopExpr(ctx?.left, ctx?.op?.text, ctx?.right, ctx?.op?.getPos(file))

    override fun visitAddSubExpr(ctx: com.jaytux.cml_parser.CMLParser.AddSubExprContext?): AstNode =
        mkBinopExpr(ctx?.left, ctx?.op?.text, ctx?.right, ctx?.op?.getPos(file))

    override fun visitModExpr(ctx: com.jaytux.cml_parser.CMLParser.ModExprContext?): AstNode =
        mkBinopExpr(ctx?.left, "%", ctx?.right, ctx?.start?.getPos(file))

    override fun visitCompareExpr(ctx: com.jaytux.cml_parser.CMLParser.CompareExprContext?): AstNode =
        mkBinopExpr(ctx?.left, ctx?.op?.text, ctx?.right, ctx?.op?.getPos(file))

    override fun visitLogicExpr(ctx: com.jaytux.cml_parser.CMLParser.LogicExprContext?): AstNode =
        mkBinopExpr(ctx?.left, ctx?.op?.text, ctx?.right, ctx?.op?.getPos(file))

    override fun visitBitwiseExpr(ctx: com.jaytux.cml_parser.CMLParser.BitwiseExprContext?): AstNode =
        mkBinopExpr(ctx?.left, ctx?.op?.text, ctx?.right, ctx?.op?.getPos(file))

    override fun visitUnaryExpr(ctx: com.jaytux.cml_parser.CMLParser.UnaryExprContext?): AstNode {
        val op = ctx?.op?.text
        return UnOperExpr(
            oper = when (op) {
                "~" -> UnOperExpr.Op.BIN_NEG
                "!" -> UnOperExpr.Op.LOG_NEG
                "-" -> UnOperExpr.Op.UN_MINUS
                null -> throw AstException.unexpectedNull()
                else -> throw AstException.invalidOperator(op)
            },
            target = visit(ctx.value) as Expression,
            pos = nonNull(ctx.start?.getPos(file))
        )
    }

    override fun visitTernaryExpr(ctx: com.jaytux.cml_parser.CMLParser.TernaryExprContext?): AstNode {
        return nonNull(ctx?.condition) { cnd ->
            nonNull(ctx?.bTrue) { btr ->
                nonNull(ctx?.bFalse) { bf ->
                    TernaryExpr(
                        cond = visit(cnd) as Expression,
                        bTrue = visit(btr) as Expression,
                        bFalse = visit(bf) as Expression,
                        pos = nonNull(ctx?.start?.getPos(file))
                    )
                }
            }
        }
    }

    override fun visitRangeExpr(ctx: com.jaytux.cml_parser.CMLParser.RangeExprContext?): AstNode {
        return nonNull(ctx?.begin) { b ->
            nonNull(ctx?.end) { e ->
                RangeExpr(
                    begin = visit(b) as Expression,
                    end = visit(e) as Expression,
                    pos = nonNull(ctx?.start?.getPos(file))
                )
            }
        }
    }

    override fun visitUntilExpr(ctx: com.jaytux.cml_parser.CMLParser.UntilExprContext?): AstNode {
        return nonNull(ctx?.begin) { b ->
            nonNull(ctx?.end) { e ->
                UntilExpr(
                    begin = visit(b) as Expression,
                    end = visit(e) as Expression,
                    pos = nonNull(ctx?.start?.getPos(file))
                )
            }
        }
    }

    override fun visitListExpr(ctx: com.jaytux.cml_parser.CMLParser.ListExprContext?): AstNode {
        return nonNull(ctx?.values) { vs ->
            ListExpr(
                vals = (visit(vs) as ExpressionSet).values,
                pos = nonNull(ctx?.start?.getPos(file))
            )
        }
    }

    override fun visitDictExpr(ctx: com.jaytux.cml_parser.CMLParser.DictExprContext?): AstNode {
        return nonNull(ctx?.values) { kvps ->
            DictExpr(
                kvps = (visit(kvps) as KvpSet).values,
                pos = nonNull(ctx?.start?.getPos(file))
            )
        }
    }

    override fun visitPlaceholderExpr(ctx: com.jaytux.cml_parser.CMLParser.PlaceholderExprContext?): AstNode {
        return nonNull(ctx?.ph) { p ->
            PlaceholderExpr(
                name = p.text.substring(1 until (p.text.length - 1)),
                pos = p.getPos(file)
            )
        }
    }
    // endregion

    // region Statements
    override fun visitExprStmt(ctx: com.jaytux.cml_parser.CMLParser.ExprStmtContext?): AstNode {
        return nonNull(ctx?.e) { ExprStmt(visit(it) as Expression, it.start.getPos(file)) }
    }

    override fun visitVarDeclStmt(ctx: com.jaytux.cml_parser.CMLParser.VarDeclStmtContext?): AstNode {
        return nonNull(ctx?.init) {
            VarDeclStmt(
                name = nonNull(ctx?.name?.text),
                init = visit(it) as Expression,
                pos = it.start.getPos(file)
            )
        }
    }

    override fun visitVarStoreStmt(ctx: com.jaytux.cml_parser.CMLParser.VarStoreStmtContext?): AstNode {
        return nonNull(ctx?.value) { v ->
            VarStoreStmt(
                name = nonNull(ctx?.name?.text),
                upd = visit(v) as Expression,
                pos = v.start.getPos(file)
            )
        }
    }

    override fun visitIfStmt(ctx: com.jaytux.cml_parser.CMLParser.IfStmtContext?): AstNode {
        return nonNull(ctx?.cond) { c ->
            nonNull(ctx?.bTrue) { bt ->
                IfStmt(
                    cond = visit(c) as Expression,
                    bodyTrue = (visit(bt) as StmtSet).contained,
                    bodyFalse = listOf(),
                    pos = nonNull(ctx?.start?.getPos(file))
                )
            }
        }
    }

    override fun visitIfElseStmt(ctx: com.jaytux.cml_parser.CMLParser.IfElseStmtContext?): AstNode {
        return nonNull(ctx?.cond) { c ->
            nonNull(ctx?.bTrue) { bt ->
                nonNull(ctx?.bFalse) { bf ->
                    IfStmt(
                        cond = visit(c) as Expression,
                        bodyTrue = (visit(bt) as StmtSet).contained,
                        bodyFalse = (visit(bf) as StmtSet).contained,
                        pos = nonNull(ctx?.start?.getPos(file))
                    )
                }
            }
        }
    }

    override fun visitWhileStmt(ctx: com.jaytux.cml_parser.CMLParser.WhileStmtContext?): AstNode {
        return nonNull(ctx?.cond) { c ->
            nonNull(ctx?.body) { b ->
                WhileStmt(
                    cond = visit(c) as Expression,
                    body = (visit(b) as StmtSet).contained,
                    pos = nonNull(ctx?.start?.getPos(file))
                )
            }
        }
    }

    override fun visitForStmt(ctx: com.jaytux.cml_parser.CMLParser.ForStmtContext?): AstNode {
        return nonNull(ctx?.range) { r ->
            nonNull(ctx?.body) { b ->
                ForStmt(
                    varN = nonNull(ctx?.varN?.text),
                    range = visit(r) as Expression,
                    body = (visit(b) as StmtSet).contained,
                    pos = nonNull(ctx?.start?.getPos(file))
                )
            }
        }
    }

    override fun visitBreakStmt(ctx: com.jaytux.cml_parser.CMLParser.BreakStmtContext?): AstNode {
        return BreakStmt(nonNull(ctx?.start?.getPos(file)))
    }

    override fun visitReturnStmt(ctx: com.jaytux.cml_parser.CMLParser.ReturnStmtContext?): AstNode {
        return ReturnStmt(null, nonNull(ctx?.start?.getPos(file)))
    }

    override fun visitReturnValStmt(ctx: com.jaytux.cml_parser.CMLParser.ReturnValStmtContext?): AstNode {
        return nonNull(ctx) {
            ReturnStmt(visit(it.v) as Expression, it.start.getPos(file))
        }
    }

    override fun visitCallExpr(ctx: com.jaytux.cml_parser.CMLParser.CallExprContext?): AstNode {
        return nonNull(ctx?.args) { args ->
            FuncCallExpr(
                name = nonNull(ctx?.ftor?.text),
                args = (visit(args) as ExpressionSet).values,
                pos = nonNull(ctx?.start?.getPos(file))
            )
        }
    }
    // endregion

    //region Special cases (aggregators)
    override fun visitArgsList(ctx: com.jaytux.cml_parser.CMLParser.ArgsListContext?): AstNode {
        return nonNull(ctx) {
            if (it.args == null) ExpressionSet(it.start.getPos(file))
            else visit(it.args) as ExpressionSet
        }
    }

    override fun visitNonEmptyArgs(ctx: com.jaytux.cml_parser.CMLParser.NonEmptyArgsContext?): AstNode {
        return nonNull(ctx) {
            (
                    if (it.prev == null) ExpressionSet(it.start.getPos(file))
                    else (visit(it.prev) as ExpressionSet)
                    ).also { set ->
                    set.values.add(visit(it.arg) as Expression)
                }
        }
    }

    override fun visitKvpList(ctx: com.jaytux.cml_parser.CMLParser.KvpListContext?): AstNode {
        return nonNull(ctx) {
            if (it.values == null) KvpSet(it.start.getPos(file))
            visit(it.values) as KvpSet
        }
    }

    override fun visitNonEmptyKvp(ctx: com.jaytux.cml_parser.CMLParser.NonEmptyKvpContext?): AstNode {
        return nonNull(ctx) {
            (
                    if (it.prev == null) KvpSet(it.start.getPos(file))
                    else (visit(it.prev) as KvpSet)
                    ).also { set ->
                    set.values.add(
                        Pair(
                            visit(it.key) as Expression,
                            visit(it.value) as Expression
                        )
                    )
                }
        }
    }

    override fun visitArgDs(ctx: com.jaytux.cml_parser.CMLParser.ArgDsContext?): AstNode {
        return nonNull(ctx) {
            if (it.args == null) ArgsDecl(it.start.getPos(file))
            else visit(it.args) as ArgsDecl
        }
    }

    override fun visitArgDsNonEmpty(ctx: com.jaytux.cml_parser.CMLParser.ArgDsNonEmptyContext?): AstNode {
        return nonNull(ctx) {
            (
                    if (it.args == null) ArgsDecl(it.start.getPos(file))
                    else (visit(it.args) as ArgsDecl)
                    ).also { set ->
                    set.names.add(it.arg?.text ?: throw AstException.unexpectedNull())
                }
        }
    }

    override fun visitDeclSet(ctx: com.jaytux.cml_parser.CMLParser.DeclSetContext?): AstNode {
        return nonNull(ctx) {
            if (it.isEmpty || it.prev == null) DeclSet(it.start.getPos(file))
            else (visit(it.prev) as DeclSet).also { set ->
                when (val v = visit(it.d)) {
                    is FunDecl -> set.functions.add(v)
                    is VarDeclStmt -> set.fields.add(v)
                    else -> throw AstException.invalidNode(v.javaClass)
                }
            }
        }
    }

    override fun visitNoStmt(ctx: com.jaytux.cml_parser.CMLParser.NoStmtContext?): AstNode {
        return StmtSet(nonNull(ctx?.start?.getPos(file)))
    }

    override fun visitStmts(ctx: com.jaytux.cml_parser.CMLParser.StmtsContext?): AstNode {
        return nonNull(ctx) {
            val prev = visit(it.prev) as StmtSet
            prev.contained.add(visit(it.s) as Statement)
            prev
        }
    }
    //endregion

    // region Declarations
    override fun visitFunDecl(ctx: com.jaytux.cml_parser.CMLParser.FunDeclContext?): AstNode {
        return nonNull(ctx?.args) { args ->
            nonNull(ctx?.body) { body ->
                FunDecl(
                    name = nonNull(ctx?.name?.text),
                    argNames = (visit(args) as ArgsDecl).names,
                    body = (visit(body) as StmtSet).contained,
                    declPos = nonNull(ctx?.start?.getPos(file))
                )
            }
        }
    }

    override fun visitFieldDecl(ctx: com.jaytux.cml_parser.CMLParser.FieldDeclContext?): AstNode {
        return nonNull(ctx?.init) { init ->
            VarDeclStmt(
                name = nonNull(ctx?.name?.text),
                init = visit(init) as Expression,
                pos = nonNull(ctx?.start?.getPos(file))
            )
        }
    }

    override fun visitTemplate(ctx: com.jaytux.cml_parser.CMLParser.TemplateContext?): AstNode {
        return nonNull(ctx) { c ->
            val argNames = visit(c.args) as ArgsDecl
            val body = visit(c.body) as DeclSet

            TemplateDecl(
                kind = nonNull(c.kind?.text),
                argNames = argNames.names,
                functions = body.functions.associateBy { it.name },
                fieldsPre = body.fields.associate { Pair(it.name, it.init) },
                declPos = c.start.getPos(file)
            )
        }
    }

    override fun visitInstance(ctx: com.jaytux.cml_parser.CMLParser.InstanceContext?): AstNode {
        return nonNull(ctx) { c ->
            val args = visit(c.args) as ExpressionSet
            InstanceDecl(
                template = nonNull(c.templ?.text),
                name = nonNull(c.name?.text),
                args = args.values,
                declPos = c.start.getPos(file)
            )
        }
    }

    override fun visitType(ctx: com.jaytux.cml_parser.CMLParser.TypeContext?): AstNode {
        return nonNull(ctx) { c ->
            val body = visit(c.body) as DeclSet

            TopLevelDecl(
                kind = nonNull(c.kind?.text),
                name = nonNull(c.name?.text),
                functions = body.functions.associateBy { it.name },
                fieldsPre = body.fields.associate { Pair(it.name, it.init) },
                declPos = c.start.getPos(file)
            ).also {
                it.functions.forEach { (_, v) -> v.parent = it }
            }
        }
    }
    // endregion
}