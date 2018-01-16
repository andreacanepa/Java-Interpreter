package visitor;

public interface PairValue extends Value {
	Value getFirst();
	
	Value getSecond();
	
	@Override
	default PairValue asPair() {
		return this;
	}
}
