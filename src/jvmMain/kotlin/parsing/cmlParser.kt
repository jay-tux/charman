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
class cmlParser(input: TokenStream?) : Parser(input) {
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
        fun topLevel(): TopLevelContext {
            return getRuleContext(TopLevelContext::class.java, 0)
        }

        fun EOF(): TerminalNode {
            return getToken(EOF, 0)
        }

        override fun getRuleIndex(): Int {
            return RULE_program
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterProgram(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitProgram(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitProgram(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun program(): ProgramContext {
        val _localctx = ProgramContext(_ctx, state)
        enterRule(_localctx, 0, RULE_program)
        try {
            enterOuterAlt(_localctx, 1)
            run {
                state = 24
                topLevel()
                state = 25
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

    open class TopLevelContext : ParserRuleContext {
        constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState)

        override fun getRuleIndex(): Int {
            return RULE_topLevel
        }

        constructor()

        fun copyFrom(ctx: TopLevelContext?) {
            super.copyFrom(ctx)
        }
    }

    class BgDescContext(ctx: TopLevelContext?) : TopLevelContext() {
        var name: Token? = null
        var body: DeclContext? = null
        fun IDENT(): TerminalNode {
            return getToken(IDENT, 0)
        }

        fun decl(): List<DeclContext> {
            return getRuleContexts(DeclContext::class.java)
        }

        fun decl(i: Int): DeclContext {
            return getRuleContext(DeclContext::class.java, i)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterBgDesc(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitBgDesc(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitBgDesc(this) else visitor.visitChildren(
                this
            )
        }
    }

    class RaceDescContext(ctx: TopLevelContext?) : TopLevelContext() {
        var name: Token? = null
        var body: DeclContext? = null
        fun IDENT(): TerminalNode {
            return getToken(IDENT, 0)
        }

        fun decl(): List<DeclContext> {
            return getRuleContexts(DeclContext::class.java)
        }

        fun decl(i: Int): DeclContext {
            return getRuleContext(DeclContext::class.java, i)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterRaceDesc(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitRaceDesc(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitRaceDesc(this) else visitor.visitChildren(
                this
            )
        }
    }

    class ItemDescContext(ctx: TopLevelContext?) : TopLevelContext() {
        var name: Token? = null
        var body: DeclContext? = null
        fun IDENT(): TerminalNode {
            return getToken(IDENT, 0)
        }

        fun decl(): List<DeclContext> {
            return getRuleContexts(DeclContext::class.java)
        }

        fun decl(i: Int): DeclContext {
            return getRuleContext(DeclContext::class.java, i)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterItemDesc(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitItemDesc(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitItemDesc(this) else visitor.visitChildren(
                this
            )
        }
    }

    class ClassDescContext(ctx: TopLevelContext?) : TopLevelContext() {
        var name: Token? = null
        var body: DeclContext? = null
        fun IDENT(): TerminalNode {
            return getToken(IDENT, 0)
        }

        fun decl(): List<DeclContext> {
            return getRuleContexts(DeclContext::class.java)
        }

        fun decl(i: Int): DeclContext {
            return getRuleContext(DeclContext::class.java, i)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterClassDesc(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitClassDesc(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitClassDesc(this) else visitor.visitChildren(
                this
            )
        }
    }

    class SpellDescContext(ctx: TopLevelContext?) : TopLevelContext() {
        var name: Token? = null
        var body: DeclContext? = null
        fun IDENT(): TerminalNode {
            return getToken(IDENT, 0)
        }

        fun decl(): List<DeclContext> {
            return getRuleContexts(DeclContext::class.java)
        }

        fun decl(i: Int): DeclContext {
            return getRuleContext(DeclContext::class.java, i)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterSpellDesc(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitSpellDesc(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitSpellDesc(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun topLevel(): TopLevelContext {
        var _localctx = TopLevelContext(_ctx, state)
        enterRule(_localctx, 2, RULE_topLevel)
        var _la: Int
        try {
            state = 77
            _errHandler.sync(this)
            when (_input.LA(1)) {
                T__0 -> {
                    _localctx = RaceDescContext(_localctx)
                    enterOuterAlt(_localctx, 1)
                    run {
                        state = 27
                        match(T__0)
                        state = 28
                        (_localctx as RaceDescContext).name = match(IDENT)
                        state = 29
                        match(T__1)
                        state = 33
                        _errHandler.sync(this)
                        _la = _input.LA(1)
                        while (_la == T__7 || _la == T__10) {
                            run {
                                run {
                                    state = 30
                                    (_localctx as RaceDescContext).body = decl()
                                }
                            }
                            state = 35
                            _errHandler.sync(this)
                            _la = _input.LA(1)
                        }
                        state = 36
                        match(T__2)
                    }
                }

                T__3 -> {
                    _localctx = ClassDescContext(_localctx)
                    enterOuterAlt(_localctx, 2)
                    run {
                        state = 37
                        match(T__3)
                        state = 38
                        (_localctx as ClassDescContext).name = match(IDENT)
                        state = 39
                        match(T__1)
                        state = 43
                        _errHandler.sync(this)
                        _la = _input.LA(1)
                        while (_la == T__7 || _la == T__10) {
                            run {
                                run {
                                    state = 40
                                    (_localctx as ClassDescContext).body = decl()
                                }
                            }
                            state = 45
                            _errHandler.sync(this)
                            _la = _input.LA(1)
                        }
                        state = 46
                        match(T__2)
                    }
                }

                T__4 -> {
                    _localctx = BgDescContext(_localctx)
                    enterOuterAlt(_localctx, 3)
                    run {
                        state = 47
                        match(T__4)
                        state = 48
                        (_localctx as BgDescContext).name = match(IDENT)
                        state = 49
                        match(T__1)
                        state = 53
                        _errHandler.sync(this)
                        _la = _input.LA(1)
                        while (_la == T__7 || _la == T__10) {
                            run {
                                run {
                                    state = 50
                                    (_localctx as BgDescContext).body = decl()
                                }
                            }
                            state = 55
                            _errHandler.sync(this)
                            _la = _input.LA(1)
                        }
                        state = 56
                        match(T__2)
                    }
                }

                T__5 -> {
                    _localctx = ItemDescContext(_localctx)
                    enterOuterAlt(_localctx, 4)
                    run {
                        state = 57
                        match(T__5)
                        state = 58
                        (_localctx as ItemDescContext).name = match(IDENT)
                        state = 59
                        match(T__1)
                        state = 63
                        _errHandler.sync(this)
                        _la = _input.LA(1)
                        while (_la == T__7 || _la == T__10) {
                            run {
                                run {
                                    state = 60
                                    (_localctx as ItemDescContext).body = decl()
                                }
                            }
                            state = 65
                            _errHandler.sync(this)
                            _la = _input.LA(1)
                        }
                        state = 66
                        match(T__2)
                    }
                }

                T__6 -> {
                    _localctx = SpellDescContext(_localctx)
                    enterOuterAlt(_localctx, 5)
                    run {
                        state = 67
                        match(T__6)
                        state = 68
                        _localctx.name = match(IDENT)
                        state = 69
                        match(T__1)
                        state = 73
                        _errHandler.sync(this)
                        _la = _input.LA(1)
                        while (_la == T__7 || _la == T__10) {
                            run {
                                run {
                                    state = 70
                                    _localctx.body = decl()
                                }
                            }
                            state = 75
                            _errHandler.sync(this)
                            _la = _input.LA(1)
                        }
                        state = 76
                        match(T__2)
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
            if (listener is cmlListener) listener.enterFieldDecl(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitFieldDecl(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitFieldDecl(this) else visitor.visitChildren(
                this
            )
        }
    }

    class FunDeclContext(ctx: DeclContext?) : DeclContext() {
        var name: Token? = null
        var body: StmtContext? = null
        fun argDs(): ArgDsContext {
            return getRuleContext(ArgDsContext::class.java, 0)
        }

        fun IDENT(): TerminalNode {
            return getToken(IDENT, 0)
        }

        fun stmt(): List<StmtContext> {
            return getRuleContexts(StmtContext::class.java)
        }

        fun stmt(i: Int): StmtContext {
            return getRuleContext(StmtContext::class.java, i)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterFunDecl(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitFunDecl(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitFunDecl(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun decl(): DeclContext {
        var _localctx = DeclContext(_ctx, state)
        enterRule(_localctx, 4, RULE_decl)
        var _la: Int
        try {
            state = 99
            _errHandler.sync(this)
            when (_input.LA(1)) {
                T__7 -> {
                    _localctx = FunDeclContext(_localctx)
                    enterOuterAlt(_localctx, 1)
                    run {
                        state = 79
                        match(T__7)
                        state = 80
                        (_localctx as FunDeclContext).name = match(IDENT)
                        state = 81
                        match(T__8)
                        state = 82
                        argDs()
                        state = 83
                        match(T__9)
                        state = 84
                        match(T__1)
                        state = 88
                        _errHandler.sync(this)
                        _la = _input.LA(1)
                        while (_la and 0x3f.inv() == 0 && 1L shl _la and 992310123659780L != 0L) {
                            run {
                                run {
                                    state = 85
                                    (_localctx as FunDeclContext).body = stmt()
                                }
                            }
                            state = 90
                            _errHandler.sync(this)
                            _la = _input.LA(1)
                        }
                        state = 91
                        match(T__2)
                    }
                }

                T__10 -> {
                    _localctx = FieldDeclContext(_localctx)
                    enterOuterAlt(_localctx, 2)
                    run {
                        state = 93
                        match(T__10)
                        state = 94
                        _localctx.name = match(IDENT)
                        state = 95
                        match(T__11)
                        state = 96
                        _localctx.init = expr(0)
                        state = 97
                        match(T__12)
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

    class ArgDsContext(parent: ParserRuleContext?, invokingState: Int) : ParserRuleContext(parent, invokingState) {
        var args: ArgDsNonEmptyContext? = null
        fun argDsNonEmpty(): ArgDsNonEmptyContext {
            return getRuleContext(ArgDsNonEmptyContext::class.java, 0)
        }

        override fun getRuleIndex(): Int {
            return RULE_argDs
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterArgDs(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitArgDs(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitArgDs(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun argDs(): ArgDsContext {
        val _localctx = ArgDsContext(_ctx, state)
        enterRule(_localctx, 6, RULE_argDs)
        try {
            state = 103
            _errHandler.sync(this)
            when (_input.LA(1)) {
                T__9 -> {
                    enterOuterAlt(_localctx, 1)
                    run {}
                }

                IDENT -> {
                    enterOuterAlt(_localctx, 2)
                    run {
                        state = 102
                        _localctx.args = argDsNonEmpty()
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
        var arg: Token? = null
        var arg1: Token? = null
        var args: ArgDsNonEmptyContext? = null
        fun IDENT(): TerminalNode {
            return getToken(IDENT, 0)
        }

        fun argDsNonEmpty(): ArgDsNonEmptyContext {
            return getRuleContext(ArgDsNonEmptyContext::class.java, 0)
        }

        override fun getRuleIndex(): Int {
            return RULE_argDsNonEmpty
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterArgDsNonEmpty(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitArgDsNonEmpty(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitArgDsNonEmpty(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun argDsNonEmpty(): ArgDsNonEmptyContext {
        val _localctx = ArgDsNonEmptyContext(_ctx, state)
        enterRule(_localctx, 8, RULE_argDsNonEmpty)
        try {
            state = 109
            _errHandler.sync(this)
            when (interpreter.adaptivePredict(_input, 9, _ctx)) {
                1 -> {
                    enterOuterAlt(_localctx, 1)
                    run {
                        state = 105
                        _localctx.arg = match(IDENT)
                    }
                }

                2 -> {
                    enterOuterAlt(_localctx, 2)
                    run {
                        state = 106
                        _localctx.arg1 = match(IDENT)
                        state = 107
                        match(T__13)
                        state = 108
                        _localctx.args = argDsNonEmpty()
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
        fun expr(): ExprContext {
            return getRuleContext(ExprContext::class.java, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterExprStmt(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitExprStmt(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitExprStmt(this) else visitor.visitChildren(
                this
            )
        }
    }

    class ForStmtContext(ctx: StmtContext?) : StmtContext() {
        var varN: Token? = null
        var range: ExprContext? = null
        var body: StmtContext? = null
        fun IDENT(): TerminalNode {
            return getToken(IDENT, 0)
        }

        fun expr(): ExprContext {
            return getRuleContext(ExprContext::class.java, 0)
        }

        fun stmt(): List<StmtContext> {
            return getRuleContexts(StmtContext::class.java)
        }

        fun stmt(i: Int): StmtContext {
            return getRuleContext(StmtContext::class.java, i)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterForStmt(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitForStmt(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitForStmt(this) else visitor.visitChildren(
                this
            )
        }
    }

    class WhileStmtContext(ctx: StmtContext?) : StmtContext() {
        var cond: ExprContext? = null
        var body: StmtContext? = null
        fun expr(): ExprContext {
            return getRuleContext(ExprContext::class.java, 0)
        }

        fun stmt(): List<StmtContext> {
            return getRuleContexts(StmtContext::class.java)
        }

        fun stmt(i: Int): StmtContext {
            return getRuleContext(StmtContext::class.java, i)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterWhileStmt(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitWhileStmt(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitWhileStmt(this) else visitor.visitChildren(
                this
            )
        }
    }

    class IfStmtContext(ctx: StmtContext?) : StmtContext() {
        var bTrue: StmtContext? = null
        fun expr(): ExprContext {
            return getRuleContext(ExprContext::class.java, 0)
        }

        fun stmt(): List<StmtContext> {
            return getRuleContexts(StmtContext::class.java)
        }

        fun stmt(i: Int): StmtContext {
            return getRuleContext(StmtContext::class.java, i)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterIfStmt(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitIfStmt(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitIfStmt(this) else visitor.visitChildren(
                this
            )
        }
    }

    class IfElseStmtContext(ctx: StmtContext?) : StmtContext() {
        var bTrue: StmtContext? = null
        var bFalse: StmtContext? = null
        fun expr(): ExprContext {
            return getRuleContext(ExprContext::class.java, 0)
        }

        fun stmt(): List<StmtContext> {
            return getRuleContexts(StmtContext::class.java)
        }

        fun stmt(i: Int): StmtContext {
            return getRuleContext(StmtContext::class.java, i)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterIfElseStmt(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitIfElseStmt(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitIfElseStmt(this) else visitor.visitChildren(
                this
            )
        }
    }

    class CallExprNoArgsContext(ctx: StmtContext?) : StmtContext() {
        var ftor: Token? = null
        var args: ArgsListContext? = null
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
            if (listener is cmlListener) listener.enterCallExprNoArgs(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitCallExprNoArgs(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitCallExprNoArgs(this) else visitor.visitChildren(
                this
            )
        }
    }

    class BreakStmtContext(ctx: StmtContext?) : StmtContext() {
        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterBreakStmt(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitBreakStmt(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitBreakStmt(this) else visitor.visitChildren(
                this
            )
        }
    }

    class VarStoreStmtContext(ctx: StmtContext?) : StmtContext() {
        var name: Token? = null
        var `val`: ExprContext? = null
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
            if (listener is cmlListener) listener.enterVarStoreStmt(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitVarStoreStmt(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitVarStoreStmt(this) else visitor.visitChildren(
                this
            )
        }
    }

    class ReturnStmtContext(ctx: StmtContext?) : StmtContext() {
        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterReturnStmt(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitReturnStmt(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitReturnStmt(this) else visitor.visitChildren(
                this
            )
        }
    }

    class VarDeclStmtContext(ctx: StmtContext?) : StmtContext() {
        var name: Token? = null
        var init: ExprContext? = null
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
            if (listener is cmlListener) listener.enterVarDeclStmt(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitVarDeclStmt(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitVarDeclStmt(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun stmt(): StmtContext {
        var _localctx = StmtContext(_ctx, state)
        enterRule(_localctx, 10, RULE_stmt)
        var _la: Int
        try {
            state = 198
            _errHandler.sync(this)
            when (interpreter.adaptivePredict(_input, 15, _ctx)) {
                1 -> {
                    _localctx = ExprStmtContext(_localctx)
                    enterOuterAlt(_localctx, 1)
                    run {
                        state = 111
                        (_localctx as ExprStmtContext).e = expr(0)
                        state = 112
                        match(T__12)
                    }
                }

                2 -> {
                    _localctx = VarDeclStmtContext(_localctx)
                    enterOuterAlt(_localctx, 2)
                    run {
                        state = 114
                        match(T__14)
                        state = 115
                        (_localctx as VarDeclStmtContext).name = match(IDENT)
                        state = 116
                        match(T__11)
                        state = 117
                        (_localctx as VarDeclStmtContext).init = expr(0)
                        state = 118
                        match(T__12)
                    }
                }

                3 -> {
                    _localctx = VarStoreStmtContext(_localctx)
                    enterOuterAlt(_localctx, 3)
                    run {
                        state = 120
                        (_localctx as VarStoreStmtContext).name = match(IDENT)
                        state = 121
                        match(T__11)
                        state = 122
                        (_localctx as VarStoreStmtContext).`val` = expr(0)
                        state = 123
                        match(T__12)
                    }
                }

                4 -> {
                    _localctx = IfStmtContext(_localctx)
                    enterOuterAlt(_localctx, 4)
                    run {
                        state = 125
                        match(T__15)
                        state = 126
                        match(T__8)
                        state = 127
                        expr(0)
                        state = 128
                        match(T__9)
                        state = 129
                        match(T__1)
                        state = 133
                        _errHandler.sync(this)
                        _la = _input.LA(1)
                        while (_la and 0x3f.inv() == 0 && 1L shl _la and 992310123659780L != 0L) {
                            run {
                                run {
                                    state = 130
                                    (_localctx as IfStmtContext).bTrue = stmt()
                                }
                            }
                            state = 135
                            _errHandler.sync(this)
                            _la = _input.LA(1)
                        }
                        state = 136
                        match(T__2)
                    }
                }

                5 -> {
                    _localctx = IfElseStmtContext(_localctx)
                    enterOuterAlt(_localctx, 5)
                    run {
                        state = 138
                        match(T__15)
                        state = 139
                        match(T__8)
                        state = 140
                        expr(0)
                        state = 141
                        match(T__9)
                        state = 142
                        match(T__1)
                        state = 146
                        _errHandler.sync(this)
                        _la = _input.LA(1)
                        while (_la and 0x3f.inv() == 0 && 1L shl _la and 992310123659780L != 0L) {
                            run {
                                run {
                                    state = 143
                                    (_localctx as IfElseStmtContext).bTrue = stmt()
                                }
                            }
                            state = 148
                            _errHandler.sync(this)
                            _la = _input.LA(1)
                        }
                        state = 149
                        match(T__2)
                        state = 150
                        match(T__16)
                        state = 151
                        match(T__1)
                        state = 155
                        _errHandler.sync(this)
                        _la = _input.LA(1)
                        while (_la and 0x3f.inv() == 0 && 1L shl _la and 992310123659780L != 0L) {
                            run {
                                run {
                                    state = 152
                                    (_localctx as IfElseStmtContext).bFalse = stmt()
                                }
                            }
                            state = 157
                            _errHandler.sync(this)
                            _la = _input.LA(1)
                        }
                        state = 158
                        match(T__2)
                    }
                }

                6 -> {
                    _localctx = WhileStmtContext(_localctx)
                    enterOuterAlt(_localctx, 6)
                    run {
                        state = 160
                        match(T__17)
                        state = 161
                        match(T__8)
                        state = 162
                        (_localctx as WhileStmtContext).cond = expr(0)
                        state = 163
                        match(T__9)
                        state = 164
                        match(T__1)
                        state = 168
                        _errHandler.sync(this)
                        _la = _input.LA(1)
                        while (_la and 0x3f.inv() == 0 && 1L shl _la and 992310123659780L != 0L) {
                            run {
                                run {
                                    state = 165
                                    (_localctx as WhileStmtContext).body = stmt()
                                }
                            }
                            state = 170
                            _errHandler.sync(this)
                            _la = _input.LA(1)
                        }
                        state = 171
                        match(T__2)
                    }
                }

                7 -> {
                    _localctx = ForStmtContext(_localctx)
                    enterOuterAlt(_localctx, 7)
                    run {
                        state = 173
                        match(T__18)
                        state = 174
                        match(T__8)
                        state = 175
                        (_localctx as ForStmtContext).varN = match(IDENT)
                        state = 176
                        match(T__19)
                        state = 177
                        (_localctx as ForStmtContext).range = expr(0)
                        state = 178
                        match(T__9)
                        state = 179
                        match(T__1)
                        state = 183
                        _errHandler.sync(this)
                        _la = _input.LA(1)
                        while (_la and 0x3f.inv() == 0 && 1L shl _la and 992310123659780L != 0L) {
                            run {
                                run {
                                    state = 180
                                    (_localctx as ForStmtContext).body = stmt()
                                }
                            }
                            state = 185
                            _errHandler.sync(this)
                            _la = _input.LA(1)
                        }
                        state = 186
                        match(T__2)
                    }
                }

                8 -> {
                    _localctx = BreakStmtContext(_localctx)
                    enterOuterAlt(_localctx, 8)
                    run {
                        state = 188
                        match(T__20)
                        state = 189
                        match(T__12)
                    }
                }

                9 -> {
                    _localctx = ReturnStmtContext(_localctx)
                    enterOuterAlt(_localctx, 9)
                    run {
                        state = 190
                        match(T__21)
                        state = 191
                        match(T__12)
                    }
                }

                10 -> {
                    _localctx = CallExprNoArgsContext(_localctx)
                    enterOuterAlt(_localctx, 10)
                    run {
                        state = 192
                        _localctx.ftor = match(IDENT)
                        state = 193
                        match(T__8)
                        state = 194
                        _localctx.args = argsList()
                        state = 195
                        match(T__9)
                        state = 196
                        match(T__12)
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

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterAddSubExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitAddSubExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitAddSubExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class ModExprContext(ctx: ExprContext?) : ExprContext() {
        var left: ExprContext? = null
        var right: ExprContext? = null
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
            if (listener is cmlListener) listener.enterModExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitModExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitModExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class UntilExprContext(ctx: ExprContext?) : ExprContext() {
        var start: ExprContext? = null
        var end: ExprContext? = null
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
            if (listener is cmlListener) listener.enterUntilExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitUntilExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitUntilExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class ParenExprContext(ctx: ExprContext?) : ExprContext() {
        var content: ExprContext? = null
        fun expr(): ExprContext {
            return getRuleContext(ExprContext::class.java, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterParenExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitParenExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitParenExpr(this) else visitor.visitChildren(
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

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterBitwiseExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitBitwiseExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitBitwiseExpr(this) else visitor.visitChildren(
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
            if (listener is cmlListener) listener.enterStringLit(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitStringLit(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitStringLit(this) else visitor.visitChildren(
                this
            )
        }
    }

    class VarExprContext(ctx: ExprContext?) : ExprContext() {
        var `var`: Token? = null
        fun IDENT(): TerminalNode {
            return getToken(IDENT, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterVarExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitVarExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitVarExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class UnaryExprContext(ctx: ExprContext?) : ExprContext() {
        var op: Token? = null
        var `val`: ExprContext? = null
        fun expr(): ExprContext {
            return getRuleContext(ExprContext::class.java, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterUnaryExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitUnaryExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitUnaryExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class TernaryExprContext(ctx: ExprContext?) : ExprContext() {
        var condition: ExprContext? = null
        var bTrue: ExprContext? = null
        var bFalse: ExprContext? = null
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
            if (listener is cmlListener) listener.enterTernaryExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitTernaryExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitTernaryExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class BoolLitContext(ctx: ExprContext?) : ExprContext() {
        var `val`: Token? = null
        fun BOOL(): TerminalNode {
            return getToken(BOOL, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterBoolLit(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitBoolLit(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitBoolLit(this) else visitor.visitChildren(
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

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterLogicExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitLogicExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitLogicExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class IntLitContext(ctx: ExprContext?) : ExprContext() {
        var `val`: Token? = null
        fun INT(): TerminalNode {
            return getToken(INT, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterIntLit(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitIntLit(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitIntLit(this) else visitor.visitChildren(
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
            if (listener is cmlListener) listener.enterDiceExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitDiceExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitDiceExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class DictExprContext(ctx: ExprContext?) : ExprContext() {
        var values: KvpListContext? = null
        fun kvpList(): KvpListContext {
            return getRuleContext(KvpListContext::class.java, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterDictExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitDictExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitDictExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class ListExprContext(ctx: ExprContext?) : ExprContext() {
        var values: ArgsListContext? = null
        fun argsList(): ArgsListContext {
            return getRuleContext(ArgsListContext::class.java, 0)
        }

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterListExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitListExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitListExpr(this) else visitor.visitChildren(
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

        init {
            copyFrom(ctx)
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterMulDivExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitMulDivExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitMulDivExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    class RangeExprContext(ctx: ExprContext?) : ExprContext() {
        var start: ExprContext? = null
        var end: ExprContext? = null
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
            if (listener is cmlListener) listener.enterRangeExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitRangeExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitRangeExpr(this) else visitor.visitChildren(
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
            if (listener is cmlListener) listener.enterCompareExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitCompareExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitCompareExpr(this) else visitor.visitChildren(
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
        val _startState = 12
        enterRecursionRule(_localctx, 12, RULE_expr, _p)
        var _la: Int
        try {
            var _alt: Int
            enterOuterAlt(_localctx, 1)
            run {
                state = 219
                _errHandler.sync(this)
                when (_input.LA(1)) {
                    T__40, T__41 -> {
                        _localctx = StringLitContext(_localctx)
                        _ctx = _localctx
                        _prevctx = _localctx
                        state = 201
                        (_localctx as StringLitContext).str = stringExpr()
                    }

                    INT -> {
                        _localctx = IntLitContext(_localctx)
                        _ctx = _localctx
                        _prevctx = _localctx
                        state = 202
                        (_localctx as IntLitContext).`val` = match(INT)
                    }

                    BOOL -> {
                        _localctx = BoolLitContext(_localctx)
                        _ctx = _localctx
                        _prevctx = _localctx
                        state = 203
                        (_localctx as BoolLitContext).`val` = match(BOOL)
                    }

                    IDENT -> {
                        _localctx = VarExprContext(_localctx)
                        _ctx = _localctx
                        _prevctx = _localctx
                        state = 204
                        (_localctx as VarExprContext).`var` = match(IDENT)
                    }

                    T__8 -> {
                        _localctx = ParenExprContext(_localctx)
                        _ctx = _localctx
                        _prevctx = _localctx
                        state = 205
                        match(T__8)
                        state = 206
                        (_localctx as ParenExprContext).content = expr(0)
                        state = 207
                        match(T__9)
                    }

                    T__25, T__27, T__28 -> {
                        _localctx = UnaryExprContext(_localctx)
                        _ctx = _localctx
                        _prevctx = _localctx
                        state = 209
                        (_localctx as UnaryExprContext).op = _input.LT(1)
                        _la = _input.LA(1)
                        if (!(_la and 0x3f.inv() == 0 && 1L shl _la and 872415232L != 0L)) {
                            (_localctx as UnaryExprContext).op = _errHandler.recoverInline(this) as Token
                        } else {
                            if (_input.LA(1) == Token.EOF) matchedEOF = true
                            _errHandler.reportMatch(this)
                            consume()
                        }
                        state = 210
                        (_localctx as UnaryExprContext).`val` = expr(9)
                    }

                    T__38 -> {
                        _localctx = ListExprContext(_localctx)
                        _ctx = _localctx
                        _prevctx = _localctx
                        state = 211
                        match(T__38)
                        state = 212
                        (_localctx as ListExprContext).values = argsList()
                        state = 213
                        match(T__39)
                    }

                    T__1 -> {
                        _localctx = DictExprContext(_localctx)
                        _ctx = _localctx
                        _prevctx = _localctx
                        state = 215
                        match(T__1)
                        state = 216
                        (_localctx as DictExprContext).values = kvpList()
                        state = 217
                        match(T__2)
                    }

                    else -> throw NoViableAltException(this)
                }
                _ctx.stop = _input.LT(-1)
                state = 256
                _errHandler.sync(this)
                _alt = interpreter.adaptivePredict(_input, 18, _ctx)
                while (_alt != 2 && _alt != ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent()
                        _prevctx = _localctx
                        run {
                            state = 254
                            _errHandler.sync(this)
                            when (interpreter.adaptivePredict(_input, 17, _ctx)) {
                                1 -> {
                                    _localctx = DiceExprContext(ExprContext(_parentctx, _parentState))
                                    (_localctx as DiceExprContext).count = _prevctx
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr)
                                    state = 221
                                    if (!precpred(_ctx, 13)) throw FailedPredicateException(this, "precpred(_ctx, 13)")
                                    state = 222
                                    match(DICE)
                                    state = 223
                                    (_localctx as DiceExprContext).dice = expr(14)
                                }

                                2 -> {
                                    _localctx = MulDivExprContext(ExprContext(_parentctx, _parentState))
                                    (_localctx as MulDivExprContext).left = _prevctx
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr)
                                    state = 224
                                    if (!precpred(_ctx, 12)) throw FailedPredicateException(this, "precpred(_ctx, 12)")
                                    state = 225
                                    (_localctx as MulDivExprContext).op = _input.LT(1)
                                    _la = _input.LA(1)
                                    if (!(_la == T__22 || _la == T__23)) {
                                        (_localctx as MulDivExprContext).op = _errHandler.recoverInline(this) as Token
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true
                                        _errHandler.reportMatch(this)
                                        consume()
                                    }
                                    state = 226
                                    (_localctx as MulDivExprContext).right = expr(13)
                                }

                                3 -> {
                                    _localctx = AddSubExprContext(ExprContext(_parentctx, _parentState))
                                    (_localctx as AddSubExprContext).left = _prevctx
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr)
                                    state = 227
                                    if (!precpred(_ctx, 11)) throw FailedPredicateException(this, "precpred(_ctx, 11)")
                                    state = 228
                                    (_localctx as AddSubExprContext).op = _input.LT(1)
                                    _la = _input.LA(1)
                                    if (!(_la == T__24 || _la == T__25)) {
                                        (_localctx as AddSubExprContext).op = _errHandler.recoverInline(this) as Token
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true
                                        _errHandler.reportMatch(this)
                                        consume()
                                    }
                                    state = 229
                                    (_localctx as AddSubExprContext).right = expr(12)
                                }

                                4 -> {
                                    _localctx = ModExprContext(ExprContext(_parentctx, _parentState))
                                    (_localctx as ModExprContext).left = _prevctx
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr)
                                    state = 230
                                    if (!precpred(_ctx, 10)) throw FailedPredicateException(this, "precpred(_ctx, 10)")
                                    state = 231
                                    match(T__26)
                                    state = 232
                                    (_localctx as ModExprContext).right = expr(11)
                                }

                                5 -> {
                                    _localctx = CompareExprContext(ExprContext(_parentctx, _parentState))
                                    (_localctx as CompareExprContext).left = _prevctx
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr)
                                    state = 233
                                    if (!precpred(_ctx, 8)) throw FailedPredicateException(this, "precpred(_ctx, 8)")
                                    state = 234
                                    (_localctx as CompareExprContext).op = match(COMPARISON_OP)
                                    state = 235
                                    (_localctx as CompareExprContext).right = expr(9)
                                }

                                6 -> {
                                    _localctx = LogicExprContext(ExprContext(_parentctx, _parentState))
                                    (_localctx as LogicExprContext).left = _prevctx
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr)
                                    state = 236
                                    if (!precpred(_ctx, 7)) throw FailedPredicateException(this, "precpred(_ctx, 7)")
                                    state = 237
                                    (_localctx as LogicExprContext).op = _input.LT(1)
                                    _la = _input.LA(1)
                                    if (!(_la == T__29 || _la == T__30)) {
                                        (_localctx as LogicExprContext).op = _errHandler.recoverInline(this) as Token
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true
                                        _errHandler.reportMatch(this)
                                        consume()
                                    }
                                    state = 238
                                    (_localctx as LogicExprContext).right = expr(8)
                                }

                                7 -> {
                                    _localctx = BitwiseExprContext(ExprContext(_parentctx, _parentState))
                                    (_localctx as BitwiseExprContext).left = _prevctx
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr)
                                    state = 239
                                    if (!precpred(_ctx, 6)) throw FailedPredicateException(this, "precpred(_ctx, 6)")
                                    state = 240
                                    (_localctx as BitwiseExprContext).op = _input.LT(1)
                                    _la = _input.LA(1)
                                    if (!(_la and 0x3f.inv() == 0 && 1L shl _la and 30064771072L != 0L)) {
                                        (_localctx as BitwiseExprContext).op = _errHandler.recoverInline(this) as Token
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true
                                        _errHandler.reportMatch(this)
                                        consume()
                                    }
                                    state = 241
                                    (_localctx as BitwiseExprContext).right = expr(7)
                                }

                                8 -> {
                                    _localctx = TernaryExprContext(ExprContext(_parentctx, _parentState))
                                    (_localctx as TernaryExprContext).condition = _prevctx
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr)
                                    state = 242
                                    if (!precpred(_ctx, 5)) throw FailedPredicateException(this, "precpred(_ctx, 5)")
                                    state = 243
                                    match(T__34)
                                    state = 244
                                    (_localctx as TernaryExprContext).bTrue = expr(0)
                                    state = 245
                                    match(T__35)
                                    state = 246
                                    (_localctx as TernaryExprContext).bFalse = expr(6)
                                }

                                9 -> {
                                    _localctx = RangeExprContext(ExprContext(_parentctx, _parentState))
                                    (_localctx as RangeExprContext).start = _prevctx as Token
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr)
                                    state = 248
                                    if (!precpred(_ctx, 4)) throw FailedPredicateException(this, "precpred(_ctx, 4)")
                                    state = 249
                                    match(T__36)
                                    state = 250
                                    (_localctx as RangeExprContext).end = expr(5)
                                }

                                10 -> {
                                    _localctx = UntilExprContext(ExprContext(_parentctx, _parentState))
                                    (_localctx as UntilExprContext).start = _prevctx as Token
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr)
                                    state = 251
                                    if (!precpred(_ctx, 3)) throw FailedPredicateException(this, "precpred(_ctx, 3)")
                                    state = 252
                                    match(T__37)
                                    state = 253
                                    (_localctx as UntilExprContext).end = expr(4)
                                }
                            }
                        }
                    }
                    state = 258
                    _errHandler.sync(this)
                    _alt = interpreter.adaptivePredict(_input, 18, _ctx)
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
            if (listener is cmlListener) listener.enterKvpList(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitKvpList(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitKvpList(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun kvpList(): KvpListContext {
        val _localctx = KvpListContext(_ctx, state)
        enterRule(_localctx, 14, RULE_kvpList)
        try {
            state = 261
            _errHandler.sync(this)
            when (_input.LA(1)) {
                T__2 -> {
                    enterOuterAlt(_localctx, 1)
                    run {}
                }

                T__1, T__8, T__25, T__27, T__28, T__38, T__40, T__41, BOOL, INT, IDENT -> {
                    enterOuterAlt(_localctx, 2)
                    run {
                        state = 260
                        _localctx.values = nonEmptyKvp()
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
        var key: ExprContext? = null
        var `val`: ExprContext? = null
        var res: NonEmptyKvpContext? = null
        fun expr(): List<ExprContext> {
            return getRuleContexts(ExprContext::class.java)
        }

        fun expr(i: Int): ExprContext {
            return getRuleContext(ExprContext::class.java, i)
        }

        fun nonEmptyKvp(): NonEmptyKvpContext {
            return getRuleContext(NonEmptyKvpContext::class.java, 0)
        }

        override fun getRuleIndex(): Int {
            return RULE_nonEmptyKvp
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterNonEmptyKvp(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitNonEmptyKvp(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitNonEmptyKvp(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun nonEmptyKvp(): NonEmptyKvpContext {
        val _localctx = NonEmptyKvpContext(_ctx, state)
        enterRule(_localctx, 16, RULE_nonEmptyKvp)
        try {
            state = 273
            _errHandler.sync(this)
            when (interpreter.adaptivePredict(_input, 20, _ctx)) {
                1 -> {
                    enterOuterAlt(_localctx, 1)
                    run {
                        state = 263
                        _localctx.key = expr(0)
                        state = 264
                        match(T__11)
                        state = 265
                        _localctx.`val` = expr(0)
                    }
                }

                2 -> {
                    enterOuterAlt(_localctx, 2)
                    run {
                        state = 267
                        _localctx.key = expr(0)
                        state = 268
                        match(T__11)
                        state = 269
                        _localctx.`val` = expr(0)
                        state = 270
                        match(T__13)
                        state = 271
                        _localctx.res = nonEmptyKvp()
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

    class ArgsListContext(parent: ParserRuleContext?, invokingState: Int) : ParserRuleContext(parent, invokingState) {
        var args: NonEmptyArgsContext? = null
        fun nonEmptyArgs(): NonEmptyArgsContext {
            return getRuleContext(NonEmptyArgsContext::class.java, 0)
        }

        override fun getRuleIndex(): Int {
            return RULE_argsList
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterArgsList(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitArgsList(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitArgsList(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun argsList(): ArgsListContext {
        val _localctx = ArgsListContext(_ctx, state)
        enterRule(_localctx, 18, RULE_argsList)
        try {
            state = 277
            _errHandler.sync(this)
            when (_input.LA(1)) {
                T__9, T__39 -> {
                    enterOuterAlt(_localctx, 1)
                    run {}
                }

                T__1, T__8, T__25, T__27, T__28, T__38, T__40, T__41, BOOL, INT, IDENT -> {
                    enterOuterAlt(_localctx, 2)
                    run {
                        state = 276
                        _localctx.args = nonEmptyArgs()
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
        var arg: ExprContext? = null
        var rest: NonEmptyArgsContext? = null
        fun expr(): ExprContext {
            return getRuleContext(ExprContext::class.java, 0)
        }

        fun nonEmptyArgs(): NonEmptyArgsContext {
            return getRuleContext(NonEmptyArgsContext::class.java, 0)
        }

        override fun getRuleIndex(): Int {
            return RULE_nonEmptyArgs
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterNonEmptyArgs(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitNonEmptyArgs(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitNonEmptyArgs(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun nonEmptyArgs(): NonEmptyArgsContext {
        val _localctx = NonEmptyArgsContext(_ctx, state)
        enterRule(_localctx, 20, RULE_nonEmptyArgs)
        try {
            state = 284
            _errHandler.sync(this)
            when (interpreter.adaptivePredict(_input, 22, _ctx)) {
                1 -> {
                    enterOuterAlt(_localctx, 1)
                    run {
                        state = 279
                        _localctx.arg = expr(0)
                    }
                }

                2 -> {
                    enterOuterAlt(_localctx, 2)
                    run {
                        state = 280
                        _localctx.arg = expr(0)
                        state = 281
                        match(T__13)
                        state = 282
                        _localctx.rest = nonEmptyArgs()
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

    class StringExprContext(parent: ParserRuleContext?, invokingState: Int) : ParserRuleContext(parent, invokingState) {
        var content: Token? = null
        fun STRING_CHARS(): TerminalNode {
            return getToken(STRING_CHARS, 0)
        }

        override fun getRuleIndex(): Int {
            return RULE_stringExpr
        }

        override fun enterRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.enterStringExpr(this)
        }

        override fun exitRule(listener: ParseTreeListener) {
            if (listener is cmlListener) listener.exitStringExpr(this)
        }

        override fun <T> accept(visitor: ParseTreeVisitor<out T>): T {
            return if (visitor is cmlVisitor<*>) (visitor as cmlVisitor<out T>).visitStringExpr(this) else visitor.visitChildren(
                this
            )
        }
    }

    @Throws(RecognitionException::class)
    fun stringExpr(): StringExprContext {
        val _localctx = StringExprContext(_ctx, state)
        enterRule(_localctx, 22, RULE_stringExpr)
        try {
            state = 290
            _errHandler.sync(this)
            when (_input.LA(1)) {
                T__40 -> {
                    enterOuterAlt(_localctx, 1)
                    run {
                        state = 286
                        match(T__40)
                    }
                }

                T__41 -> {
                    enterOuterAlt(_localctx, 2)
                    run {
                        state = 287
                        match(T__41)
                        state = 288
                        _localctx.content = match(STRING_CHARS)
                        state = 289
                        match(T__41)
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

    override fun sempred(_localctx: RuleContext, ruleIndex: Int, predIndex: Int): Boolean {
        when (ruleIndex) {
            6 -> return expr_sempred(_localctx as ExprContext, predIndex)
        }
        return true
    }

    private fun expr_sempred(_localctx: ExprContext, predIndex: Int): Boolean {
        when (predIndex) {
            0 -> return precpred(_ctx, 13)
            1 -> return precpred(_ctx, 12)
            2 -> return precpred(_ctx, 11)
            3 -> return precpred(_ctx, 10)
            4 -> return precpred(_ctx, 8)
            5 -> return precpred(_ctx, 7)
            6 -> return precpred(_ctx, 6)
            7 -> return precpred(_ctx, 5)
            8 -> return precpred(_ctx, 4)
            9 -> return precpred(_ctx, 3)
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
        const val T__0 = 1
        const val T__1 = 2
        const val T__2 = 3
        const val T__3 = 4
        const val T__4 = 5
        const val T__5 = 6
        const val T__6 = 7
        const val T__7 = 8
        const val T__8 = 9
        const val T__9 = 10
        const val T__10 = 11
        const val T__11 = 12
        const val T__12 = 13
        const val T__13 = 14
        const val T__14 = 15
        const val T__15 = 16
        const val T__16 = 17
        const val T__17 = 18
        const val T__18 = 19
        const val T__19 = 20
        const val T__20 = 21
        const val T__21 = 22
        const val T__22 = 23
        const val T__23 = 24
        const val T__24 = 25
        const val T__25 = 26
        const val T__26 = 27
        const val T__27 = 28
        const val T__28 = 29
        const val T__29 = 30
        const val T__30 = 31
        const val T__31 = 32
        const val T__32 = 33
        const val T__33 = 34
        const val T__34 = 35
        const val T__35 = 36
        const val T__36 = 37
        const val T__37 = 38
        const val T__38 = 39
        const val T__39 = 40
        const val T__40 = 41
        const val T__41 = 42
        const val NEWLINE = 43
        const val WS = 44
        const val COMMENT_CHARS = 45
        const val DICE = 46
        const val BOOL = 47
        const val INT = 48
        const val IDENT = 49
        const val STRING_CHARS = 50
        const val COMPARISON_OP = 51
        const val RULE_program = 0
        const val RULE_topLevel = 1
        const val RULE_decl = 2
        const val RULE_argDs = 3
        const val RULE_argDsNonEmpty = 4
        const val RULE_stmt = 5
        const val RULE_expr = 6
        const val RULE_kvpList = 7
        const val RULE_nonEmptyKvp = 8
        const val RULE_argsList = 9
        const val RULE_nonEmptyArgs = 10
        const val RULE_stringExpr = 11
        private fun makeRuleNames(): Array<String> {
            return arrayOf(
                "program", "topLevel", "decl", "argDs", "argDsNonEmpty", "stmt", "expr",
                "kvpList", "nonEmptyKvp", "argsList", "nonEmptyArgs", "stringExpr"
            )
        }

        val ruleNames = makeRuleNames()
        private fun makeLiteralNames(): Array<String?> {
            return arrayOf(
                null, "'race'", "'{'", "'}'", "'class'", "'background'", "'item'", "'spell'",
                "'fun'", "'('", "')'", "'field'", "'='", "';'", "','", "'var'", "'if'",
                "'else'", "'while'", "'for'", "'in'", "'break'", "'return'", "'*'", "'/'",
                "'+'", "'-'", "'%'", "'~'", "'!'", "'&&'", "'||'", "'^'", "'&'", "'|'",
                "'?'", "':'", "'...'", "'until'", "'['", "']'", "'\"\"'", "'\"'"
            )
        }

        private val _LITERAL_NAMES = makeLiteralNames()
        private fun makeSymbolicNames(): Array<String?> {
            return arrayOf(
                null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, "NEWLINE", "WS", "COMMENT_CHARS",
                "DICE", "BOOL", "INT", "IDENT", "STRING_CHARS", "COMPARISON_OP"
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

        const val _serializedATN = "\u0004\u00013\u0125\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002" +
                "\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002" +
                "\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002" +
                "\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0001" +
                "\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001" +
                "\u0001\u0005\u0001 \b\u0001\n\u0001\u000c\u0001#\t\u0001\u0001\u0001\u0001" +
                "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001*\b\u0001\n\u0001" +
                "\u000c\u0001-\t\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001" +
                "\u0001\u0005\u00014\b\u0001\n\u0001\u000c\u00017\t\u0001\u0001\u0001\u0001" +
                "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001>\b\u0001\n\u0001" +
                "\u000c\u0001A\t\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001" +
                "\u0001\u0005\u0001H\b\u0001\n\u0001\u000c\u0001K\t\u0001\u0001\u0001\u0003" +
                "\u0001N\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001" +
                "\u0002\u0001\u0002\u0001\u0002\u0005\u0002W\b\u0002\n\u0002\u000c\u0002Z\t" +
                "\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001" +
                "\u0002\u0001\u0002\u0001\u0002\u0003\u0002d\b\u0002\u0001\u0003\u0001" +
                "\u0003\u0003\u0003h\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001" +
                "\u0004\u0003\u0004n\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001" +
                "\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001" +
                "\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001" +
                "\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005\u0084" +
                "\b\u0005\n\u0005\u000c\u0005\u0087\t\u0005\u0001\u0005\u0001\u0005\u0001\u0005" +
                "\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005" +
                "\u0091\b\u0005\n\u0005\u000c\u0005\u0094\t\u0005\u0001\u0005\u0001\u0005\u0001" +
                "\u0005\u0001\u0005\u0005\u0005\u009a\b\u0005\n\u0005\u000c\u0005\u009d\t\u0005" +
                "\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005" +
                "\u0001\u0005\u0001\u0005\u0005\u0005\u00a7\b\u0005\n\u0005\u000c\u0005\u00aa" +
                "\t\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001" +
                "\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005\u00b6" +
                "\b\u0005\n\u0005\u000c\u0005\u00b9\t\u0005\u0001\u0005\u0001\u0005\u0001\u0005" +
                "\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005" +
                "\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005\u00c7\b\u0005\u0001\u0006" +
                "\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006" +
                "\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006" +
                "\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006" +
                "\u0003\u0006\u00dc\b\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006" +
                "\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006" +
                "\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006" +
                "\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006" +
                "\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006" +
                "\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0005\u0006" +
                "\u00ff\b\u0006\n\u0006\u000c\u0006\u0102\t\u0006\u0001\u0007\u0001\u0007\u0003" +
                "\u0007\u0106\b\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001" +
                "\b\u0001\b\u0001\b\u0001\b\u0003\b\u0112\b\b\u0001\t\u0001\t\u0003\t\u0116" +
                "\b\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n\u011d\b\n\u0001\u000b" +
                "\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u0123\b\u000b\u0001\u000b" +
                "\u0000\u0001\u000c\u000c\u0000\u0002\u0004\u0006\b\n\u000c\u000e\u0010\u0012\u0014" +
                "\u0016\u0000\u0005\u0002\u0000\u001a\u001a\u001c\u001d\u0001\u0000\u0017" +
                "\u0018\u0001\u0000\u0019\u001a\u0001\u0000\u001e\u001f\u0001\u0000 \"" +
                "\u0149\u0000\u0018\u0001\u0000\u0000\u0000\u0002M\u0001\u0000\u0000\u0000" +
                "\u0004c\u0001\u0000\u0000\u0000\u0006g\u0001\u0000\u0000\u0000\bm\u0001" +
                "\u0000\u0000\u0000\n\u00c6\u0001\u0000\u0000\u0000\u000c\u00db\u0001\u0000" +
                "\u0000\u0000\u000e\u0105\u0001\u0000\u0000\u0000\u0010\u0111\u0001\u0000" +
                "\u0000\u0000\u0012\u0115\u0001\u0000\u0000\u0000\u0014\u011c\u0001\u0000" +
                "\u0000\u0000\u0016\u0122\u0001\u0000\u0000\u0000\u0018\u0019\u0003\u0002" +
                "\u0001\u0000\u0019\u001a\u0005\u0000\u0000\u0001\u001a\u0001\u0001\u0000" +
                "\u0000\u0000\u001b\u001c\u0005\u0001\u0000\u0000\u001c\u001d\u00051\u0000" +
                "\u0000\u001d!\u0005\u0002\u0000\u0000\u001e \u0003\u0004\u0002\u0000\u001f" +
                "\u001e\u0001\u0000\u0000\u0000 #\u0001\u0000\u0000\u0000!\u001f\u0001" +
                "\u0000\u0000\u0000!\"\u0001\u0000\u0000\u0000\"$\u0001\u0000\u0000\u0000" +
                "#!\u0001\u0000\u0000\u0000\$N\u0005\u0003\u0000\u0000%&\u0005\u0004\u0000" +
                "\u0000&\'\u00051\u0000\u0000\'+\u0005\u0002\u0000\u0000(*\u0003\u0004" +
                "\u0002\u0000)(\u0001\u0000\u0000\u0000*-\u0001\u0000\u0000\u0000+)\u0001" +
                "\u0000\u0000\u0000+,\u0001\u0000\u0000\u0000,.\u0001\u0000\u0000\u0000" +
                "-+\u0001\u0000\u0000\u0000.N\u0005\u0003\u0000\u0000/0\u0005\u0005\u0000" +
                "\u000001\u00051\u0000\u000015\u0005\u0002\u0000\u000024\u0003\u0004\u0002" +
                "\u000032\u0001\u0000\u0000\u000047\u0001\u0000\u0000\u000053\u0001\u0000" +
                "\u0000\u000056\u0001\u0000\u0000\u000068\u0001\u0000\u0000\u000075\u0001" +
                "\u0000\u0000\u00008N\u0005\u0003\u0000\u00009:\u0005\u0006\u0000\u0000" +
                ":;\u00051\u0000\u0000;?\u0005\u0002\u0000\u0000<>\u0003\u0004\u0002\u0000" +
                "=<\u0001\u0000\u0000\u0000>A\u0001\u0000\u0000\u0000?=\u0001\u0000\u0000" +
                "\u0000?@\u0001\u0000\u0000\u0000@B\u0001\u0000\u0000\u0000A?\u0001\u0000" +
                "\u0000\u0000BN\u0005\u0003\u0000\u0000CD\u0005\u0007\u0000\u0000DE\u0005" +
                "1\u0000\u0000EI\u0005\u0002\u0000\u0000FH\u0003\u0004\u0002\u0000GF\u0001" +
                "\u0000\u0000\u0000HK\u0001\u0000\u0000\u0000IG\u0001\u0000\u0000\u0000" +
                "IJ\u0001\u0000\u0000\u0000JL\u0001\u0000\u0000\u0000KI\u0001\u0000\u0000" +
                "\u0000LN\u0005\u0003\u0000\u0000M\u001b\u0001\u0000\u0000\u0000M%\u0001" +
                "\u0000\u0000\u0000M/\u0001\u0000\u0000\u0000M9\u0001\u0000\u0000\u0000" +
                "MC\u0001\u0000\u0000\u0000N\u0003\u0001\u0000\u0000\u0000OP\u0005\b\u0000" +
                "\u0000PQ\u00051\u0000\u0000QR\u0005\t\u0000\u0000RS\u0003\u0006\u0003" +
                "\u0000ST\u0005\n\u0000\u0000TX\u0005\u0002\u0000\u0000UW\u0003\n\u0005" +
                "\u0000VU\u0001\u0000\u0000\u0000WZ\u0001\u0000\u0000\u0000XV\u0001\u0000" +
                "\u0000\u0000XY\u0001\u0000\u0000\u0000Y[\u0001\u0000\u0000\u0000ZX\u0001" +
                "\u0000\u0000\u0000[\\\u0005\u0003\u0000\u0000\\d\u0001\u0000\u0000\u0000" +
                "]^\u0005\u000b\u0000\u0000^_\u00051\u0000\u0000_`\u0005\u000c\u0000\u0000" +
                "`a\u0003\u000c\u0006\u0000ab\u0005\r\u0000\u0000bd\u0001\u0000\u0000\u0000" +
                "cO\u0001\u0000\u0000\u0000c]\u0001\u0000\u0000\u0000d\u0005\u0001\u0000" +
                "\u0000\u0000eh\u0001\u0000\u0000\u0000fh\u0003\b\u0004\u0000ge\u0001\u0000" +
                "\u0000\u0000gf\u0001\u0000\u0000\u0000h\u0007\u0001\u0000\u0000\u0000" +
                "in\u00051\u0000\u0000jk\u00051\u0000\u0000kl\u0005\u000e\u0000\u0000l" +
                "n\u0003\b\u0004\u0000mi\u0001\u0000\u0000\u0000mj\u0001\u0000\u0000\u0000" +
                "n\t\u0001\u0000\u0000\u0000op\u0003\u000c\u0006\u0000pq\u0005\r\u0000\u0000" +
                "q\u00c7\u0001\u0000\u0000\u0000rs\u0005\u000f\u0000\u0000st\u00051\u0000" +
                "\u0000tu\u0005\u000c\u0000\u0000uv\u0003\u000c\u0006\u0000vw\u0005\r\u0000\u0000" +
                "w\u00c7\u0001\u0000\u0000\u0000xy\u00051\u0000\u0000yz\u0005\u000c\u0000\u0000" +
                "z{\u0003\u000c\u0006\u0000{|\u0005\r\u0000\u0000|\u00c7\u0001\u0000\u0000" +
                "\u0000}~\u0005\u0010\u0000\u0000~\u007f\u0005\t\u0000\u0000\u007f\u0080" +
                "\u0003\u000c\u0006\u0000\u0080\u0081\u0005\n\u0000\u0000\u0081\u0085\u0005" +
                "\u0002\u0000\u0000\u0082\u0084\u0003\n\u0005\u0000\u0083\u0082\u0001\u0000" +
                "\u0000\u0000\u0084\u0087\u0001\u0000\u0000\u0000\u0085\u0083\u0001\u0000" +
                "\u0000\u0000\u0085\u0086\u0001\u0000\u0000\u0000\u0086\u0088\u0001\u0000" +
                "\u0000\u0000\u0087\u0085\u0001\u0000\u0000\u0000\u0088\u0089\u0005\u0003" +
                "\u0000\u0000\u0089\u00c7\u0001\u0000\u0000\u0000\u008a\u008b\u0005\u0010" +
                "\u0000\u0000\u008b\u008c\u0005\t\u0000\u0000\u008c\u008d\u0003\u000c\u0006" +
                "\u0000\u008d\u008e\u0005\n\u0000\u0000\u008e\u0092\u0005\u0002\u0000\u0000" +
                "\u008f\u0091\u0003\n\u0005\u0000\u0090\u008f\u0001\u0000\u0000\u0000\u0091" +
                "\u0094\u0001\u0000\u0000\u0000\u0092\u0090\u0001\u0000\u0000\u0000\u0092" +
                "\u0093\u0001\u0000\u0000\u0000\u0093\u0095\u0001\u0000\u0000\u0000\u0094" +
                "\u0092\u0001\u0000\u0000\u0000\u0095\u0096\u0005\u0003\u0000\u0000\u0096" +
                "\u0097\u0005\u0011\u0000\u0000\u0097\u009b\u0005\u0002\u0000\u0000\u0098" +
                "\u009a\u0003\n\u0005\u0000\u0099\u0098\u0001\u0000\u0000\u0000\u009a\u009d" +
                "\u0001\u0000\u0000\u0000\u009b\u0099\u0001\u0000\u0000\u0000\u009b\u009c" +
                "\u0001\u0000\u0000\u0000\u009c\u009e\u0001\u0000\u0000\u0000\u009d\u009b" +
                "\u0001\u0000\u0000\u0000\u009e\u009f\u0005\u0003\u0000\u0000\u009f\u00c7" +
                "\u0001\u0000\u0000\u0000\u00a0\u00a1\u0005\u0012\u0000\u0000\u00a1\u00a2" +
                "\u0005\t\u0000\u0000\u00a2\u00a3\u0003\u000c\u0006\u0000\u00a3\u00a4\u0005" +
                "\n\u0000\u0000\u00a4\u00a8\u0005\u0002\u0000\u0000\u00a5\u00a7\u0003\n" +
                "\u0005\u0000\u00a6\u00a5\u0001\u0000\u0000\u0000\u00a7\u00aa\u0001\u0000" +
                "\u0000\u0000\u00a8\u00a6\u0001\u0000\u0000\u0000\u00a8\u00a9\u0001\u0000" +
                "\u0000\u0000\u00a9\u00ab\u0001\u0000\u0000\u0000\u00aa\u00a8\u0001\u0000" +
                "\u0000\u0000\u00ab\u00ac\u0005\u0003\u0000\u0000\u00ac\u00c7\u0001\u0000" +
                "\u0000\u0000\u00ad\u00ae\u0005\u0013\u0000\u0000\u00ae\u00af\u0005\t\u0000" +
                "\u0000\u00af\u00b0\u00051\u0000\u0000\u00b0\u00b1\u0005\u0014\u0000\u0000" +
                "\u00b1\u00b2\u0003\u000c\u0006\u0000\u00b2\u00b3\u0005\n\u0000\u0000\u00b3" +
                "\u00b7\u0005\u0002\u0000\u0000\u00b4\u00b6\u0003\n\u0005\u0000\u00b5\u00b4" +
                "\u0001\u0000\u0000\u0000\u00b6\u00b9\u0001\u0000\u0000\u0000\u00b7\u00b5" +
                "\u0001\u0000\u0000\u0000\u00b7\u00b8\u0001\u0000\u0000\u0000\u00b8\u00ba" +
                "\u0001\u0000\u0000\u0000\u00b9\u00b7\u0001\u0000\u0000\u0000\u00ba\u00bb" +
                "\u0005\u0003\u0000\u0000\u00bb\u00c7\u0001\u0000\u0000\u0000\u00bc\u00bd" +
                "\u0005\u0015\u0000\u0000\u00bd\u00c7\u0005\r\u0000\u0000\u00be\u00bf\u0005" +
                "\u0016\u0000\u0000\u00bf\u00c7\u0005\r\u0000\u0000\u00c0\u00c1\u00051" +
                "\u0000\u0000\u00c1\u00c2\u0005\t\u0000\u0000\u00c2\u00c3\u0003\u0012\t" +
                "\u0000\u00c3\u00c4\u0005\n\u0000\u0000\u00c4\u00c5\u0005\r\u0000\u0000" +
                "\u00c5\u00c7\u0001\u0000\u0000\u0000\u00c6o\u0001\u0000\u0000\u0000\u00c6" +
                "r\u0001\u0000\u0000\u0000\u00c6x\u0001\u0000\u0000\u0000\u00c6}\u0001" +
                "\u0000\u0000\u0000\u00c6\u008a\u0001\u0000\u0000\u0000\u00c6\u00a0\u0001" +
                "\u0000\u0000\u0000\u00c6\u00ad\u0001\u0000\u0000\u0000\u00c6\u00bc\u0001" +
                "\u0000\u0000\u0000\u00c6\u00be\u0001\u0000\u0000\u0000\u00c6\u00c0\u0001" +
                "\u0000\u0000\u0000\u00c7\u000b\u0001\u0000\u0000\u0000\u00c8\u00c9\u0006" +
                "\u0006\uffff\uffff\u0000\u00c9\u00dc\u0003\u0016\u000b\u0000\u00ca\u00dc" +
                "\u00050\u0000\u0000\u00cb\u00dc\u0005/\u0000\u0000\u00cc\u00dc\u00051" +
                "\u0000\u0000\u00cd\u00ce\u0005\t\u0000\u0000\u00ce\u00cf\u0003\u000c\u0006" +
                "\u0000\u00cf\u00d0\u0005\n\u0000\u0000\u00d0\u00dc\u0001\u0000\u0000\u0000" +
                "\u00d1\u00d2\u0007\u0000\u0000\u0000\u00d2\u00dc\u0003\u000c\u0006\t\u00d3" +
                "\u00d4\u0005\'\u0000\u0000\u00d4\u00d5\u0003\u0012\t\u0000\u00d5\u00d6" +
                "\u0005(\u0000\u0000\u00d6\u00dc\u0001\u0000\u0000\u0000\u00d7\u00d8\u0005" +
                "\u0002\u0000\u0000\u00d8\u00d9\u0003\u000e\u0007\u0000\u00d9\u00da\u0005" +
                "\u0003\u0000\u0000\u00da\u00dc\u0001\u0000\u0000\u0000\u00db\u00c8\u0001" +
                "\u0000\u0000\u0000\u00db\u00ca\u0001\u0000\u0000\u0000\u00db\u00cb\u0001" +
                "\u0000\u0000\u0000\u00db\u00cc\u0001\u0000\u0000\u0000\u00db\u00cd\u0001" +
                "\u0000\u0000\u0000\u00db\u00d1\u0001\u0000\u0000\u0000\u00db\u00d3\u0001" +
                "\u0000\u0000\u0000\u00db\u00d7\u0001\u0000\u0000\u0000\u00dc\u0100\u0001" +
                "\u0000\u0000\u0000\u00dd\u00de\n\r\u0000\u0000\u00de\u00df\u0005.\u0000" +
                "\u0000\u00df\u00ff\u0003\u000c\u0006\u000e\u00e0\u00e1\n\u000c\u0000\u0000\u00e1" +
                "\u00e2\u0007\u0001\u0000\u0000\u00e2\u00ff\u0003\u000c\u0006\r\u00e3\u00e4" +
                "\n\u000b\u0000\u0000\u00e4\u00e5\u0007\u0002\u0000\u0000\u00e5\u00ff\u0003" +
                "\u000c\u0006\u000c\u00e6\u00e7\n\n\u0000\u0000\u00e7\u00e8\u0005\u001b\u0000\u0000" +
                "\u00e8\u00ff\u0003\u000c\u0006\u000b\u00e9\u00ea\n\b\u0000\u0000\u00ea\u00eb" +
                "\u00053\u0000\u0000\u00eb\u00ff\u0003\u000c\u0006\t\u00ec\u00ed\n\u0007\u0000" +
                "\u0000\u00ed\u00ee\u0007\u0003\u0000\u0000\u00ee\u00ff\u0003\u000c\u0006\b" +
                "\u00ef\u00f0\n\u0006\u0000\u0000\u00f0\u00f1\u0007\u0004\u0000\u0000\u00f1" +
                "\u00ff\u0003\u000c\u0006\u0007\u00f2\u00f3\n\u0005\u0000\u0000\u00f3\u00f4" +
                "\u0005#\u0000\u0000\u00f4\u00f5\u0003\u000c\u0006\u0000\u00f5\u00f6\u0005" +
                "$\u0000\u0000\u00f6\u00f7\u0003\u000c\u0006\u0006\u00f7\u00ff\u0001\u0000" +
                "\u0000\u0000\u00f8\u00f9\n\u0004\u0000\u0000\u00f9\u00fa\u0005%\u0000" +
                "\u0000\u00fa\u00ff\u0003\u000c\u0006\u0005\u00fb\u00fc\n\u0003\u0000\u0000" +
                "\u00fc\u00fd\u0005&\u0000\u0000\u00fd\u00ff\u0003\u000c\u0006\u0004\u00fe" +
                "\u00dd\u0001\u0000\u0000\u0000\u00fe\u00e0\u0001\u0000\u0000\u0000\u00fe" +
                "\u00e3\u0001\u0000\u0000\u0000\u00fe\u00e6\u0001\u0000\u0000\u0000\u00fe" +
                "\u00e9\u0001\u0000\u0000\u0000\u00fe\u00ec\u0001\u0000\u0000\u0000\u00fe" +
                "\u00ef\u0001\u0000\u0000\u0000\u00fe\u00f2\u0001\u0000\u0000\u0000\u00fe" +
                "\u00f8\u0001\u0000\u0000\u0000\u00fe\u00fb\u0001\u0000\u0000\u0000\u00ff" +
                "\u0102\u0001\u0000\u0000\u0000\u0100\u00fe\u0001\u0000\u0000\u0000\u0100" +
                "\u0101\u0001\u0000\u0000\u0000\u0101\r\u0001\u0000\u0000\u0000\u0102\u0100" +
                "\u0001\u0000\u0000\u0000\u0103\u0106\u0001\u0000\u0000\u0000\u0104\u0106" +
                "\u0003\u0010\b\u0000\u0105\u0103\u0001\u0000\u0000\u0000\u0105\u0104\u0001" +
                "\u0000\u0000\u0000\u0106\u000f\u0001\u0000\u0000\u0000\u0107\u0108\u0003" +
                "\u000c\u0006\u0000\u0108\u0109\u0005\u000c\u0000\u0000\u0109\u010a\u0003\u000c\u0006" +
                "\u0000\u010a\u0112\u0001\u0000\u0000\u0000\u010b\u010c\u0003\u000c\u0006\u0000" +
                "\u010c\u010d\u0005\u000c\u0000\u0000\u010d\u010e\u0003\u000c\u0006\u0000\u010e" +
                "\u010f\u0005\u000e\u0000\u0000\u010f\u0110\u0003\u0010\b\u0000\u0110\u0112" +
                "\u0001\u0000\u0000\u0000\u0111\u0107\u0001\u0000\u0000\u0000\u0111\u010b" +
                "\u0001\u0000\u0000\u0000\u0112\u0011\u0001\u0000\u0000\u0000\u0113\u0116" +
                "\u0001\u0000\u0000\u0000\u0114\u0116\u0003\u0014\n\u0000\u0115\u0113\u0001" +
                "\u0000\u0000\u0000\u0115\u0114\u0001\u0000\u0000\u0000\u0116\u0013\u0001" +
                "\u0000\u0000\u0000\u0117\u011d\u0003\u000c\u0006\u0000\u0118\u0119\u0003\u000c" +
                "\u0006\u0000\u0119\u011a\u0005\u000e\u0000\u0000\u011a\u011b\u0003\u0014" +
                "\n\u0000\u011b\u011d\u0001\u0000\u0000\u0000\u011c\u0117\u0001\u0000\u0000" +
                "\u0000\u011c\u0118\u0001\u0000\u0000\u0000\u011d\u0015\u0001\u0000\u0000" +
                "\u0000\u011e\u0123\u0005)\u0000\u0000\u011f\u0120\u0005*\u0000\u0000\u0120" +
                "\u0121\u00052\u0000\u0000\u0121\u0123\u0005*\u0000\u0000\u0122\u011e\u0001" +
                "\u0000\u0000\u0000\u0122\u011f\u0001\u0000\u0000\u0000\u0123\u0017\u0001" +
                "\u0000\u0000\u0000\u0018!+5?IMXcgm\u0085\u0092\u009b\u00a8\u00b7\u00c6" +
                "\u00db\u00fe\u0100\u0105\u0111\u0115\u011c\u0122"
        val _ATN = ATNDeserializer().deserialize(_serializedATN.toCharArray())

        init {
            _decisionToDFA = arrayOfNulls(_ATN.numberOfDecisions)
            for (i in 0 until _ATN.numberOfDecisions) {
                _decisionToDFA[i] = DFA(_ATN.getDecisionState(i), i)
            }
        }
    }
}