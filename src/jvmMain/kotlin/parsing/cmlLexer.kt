package parsing

import org.antlr.v4.runtime.*
import org.antlr.v4.runtime.atn.ATN
import org.antlr.v4.runtime.atn.ATNDeserializer
import org.antlr.v4.runtime.atn.LexerATNSimulator
import org.antlr.v4.runtime.atn.PredictionContextCache
import org.antlr.v4.runtime.dfa.DFA

// Generated from java-escape by ANTLR 4.11.1
@Suppress("unused")
class cmlLexer(input: CharStream?) : Lexer(input) {
    @Deprecated("")
    override fun getTokenNames(): Array<String?> {
        return Companion.tokenNames
    }

    override fun getVocabulary(): Vocabulary {
        return VOCABULARY
    }

    override fun getGrammarFileName(): String {
        return "cml.g4"
    }

    override fun getRuleNames(): Array<String> {
        return Companion.ruleNames
    }

    override fun getSerializedATN(): String {
        return _serializedATN
    }

    override fun getChannelNames(): Array<String> {
        return Companion.channelNames
    }

    override fun getModeNames(): Array<String> {
        return Companion.modeNames
    }

    override fun getATN(): ATN {
        return _ATN
    }

    init {
        _interp = LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache)
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
        var channelNames = arrayOf(
            "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
        )
        var modeNames = arrayOf(
            "DEFAULT_MODE"
        )

        private fun makeRuleNames(): Array<String> {
            return arrayOf(
                "T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8",
                "T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16",
                "T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24",
                "T__25", "T__26", "T__27", "T__28", "T__29", "T__30", "T__31", "T__32",
                "T__33", "T__34", "T__35", "T__36", "T__37", "T__38", "T__39", "T__40",
                "T__41", "NEWLINE", "WS", "COMMENT_CHARS", "DICE", "BOOL", "INT", "IDENT",
                "STRING_CHARS", "COMPARISON_OP"
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

        const val _serializedATN = "\u0004\u00003\u0141\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001" +
                "\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004" +
                "\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007" +
                "\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b" +
                "\u0007\u000b\u0002\u000c\u0007\u000c\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002" +
                "\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002" +
                "\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002" +
                "\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002" +
                "\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002" +
                "\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002" +
                "\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007" +
                "!\u0002\"\u0007\"\u0002#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007" +
                "&\u0002\'\u0007\'\u0002(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007" +
                "+\u0002,\u0007,\u0002-\u0007-\u0002.\u0007.\u0002/\u0007/\u00020\u0007" +
                "0\u00021\u00071\u00022\u00072\u0001\u0000\u0001\u0000\u0001\u0000\u0001" +
                "\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001" +
                "\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001" +
                "\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001" +
                "\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001" +
                "\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001" +
                "\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001" +
                "\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001" +
                "\n\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000c\u0001\u000c\u0001" +
                "\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f" +
                "\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010" +
                "\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011" +
                "\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013" +
                "\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014" +
                "\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015" +
                "\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0017" +
                "\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u001a" +
                "\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0001\u001d" +
                "\u0001\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001f" +
                "\u0001\u001f\u0001 \u0001 \u0001!\u0001!\u0001\"\u0001\"\u0001#\u0001" +
                "#\u0001$\u0001$\u0001$\u0001$\u0001%\u0001%\u0001%\u0001%\u0001%\u0001" +
                "%\u0001&\u0001&\u0001\'\u0001\'\u0001(\u0001(\u0001(\u0001)\u0001)\u0001" +
                "*\u0004*\u00f9\b*\u000b*\u000c*\u00fa\u0001*\u0001*\u0001+\u0004+\u0100\b" +
                "+\u000b+\u000c+\u0101\u0001+\u0001+\u0001,\u0001,\u0001,\u0001,\u0005,\u010a" +
                "\b,\n,\u000c,\u010d\t,\u0001,\u0001,\u0001,\u0001,\u0001-\u0001-\u0001.\u0001" +
                ".\u0001.\u0001.\u0001.\u0001.\u0001.\u0001.\u0001.\u0003.\u011e\b.\u0001" +
                "/\u0001/\u0005/\u0122\b/\n/\u000c/\u0125\t/\u0001/\u0003/\u0128\b/\u00010" +
                "\u00010\u00050\u012c\b0\n0\u000c0\u012f\t0\u00011\u00041\u0132\b1\u000b1\u000c" +
                "1\u0133\u00012\u00012\u00012\u00012\u00012\u00012\u00012\u00012\u0001" +
                "2\u00012\u00032\u0140\b2\u0000\u00003\u0001\u0001\u0003\u0002\u0005\u0003" +
                "\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015" +
                "\u000b\u0017\u000c\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012" +
                "%\u0013\'\u0014)\u0015+\u0016-\u0017/\u00181\u00193\u001a5\u001b7\u001c" +
                "9\u001d;\u001e=\u001f? A!C\"E#G\$I%K&M\'O(Q)S*U+W,Y-[.]/_0a1c2e3\u0001" +
                "\u0000\b\u0002\u0000\n\n\r\r\u0002\u0000\t\t  \u0002\u0000DDdd\u0001\u0000" +
                "19\u0001\u000009\u0003\u0000AZ__az\u0004\u000009AZ__az\u0003\u0000\n\n" +
                "\r\r\"\"\u014d\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000" +
                "\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000" +
                "\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000" +
                "\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000" +
                "\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000" +
                "\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000" +
                "\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000" +
                "\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000" +
                "\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0000%" +
                "\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000)\u0001" +
                "\u0000\u0000\u0000\u0000+\u0001\u0000\u0000\u0000\u0000-\u0001\u0000\u0000" +
                "\u0000\u0000/\u0001\u0000\u0000\u0000\u00001\u0001\u0000\u0000\u0000\u0000" +
                "3\u0001\u0000\u0000\u0000\u00005\u0001\u0000\u0000\u0000\u00007\u0001" +
                "\u0000\u0000\u0000\u00009\u0001\u0000\u0000\u0000\u0000;\u0001\u0000\u0000" +
                "\u0000\u0000=\u0001\u0000\u0000\u0000\u0000?\u0001\u0000\u0000\u0000\u0000" +
                "A\u0001\u0000\u0000\u0000\u0000C\u0001\u0000\u0000\u0000\u0000E\u0001" +
                "\u0000\u0000\u0000\u0000G\u0001\u0000\u0000\u0000\u0000I\u0001\u0000\u0000" +
                "\u0000\u0000K\u0001\u0000\u0000\u0000\u0000M\u0001\u0000\u0000\u0000\u0000" +
                "O\u0001\u0000\u0000\u0000\u0000Q\u0001\u0000\u0000\u0000\u0000S\u0001" +
                "\u0000\u0000\u0000\u0000U\u0001\u0000\u0000\u0000\u0000W\u0001\u0000\u0000" +
                "\u0000\u0000Y\u0001\u0000\u0000\u0000\u0000[\u0001\u0000\u0000\u0000\u0000" +
                "]\u0001\u0000\u0000\u0000\u0000_\u0001\u0000\u0000\u0000\u0000a\u0001" +
                "\u0000\u0000\u0000\u0000c\u0001\u0000\u0000\u0000\u0000e\u0001\u0000\u0000" +
                "\u0000\u0001g\u0001\u0000\u0000\u0000\u0003l\u0001\u0000\u0000\u0000\u0005" +
                "n\u0001\u0000\u0000\u0000\u0007p\u0001\u0000\u0000\u0000\tv\u0001\u0000" +
                "\u0000\u0000\u000b\u0081\u0001\u0000\u0000\u0000\r\u0086\u0001\u0000\u0000" +
                "\u0000\u000f\u008c\u0001\u0000\u0000\u0000\u0011\u0090\u0001\u0000\u0000" +
                "\u0000\u0013\u0092\u0001\u0000\u0000\u0000\u0015\u0094\u0001\u0000\u0000" +
                "\u0000\u0017\u009a\u0001\u0000\u0000\u0000\u0019\u009c\u0001\u0000\u0000" +
                "\u0000\u001b\u009e\u0001\u0000\u0000\u0000\u001d\u00a0\u0001\u0000\u0000" +
                "\u0000\u001f\u00a4\u0001\u0000\u0000\u0000!\u00a7\u0001\u0000\u0000\u0000" +
                "#\u00ac\u0001\u0000\u0000\u0000%\u00b2\u0001\u0000\u0000\u0000\'\u00b6" +
                "\u0001\u0000\u0000\u0000)\u00b9\u0001\u0000\u0000\u0000+\u00bf\u0001\u0000" +
                "\u0000\u0000-\u00c6\u0001\u0000\u0000\u0000/\u00c8\u0001\u0000\u0000\u0000" +
                "1\u00ca\u0001\u0000\u0000\u00003\u00cc\u0001\u0000\u0000\u00005\u00ce" +
                "\u0001\u0000\u0000\u00007\u00d0\u0001\u0000\u0000\u00009\u00d2\u0001\u0000" +
                "\u0000\u0000;\u00d4\u0001\u0000\u0000\u0000=\u00d7\u0001\u0000\u0000\u0000" +
                "?\u00da\u0001\u0000\u0000\u0000A\u00dc\u0001\u0000\u0000\u0000C\u00de" +
                "\u0001\u0000\u0000\u0000E\u00e0\u0001\u0000\u0000\u0000G\u00e2\u0001\u0000" +
                "\u0000\u0000I\u00e4\u0001\u0000\u0000\u0000K\u00e8\u0001\u0000\u0000\u0000" +
                "M\u00ee\u0001\u0000\u0000\u0000O\u00f0\u0001\u0000\u0000\u0000Q\u00f2" +
                "\u0001\u0000\u0000\u0000S\u00f5\u0001\u0000\u0000\u0000U\u00f8\u0001\u0000" +
                "\u0000\u0000W\u00ff\u0001\u0000\u0000\u0000Y\u0105\u0001\u0000\u0000\u0000" +
                "[\u0112\u0001\u0000\u0000\u0000]\u011d\u0001\u0000\u0000\u0000_\u0127" +
                "\u0001\u0000\u0000\u0000a\u0129\u0001\u0000\u0000\u0000c\u0131\u0001\u0000" +
                "\u0000\u0000e\u013f\u0001\u0000\u0000\u0000gh\u0005r\u0000\u0000hi\u0005" +
                "a\u0000\u0000ij\u0005c\u0000\u0000jk\u0005e\u0000\u0000k\u0002\u0001\u0000" +
                "\u0000\u0000lm\u0005{\u0000\u0000m\u0004\u0001\u0000\u0000\u0000no\u0005" +
                "}\u0000\u0000o\u0006\u0001\u0000\u0000\u0000pq\u0005c\u0000\u0000qr\u0005" +
                "l\u0000\u0000rs\u0005a\u0000\u0000st\u0005s\u0000\u0000tu\u0005s\u0000" +
                "\u0000u\b\u0001\u0000\u0000\u0000vw\u0005b\u0000\u0000wx\u0005a\u0000" +
                "\u0000xy\u0005c\u0000\u0000yz\u0005k\u0000\u0000z{\u0005g\u0000\u0000" +
                "{|\u0005r\u0000\u0000|}\u0005o\u0000\u0000}~\u0005u\u0000\u0000~\u007f" +
                "\u0005n\u0000\u0000\u007f\u0080\u0005d\u0000\u0000\u0080\n\u0001\u0000" +
                "\u0000\u0000\u0081\u0082\u0005i\u0000\u0000\u0082\u0083\u0005t\u0000\u0000" +
                "\u0083\u0084\u0005e\u0000\u0000\u0084\u0085\u0005m\u0000\u0000\u0085\u000c" +
                "\u0001\u0000\u0000\u0000\u0086\u0087\u0005s\u0000\u0000\u0087\u0088\u0005" +
                "p\u0000\u0000\u0088\u0089\u0005e\u0000\u0000\u0089\u008a\u0005l\u0000" +
                "\u0000\u008a\u008b\u0005l\u0000\u0000\u008b\u000e\u0001\u0000\u0000\u0000" +
                "\u008c\u008d\u0005f\u0000\u0000\u008d\u008e\u0005u\u0000\u0000\u008e\u008f" +
                "\u0005n\u0000\u0000\u008f\u0010\u0001\u0000\u0000\u0000\u0090\u0091\u0005" +
                "(\u0000\u0000\u0091\u0012\u0001\u0000\u0000\u0000\u0092\u0093\u0005)\u0000" +
                "\u0000\u0093\u0014\u0001\u0000\u0000\u0000\u0094\u0095\u0005f\u0000\u0000" +
                "\u0095\u0096\u0005i\u0000\u0000\u0096\u0097\u0005e\u0000\u0000\u0097\u0098" +
                "\u0005l\u0000\u0000\u0098\u0099\u0005d\u0000\u0000\u0099\u0016\u0001\u0000" +
                "\u0000\u0000\u009a\u009b\u0005=\u0000\u0000\u009b\u0018\u0001\u0000\u0000" +
                "\u0000\u009c\u009d\u0005;\u0000\u0000\u009d\u001a\u0001\u0000\u0000\u0000" +
                "\u009e\u009f\u0005,\u0000\u0000\u009f\u001c\u0001\u0000\u0000\u0000\u00a0" +
                "\u00a1\u0005v\u0000\u0000\u00a1\u00a2\u0005a\u0000\u0000\u00a2\u00a3\u0005" +
                "r\u0000\u0000\u00a3\u001e\u0001\u0000\u0000\u0000\u00a4\u00a5\u0005i\u0000" +
                "\u0000\u00a5\u00a6\u0005f\u0000\u0000\u00a6 \u0001\u0000\u0000\u0000\u00a7" +
                "\u00a8\u0005e\u0000\u0000\u00a8\u00a9\u0005l\u0000\u0000\u00a9\u00aa\u0005" +
                "s\u0000\u0000\u00aa\u00ab\u0005e\u0000\u0000\u00ab\"\u0001\u0000\u0000" +
                "\u0000\u00ac\u00ad\u0005w\u0000\u0000\u00ad\u00ae\u0005h\u0000\u0000\u00ae" +
                "\u00af\u0005i\u0000\u0000\u00af\u00b0\u0005l\u0000\u0000\u00b0\u00b1\u0005" +
                "e\u0000\u0000\u00b1$\u0001\u0000\u0000\u0000\u00b2\u00b3\u0005f\u0000" +
                "\u0000\u00b3\u00b4\u0005o\u0000\u0000\u00b4\u00b5\u0005r\u0000\u0000\u00b5" +
                "&\u0001\u0000\u0000\u0000\u00b6\u00b7\u0005i\u0000\u0000\u00b7\u00b8\u0005" +
                "n\u0000\u0000\u00b8(\u0001\u0000\u0000\u0000\u00b9\u00ba\u0005b\u0000" +
                "\u0000\u00ba\u00bb\u0005r\u0000\u0000\u00bb\u00bc\u0005e\u0000\u0000\u00bc" +
                "\u00bd\u0005a\u0000\u0000\u00bd\u00be\u0005k\u0000\u0000\u00be*\u0001" +
                "\u0000\u0000\u0000\u00bf\u00c0\u0005r\u0000\u0000\u00c0\u00c1\u0005e\u0000" +
                "\u0000\u00c1\u00c2\u0005t\u0000\u0000\u00c2\u00c3\u0005u\u0000\u0000\u00c3" +
                "\u00c4\u0005r\u0000\u0000\u00c4\u00c5\u0005n\u0000\u0000\u00c5,\u0001" +
                "\u0000\u0000\u0000\u00c6\u00c7\u0005*\u0000\u0000\u00c7.\u0001\u0000\u0000" +
                "\u0000\u00c8\u00c9\u0005/\u0000\u0000\u00c90\u0001\u0000\u0000\u0000\u00ca" +
                "\u00cb\u0005+\u0000\u0000\u00cb2\u0001\u0000\u0000\u0000\u00cc\u00cd\u0005" +
                "-\u0000\u0000\u00cd4\u0001\u0000\u0000\u0000\u00ce\u00cf\u0005%\u0000" +
                "\u0000\u00cf6\u0001\u0000\u0000\u0000\u00d0\u00d1\u0005~\u0000\u0000\u00d1" +
                "8\u0001\u0000\u0000\u0000\u00d2\u00d3\u0005!\u0000\u0000\u00d3:\u0001" +
                "\u0000\u0000\u0000\u00d4\u00d5\u0005&\u0000\u0000\u00d5\u00d6\u0005&\u0000" +
                "\u0000\u00d6<\u0001\u0000\u0000\u0000\u00d7\u00d8\u0005|\u0000\u0000\u00d8" +
                "\u00d9\u0005|\u0000\u0000\u00d9>\u0001\u0000\u0000\u0000\u00da\u00db\u0005" +
                "^\u0000\u0000\u00db@\u0001\u0000\u0000\u0000\u00dc\u00dd\u0005&\u0000" +
                "\u0000\u00ddB\u0001\u0000\u0000\u0000\u00de\u00df\u0005|\u0000\u0000\u00df" +
                "D\u0001\u0000\u0000\u0000\u00e0\u00e1\u0005?\u0000\u0000\u00e1F\u0001" +
                "\u0000\u0000\u0000\u00e2\u00e3\u0005:\u0000\u0000\u00e3H\u0001\u0000\u0000" +
                "\u0000\u00e4\u00e5\u0005.\u0000\u0000\u00e5\u00e6\u0005.\u0000\u0000\u00e6" +
                "\u00e7\u0005.\u0000\u0000\u00e7J\u0001\u0000\u0000\u0000\u00e8\u00e9\u0005" +
                "u\u0000\u0000\u00e9\u00ea\u0005n\u0000\u0000\u00ea\u00eb\u0005t\u0000" +
                "\u0000\u00eb\u00ec\u0005i\u0000\u0000\u00ec\u00ed\u0005l\u0000\u0000\u00ed" +
                "L\u0001\u0000\u0000\u0000\u00ee\u00ef\u0005[\u0000\u0000\u00efN\u0001" +
                "\u0000\u0000\u0000\u00f0\u00f1\u0005]\u0000\u0000\u00f1P\u0001\u0000\u0000" +
                "\u0000\u00f2\u00f3\u0005\"\u0000\u0000\u00f3\u00f4\u0005\"\u0000\u0000" +
                "\u00f4R\u0001\u0000\u0000\u0000\u00f5\u00f6\u0005\"\u0000\u0000\u00f6" +
                "T\u0001\u0000\u0000\u0000\u00f7\u00f9\u0007\u0000\u0000\u0000\u00f8\u00f7" +
                "\u0001\u0000\u0000\u0000\u00f9\u00fa\u0001\u0000\u0000\u0000\u00fa\u00f8" +
                "\u0001\u0000\u0000\u0000\u00fa\u00fb\u0001\u0000\u0000\u0000\u00fb\u00fc" +
                "\u0001\u0000\u0000\u0000\u00fc\u00fd\u0006*\u0000\u0000\u00fdV\u0001\u0000" +
                "\u0000\u0000\u00fe\u0100\u0007\u0001\u0000\u0000\u00ff\u00fe\u0001\u0000" +
                "\u0000\u0000\u0100\u0101\u0001\u0000\u0000\u0000\u0101\u00ff\u0001\u0000" +
                "\u0000\u0000\u0101\u0102\u0001\u0000\u0000\u0000\u0102\u0103\u0001\u0000" +
                "\u0000\u0000\u0103\u0104\u0006+\u0000\u0000\u0104X\u0001\u0000\u0000\u0000" +
                "\u0105\u0106\u0005/\u0000\u0000\u0106\u0107\u0005/\u0000\u0000\u0107\u010b" +
                "\u0001\u0000\u0000\u0000\u0108\u010a\b\u0000\u0000\u0000\u0109\u0108\u0001" +
                "\u0000\u0000\u0000\u010a\u010d\u0001\u0000\u0000\u0000\u010b\u0109\u0001" +
                "\u0000\u0000\u0000\u010b\u010c\u0001\u0000\u0000\u0000\u010c\u010e\u0001" +
                "\u0000\u0000\u0000\u010d\u010b\u0001\u0000\u0000\u0000\u010e\u010f\u0007" +
                "\u0000\u0000\u0000\u010f\u0110\u0001\u0000\u0000\u0000\u0110\u0111\u0006" +
                ",\u0000\u0000\u0111Z\u0001\u0000\u0000\u0000\u0112\u0113\u0007\u0002\u0000" +
                "\u0000\u0113\\\u0001\u0000\u0000\u0000\u0114\u0115\u0005t\u0000\u0000" +
                "\u0115\u0116\u0005r\u0000\u0000\u0116\u0117\u0005u\u0000\u0000\u0117\u011e" +
                "\u0005e\u0000\u0000\u0118\u0119\u0005f\u0000\u0000\u0119\u011a\u0005a" +
                "\u0000\u0000\u011a\u011b\u0005l\u0000\u0000\u011b\u011c\u0005s\u0000\u0000" +
                "\u011c\u011e\u0005e\u0000\u0000\u011d\u0114\u0001\u0000\u0000\u0000\u011d" +
                "\u0118\u0001\u0000\u0000\u0000\u011e^\u0001\u0000\u0000\u0000\u011f\u0123" +
                "\u0007\u0003\u0000\u0000\u0120\u0122\u0007\u0004\u0000\u0000\u0121\u0120" +
                "\u0001\u0000\u0000\u0000\u0122\u0125\u0001\u0000\u0000\u0000\u0123\u0121" +
                "\u0001\u0000\u0000\u0000\u0123\u0124\u0001\u0000\u0000\u0000\u0124\u0128" +
                "\u0001\u0000\u0000\u0000\u0125\u0123\u0001\u0000\u0000\u0000\u0126\u0128" +
                "\u00050\u0000\u0000\u0127\u011f\u0001\u0000\u0000\u0000\u0127\u0126\u0001" +
                "\u0000\u0000\u0000\u0128`\u0001\u0000\u0000\u0000\u0129\u012d\u0007\u0005" +
                "\u0000\u0000\u012a\u012c\u0007\u0006\u0000\u0000\u012b\u012a\u0001\u0000" +
                "\u0000\u0000\u012c\u012f\u0001\u0000\u0000\u0000\u012d\u012b\u0001\u0000" +
                "\u0000\u0000\u012d\u012e\u0001\u0000\u0000\u0000\u012eb\u0001\u0000\u0000" +
                "\u0000\u012f\u012d\u0001\u0000\u0000\u0000\u0130\u0132\b\u0007\u0000\u0000" +
                "\u0131\u0130\u0001\u0000\u0000\u0000\u0132\u0133\u0001\u0000\u0000\u0000" +
                "\u0133\u0131\u0001\u0000\u0000\u0000\u0133\u0134\u0001\u0000\u0000\u0000" +
                "\u0134d\u0001\u0000\u0000\u0000\u0135\u0136\u0005=\u0000\u0000\u0136\u0140" +
                "\u0005=\u0000\u0000\u0137\u0138\u0005!\u0000\u0000\u0138\u0140\u0005=" +
                "\u0000\u0000\u0139\u0140\u0005<\u0000\u0000\u013a\u013b\u0005<\u0000\u0000" +
                "\u013b\u0140\u0005=\u0000\u0000\u013c\u0140\u0005>\u0000\u0000\u013d\u013e" +
                "\u0005>\u0000\u0000\u013e\u0140\u0005=\u0000\u0000\u013f\u0135\u0001\u0000" +
                "\u0000\u0000\u013f\u0137\u0001\u0000\u0000\u0000\u013f\u0139\u0001\u0000" +
                "\u0000\u0000\u013f\u013a\u0001\u0000\u0000\u0000\u013f\u013c\u0001\u0000" +
                "\u0000\u0000\u013f\u013d\u0001\u0000\u0000\u0000\u0140f\u0001\u0000\u0000" +
                "\u0000\n\u0000\u00fa\u0101\u010b\u011d\u0123\u0127\u012d\u0133\u013f\u0001" +
                "\u0006\u0000\u0000"
        val _ATN = ATNDeserializer().deserialize(_serializedATN.toCharArray())

        init {
            _decisionToDFA = arrayOfNulls(_ATN.numberOfDecisions)
            for (i in 0 until _ATN.numberOfDecisions) {
                _decisionToDFA[i] = DFA(_ATN.getDecisionState(i), i)
            }
        }
    }
}