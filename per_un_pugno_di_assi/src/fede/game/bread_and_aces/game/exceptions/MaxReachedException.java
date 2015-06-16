package bread_and_aces.game.exceptions;

public class MaxReachedException extends Exception {

	private static final long serialVersionUID = 2354253577445277596L;

	public MaxReachedException() {
		super();
	}

	public MaxReachedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MaxReachedException(String message, Throwable cause) {
		super(message, cause);
	}

	public MaxReachedException(String message) {
		super(message);
	}

	public MaxReachedException(Throwable cause) {
		super(cause);
	}
}
