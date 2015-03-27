package breads_and_aces.services.rmi.game._init;

import java.rmi.Remote;
import java.rmi.RemoteException;

import breads_and_aces.game.init.servable.registrar.result.RegistrationResult;
import breads_and_aces.node.model.NodeConnectionInfos;

public interface PlayersRegistrar extends Remote {
	RegistrationResult registerPlayer(NodeConnectionInfos nodeConnectionInfo, String playerId) throws RemoteException;
}
