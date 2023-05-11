package parsing

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.TerminalNode
import parsing.CMLParser.*

// Generated from java-escape by ANTLR 4.11.1
/**
 * This class provides an empty implementation of [CMLListener],
 * which can be extended to create a listener which only needs to handle a subset
 * of the available methods.
 */
class CMLBaseListener : CMLListener {
    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterProgram(ctx: ProgramContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitProgram(ctx: ProgramContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterTopLevel(ctx: CMLParser.TopLevelContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitTopLevel(ctx: CMLParser.TopLevelContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterDeclSet(ctx: DeclSetContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitDeclSet(ctx: DeclSetContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterFunDecl(ctx: FunDeclContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitFunDecl(ctx: FunDeclContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterFieldDecl(ctx: FieldDeclContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitFieldDecl(ctx: FieldDeclContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterNoStmt(ctx: NoStmtContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitNoStmt(ctx: NoStmtContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterStmts(ctx: StmtsContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitStmts(ctx: StmtsContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterArgDs(ctx: ArgDsContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitArgDs(ctx: ArgDsContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterArgDsNonEmpty(ctx: ArgDsNonEmptyContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitArgDsNonEmpty(ctx: ArgDsNonEmptyContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterExprStmt(ctx: ExprStmtContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitExprStmt(ctx: ExprStmtContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterVarDeclStmt(ctx: VarDeclStmtContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitVarDeclStmt(ctx: VarDeclStmtContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterVarStoreStmt(ctx: VarStoreStmtContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitVarStoreStmt(ctx: VarStoreStmtContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterIfStmt(ctx: IfStmtContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitIfStmt(ctx: IfStmtContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterIfElseStmt(ctx: IfElseStmtContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitIfElseStmt(ctx: IfElseStmtContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterWhileStmt(ctx: WhileStmtContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitWhileStmt(ctx: WhileStmtContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterForStmt(ctx: ForStmtContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitForStmt(ctx: ForStmtContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterBreakStmt(ctx: BreakStmtContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitBreakStmt(ctx: BreakStmtContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterReturnStmt(ctx: ReturnStmtContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitReturnStmt(ctx: ReturnStmtContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterReturnValStmt(ctx: ReturnValStmtContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitReturnValStmt(ctx: ReturnValStmtContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterAddSubExpr(ctx: AddSubExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitAddSubExpr(ctx: AddSubExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterModExpr(ctx: ModExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitModExpr(ctx: ModExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterUntilExpr(ctx: UntilExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitUntilExpr(ctx: UntilExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterParenExpr(ctx: ParenExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitParenExpr(ctx: ParenExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterBitwiseExpr(ctx: BitwiseExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitBitwiseExpr(ctx: BitwiseExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterStringLit(ctx: StringLitContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitStringLit(ctx: StringLitContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterVarExpr(ctx: VarExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitVarExpr(ctx: VarExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterCtorExpr(ctx: CtorExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitCtorExpr(ctx: CtorExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterUnaryExpr(ctx: UnaryExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitUnaryExpr(ctx: UnaryExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterTernaryExpr(ctx: TernaryExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitTernaryExpr(ctx: TernaryExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterBoolLit(ctx: BoolLitContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitBoolLit(ctx: BoolLitContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterLogicExpr(ctx: LogicExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitLogicExpr(ctx: LogicExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterIntLit(ctx: IntLitContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitIntLit(ctx: IntLitContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterDiceExpr(ctx: DiceExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitDiceExpr(ctx: DiceExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterDictExpr(ctx: DictExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitDictExpr(ctx: DictExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterCallExpr(ctx: CallExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitCallExpr(ctx: CallExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterListExpr(ctx: ListExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitListExpr(ctx: ListExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterMulDivExpr(ctx: MulDivExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitMulDivExpr(ctx: MulDivExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterRangeExpr(ctx: RangeExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitRangeExpr(ctx: RangeExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterCompareExpr(ctx: CompareExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitCompareExpr(ctx: CompareExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterKvpList(ctx: KvpListContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitKvpList(ctx: KvpListContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterNonEmptyKvp(ctx: NonEmptyKvpContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitNonEmptyKvp(ctx: NonEmptyKvpContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterArgsList(ctx: ArgsListContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitArgsList(ctx: ArgsListContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterNonEmptyArgs(ctx: NonEmptyArgsContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitNonEmptyArgs(ctx: NonEmptyArgsContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterStringExpr(ctx: StringExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitStringExpr(ctx: StringExprContext?) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun enterEveryRule(ctx: ParserRuleContext) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun exitEveryRule(ctx: ParserRuleContext) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun visitTerminal(node: TerminalNode) {}

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation does nothing.
     */
    override fun visitErrorNode(node: ErrorNode) {}
}