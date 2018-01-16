package scanner_tokenizer;

import java.io.IOException;

public interface Tokenizer extends AutoCloseable {

	TokenType next() throws IOException, ScannerException;

	String tokenString();

	int intValue();

	boolean boolValue();

	TokenType tokenType();

	boolean hasNext();

	public void close() throws IOException;

	int intValue8();

}