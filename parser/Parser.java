package parser;

import java.io.IOException;

import parser.ast.Prog;
import scanner_tokenizer.ScannerException;

public interface Parser {

	Prog parseProg() throws IOException, ScannerException, ParserException;

}