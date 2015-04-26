package breads_and_aces.game.exceptions;

public class DrawException extends Exception {

	private static final long serialVersionUID = 2038601318977641928L;

	public DrawException() {
	}

	public DrawException(String message) {
		super(message);
	}

	public DrawException(Throwable cause) {
		super(cause);
	}

	public DrawException(String message, Throwable cause) {
		super(message, cause);
	}

	public DrawException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
