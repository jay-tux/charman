// Generated from /home/jay/hdd/kotlin/charman/antlr/cmlParser.g4 by ANTLR 4.12.0
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link cmlParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface cmlParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link cmlParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(cmlParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by the {@code type}
	 * labeled alternative in {@link cmlParser#topLevel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(cmlParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code instance}
	 * labeled alternative in {@link cmlParser#topLevel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance(cmlParser.InstanceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code template}
	 * labeled alternative in {@link cmlParser#topLevel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplate(cmlParser.TemplateContext ctx);
	/**
	 * Visit a parse tree produced by {@link cmlParser#declSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclSet(cmlParser.DeclSetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code funDecl}
	 * labeled alternative in {@link cmlParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunDecl(cmlParser.FunDeclContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fieldDecl}
	 * labeled alternative in {@link cmlParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldDecl(cmlParser.FieldDeclContext ctx);
	/**
	 * Visit a parse tree produced by the {@code noStmt}
	 * labeled alternative in {@link cmlParser#stmtSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoStmt(cmlParser.NoStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmts}
	 * labeled alternative in {@link cmlParser#stmtSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmts(cmlParser.StmtsContext ctx);
	/**
	 * Visit a parse tree produced by {@link cmlParser#argDs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgDs(cmlParser.ArgDsContext ctx);
	/**
	 * Visit a parse tree produced by {@link cmlParser#argDsNonEmpty}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgDsNonEmpty(cmlParser.ArgDsNonEmptyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStmt(cmlParser.ExprStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varDeclStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclStmt(cmlParser.VarDeclStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varStoreStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarStoreStmt(cmlParser.VarStoreStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(cmlParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifElseStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfElseStmt(cmlParser.IfElseStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(cmlParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStmt(cmlParser.ForStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code breakStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStmt(cmlParser.BreakStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(cmlParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code returnValStmt}
	 * labeled alternative in {@link cmlParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnValStmt(cmlParser.ReturnValStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addSubExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSubExpr(cmlParser.AddSubExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code modExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModExpr(cmlParser.ModExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code untilExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUntilExpr(cmlParser.UntilExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code objCallExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjCallExpr(cmlParser.ObjCallExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fieldExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldExpr(cmlParser.FieldExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpr(cmlParser.ParenExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bitwiseExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitwiseExpr(cmlParser.BitwiseExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code indexExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexExpr(cmlParser.IndexExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringLit}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLit(cmlParser.StringLitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarExpr(cmlParser.VarExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ctorExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCtorExpr(cmlParser.CtorExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpr(cmlParser.UnaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ternaryExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTernaryExpr(cmlParser.TernaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolLit}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolLit(cmlParser.BoolLitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicExpr(cmlParser.LogicExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intLit}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntLit(cmlParser.IntLitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code diceExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDiceExpr(cmlParser.DiceExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code placeholderExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlaceholderExpr(cmlParser.PlaceholderExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code dictExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictExpr(cmlParser.DictExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code callExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallExpr(cmlParser.CallExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code listExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListExpr(cmlParser.ListExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mulDivExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDivExpr(cmlParser.MulDivExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rangeExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRangeExpr(cmlParser.RangeExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code compareExpr}
	 * labeled alternative in {@link cmlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompareExpr(cmlParser.CompareExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link cmlParser#kvpList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKvpList(cmlParser.KvpListContext ctx);
	/**
	 * Visit a parse tree produced by {@link cmlParser#nonEmptyKvp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonEmptyKvp(cmlParser.NonEmptyKvpContext ctx);
	/**
	 * Visit a parse tree produced by {@link cmlParser#argsList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgsList(cmlParser.ArgsListContext ctx);
	/**
	 * Visit a parse tree produced by {@link cmlParser#nonEmptyArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonEmptyArgs(cmlParser.NonEmptyArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link cmlParser#stringExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringExpr(cmlParser.StringExprContext ctx);
}