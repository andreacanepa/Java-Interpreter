package visitor;

import static java.util.Objects.requireNonNull;

public class Pair implements PairValue {
	private final Value first;
	private final Value second;
	
	Pair (Value first, Value second) {
		this.first = requireNonNull(first);
		this.second = requireNonNull(second);
	}
	
	public Value getFirst() {
		return first;
	}
	
	public Value getSecond() {
		return second;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof Pair))
			return false;
		Pair lt = (Pair) obj;
		return (first.equals(lt.getFirst()) && second.equals(lt.getSecond()));
	}
	
	@Override
	public int hashCode()
	{
		return 37 * first.hashCode() * second.hashCode();
	}
	
	@Override
	public String toString() {
		return "(" + first + "," + second + ")";
	}
 }
