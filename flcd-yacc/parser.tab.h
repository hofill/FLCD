/* A Bison parser, made by GNU Bison 2.3.  */

/* Skeleton interface for Bison's Yacc-like parsers in C

   Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2, or (at your option)
   any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor,
   Boston, MA 02110-1301, USA.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     INT = 258,
     FLOAT = 259,
     LONG = 260,
     STRING = 261,
     WHILE = 262,
     IF = 263,
     ELSE = 264,
     FOR = 265,
     READ = 266,
     PRINT = 267,
     plus = 268,
     minus = 269,
     mul = 270,
     division = 271,
     mod = 272,
     eq = 273,
     equal = 274,
     loe = 275,
     moe = 276,
     different = 277,
     less = 278,
     more = 279,
     lrb = 280,
     rrb = 281,
     semicolon = 282,
     lcb = 283,
     rcb = 284,
     IDENTIFIER = 285,
     NUMBER_CONST = 286,
     STRING_CONST = 287,
     CHAR_CONST = 288
   };
#endif
/* Tokens.  */
#define INT 258
#define FLOAT 259
#define LONG 260
#define STRING 261
#define WHILE 262
#define IF 263
#define ELSE 264
#define FOR 265
#define READ 266
#define PRINT 267
#define plus 268
#define minus 269
#define mul 270
#define division 271
#define mod 272
#define eq 273
#define equal 274
#define loe 275
#define moe 276
#define different 277
#define less 278
#define more 279
#define lrb 280
#define rrb 281
#define semicolon 282
#define lcb 283
#define rcb 284
#define IDENTIFIER 285
#define NUMBER_CONST 286
#define STRING_CONST 287
#define CHAR_CONST 288




#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
# define YYSTYPE_IS_TRIVIAL 1
#endif

extern YYSTYPE yylval;

