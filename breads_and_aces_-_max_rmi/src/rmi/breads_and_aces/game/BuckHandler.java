package breads_and_aces.game;

import java.rmi.RemoteException;

import javax.inject.Inject;

import breads_and_aces.game.model.Player;
import breads_and_aces.game.registry.PlayersShelf;
import breads_and_aces.main.Me;
import breads_and_aces.services.rmi.game.GameService;
import breads_and_aces.services.rmi.utils.Communicator;

public class BuckHandler {

	private final PlayersShelf playersShelf;
	private final Communicator communicator;
	private final String meId;
	
	@Inject
	public BuckHandler(PlayersShelf playersShelf, Me me, Communicator communicator) {
		this.playersShelf = playersShelf;
		this.meId = me.getId();
		this.communicator = communicator;
	}
	
	public void play(String dummyString) {
		if (playersShelf.getPlayer(meId).hasToken()) {
			communicator.toAll(this::sayToAll, dummyString, null);
			Player next = playersShelf.getNext(meId);
			communicator.toOne(this::passToken, next.getId());
		}
	}
	
	private <T1,T2> void sayToAll(GameService gameServiceExternalInjected, T1 arg, T2 nullArg) {
		try {
			gameServiceExternalInjected.echo(meId, (String)arg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void passToken(GameService gameServiceExternalInjected) throws RemoteException {
		gameServiceExternalInjected.receiveToken();
	}
}
