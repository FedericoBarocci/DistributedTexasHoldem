package breads_and_aces.registration.init.servable;

import java.util.Scanner;

import breads_and_aces._di.providers.GameRegistrarProvider;
import breads_and_aces.game.model.players.keeper.PlayersKeeper;
import breads_and_aces.registration.Game;
import breads_and_aces.services.rmi.game.keeper.GameServicesKeeper;
import breads_and_aces.services.rmi.utils.communicator.Communicator;
import breads_and_aces.utils.misc.InputUtils;
import breads_and_aces.utils.printer.Printer;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class RegistrationInitializerServableUsingShellInput extends AbstractRegistrationInitializerServable {
	
	@AssistedInject
	public RegistrationInitializerServableUsingShellInput(@Assisted String nodeId, GameRegistrarProvider gameRegistrarProvider,
			GameServicesKeeper gameServicesRegistry, PlayersKeeper playersRegistry,
			Communicator communicator, Game game,
			Printer printer) {
		super(nodeId, gameRegistrarProvider, gameServicesRegistry, playersRegistry, communicator, game,
				printer);
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
}
