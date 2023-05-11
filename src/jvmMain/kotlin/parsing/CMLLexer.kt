package parsing

import org.antlr.v4.runtime.*
import org.antlr.v4.runtime.atn.ATN
import org.antlr.v4.runtime.atn.ATNDeserializer
import org.antlr.v4.runtime.atn.LexerATNSimulator
import org.antlr.v4.runtime.atn.PredictionContextCache
import org.antlr.v4.runtime.dfa.DFA

// Generated from java-escape by ANTLR 4.11.1
@Suppress("unused")
class CMLLexer(input: CharStream?) : Lexer(input) {
    @Deprecated("")
    override fun getTokenNames(): Array<String?> {
        return Companion.tokenNames
    }

    override fun getVocabulary(): Vocabulary {
        return VOCABULARY
    }

    override fun getGrammarFileName(): String {
        return "CML.g4"
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
        const val DOT = 13
        const val ELIPSIS = 14
        const val MD_OP = 15
        const val AS_OP = 16
        const val MOD_OP = 17
        const val UN_OP = 18
        const val LOGIC_OP = 19
        const val BITWISE_OP = 20
        const val DICE = 21
        const val BOOL = 22
        const val COMPARISON_OP = 23
        const val IF = 24
        const val IN = 25
        const val FOR = 26
        const val FUN = 27
        const val VAR = 28
        const val DATA = 29
        const val ELSE = 30
        const val BREAK = 31
        const val FIELD = 32
        const val UNTIL = 33
        const val WHILE = 34
        const val RETURN = 35
        const val INT = 36
        const val IDENT = 37
        const val STRING_LIT = 38
        const val NEWLINE = 39
        const val WS = 40
        const val COMMENT_CHARS = 41
        var channelNames = arrayOf(
            "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
        )
        var modeNames = arrayOf(
            "DEFAULT_MODE"
        )

        private fun makeRuleNames(): Array<String> {
            return arrayOf(
                "B_O", "B_C", "P_O", "P_C", "BR_O", "BR_C", "SEMI", "ASSIGN", "COMMA",
                "QMARK", "COLON", "STRING_DELIM", "DOT", "ELIPSIS", "MD_OP", "AS_OP",
                "MOD_OP", "UN_OP", "LOGIC_OP", "BITWISE_OP", "DICE", "BOOL", "COMPARISON_OP",
                "IF", "IN", "FOR", "FUN", "VAR", "DATA", "ELSE", "BREAK", "FIELD", "UNTIL",
                "WHILE", "RETURN", "INT", "IDENT", "STRING_LIT", "NEWLINE", "WS", "COMMENT_CHARS"
            )
        }

        val ruleNames = makeRuleNames()
        private fun makeLiteralNames(): Array<String?> {
            return arrayOf(
                null, "'{'", "'}'", "'('", "')'", "'['", "']'", "';'", "'='", "','",
                "'?'", "':'", "'\"'", "'.'", "'...'", null, null, "'%'", null, null,
                null, null, null, null, "'if'", "'in'", "'for'", "'fun'", "'var'", "'data'",
                "'else'", "'break'", "'field'", "'until'", "'while'", "'return'"
            )
        }

        private val _LITERAL_NAMES = makeLiteralNames()
        private fun makeSymbolicNames(): Array<String?> {
            return arrayOf(
                null, "B_O", "B_C", "P_O", "P_C", "BR_O", "BR_C", "SEMI", "ASSIGN", "COMMA",
                "QMARK", "COLON", "STRING_DELIM", "DOT", "ELIPSIS", "MD_OP", "AS_OP",
                "MOD_OP", "UN_OP", "LOGIC_OP", "BITWISE_OP", "DICE", "BOOL", "COMPARISON_OP",
                "IF", "IN", "FOR", "FUN", "VAR", "DATA", "ELSE", "BREAK", "FIELD", "UNTIL",
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

        const val _serializedATN = "\u0004\u0000)\u010a\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001" +
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
                "&\u0002\'\u0007\'\u0002(\u0007(\u0001\u0000\u0001\u0000\u0001\u0001\u0001" +
                "\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001" +
                "\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001" +
                "\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001" +
                "\u000b\u0001\u000c\u0001\u000c\u0001\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001" +
                "\u000e\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0011\u0001" +
                "\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0003\u0012~\b" +
                "\u0012\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0015\u0001" +
                "\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001" +
                "\u0015\u0001\u0015\u0003\u0015\u008d\b\u0015\u0001\u0016\u0001\u0016\u0001" +
                "\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001" +
                "\u0016\u0001\u0016\u0003\u0016\u0099\b\u0016\u0001\u0017\u0001\u0017\u0001" +
                "\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001" +
                "\u0019\u0001\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001" +
                "\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0001" +
                "\u001c\u0001\u001c\u0001\u001c\u0001\u001d\u0001\u001d\u0001\u001d\u0001" +
                "\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001" +
                "\u001e\u0001\u001e\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001" +
                "\u001f\u0001\u001f\u0001 \u0001 \u0001 \u0001 \u0001 \u0001 \u0001!\u0001" +
                "!\u0001!\u0001!\u0001!\u0001!\u0001\"\u0001\"\u0001\"\u0001\"\u0001\"" +
                "\u0001\"\u0001\"\u0001#\u0001#\u0005#\u00d8\b#\n#\u000c#\u00db\t#\u0001#\u0003" +
                "#\u00de\b#\u0001$\u0001$\u0005$\u00e2\b$\n$\u000c$\u00e5\t$\u0001%\u0001%" +
                "\u0005%\u00e9\b%\n%\u000c%\u00ec\t%\u0001%\u0001%\u0001&\u0004&\u00f1\b&\u000b" +
                "&\u000c&\u00f2\u0001&\u0001&\u0001\'\u0004\'\u00f8\b\'\u000b\'\u000c\'\u00f9\u0001" +
                "\'\u0001\'\u0001(\u0001(\u0001(\u0001(\u0005(\u0102\b(\n(\u000c(\u0105\t(" +
                "\u0001(\u0001(\u0001(\u0001(\u0000\u0000)\u0001\u0001\u0003\u0002\u0005" +
                "\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n" +
                "\u0015\u000b\u0017\u000c\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011" +
                "#\u0012%\u0013\'\u0014)\u0015+\u0016-\u0017/\u00181\u00193\u001a5\u001b" +
                "7\u001c9\u001d;\u001e=\u001f? A!C\"E#G\$I%K&M\'O(Q)\u0001\u0000\u000c\u0002" +
                "\u0000**//\u0002\u0000++--\u0003\u0000!!--~~\u0003\u0000&&^^||\u0002\u0000" +
                "DDdd\u0001\u000019\u0001\u000009\u0003\u0000AZ__az\u0004\u000009AZ__a" +
                "z\u0003\u0000\n\n\r\r\"\"\u0002\u0000\n\n\r\r\u0002\u0000\t\t  \u0117" +
                "\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000" +
                "\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000" +
                "\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000" +
                "\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011" +
                "\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015" +
                "\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019" +
                "\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d" +
                "\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001" +
                "\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000" +
                "\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000)\u0001\u0000\u0000\u0000" +
                "\u0000+\u0001\u0000\u0000\u0000\u0000-\u0001\u0000\u0000\u0000\u0000/" +
                "\u0001\u0000\u0000\u0000\u00001\u0001\u0000\u0000\u0000\u00003\u0001\u0000" +
                "\u0000\u0000\u00005\u0001\u0000\u0000\u0000\u00007\u0001\u0000\u0000\u0000" +
                "\u00009\u0001\u0000\u0000\u0000\u0000;\u0001\u0000\u0000\u0000\u0000=" +
                "\u0001\u0000\u0000\u0000\u0000?\u0001\u0000\u0000\u0000\u0000A\u0001\u0000" +
                "\u0000\u0000\u0000C\u0001\u0000\u0000\u0000\u0000E\u0001\u0000\u0000\u0000" +
                "\u0000G\u0001\u0000\u0000\u0000\u0000I\u0001\u0000\u0000\u0000\u0000K" +
                "\u0001\u0000\u0000\u0000\u0000M\u0001\u0000\u0000\u0000\u0000O\u0001\u0000" +
                "\u0000\u0000\u0000Q\u0001\u0000\u0000\u0000\u0001S\u0001\u0000\u0000\u0000" +
                "\u0003U\u0001\u0000\u0000\u0000\u0005W\u0001\u0000\u0000\u0000\u0007Y" +
                "\u0001\u0000\u0000\u0000\t[\u0001\u0000\u0000\u0000\u000b]\u0001\u0000" +
                "\u0000\u0000\r_\u0001\u0000\u0000\u0000\u000fa\u0001\u0000\u0000\u0000" +
                "\u0011c\u0001\u0000\u0000\u0000\u0013e\u0001\u0000\u0000\u0000\u0015g" +
                "\u0001\u0000\u0000\u0000\u0017i\u0001\u0000\u0000\u0000\u0019k\u0001\u0000" +
                "\u0000\u0000\u001bm\u0001\u0000\u0000\u0000\u001dq\u0001\u0000\u0000\u0000" +
                "\u001fs\u0001\u0000\u0000\u0000!u\u0001\u0000\u0000\u0000#w\u0001\u0000" +
                "\u0000\u0000%}\u0001\u0000\u0000\u0000\'\u007f\u0001\u0000\u0000\u0000" +
                ")\u0081\u0001\u0000\u0000\u0000+\u008c\u0001\u0000\u0000\u0000-\u0098" +
                "\u0001\u0000\u0000\u0000/\u009a\u0001\u0000\u0000\u00001\u009d\u0001\u0000" +
                "\u0000\u00003\u00a0\u0001\u0000\u0000\u00005\u00a4\u0001\u0000\u0000\u0000" +
                "7\u00a8\u0001\u0000\u0000\u00009\u00ac\u0001\u0000\u0000\u0000;\u00b1" +
                "\u0001\u0000\u0000\u0000=\u00b6\u0001\u0000\u0000\u0000?\u00bc\u0001\u0000" +
                "\u0000\u0000A\u00c2\u0001\u0000\u0000\u0000C\u00c8\u0001\u0000\u0000\u0000" +
                "E\u00ce\u0001\u0000\u0000\u0000G\u00dd\u0001\u0000\u0000\u0000I\u00df" +
                "\u0001\u0000\u0000\u0000K\u00e6\u0001\u0000\u0000\u0000M\u00f0\u0001\u0000" +
                "\u0000\u0000O\u00f7\u0001\u0000\u0000\u0000Q\u00fd\u0001\u0000\u0000\u0000" +
                "ST\u0005{\u0000\u0000T\u0002\u0001\u0000\u0000\u0000UV\u0005}\u0000\u0000" +
                "V\u0004\u0001\u0000\u0000\u0000WX\u0005(\u0000\u0000X\u0006\u0001\u0000" +
                "\u0000\u0000YZ\u0005)\u0000\u0000Z\b\u0001\u0000\u0000\u0000[\\\u0005" +
                "[\u0000\u0000\\\n\u0001\u0000\u0000\u0000]^\u0005]\u0000\u0000^\u000c\u0001" +
                "\u0000\u0000\u0000_`\u0005;\u0000\u0000`\u000e\u0001\u0000\u0000\u0000" +
                "ab\u0005=\u0000\u0000b\u0010\u0001\u0000\u0000\u0000cd\u0005,\u0000\u0000" +
                "d\u0012\u0001\u0000\u0000\u0000ef\u0005?\u0000\u0000f\u0014\u0001\u0000" +
                "\u0000\u0000gh\u0005:\u0000\u0000h\u0016\u0001\u0000\u0000\u0000ij\u0005" +
                "\"\u0000\u0000j\u0018\u0001\u0000\u0000\u0000kl\u0005.\u0000\u0000l\u001a" +
                "\u0001\u0000\u0000\u0000mn\u0005.\u0000\u0000no\u0005.\u0000\u0000op\u0005" +
                ".\u0000\u0000p\u001c\u0001\u0000\u0000\u0000qr\u0007\u0000\u0000\u0000" +
                "r\u001e\u0001\u0000\u0000\u0000st\u0007\u0001\u0000\u0000t \u0001\u0000" +
                "\u0000\u0000uv\u0005%\u0000\u0000v\"\u0001\u0000\u0000\u0000wx\u0007\u0002" +
                "\u0000\u0000x$\u0001\u0000\u0000\u0000yz\u0005&\u0000\u0000z~\u0005&\u0000" +
                "\u0000{|\u0005|\u0000\u0000|~\u0005|\u0000\u0000}y\u0001\u0000\u0000\u0000" +
                "}{\u0001\u0000\u0000\u0000~&\u0001\u0000\u0000\u0000\u007f\u0080\u0007" +
                "\u0003\u0000\u0000\u0080(\u0001\u0000\u0000\u0000\u0081\u0082\u0007\u0004" +
                "\u0000\u0000\u0082*\u0001\u0000\u0000\u0000\u0083\u0084\u0005t\u0000\u0000" +
                "\u0084\u0085\u0005r\u0000\u0000\u0085\u0086\u0005u\u0000\u0000\u0086\u008d" +
                "\u0005e\u0000\u0000\u0087\u0088\u0005f\u0000\u0000\u0088\u0089\u0005a" +
                "\u0000\u0000\u0089\u008a\u0005l\u0000\u0000\u008a\u008b\u0005s\u0000\u0000" +
                "\u008b\u008d\u0005e\u0000\u0000\u008c\u0083\u0001\u0000\u0000\u0000\u008c" +
                "\u0087\u0001\u0000\u0000\u0000\u008d,\u0001\u0000\u0000\u0000\u008e\u008f" +
                "\u0005=\u0000\u0000\u008f\u0099\u0005=\u0000\u0000\u0090\u0091\u0005!" +
                "\u0000\u0000\u0091\u0099\u0005=\u0000\u0000\u0092\u0099\u0005<\u0000\u0000" +
                "\u0093\u0094\u0005<\u0000\u0000\u0094\u0099\u0005=\u0000\u0000\u0095\u0099" +
                "\u0005>\u0000\u0000\u0096\u0097\u0005>\u0000\u0000\u0097\u0099\u0005=" +
                "\u0000\u0000\u0098\u008e\u0001\u0000\u0000\u0000\u0098\u0090\u0001\u0000" +
                "\u0000\u0000\u0098\u0092\u0001\u0000\u0000\u0000\u0098\u0093\u0001\u0000" +
                "\u0000\u0000\u0098\u0095\u0001\u0000\u0000\u0000\u0098\u0096\u0001\u0000" +
                "\u0000\u0000\u0099.\u0001\u0000\u0000\u0000\u009a\u009b\u0005i\u0000\u0000" +
                "\u009b\u009c\u0005f\u0000\u0000\u009c0\u0001\u0000\u0000\u0000\u009d\u009e" +
                "\u0005i\u0000\u0000\u009e\u009f\u0005n\u0000\u0000\u009f2\u0001\u0000" +
                "\u0000\u0000\u00a0\u00a1\u0005f\u0000\u0000\u00a1\u00a2\u0005o\u0000\u0000" +
                "\u00a2\u00a3\u0005r\u0000\u0000\u00a34\u0001\u0000\u0000\u0000\u00a4\u00a5" +
                "\u0005f\u0000\u0000\u00a5\u00a6\u0005u\u0000\u0000\u00a6\u00a7\u0005n" +
                "\u0000\u0000\u00a76\u0001\u0000\u0000\u0000\u00a8\u00a9\u0005v\u0000\u0000" +
                "\u00a9\u00aa\u0005a\u0000\u0000\u00aa\u00ab\u0005r\u0000\u0000\u00ab8" +
                "\u0001\u0000\u0000\u0000\u00ac\u00ad\u0005d\u0000\u0000\u00ad\u00ae\u0005" +
                "a\u0000\u0000\u00ae\u00af\u0005t\u0000\u0000\u00af\u00b0\u0005a\u0000" +
                "\u0000\u00b0:\u0001\u0000\u0000\u0000\u00b1\u00b2\u0005e\u0000\u0000\u00b2" +
                "\u00b3\u0005l\u0000\u0000\u00b3\u00b4\u0005s\u0000\u0000\u00b4\u00b5\u0005" +
                "e\u0000\u0000\u00b5<\u0001\u0000\u0000\u0000\u00b6\u00b7\u0005b\u0000" +
                "\u0000\u00b7\u00b8\u0005r\u0000\u0000\u00b8\u00b9\u0005e\u0000\u0000\u00b9" +
                "\u00ba\u0005a\u0000\u0000\u00ba\u00bb\u0005k\u0000\u0000\u00bb>\u0001" +
                "\u0000\u0000\u0000\u00bc\u00bd\u0005f\u0000\u0000\u00bd\u00be\u0005i\u0000" +
                "\u0000\u00be\u00bf\u0005e\u0000\u0000\u00bf\u00c0\u0005l\u0000\u0000\u00c0" +
                "\u00c1\u0005d\u0000\u0000\u00c1@\u0001\u0000\u0000\u0000\u00c2\u00c3\u0005" +
                "u\u0000\u0000\u00c3\u00c4\u0005n\u0000\u0000\u00c4\u00c5\u0005t\u0000" +
                "\u0000\u00c5\u00c6\u0005i\u0000\u0000\u00c6\u00c7\u0005l\u0000\u0000\u00c7" +
                "B\u0001\u0000\u0000\u0000\u00c8\u00c9\u0005w\u0000\u0000\u00c9\u00ca\u0005" +
                "h\u0000\u0000\u00ca\u00cb\u0005i\u0000\u0000\u00cb\u00cc\u0005l\u0000" +
                "\u0000\u00cc\u00cd\u0005e\u0000\u0000\u00cdD\u0001\u0000\u0000\u0000\u00ce" +
                "\u00cf\u0005r\u0000\u0000\u00cf\u00d0\u0005e\u0000\u0000\u00d0\u00d1\u0005" +
                "t\u0000\u0000\u00d1\u00d2\u0005u\u0000\u0000\u00d2\u00d3\u0005r\u0000" +
                "\u0000\u00d3\u00d4\u0005n\u0000\u0000\u00d4F\u0001\u0000\u0000\u0000\u00d5" +
                "\u00d9\u0007\u0005\u0000\u0000\u00d6\u00d8\u0007\u0006\u0000\u0000\u00d7" +
                "\u00d6\u0001\u0000\u0000\u0000\u00d8\u00db\u0001\u0000\u0000\u0000\u00d9" +
                "\u00d7\u0001\u0000\u0000\u0000\u00d9\u00da\u0001\u0000\u0000\u0000\u00da" +
                "\u00de\u0001\u0000\u0000\u0000\u00db\u00d9\u0001\u0000\u0000\u0000\u00dc" +
                "\u00de\u00050\u0000\u0000\u00dd\u00d5\u0001\u0000\u0000\u0000\u00dd\u00dc" +
                "\u0001\u0000\u0000\u0000\u00deH\u0001\u0000\u0000\u0000\u00df\u00e3\u0007" +
                "\u0007\u0000\u0000\u00e0\u00e2\u0007\b\u0000\u0000\u00e1\u00e0\u0001\u0000" +
                "\u0000\u0000\u00e2\u00e5\u0001\u0000\u0000\u0000\u00e3\u00e1\u0001\u0000" +
                "\u0000\u0000\u00e3\u00e4\u0001\u0000\u0000\u0000\u00e4J\u0001\u0000\u0000" +
                "\u0000\u00e5\u00e3\u0001\u0000\u0000\u0000\u00e6\u00ea\u0005\"\u0000\u0000" +
                "\u00e7\u00e9\b\t\u0000\u0000\u00e8\u00e7\u0001\u0000\u0000\u0000\u00e9" +
                "\u00ec\u0001\u0000\u0000\u0000\u00ea\u00e8\u0001\u0000\u0000\u0000\u00ea" +
                "\u00eb\u0001\u0000\u0000\u0000\u00eb\u00ed\u0001\u0000\u0000\u0000\u00ec" +
                "\u00ea\u0001\u0000\u0000\u0000\u00ed\u00ee\u0005\"\u0000\u0000\u00eeL" +
                "\u0001\u0000\u0000\u0000\u00ef\u00f1\u0007\n\u0000\u0000\u00f0\u00ef\u0001" +
                "\u0000\u0000\u0000\u00f1\u00f2\u0001\u0000\u0000\u0000\u00f2\u00f0\u0001" +
                "\u0000\u0000\u0000\u00f2\u00f3\u0001\u0000\u0000\u0000\u00f3\u00f4\u0001" +
                "\u0000\u0000\u0000\u00f4\u00f5\u0006&\u0000\u0000\u00f5N\u0001\u0000\u0000" +
                "\u0000\u00f6\u00f8\u0007\u000b\u0000\u0000\u00f7\u00f6\u0001\u0000\u0000" +
                "\u0000\u00f8\u00f9\u0001\u0000\u0000\u0000\u00f9\u00f7\u0001\u0000\u0000" +
                "\u0000\u00f9\u00fa\u0001\u0000\u0000\u0000\u00fa\u00fb\u0001\u0000\u0000" +
                "\u0000\u00fb\u00fc\u0006\'\u0000\u0000\u00fcP\u0001\u0000\u0000\u0000" +
                "\u00fd\u00fe\u0005/\u0000\u0000\u00fe\u00ff\u0005/\u0000\u0000\u00ff\u0103" +
                "\u0001\u0000\u0000\u0000\u0100\u0102\b\n\u0000\u0000\u0101\u0100\u0001" +
                "\u0000\u0000\u0000\u0102\u0105\u0001\u0000\u0000\u0000\u0103\u0101\u0001" +
                "\u0000\u0000\u0000\u0103\u0104\u0001\u0000\u0000\u0000\u0104\u0106\u0001" +
                "\u0000\u0000\u0000\u0105\u0103\u0001\u0000\u0000\u0000\u0106\u0107\u0007" +
                "\n\u0000\u0000\u0107\u0108\u0001\u0000\u0000\u0000\u0108\u0109\u0006(" +
                "\u0000\u0000\u0109R\u0001\u0000\u0000\u0000\u000b\u0000}\u008c\u0098\u00d9" +
                "\u00dd\u00e3\u00ea\u00f2\u00f9\u0103\u0001\u0006\u0000\u0000"
        val _ATN = ATNDeserializer().deserialize(_serializedATN.toCharArray())

        init {
            _decisionToDFA = arrayOfNulls(_ATN.numberOfDecisions)
            for (i in 0 until _ATN.numberOfDecisions) {
                _decisionToDFA[i] = DFA(_ATN.getDecisionState(i), i)
            }
        }
    }
}