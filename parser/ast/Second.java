package parser.ast;

import visitor.Visitor;

public class Second extends UnaryOp{

	public Second(Exp exp) {
		super(exp);
	}
	
	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitSecond(exp);
	}
}
