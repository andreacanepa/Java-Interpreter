package parser.ast;

import visitor.Visitor;

public class Concat implements Exp{

	private final Exp left;
	private final Exp right;
	
	public Concat(Exp left, Exp right) {
		this.left = left;
		this.right = right;
	}

	public Exp getLeft()
	{
		return left;
	}
	
	public Exp getRight()
	{
		return right;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + left + "," + right + ")";
	}
	
	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitConcat(left, right);
	}
}