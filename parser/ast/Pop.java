package parser.ast;

import visitor.Visitor;

public class Pop extends UnaryOp {

	public Pop(Exp exp) {
		super(exp);
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitPop(exp);
	}

	// @Override
	// public Type typecheck(GenEnvironment<Type> env) throws
	// TypecheckerException {
	// Type ty = exp.typecheck(env);
	// ty.checkList();
	// return ty;
	// }
}
