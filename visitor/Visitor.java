package visitor;

import parser.ast.Exp;
import parser.ast.ExpSeq;
import parser.ast.Ident;
import parser.ast.Stmt;
import parser.ast.StmtSeq;

public interface Visitor<T> {
	T visitAdd(Exp left, Exp right);

	T visitAnd(Exp left, Exp right);

	T visitAssignStmt(Ident ident, Exp exp);

	T visitBoolLiteral(boolean value);

	T visitEq(Exp left, Exp right);

	T visitForEachStmt(Ident ident, Exp exp, StmtSeq block);

	T visitIntLiteral(int value);

	T visitListLiteral(ExpSeq exps);

	T visitLth(Exp left, Exp right);

	T visitMoreExp(Exp first, ExpSeq rest);

	T visitMoreStmt(Stmt first, StmtSeq rest);

	T visitMul(Exp left, Exp right);

	T visitNot(Exp exp);

	T visitOr(Exp left, Exp right);

	T visitPop(Exp exp);

	T visitPrintStmt(Exp exp);

	T visitProg(StmtSeq stmtSeq);

	T visitPush(Exp left, Exp right);

	T visitSign(Exp exp);

	T visitIdent(String name);

	T visitSingleExp(Exp exp);

	T visitSingleStmt(Stmt stmt);

	T visitTop(Exp exp);

	T visitVarStmt(Ident ident, Exp exp);

	T visitConcat(Exp left, Exp right);

	T visitLength(Exp exp);

	T visitFirst(Exp exp);

	T visitSecond(Exp exp);

	T visitWhile(Exp exp, StmtSeq block);

	T visitIf(Exp exp, StmtSeq block, StmtSeq block2);

	T visitPair(Exp left, Exp right);

	T visitSub(Exp left, Exp right);

	T visitDiv(Exp left, Exp right);
}
