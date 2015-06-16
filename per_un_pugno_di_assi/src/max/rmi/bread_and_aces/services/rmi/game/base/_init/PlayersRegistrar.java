package bread_and_aces.services.rmi.game.base._init;

import java.rmi.Remote;
import java.rmi.RemoteException;

import bread_and_aces.registration.initializers.servable.registrar.RegistrationResult;
import bread_and_aces.registration.model.NodeConnectionInfos;

public interface PlayersRegistrar extends Remote {
	RegistrationResult registerPlayer(NodeConnectionInfos nodeConnectionInfo, String playerId) throws RemoteException;
}
