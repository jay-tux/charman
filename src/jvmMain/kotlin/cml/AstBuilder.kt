package cml

import org.antlr.v4.runtime.Token
import parsing.CMLBaseVisitor
import parsing.CMLParser

fun Token.getPos(file: String): PosInfo = PosInfo(file, this.line, this.charPositionInLine)

class AstBuilder(private val file: String) : CMLBaseVisitor<AstNode>() {
    private fun <T> nonNull(v: T?): T {
        return v ?: TODO("I don't understand")
    }

    private inline fun <T, R> nonNull(v: T?, func: (T) -> R): R {
        return v?.let { func(it) } ?: TODO("I don't understand")
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
            res.declarations.addAll(it.topLevel().map { d -> visit(d) as TopLevelDecl })
            res
        }
    }

    // region Expressions
    override fun visitStringExpr(ctx: CMLParser.StringExprContext?): AstNode {
        return nonNull(ctx) { ctx2 ->
            StringVal(ctx2.STRING_LIT().text, ctx2.start.getPos(file))
        }
    }

    override fun visitStringLit(ctx: CMLParser.StringLitContext?): AstNode {
        return nonNull(ctx) { LiteralExpr(visit(it.str) as Value, it.start.getPos(file)) }
    }

    override fun visitIntLit(ctx: CMLParser.IntLitContext?): AstNode {
        return nonNull(ctx, { it.value }) {
            LiteralExpr(IntVal(it.text.toInt(), it.getPos(file)), it.getPos(file))
        }
    }

    override fun visitBoolLit(ctx: CMLParser.BoolLitContext?): AstNode {
        return nonNull(ctx, { it.value }) {
            LiteralExpr(BoolVal(it.text == "true", it.getPos(file)), it.getPos(file))
        }
    }

    override fun visitVarExpr(ctx: CMLParser.VarExprContext?): AstNode {
        return nonNull(ctx, { it.value }) {
            VarExpr(it.text, it.getPos(file))
        }
    }

    override fun visitParenExpr(ctx: CMLParser.ParenExprContext?): AstNode {
        return nonNull(ctx) { ParenExpr(visit(it.expr()) as Expression, it.start.getPos(file)) }
    }

    override fun visitDiceExpr(ctx: CMLParser.DiceExprContext?): AstNode {
        return nonNull(ctx) {
            DiceExpr(
                count = visit(it.count) as Expression,
                kind = visit(it.dice) as Expression,
                pos = it.start.getPos(file)
            )
        }
    }

    fun mkBinopExpr(left: CMLParser.ExprContext?, op: String?, right: CMLParser.ExprContext?, opPos: PosInfo?): BinOperExpr {
        return BinOperExpr(
            left = visit(left) as Expression,
            operator = when(op) {
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
                else -> TODO("Shouldn't be possible?")
            },
            right = visit(right) as Expression,
            pos = nonNull(opPos)
        )
    }

    override fun visitMulDivExpr(ctx: CMLParser.MulDivExprContext?): AstNode
        = mkBinopExpr(ctx?.left, ctx?.op?.text, ctx?.right, ctx?.op?.getPos(file))
    override fun visitAddSubExpr(ctx: CMLParser.AddSubExprContext?): AstNode
            = mkBinopExpr(ctx?.left, ctx?.op?.text, ctx?.right, ctx?.op?.getPos(file))
    override fun visitModExpr(ctx: CMLParser.ModExprContext?): AstNode
            = mkBinopExpr(ctx?.left, "%", ctx?.right, ctx?.start?.getPos(file))
    override fun visitCompareExpr(ctx: CMLParser.CompareExprContext?): AstNode
            = mkBinopExpr(ctx?.left, ctx?.op?.text, ctx?.right, ctx?.op?.getPos(file))
    override fun visitLogicExpr(ctx: CMLParser.LogicExprContext?): AstNode
            = mkBinopExpr(ctx?.left, ctx?.op?.text, ctx?.right, ctx?.op?.getPos(file))
    override fun visitBitwiseExpr(ctx: CMLParser.BitwiseExprContext?): AstNode
            = mkBinopExpr(ctx?.left, ctx?.op?.text, ctx?.right, ctx?.op?.getPos(file))

    override fun visitUnaryExpr(ctx: CMLParser.UnaryExprContext?): AstNode {
        return UnOperExpr(
            oper = when(ctx?.op?.text) {
                "~" -> UnOperExpr.Op.BIN_NEG
                "!" -> UnOperExpr.Op.LOG_NEG
                "-" -> UnOperExpr.Op.UN_MINUS
                else -> TODO("Shouldn't be possible?")
            },
            target = visit(ctx.value) as Expression,
            pos = nonNull(ctx.start?.getPos(file))
        )
    }

    override fun visitTernaryExpr(ctx: CMLParser.TernaryExprContext?): AstNode {
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

    override fun visitRangeExpr(ctx: CMLParser.RangeExprContext?): AstNode {
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

    override fun visitUntilExpr(ctx: CMLParser.UntilExprContext?): AstNode {
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

    override fun visitListExpr(ctx: CMLParser.ListExprContext?): AstNode {
        return nonNull(ctx?.values) { vs ->
            ListExpr(
                vals = (visit(vs) as ExpressionSet).values,
                pos = nonNull(ctx?.start?.getPos(file))
            )
        }
    }

    override fun visitDictExpr(ctx: CMLParser.DictExprContext?): AstNode {
        return nonNull(ctx?.values) { kvps ->
            DictExpr(
                kvps = (visit(kvps) as KvpSet).values,
                pos = nonNull(ctx?.start?.getPos(file))
            )
        }
    }
    // endregion

    // region Statements
    override fun visitExprStmt(ctx: CMLParser.ExprStmtContext?): AstNode {
        return nonNull(ctx?.e) { ExprStmt(visit(it) as Expression, it.start.getPos(file)) }
    }

    override fun visitVarDeclStmt(ctx: CMLParser.VarDeclStmtContext?): AstNode {
        return nonNull(ctx?.init) {
            VarDeclStmt(
                name = nonNull(ctx?.name?.text),
                init = visit(it) as Expression,
                pos = it.start.getPos(file)
            )
        }
    }

    override fun visitVarStoreStmt(ctx: CMLParser.VarStoreStmtContext?): AstNode {
        return nonNull(ctx?.value) { v ->
            VarStoreStmt(
                name = nonNull(ctx?.name?.text),
                upd = visit(v) as Expression,
                pos = v.start.getPos(file)
            )
        }
    }

    override fun visitIfStmt(ctx: CMLParser.IfStmtContext?): AstNode {
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

    override fun visitIfElseStmt(ctx: CMLParser.IfElseStmtContext?): AstNode {
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

    override fun visitWhileStmt(ctx: CMLParser.WhileStmtContext?): AstNode {
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

    override fun visitForStmt(ctx: CMLParser.ForStmtContext?): AstNode {
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

    override fun visitBreakStmt(ctx: CMLParser.BreakStmtContext?): AstNode {
        return BreakStmt(nonNull(ctx?.start?.getPos(file)))
    }

    override fun visitReturnStmt(ctx: CMLParser.ReturnStmtContext?): AstNode {
        return ReturnStmt(nonNull(ctx?.start?.getPos(file)))
    }

    override fun visitCallExpr(ctx: CMLParser.CallExprContext?): AstNode {
        return nonNull(ctx?.args) { args ->
            FuncCallStmt(
                name = nonNull(ctx?.ftor?.text),
                args = (visit(args) as ExpressionSet).values,
                pos = nonNull(ctx?.start?.getPos(file))
            )
        }
    }
    // endregion

    //region Special cases (aggregators)
    override fun visitArgsList(ctx: CMLParser.ArgsListContext?): AstNode {
        return nonNull(ctx) {
            if (it.args == null) ExpressionSet(it.start.getPos(file))
            else visit(it.args) as ExpressionSet
        }
    }

    override fun visitNonEmptyArgs(ctx: CMLParser.NonEmptyArgsContext?): AstNode {
        return nonNull(ctx) {
            (
                if(it.prev == null) ExpressionSet(it.start.getPos(file))
                else (visit(it.prev) as ExpressionSet)
            ).also { set ->
                set.values.add(visit(it.prev) as Expression)
            }
        }
    }

    override fun visitKvpList(ctx: CMLParser.KvpListContext?): AstNode {
        return nonNull(ctx) {
            if(it.values == null) KvpSet(it.start.getPos(file))
            visit(it.values) as KvpSet
        }
    }

    override fun visitNonEmptyKvp(ctx: CMLParser.NonEmptyKvpContext?): AstNode {
        return nonNull(ctx) {
            (
                if(it.prev == null) KvpSet(it.start.getPos(file))
                else (visit(it.prev) as KvpSet)
            ).also { set ->
                set.values.add(Pair(
                    visit(it.key) as Expression,
                    visit(it.value) as Expression
                ))
            }
        }
    }

    override fun visitArgDs(ctx: CMLParser.ArgDsContext?): AstNode {
        return nonNull(ctx) {
            if(it.args == null) ArgsDecl(it.start.getPos(file))
            else visit(it.args) as ArgsDecl
        }
    }

    override fun visitArgDsNonEmpty(ctx: CMLParser.ArgDsNonEmptyContext?): AstNode {
        return nonNull(ctx) {
            (
                if(it.args == null) ArgsDecl(it.start.getPos(file))
                else (visit(it.args) as ArgsDecl)
            ).also { set ->
                set.names.add(it.arg?.text ?: TODO("NANI?"))
            }
        }
    }

    override fun visitDeclSet(ctx: CMLParser.DeclSetContext?): AstNode {
        return nonNull(ctx) {
            if(it.isEmpty || it.prev == null) DeclSet(it.start.getPos(file))
            else (visit(it.prev) as DeclSet).also { set ->
                when(val v = visit(it.d)) {
                    is FunDecl -> set.functions.add(v)
                    is VarDeclStmt -> set.fields.add(v)
                    else -> TODO("Eh?")
                }
            }
        }
    }

    override fun visitNoStmt(ctx: CMLParser.NoStmtContext?): AstNode {
        return StmtSet(nonNull(ctx?.start?.getPos(file)))
    }

    override fun visitStmts(ctx: CMLParser.StmtsContext?): AstNode {
        return nonNull(ctx) {
            val prev = visit(it.prev) as StmtSet
            prev.contained.add(visit(it.s) as Statement)
            prev
        }
    }
    //endregion

    // region Declarations
    override fun visitFunDecl(ctx: CMLParser.FunDeclContext?): AstNode {
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

    override fun visitFieldDecl(ctx: CMLParser.FieldDeclContext?): AstNode {
        return nonNull(ctx?.init) { init ->
            VarDeclStmt(
                name = nonNull(ctx?.name?.text),
                init = visit(init) as Expression,
                pos = nonNull(ctx?.start?.getPos(file))
            )
        }
    }

    override fun visitTopLevel(ctx: CMLParser.TopLevelContext?): AstNode {
        return nonNull(ctx) { c ->
            val body = visit(c.body) as DeclSet

            TopLevelDecl(
                kind = nonNull(c.kind?.text),
                name = nonNull(c.name?.text),
                functions = body.functions.associateBy { it.name },
                fieldsPre = body.fields.associate{ Pair(it.name, it.init) },
                declPos = c.start.getPos(file)
            ).also {
                it.functions.forEach { (_, v) -> v.parent = it }
            }
        }
    }
    // endregion
}