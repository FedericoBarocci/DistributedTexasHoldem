package bread_and_aces.utils.keepers;

import bread_and_aces.registration.initializers.servable.registrar.RegistrationResult;
import bread_and_aces.registration.model.NodeConnectionInfos;

public interface KeepersUtilDelegateForServable {
	
	/**
	 * @param playerId
	 * @return
	 */
	boolean contains(String playerId);
	
	/**
	 * register clients
	 * @param playerId
	 */
	void registerPlayer(String playerId);
	
	/**
	 * register servable itself
	 * @param playerId
	 * @param isMe
	 */
	void registerServable(String playerId);
	
	/**
	 * 
	 * @param nodeConnectionInfos
	 * @param playerId
	 * @return
	 */
	RegistrationResult registerClientable(NodeConnectionInfos nodeConnectionInfos, String playerId);
}
