package breads_and_aces.registration.init;

import breads_and_aces.registration.init.model.NodeConnectionInfos;

public interface RegistrationInitializer {
	void initialize(NodeConnectionInfos nodeConnectionInfo, String playerId);
}
