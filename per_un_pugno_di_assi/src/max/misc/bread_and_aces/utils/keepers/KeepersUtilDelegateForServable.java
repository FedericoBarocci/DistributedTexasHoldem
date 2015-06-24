package bread_and_aces.utils.keepers;

import bread_and_aces.registration.initializers.servable.registrar.RegistrationResult;
import bread_and_aces.registration.model.NodeConnectionInfos;

public interface KeepersUtilDelegateForServable {
	boolean contains(String playerId);
	/**
	 * register clients
	 * @param playerId
	 */
	void registerClientablePlayer(String playerId);
	/**
	 * register servable itself
	 * @param playerId
	 * @param isMe
	 */
	void registerServablePlayer(String playerId);
	RegistrationResult registerClientableNodePlayerGameService(NodeConnectionInfos nodeConnectionInfos, String playerId);
}
