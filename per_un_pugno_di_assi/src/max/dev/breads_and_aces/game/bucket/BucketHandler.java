package breads_and_aces.game.bucket;

import java.rmi.RemoteException;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.services.rmi.game.core.GameService;
import breads_and_aces.services.rmi.utils.communicator.Communicator;

@Singleton
public class BucketHandler {

	private final GamePlayersKeeper playersKeeper;
	private final Communicator communicator;
	private String currentMyId;
	
	private String tmpDummyString;
	
	@Inject
	public BucketHandler(GamePlayersKeeper playersKeeper, /*Me me,*/ Communicator communicator) {
		this.playersKeeper = playersKeeper;
//		this.meId = me.getId();
		this.communicator = communicator;
	}
	
	public void play(String meId, String dummyString) {
		this.currentMyId = meId;
		Player playerMe = playersKeeper.getPlayer(currentMyId);
		if (playerMe.hasToken()) {
			System.out.println("\t said: "+dummyString);
//			communicator.toAll(Comparator::comparingDouble, dummyString, null);
			this.tmpDummyString = dummyString;
			communicator.toAll(meId, this::sayToAll);
			Player next = playersKeeper.getNext(currentMyId);
			communicator.toOne(this::passToken, next.getName());
			playerMe.sendToken();
		}
	}
	
	private void sayToAll(GameService gameServiceExternalInjected) {
		try {
			gameServiceExternalInjected.echo(currentMyId, tmpDummyString);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	/*
	private void sayToAllIHaveToken(GameService gameServiceExternalInjected) {
		try {
			gameServiceExternalInjected.sayIHaveToken(this.currentMyId, currentMyId);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}*/
	
	private void passToken(GameService gameServiceExternalInjected) throws RemoteException {
		gameServiceExternalInjected.receiveBucket();
		//communicator.toAll(this.currentMyId, this::sayToAllIHaveToken);
	}
}
