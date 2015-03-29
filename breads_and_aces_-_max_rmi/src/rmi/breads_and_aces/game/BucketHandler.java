package breads_and_aces.game;

import java.rmi.RemoteException;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.model.Player;
import breads_and_aces.game.registry.PlayersShelf;
import breads_and_aces.main.Me;
import breads_and_aces.services.rmi.game.GameService;
import breads_and_aces.services.rmi.utils.Communicator;

@Singleton
public class BucketHandler {

	private final PlayersShelf playersShelf;
	private final Communicator communicator;
	private final String meId;
	
	private String tmpDummyString;
	
	@Inject
	public BucketHandler(PlayersShelf playersShelf, Me me, Communicator communicator) {
		this.playersShelf = playersShelf;
		this.meId = me.getId();
		this.communicator = communicator;
	}
	
	public void play(String dummyString) {
		Player playerMe = playersShelf.getPlayer(meId);
		if (playerMe.hasBucket()) {
			System.out.println("\t said: "+dummyString);
//			communicator.toAll(Comparator::comparingDouble, dummyString, null);
			this.tmpDummyString = dummyString;
			communicator.toAll(this::sayToAll);
			Player next = playersShelf.getNext(meId);
			communicator.toOne(this::passBucket, next.getId());
			playerMe.passBucket();
		}
	}
	private void sayToAll(GameService gameServiceExternalInjected) {
		try {
			gameServiceExternalInjected.echo(meId, tmpDummyString);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}	
	private void passBucket(GameService gameServiceExternalInjected) throws RemoteException {
		gameServiceExternalInjected.receiveBucket();
	}
}
