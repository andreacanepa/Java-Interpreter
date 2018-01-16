package visitor;

import static java.util.Objects.requireNonNull;

public class PairType implements Type {

	private final Type left;
	private final Type right;
	public final static String PAIR = "PAIR";
	
	public PairType(Type left, Type right) {
		this.left = requireNonNull(left);
		this.right = requireNonNull(right);
	}
	
	public Type getLeft()
	{
		return this.left;
	}
	
	public Type getRight()
	{
		return this.right;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof PairType))
			return false;
		PairType lt = (PairType) obj;
		return (left.equals(lt.getLeft()) && right.equals(lt.getRight()));
	}
	
	@Override
	public int hashCode()
	{
		return 20 * left.hashCode() * right.hashCode();
	}

	@Override
	public String toString()
	{
		return "(" + left + "*" + right + ")"; //per testing
	}
}
