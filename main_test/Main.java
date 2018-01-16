package main_test;

import static java.lang.System.err;


import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import parser.Parser;
import parser.ParserException;
import parser.StreamParser;
import parser.ast.Prog;
import scanner_tokenizer.ScannerException;
import scanner_tokenizer.StreamTokenizer;
import scanner_tokenizer.Tokenizer;
import visitor.Eval;
import visitor.TypeCheck;

public class Main {
	
	private static void start(InputStreamReader inputsr, PrintStream prints) throws IOException, ScannerException, ParserException {
		Tokenizer tokenizer = new StreamTokenizer(inputsr);
		Parser parser = new StreamParser(tokenizer);
		Prog prog = parser.parseProg();
		prog.accept(new TypeCheck());
		System.setOut(prints);
		prog.accept(new Eval());
		inputsr.close();
		prints.flush();
		prints.close();
	}
	
	public static void main(String[] args) {
		try {
			if(args.length == 0){
				start(new InputStreamReader(System.in), new PrintStream(System.out));
			} else if (args.length == 1){
				start(new FileReader(args[0]), new PrintStream(System.out));
			} else if (args[0].equals("-o") && args.length == 2){
				start(new InputStreamReader(System.in), new PrintStream(args[1]));
			} else if (args[0].equals("-o") && args.length == 3){
				start(new FileReader(args[2]), new PrintStream(args[1]));
			}
			
		} catch (ScannerException e) {
			String skipped = e.getSkipped();
			if (skipped != null)
				err.println("tokenizer error: " + e.getMessage() + e.getSkipped());
			else
				err.println(e.getMessage());
		} catch (Exception e) {
			if (e.getClass().getSimpleName().toString().equals("ParserException"))
				err.println("parsing error: "+e.getMessage());
			else
				err.println(e.getClass().getSimpleName().replace("E", "e") + ": " + e.getMessage());
			
		} catch (Throwable e) {
			err.println("Unexpected error: " + e.getMessage());
		}
	}
}









