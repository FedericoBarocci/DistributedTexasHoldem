package bread_and_aces.utils.keepers;

import bread_and_aces.registration.initializers.servable.registrar.RegistrationResult;
import bread_and_aces.registration.model.NodeConnectionInfos;

public interface KeepersUtilDelegateForServable {
	boolean contains(String playerId);
	void registerPlayer(String playerId);
	void registerPlayer(String playerId, boolean isMe);
	RegistrationResult registerClientableNodePlayerGameService(NodeConnectionInfos nodeConnectionInfos, String playerId);
}
