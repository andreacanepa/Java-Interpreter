package parser.ast;

import static java.util.Objects.requireNonNull;

import visitor.Visitor;

public class If implements Stmt {
	private final Exp exp;
	private final StmtSeq block;
	private final StmtSeq block2;

	public If(Exp exp, StmtSeq block, StmtSeq block2) {
		this.exp = requireNonNull(exp);
		this.block = requireNonNull(block);
		this.block2 = requireNonNull(block2);

	}

	public Exp getExp() {
		return exp;
	}

	public StmtSeq getBlock() {
		return block;
	}

	public StmtSeq getBlock2() {
		return block2;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + exp + " " + "," + block + " " + "," + block2 + ")";
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitIf(exp, block, block2);
	}

}
