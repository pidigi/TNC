// ANTLR v4 Grammar for controling UFO entities in Asteroids.
// Check the tutorial at http://jnb.ociweb.com/jnb/jnbJun2008.html
// or http://www.antlr.org/ for details.


grammar AsteroidsParser;

@header { package asteroids.model.programs.parsing; }


@members
{

}


// ------------------------------------------------------------------------
// --- Eval and Related Definitions ---------------------------------------
// ------------------------------------------------------------------------
eval:	    ((decl SEMICOLON
            | action SEMICOLON
            | assign SEMICOLON
            | PRINT expr SEMICOLON
            | ctrl
            | SEMICOLON) (eval)?)?
;
decl:       type IDENTIFIER (ASSIGN expr)?;
action:     THRUSTON
            | THRUSTOFF
            | TURN expr
            | FIRE
            | SKIP
;
unop:     GETRADIUS expr
            | GETX expr
            | GETY expr
            | GETVX expr
            | GETVY expr
            | SQRT LEFT_PAREN expr RIGHT_PAREN
            | SIN LEFT_PAREN expr RIGHT_PAREN
            | COS LEFT_PAREN expr RIGHT_PAREN
            | NOT expr
;
ctrl:       ifthenelse | whiledo | foreach;
ifthenelse: IF expr (THEN)? LEFT_BRACE eval RIGHT_BRACE
            (ELSE LEFT_BRACE eval RIGHT_BRACE)?;
whiledo:    WHILE expr (DO)? LEFT_BRACE eval RIGHT_BRACE;
foreach:    FOREACH LEFT_PAREN entityspec COMMA IDENTIFIER RIGHT_PAREN
            (DO)? LEFT_BRACE eval RIGHT_BRACE;
assign:     IDENTIFIER ASSIGN expr;
expr:       NUMBER
            | IDENTIFIER
            | GETDIR
            | LEFT_PAREN expr RIGHT_PAREN
            | namedconst
            | unop
            | expr binop expr
;

// ------------------------------------------------------------------------
// --- Named Constants ----------------------------------------------------
// ------------------------------------------------------------------------

namedconst: SELF | TRUE | FALSE | NULL;
SELF: 'self';
TRUE: 'true';
FALSE: 'false';
NULL: 'null';


// ------------------------------------------------------------------------
// --- Types and Specifiers -----------------------------------------------
// ------------------------------------------------------------------------
type:   BOOL | DOUBLE | ENTITY;
BOOL: 'bool';
DOUBLE: 'double';
ENTITY: 'entity';

entityspec: SHIP | ASTEROID | BULLET | ANY;
SHIP: 'ship';
ASTEROID: 'asteroid';
BULLET: 'bullet';
ANY: 'any';


// ------------------------------------------------------------------------
// --- Unary Operations ---------------------------------------------------
// ------------------------------------------------------------------------
GETRADIUS: 'getradius';
GETX: 'getx';
GETY: 'gety';
GETVX: 'getvx';
GETVY: 'getvy';
GETDIR: 'getdir';
SQRT: 'sqrt';
SIN: 'sin';
COS: 'cos';
NOT: '!';


// ------------------------------------------------------------------------
// --- Space Entity Actions -----------------------------------------------
// ------------------------------------------------------------------------
THRUSTON:  'thrust';
THRUSTOFF: 'thrust_off';
TURN:      'turn';
FIRE:      'fire'; 
SKIP:      'skip';
PRINT: 'print';


// ------------------------------------------------------------------------
// --- Control Flow -------------------------------------------------------
// ------------------------------------------------------------------------
IF: 'if';
THEN: 'then';
ELSE: 'else';
WHILE: 'while';
DO: 'do';
FOREACH: 'foreach';


// ------------------------------------------------------------------------
// --- Assignment and Arithmetics -----------------------------------------
// ------------------------------------------------------------------------
ASSIGN: ':=';

binop:  MUL | DIV | MOD | ADD | SUB |
        NEQ | EQ | LT | GT | LEQ | GEQ |
        AND | OR;
MUL: '*';
DIV: '/';
ADD: '+';
SUB: '-';
EQ:  '==';
NEQ: '!=';
LT:  '<';
GT:  '>';
LEQ: '<=';
GEQ: '>=';
AND: '&&';
OR:  '||';


// ------------------------------------------------------------------------
// --- Literals and Variables ---------------------------------------------
// ------------------------------------------------------------------------
NUMBER: INTEGER | FLOAT;
FLOAT: INTEGER '.' '0'..'9'+;
INTEGER: '0' | SIGN? '1'..'9' '0'..'9'*;
SIGN: (ADD | SUB);

IDENTIFIER: LETTER (LETTER | DIGIT | '_')*;
fragment LETTER: LOWER | UPPER;
fragment LOWER: 'a'..'z';
fragment UPPER: 'A'..'Z';
fragment DIGIT: '0'..'9';


// ------------------------------------------------------------------------
// --- Syntactical Ballast ------------------------------------------------
// ------------------------------------------------------------------------
LEFT_PAREN: '(';
RIGHT_PAREN: ')';
LEFT_BRACE: '{';
RIGHT_BRACE: '}';

SEMICOLON: ';';
COMMA: ',';

// Skip runs of newline, space and tab characters.
WHITESPACE: [ \t\r\n]+ -> skip;
 
// Single-line comments begin with //, are followed by any characters
// other than those in a newline, and are terminated by newline characters.
SINGLE_COMMENT: '//' ~('\r' | '\n')* NEWLINE -> skip;
fragment NEWLINE: ('\r'? '\n')+;

