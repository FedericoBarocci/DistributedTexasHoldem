package breads_and_aces.registration.initializers;

import breads_and_aces.registration.model.NodeConnectionInfos;

public interface RegistrationInitializer {
	void initialize(NodeConnectionInfos nodeConnectionInfo, String playerId/*, CountDownLatch latch*/);
}
