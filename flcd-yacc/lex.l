%{
  // Definitions
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "parser.tab.h"
int currentLine = 1;
%}

%option noyywrap

IDENTIFIER		[a-zA-Z][a-zA-Z0-9_]*
NUMBER_CONST	0|[+|-]?[1-9][0-9]*([.][0-9]*)?|[+|-]?0[.][0-9]*
STRING_CONST	[\"][a-zA-Z0-9_]*[\"]
CHAR_CONST		[\'][a-zA-Z0-9_][\']


%%

"int" {printf("Reserved word: %s\n", yytext);return INT;}
"float" {printf("Reserved word: %s\n", yytext);return FLOAT;}
"str" {printf("Reserved word: %s\n", yytext);return STRING;}
"if" {printf("Reserved word: %s\n", yytext);return IF;}
"else" {printf("Reserved word: %s\n", yytext);return ELSE;}
"print" {printf("Reserved word: %s\n", yytext);return PRINT;}
"read" {printf("Reserved word: %s\n", yytext);return READ;}

"+" {printf("Operator: %s\n", yytext);return plus;}
"-" {printf("Operator: %s\n", yytext);return minus;}
"/" {printf("Operator: %s\n", yytext);return division;}
"*" {printf("Operator: %s\n", yytext);return mul;}
"=" {printf("Operator: %s\n", yytext);return eq;}
">" {printf("Operator: %s\n", yytext);return more;}
"<" {printf("Operator: %s\n", yytext);return less;}
"<=" {printf("Operator: %s\n", yytext);return loe;}
">=" {printf("Operator: %s\n", yytext);return moe;}
"!=" {printf("Operator: %s\n", yytext);return different;}

"("			{printf("Separator: %s\n", yytext);return lrb;}
")"			{printf("Separator: %s\n", yytext);return rrb;}
"{"     {printf("Separator: %s\n", yytext);return lcb;}
"}"     {printf("Separator: %s\n", yytext);return rcb;}
";"			{printf("Separator: %s\n", yytext);return semicolon;}

{IDENTIFIER}		{printf("Identifier: %s\n", yytext);return IDENTIFIER;}
{NUMBER_CONST}  {printf("Identifier: %s\n", yytext);return NUMBER_CONST;}
{STRING_CONST}  {printf("Identifier: %s\n", yytext);return STRING_CONST;}
{CHAR_CONST}  {printf("Identifier: %s\n", yytext);return CHAR_CONST;}

[ \t]+  	{}
[\n]+ {currentLine++;}

[0-9_][a-zA-Z0-9_]*		{printf("Illegal identifier at line %d\n", currentLine); return -1;}
[+|-]0		{printf("Illegal numeric constant at line %d\n", currentLine); return -1;}
[+|-]?[0][0-9]*([.][0-9]*)?		{printf("Illegal numeric constant at line %d\n", currentLine); return -1;}
[\'][a-zA-Z0-9_]{2,}[\']|[\'][a-zA-Z0-9_]|[a-zA-Z0-9_][\']		{printf("Illegal character constant at line %d\n", currentLine); return -1;}
[\"][a-zA-Z0-9_]+|[a-zA-Z0-9_]+[\"]		{printf("Illegal string constant at line %d\n", currentLine); return -1;}

%%


