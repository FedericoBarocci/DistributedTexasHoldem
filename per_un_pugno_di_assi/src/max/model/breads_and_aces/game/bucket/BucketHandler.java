package breads_and_aces.game.bucket;

import java.rmi.RemoteException;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.model.players.keeper.PlayersKeeper;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.services.rmi.game.core.GameService;
import breads_and_aces.services.rmi.utils.communicator.Communicator;

@Singleton
public class BucketHandler {

	private final PlayersKeeper playersKeeper;
	private final Communicator communicator;
	private String currentMeId;
	
	private String tmpDummyString;
	
	@Inject
	public BucketHandler(PlayersKeeper playersKeeper, /*Me me,*/ Communicator communicator) {
		this.playersKeeper = playersKeeper;
//		this.meId = me.getId();
		this.communicator = communicator;
	}
	
	public void play(String meId, String dummyString) {
		this.currentMeId = meId;
		Player playerMe = playersKeeper.getPlayer(currentMeId);
		if (playerMe.hasBucket()) {
			System.out.println("\t said: "+dummyString);
//			communicator.toAll(Comparator::comparingDouble, dummyString, null);
			this.tmpDummyString = dummyString;
			communicator.toAll(meId, this::sayToAll);
			Player next = playersKeeper.getNext(currentMeId);
			communicator.toOne(this::passBucket, next.getName());
			playerMe.passBucket();
		}
	}
	private void sayToAll(GameService gameServiceExternalInjected) {
		try {
			gameServiceExternalInjected.echo(currentMeId, tmpDummyString);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}	
	private void passBucket(GameService gameServiceExternalInjected) throws RemoteException {
		gameServiceExternalInjected.receiveBucket();
	}
}
