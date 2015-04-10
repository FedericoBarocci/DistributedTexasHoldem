package breads_and_aces.registration.initializers.servable._gui;

import java.awt.EventQueue;
import java.util.concurrent.CountDownLatch;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import breads_and_aces._di.providers.GameRegistrarProvider;
import breads_and_aces.game.Game;
import breads_and_aces.game.model.players.keeper.RegistrarPlayersKeeper;
import breads_and_aces.registration.initializers.servable.AbstractRegistrationInitializerServable;
import breads_and_aces.services.rmi.game.keeper.GameServicesKeeper;
import breads_and_aces.services.rmi.utils.communicator.Communicator;
import breads_and_aces.utils.printer.Printer;

public class RegistrationInitializerServableGUI extends AbstractRegistrationInitializerServable {

	private final AccepterPlayersGUIFactory accepterPlayersGUIFactory;

	@AssistedInject
	public RegistrationInitializerServableGUI(@Assisted String nodeId, 
			GameRegistrarProvider gameRegistrarProvider, GameServicesKeeper gameServicesRegistry,
			RegistrarPlayersKeeper playersKeeper, Communicator communicator, Game game, Printer printer, AccepterPlayersGUIFactory accepterPlayersGUIFactory) {
		super(nodeId, gameRegistrarProvider, gameServicesRegistry, playersKeeper, communicator, game, printer);
		this.accepterPlayersGUIFactory = accepterPlayersGUIFactory;
	}

	@Override
	protected void waitForRegistrationsClosingWhileAcceptPlayersThenStartGame() {
		CountDownLatch startLatch = new CountDownLatch(1);
//		accepterPlayersGUIFactory.create(startLatch);
		EventQueue.invokeLater(()->{
			AccepterPlayersGUI frame = 
//					new AccepterPlayersGUI(startLatch);
					accepterPlayersGUIFactory.create(startLatch);
			frame.setVisible(true);
		});
		try {
			startLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
