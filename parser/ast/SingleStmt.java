package parser.ast;

import visitor.Visitor;

public class SingleStmt extends Single<Stmt> implements StmtSeq {

	public SingleStmt(Stmt single) {
		super(single);
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitSingleStmt(single);
	}

	// @Override
	// public void typecheck(GenEnvironment<Type> env) throws
	// TypecheckerException {
	// single.typecheck(env);
	// }

}
