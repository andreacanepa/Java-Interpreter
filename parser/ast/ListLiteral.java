package parser.ast;

import static java.util.Objects.requireNonNull;

import visitor.Visitor;

public class ListLiteral implements Exp {
	private final ExpSeq exps;

	public ListLiteral(ExpSeq exps) {
		this.exps = requireNonNull(exps);
	}

	public ExpSeq getExps() {
		return exps;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + exps + ")";
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitListLiteral(exps);
	}
	
//	@Override
//	public ListType typecheck(GenEnvironment<Type> env) throws TypecheckerException {
//		return new ListType(exps.typecheck(env));
//	}
}
