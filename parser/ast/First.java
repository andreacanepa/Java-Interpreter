package parser.ast;

import visitor.Visitor;

public class First extends UnaryOp{

	public First(Exp exp) {
		super(exp);
	}
	

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitFirst(exp);
	}
}
