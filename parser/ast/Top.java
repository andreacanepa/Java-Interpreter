package parser.ast;

import visitor.Visitor;

public class Top extends UnaryOp {

	public Top(Exp exp) {
		super(exp);
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitTop(exp);
	}

	// @Override
	// public Type typecheck(GenEnvironment<Type> env) throws
	// TypecheckerException {
	// return exp.typecheck(env).checkList();
	// }

}
