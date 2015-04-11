package breads_and_aces.registration.init.clientable.old_distinguish_abstract;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import breads_and_aces.registration.model.Player;
import breads_and_aces.services.rmi.game._init.PlayersRegistrar;
import breads_and_aces.services.rmi.game.utils.ServiceUtils;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class GameInitializerClientableUsingRMI extends AbstractGameInitializerClientable {

	@AssistedInject
	public GameInitializerClientableUsingRMI(@Assisted String initializingHostAddress, @Assisted int initializingHostPort, PlayersRegistry players) {
		super(initializingHostAddress, initializingHostPort, players);
	}
	
	@Override
	protected void registerNodeThenDo(Player player, String initializingHostAddress,
			int initializingHostPort) {
		try {
			PlayersRegistrar remoteService = (PlayersRegistrar) ServiceUtils.lookup(initializingHostAddress, initializingHostPort);
			boolean registered = remoteService.registerPlayer(player);
			
			if (registered)
				System.out.println("initializer confirmed my registration as new player.");
		} catch (MalformedURLException e) {
		} catch (NotBoundException e) {
			System.out.println("Something was wrong: remote host not responding. Exit.");
			System.exit(0);
		} catch(RemoteException e) {
			System.out.println("Something was wrong: remote host not responding. Exit.");
			System.exit(0);
		}
	}

}
