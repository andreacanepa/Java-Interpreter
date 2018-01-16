package parser.ast;

import static java.util.Objects.requireNonNull;

import visitor.Visitor;

public class While implements Stmt {
	private final Exp exp;
	private final StmtSeq block;

	public While(Exp exp, StmtSeq block) {
		this.exp = requireNonNull(exp);
		this.block = requireNonNull(block);
	}

	public Exp getExp() {
		return exp;
	}

	public StmtSeq getBlock() {
		return block;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + exp + "" + "," + block + ")";
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitWhile(exp, block);
	}

}
