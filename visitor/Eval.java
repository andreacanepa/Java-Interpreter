package visitor;

import parser.ast.Exp;
import parser.ast.ExpSeq;
import parser.ast.Ident;
import parser.ast.SimpleIdent;
import parser.ast.Stmt;
import parser.ast.StmtSeq;

public class Eval implements Visitor<Value> {

	private final GenEnvironment<Value> env = new GenEnvironment<>();

	@Override
	public Value visitAdd(Exp left, Exp right) {
		return new IntValue(left.accept(this).asInt() + right.accept(this).asInt());
	}

	@Override
	public Value visitAnd(Exp left, Exp right) {
		return new BoolValue(left.accept(this).asBool() && right.accept(this).asBool());
	}

	@Override
	public Value visitAssignStmt(Ident ident, Exp exp) {
		env.update(ident, exp.accept(this));
		return null;
	}

	@Override
	public Value visitBoolLiteral(boolean value) {
		return new BoolValue(value);
	}

	@Override
	public Value visitEq(Exp left, Exp right) {
		return new BoolValue(left.accept(this).equals(right.accept(this)));
	}

	@Override
	public Value visitForEachStmt(Ident ident, Exp exp, StmtSeq block) {
		ListValue list = exp.accept(this).asList();
		for (Value val : list) {
			env.enterScope();
			env.newFresh(ident, val);
			block.accept(this);
			env.exitScope();
		}
		return null;
	}

	@Override
	public Value visitIntLiteral(int value) {
		return new IntValue(value);
	}

	@Override
	public Value visitListLiteral(ExpSeq exps) {
		return exps.accept(this);
	}

	@Override
	public Value visitLth(Exp left, Exp right) {
		return new BoolValue(left.accept(this).asInt() < right.accept(this).asInt());
	}

	@Override
	public Value visitMoreExp(Exp first, ExpSeq rest) {
		return new LinkedListValue(first.accept(this), rest.accept(this).asList());
	}

	@Override
	public Value visitMoreStmt(Stmt first, StmtSeq rest) {
		first.accept(this);
		rest.accept(this);
		return null;
	}

	@Override
	public Value visitMul(Exp left, Exp right) {
		return new IntValue(left.accept(this).asInt() * right.accept(this).asInt());
	}

	@Override
	public Value visitNot(Exp exp) {
		return new BoolValue(!exp.accept(this).asBool());
	}

	@Override
	public Value visitOr(Exp left, Exp right) {
		return new BoolValue(left.accept(this).asBool() || right.accept(this).asBool());
	}

	@Override
	public Value visitPop(Exp exp) {
		return exp.accept(this).asList().pop();
	}

	@Override
	public Value visitPrintStmt(Exp exp) {
		System.out.println(exp.accept(this));
		return null;
	}

	@Override
	public Value visitProg(StmtSeq stmtSeq) {
		stmtSeq.accept(this);
		return null;
	}

	@Override
	public Value visitPush(Exp left, Exp right) {
		Value el = left.accept(this);
		return right.accept(this).asList().push(el);
	}

	@Override
	public Value visitSign(Exp exp) {
		return new IntValue(-exp.accept(this).asInt());
	}

	@Override
	public Value visitIdent(String name) {
		return env.lookup(new SimpleIdent(name));
	}

	@Override
	public Value visitSingleExp(Exp exp) {
		return new LinkedListValue(exp.accept(this), new LinkedListValue());
	}

	@Override
	public Value visitSingleStmt(Stmt stmt) {
		stmt.accept(this);
		return null;
	}

	@Override
	public Value visitTop(Exp exp) {
		return exp.accept(this).asList().top();
	}

	@Override
	public Value visitVarStmt(Ident ident, Exp exp) {
		env.newFresh(ident, exp.accept(this));
		return null;
	}

	@Override
	public Value visitConcat(Exp left, Exp right) {
		LinkedListValue first = (LinkedListValue) left.accept(this).asList();
		LinkedListValue second = (LinkedListValue) right.accept(this).asList();
		LinkedListValue concatList = new LinkedListValue(first);
		
		for (Value v : second)
			concatList = (LinkedListValue) concatList.push(v);
		
		return concatList;
	}

	@Override
	public Value visitLength(Exp exp) {
		return new IntValue(exp.accept(this).asList().size());
	}

	@Override
	public Value visitFirst(Exp exp) {
		return exp.accept(this).asPair().getFirst();
	}

	@Override
	public Value visitSecond(Exp exp) {
		return exp.accept(this).asPair().getSecond();
	}

	@Override
	public Value visitWhile(Exp exp, StmtSeq block) {
		while (exp.accept(this).asBool()) {
			env.enterScope();
			block.accept(this);
			env.exitScope();
		}
		return null;
	}

	@Override
	public Value visitIf(Exp exp, StmtSeq block, StmtSeq block2) {
		if (exp.accept(this).asBool()) {
			env.enterScope();
			block.accept(this);
			env.exitScope();
		} else {
			env.enterScope();
			block2.accept(this);
			env.exitScope();
		}
		return null;
	}

	@Override
	public Value visitPair(Exp left, Exp right) {
		Value first = left.accept(this);
		Value second = right.accept(this);
		return new Pair(first, second);
	}

	@Override
	public Value visitSub(Exp left, Exp right) {
		return new IntValue(left.accept(this).asInt() - right.accept(this).asInt());

	}

	@Override
	public Value visitDiv(Exp left, Exp right) {
		return new IntValue(left.accept(this).asInt() / right.accept(this).asInt());
	}

}
