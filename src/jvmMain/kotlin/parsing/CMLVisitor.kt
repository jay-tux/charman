package parsing

import org.antlr.v4.runtime.tree.ParseTreeVisitor
import parsing.CMLParser.*

// Generated from java-escape by ANTLR 4.11.1
/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by [CMLParser].
 *
 * @param <T> The return type of the visit operation. Use [Void] for
 * operations with no return type.
</T> */
interface CMLVisitor<T> : ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by [CMLParser.program].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitProgram(ctx: ProgramContext?): T

    /**
     * Visit a parse tree produced by [CMLParser.topLevel].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitTopLevel(ctx: CMLParser.TopLevelContext?): T

    /**
     * Visit a parse tree produced by [CMLParser.declSet].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitDeclSet(ctx: DeclSetContext?): T

    /**
     * Visit a parse tree produced by the `funDecl`
     * labeled alternative in [CMLParser.decl].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitFunDecl(ctx: FunDeclContext?): T

    /**
     * Visit a parse tree produced by the `fieldDecl`
     * labeled alternative in [CMLParser.decl].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitFieldDecl(ctx: FieldDeclContext?): T

    /**
     * Visit a parse tree produced by the `noStmt`
     * labeled alternative in [CMLParser.stmtSet].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitNoStmt(ctx: NoStmtContext?): T

    /**
     * Visit a parse tree produced by the `stmts`
     * labeled alternative in [CMLParser.stmtSet].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitStmts(ctx: StmtsContext?): T

    /**
     * Visit a parse tree produced by [CMLParser.argDs].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitArgDs(ctx: ArgDsContext?): T

    /**
     * Visit a parse tree produced by [CMLParser.argDsNonEmpty].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitArgDsNonEmpty(ctx: ArgDsNonEmptyContext?): T

    /**
     * Visit a parse tree produced by the `exprStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitExprStmt(ctx: ExprStmtContext?): T

    /**
     * Visit a parse tree produced by the `varDeclStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitVarDeclStmt(ctx: VarDeclStmtContext?): T

    /**
     * Visit a parse tree produced by the `varStoreStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitVarStoreStmt(ctx: VarStoreStmtContext?): T

    /**
     * Visit a parse tree produced by the `ifStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitIfStmt(ctx: IfStmtContext?): T

    /**
     * Visit a parse tree produced by the `ifElseStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitIfElseStmt(ctx: IfElseStmtContext?): T

    /**
     * Visit a parse tree produced by the `whileStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitWhileStmt(ctx: WhileStmtContext?): T

    /**
     * Visit a parse tree produced by the `forStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitForStmt(ctx: ForStmtContext?): T

    /**
     * Visit a parse tree produced by the `breakStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitBreakStmt(ctx: BreakStmtContext?): T

    /**
     * Visit a parse tree produced by the `returnStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitReturnStmt(ctx: ReturnStmtContext?): T

    /**
     * Visit a parse tree produced by the `callExpr`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitCallExpr(ctx: CallExprContext?): T

    /**
     * Visit a parse tree produced by the `addSubExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitAddSubExpr(ctx: AddSubExprContext?): T

    /**
     * Visit a parse tree produced by the `modExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitModExpr(ctx: ModExprContext?): T

    /**
     * Visit a parse tree produced by the `untilExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitUntilExpr(ctx: UntilExprContext?): T

    /**
     * Visit a parse tree produced by the `parenExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitParenExpr(ctx: ParenExprContext?): T

    /**
     * Visit a parse tree produced by the `bitwiseExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitBitwiseExpr(ctx: BitwiseExprContext?): T

    /**
     * Visit a parse tree produced by the `stringLit`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitStringLit(ctx: StringLitContext?): T

    /**
     * Visit a parse tree produced by the `varExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitVarExpr(ctx: VarExprContext?): T

    /**
     * Visit a parse tree produced by the `unaryExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitUnaryExpr(ctx: UnaryExprContext?): T

    /**
     * Visit a parse tree produced by the `ternaryExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitTernaryExpr(ctx: TernaryExprContext?): T

    /**
     * Visit a parse tree produced by the `boolLit`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitBoolLit(ctx: BoolLitContext?): T

    /**
     * Visit a parse tree produced by the `logicExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitLogicExpr(ctx: LogicExprContext?): T

    /**
     * Visit a parse tree produced by the `intLit`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitIntLit(ctx: IntLitContext?): T

    /**
     * Visit a parse tree produced by the `diceExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitDiceExpr(ctx: DiceExprContext?): T

    /**
     * Visit a parse tree produced by the `dictExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitDictExpr(ctx: DictExprContext?): T

    /**
     * Visit a parse tree produced by the `listExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitListExpr(ctx: ListExprContext?): T

    /**
     * Visit a parse tree produced by the `mulDivExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitMulDivExpr(ctx: MulDivExprContext?): T

    /**
     * Visit a parse tree produced by the `rangeExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitRangeExpr(ctx: RangeExprContext?): T

    /**
     * Visit a parse tree produced by the `compareExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitCompareExpr(ctx: CompareExprContext?): T

    /**
     * Visit a parse tree produced by [CMLParser.kvpList].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitKvpList(ctx: KvpListContext?): T

    /**
     * Visit a parse tree produced by [CMLParser.nonEmptyKvp].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitNonEmptyKvp(ctx: NonEmptyKvpContext?): T

    /**
     * Visit a parse tree produced by [CMLParser.argsList].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitArgsList(ctx: ArgsListContext?): T

    /**
     * Visit a parse tree produced by [CMLParser.nonEmptyArgs].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitNonEmptyArgs(ctx: NonEmptyArgsContext?): T

    /**
     * Visit a parse tree produced by [CMLParser.stringExpr].
     * @param ctx the parse tree
     * @return the visitor result
     */
    fun visitStringExpr(ctx: StringExprContext?): T
}