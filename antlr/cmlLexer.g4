lexer grammar cmlLexer;

B_O:            '{';
B_C:            '}';
P_O:            '(';
P_C:            ')';
BR_O:           '[';
BR_C:           ']';
SEMI:           ';';
ASSIGN:         '=';
COMMA:          ',';
QMARK:          '?';
COLON:          ':';
STRING_DELIM:   '"';
DOT:            '.';
ELIPSIS:        '...';
MD_OP:          '*'|'/';
AS_OP:          '+'|'-';
MOD_OP:         '%';
UN_OP:          '~'|'!'|'-';
LOGIC_OP:       '&&'|'||';
BITWISE_OP:     '&'|'|'|'^';
DICE:           'd'|'D';
BOOL:           'true'|'false';
COMPARISON_OP:  '=='|'!='|'<'|'<='|'>'|'>=';

IF:             'if';
IN:             'in';
FOR:            'for';
FUN:            'fun';
VAR:            'var';
DATA:           'data';
ELSE:           'else';
BREAK:          'break';
FIELD:          'field';
UNTIL:          'until';
WHILE:          'while';
GLOBAL:         'global';
RETURN:         'return';
INSTANCE:       'instance';
TEMPLATE:       'template';

INT:            [1-9][0-9]*|'0';
FLOAT:          INT'.'[0-9]+;
DICE_LIT:       INT('d'|'D')INT;
IDENT:          [a-zA-Z_][a-zA-Z0-9_]*;
PLACEHOLDER:    '<'IDENT'>';
STRING_LIT:     '"'~["]*'"';

NEWLINE:        [\r\n]+ -> skip;
WS:             [ \t]+ -> skip;
COMMENT_CHARS:  '//'~[\r\n]*[\r\n] -> skip;

ANY:            .;