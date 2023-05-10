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
        var channelNames = arrayOf(
            "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
        )
        var modeNames = arrayOf(
            "DEFAULT_MODE"
        )

        private fun makeRuleNames(): Array<String> {
            return arrayOf(
                "B_O", "B_C", "P_O", "P_C", "BR_O", "BR_C", "SEMI", "ASSIGN", "COMMA",
                "QMARK", "COLON", "STRING_DELIM", "ELIPSIS", "MD_OP", "AS_OP", "MOD_OP",
                "UN_OP", "LOGIC_OP", "BITWISE_OP", "DICE", "BOOL", "COMPARISON_OP", "IF",
                "IN", "FOR", "FUN", "VAR", "DATA", "ELSE", "BREAK", "FIELD", "UNTIL",
                "WHILE", "RETURN", "INT", "IDENT", "STRING_LIT", "NEWLINE", "WS", "COMMENT_CHARS"
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

        const val _serializedATN = "\u0004\u0000(\u0106\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001" +
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
                "&\u0002\'\u0007\'\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001" +
                "\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001" +
                "\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001" +
                "\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001" +
                "\u000c\u0001\u000c\u0001\u000c\u0001\u000c\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001" +
                "\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001" +
                "\u0011\u0001\u0011\u0003\u0011z\b\u0011\u0001\u0012\u0001\u0012\u0001" +
                "\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001" +
                "\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0003\u0014\u0089" +
                "\b\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001" +
                "\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0003\u0015\u0095" +
                "\b\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001" +
                "\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0019\u0001" +
                "\u0019\u0001\u0019\u0001\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0001" +
                "\u001a\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001" +
                "\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001d\u0001" +
                "\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001e\u0001" +
                "\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001f\u0001" +
                "\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001 \u0001 \u0001" +
                " \u0001 \u0001 \u0001 \u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001" +
                "!\u0001\"\u0001\"\u0005\"\u00d4\b\"\n\"\u000c\"\u00d7\t\"\u0001\"\u0003\"" +
                "\u00da\b\"\u0001#\u0001#\u0005#\u00de\b#\n#\u000c#\u00e1\t#\u0001$\u0001$" +
                "\u0005$\u00e5\b$\n$\u000c$\u00e8\t$\u0001$\u0001$\u0001%\u0004%\u00ed\b%\u000b" +
                "%\u000c%\u00ee\u0001%\u0001%\u0001&\u0004&\u00f4\b&\u000b&\u000c&\u00f5\u0001" +
                "&\u0001&\u0001\'\u0001\'\u0001\'\u0001\'\u0005\'\u00fe\b\'\n\'\u000c\'\u0101" +
                "\t\'\u0001\'\u0001\'\u0001\'\u0001\'\u0000\u0000(\u0001\u0001\u0003\u0002" +
                "\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013" +
                "\n\u0015\u000b\u0017\u000c\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011" +
                "#\u0012%\u0013\'\u0014)\u0015+\u0016-\u0017/\u00181\u00193\u001a5\u001b" +
                "7\u001c9\u001d;\u001e=\u001f? A!C\"E#G\$I%K&M\'O(\u0001\u0000\u000c\u0002\u0000" +
                "**//\u0002\u0000++--\u0003\u0000!!--~~\u0003\u0000&&^^||\u0002\u0000D" +
                "Ddd\u0001\u000019\u0001\u000009\u0003\u0000AZ__az\u0004\u000009AZ__az" +
                "\u0003\u0000\n\n\r\r\"\"\u0002\u0000\n\n\r\r\u0002\u0000\t\t  \u0113\u0000" +
                "\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000" +
                "\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000" +
                "\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r" +
                "\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011" +
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
                "\u0000\u0000\u0001Q\u0001\u0000\u0000\u0000\u0003S\u0001\u0000\u0000\u0000" +
                "\u0005U\u0001\u0000\u0000\u0000\u0007W\u0001\u0000\u0000\u0000\tY\u0001" +
                "\u0000\u0000\u0000\u000b[\u0001\u0000\u0000\u0000\r]\u0001\u0000\u0000" +
                "\u0000\u000f_\u0001\u0000\u0000\u0000\u0011a\u0001\u0000\u0000\u0000\u0013" +
                "c\u0001\u0000\u0000\u0000\u0015e\u0001\u0000\u0000\u0000\u0017g\u0001" +
                "\u0000\u0000\u0000\u0019i\u0001\u0000\u0000\u0000\u001bm\u0001\u0000\u0000" +
                "\u0000\u001do\u0001\u0000\u0000\u0000\u001fq\u0001\u0000\u0000\u0000!" +
                "s\u0001\u0000\u0000\u0000#y\u0001\u0000\u0000\u0000%{\u0001\u0000\u0000" +
                "\u0000\'}\u0001\u0000\u0000\u0000)\u0088\u0001\u0000\u0000\u0000+\u0094" +
                "\u0001\u0000\u0000\u0000-\u0096\u0001\u0000\u0000\u0000/\u0099\u0001\u0000" +
                "\u0000\u00001\u009c\u0001\u0000\u0000\u00003\u00a0\u0001\u0000\u0000\u0000" +
                "5\u00a4\u0001\u0000\u0000\u00007\u00a8\u0001\u0000\u0000\u00009\u00ad" +
                "\u0001\u0000\u0000\u0000;\u00b2\u0001\u0000\u0000\u0000=\u00b8\u0001\u0000" +
                "\u0000\u0000?\u00be\u0001\u0000\u0000\u0000A\u00c4\u0001\u0000\u0000\u0000" +
                "C\u00ca\u0001\u0000\u0000\u0000E\u00d9\u0001\u0000\u0000\u0000G\u00db" +
                "\u0001\u0000\u0000\u0000I\u00e2\u0001\u0000\u0000\u0000K\u00ec\u0001\u0000" +
                "\u0000\u0000M\u00f3\u0001\u0000\u0000\u0000O\u00f9\u0001\u0000\u0000\u0000" +
                "QR\u0005{\u0000\u0000R\u0002\u0001\u0000\u0000\u0000ST\u0005}\u0000\u0000" +
                "T\u0004\u0001\u0000\u0000\u0000UV\u0005(\u0000\u0000V\u0006\u0001\u0000" +
                "\u0000\u0000WX\u0005)\u0000\u0000X\b\u0001\u0000\u0000\u0000YZ\u0005[" +
                "\u0000\u0000Z\n\u0001\u0000\u0000\u0000[\\\u0005]\u0000\u0000\\\u000c\u0001" +
                "\u0000\u0000\u0000]^\u0005;\u0000\u0000^\u000e\u0001\u0000\u0000\u0000" +
                "_`\u0005=\u0000\u0000`\u0010\u0001\u0000\u0000\u0000ab\u0005,\u0000\u0000" +
                "b\u0012\u0001\u0000\u0000\u0000cd\u0005?\u0000\u0000d\u0014\u0001\u0000" +
                "\u0000\u0000ef\u0005:\u0000\u0000f\u0016\u0001\u0000\u0000\u0000gh\u0005" +
                "\"\u0000\u0000h\u0018\u0001\u0000\u0000\u0000ij\u0005.\u0000\u0000jk\u0005" +
                ".\u0000\u0000kl\u0005.\u0000\u0000l\u001a\u0001\u0000\u0000\u0000mn\u0007" +
                "\u0000\u0000\u0000n\u001c\u0001\u0000\u0000\u0000op\u0007\u0001\u0000" +
                "\u0000p\u001e\u0001\u0000\u0000\u0000qr\u0005%\u0000\u0000r \u0001\u0000" +
                "\u0000\u0000st\u0007\u0002\u0000\u0000t\"\u0001\u0000\u0000\u0000uv\u0005" +
                "&\u0000\u0000vz\u0005&\u0000\u0000wx\u0005|\u0000\u0000xz\u0005|\u0000" +
                "\u0000yu\u0001\u0000\u0000\u0000yw\u0001\u0000\u0000\u0000z$\u0001\u0000" +
                "\u0000\u0000{|\u0007\u0003\u0000\u0000|&\u0001\u0000\u0000\u0000}~\u0007" +
                "\u0004\u0000\u0000~(\u0001\u0000\u0000\u0000\u007f\u0080\u0005t\u0000" +
                "\u0000\u0080\u0081\u0005r\u0000\u0000\u0081\u0082\u0005u\u0000\u0000\u0082" +
                "\u0089\u0005e\u0000\u0000\u0083\u0084\u0005f\u0000\u0000\u0084\u0085\u0005" +
                "a\u0000\u0000\u0085\u0086\u0005l\u0000\u0000\u0086\u0087\u0005s\u0000" +
                "\u0000\u0087\u0089\u0005e\u0000\u0000\u0088\u007f\u0001\u0000\u0000\u0000" +
                "\u0088\u0083\u0001\u0000\u0000\u0000\u0089*\u0001\u0000\u0000\u0000\u008a" +
                "\u008b\u0005=\u0000\u0000\u008b\u0095\u0005=\u0000\u0000\u008c\u008d\u0005" +
                "!\u0000\u0000\u008d\u0095\u0005=\u0000\u0000\u008e\u0095\u0005<\u0000" +
                "\u0000\u008f\u0090\u0005<\u0000\u0000\u0090\u0095\u0005=\u0000\u0000\u0091" +
                "\u0095\u0005>\u0000\u0000\u0092\u0093\u0005>\u0000\u0000\u0093\u0095\u0005" +
                "=\u0000\u0000\u0094\u008a\u0001\u0000\u0000\u0000\u0094\u008c\u0001\u0000" +
                "\u0000\u0000\u0094\u008e\u0001\u0000\u0000\u0000\u0094\u008f\u0001\u0000" +
                "\u0000\u0000\u0094\u0091\u0001\u0000\u0000\u0000\u0094\u0092\u0001\u0000" +
                "\u0000\u0000\u0095,\u0001\u0000\u0000\u0000\u0096\u0097\u0005i\u0000\u0000" +
                "\u0097\u0098\u0005f\u0000\u0000\u0098.\u0001\u0000\u0000\u0000\u0099\u009a" +
                "\u0005i\u0000\u0000\u009a\u009b\u0005n\u0000\u0000\u009b0\u0001\u0000" +
                "\u0000\u0000\u009c\u009d\u0005f\u0000\u0000\u009d\u009e\u0005o\u0000\u0000" +
                "\u009e\u009f\u0005r\u0000\u0000\u009f2\u0001\u0000\u0000\u0000\u00a0\u00a1" +
                "\u0005f\u0000\u0000\u00a1\u00a2\u0005u\u0000\u0000\u00a2\u00a3\u0005n" +
                "\u0000\u0000\u00a34\u0001\u0000\u0000\u0000\u00a4\u00a5\u0005v\u0000\u0000" +
                "\u00a5\u00a6\u0005a\u0000\u0000\u00a6\u00a7\u0005r\u0000\u0000\u00a76" +
                "\u0001\u0000\u0000\u0000\u00a8\u00a9\u0005d\u0000\u0000\u00a9\u00aa\u0005" +
                "a\u0000\u0000\u00aa\u00ab\u0005t\u0000\u0000\u00ab\u00ac\u0005a\u0000" +
                "\u0000\u00ac8\u0001\u0000\u0000\u0000\u00ad\u00ae\u0005e\u0000\u0000\u00ae" +
                "\u00af\u0005l\u0000\u0000\u00af\u00b0\u0005s\u0000\u0000\u00b0\u00b1\u0005" +
                "e\u0000\u0000\u00b1:\u0001\u0000\u0000\u0000\u00b2\u00b3\u0005b\u0000" +
                "\u0000\u00b3\u00b4\u0005r\u0000\u0000\u00b4\u00b5\u0005e\u0000\u0000\u00b5" +
                "\u00b6\u0005a\u0000\u0000\u00b6\u00b7\u0005k\u0000\u0000\u00b7<\u0001" +
                "\u0000\u0000\u0000\u00b8\u00b9\u0005f\u0000\u0000\u00b9\u00ba\u0005i\u0000" +
                "\u0000\u00ba\u00bb\u0005e\u0000\u0000\u00bb\u00bc\u0005l\u0000\u0000\u00bc" +
                "\u00bd\u0005d\u0000\u0000\u00bd>\u0001\u0000\u0000\u0000\u00be\u00bf\u0005" +
                "u\u0000\u0000\u00bf\u00c0\u0005n\u0000\u0000\u00c0\u00c1\u0005t\u0000" +
                "\u0000\u00c1\u00c2\u0005i\u0000\u0000\u00c2\u00c3\u0005l\u0000\u0000\u00c3" +
                "@\u0001\u0000\u0000\u0000\u00c4\u00c5\u0005w\u0000\u0000\u00c5\u00c6\u0005" +
                "h\u0000\u0000\u00c6\u00c7\u0005i\u0000\u0000\u00c7\u00c8\u0005l\u0000" +
                "\u0000\u00c8\u00c9\u0005e\u0000\u0000\u00c9B\u0001\u0000\u0000\u0000\u00ca" +
                "\u00cb\u0005r\u0000\u0000\u00cb\u00cc\u0005e\u0000\u0000\u00cc\u00cd\u0005" +
                "t\u0000\u0000\u00cd\u00ce\u0005u\u0000\u0000\u00ce\u00cf\u0005r\u0000" +
                "\u0000\u00cf\u00d0\u0005n\u0000\u0000\u00d0D\u0001\u0000\u0000\u0000\u00d1" +
                "\u00d5\u0007\u0005\u0000\u0000\u00d2\u00d4\u0007\u0006\u0000\u0000\u00d3" +
                "\u00d2\u0001\u0000\u0000\u0000\u00d4\u00d7\u0001\u0000\u0000\u0000\u00d5" +
                "\u00d3\u0001\u0000\u0000\u0000\u00d5\u00d6\u0001\u0000\u0000\u0000\u00d6" +
                "\u00da\u0001\u0000\u0000\u0000\u00d7\u00d5\u0001\u0000\u0000\u0000\u00d8" +
                "\u00da\u00050\u0000\u0000\u00d9\u00d1\u0001\u0000\u0000\u0000\u00d9\u00d8" +
                "\u0001\u0000\u0000\u0000\u00daF\u0001\u0000\u0000\u0000\u00db\u00df\u0007" +
                "\u0007\u0000\u0000\u00dc\u00de\u0007\b\u0000\u0000\u00dd\u00dc\u0001\u0000" +
                "\u0000\u0000\u00de\u00e1\u0001\u0000\u0000\u0000\u00df\u00dd\u0001\u0000" +
                "\u0000\u0000\u00df\u00e0\u0001\u0000\u0000\u0000\u00e0H\u0001\u0000\u0000" +
                "\u0000\u00e1\u00df\u0001\u0000\u0000\u0000\u00e2\u00e6\u0005\"\u0000\u0000" +
                "\u00e3\u00e5\b\t\u0000\u0000\u00e4\u00e3\u0001\u0000\u0000\u0000\u00e5" +
                "\u00e8\u0001\u0000\u0000\u0000\u00e6\u00e4\u0001\u0000\u0000\u0000\u00e6" +
                "\u00e7\u0001\u0000\u0000\u0000\u00e7\u00e9\u0001\u0000\u0000\u0000\u00e8" +
                "\u00e6\u0001\u0000\u0000\u0000\u00e9\u00ea\u0005\"\u0000\u0000\u00eaJ" +
                "\u0001\u0000\u0000\u0000\u00eb\u00ed\u0007\n\u0000\u0000\u00ec\u00eb\u0001" +
                "\u0000\u0000\u0000\u00ed\u00ee\u0001\u0000\u0000\u0000\u00ee\u00ec\u0001" +
                "\u0000\u0000\u0000\u00ee\u00ef\u0001\u0000\u0000\u0000\u00ef\u00f0\u0001" +
                "\u0000\u0000\u0000\u00f0\u00f1\u0006%\u0000\u0000\u00f1L\u0001\u0000\u0000" +
                "\u0000\u00f2\u00f4\u0007\u000b\u0000\u0000\u00f3\u00f2\u0001\u0000\u0000" +
                "\u0000\u00f4\u00f5\u0001\u0000\u0000\u0000\u00f5\u00f3\u0001\u0000\u0000" +
                "\u0000\u00f5\u00f6\u0001\u0000\u0000\u0000\u00f6\u00f7\u0001\u0000\u0000" +
                "\u0000\u00f7\u00f8\u0006&\u0000\u0000\u00f8N\u0001\u0000\u0000\u0000\u00f9" +
                "\u00fa\u0005/\u0000\u0000\u00fa\u00fb\u0005/\u0000\u0000\u00fb\u00ff\u0001" +
                "\u0000\u0000\u0000\u00fc\u00fe\b\n\u0000\u0000\u00fd\u00fc\u0001\u0000" +
                "\u0000\u0000\u00fe\u0101\u0001\u0000\u0000\u0000\u00ff\u00fd\u0001\u0000" +
                "\u0000\u0000\u00ff\u0100\u0001\u0000\u0000\u0000\u0100\u0102\u0001\u0000" +
                "\u0000\u0000\u0101\u00ff\u0001\u0000\u0000\u0000\u0102\u0103\u0007\n\u0000" +
                "\u0000\u0103\u0104\u0001\u0000\u0000\u0000\u0104\u0105\u0006\'\u0000\u0000" +
                "\u0105P\u0001\u0000\u0000\u0000\u000b\u0000y\u0088\u0094\u00d5\u00d9\u00df" +
                "\u00e6\u00ee\u00f5\u00ff\u0001\u0006\u0000\u0000"
        val _ATN = ATNDeserializer().deserialize(_serializedATN.toCharArray())

        init {
            _decisionToDFA = arrayOfNulls(_ATN.numberOfDecisions)
            for (i in 0 until _ATN.numberOfDecisions) {
                _decisionToDFA[i] = DFA(_ATN.getDecisionState(i), i)
            }
        }
    }
}