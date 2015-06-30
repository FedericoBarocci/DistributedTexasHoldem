package bread_and_aces.registration.initializers.servable.registrar;

import java.io.Serializable;

import bread_and_aces.utils.DevPrinter;

public class RegistrationResult implements Serializable {

	private static final long serialVersionUID = -8844580012053989866L;
	
//	private final boolean accepted;
	private final RegistrationStatus registrationStatus;

	private String playerId;

	private String msg;
	
	public RegistrationResult(RegistrationStatus cause) {
//		this.accepted = accepted;
		this.registrationStatus = cause;
	}
	public RegistrationResult(RegistrationStatus status, String playerId) {
//		this.accepted = accepted;
		this.registrationStatus = status;
		this.playerId = playerId;
		DevPrinter.println(playerId);
	}
	/*public RegistrationResult(RegistrationStatus status, String msg) {
		this.registrationStatus = status;
		this.msg = msg;
	}*/

	public final boolean isAccepted() {
		return registrationStatus.equals(RegistrationStatus.OK) ? true : false;
	}

	public final RegistrationStatus getRegistrationStatus() {
		return registrationStatus;
	}
	
	public String getPlayerId() {
		return playerId;
	}
	
	public enum RegistrationStatus {
		EXISTING {
			public String toString() {
				return MESSAGE_EXISTING;
			}
		},
		ERROR {
			public String toString() {
				return MESSAGE_ERROR;
			}
		}, 
		OK {
			public String toString() {
				return MESSAGE_OK;
			}
		}, 
		GAME_STARTED {
			public String toString() {
				return MESSAGE_GAME_STARTED;
			}
		};
		
		abstract public String toString();
		
		private static final String MESSAGE_EXISTING = "Existing user with same name";
		private static final String MESSAGE_ERROR = "Error";
		private static final String MESSAGE_OK = "Ok";
		private static final String MESSAGE_GAME_STARTED = "Game started";
	}

	public void setErrorMessage(String msg) {
		this.msg = msg;		
	}
	
	public String getErrorMsg() {
		return msg;
	}

	
}
