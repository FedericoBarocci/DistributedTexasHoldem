package breads_and_aces.registration.initializers.servable._gui;

import java.awt.EventQueue;
import java.util.concurrent.CountDownLatch;

import breads_and_aces._di.providers.registration.initializers.servable.registrar.GameRegistrarProvider;
import breads_and_aces.game.Game;
import breads_and_aces.game.model.players.keeper.RegistrarPlayersKeeper;
import breads_and_aces.game.model.table.Table;
import breads_and_aces.registration.initializers.servable.AbstractRegistrationInitializerServable;
import breads_and_aces.services.rmi.game.keeper.GameServicesKeeper;
import breads_and_aces.services.rmi.utils.communicator.Communicator;
import breads_and_aces.utils.printer.Printer;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class RegistrationInitializerServableGUI extends
		AbstractRegistrationInitializerServable {

	private final AccepterPlayersGUIFactory accepterPlayersGUIFactory;

	@AssistedInject
	public RegistrationInitializerServableGUI(@Assisted String nodeId,
			GameRegistrarProvider gameRegistrarProvider,
			GameServicesKeeper gameServicesRegistry,
			RegistrarPlayersKeeper registrarPlayersKeeper, 
			Communicator communicator,
			Table table, Game game, Printer printer,
			AccepterPlayersGUIFactory accepterPlayersGUIFactory) {
		super(nodeId, gameRegistrarProvider,
				registrarPlayersKeeper,
				communicator, table, game, printer);
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
