package breads_and_aces.registration.initializers.servable.registrar;

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
		EXISTING {
			public String toString() {
				return c1;
			}
		},
		ERROR {
			public String toString() {
				return c2;
			}
		}, 
		OK {
			public String toString() {
				return c3;
			}
		}, 
		GAME_STARTED {
			public String toString() {
				return c4;
			}
		};
		
		abstract public String toString();
		
		private static final String c1 = "Existing";
		private static final String c2 = "Error";
		private static final String c3 = "Ok";
		private static final String c4 = "Game started";
	}
}
