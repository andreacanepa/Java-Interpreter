package parser.ast;

import visitor.Visitor;

public class Lth extends BinaryOp {
	public Lth(Exp left, Exp right) {
		super(left, right);
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitLth(left, right);
	}

	// public Type typecheck(GenEnvironment<Type> env) throws
	// TypecheckerException {
	// left.typecheck(env).checkEqual(INT);
	// right.typecheck(env).checkEqual(INT);
	// return BOOL;
	// }
}
