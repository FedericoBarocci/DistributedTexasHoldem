package breads_and_aces.services.rmi.utils.communicator;

public class UnicastException extends Exception {

	private static final long serialVersionUID = 5707585378793272430L;

	public UnicastException() {
	}

	public UnicastException(String message) {
		super(message);
	}

	public UnicastException(Throwable cause) {
		super(cause);
	}

	public UnicastException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnicastException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
