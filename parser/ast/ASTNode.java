package parser.ast;

import visitor.Visitor;

public interface ASTNode {
	<T> T accept(Visitor<T> visitor);
}
