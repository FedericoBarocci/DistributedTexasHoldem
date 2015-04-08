package it.unibo.cs.sd.poker.game.core;

public class NegativeIntegerException extends Exception {

	public NegativeIntegerException() {
		super();
	}

	public NegativeIntegerException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NegativeIntegerException(String message, Throwable cause) {
		super(message, cause);
	}

	public NegativeIntegerException(String message) {
		super(message);
	}

	public NegativeIntegerException(Throwable cause) {
		super(cause);
	}

	private static final long serialVersionUID = 7411206596394364473L;

}
