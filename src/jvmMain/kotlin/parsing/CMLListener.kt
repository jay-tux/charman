package parsing

import org.antlr.v4.runtime.tree.ParseTreeListener
import parsing.CMLParser.*

// Generated from java-escape by ANTLR 4.11.1
/**
 * This interface defines a complete listener for a parse tree produced by
 * [CMLParser].
 */
interface CMLListener : ParseTreeListener {
    /**
     * Enter a parse tree produced by [CMLParser.program].
     * @param ctx the parse tree
     */
    fun enterProgram(ctx: ProgramContext?)

    /**
     * Exit a parse tree produced by [CMLParser.program].
     * @param ctx the parse tree
     */
    fun exitProgram(ctx: ProgramContext?)

    /**
     * Enter a parse tree produced by [CMLParser.topLevel].
     * @param ctx the parse tree
     */
    fun enterTopLevel(ctx: CMLParser.TopLevelContext?)

    /**
     * Exit a parse tree produced by [CMLParser.topLevel].
     * @param ctx the parse tree
     */
    fun exitTopLevel(ctx: CMLParser.TopLevelContext?)

    /**
     * Enter a parse tree produced by [CMLParser.declSet].
     * @param ctx the parse tree
     */
    fun enterDeclSet(ctx: DeclSetContext?)

    /**
     * Exit a parse tree produced by [CMLParser.declSet].
     * @param ctx the parse tree
     */
    fun exitDeclSet(ctx: DeclSetContext?)

    /**
     * Enter a parse tree produced by the `funDecl`
     * labeled alternative in [CMLParser.decl].
     * @param ctx the parse tree
     */
    fun enterFunDecl(ctx: FunDeclContext?)

    /**
     * Exit a parse tree produced by the `funDecl`
     * labeled alternative in [CMLParser.decl].
     * @param ctx the parse tree
     */
    fun exitFunDecl(ctx: FunDeclContext?)

    /**
     * Enter a parse tree produced by the `fieldDecl`
     * labeled alternative in [CMLParser.decl].
     * @param ctx the parse tree
     */
    fun enterFieldDecl(ctx: FieldDeclContext?)

    /**
     * Exit a parse tree produced by the `fieldDecl`
     * labeled alternative in [CMLParser.decl].
     * @param ctx the parse tree
     */
    fun exitFieldDecl(ctx: FieldDeclContext?)

    /**
     * Enter a parse tree produced by the `noStmt`
     * labeled alternative in [CMLParser.stmtSet].
     * @param ctx the parse tree
     */
    fun enterNoStmt(ctx: NoStmtContext?)

    /**
     * Exit a parse tree produced by the `noStmt`
     * labeled alternative in [CMLParser.stmtSet].
     * @param ctx the parse tree
     */
    fun exitNoStmt(ctx: NoStmtContext?)

    /**
     * Enter a parse tree produced by the `stmts`
     * labeled alternative in [CMLParser.stmtSet].
     * @param ctx the parse tree
     */
    fun enterStmts(ctx: StmtsContext?)

    /**
     * Exit a parse tree produced by the `stmts`
     * labeled alternative in [CMLParser.stmtSet].
     * @param ctx the parse tree
     */
    fun exitStmts(ctx: StmtsContext?)

    /**
     * Enter a parse tree produced by [CMLParser.argDs].
     * @param ctx the parse tree
     */
    fun enterArgDs(ctx: ArgDsContext?)

    /**
     * Exit a parse tree produced by [CMLParser.argDs].
     * @param ctx the parse tree
     */
    fun exitArgDs(ctx: ArgDsContext?)

    /**
     * Enter a parse tree produced by [CMLParser.argDsNonEmpty].
     * @param ctx the parse tree
     */
    fun enterArgDsNonEmpty(ctx: ArgDsNonEmptyContext?)

    /**
     * Exit a parse tree produced by [CMLParser.argDsNonEmpty].
     * @param ctx the parse tree
     */
    fun exitArgDsNonEmpty(ctx: ArgDsNonEmptyContext?)

    /**
     * Enter a parse tree produced by the `exprStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     */
    fun enterExprStmt(ctx: ExprStmtContext?)

    /**
     * Exit a parse tree produced by the `exprStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     */
    fun exitExprStmt(ctx: ExprStmtContext?)

    /**
     * Enter a parse tree produced by the `varDeclStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     */
    fun enterVarDeclStmt(ctx: VarDeclStmtContext?)

    /**
     * Exit a parse tree produced by the `varDeclStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     */
    fun exitVarDeclStmt(ctx: VarDeclStmtContext?)

    /**
     * Enter a parse tree produced by the `varStoreStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     */
    fun enterVarStoreStmt(ctx: VarStoreStmtContext?)

    /**
     * Exit a parse tree produced by the `varStoreStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     */
    fun exitVarStoreStmt(ctx: VarStoreStmtContext?)

    /**
     * Enter a parse tree produced by the `ifStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     */
    fun enterIfStmt(ctx: IfStmtContext?)

    /**
     * Exit a parse tree produced by the `ifStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     */
    fun exitIfStmt(ctx: IfStmtContext?)

    /**
     * Enter a parse tree produced by the `ifElseStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     */
    fun enterIfElseStmt(ctx: IfElseStmtContext?)

    /**
     * Exit a parse tree produced by the `ifElseStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     */
    fun exitIfElseStmt(ctx: IfElseStmtContext?)

    /**
     * Enter a parse tree produced by the `whileStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     */
    fun enterWhileStmt(ctx: WhileStmtContext?)

    /**
     * Exit a parse tree produced by the `whileStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     */
    fun exitWhileStmt(ctx: WhileStmtContext?)

    /**
     * Enter a parse tree produced by the `forStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     */
    fun enterForStmt(ctx: ForStmtContext?)

    /**
     * Exit a parse tree produced by the `forStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     */
    fun exitForStmt(ctx: ForStmtContext?)

    /**
     * Enter a parse tree produced by the `breakStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     */
    fun enterBreakStmt(ctx: BreakStmtContext?)

    /**
     * Exit a parse tree produced by the `breakStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     */
    fun exitBreakStmt(ctx: BreakStmtContext?)

    /**
     * Enter a parse tree produced by the `returnStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     */
    fun enterReturnStmt(ctx: ReturnStmtContext?)

    /**
     * Exit a parse tree produced by the `returnStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     */
    fun exitReturnStmt(ctx: ReturnStmtContext?)

    /**
     * Enter a parse tree produced by the `returnValStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     */
    fun enterReturnValStmt(ctx: ReturnValStmtContext?)

    /**
     * Exit a parse tree produced by the `returnValStmt`
     * labeled alternative in [CMLParser.stmt].
     * @param ctx the parse tree
     */
    fun exitReturnValStmt(ctx: ReturnValStmtContext?)

    /**
     * Enter a parse tree produced by the `addSubExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun enterAddSubExpr(ctx: AddSubExprContext?)

    /**
     * Exit a parse tree produced by the `addSubExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun exitAddSubExpr(ctx: AddSubExprContext?)

    /**
     * Enter a parse tree produced by the `modExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun enterModExpr(ctx: ModExprContext?)

    /**
     * Exit a parse tree produced by the `modExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun exitModExpr(ctx: ModExprContext?)

    /**
     * Enter a parse tree produced by the `untilExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun enterUntilExpr(ctx: UntilExprContext?)

    /**
     * Exit a parse tree produced by the `untilExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun exitUntilExpr(ctx: UntilExprContext?)

    /**
     * Enter a parse tree produced by the `parenExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun enterParenExpr(ctx: ParenExprContext?)

    /**
     * Exit a parse tree produced by the `parenExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun exitParenExpr(ctx: ParenExprContext?)

    /**
     * Enter a parse tree produced by the `bitwiseExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun enterBitwiseExpr(ctx: BitwiseExprContext?)

    /**
     * Exit a parse tree produced by the `bitwiseExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun exitBitwiseExpr(ctx: BitwiseExprContext?)

    /**
     * Enter a parse tree produced by the `stringLit`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun enterStringLit(ctx: StringLitContext?)

    /**
     * Exit a parse tree produced by the `stringLit`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun exitStringLit(ctx: StringLitContext?)

    /**
     * Enter a parse tree produced by the `varExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun enterVarExpr(ctx: VarExprContext?)

    /**
     * Exit a parse tree produced by the `varExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun exitVarExpr(ctx: VarExprContext?)

    /**
     * Enter a parse tree produced by the `ctorExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun enterCtorExpr(ctx: CtorExprContext?)

    /**
     * Exit a parse tree produced by the `ctorExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun exitCtorExpr(ctx: CtorExprContext?)

    /**
     * Enter a parse tree produced by the `unaryExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun enterUnaryExpr(ctx: UnaryExprContext?)

    /**
     * Exit a parse tree produced by the `unaryExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun exitUnaryExpr(ctx: UnaryExprContext?)

    /**
     * Enter a parse tree produced by the `ternaryExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun enterTernaryExpr(ctx: TernaryExprContext?)

    /**
     * Exit a parse tree produced by the `ternaryExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun exitTernaryExpr(ctx: TernaryExprContext?)

    /**
     * Enter a parse tree produced by the `boolLit`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun enterBoolLit(ctx: BoolLitContext?)

    /**
     * Exit a parse tree produced by the `boolLit`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun exitBoolLit(ctx: BoolLitContext?)

    /**
     * Enter a parse tree produced by the `logicExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun enterLogicExpr(ctx: LogicExprContext?)

    /**
     * Exit a parse tree produced by the `logicExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun exitLogicExpr(ctx: LogicExprContext?)

    /**
     * Enter a parse tree produced by the `intLit`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun enterIntLit(ctx: IntLitContext?)

    /**
     * Exit a parse tree produced by the `intLit`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun exitIntLit(ctx: IntLitContext?)

    /**
     * Enter a parse tree produced by the `diceExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun enterDiceExpr(ctx: DiceExprContext?)

    /**
     * Exit a parse tree produced by the `diceExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun exitDiceExpr(ctx: DiceExprContext?)

    /**
     * Enter a parse tree produced by the `dictExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun enterDictExpr(ctx: DictExprContext?)

    /**
     * Exit a parse tree produced by the `dictExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun exitDictExpr(ctx: DictExprContext?)

    /**
     * Enter a parse tree produced by the `callExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun enterCallExpr(ctx: CallExprContext?)

    /**
     * Exit a parse tree produced by the `callExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun exitCallExpr(ctx: CallExprContext?)

    /**
     * Enter a parse tree produced by the `listExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun enterListExpr(ctx: ListExprContext?)

    /**
     * Exit a parse tree produced by the `listExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun exitListExpr(ctx: ListExprContext?)

    /**
     * Enter a parse tree produced by the `mulDivExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun enterMulDivExpr(ctx: MulDivExprContext?)

    /**
     * Exit a parse tree produced by the `mulDivExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun exitMulDivExpr(ctx: MulDivExprContext?)

    /**
     * Enter a parse tree produced by the `rangeExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun enterRangeExpr(ctx: RangeExprContext?)

    /**
     * Exit a parse tree produced by the `rangeExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun exitRangeExpr(ctx: RangeExprContext?)

    /**
     * Enter a parse tree produced by the `compareExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun enterCompareExpr(ctx: CompareExprContext?)

    /**
     * Exit a parse tree produced by the `compareExpr`
     * labeled alternative in [CMLParser.expr].
     * @param ctx the parse tree
     */
    fun exitCompareExpr(ctx: CompareExprContext?)

    /**
     * Enter a parse tree produced by [CMLParser.kvpList].
     * @param ctx the parse tree
     */
    fun enterKvpList(ctx: KvpListContext?)

    /**
     * Exit a parse tree produced by [CMLParser.kvpList].
     * @param ctx the parse tree
     */
    fun exitKvpList(ctx: KvpListContext?)

    /**
     * Enter a parse tree produced by [CMLParser.nonEmptyKvp].
     * @param ctx the parse tree
     */
    fun enterNonEmptyKvp(ctx: NonEmptyKvpContext?)

    /**
     * Exit a parse tree produced by [CMLParser.nonEmptyKvp].
     * @param ctx the parse tree
     */
    fun exitNonEmptyKvp(ctx: NonEmptyKvpContext?)

    /**
     * Enter a parse tree produced by [CMLParser.argsList].
     * @param ctx the parse tree
     */
    fun enterArgsList(ctx: ArgsListContext?)

    /**
     * Exit a parse tree produced by [CMLParser.argsList].
     * @param ctx the parse tree
     */
    fun exitArgsList(ctx: ArgsListContext?)

    /**
     * Enter a parse tree produced by [CMLParser.nonEmptyArgs].
     * @param ctx the parse tree
     */
    fun enterNonEmptyArgs(ctx: NonEmptyArgsContext?)

    /**
     * Exit a parse tree produced by [CMLParser.nonEmptyArgs].
     * @param ctx the parse tree
     */
    fun exitNonEmptyArgs(ctx: NonEmptyArgsContext?)

    /**
     * Enter a parse tree produced by [CMLParser.stringExpr].
     * @param ctx the parse tree
     */
    fun enterStringExpr(ctx: StringExprContext?)

    /**
     * Exit a parse tree produced by [CMLParser.stringExpr].
     * @param ctx the parse tree
     */
    fun exitStringExpr(ctx: StringExprContext?)
}