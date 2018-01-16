package visitor;

public interface ListValue extends Value, Iterable<Value> {
	
	ListValue push(Value el);

	Value top();

	ListValue pop();

	int size();
	
	@Override
	default ListValue asList() {
		return this;
	}
}
