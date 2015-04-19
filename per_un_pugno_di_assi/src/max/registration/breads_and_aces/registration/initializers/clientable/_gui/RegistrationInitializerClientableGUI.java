package breads_and_aces.registration.initializers.clientable._gui;

import java.util.concurrent.CountDownLatch;

import breads_and_aces.game.model.players.keeper.RegistrarPlayersKeeper;
import breads_and_aces.registration.initializers.clientable.AbstractRegistrationInitializerClientable;
import breads_and_aces.registration.initializers.servable.registrar.RegistrationResult;
import breads_and_aces.registration.model.NodeConnectionInfos;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class RegistrationInitializerClientableGUI extends AbstractRegistrationInitializerClientable {

	private WaiterGameStartGUI waiterGameStartGUI;
	private final CountDownLatch latch;

	@AssistedInject
	public RegistrationInitializerClientableGUI(@Assisted String initializingHostAddress, 
			@Assisted int initializingHostPort, 
			RegistrarPlayersKeeper playersRegistry
			//, WaiterGameStartGUI waiterGameStartGUI
//			, CountDownLatch latch
			) {
		super(initializingHostAddress, initializingHostPort, playersRegistry);
		this.latch = new CountDownLatch(1);
		this.waiterGameStartGUI = new WaiterGameStartGUI();
	}
	
	
	@Override
	public void initialize(NodeConnectionInfos nodeConnectionInfo, String playerId/*, CountDownLatch latch*/) {
		waiterGameStartGUI.setVisible(true);
		super.initialize(nodeConnectionInfo, playerId);
	}

	@Override
	protected void onError(String message) {
		waiterGameStartGUI.onError(message);
	}

	@Override
	protected void onAccepted(RegistrationResult registrationResult) {
		waiterGameStartGUI.onAccepted();
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onRejected(RegistrationResult registrationResult) {
		waiterGameStartGUI.onRejected(registrationResult);
	}
	
	@Override
	public void goFurther() {
		waiterGameStartGUI.dispose();
		waiterGameStartGUI = null;
//		super.goFurther();
		if (latch!=null) {
			latch.countDown();
//			waiterGameStartGUI.dispose();
//			waiterGameStartGUI = null;
		}
	}
}
