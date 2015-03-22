package breads_and_aces.game.init.servable;

import java.util.Scanner;

import javax.inject.Inject;

import breads_and_aces._di.providers.GameRegistrarProvider;
import breads_and_aces.game.Game;
import breads_and_aces.game.registry.PlayersRegistry;
import breads_and_aces.node.NodesConnectionInfosRegistry;
import breads_and_aces.services.rmi.NodesGameServiceRegistry;
import breads_and_aces.utils.misc.InputUtils;
import breads_and_aces.utils.printer.Printer;

public class GameInitializerServableUsingShellInput extends AbstractGameInitializerServable {
	
	@Inject
	public GameInitializerServableUsingShellInput(GameRegistrarProvider gameRegistrarProvider, NodesGameServiceRegistry nodesGameServiceRegistry, Game game, PlayersRegistry playersRegistry, NodesConnectionInfosRegistry nodesConnectionInfosRegistry, Printer printer) {
		super(gameRegistrarProvider, nodesGameServiceRegistry, game, playersRegistry, nodesConnectionInfosRegistry, printer);
	}

	// TODO real version will use GUI
	@Override
	protected void waitForPlayersAndStartGame() {
		Scanner scanner = InputUtils.getScanner();
		String next = "";
		while (!next.equals(START_GAME)) {
			next = scanner.next();
		}
	}
}
