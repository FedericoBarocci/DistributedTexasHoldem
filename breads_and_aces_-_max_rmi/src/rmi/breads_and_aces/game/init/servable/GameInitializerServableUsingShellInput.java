package breads_and_aces.game.init.servable;

import java.util.Scanner;

import javax.inject.Inject;

import breads_and_aces._di.providers.GameRegistrarProvider;
import breads_and_aces.game.Game;
import breads_and_aces.game.registry.PlayersShelf;
import breads_and_aces.node.NodesConnectionInfosShelf;
import breads_and_aces.services.rmi.GameServicesShelf;
import breads_and_aces.services.rmi.utils.Communicator;
import breads_and_aces.utils.misc.InputUtils;
import breads_and_aces.utils.printer.Printer;

public class GameInitializerServableUsingShellInput extends AbstractGameInitializerServable {
	
	@Inject
	public GameInitializerServableUsingShellInput(GameRegistrarProvider gameRegistrarProvider,
			GameServicesShelf gameServicesRegistry, PlayersShelf playersRegistry,
			NodesConnectionInfosShelf nodesConnectionInfosRegistry, Communicator communicator, Game game,
			Printer printer) {
		super(gameRegistrarProvider, gameServicesRegistry, playersRegistry, nodesConnectionInfosRegistry, communicator, game,
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
