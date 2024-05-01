/**************************************************/
/************** SECTION 1: USER CODE **************/
/**************************************************/

package Model.Compiler;


import java_cup.runtime.*;
import java.util.ArrayList;
// import sym.*; // avoid, same package
import Model.Utils.CustomLogger;

/**************************************************/
/*************** SECTION 2: CONFIGS ***************/
/**************************************************/
%%


%class MatrixLexer
%type java_cup.runtime.Symbol
%cup
%column
%line
%full
%public


// DEFINE CUSTOM REGEX
ID = [a-zA-Z][a-zA-Z0-9_]*
NUMBER = -?\d+(\.\d+)?
IGNORE = [\n\t\r ]+
STRING = \".*?\"

// METHODS
%{
    // Java code that can be included in the generated lexer

    public void addError(String lexeme) {
        try {
            CustomLogger.getInstance().addLog(String.format("LEXICAL ERROR | Error at line %1$d column %2$d, lexeme %3$s", yyline+1, yycolumn+1, lexeme), true);
        } catch(Exception e) {
            CustomLogger.getInstance().addLog("Unable to save error at lexer class", true);
        }

    }

%}

%%


/**************************************************/
/************ SECTION 3: LEXICAL RULES ************/
/**************************************************/


/* IGNORE TOKENS  */
{IGNORE}      { /*ignore this line*/ }
/* Comments */
"//"(.)*\n    { /* Ignore */ }

// symbols
("[")         { return new Symbol(sym.LBRACKET, yyline+1, yycolumn+1); }
("]")         { return new Symbol(sym.RBRACKET, yyline+1, yycolumn+1); }
("(")         { return new Symbol(sym.LPARENTHESIS, yyline+1, yycolumn+1); }
(")")         { return new Symbol(sym.RPARENTHESIS, yyline+1, yycolumn+1); }
("+")         { return new Symbol(sym.PLUS, yyline+1, yycolumn+1); }
("*")         { return new Symbol(sym.TIMES, yyline+1, yycolumn+1); }
("-")         { return new Symbol(sym.MINUS, yyline+1, yycolumn+1); }
("/")         { return new Symbol(sym.DIVIDE, yyline+1, yycolumn+1); }
(",")         { return new Symbol(sym.COMMA, yyline+1, yycolumn+1); }
(";")         { return new Symbol(sym.SEMI_COLON, yyline+1, yycolumn+1); }
// reserved words
(MATRIX)      { return new Symbol(sym.MATRIX, yyline+1, yycolumn+1); }
(ARITH)       { return new Symbol(sym.ARITH, yyline+1, yycolumn+1); }
(INV)         { return new Symbol(sym.INVERSE, yyline+1, yycolumn+1); }
(DET)         { return new Symbol(sym.DETERMINANT, yyline+1, yycolumn+1); }
(RANK)        { return new Symbol(sym.RANK, yyline+1, yycolumn+1); }
(ENCRYPT)     { return new Symbol(sym.ENCRYPT, yyline+1, yycolumn+1); }
(DECRYPT)     { return new Symbol(sym.DECRYPT, yyline+1, yycolumn+1); }
(GAUSS)       { return new Symbol(sym.GAUSS, yyline+1, yycolumn+1); }
(JORDAN)      { return new Symbol(sym.JORDAN, yyline+1, yycolumn+1); }
(MARKOV)      { return new Symbol(sym.MARKOV, yyline+1, yycolumn+1); }
// data
{STRING}      { return new Symbol(sym.STRING, yyline+1, yycolumn+1, yytext()); }
{ID}          { return new Symbol(sym.IDENTIFIER, yyline+1, yycolumn+1, yytext()); }
{NUMBER}      { return new Symbol(sym.NUMBER, yyline+1, yycolumn+1, yytext()); }

/* Error catching */
[^]           { addError(yytext()); return new Symbol(sym.UNKNOWN, yyline+1, yycolumn+1); }