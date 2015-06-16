package bread_and_aces.registration.initializers.servable._gui;

import java.awt.EventQueue;
import java.util.concurrent.CountDownLatch;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import bread_and_aces._di.providers.registration.initializers.servable.registrar.GameRegistrarProvider;
import bread_and_aces.game.Game;
import bread_and_aces.game.model.players.keeper.RegistrarPlayersKeeper;
import bread_and_aces.game.model.table.Table;
import bread_and_aces.registration.initializers.servable.AbstractRegistrationInitializerServable;
import bread_and_aces.services.rmi.game.keeper.GameServicesKeeper;
import bread_and_aces.services.rmi.utils.communicator.Communicator;
import bread_and_aces.services.rmi.utils.crashhandler.CrashHandler;
import bread_and_aces.utils.printer.Printer;

public class RegistrationInitializerServableGUI extends
		AbstractRegistrationInitializerServable {

	private final AccepterPlayersGUIFactory accepterPlayersGUIFactory;

	@AssistedInject
	public RegistrationInitializerServableGUI(@Assisted String nodeId,
			GameRegistrarProvider gameRegistrarProvider,
			GameServicesKeeper gameServicesRegistry,
			RegistrarPlayersKeeper registrarPlayersKeeper, 
			Communicator communicator,
			Table table, Game game, CrashHandler crashHandler, Printer printer,
			AccepterPlayersGUIFactory accepterPlayersGUIFactory) {
		super(nodeId, gameRegistrarProvider,
				registrarPlayersKeeper,
				communicator, table, game, crashHandler, printer);
		this.accepterPlayersGUIFactory = accepterPlayersGUIFactory;
	}

	@Override
	protected void waitForRegistrationsClosingWhileAcceptPlayersThenStartGame() {
		CountDownLatch startLatch = new CountDownLatch(1);
		
		EventQueue.invokeLater(() -> {
			accepterPlayersGUIFactory.create(startLatch)/*.setVisible(true)*/;
		});
		
		try {
			startLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
