package parser.ast;

import visitor.Visitor;

public class MoreStmt extends More<Stmt, StmtSeq> implements StmtSeq {

	public MoreStmt(Stmt first, StmtSeq rest) {
		super(first, rest);
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitMoreStmt(first, rest);
	}

	// @Override
	// public void typecheck(GenEnvironment<Type> env) throws
	// TypecheckerException {
	// first.typecheck(env);
	// rest.typecheck(env);
	// }

}
