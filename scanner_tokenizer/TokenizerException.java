package scanner_tokenizer;

import parser.ParserException;

public class TokenizerException extends ParserException {

	public TokenizerException() {
	}

	public TokenizerException(String message) {
		super(message);
	}

}
