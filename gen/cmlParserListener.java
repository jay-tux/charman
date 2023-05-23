// Generated from /home/jay/hdd/kotlin/charman/antlr/cmlParser.g4 by ANTLR 4.12.0
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link cmlParser}.
 */
public interface cmlParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link cmlParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(cmlParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link cmlParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(cmlParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by the {@code type}
	 * labeled alternative in {@link cmlParser#topLevel}.
	 * @param ctx the parse tree
	 */
	void enterType(cmlParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code type}
	 * labeled alternative in {@link cmlParser#topLevel}.
	 * @param ctx the parse tree
	 */
	void exitType(cmlParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code instance}
	 * labeled alternative in {@link cmlParser#topLevel}.
	 * @param ctx the parse tree
	 */
	void enterInstance(cmlParser.InstanceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code instance}
	 * labeled alternative in {@link cmlParser#topLevel}.
	 * @param ctx the parse tree
	 */
	void exitInstance(cmlParser.InstanceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code template}
	 * labeled alternative in {@link cmlParser#topLevel}.
	 * @param ctx the parse tree
	 */
	void enterTemplate(cmlParser.TemplateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code template}
	 * labeled alternative in {@link cmlParser#topLevel}.
	 * @param ctx the parse tree
	 */
	void exitTemplate(cmlParser.TemplateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code freeFunDecl}
	 * labeled alternative in {@link cmlParser#topLevel}.
	 * @param ctx the parse tree
	 */
	void enterFreeFunDecl(cmlParser.FreeFunDeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code freeFunDecl}
	 * labeled alternative in {@link cmlParser#topLevel}.
	 * @param ctx the parse tree
	 */
	void exitFreeFunDecl(cmlParser.FreeFunDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link cmlParser#declSet}.
	 * @param ctx the parse tree
	 */
	void enterDeclSet(cmlParser.DeclSetContext ctx);
	/**
	 * Exit a parse tree produced by {@link cmlParser#declSet}.
	 * @param ctx the parse tree
	 */
	void exitDeclSet(cmlParser.DeclSetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funDecl}
	 * labeled alternative in {@link cmlParser#decl}.
	 * @param ctx the parse tree
	 */
	void enterFunDecl(cmlParser.FunDeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funDecl}
	 * labeled alternative in {@link cmlParser#decl}.
	 * @param ctx the parse tree
	 */
	void exitFunDecl(cmlParser.FunDeclContext ctx);
	/**
	 * Enter a parse tree produced by the {@code fieldDecl}
	 * labeled alternative in {@link cmlParser#decl}.
	 * @param ctx the parse tree
	 */
	void enterFieldDecl(cmlParser.FieldDeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code fieldDecl}
	 * labeled alternative in {@link cmlParser#decl}.
	 * @param ctx the parse tree
	 */
	void exitFieldDecl(cmlParser.FieldDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link cmlParser#stmtSet}.
	 * @param ctx the parse tree
	 */
	void enterStmtSet(cmlParser.StmtSetContext ctx);
	/**
	 * Exit a parse tree produced by {@link cmlParser#stmtSet}.
	 * @param ctx the parse tree
	 */
	void exitStmtSet(cmlParser.StmtSetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code emptyArgDs}
	 * labeled alternative in {@link cmlParser#argDs}.
	 * @param ctx the parse tree
	 */
	void enterEmptyArgDs(cmlParser.EmptyArgDsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code emptyArgDs}
	 * labeled alternative in {@link cmlParser#argDs}.
	 * @param ctx the parse tree
	 */
	void exitEmptyArgDs(cmlParser.EmptyArgDsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nonEmptyArgDs}
	 * labeled alternative in {@link cmlParser#argDs}.
	 * @param ctx the parse tree
	 */
	void enterNonEmptyArgDs(cmlParser.NonEmptyArgDsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nonEmptyArgDs}
	 * labeled alternative in {@link cmlParser#argDs}.
	 * @param ctx the parse tree
	 */
	void exitNonEmptyArgDs(cmlParser.NonEmptyArgDsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterExprStmt(cmlParser.ExprStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitExprStmt(cmlParser.ExprStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varDeclStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclStmt(cmlParser.VarDeclStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varDeclStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclStmt(cmlParser.VarDeclStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varStoreStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterVarStoreStmt(cmlParser.VarStoreStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varStoreStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitVarStoreStmt(cmlParser.VarStoreStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(cmlParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(cmlParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifElseStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterIfElseStmt(cmlParser.IfElseStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifElseStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitIfElseStmt(cmlParser.IfElseStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(cmlParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(cmlParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterForStmt(cmlParser.ForStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitForStmt(cmlParser.ForStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code breakStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterBreakStmt(cmlParser.BreakStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code breakStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitBreakStmt(cmlParser.BreakStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(cmlParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(cmlParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code returnValStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnValStmt(cmlParser.ReturnValStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code returnValStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnValStmt(cmlParser.ReturnValStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code indexExpr}
	 * labeled alternative in {@link cmlParser#varRef}.
	 * @param ctx the parse tree
	 */
	void enterIndexExpr(cmlParser.IndexExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code indexExpr}
	 * labeled alternative in {@link cmlParser#varRef}.
	 * @param ctx the parse tree
	 */
	void exitIndexExpr(cmlParser.IndexExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varName}
	 * labeled alternative in {@link cmlParser#varRef}.
	 * @param ctx the parse tree
	 */
	void enterVarName(cmlParser.VarNameContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varName}
	 * labeled alternative in {@link cmlParser#varRef}.
	 * @param ctx the parse tree
	 */
	void exitVarName(cmlParser.VarNameContext ctx);
	/**
	 * Enter a parse tree produced by the {@code fieldExpr}
	 * labeled alternative in {@link cmlParser#varRef}.
	 * @param ctx the parse tree
	 */
	void enterFieldExpr(cmlParser.FieldExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code fieldExpr}
	 * labeled alternative in {@link cmlParser#varRef}.
	 * @param ctx the parse tree
	 */
	void exitFieldExpr(cmlParser.FieldExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addSubExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAddSubExpr(cmlParser.AddSubExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addSubExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAddSubExpr(cmlParser.AddSubExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code modExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterModExpr(cmlParser.ModExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code modExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitModExpr(cmlParser.ModExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code untilExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterUntilExpr(cmlParser.UntilExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code untilExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitUntilExpr(cmlParser.UntilExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code objCallExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterObjCallExpr(cmlParser.ObjCallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code objCallExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitObjCallExpr(cmlParser.ObjCallExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code diceLit}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterDiceLit(cmlParser.DiceLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code diceLit}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitDiceLit(cmlParser.DiceLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(cmlParser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(cmlParser.ParenExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bitwiseExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBitwiseExpr(cmlParser.BitwiseExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bitwiseExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBitwiseExpr(cmlParser.BitwiseExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringLit}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterStringLit(cmlParser.StringLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringLit}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitStringLit(cmlParser.StringLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterVarExpr(cmlParser.VarExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitVarExpr(cmlParser.VarExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ctorExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCtorExpr(cmlParser.CtorExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ctorExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCtorExpr(cmlParser.CtorExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpr(cmlParser.UnaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpr(cmlParser.UnaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code floatLit}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFloatLit(cmlParser.FloatLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code floatLit}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFloatLit(cmlParser.FloatLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ternaryExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterTernaryExpr(cmlParser.TernaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ternaryExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitTernaryExpr(cmlParser.TernaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolLit}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBoolLit(cmlParser.BoolLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolLit}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBoolLit(cmlParser.BoolLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLogicExpr(cmlParser.LogicExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLogicExpr(cmlParser.LogicExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intLit}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIntLit(cmlParser.IntLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intLit}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIntLit(cmlParser.IntLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code diceExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterDiceExpr(cmlParser.DiceExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code diceExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitDiceExpr(cmlParser.DiceExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code placeholderExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPlaceholderExpr(cmlParser.PlaceholderExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code placeholderExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPlaceholderExpr(cmlParser.PlaceholderExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dictExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterDictExpr(cmlParser.DictExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dictExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitDictExpr(cmlParser.DictExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code callExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCallExpr(cmlParser.CallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code callExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCallExpr(cmlParser.CallExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code listExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterListExpr(cmlParser.ListExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code listExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitListExpr(cmlParser.ListExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mulDivExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMulDivExpr(cmlParser.MulDivExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mulDivExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMulDivExpr(cmlParser.MulDivExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rangeExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterRangeExpr(cmlParser.RangeExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rangeExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitRangeExpr(cmlParser.RangeExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code compareExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCompareExpr(cmlParser.CompareExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code compareExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCompareExpr(cmlParser.CompareExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code emptyKvp}
	 * labeled alternative in {@link cmlParser#kvpList}.
	 * @param ctx the parse tree
	 */
	void enterEmptyKvp(cmlParser.EmptyKvpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code emptyKvp}
	 * labeled alternative in {@link cmlParser#kvpList}.
	 * @param ctx the parse tree
	 */
	void exitEmptyKvp(cmlParser.EmptyKvpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nonEmptyKvp}
	 * labeled alternative in {@link cmlParser#kvpList}.
	 * @param ctx the parse tree
	 */
	void enterNonEmptyKvp(cmlParser.NonEmptyKvpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nonEmptyKvp}
	 * labeled alternative in {@link cmlParser#kvpList}.
	 * @param ctx the parse tree
	 */
	void exitNonEmptyKvp(cmlParser.NonEmptyKvpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignKvp}
	 * labeled alternative in {@link cmlParser#kvp}.
	 * @param ctx the parse tree
	 */
	void enterAssignKvp(cmlParser.AssignKvpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignKvp}
	 * labeled alternative in {@link cmlParser#kvp}.
	 * @param ctx the parse tree
	 */
	void exitAssignKvp(cmlParser.AssignKvpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code colonKvp}
	 * labeled alternative in {@link cmlParser#kvp}.
	 * @param ctx the parse tree
	 */
	void enterColonKvp(cmlParser.ColonKvpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code colonKvp}
	 * labeled alternative in {@link cmlParser#kvp}.
	 * @param ctx the parse tree
	 */
	void exitColonKvp(cmlParser.ColonKvpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code emptyArgs}
	 * labeled alternative in {@link cmlParser#argsList}.
	 * @param ctx the parse tree
	 */
	void enterEmptyArgs(cmlParser.EmptyArgsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code emptyArgs}
	 * labeled alternative in {@link cmlParser#argsList}.
	 * @param ctx the parse tree
	 */
	void exitEmptyArgs(cmlParser.EmptyArgsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nonEmptyArgs}
	 * labeled alternative in {@link cmlParser#argsList}.
	 * @param ctx the parse tree
	 */
	void enterNonEmptyArgs(cmlParser.NonEmptyArgsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nonEmptyArgs}
	 * labeled alternative in {@link cmlParser#argsList}.
	 * @param ctx the parse tree
	 */
	void exitNonEmptyArgs(cmlParser.NonEmptyArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link cmlParser#stringExpr}.
	 * @param ctx the parse tree
	 */
	void enterStringExpr(cmlParser.StringExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link cmlParser#stringExpr}.
	 * @param ctx the parse tree
	 */
	void exitStringExpr(cmlParser.StringExprContext ctx);
}