import java.io.*;
import java.io.PrintWriter;

import java_cup.runtime.Symbol;

import java.lang.Math;


public class Main
{

	static public Boolean intInRange(int i){
		int result = (int)Math.pow(2, 15);
		if (i <= result - 1){
			return true;
		}
		return false;
	}

	static public void main(String argv[])
	{
		Lexer l;
		Symbol s;
		FileReader file_reader;
		PrintWriter file_writer;
		String inputFilename = argv[0];
		String outputFilename = argv[1];

		Boolean error = false;

		String[] mapping = {"EOF",
				"LPAREN",
				"RPAREN",
				"LBRACK",
				"RBRACK",
				"LBRACE",
				"RBRACE",
				"NIL"   ,
				"PLUS"  ,
				"MINUS" ,
				"TIMES" ,
				"DIVIDE",
				"COMMA" ,
				"DOT",
				"SEMICOLON",
				"TYPE_INT",
				"ASSIGN",
				"EQ",
				"LT",
				"GT",
				"ARRAY",
				"CLASS",
				"EXTENDS",
				"RETURN",
				"WHILE",
				"IF",
				"NEW",
				"INT",
				"STRING",
				"ID",
				"TYPE_STRING",
				"ERROR",
				"IGNORE"
		};

		try
		{
			/********************************/
			/* [1] Initialize a file reader */
			/********************************/
			file_reader = new FileReader(inputFilename);

			/********************************/
			/* [2] Initialize a file writer */
			/********************************/

			file_writer = new PrintWriter(outputFilename);

			/******************************/
			/* [3] Initialize a new lexer */
			/******************************/
			l = new Lexer(file_reader);

			/***********************/
			/* [4] Read next token */
			/***********************/
			s = l.next_token();

			/********************************/
			/* [5] Main reading tokens loop */
			/********************************/
			while (s.sym != TokenNames.EOF)
			{
				if (mapping[s.sym] == "IGNORE"){
					s = l.next_token();
					continue;
				}

				if(mapping[s.sym] == "ERROR"){
					error = true;
					break;
				}


				System.out.print(mapping[s.sym]);
				file_writer.print(mapping[s.sym]);


				if (mapping[s.sym] == "INT" || mapping[s.sym] == "STRING" || mapping[s.sym] == "ID") {


					System.out.print("(");
					file_writer.print("(");


					if (mapping[s.sym] == "INT") {

						int x = (int) s.value;
						if (!intInRange(x)) {
							error = true;
							break;
						}
					}
					System.out.print(s.value);
					file_writer.print(s.value);


					System.out.print(")");
					file_writer.print(")");

				}

				/************************/
				/* [6] Print to console */
				/************************/
				/*********************/
				/* [7] Print to file */
				/*********************/

				System.out.print("[");
				file_writer.print("[");

				System.out.print(l.getLine());
				file_writer.print(l.getLine());

				System.out.print(",");
				file_writer.print(",");


				System.out.print(l.getTokenStartPosition());
				file_writer.print(l.getTokenStartPosition());

				System.out.print("]");
				file_writer.print("]");

				System.out.print("\n");
				file_writer.print("\n");

				/***********************/
				/* [8] Read next token */
				/***********************/
				s = l.next_token();
			}

			/******************************/
			/* [9] Close lexer input file */
			/******************************/
			l.yyclose();

			/**************************/
			/* [10] Close output file */
			/**************************/
			file_writer.close();

			if (error){
				file_writer = new PrintWriter(new FileOutputStream(outputFilename, false));
				System.out.print("ERROR");
				file_writer.print("ERROR");

				file_writer.close();
			}
		}

		catch (Exception e)
		{
			System.out.print("ERROR");
			try
			{
				file_writer = new PrintWriter(new FileOutputStream(outputFilename, false));
				System.out.print("ERROR");
				file_writer.print("ERROR");
				file_writer.close();
			}

			catch (Exception er){

			}

		}
	}
}


