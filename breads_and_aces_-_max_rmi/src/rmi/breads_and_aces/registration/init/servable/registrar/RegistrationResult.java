package breads_and_aces.registration.init.servable.registrar;

import java.io.Serializable;

public class RegistrationResult implements Serializable {

	private static final long serialVersionUID = -8844580012053989866L;
	private final boolean accepted;
	private final Cause cause;
	
	public RegistrationResult(boolean accepted, Cause cause) {
		this.accepted = accepted;
		this.cause = cause;
	}

	public final boolean isAccepted() {
		return accepted;
	}

	public final Cause getCause() {
		return cause;
	}
	
	public enum Cause {
		EXISTING,
		ERROR, 
		OK, 
		GAME_STARTED
	}
}
