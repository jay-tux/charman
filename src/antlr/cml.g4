grammar cml;

program:        topLevel EOF ;

topLevel:       'race' name=IDENT '{' body=decl* '}'                #raceDesc
        |       'class' name=IDENT '{' body=decl* '}'               #classDesc
        |       'background' name=IDENT '{' body=decl* '}'          #bgDesc
        |       'item' name=IDENT '{' body=decl* '}'                #itemDesc
        |       'spell' name=IDENT '{' body=decl* '}'               #spellDesc
        ;

decl:           'fun' name=IDENT '(' argDs ')' '{' body=stmt* '}'   #funDecl
    |           'field' name=IDENT '=' init=expr ';'                #fieldDecl
    ;

argDs:
     |          args=argDsNonEmpty
     ;

argDsNonEmpty:  arg=IDENT
             |  arg1=IDENT ',' args=argDsNonEmpty
             ;

stmt:
// base statements
                e=expr ';'                                          #exprStmt
    |           'var' name=IDENT '=' init=expr ';'                  #varDeclStmt
    |           name=IDENT '=' val=expr ';'                         #varStoreStmt
// conditionals and flow control
    |           'if' '(' expr ')' '{' bTrue=stmt* '}'               #ifStmt
    |           'if' '(' expr ')' '{' bTrue=stmt* '}' 'else' '{' bFalse=stmt* '}'
                                                                    #ifElseStmt
    |           'while' '(' cond=expr ')' '{' body=stmt* '}'        #whileStmt
    |           'for' '(' varN=IDENT 'in' range=expr ')' '{' body=stmt* '}'
                                                                    #forStmt
    |           'break' ';'                                         #breakStmt
    |           'return' ';'                                        #returnStmt
// Function call
    |           ftor=IDENT '(' args=argsList ')' ';'                #callExprNoArgs
    ;

expr:
// Literals
                str=stringExpr                                      #stringLit
    |           val=INT                                             #intLit
    |           val=BOOL                                            #boolLit
    |           var=IDENT                                           #varExpr
// Arithmetic and operators
    |           '(' content=expr ')'                                #parenExpr
    |           count=expr DICE dice=expr                           #diceExpr
    |           left=expr op=('*'|'/') right=expr                   #mulDivExpr
    |           left=expr op=('+'|'-') right=expr                   #addSubExpr
    |           left=expr '%' right=expr                            #modExpr
    |           op=('~'|'!'|'-') val=expr                           #unaryExpr
    |           left=expr op=COMPARISON_OP right=expr               #compareExpr
    |           left=expr op=('&&'|'||') right=expr                 #logicExpr
    |           left=expr op=('^'|'&'|'|') right=expr               #bitwiseExpr
    |           condition=expr '?' bTrue=expr ':' bFalse=expr       #ternaryExpr
// Special constructors
    |           start=expr '...' end=expr                           #rangeExpr
    |           start=expr 'until' end=expr                         #untilExpr
    |           '[' values=argsList ']'                             #listExpr
    |           '{' values=kvpList '}'                              #dictExpr
    ;

kvpList:
       |        values=nonEmptyKvp
       ;

nonEmptyKvp:    key=expr '=' val=expr
           |    key=expr '=' val=expr ',' res=nonEmptyKvp
           ;

argsList:
        |       args=nonEmptyArgs
        ;

nonEmptyArgs:   arg=expr
            |   arg=expr ',' rest=nonEmptyArgs
            ;

stringExpr:     '""'
          |     '"' content=STRING_CHARS '"' ;

NEWLINE:        [\r\n]+ -> skip;
WS:             [\t ]+ -> skip;
COMMENT_CHARS:  '//'~[\r\n]*[\r\n] -> skip;

DICE:           'd'|'D';
BOOL:           'true'|'false';
INT:            [1-9][0-9]*|'0';
IDENT:          [a-zA-Z_][a-zA-Z0-9_]*;
STRING_CHARS:   ~[\r\n"]+;
COMPARISON_OP:  '=='|'!='|'<'|'<='|'>'|'>=';
