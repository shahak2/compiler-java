/***************************/
/* FILE NAME: LEX_FILE.lex */
/***************************/

/*************/
/* USER CODE */
/*************/
import java_cup.runtime.*;

/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%%

/************************************/
/* OPTIONS AND DECLARATIONS SECTION */
/************************************/
   
/*****************************************************/ 
/* Lexer is the name of the class JFlex will create. */
/* The code will be written to the file Lexer.java.  */
/*****************************************************/ 
%class Lexer

/********************************************************************/
/* The current line number can be accessed with the variable yyline */
/* and the current column number with the variable yycolumn.        */
/********************************************************************/
%line
%column

/*******************************************************************************/
/* Note that this has to be the EXACT same name of the class the CUP generates */
/*******************************************************************************/
%cupsym TokenNames

/******************************************************************/
/* CUP compatibility mode interfaces with a CUP generated parser. */
/******************************************************************/
%cup

/****************/
/* DECLARATIONS */
/****************/
/*****************************************************************************/   
/* Code between %{ and %}, both of which must be at the beginning of a line, */
/* will be copied verbatim (letter to letter) into the Lexer class code.     */
/* Here you declare member variables and functions that are used inside the  */
/* scanner actions.                                                          */  
/*****************************************************************************/   
%{
	/*********************************************************************************/
	/* Create a new java_cup.runtime.Symbol with information about the current token */
	/*********************************************************************************/
	private Symbol symbol(int type)               {return new Symbol(type, yyline, yycolumn);}
	private Symbol symbol(int type, Object value) {return new Symbol(type, yyline, yycolumn, value);}

	/*******************************************/
	/* Enable line number extraction from main */
	/*******************************************/
	public int getLine() { return yyline + 1; } 

	/**********************************************/
	/* Enable token position extraction from main */
	/**********************************************/
	public int getTokenStartPosition() { return yycolumn + 1; } 
	

	public int getCharPos() { return yycolumn;   } 
%}

/***********************/
/* MACRO DECALARATIONS */
/***********************/

///Not any of these characters
NotAllowed = [^0-9a-zA-Z\(\)\{\}\[\]\?!\+\-\*\/\.; \t\f\r\n]

Type_int=int
Type_string=string
Type_void = void
Nil = nil
Assign = :=
Array = array
Class = class
Extends = extends
Return = return
While = while
If = if
New = new


/* NO LEADING ZEROS */

Integer = 0 | [1-9][0-9]*
WrongInt = 0[0-9]+
String = \"[a-zA-Z]*\"
WrongString = \"[.]*


/* IDENTIFIER MUST START WITH A LETTER */
Id = [a-zA-Z]+[a-zA-Z0-9]*

/*Comments */
LineTerminator	= \r|\n|\r\n
WhiteSpace		= {LineTerminator} | [ \t\f]

InputCharacter = [0-9] | [a-zA-Z] | [\(\)\{\}\[\]] | [\?!] | [\+\-\*\/] | [\.;] | [ \t\f]

Comment = {TraditionalComment} | {EndOfLineComment}

TraditionalComment   = (\/\*)([a-zA-Z0-9\(\)\{\}\[\]\+\-\\\/ \t\f\r\n\?!\.;])*[*]*([a-zA-Z0-9\(\)\{\}\[\]\+\-\\\* \t\\r\n\?!\.;]|[a-zA-Z0-9\(\)\{\}\[\]\+\-\\ \t\f\r\n\?!\.;](\/)*)*(\*\/)
// Comment can be the last line of the file, without line terminator.
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?



WrongMultiLineComment = \/\* [^*]


/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%%

/************************************************************/
/* LEXER matches regular expressions to actions (Java code) */
/************************************************************/

/**************************************************************/
/* YYINITIAL is the state at which the lexer begins scanning. */
/* So these regular expressions will only be matched if the   */
/* scanner is in the start state YYINITIAL.                   */
/**************************************************************/

<YYINITIAL> {

<<EOF>>					{ return symbol(TokenNames.EOF);}



{LineTerminator}				{  }

{WhiteSpace}					{  }

{Comment}  					{  }

"("						{ return symbol(TokenNames.LPAREN);}

")"						{ return symbol(TokenNames.RPAREN);}

"["						{ return symbol(TokenNames.LBRACK);}

"]"						{ return symbol(TokenNames.RBRACK);}

"{"						{ return symbol(TokenNames.LBRACE);}

"}"						{ return symbol(TokenNames.RBRACE);}

{Nil}						{ return symbol(TokenNames.NIL);}

"+"						{ return symbol(TokenNames.PLUS);}

"-"						{ return symbol(TokenNames.MINUS);}

"*"						{ return symbol(TokenNames.TIMES);}

"/"						{ return symbol(TokenNames.DIVIDE);}

","						{ return symbol(TokenNames.COMMA);}

"."						{ return symbol(TokenNames.DOT);}

";"						{ return symbol(TokenNames.SEMICOLON);}

{Assign}					{ return symbol(TokenNames.ASSIGN);}

"="						{ return symbol(TokenNames.EQ);}

"<"						{ return symbol(TokenNames.LT);}

">"						{ return symbol(TokenNames.GT);}

{Array}					{ return symbol(TokenNames.ARRAY);}

{Class}					{ return symbol(TokenNames.CLASS);}

{Extends}					{ return symbol(TokenNames.EXTENDS);}

{Return}					{ return symbol(TokenNames.RETURN);}

{While}					{ return symbol(TokenNames.WHILE);}

{If}						{ return symbol(TokenNames.IF);}

{New}						{ return symbol(TokenNames.NEW);}

{Type_int}					{ return symbol(TokenNames.TYPE_INT);}

{Type_string}					{ return symbol(TokenNames.TYPE_STRING);}

{Type_void}					{ return symbol(TokenNames.TYPE_VOID);}

{Integer}					{ return symbol(TokenNames.INT, new Integer(yytext()));}

{String}					{ return symbol(TokenNames.STRING, new String(yytext()));}

{Id}						{ return symbol(TokenNames.ID,     new String( yytext()));}

{NotAllowed}					{ return symbol(TokenNames.ERROR); }

{WrongString}					{ return symbol(TokenNames.ERROR); }

{WrongInt}					{ return symbol(TokenNames.ERROR); }

{WrongMultiLineComment}  			{ return symbol(TokenNames.ERROR);}



}
