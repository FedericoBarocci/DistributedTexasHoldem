package breads_and_aces.registration.initializers.servable._shell;

import java.util.Scanner;

import breads_and_aces._di.providers.registration.initializers.servable.registrar.GameRegistrarProvider;
import breads_and_aces.game.Game;
import breads_and_aces.game.model.players.keeper.RegistrarPlayersKeeper;
import breads_and_aces.registration.initializers.servable.AbstractRegistrationInitializerServable;
import breads_and_aces.services.rmi.game.keeper.GameServicesKeeper;
import breads_and_aces.services.rmi.utils.communicator.Communicator;
import breads_and_aces.utils.misc.InputUtils;
import breads_and_aces.utils.printer.Printer;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class RegistrationInitializerServableShell extends AbstractRegistrationInitializerServable {
	
	@AssistedInject
	public RegistrationInitializerServableShell(@Assisted String nodeId, 
			GameRegistrarProvider gameRegistrarProvider,
			GameServicesKeeper gameServicesRegistry, RegistrarPlayersKeeper playersRegistry,
			Communicator communicator, Game game,
//			CountDownLatch latch,
			Printer printer) {
		super(nodeId, gameRegistrarProvider, gameServicesRegistry, playersRegistry, communicator, game,
//				latch,
				printer );
	}

	// TODO real version will use GUI
	@Override
	protected void waitForRegistrationsClosingWhileAcceptPlayersThenStartGame() {
		Scanner scanner = InputUtils.getScanner();
		String next = "";
		while (!next.equals(START_GAME)) {
			next = scanner.next();
		}
	}
	
	private String START_GAME = "START";
}
