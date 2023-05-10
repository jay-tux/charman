package parsing

import org.antlr.v4.runtime.*
import org.antlr.v4.runtime.atn.ATN
import org.antlr.v4.runtime.atn.ATNDeserializer
import org.antlr.v4.runtime.atn.ParserATNSimulator
import org.antlr.v4.runtime.atn.PredictionContextCache
import org.antlr.v4.runtime.dfa.DFA
import org.antlr.v4.runtime.tree.ParseTreeListener
import org.antlr.v4.runtime.tree.ParseTreeVisitor
import org.antlr.v4.runtime.tree.TerminalNode

// Generated from java-escape by ANTLR 4.11.1
@Suppress("unused")
class CMLParser(input: TokenStream?) : Parser(input) {
    @Deprecated("")
    override fun getTokenNames(): Array<String?> {
        return Companion.tokenNames
    }

    override fun getVocabulary(): Vocabulary {
        return VOCABULARY
    }

    override fun getGrammarFileName(): String {
        return "java-escape"
    }

    override fun getRuleNames(): Array<String> {
        return Companion.ruleNames
    }

    override fun getSerializedATN(): String {
        return _serializedATN
    }

    override fun getATN(): ATN {
        return _ATN
    }

    class ProgramContext(parent: ParserRuleContext?, invokingState: Int) : ParserRuleContext(parent, invokingState) {
        fun EOF(): TerminalNode {
            return getToken(EOF, 0)
        }

        fun topLevel(): List<TopLevelContext> {
            return getRuleContexts(TopLevelContext::class.java)
        }

        fun topLevel(i: Int): TopLevelContext {
            return getRuleContext(TopLevelContext::class.java, i)
        }

        override fun getRuleIndex(): Int {
            return RULE_program
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterProgram(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitProgram(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitProgram(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun program(): ProgramContext {
        val _localctx = ProgramContext(_ctx, state)
        enterRule(_localctx, 0, RULE_program)
        var _la: Int
        try {
            enterOuterAlt(_localctx, 1)
            run {
                state = 31
                _errHandler.sync(this)
                _la = _input.LA(1)
                while (_la == DATA) {
                    run {
                        run {
                            state = 28
                            topLevel()
                        }
                    }
                    state = 33
                    _errHandler.sync(this)
                    _la = _input.LA(1)
                }
                state = 34
                match(EOF)
            }
        } catch (re: RecognitionException) {
            _localctx.exception = re
            _errHandler.reportError(this, re)
            _errHandler.recover(this, re)
        } finally {
            exitRule()
        }
        return _localctx
    }

    class TopLevelContext(parent: ParserRuleContext?, invokingState: Int) : ParserRuleContext(parent, invokingState) {
        var kind: Token? = null
        var name: Token? = null
        var body: DeclSetContext? = null
        fun DATA(): TerminalNode {
            return getToken(DATA, 0)
        }

        fun B_O(): TerminalNode {
            return getToken(B_O, 0)
        }

        fun B_C(): TerminalNode {
            return getToken(B_C, 0)
        }

        fun IDENT(): List<TerminalNode> {
            return getTokens(IDENT)
        }

        fun IDENT(i: Int): TerminalNode {
            return getToken(IDENT, i)
        }

        fun declSet(): DeclSetContext {
            return getRuleContext(DeclSetContext::class.java, 0)
        }

        override fun getRuleIndex(): Int {
            return RULE_topLevel
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterTopLevel(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitTopLevel(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitTopLevel(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun topLevel(): TopLevelContext {
        val _localctx = TopLevelContext(_ctx, state)
        enterRule(_localctx, 2, RULE_topLevel)
        try {
            enterOuterAlt(_localctx, 1)
            run {
                state = 36
                match(DATA)
                state = 37
                _localctx.kind = match(IDENT)
                state = 38
                _localctx.name = match(IDENT)
                state = 39
                match(B_O)
                state = 40
                _localctx.body = declSet(0)
                state = 41
                match(B_C)
            }
        } catch (re: RecognitionException) {
            _localctx.exception = re
            _errHandler.reportError(this, re)
            _errHandler.recover(this, re)
        } finally {
            exitRule()
        }
        return _localctx
    }

    class DeclSetContext(parent: ParserRuleContext?, invokingState: Int) : ParserRuleContext(parent, invokingState) {
        var prev: DeclSetContext? = null
        var d: DeclContext? = null
        fun declSet(): DeclSetContext {
            return getRuleContext(DeclSetContext::class.java, 0)
        }

        fun decl(): DeclContext {
            return getRuleContext(DeclContext::class.java, 0)
        }

        override fun getRuleIndex(): Int {
            return RULE_declSet
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterDeclSet(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitDeclSet(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitDeclSet(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun declSet(): DeclSetContext {
        return declSet(0)
    }

    @Throws(RecognitionException::class)
    private fun declSet(_p: Int): DeclSetContext {
        val _parentctx = _ctx
        val _parentState = state
        var _localctx = DeclSetContext(_ctx, _parentState)
        var _prevctx = _localctx
        val _startState = 4
        enterRecursionRule(_localctx, 4, RULE_declSet, _p)
        try {
            var _alt: Int
            enterOuterAlt(_localctx, 1)
            run {
                run {}
                _ctx.stop = _input.LT(-1)
                state = 48
                _errHandler.sync(this)
                _alt = interpreter.adaptivePredict(_input, 1, _ctx)
                while (_alt != 2 && _alt != ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent()
                        _prevctx = _localctx
                        run {
                            run {
                                _localctx = DeclSetContext(_parentctx, _parentState)
                                _localctx.prev = _prevctx
                                pushNewRecursionContext(_localctx, _startState, RULE_declSet)
                                state = 44
                                if (!precpred(_ctx, 1)) throw FailedPredicateException(this, "precpred(_ctx, 1)")
                                state = 45
                                _localctx.d = decl()
                            }
                        }
                    }
                    state = 50
                    _errHandler.sync(this)
                    _alt = interpreter.adaptivePredict(_input, 1, _ctx)
                }
            }
        } catch (re: RecognitionException) {
            _localctx.exception = re
            _errHandler.reportError(this, re)
            _errHandler.recover(this, re)
        } finally {
            unrollRecursionContexts(_parentctx)
        }
        return _localctx
    }

    open class DeclContext : ParserRuleContext {
        constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState)

        override fun getRuleIndex(): Int {
            return RULE_decl
        }

        constructor()

        fun copyFrom(ctx: DeclContext?) {
            super.copyFrom(ctx)
        }
    }

    class FieldDeclContext(ctx: DeclContext?) : DeclContext() {
        var name: Token? = null
        var init: ExprContext? = null
        fun FIELD(): TerminalNode {
            return getToken(FIELD, 0)
        }

        fun ASSIGN(): TerminalNode {
            return getToken(ASSIGN, 0)
        }

        fun SEMI(): TerminalNode {
            return getToken(SEMI, 0)
        }

        fun IDENT(): TerminalNode {
            return getToken(IDENT, 0)
        }

        fun expr(): ExprContext {
            return getRuleContext(ExprContext::class.java, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterFieldDecl(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitFieldDecl(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitFieldDecl(this) else visitor.visitChildren(
                this
            )
        }
    }

    class FunDeclContext(ctx: DeclContext?) : DeclContext() {
        var name: Token? = null
        var args: ArgDsContext? = null
        var body: StmtSetContext? = null
        fun FUN(): TerminalNode {
            return getToken(FUN, 0)
        }

        fun P_O(): TerminalNode {
            return getToken(P_O, 0)
        }

        fun P_C(): TerminalNode {
            return getToken(P_C, 0)
        }

        fun B_O(): TerminalNode {
            return getToken(B_O, 0)
        }

        fun B_C(): TerminalNode {
            return getToken(B_C, 0)
        }

        fun IDENT(): TerminalNode {
            return getToken(IDENT, 0)
        }

        fun argDs(): ArgDsContext {
            return getRuleContext(ArgDsContext::class.java, 0)
        }

        fun stmtSet(): StmtSetContext {
            return getRuleContext(StmtSetContext::class.java, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterFunDecl(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitFunDecl(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitFunDecl(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun decl(): DeclContext {
        var _localctx = DeclContext(_ctx, state)
        enterRule(_localctx, 6, RULE_decl)
        try {
            state = 66
            _errHandler.sync(this)
            when (_input.LA(1)) {
                FUN -> {
                    _localctx = FunDeclContext(_localctx)
                    enterOuterAlt(_localctx, 1)
                    run {
                        state = 51
                        match(FUN)
                        state = 52
                        (_localctx as FunDeclContext).name = match(IDENT)
                        state = 53
                        match(P_O)
                        state = 54
                        (_localctx as FunDeclContext).args = argDs()
                        state = 55
                        match(P_C)
                        state = 56
                        match(B_O)
                        state = 57
                        (_localctx as FunDeclContext).body = stmtSet(0)
                        state = 58
                        match(B_C)
                    }
                }

                FIELD -> {
                    _localctx = FieldDeclContext(_localctx)
                    enterOuterAlt(_localctx, 2)
                    run {
                        state = 60
                        match(FIELD)
                        state = 61
                        _localctx.name = match(IDENT)
                        state = 62
                        match(ASSIGN)
                        state = 63
                        _localctx.init = expr(0)
                        state = 64
                        match(SEMI)
                    }
                }

                else -> throw NoViableAltException(this)
            }
        } catch (re: RecognitionException) {
            _localctx.exception = re
            _errHandler.reportError(this, re)
            _errHandler.recover(this, re)
        } finally {
            exitRule()
        }
        return _localctx
    }

    open class StmtSetContext : ParserRuleContext {
        constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState)

        override fun getRuleIndex(): Int {
            return RULE_stmtSet
        }

        constructor()

        fun copyFrom(ctx: StmtSetContext?) {
            super.copyFrom(ctx)
        }
    }

    class NoStmtContext(ctx: StmtSetContext?) : StmtSetContext() {
        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterNoStmt(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitNoStmt(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitNoStmt(this) else visitor.visitChildren(
                this
            )
        }
    }

    class StmtsContext(ctx: StmtSetContext?) : StmtSetContext() {
        var prev: StmtSetContext? = null
        var s: StmtContext? = null
        fun stmtSet(): StmtSetContext {
            return getRuleContext(StmtSetContext::class.java, 0)
        }

        fun stmt(): StmtContext {
            return getRuleContext(StmtContext::class.java, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterStmts(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitStmts(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitStmts(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun stmtSet(): StmtSetContext {
        return stmtSet(0)
    }

    @Throws(RecognitionException::class)
    private fun stmtSet(_p: Int): StmtSetContext {
        val _parentctx = _ctx
        val _parentState = state
        var _localctx = StmtSetContext(_ctx, _parentState)
        var _prevctx = _localctx
        val _startState = 8
        enterRecursionRule(_localctx, 8, RULE_stmtSet, _p)
        try {
            var _alt: Int
            enterOuterAlt(_localctx, 1)
            run {
                run {
                    _localctx = NoStmtContext(_localctx)
                    _ctx = _localctx
                    _prevctx = _localctx
                }
                _ctx.stop = _input.LT(-1)
                state = 73
                _errHandler.sync(this)
                _alt = interpreter.adaptivePredict(_input, 3, _ctx)
                while (_alt != 2 && _alt != ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent()
                        _prevctx = _localctx
                        run {
                            run {
                                _localctx = StmtsContext(StmtSetContext(_parentctx, _parentState))
                                (_localctx as StmtsContext).prev = _prevctx
                                pushNewRecursionContext(_localctx, _startState, RULE_stmtSet)
                                state = 69
                                if (!precpred(_ctx, 1)) throw FailedPredicateException(this, "precpred(_ctx, 1)")
                                state = 70
                                (_localctx as StmtsContext).s = stmt()
                            }
                        }
                    }
                    state = 75
                    _errHandler.sync(this)
                    _alt = interpreter.adaptivePredict(_input, 3, _ctx)
                }
            }
        } catch (re: RecognitionException) {
            _localctx.exception = re
            _errHandler.reportError(this, re)
            _errHandler.recover(this, re)
        } finally {
            unrollRecursionContexts(_parentctx)
        }
        return _localctx
    }

    class ArgDsContext(parent: ParserRuleContext?, invokingState: Int) : ParserRuleContext(parent, invokingState) {
        var args: ArgDsNonEmptyContext? = null
        fun argDsNonEmpty(): ArgDsNonEmptyContext {
            return getRuleContext(ArgDsNonEmptyContext::class.java, 0)
        }

        override fun getRuleIndex(): Int {
            return RULE_argDs
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterArgDs(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitArgDs(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitArgDs(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun argDs(): ArgDsContext {
        val _localctx = ArgDsContext(_ctx, state)
        enterRule(_localctx, 10, RULE_argDs)
        try {
            state = 78
            _errHandler.sync(this)
            when (_input.LA(1)) {
                P_C -> {
                    enterOuterAlt(_localctx, 1)
                    run {}
                }

                IDENT -> {
                    enterOuterAlt(_localctx, 2)
                    run {
                        state = 77
                        _localctx.args = argDsNonEmpty(0)
                    }
                }

                else -> throw NoViableAltException(this)
            }
        } catch (re: RecognitionException) {
            _localctx.exception = re
            _errHandler.reportError(this, re)
            _errHandler.recover(this, re)
        } finally {
            exitRule()
        }
        return _localctx
    }

    class ArgDsNonEmptyContext(parent: ParserRuleContext?, invokingState: Int) :
        ParserRuleContext(parent, invokingState) {
        var args: ArgDsNonEmptyContext? = null
        var arg: Token? = null
        fun IDENT(): TerminalNode {
            return getToken(IDENT, 0)
        }

        fun COMMA(): TerminalNode {
            return getToken(COMMA, 0)
        }

        fun argDsNonEmpty(): ArgDsNonEmptyContext {
            return getRuleContext(ArgDsNonEmptyContext::class.java, 0)
        }

        override fun getRuleIndex(): Int {
            return RULE_argDsNonEmpty
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterArgDsNonEmpty(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitArgDsNonEmpty(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitArgDsNonEmpty(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun argDsNonEmpty(): ArgDsNonEmptyContext {
        return argDsNonEmpty(0)
    }

    @Throws(RecognitionException::class)
    private fun argDsNonEmpty(_p: Int): ArgDsNonEmptyContext {
        val _parentctx = _ctx
        val _parentState = state
        var _localctx = ArgDsNonEmptyContext(_ctx, _parentState)
        var _prevctx = _localctx
        val _startState = 12
        enterRecursionRule(_localctx, 12, RULE_argDsNonEmpty, _p)
        try {
            var _alt: Int
            enterOuterAlt(_localctx, 1)
            run {
                run {
                    state = 81
                    _localctx.arg = match(IDENT)
                }
                _ctx.stop = _input.LT(-1)
                state = 88
                _errHandler.sync(this)
                _alt = interpreter.adaptivePredict(_input, 5, _ctx)
                while (_alt != 2 && _alt != ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent()
                        _prevctx = _localctx
                        run {
                            run {
                                _localctx = ArgDsNonEmptyContext(_parentctx, _parentState)
                                _localctx.args = _prevctx
                                pushNewRecursionContext(_localctx, _startState, RULE_argDsNonEmpty)
                                state = 83
                                if (!precpred(_ctx, 1)) throw FailedPredicateException(this, "precpred(_ctx, 1)")
                                state = 84
                                match(COMMA)
                                state = 85
                                _localctx.arg = match(IDENT)
                            }
                        }
                    }
                    state = 90
                    _errHandler.sync(this)
                    _alt = interpreter.adaptivePredict(_input, 5, _ctx)
                }
            }
        } catch (re: RecognitionException) {
            _localctx.exception = re
            _errHandler.reportError(this, re)
            _errHandler.recover(this, re)
        } finally {
            unrollRecursionContexts(_parentctx)
        }
        return _localctx
    }

    open class StmtContext : ParserRuleContext {
        constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState)

        override fun getRuleIndex(): Int {
            return RULE_stmt
        }

        constructor()

        fun copyFrom(ctx: StmtContext?) {
            super.copyFrom(ctx)
        }
    }

    class ExprStmtContext(ctx: StmtContext?) : StmtContext() {
        var e: ExprContext? = null
        fun SEMI(): TerminalNode {
            return getToken(SEMI, 0)
        }

        fun expr(): ExprContext {
            return getRuleContext(ExprContext::class.java, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterExprStmt(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitExprStmt(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitExprStmt(this) else visitor.visitChildren(
                this
            )
        }
    }

    class ForStmtContext(ctx: StmtContext?) : StmtContext() {
        var varN: Token? = null
        var range: ExprContext? = null
        var body: StmtSetContext? = null
        fun FOR(): TerminalNode {
            return getToken(FOR, 0)
        }

        fun P_O(): TerminalNode {
            return getToken(P_O, 0)
        }

        fun IN(): TerminalNode {
            return getToken(IN, 0)
        }

        fun P_C(): TerminalNode {
            return getToken(P_C, 0)
        }

        fun B_O(): TerminalNode {
            return getToken(B_O, 0)
        }

        fun B_C(): TerminalNode {
            return getToken(B_C, 0)
        }

        fun IDENT(): TerminalNode {
            return getToken(IDENT, 0)
        }

        fun expr(): ExprContext {
            return getRuleContext(ExprContext::class.java, 0)
        }

        fun stmtSet(): StmtSetContext {
            return getRuleContext(StmtSetContext::class.java, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterForStmt(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitForStmt(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitForStmt(this) else visitor.visitChildren(
                this
            )
        }
    }

    class WhileStmtContext(ctx: StmtContext?) : StmtContext() {
        var cond: ExprContext? = null
        var body: StmtSetContext? = null
        fun WHILE(): TerminalNode {
            return getToken(WHILE, 0)
        }

        fun P_O(): TerminalNode {
            return getToken(P_O, 0)
        }

        fun P_C(): TerminalNode {
            return getToken(P_C, 0)
        }

        fun B_O(): TerminalNode {
            return getToken(B_O, 0)
        }

        fun B_C(): TerminalNode {
            return getToken(B_C, 0)
        }

        fun expr(): ExprContext {
            return getRuleContext(ExprContext::class.java, 0)
        }

        fun stmtSet(): StmtSetContext {
            return getRuleContext(StmtSetContext::class.java, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterWhileStmt(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitWhileStmt(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitWhileStmt(this) else visitor.visitChildren(
                this
            )
        }
    }

    class IfStmtContext(ctx: StmtContext?) : StmtContext() {
        var cond: ExprContext? = null
        var bTrue: StmtSetContext? = null
        fun IF(): TerminalNode {
            return getToken(IF, 0)
        }

        fun P_O(): TerminalNode {
            return getToken(P_O, 0)
        }

        fun P_C(): TerminalNode {
            return getToken(P_C, 0)
        }

        fun B_O(): TerminalNode {
            return getToken(B_O, 0)
        }

        fun B_C(): TerminalNode {
            return getToken(B_C, 0)
        }

        fun expr(): ExprContext {
            return getRuleContext(ExprContext::class.java, 0)
        }

        fun stmtSet(): StmtSetContext {
            return getRuleContext(StmtSetContext::class.java, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterIfStmt(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitIfStmt(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitIfStmt(this) else visitor.visitChildren(
                this
            )
        }
    }

    class IfElseStmtContext(ctx: StmtContext?) : StmtContext() {
        var cond: ExprContext? = null
        var bTrue: StmtSetContext? = null
        var bFalse: StmtSetContext? = null
        fun IF(): TerminalNode {
            return getToken(IF, 0)
        }

        fun P_O(): TerminalNode {
            return getToken(P_O, 0)
        }

        fun P_C(): TerminalNode {
            return getToken(P_C, 0)
        }

        fun B_O(): List<TerminalNode> {
            return getTokens(B_O)
        }

        fun B_O(i: Int): TerminalNode {
            return getToken(B_O, i)
        }

        fun B_C(): List<TerminalNode> {
            return getTokens(B_C)
        }

        fun B_C(i: Int): TerminalNode {
            return getToken(B_C, i)
        }

        fun ELSE(): TerminalNode {
            return getToken(ELSE, 0)
        }

        fun expr(): ExprContext {
            return getRuleContext(ExprContext::class.java, 0)
        }

        fun stmtSet(): List<StmtSetContext> {
            return getRuleContexts(StmtSetContext::class.java)
        }

        fun stmtSet(i: Int): StmtSetContext {
            return getRuleContext(StmtSetContext::class.java, i)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterIfElseStmt(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitIfElseStmt(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitIfElseStmt(this) else visitor.visitChildren(
                this
            )
        }
    }

    class BreakStmtContext(ctx: StmtContext?) : StmtContext() {
        fun BREAK(): TerminalNode {
            return getToken(BREAK, 0)
        }

        fun SEMI(): TerminalNode {
            return getToken(SEMI, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterBreakStmt(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitBreakStmt(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitBreakStmt(this) else visitor.visitChildren(
                this
            )
        }
    }

    class VarStoreStmtContext(ctx: StmtContext?) : StmtContext() {
        var name: Token? = null
        var value: ExprContext? = null
        fun ASSIGN(): TerminalNode {
            return getToken(ASSIGN, 0)
        }

        fun SEMI(): TerminalNode {
            return getToken(SEMI, 0)
        }

        fun IDENT(): TerminalNode {
            return getToken(IDENT, 0)
        }

        fun expr(): ExprContext {
            return getRuleContext(ExprContext::class.java, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterVarStoreStmt(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitVarStoreStmt(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitVarStoreStmt(this) else visitor.visitChildren(
                this
            )
        }
    }

    class ReturnStmtContext(ctx: StmtContext?) : StmtContext() {
        fun RETURN(): TerminalNode {
            return getToken(RETURN, 0)
        }

        fun SEMI(): TerminalNode {
            return getToken(SEMI, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterReturnStmt(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitReturnStmt(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitReturnStmt(this) else visitor.visitChildren(
                this
            )
        }
    }

    class CallExprContext(ctx: StmtContext?) : StmtContext() {
        var ftor: Token? = null
        var args: ArgsListContext? = null
        fun P_O(): TerminalNode {
            return getToken(P_O, 0)
        }

        fun P_C(): TerminalNode {
            return getToken(P_C, 0)
        }

        fun SEMI(): TerminalNode {
            return getToken(SEMI, 0)
        }

        fun IDENT(): TerminalNode {
            return getToken(IDENT, 0)
        }

        fun argsList(): ArgsListContext {
            return getRuleContext(ArgsListContext::class.java, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterCallExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitCallExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitCallExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class VarDeclStmtContext(ctx: StmtContext?) : StmtContext() {
        var name: Token? = null
        var init: ExprContext? = null
        fun VAR(): TerminalNode {
            return getToken(VAR, 0)
        }

        fun ASSIGN(): TerminalNode {
            return getToken(ASSIGN, 0)
        }

        fun SEMI(): TerminalNode {
            return getToken(SEMI, 0)
        }

        fun IDENT(): TerminalNode {
            return getToken(IDENT, 0)
        }

        fun expr(): ExprContext {
            return getRuleContext(ExprContext::class.java, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterVarDeclStmt(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitVarDeclStmt(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitVarDeclStmt(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun stmt(): StmtContext {
        var _localctx = StmtContext(_ctx, state)
        enterRule(_localctx, 14, RULE_stmt)
        try {
            state = 153
            _errHandler.sync(this)
            when (interpreter.adaptivePredict(_input, 6, _ctx)) {
                1 -> {
                    _localctx = ExprStmtContext(_localctx)
                    enterOuterAlt(_localctx, 1)
                    run {
                        state = 91
                        (_localctx as ExprStmtContext).e = expr(0)
                        state = 92
                        match(SEMI)
                    }
                }

                2 -> {
                    _localctx = VarDeclStmtContext(_localctx)
                    enterOuterAlt(_localctx, 2)
                    run {
                        state = 94
                        match(VAR)
                        state = 95
                        (_localctx as VarDeclStmtContext).name = match(IDENT)
                        state = 96
                        match(ASSIGN)
                        state = 97
                        (_localctx as VarDeclStmtContext).init = expr(0)
                        state = 98
                        match(SEMI)
                    }
                }

                3 -> {
                    _localctx = VarStoreStmtContext(_localctx)
                    enterOuterAlt(_localctx, 3)
                    run {
                        state = 100
                        (_localctx as VarStoreStmtContext).name = match(IDENT)
                        state = 101
                        match(ASSIGN)
                        state = 102
                        (_localctx as VarStoreStmtContext).value = expr(0)
                        state = 103
                        match(SEMI)
                    }
                }

                4 -> {
                    _localctx = IfStmtContext(_localctx)
                    enterOuterAlt(_localctx, 4)
                    run {
                        state = 105
                        match(IF)
                        state = 106
                        match(P_O)
                        state = 107
                        (_localctx as IfStmtContext).cond = expr(0)
                        state = 108
                        match(P_C)
                        state = 109
                        match(B_O)
                        state = 110
                        (_localctx as IfStmtContext).bTrue = stmtSet(0)
                        state = 111
                        match(B_C)
                    }
                }

                5 -> {
                    _localctx = IfElseStmtContext(_localctx)
                    enterOuterAlt(_localctx, 5)
                    run {
                        state = 113
                        match(IF)
                        state = 114
                        match(P_O)
                        state = 115
                        (_localctx as IfElseStmtContext).cond = expr(0)
                        state = 116
                        match(P_C)
                        state = 117
                        match(B_O)
                        state = 118
                        (_localctx as IfElseStmtContext).bTrue = stmtSet(0)
                        state = 119
                        match(B_C)
                        state = 120
                        match(ELSE)
                        state = 121
                        match(B_O)
                        state = 122
                        (_localctx as IfElseStmtContext).bFalse = stmtSet(0)
                        state = 123
                        match(B_C)
                    }
                }

                6 -> {
                    _localctx = WhileStmtContext(_localctx)
                    enterOuterAlt(_localctx, 6)
                    run {
                        state = 125
                        match(WHILE)
                        state = 126
                        match(P_O)
                        state = 127
                        (_localctx as WhileStmtContext).cond = expr(0)
                        state = 128
                        match(P_C)
                        state = 129
                        match(B_O)
                        state = 130
                        (_localctx as WhileStmtContext).body = stmtSet(0)
                        state = 131
                        match(B_C)
                    }
                }

                7 -> {
                    _localctx = ForStmtContext(_localctx)
                    enterOuterAlt(_localctx, 7)
                    run {
                        state = 133
                        match(FOR)
                        state = 134
                        match(P_O)
                        state = 135
                        (_localctx as ForStmtContext).varN = match(IDENT)
                        state = 136
                        match(IN)
                        state = 137
                        (_localctx as ForStmtContext).range = expr(0)
                        state = 138
                        match(P_C)
                        state = 139
                        match(B_O)
                        state = 140
                        (_localctx as ForStmtContext).body = stmtSet(0)
                        state = 141
                        match(B_C)
                    }
                }

                8 -> {
                    _localctx = BreakStmtContext(_localctx)
                    enterOuterAlt(_localctx, 8)
                    run {
                        state = 143
                        match(BREAK)
                        state = 144
                        match(SEMI)
                    }
                }

                9 -> {
                    _localctx = ReturnStmtContext(_localctx)
                    enterOuterAlt(_localctx, 9)
                    run {
                        state = 145
                        match(RETURN)
                        state = 146
                        match(SEMI)
                    }
                }

                10 -> {
                    _localctx = CallExprContext(_localctx)
                    enterOuterAlt(_localctx, 10)
                    run {
                        state = 147
                        _localctx.ftor = match(IDENT)
                        state = 148
                        match(P_O)
                        state = 149
                        _localctx.args = argsList()
                        state = 150
                        match(P_C)
                        state = 151
                        match(SEMI)
                    }
                }
            }
        } catch (re: RecognitionException) {
            _localctx.exception = re
            _errHandler.reportError(this, re)
            _errHandler.recover(this, re)
        } finally {
            exitRule()
        }
        return _localctx
    }

    open class ExprContext : ParserRuleContext {
        constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState)

        override fun getRuleIndex(): Int {
            return RULE_expr
        }

        constructor()

        fun copyFrom(ctx: ExprContext?) {
            super.copyFrom(ctx)
        }
    }

    class AddSubExprContext(ctx: ExprContext?) : ExprContext() {
        var left: ExprContext? = null
        var op: Token? = null
        var right: ExprContext? = null
        fun expr(): List<ExprContext> {
            return getRuleContexts(ExprContext::class.java)
        }

        fun expr(i: Int): ExprContext {
            return getRuleContext(ExprContext::class.java, i)
        }

        fun AS_OP(): TerminalNode {
            return getToken(AS_OP, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterAddSubExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitAddSubExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitAddSubExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class ModExprContext(ctx: ExprContext?) : ExprContext() {
        var left: ExprContext? = null
        var right: ExprContext? = null
        fun MOD_OP(): TerminalNode {
            return getToken(MOD_OP, 0)
        }

        fun expr(): List<ExprContext> {
            return getRuleContexts(ExprContext::class.java)
        }

        fun expr(i: Int): ExprContext {
            return getRuleContext(ExprContext::class.java, i)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterModExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitModExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitModExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class UntilExprContext(ctx: ExprContext?) : ExprContext() {
        var begin: ExprContext? = null
        var end: ExprContext? = null
        fun UNTIL(): TerminalNode {
            return getToken(UNTIL, 0)
        }

        fun expr(): List<ExprContext> {
            return getRuleContexts(ExprContext::class.java)
        }

        fun expr(i: Int): ExprContext {
            return getRuleContext(ExprContext::class.java, i)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterUntilExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitUntilExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitUntilExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class ParenExprContext(ctx: ExprContext?) : ExprContext() {
        var content: ExprContext? = null
        fun P_O(): TerminalNode {
            return getToken(P_O, 0)
        }

        fun P_C(): TerminalNode {
            return getToken(P_C, 0)
        }

        fun expr(): ExprContext {
            return getRuleContext(ExprContext::class.java, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterParenExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitParenExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitParenExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class BitwiseExprContext(ctx: ExprContext?) : ExprContext() {
        var left: ExprContext? = null
        var op: Token? = null
        var right: ExprContext? = null
        fun expr(): List<ExprContext> {
            return getRuleContexts(ExprContext::class.java)
        }

        fun expr(i: Int): ExprContext {
            return getRuleContext(ExprContext::class.java, i)
        }

        fun BITWISE_OP(): TerminalNode {
            return getToken(BITWISE_OP, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterBitwiseExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitBitwiseExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitBitwiseExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class StringLitContext(ctx: ExprContext?) : ExprContext() {
        var str: StringExprContext? = null
        fun stringExpr(): StringExprContext {
            return getRuleContext(StringExprContext::class.java, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterStringLit(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitStringLit(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitStringLit(this) else visitor.visitChildren(
                this
            )
        }
    }

    class VarExprContext(ctx: ExprContext?) : ExprContext() {
        var value: Token? = null
        fun IDENT(): TerminalNode {
            return getToken(IDENT, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterVarExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitVarExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitVarExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class UnaryExprContext(ctx: ExprContext?) : ExprContext() {
        var op: Token? = null
        var value: ExprContext? = null
        fun UN_OP(): TerminalNode {
            return getToken(UN_OP, 0)
        }

        fun expr(): ExprContext {
            return getRuleContext(ExprContext::class.java, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterUnaryExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitUnaryExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitUnaryExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class TernaryExprContext(ctx: ExprContext?) : ExprContext() {
        var condition: ExprContext? = null
        var bTrue: ExprContext? = null
        var bFalse: ExprContext? = null
        fun QMARK(): TerminalNode {
            return getToken(QMARK, 0)
        }

        fun COLON(): TerminalNode {
            return getToken(COLON, 0)
        }

        fun expr(): List<ExprContext> {
            return getRuleContexts(ExprContext::class.java)
        }

        fun expr(i: Int): ExprContext {
            return getRuleContext(ExprContext::class.java, i)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterTernaryExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitTernaryExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitTernaryExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class BoolLitContext(ctx: ExprContext?) : ExprContext() {
        var value: Token? = null
        fun BOOL(): TerminalNode {
            return getToken(BOOL, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterBoolLit(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitBoolLit(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitBoolLit(this) else visitor.visitChildren(
                this
            )
        }
    }

    class LogicExprContext(ctx: ExprContext?) : ExprContext() {
        var left: ExprContext? = null
        var op: Token? = null
        var right: ExprContext? = null
        fun expr(): List<ExprContext> {
            return getRuleContexts(ExprContext::class.java)
        }

        fun expr(i: Int): ExprContext {
            return getRuleContext(ExprContext::class.java, i)
        }

        fun LOGIC_OP(): TerminalNode {
            return getToken(LOGIC_OP, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterLogicExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitLogicExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitLogicExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class IntLitContext(ctx: ExprContext?) : ExprContext() {
        var value: Token? = null
        fun INT(): TerminalNode {
            return getToken(INT, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterIntLit(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitIntLit(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitIntLit(this) else visitor.visitChildren(
                this
            )
        }
    }

    class DiceExprContext(ctx: ExprContext?) : ExprContext() {
        var count: ExprContext? = null
        var dice: ExprContext? = null
        fun DICE(): TerminalNode {
            return getToken(DICE, 0)
        }

        fun expr(): List<ExprContext> {
            return getRuleContexts(ExprContext::class.java)
        }

        fun expr(i: Int): ExprContext {
            return getRuleContext(ExprContext::class.java, i)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterDiceExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitDiceExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitDiceExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class DictExprContext(ctx: ExprContext?) : ExprContext() {
        var values: KvpListContext? = null
        fun B_O(): TerminalNode {
            return getToken(B_O, 0)
        }

        fun B_C(): TerminalNode {
            return getToken(B_C, 0)
        }

        fun kvpList(): KvpListContext {
            return getRuleContext(KvpListContext::class.java, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterDictExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitDictExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitDictExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class ListExprContext(ctx: ExprContext?) : ExprContext() {
        var values: ArgsListContext? = null
        fun BR_O(): TerminalNode {
            return getToken(BR_O, 0)
        }

        fun BR_C(): TerminalNode {
            return getToken(BR_C, 0)
        }

        fun argsList(): ArgsListContext {
            return getRuleContext(ArgsListContext::class.java, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterListExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitListExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitListExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class MulDivExprContext(ctx: ExprContext?) : ExprContext() {
        var left: ExprContext? = null
        var op: Token? = null
        var right: ExprContext? = null
        fun expr(): List<ExprContext> {
            return getRuleContexts(ExprContext::class.java)
        }

        fun expr(i: Int): ExprContext {
            return getRuleContext(ExprContext::class.java, i)
        }

        fun MD_OP(): TerminalNode {
            return getToken(MD_OP, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterMulDivExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitMulDivExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitMulDivExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class RangeExprContext(ctx: ExprContext?) : ExprContext() {
        var begin: ExprContext? = null
        var end: ExprContext? = null
        fun ELIPSIS(): TerminalNode {
            return getToken(ELIPSIS, 0)
        }

        fun expr(): List<ExprContext> {
            return getRuleContexts(ExprContext::class.java)
        }

        fun expr(i: Int): ExprContext {
            return getRuleContext(ExprContext::class.java, i)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterRangeExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitRangeExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitRangeExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class CompareExprContext(ctx: ExprContext?) : ExprContext() {
        var left: ExprContext? = null
        var op: Token? = null
        var right: ExprContext? = null
        fun expr(): List<ExprContext> {
            return getRuleContexts(ExprContext::class.java)
        }

        fun expr(i: Int): ExprContext {
            return getRuleContext(ExprContext::class.java, i)
        }

        fun COMPARISON_OP(): TerminalNode {
            return getToken(COMPARISON_OP, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterCompareExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitCompareExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitCompareExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun expr(): ExprContext {
        return expr(0)
    }

    @Throws(RecognitionException::class)
    private fun expr(_p: Int): ExprContext {
        val _parentctx = _ctx
        val _parentState = state
        var _localctx = ExprContext(_ctx, _parentState)
        var _prevctx = _localctx
        val _startState = 16
        enterRecursionRule(_localctx, 16, RULE_expr, _p)
        try {
            var _alt: Int
            enterOuterAlt(_localctx, 1)
            run {
                state = 174
                _errHandler.sync(this)
                when (_input.LA(1)) {
                    STRING_LIT -> {
                        _localctx = StringLitContext(_localctx)
                        _ctx = _localctx
                        _prevctx = _localctx
                        state = 156
                        (_localctx as StringLitContext).str = stringExpr()
                    }

                    INT -> {
                        _localctx = IntLitContext(_localctx)
                        _ctx = _localctx
                        _prevctx = _localctx
                        state = 157
                        (_localctx as IntLitContext).value = match(INT)
                    }

                    BOOL -> {
                        _localctx = BoolLitContext(_localctx)
                        _ctx = _localctx
                        _prevctx = _localctx
                        state = 158
                        (_localctx as BoolLitContext).value = match(BOOL)
                    }

                    IDENT -> {
                        _localctx = VarExprContext(_localctx)
                        _ctx = _localctx
                        _prevctx = _localctx
                        state = 159
                        (_localctx as VarExprContext).value = match(IDENT)
                    }

                    P_O -> {
                        _localctx = ParenExprContext(_localctx)
                        _ctx = _localctx
                        _prevctx = _localctx
                        state = 160
                        match(P_O)
                        state = 161
                        (_localctx as ParenExprContext).content = expr(0)
                        state = 162
                        match(P_C)
                    }

                    UN_OP -> {
                        _localctx = UnaryExprContext(_localctx)
                        _ctx = _localctx
                        _prevctx = _localctx
                        state = 164
                        (_localctx as UnaryExprContext).op = match(UN_OP)
                        state = 165
                        (_localctx as UnaryExprContext).value = expr(9)
                    }

                    BR_O -> {
                        _localctx = ListExprContext(_localctx)
                        _ctx = _localctx
                        _prevctx = _localctx
                        state = 166
                        match(BR_O)
                        state = 167
                        (_localctx as ListExprContext).values = argsList()
                        state = 168
                        match(BR_C)
                    }

                    B_O -> {
                        _localctx = DictExprContext(_localctx)
                        _ctx = _localctx
                        _prevctx = _localctx
                        state = 170
                        match(B_O)
                        state = 171
                        (_localctx as DictExprContext).values = kvpList()
                        state = 172
                        match(B_C)
                    }

                    else -> throw NoViableAltException(this)
                }
                _ctx.stop = _input.LT(-1)
                state = 211
                _errHandler.sync(this)
                _alt = interpreter.adaptivePredict(_input, 9, _ctx)
                while (_alt != 2 && _alt != ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent()
                        _prevctx = _localctx
                        run {
                            state = 209
                            _errHandler.sync(this)
                            when (interpreter.adaptivePredict(_input, 8, _ctx)) {
                                1 -> {
                                    _localctx = DiceExprContext(ExprContext(_parentctx, _parentState))
                                    (_localctx as DiceExprContext).count = _prevctx
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr)
                                    state = 176
                                    if (!precpred(_ctx, 13)) throw FailedPredicateException(this, "precpred(_ctx, 13)")
                                    state = 177
                                    match(DICE)
                                    state = 178
                                    (_localctx as DiceExprContext).dice = expr(14)
                                }

                                2 -> {
                                    _localctx = MulDivExprContext(ExprContext(_parentctx, _parentState))
                                    (_localctx as MulDivExprContext).left = _prevctx
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr)
                                    state = 179
                                    if (!precpred(_ctx, 12)) throw FailedPredicateException(this, "precpred(_ctx, 12)")
                                    state = 180
                                    (_localctx as MulDivExprContext).op = match(MD_OP)
                                    state = 181
                                    (_localctx as MulDivExprContext).right = expr(13)
                                }

                                3 -> {
                                    _localctx = AddSubExprContext(ExprContext(_parentctx, _parentState))
                                    (_localctx as AddSubExprContext).left = _prevctx
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr)
                                    state = 182
                                    if (!precpred(_ctx, 11)) throw FailedPredicateException(this, "precpred(_ctx, 11)")
                                    state = 183
                                    (_localctx as AddSubExprContext).op = match(AS_OP)
                                    state = 184
                                    (_localctx as AddSubExprContext).right = expr(12)
                                }

                                4 -> {
                                    _localctx = ModExprContext(ExprContext(_parentctx, _parentState))
                                    (_localctx as ModExprContext).left = _prevctx
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr)
                                    state = 185
                                    if (!precpred(_ctx, 10)) throw FailedPredicateException(this, "precpred(_ctx, 10)")
                                    state = 186
                                    match(MOD_OP)
                                    state = 187
                                    (_localctx as ModExprContext).right = expr(11)
                                }

                                5 -> {
                                    _localctx = CompareExprContext(ExprContext(_parentctx, _parentState))
                                    (_localctx as CompareExprContext).left = _prevctx
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr)
                                    state = 188
                                    if (!precpred(_ctx, 8)) throw FailedPredicateException(this, "precpred(_ctx, 8)")
                                    state = 189
                                    (_localctx as CompareExprContext).op = match(COMPARISON_OP)
                                    state = 190
                                    (_localctx as CompareExprContext).right = expr(9)
                                }

                                6 -> {
                                    _localctx = LogicExprContext(ExprContext(_parentctx, _parentState))
                                    (_localctx as LogicExprContext).left = _prevctx
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr)
                                    state = 191
                                    if (!precpred(_ctx, 7)) throw FailedPredicateException(this, "precpred(_ctx, 7)")
                                    state = 192
                                    (_localctx as LogicExprContext).op = match(LOGIC_OP)
                                    state = 193
                                    (_localctx as LogicExprContext).right = expr(8)
                                }

                                7 -> {
                                    _localctx = BitwiseExprContext(ExprContext(_parentctx, _parentState))
                                    (_localctx as BitwiseExprContext).left = _prevctx
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr)
                                    state = 194
                                    if (!precpred(_ctx, 6)) throw FailedPredicateException(this, "precpred(_ctx, 6)")
                                    state = 195
                                    (_localctx as BitwiseExprContext).op = match(BITWISE_OP)
                                    state = 196
                                    (_localctx as BitwiseExprContext).right = expr(7)
                                }

                                8 -> {
                                    _localctx = TernaryExprContext(ExprContext(_parentctx, _parentState))
                                    (_localctx as TernaryExprContext).condition = _prevctx
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr)
                                    state = 197
                                    if (!precpred(_ctx, 5)) throw FailedPredicateException(this, "precpred(_ctx, 5)")
                                    state = 198
                                    match(QMARK)
                                    state = 199
                                    (_localctx as TernaryExprContext).bTrue = expr(0)
                                    state = 200
                                    match(COLON)
                                    state = 201
                                    (_localctx as TernaryExprContext).bFalse = expr(6)
                                }

                                9 -> {
                                    _localctx = RangeExprContext(ExprContext(_parentctx, _parentState))
                                    (_localctx as RangeExprContext).begin = _prevctx
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr)
                                    state = 203
                                    if (!precpred(_ctx, 4)) throw FailedPredicateException(this, "precpred(_ctx, 4)")
                                    state = 204
                                    match(ELIPSIS)
                                    state = 205
                                    (_localctx as RangeExprContext).end = expr(5)
                                }

                                10 -> {
                                    _localctx = UntilExprContext(ExprContext(_parentctx, _parentState))
                                    (_localctx as UntilExprContext).begin = _prevctx
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr)
                                    state = 206
                                    if (!precpred(_ctx, 3)) throw FailedPredicateException(this, "precpred(_ctx, 3)")
                                    state = 207
                                    match(UNTIL)
                                    state = 208
                                    (_localctx as UntilExprContext).end = expr(4)
                                }
                            }
                        }
                    }
                    state = 213
                    _errHandler.sync(this)
                    _alt = interpreter.adaptivePredict(_input, 9, _ctx)
                }
            }
        } catch (re: RecognitionException) {
            _localctx.exception = re
            _errHandler.reportError(this, re)
            _errHandler.recover(this, re)
        } finally {
            unrollRecursionContexts(_parentctx)
        }
        return _localctx
    }

    class KvpListContext(parent: ParserRuleContext?, invokingState: Int) : ParserRuleContext(parent, invokingState) {
        var values: NonEmptyKvpContext? = null
        fun nonEmptyKvp(): NonEmptyKvpContext {
            return getRuleContext(NonEmptyKvpContext::class.java, 0)
        }

        override fun getRuleIndex(): Int {
            return RULE_kvpList
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterKvpList(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitKvpList(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitKvpList(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun kvpList(): KvpListContext {
        val _localctx = KvpListContext(_ctx, state)
        enterRule(_localctx, 18, RULE_kvpList)
        try {
            state = 216
            _errHandler.sync(this)
            when (_input.LA(1)) {
                B_C -> {
                    enterOuterAlt(_localctx, 1)
                    run {}
                }

                B_O, P_O, BR_O, UN_OP, BOOL, INT, IDENT, STRING_LIT -> {
                    enterOuterAlt(_localctx, 2)
                    run {
                        state = 215
                        _localctx.values = nonEmptyKvp(0)
                    }
                }

                else -> throw NoViableAltException(this)
            }
        } catch (re: RecognitionException) {
            _localctx.exception = re
            _errHandler.reportError(this, re)
            _errHandler.recover(this, re)
        } finally {
            exitRule()
        }
        return _localctx
    }

    class NonEmptyKvpContext(parent: ParserRuleContext?, invokingState: Int) :
        ParserRuleContext(parent, invokingState) {
        var prev: NonEmptyKvpContext? = null
        var key: ExprContext? = null
        var value: ExprContext? = null
        var `val`: ExprContext? = null
        fun ASSIGN(): TerminalNode {
            return getToken(ASSIGN, 0)
        }

        fun expr(): List<ExprContext> {
            return getRuleContexts(ExprContext::class.java)
        }

        fun expr(i: Int): ExprContext {
            return getRuleContext(ExprContext::class.java, i)
        }

        fun COMMA(): TerminalNode {
            return getToken(COMMA, 0)
        }

        fun nonEmptyKvp(): NonEmptyKvpContext {
            return getRuleContext(NonEmptyKvpContext::class.java, 0)
        }

        override fun getRuleIndex(): Int {
            return RULE_nonEmptyKvp
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterNonEmptyKvp(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitNonEmptyKvp(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitNonEmptyKvp(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun nonEmptyKvp(): NonEmptyKvpContext {
        return nonEmptyKvp(0)
    }

    @Throws(RecognitionException::class)
    private fun nonEmptyKvp(_p: Int): NonEmptyKvpContext {
        val _parentctx = _ctx
        val _parentState = state
        var _localctx = NonEmptyKvpContext(_ctx, _parentState)
        var _prevctx = _localctx
        val _startState = 20
        enterRecursionRule(_localctx, 20, RULE_nonEmptyKvp, _p)
        try {
            var _alt: Int
            enterOuterAlt(_localctx, 1)
            run {
                run {
                    state = 219
                    _localctx.key = expr(0)
                    state = 220
                    match(ASSIGN)
                    state = 221
                    _localctx.value = expr(0)
                }
                _ctx.stop = _input.LT(-1)
                state = 231
                _errHandler.sync(this)
                _alt = interpreter.adaptivePredict(_input, 11, _ctx)
                while (_alt != 2 && _alt != ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent()
                        _prevctx = _localctx
                        run {
                            run {
                                _localctx = NonEmptyKvpContext(_parentctx, _parentState)
                                _localctx.prev = _prevctx
                                pushNewRecursionContext(_localctx, _startState, RULE_nonEmptyKvp)
                                state = 223
                                if (!precpred(_ctx, 1)) throw FailedPredicateException(this, "precpred(_ctx, 1)")
                                state = 224
                                match(COMMA)
                                state = 225
                                _localctx.key = expr(0)
                                state = 226
                                match(ASSIGN)
                                state = 227
                                _localctx.`val` = expr(0)
                            }
                        }
                    }
                    state = 233
                    _errHandler.sync(this)
                    _alt = interpreter.adaptivePredict(_input, 11, _ctx)
                }
            }
        } catch (re: RecognitionException) {
            _localctx.exception = re
            _errHandler.reportError(this, re)
            _errHandler.recover(this, re)
        } finally {
            unrollRecursionContexts(_parentctx)
        }
        return _localctx
    }

    class ArgsListContext(parent: ParserRuleContext?, invokingState: Int) : ParserRuleContext(parent, invokingState) {
        var args: NonEmptyArgsContext? = null
        fun nonEmptyArgs(): NonEmptyArgsContext {
            return getRuleContext(NonEmptyArgsContext::class.java, 0)
        }

        override fun getRuleIndex(): Int {
            return RULE_argsList
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterArgsList(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitArgsList(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitArgsList(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun argsList(): ArgsListContext {
        val _localctx = ArgsListContext(_ctx, state)
        enterRule(_localctx, 22, RULE_argsList)
        try {
            state = 236
            _errHandler.sync(this)
            when (_input.LA(1)) {
                P_C, BR_C -> {
                    enterOuterAlt(_localctx, 1)
                    run {}
                }

                B_O, P_O, BR_O, UN_OP, BOOL, INT, IDENT, STRING_LIT -> {
                    enterOuterAlt(_localctx, 2)
                    run {
                        state = 235
                        _localctx.args = nonEmptyArgs(0)
                    }
                }

                else -> throw NoViableAltException(this)
            }
        } catch (re: RecognitionException) {
            _localctx.exception = re
            _errHandler.reportError(this, re)
            _errHandler.recover(this, re)
        } finally {
            exitRule()
        }
        return _localctx
    }

    class NonEmptyArgsContext(parent: ParserRuleContext?, invokingState: Int) :
        ParserRuleContext(parent, invokingState) {
        var prev: NonEmptyArgsContext? = null
        var arg: ExprContext? = null
        fun expr(): ExprContext {
            return getRuleContext(ExprContext::class.java, 0)
        }

        fun COMMA(): TerminalNode {
            return getToken(COMMA, 0)
        }

        fun nonEmptyArgs(): NonEmptyArgsContext {
            return getRuleContext(NonEmptyArgsContext::class.java, 0)
        }

        override fun getRuleIndex(): Int {
            return RULE_nonEmptyArgs
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterNonEmptyArgs(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitNonEmptyArgs(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitNonEmptyArgs(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun nonEmptyArgs(): NonEmptyArgsContext {
        return nonEmptyArgs(0)
    }

    @Throws(RecognitionException::class)
    private fun nonEmptyArgs(_p: Int): NonEmptyArgsContext {
        val _parentctx = _ctx
        val _parentState = state
        var _localctx = NonEmptyArgsContext(_ctx, _parentState)
        var _prevctx = _localctx
        val _startState = 24
        enterRecursionRule(_localctx, 24, RULE_nonEmptyArgs, _p)
        try {
            var _alt: Int
            enterOuterAlt(_localctx, 1)
            run {
                run {
                    state = 239
                    _localctx.arg = expr(0)
                }
                _ctx.stop = _input.LT(-1)
                state = 246
                _errHandler.sync(this)
                _alt = interpreter.adaptivePredict(_input, 13, _ctx)
                while (_alt != 2 && _alt != ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent()
                        _prevctx = _localctx
                        run {
                            run {
                                _localctx = NonEmptyArgsContext(_parentctx, _parentState)
                                _localctx.prev = _prevctx
                                pushNewRecursionContext(_localctx, _startState, RULE_nonEmptyArgs)
                                state = 241
                                if (!precpred(_ctx, 1)) throw FailedPredicateException(this, "precpred(_ctx, 1)")
                                state = 242
                                match(COMMA)
                                state = 243
                                _localctx.arg = expr(0)
                            }
                        }
                    }
                    state = 248
                    _errHandler.sync(this)
                    _alt = interpreter.adaptivePredict(_input, 13, _ctx)
                }
            }
        } catch (re: RecognitionException) {
            _localctx.exception = re
            _errHandler.reportError(this, re)
            _errHandler.recover(this, re)
        } finally {
            unrollRecursionContexts(_parentctx)
        }
        return _localctx
    }

    class StringExprContext(parent: ParserRuleContext?, invokingState: Int) : ParserRuleContext(parent, invokingState) {
        fun STRING_LIT(): TerminalNode {
            return getToken(STRING_LIT, 0)
        }

        override fun getRuleIndex(): Int {
            return RULE_stringExpr
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.enterStringExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is CMLListener) listener.exitStringExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is CMLVisitor<*>) (visitor as CMLVisitor<out T>).visitStringExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun stringExpr(): StringExprContext {
        val _localctx = StringExprContext(_ctx, state)
        enterRule(_localctx, 26, RULE_stringExpr)
        try {
            enterOuterAlt(_localctx, 1)
            run {
                state = 249
                match(STRING_LIT)
            }
        } catch (re: RecognitionException) {
            _localctx.exception = re
            _errHandler.reportError(this, re)
            _errHandler.recover(this, re)
        } finally {
            exitRule()
        }
        return _localctx
    }

    override fun sempred(_localctx: RuleContext, ruleIndex: Int, predIndex: Int): Boolean {
        when (ruleIndex) {
            2 -> return declSet_sempred(_localctx as DeclSetContext, predIndex)
            4 -> return stmtSet_sempred(_localctx as StmtSetContext, predIndex)
            6 -> return argDsNonEmpty_sempred(_localctx as ArgDsNonEmptyContext, predIndex)
            8 -> return expr_sempred(_localctx as ExprContext, predIndex)
            10 -> return nonEmptyKvp_sempred(_localctx as NonEmptyKvpContext, predIndex)
            12 -> return nonEmptyArgs_sempred(_localctx as NonEmptyArgsContext, predIndex)
        }
        return true
    }

    private fun declSet_sempred(_localctx: DeclSetContext, predIndex: Int): Boolean {
        when (predIndex) {
            0 -> return precpred(_ctx, 1)
        }
        return true
    }

    private fun stmtSet_sempred(_localctx: StmtSetContext, predIndex: Int): Boolean {
        when (predIndex) {
            1 -> return precpred(_ctx, 1)
        }
        return true
    }

    private fun argDsNonEmpty_sempred(_localctx: ArgDsNonEmptyContext, predIndex: Int): Boolean {
        when (predIndex) {
            2 -> return precpred(_ctx, 1)
        }
        return true
    }

    private fun expr_sempred(_localctx: ExprContext, predIndex: Int): Boolean {
        when (predIndex) {
            3 -> return precpred(_ctx, 13)
            4 -> return precpred(_ctx, 12)
            5 -> return precpred(_ctx, 11)
            6 -> return precpred(_ctx, 10)
            7 -> return precpred(_ctx, 8)
            8 -> return precpred(_ctx, 7)
            9 -> return precpred(_ctx, 6)
            10 -> return precpred(_ctx, 5)
            11 -> return precpred(_ctx, 4)
            12 -> return precpred(_ctx, 3)
        }
        return true
    }

    private fun nonEmptyKvp_sempred(_localctx: NonEmptyKvpContext, predIndex: Int): Boolean {
        when (predIndex) {
            13 -> return precpred(_ctx, 1)
        }
        return true
    }

    private fun nonEmptyArgs_sempred(_localctx: NonEmptyArgsContext, predIndex: Int): Boolean {
        when (predIndex) {
            14 -> return precpred(_ctx, 1)
        }
        return true
    }

    init {
        _interp = ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache)
    }

    companion object {
        init {
            RuntimeMetaData.checkVersion("4.11.1", RuntimeMetaData.VERSION)
        }

        protected val _decisionToDFA: Array<DFA?>
        protected val _sharedContextCache = PredictionContextCache()
        const val B_O = 1
        const val B_C = 2
        const val P_O = 3
        const val P_C = 4
        const val BR_O = 5
        const val BR_C = 6
        const val SEMI = 7
        const val ASSIGN = 8
        const val COMMA = 9
        const val QMARK = 10
        const val COLON = 11
        const val STRING_DELIM = 12
        const val ELIPSIS = 13
        const val MD_OP = 14
        const val AS_OP = 15
        const val MOD_OP = 16
        const val UN_OP = 17
        const val LOGIC_OP = 18
        const val BITWISE_OP = 19
        const val DICE = 20
        const val BOOL = 21
        const val COMPARISON_OP = 22
        const val IF = 23
        const val IN = 24
        const val FOR = 25
        const val FUN = 26
        const val VAR = 27
        const val DATA = 28
        const val ELSE = 29
        const val BREAK = 30
        const val FIELD = 31
        const val UNTIL = 32
        const val WHILE = 33
        const val RETURN = 34
        const val INT = 35
        const val IDENT = 36
        const val STRING_LIT = 37
        const val NEWLINE = 38
        const val WS = 39
        const val COMMENT_CHARS = 40
        const val RULE_program = 0
        const val RULE_topLevel = 1
        const val RULE_declSet = 2
        const val RULE_decl = 3
        const val RULE_stmtSet = 4
        const val RULE_argDs = 5
        const val RULE_argDsNonEmpty = 6
        const val RULE_stmt = 7
        const val RULE_expr = 8
        const val RULE_kvpList = 9
        const val RULE_nonEmptyKvp = 10
        const val RULE_argsList = 11
        const val RULE_nonEmptyArgs = 12
        const val RULE_stringExpr = 13
        private fun makeRuleNames(): Array<String> {
            return arrayOf(
                "program", "topLevel", "declSet", "decl", "stmtSet", "argDs", "argDsNonEmpty",
                "stmt", "expr", "kvpList", "nonEmptyKvp", "argsList", "nonEmptyArgs",
                "stringExpr"
            )
        }

        val ruleNames = makeRuleNames()
        private fun makeLiteralNames(): Array<String?> {
            return arrayOf(
                null, "'{'", "'}'", "'('", "')'", "'['", "']'", "';'", "'='", "','",
                "'?'", "':'", "'\"'", "'...'", null, null, "'%'", null, null, null, null,
                null, null, "'if'", "'in'", "'for'", "'fun'", "'var'", "'data'", "'else'",
                "'break'", "'field'", "'until'", "'while'", "'return'"
            )
        }

        private val _LITERAL_NAMES = makeLiteralNames()
        private fun makeSymbolicNames(): Array<String?> {
            return arrayOf(
                null, "B_O", "B_C", "P_O", "P_C", "BR_O", "BR_C", "SEMI", "ASSIGN", "COMMA",
                "QMARK", "COLON", "STRING_DELIM", "ELIPSIS", "MD_OP", "AS_OP", "MOD_OP",
                "UN_OP", "LOGIC_OP", "BITWISE_OP", "DICE", "BOOL", "COMPARISON_OP", "IF",
                "IN", "FOR", "FUN", "VAR", "DATA", "ELSE", "BREAK", "FIELD", "UNTIL",
                "WHILE", "RETURN", "INT", "IDENT", "STRING_LIT", "NEWLINE", "WS", "COMMENT_CHARS"
            )
        }

        private val _SYMBOLIC_NAMES = makeSymbolicNames()
        val VOCABULARY: Vocabulary = VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES)

        @Deprecated("Use {@link #VOCABULARY} instead.")
        val tokenNames: Array<String?>

        init {
            tokenNames = arrayOfNulls(_SYMBOLIC_NAMES.size)
            for (i in tokenNames.indices) {
                tokenNames[i] = VOCABULARY.getLiteralName(i)
                if (tokenNames[i] == null) {
                    tokenNames[i] = VOCABULARY.getSymbolicName(i)
                }
                if (tokenNames[i] == null) {
                    tokenNames[i] = "<INVALID>"
                }
            }
        }

        const val _serializedATN = "\u0004\u0001(\u00fc\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002" +
                "\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002" +
                "\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002" +
                "\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002" +
                "\u000c\u0007\u000c\u0002\r\u0007\r\u0001\u0000\u0005\u0000\u001e\b\u0000\n\u0000" +
                "\u000c\u0000!\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001" +
                "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001" +
                "\u0002\u0001\u0002\u0005\u0002/\b\u0002\n\u0002\u000c\u00022\t\u0002\u0001" +
                "\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001" +
                "\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001" +
                "\u0003\u0001\u0003\u0001\u0003\u0003\u0003C\b\u0003\u0001\u0004\u0001" +
                "\u0004\u0001\u0004\u0005\u0004H\b\u0004\n\u0004\u000c\u0004K\t\u0004\u0001" +
                "\u0005\u0001\u0005\u0003\u0005O\b\u0005\u0001\u0006\u0001\u0006\u0001" +
                "\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0005\u0006W\b\u0006\n\u0006" +
                "\u000c\u0006Z\t\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001" +
                "\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001" +
                "\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001" +
                "\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001" +
                "\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001" +
                "\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001" +
                "\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001" +
                "\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001" +
                "\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001" +
                "\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001" +
                "\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007\u009a\b\u0007\u0001" +
                "\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001" +
                "\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001" +
                "\b\u0003\b\u00af\b\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001" +
                "\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001" +
                "\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001" +
                "\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0005" +
                "\b\u00d2\b\b\n\b\u000c\b\u00d5\t\b\u0001\t\u0001\t\u0003\t\u00d9\b\t\u0001" +
                "\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001" +
                "\n\u0001\n\u0005\n\u00e6\b\n\n\n\u000c\n\u00e9\t\n\u0001\u000b\u0001\u000b" +
                "\u0003\u000b\u00ed\b\u000b\u0001\u000c\u0001\u000c\u0001\u000c\u0001\u000c\u0001\u000c\u0001" +
                "\u000c\u0005\u000c\u00f5\b\u000c\n\u000c\u000c\u000c\u00f8\t\u000c\u0001\r\u0001\r\u0001\r\u0000\u0006" +
                "\u0004\b\u000c\u0010\u0014\u0018\u000e\u0000\u0002\u0004\u0006\b\n\u000c\u000e" +
                "\u0010\u0012\u0014\u0016\u0018\u001a\u0000\u0000\u0111\u0000\u001f\u0001" +
                "\u0000\u0000\u0000\u0002$\u0001\u0000\u0000\u0000\u0004+\u0001\u0000\u0000" +
                "\u0000\u0006B\u0001\u0000\u0000\u0000\bD\u0001\u0000\u0000\u0000\nN\u0001" +
                "\u0000\u0000\u0000\u000cP\u0001\u0000\u0000\u0000\u000e\u0099\u0001\u0000" +
                "\u0000\u0000\u0010\u00ae\u0001\u0000\u0000\u0000\u0012\u00d8\u0001\u0000" +
                "\u0000\u0000\u0014\u00da\u0001\u0000\u0000\u0000\u0016\u00ec\u0001\u0000" +
                "\u0000\u0000\u0018\u00ee\u0001\u0000\u0000\u0000\u001a\u00f9\u0001\u0000" +
                "\u0000\u0000\u001c\u001e\u0003\u0002\u0001\u0000\u001d\u001c\u0001\u0000" +
                "\u0000\u0000\u001e!\u0001\u0000\u0000\u0000\u001f\u001d\u0001\u0000\u0000" +
                "\u0000\u001f \u0001\u0000\u0000\u0000 \"\u0001\u0000\u0000\u0000!\u001f" +
                "\u0001\u0000\u0000\u0000\"#\u0005\u0000\u0000\u0001#\u0001\u0001\u0000" +
                "\u0000\u0000$%\u0005\u001c\u0000\u0000%&\u0005$\u0000\u0000&\'\u0005$" +
                "\u0000\u0000\'(\u0005\u0001\u0000\u0000()\u0003\u0004\u0002\u0000)*\u0005" +
                "\u0002\u0000\u0000*\u0003\u0001\u0000\u0000\u0000+0\u0006\u0002\uffff" +
                "\uffff\u0000,-\n\u0001\u0000\u0000-/\u0003\u0006\u0003\u0000.,\u0001\u0000" +
                "\u0000\u0000/2\u0001\u0000\u0000\u00000.\u0001\u0000\u0000\u000001\u0001" +
                "\u0000\u0000\u00001\u0005\u0001\u0000\u0000\u000020\u0001\u0000\u0000" +
                "\u000034\u0005\u001a\u0000\u000045\u0005$\u0000\u000056\u0005\u0003\u0000" +
                "\u000067\u0003\n\u0005\u000078\u0005\u0004\u0000\u000089\u0005\u0001\u0000" +
                "\u00009:\u0003\b\u0004\u0000:;\u0005\u0002\u0000\u0000;C\u0001\u0000\u0000" +
                "\u0000<=\u0005\u001f\u0000\u0000=>\u0005$\u0000\u0000>?\u0005\b\u0000" +
                "\u0000?@\u0003\u0010\b\u0000@A\u0005\u0007\u0000\u0000AC\u0001\u0000\u0000" +
                "\u0000B3\u0001\u0000\u0000\u0000B<\u0001\u0000\u0000\u0000C\u0007\u0001" +
                "\u0000\u0000\u0000DI\u0006\u0004\uffff\uffff\u0000EF\n\u0001\u0000\u0000" +
                "FH\u0003\u000e\u0007\u0000GE\u0001\u0000\u0000\u0000HK\u0001\u0000\u0000" +
                "\u0000IG\u0001\u0000\u0000\u0000IJ\u0001\u0000\u0000\u0000J\t\u0001\u0000" +
                "\u0000\u0000KI\u0001\u0000\u0000\u0000LO\u0001\u0000\u0000\u0000MO\u0003" +
                "\u000c\u0006\u0000NL\u0001\u0000\u0000\u0000NM\u0001\u0000\u0000\u0000O\u000b" +
                "\u0001\u0000\u0000\u0000PQ\u0006\u0006\uffff\uffff\u0000QR\u0005$\u0000" +
                "\u0000RX\u0001\u0000\u0000\u0000ST\n\u0001\u0000\u0000TU\u0005\t\u0000" +
                "\u0000UW\u0005$\u0000\u0000VS\u0001\u0000\u0000\u0000WZ\u0001\u0000\u0000" +
                "\u0000XV\u0001\u0000\u0000\u0000XY\u0001\u0000\u0000\u0000Y\r\u0001\u0000" +
                "\u0000\u0000ZX\u0001\u0000\u0000\u0000[\\\u0003\u0010\b\u0000\\]\u0005" +
                "\u0007\u0000\u0000]\u009a\u0001\u0000\u0000\u0000^_\u0005\u001b\u0000" +
                "\u0000_`\u0005$\u0000\u0000`a\u0005\b\u0000\u0000ab\u0003\u0010\b\u0000" +
                "bc\u0005\u0007\u0000\u0000c\u009a\u0001\u0000\u0000\u0000de\u0005$\u0000" +
                "\u0000ef\u0005\b\u0000\u0000fg\u0003\u0010\b\u0000gh\u0005\u0007\u0000" +
                "\u0000h\u009a\u0001\u0000\u0000\u0000ij\u0005\u0017\u0000\u0000jk\u0005" +
                "\u0003\u0000\u0000kl\u0003\u0010\b\u0000lm\u0005\u0004\u0000\u0000mn\u0005" +
                "\u0001\u0000\u0000no\u0003\b\u0004\u0000op\u0005\u0002\u0000\u0000p\u009a" +
                "\u0001\u0000\u0000\u0000qr\u0005\u0017\u0000\u0000rs\u0005\u0003\u0000" +
                "\u0000st\u0003\u0010\b\u0000tu\u0005\u0004\u0000\u0000uv\u0005\u0001\u0000" +
                "\u0000vw\u0003\b\u0004\u0000wx\u0005\u0002\u0000\u0000xy\u0005\u001d\u0000" +
                "\u0000yz\u0005\u0001\u0000\u0000z{\u0003\b\u0004\u0000{|\u0005\u0002\u0000" +
                "\u0000|\u009a\u0001\u0000\u0000\u0000}~\u0005!\u0000\u0000~\u007f\u0005" +
                "\u0003\u0000\u0000\u007f\u0080\u0003\u0010\b\u0000\u0080\u0081\u0005\u0004" +
                "\u0000\u0000\u0081\u0082\u0005\u0001\u0000\u0000\u0082\u0083\u0003\b\u0004" +
                "\u0000\u0083\u0084\u0005\u0002\u0000\u0000\u0084\u009a\u0001\u0000\u0000" +
                "\u0000\u0085\u0086\u0005\u0019\u0000\u0000\u0086\u0087\u0005\u0003\u0000" +
                "\u0000\u0087\u0088\u0005$\u0000\u0000\u0088\u0089\u0005\u0018\u0000\u0000" +
                "\u0089\u008a\u0003\u0010\b\u0000\u008a\u008b\u0005\u0004\u0000\u0000\u008b" +
                "\u008c\u0005\u0001\u0000\u0000\u008c\u008d\u0003\b\u0004\u0000\u008d\u008e" +
                "\u0005\u0002\u0000\u0000\u008e\u009a\u0001\u0000\u0000\u0000\u008f\u0090" +
                "\u0005\u001e\u0000\u0000\u0090\u009a\u0005\u0007\u0000\u0000\u0091\u0092" +
                "\u0005\"\u0000\u0000\u0092\u009a\u0005\u0007\u0000\u0000\u0093\u0094\u0005" +
                "$\u0000\u0000\u0094\u0095\u0005\u0003\u0000\u0000\u0095\u0096\u0003\u0016" +
                "\u000b\u0000\u0096\u0097\u0005\u0004\u0000\u0000\u0097\u0098\u0005\u0007" +
                "\u0000\u0000\u0098\u009a\u0001\u0000\u0000\u0000\u0099[\u0001\u0000\u0000" +
                "\u0000\u0099^\u0001\u0000\u0000\u0000\u0099d\u0001\u0000\u0000\u0000\u0099" +
                "i\u0001\u0000\u0000\u0000\u0099q\u0001\u0000\u0000\u0000\u0099}\u0001" +
                "\u0000\u0000\u0000\u0099\u0085\u0001\u0000\u0000\u0000\u0099\u008f\u0001" +
                "\u0000\u0000\u0000\u0099\u0091\u0001\u0000\u0000\u0000\u0099\u0093\u0001" +
                "\u0000\u0000\u0000\u009a\u000f\u0001\u0000\u0000\u0000\u009b\u009c\u0006" +
                "\b\uffff\uffff\u0000\u009c\u00af\u0003\u001a\r\u0000\u009d\u00af\u0005" +
                "#\u0000\u0000\u009e\u00af\u0005\u0015\u0000\u0000\u009f\u00af\u0005$\u0000" +
                "\u0000\u00a0\u00a1\u0005\u0003\u0000\u0000\u00a1\u00a2\u0003\u0010\b\u0000" +
                "\u00a2\u00a3\u0005\u0004\u0000\u0000\u00a3\u00af\u0001\u0000\u0000\u0000" +
                "\u00a4\u00a5\u0005\u0011\u0000\u0000\u00a5\u00af\u0003\u0010\b\t\u00a6" +
                "\u00a7\u0005\u0005\u0000\u0000\u00a7\u00a8\u0003\u0016\u000b\u0000\u00a8" +
                "\u00a9\u0005\u0006\u0000\u0000\u00a9\u00af\u0001\u0000\u0000\u0000\u00aa" +
                "\u00ab\u0005\u0001\u0000\u0000\u00ab\u00ac\u0003\u0012\t\u0000\u00ac\u00ad" +
                "\u0005\u0002\u0000\u0000\u00ad\u00af\u0001\u0000\u0000\u0000\u00ae\u009b" +
                "\u0001\u0000\u0000\u0000\u00ae\u009d\u0001\u0000\u0000\u0000\u00ae\u009e" +
                "\u0001\u0000\u0000\u0000\u00ae\u009f\u0001\u0000\u0000\u0000\u00ae\u00a0" +
                "\u0001\u0000\u0000\u0000\u00ae\u00a4\u0001\u0000\u0000\u0000\u00ae\u00a6" +
                "\u0001\u0000\u0000\u0000\u00ae\u00aa\u0001\u0000\u0000\u0000\u00af\u00d3" +
                "\u0001\u0000\u0000\u0000\u00b0\u00b1\n\r\u0000\u0000\u00b1\u00b2\u0005" +
                "\u0014\u0000\u0000\u00b2\u00d2\u0003\u0010\b\u000e\u00b3\u00b4\n\u000c\u0000" +
                "\u0000\u00b4\u00b5\u0005\u000e\u0000\u0000\u00b5\u00d2\u0003\u0010\b\r" +
                "\u00b6\u00b7\n\u000b\u0000\u0000\u00b7\u00b8\u0005\u000f\u0000\u0000\u00b8" +
                "\u00d2\u0003\u0010\b\u000c\u00b9\u00ba\n\n\u0000\u0000\u00ba\u00bb\u0005\u0010" +
                "\u0000\u0000\u00bb\u00d2\u0003\u0010\b\u000b\u00bc\u00bd\n\b\u0000\u0000" +
                "\u00bd\u00be\u0005\u0016\u0000\u0000\u00be\u00d2\u0003\u0010\b\t\u00bf" +
                "\u00c0\n\u0007\u0000\u0000\u00c0\u00c1\u0005\u0012\u0000\u0000\u00c1\u00d2" +
                "\u0003\u0010\b\b\u00c2\u00c3\n\u0006\u0000\u0000\u00c3\u00c4\u0005\u0013" +
                "\u0000\u0000\u00c4\u00d2\u0003\u0010\b\u0007\u00c5\u00c6\n\u0005\u0000" +
                "\u0000\u00c6\u00c7\u0005\n\u0000\u0000\u00c7\u00c8\u0003\u0010\b\u0000" +
                "\u00c8\u00c9\u0005\u000b\u0000\u0000\u00c9\u00ca\u0003\u0010\b\u0006\u00ca" +
                "\u00d2\u0001\u0000\u0000\u0000\u00cb\u00cc\n\u0004\u0000\u0000\u00cc\u00cd" +
                "\u0005\r\u0000\u0000\u00cd\u00d2\u0003\u0010\b\u0005\u00ce\u00cf\n\u0003" +
                "\u0000\u0000\u00cf\u00d0\u0005 \u0000\u0000\u00d0\u00d2\u0003\u0010\b" +
                "\u0004\u00d1\u00b0\u0001\u0000\u0000\u0000\u00d1\u00b3\u0001\u0000\u0000" +
                "\u0000\u00d1\u00b6\u0001\u0000\u0000\u0000\u00d1\u00b9\u0001\u0000\u0000" +
                "\u0000\u00d1\u00bc\u0001\u0000\u0000\u0000\u00d1\u00bf\u0001\u0000\u0000" +
                "\u0000\u00d1\u00c2\u0001\u0000\u0000\u0000\u00d1\u00c5\u0001\u0000\u0000" +
                "\u0000\u00d1\u00cb\u0001\u0000\u0000\u0000\u00d1\u00ce\u0001\u0000\u0000" +
                "\u0000\u00d2\u00d5\u0001\u0000\u0000\u0000\u00d3\u00d1\u0001\u0000\u0000" +
                "\u0000\u00d3\u00d4\u0001\u0000\u0000\u0000\u00d4\u0011\u0001\u0000\u0000" +
                "\u0000\u00d5\u00d3\u0001\u0000\u0000\u0000\u00d6\u00d9\u0001\u0000\u0000" +
                "\u0000\u00d7\u00d9\u0003\u0014\n\u0000\u00d8\u00d6\u0001\u0000\u0000\u0000" +
                "\u00d8\u00d7\u0001\u0000\u0000\u0000\u00d9\u0013\u0001\u0000\u0000\u0000" +
                "\u00da\u00db\u0006\n\uffff\uffff\u0000\u00db\u00dc\u0003\u0010\b\u0000" +
                "\u00dc\u00dd\u0005\b\u0000\u0000\u00dd\u00de\u0003\u0010\b\u0000\u00de" +
                "\u00e7\u0001\u0000\u0000\u0000\u00df\u00e0\n\u0001\u0000\u0000\u00e0\u00e1" +
                "\u0005\t\u0000\u0000\u00e1\u00e2\u0003\u0010\b\u0000\u00e2\u00e3\u0005" +
                "\b\u0000\u0000\u00e3\u00e4\u0003\u0010\b\u0000\u00e4\u00e6\u0001\u0000" +
                "\u0000\u0000\u00e5\u00df\u0001\u0000\u0000\u0000\u00e6\u00e9\u0001\u0000" +
                "\u0000\u0000\u00e7\u00e5\u0001\u0000\u0000\u0000\u00e7\u00e8\u0001\u0000" +
                "\u0000\u0000\u00e8\u0015\u0001\u0000\u0000\u0000\u00e9\u00e7\u0001\u0000" +
                "\u0000\u0000\u00ea\u00ed\u0001\u0000\u0000\u0000\u00eb\u00ed\u0003\u0018" +
                "\u000c\u0000\u00ec\u00ea\u0001\u0000\u0000\u0000\u00ec\u00eb\u0001\u0000\u0000" +
                "\u0000\u00ed\u0017\u0001\u0000\u0000\u0000\u00ee\u00ef\u0006\u000c\uffff\uffff" +
                "\u0000\u00ef\u00f0\u0003\u0010\b\u0000\u00f0\u00f6\u0001\u0000\u0000\u0000" +
                "\u00f1\u00f2\n\u0001\u0000\u0000\u00f2\u00f3\u0005\t\u0000\u0000\u00f3" +
                "\u00f5\u0003\u0010\b\u0000\u00f4\u00f1\u0001\u0000\u0000\u0000\u00f5\u00f8" +
                "\u0001\u0000\u0000\u0000\u00f6\u00f4\u0001\u0000\u0000\u0000\u00f6\u00f7" +
                "\u0001\u0000\u0000\u0000\u00f7\u0019\u0001\u0000\u0000\u0000\u00f8\u00f6" +
                "\u0001\u0000\u0000\u0000\u00f9\u00fa\u0005%\u0000\u0000\u00fa\u001b\u0001" +
                "\u0000\u0000\u0000\u000e\u001f0BINX\u0099\u00ae\u00d1\u00d3\u00d8\u00e7" +
                "\u00ec\u00f6"
        val _ATN = ATNDeserializer().deserialize(_serializedATN.toCharArray())

        init {
            _decisionToDFA = arrayOfNulls(_ATN.numberOfDecisions)
            for (i in 0 until _ATN.numberOfDecisions) {
                _decisionToDFA[i] = DFA(_ATN.getDecisionState(i), i)
            }
        }
    }
}