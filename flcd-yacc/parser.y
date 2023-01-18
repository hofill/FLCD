%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
int yylex(void);
int yyerror(char *s);
int main(int argc, char **argv);

#define YYDEBUG 1

%}

%token INT
%token FLOAT
%token LONG
%token STRING
%token WHILE
%token IF
%token ELSE
%token FOR
%token READ
%token PRINT

%token plus
%token minus
%token mul
%token division
%token mod 
%token eq
%token equal
%token loe
%token moe
%token different
%token less
%token more

%token lrb
%token rrb
%token semicolon
%token lcb
%token rcb

%token IDENTIFIER
%token NUMBER_CONST
%token STRING_CONST
%token CHAR_CONST

%start program

%%
program : declaration_list statements
declaration_list : declaration declaration_list | /*Empty*/
declaration : IDENTIFIER var_type equal_expression semicolon
equal_expression : eq expression | /*Empty*/
var_type : INT | FLOAT | LONG | STRING
expression : term sign_and_expression
sign_and_expression : sign expression | /*Empty*/
sign : plus | minus | mul | division | mod
term : IDENTIFIER | constant
constant : NUMBER_CONST | STRING_CONST | CHAR_CONST
statements : statement statements | /*Empty*/
statement : simple_stmt | struct_stmt
simple_stmt : assignment_stmt | input_output_stmt
struct_stmt : if_stmt | while_stmt
assignment_stmt : IDENTIFIER eq expression semicolon
input_output_stmt : READ lrb term rrb semicolon | PRINT lrb term rrb semicolon
if_stmt : IF lrb condition rrb lcb statements rcb else_stmt
else_stmt : ELSE lcb statements rcb | /*Empty*/
while_stmt : WHILE lrb condition rrb lcb statements rcb
condition : expression relation expression
relation : equal | different | less | more | loe | moe
%%


yyerror(char *s)
{	
	printf("%s\n",s);
}


extern FILE *yyin;

main(int argc, char **argv)
{
	if(argc>1) yyin = fopen(argv[1],"r");
	if(argc>2 && !strcmp(argv[2],"-d")) yydebug: 1;
	if(!yyparse()) fprintf(stderr, "\tProgram is syntactically correct.\n");
}
