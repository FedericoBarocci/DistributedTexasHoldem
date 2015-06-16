package bread_and_aces.game.exceptions;

public class SinglePlayerException extends Exception {

	private static final long serialVersionUID = 4496642253320186252L;

	public SinglePlayerException() {}

	public SinglePlayerException(String message) {
		super(message);
	}

	public SinglePlayerException(Throwable cause) {
		super(cause);
	}

	public SinglePlayerException(String message, Throwable cause) {
		super(message, cause);
	}

	public SinglePlayerException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
