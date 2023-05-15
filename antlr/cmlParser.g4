parser grammar cmlParser;

program:        topLevel* EOF ;

topLevel:       DATA kind=IDENT name=IDENT B_O body=declSet B_C     #type
        |       INSTANCE templ=IDENT name=IDENT BR_O args=argsList BR_C SEMI
                                                                    #instance
        |       TEMPLATE kind=IDENT BR_O args=argDs BR_C B_O body=declSet B_C
                                                                    #template
        ;

declSet:
       |        prev=declSet d=decl
       ;

decl:           FUN name=IDENT P_O args=argDs P_C B_O body=stmtSet B_C
                                                                    #funDecl
    |           FIELD name=IDENT ASSIGN init=expr SEMI              #fieldDecl
    ;

stmtSet:                                                            #noStmt
       |         prev=stmtSet s=stmt                                #stmts
       ;

argDs:
     |          args=argDsNonEmpty
     ;

argDsNonEmpty:  arg=IDENT
             |  args=argDsNonEmpty COMMA arg=IDENT
             ;

stmt:
// base statements
                e=expr SEMI                                         #exprStmt
    |           VAR name=IDENT ASSIGN init=expr SEMI                #varDeclStmt
    |           name=IDENT ASSIGN value=expr SEMI                   #varStoreStmt
// conditionals and flow control
    |           IF P_O cond=expr P_C B_O bTrue=stmtSet B_C          #ifStmt
    |           IF P_O cond=expr P_C B_O bTrue=stmtSet B_C ELSE B_O bFalse=stmtSet B_C
                                                                    #ifElseStmt
    |           WHILE P_O cond=expr P_C B_O body=stmtSet B_C        #whileStmt
    |           FOR P_O varN=IDENT IN range=expr P_C B_O body=stmtSet B_C
                                                                    #forStmt
    |           BREAK SEMI                                          #breakStmt
    |           RETURN SEMI                                         #returnStmt
    |           RETURN v=expr SEMI                                  #returnValStmt
    ;

expr:
// Literals
                str=stringExpr                                      #stringLit
    |           value=INT                                           #intLit
    |           value=BOOL                                          #boolLit
    |           value=IDENT                                         #varExpr
    |           base=expr DOT name=IDENT                            #fieldExpr
    |           base=expr BR_O index=expr BR_C                      #indexExpr
    |           ph=PLACEHOLDER                                      #placeholderExpr
// Pseudo-constructors
    |           DOT type=IDENT                                      #ctorExpr
// Arithmetic and operators
    |           P_O content=expr P_C                                #parenExpr
    |           count=expr DICE dice=expr                           #diceExpr
    |           left=expr op=MD_OP right=expr                       #mulDivExpr
    |           left=expr op=AS_OP right=expr                       #addSubExpr
    |           left=expr MOD_OP right=expr                         #modExpr
    |           op=UN_OP value=expr                                 #unaryExpr
    |           left=expr op=COMPARISON_OP right=expr               #compareExpr
    |           left=expr op=LOGIC_OP right=expr                    #logicExpr
    |           left=expr op=BITWISE_OP right=expr                  #bitwiseExpr
    |           condition=expr QMARK bTrue=expr COLON bFalse=expr   #ternaryExpr
// Special constructors
    |           begin=expr ELIPSIS end=expr                         #rangeExpr
    |           begin=expr UNTIL end=expr                           #untilExpr
    |           BR_O values=argsList BR_C                           #listExpr
    |           B_O values=kvpList B_C                              #dictExpr
// Function call
    |           ftor=IDENT P_O args=argsList P_C                    #callExpr
    |           base=expr DOT func=IDENT P_O args=argsList P_C      #objCallExpr
    ;

kvpList:
       |        values=nonEmptyKvp
       ;

nonEmptyKvp:    key=expr ASSIGN value=expr
           |    prev=nonEmptyKvp COMMA key=expr ASSIGN val=expr
           ;

argsList:
        |       args=nonEmptyArgs
        ;

nonEmptyArgs:   arg=expr
            |   prev=nonEmptyArgs COMMA arg=expr
            ;

stringExpr:     STRING_LIT;
