package breads_and_aces.game.exceptions;

public class NegativeIntegerException extends Exception {

	private static final long serialVersionUID = 7411206596394364473L;
	
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
}
