package parser;

import static scanner_tokenizer.TokenType.*;

import java.io.IOException;

import parser.ast.*;
import scanner_tokenizer.ScannerException;
import scanner_tokenizer.TokenType;
import scanner_tokenizer.Tokenizer;

public class StreamParser implements Parser {

	private final Tokenizer tokenizer;

	public StreamParser(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	@Override
	public Prog parseProg() throws IOException, ScannerException, ParserException {
		tokenizer.next();
		Prog prog = new ProgClass(parseStmtSeq());
		match(EOF);
		return prog;
	}

	private StmtSeq parseStmtSeq() throws IOException, ScannerException, ParserException {
		Stmt stmt = parseStmt();
		if (tokenizer.tokenType() == STMT_SEP) {
			tokenizer.next();
			return new MoreStmt(stmt, parseStmtSeq());
		}
		return new SingleStmt(stmt);
	}

	private ExpSeq parseExpSeq() throws IOException, ScannerException, ParserException {
		Exp exp = parseExp();
		if (tokenizer.tokenType() == EXP_SEP) {
			tokenizer.next();
			return new MoreExp(exp, parseExpSeq());
		}
		return new SingleExp(exp);
	}

	private Stmt parseStmt() throws IOException, ScannerException, ParserException {
		switch (tokenizer.tokenType()) {
		default:
			unexpectedTokenError();
		case PRINT:
			return parsePrintStmt();
		case VAR:
			return parseVarStmt();
		case IDENT:
			return parseAssignStmt();
		case FOR:
			return parseForEachStmt();
		case WHILE:
			return parseWhileStmt();
		case IF:
			return parseIfStmt();
		}
	}

	private PrintStmt parsePrintStmt() throws IOException, ScannerException, ParserException {
		consume(PRINT);
		return new PrintStmt(parseExp());
	}

	private VarStmt parseVarStmt() throws IOException, ScannerException, ParserException {
		consume(VAR);
		Ident ident = parseIdent();
		consume(ASSIGN);
		return new VarStmt(ident, parseExp());
	}

	private AssignStmt parseAssignStmt() throws IOException, ScannerException, ParserException {
		Ident ident = parseIdent();
		consume(ASSIGN);
		return new AssignStmt(ident, parseExp());
	}

	private ForEachStmt parseForEachStmt() throws IOException, ScannerException, ParserException {
		consume(FOR);
		Ident ident = parseIdent();
		consume(IN);
		Exp exp = parseExp();
		consume(START_BLOCK);
		StmtSeq stmts = parseStmtSeq();
		consume(END_BLOCK);
		return new ForEachStmt(ident, exp, stmts);
	}
	
	private While parseWhileStmt() throws IOException, ScannerException, ParserException {
		consume(WHILE);
		consume(OPEN_PAR);
		Exp exp = parseExp();
		consume(CLOSED_PAR);
		consume(START_BLOCK);
		StmtSeq stmts = parseStmtSeq();
		consume(END_BLOCK);
		return new While(exp, stmts);
	}
	
	private If parseIfStmt() throws IOException, ScannerException, ParserException {
		consume(IF);
		consume(OPEN_PAR);
		Exp exp = parseExp();
		consume(CLOSED_PAR);
		consume(START_BLOCK);
		StmtSeq thenStmt = parseStmtSeq();
		consume(END_BLOCK);
		consume(ELSE);
		consume(START_BLOCK);
		StmtSeq elseStmt = parseStmtSeq();
		consume(END_BLOCK);
		return new If(exp, thenStmt, elseStmt);
	}
	
	private Exp parseExp() throws IOException, ScannerException, ParserException {
		Exp exp = parseAnd();
		while (tokenizer.tokenType() == OR) {
			tokenizer.next();
			exp = new Or(exp, parseAnd());
		}
		return exp;
	}

	private Exp parseAnd() throws IOException, ScannerException, ParserException {
		Exp exp = parseEq();
		while (tokenizer.tokenType() == AND) {
			tokenizer.next();
			exp = new And(exp, parseEq());
		}
		return exp;
	}

	private Exp parseEq() throws IOException, ScannerException, ParserException {
		Exp exp = parseLth();
		while (tokenizer.tokenType() == EQ) {
			tokenizer.next();
			exp = new Eq(exp, parseLth());
		}
		return exp;
	}

	private Exp parseLth() throws IOException, ScannerException, ParserException {
		Exp exp = parseApp();
		while (tokenizer.tokenType() == LTH) {
			tokenizer.next();
			exp = new Lth(exp, parseApp());
		}
		return exp;
	}
	
	private Exp parseApp() throws IOException, ScannerException, ParserException {
		Exp exp = parseAddSub();
		while (tokenizer.tokenType() == CONCAT) {
			tokenizer.next();
			exp = new Concat(exp, parseAddSub());
		}
		return exp;
	}
	
	private Exp parseAddSub() throws IOException, ScannerException, ParserException {
		Exp exp = parseTimesDiv();

		while (tokenizer.tokenType() == PLUS || tokenizer.tokenType() == MINUS) {
			TokenType res = tokenizer.tokenType();
			tokenizer.next();
			if (res == PLUS)
				exp = new Add(exp, parseTimesDiv());
			else if (res == MINUS){
				exp = new Sub(exp, parseTimesDiv());
			}
		}
		
		return exp;
	}

	private Exp parseTimesDiv() throws IOException, ScannerException, ParserException {
		Exp exp = parseAtom();
		
		while (tokenizer.tokenType() == TIMES || tokenizer.tokenType() == DIV) {
			TokenType res = tokenizer.tokenType();
			tokenizer.next();
			if (res == TIMES)
				exp = new Mul(exp, parseAtom());
			else if (res == DIV)
				exp = new Div(exp, parseAtom());
		}
		
		return exp;
		
		
	}

	private Exp parseAtom() throws IOException, ScannerException, ParserException {
		switch (tokenizer.tokenType()) {
		default:
			unexpectedTokenError();
		case NUM:
			return parseNum();
		case NUM8:
			return parseNum_8();
		case BOOL:
			return parseBool();
		case IDENT:
			return parseIdent();
		case NOT:
			return parseNot();
		case MINUS:
			return parseMinus();
		case POP:
			return parsePop();
		case TOP:
			return parseTop();
		case PUSH:
			return parsePushStmt();
		case START_LIST:
			return parseList();
		case LENGTH:
			return parseLength();
		case PAIR:
			return parsePairStmt();
		case FIRST:
			return parseFst();
		case SECOND:
			return parseSnd();
		case OPEN_PAR:
			tokenizer.next();
			Exp exp = parseExp();
			consume(CLOSED_PAR);
			return exp;
		}
	}

	private IntLiteral parseNum() throws IOException, ScannerException, ParserException {
		int val = tokenizer.intValue();
		consume(NUM);
		return new IntLiteral(val);
	}
	
	private IntLiteral parseNum_8() throws IOException, ScannerException, ParserException {
		int val = tokenizer.intValue8();
		consume(NUM8);
		return new IntLiteral(val);
	}

	private PrimLiteral<Boolean> parseBool() throws IOException, ScannerException, ParserException {
		boolean val = tokenizer.boolValue();
		consume(BOOL);
		return new BoolLiteral(val);
	}

	private Ident parseIdent() throws IOException, ScannerException, ParserException {
		String name = tokenizer.tokenString();
		consume(IDENT);
		return new SimpleIdent(name);
	}

	private Not parseNot() throws IOException, ScannerException, ParserException {
		consume(NOT);
		return new Not(parseAtom());
	}

	private Sign parseMinus() throws IOException, ScannerException, ParserException {
		consume(MINUS);
		return new Sign(parseAtom());
	}

	private Top parseTop() throws IOException, ScannerException, ParserException {
		consume(TOP);
		Exp exp = parseAtom();
		return new Top(exp);
	}

	private Pop parsePop() throws IOException, ScannerException, ParserException {
		consume(POP);
		Exp exp = parseAtom();
		return new Pop(exp);
	}

	private Push parsePushStmt() throws IOException, ScannerException, ParserException {
		consume(PUSH);
		consume(OPEN_PAR);
		Exp left = parseExp();
		consume(EXP_SEP);
		Exp right = parseExp();
		consume(CLOSED_PAR);
		return new Push(left, right);
	}

	private ListLiteral parseList() throws IOException, ScannerException, ParserException {
		consume(START_LIST);
		ExpSeq exps = parseExpSeq();
		consume(END_LIST);
		return new ListLiteral(exps);
	}
	
	private Length parseLength() throws IOException, ScannerException, ParserException
	{
		consume(LENGTH);
		Exp exp = parseAtom();
		return new Length(exp);
	}
	
	private Pair parsePairStmt() throws IOException, ScannerException, ParserException
	{
		consume(PAIR);
		consume(OPEN_PAR);
		Exp left = parseExp();
		consume(EXP_SEP);
		Exp right = parseExp();
		consume(CLOSED_PAR);
		return new Pair(left, right);
	}
	
	private First parseFst() throws IOException, ScannerException, ParserException
	{
		consume(FIRST);
		Exp exp = parseAtom();
		return new First(exp);
	}
	
	private Second parseSnd() throws IOException, ScannerException, ParserException
	{
		consume(SECOND);
		Exp exp = parseAtom();
		return new Second(exp);
	}

	private void unexpectedTokenError() throws ParserException {
		throw new ParserException("Unexpected token " + tokenizer.tokenType() + "('" + tokenizer.tokenString() + "')");
	}

	private void match(TokenType expected) throws ParserException {
		final TokenType found = tokenizer.tokenType();
		if (found != expected)
			throw new ParserException(
					"Expecting " + expected + ", found " + found + "('" + tokenizer.tokenString() + "')");
	}

	private void consume(TokenType expected) throws IOException, ScannerException, ParserException {
		match(expected);
		tokenizer.next();
	}
}

