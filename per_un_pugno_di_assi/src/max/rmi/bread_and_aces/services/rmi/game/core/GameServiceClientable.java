package bread_and_aces.services.rmi.game.core;

import java.rmi.RemoteException;

import bread_and_aces.services.rmi.game.base._init.PlayersSynchronizar;

public interface GameServiceClientable extends GameService, PlayersSynchronizar {
	void receiveStartGame(String whoHasToken) throws RemoteException;
}