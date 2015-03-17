package breads_and_aces.node.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

public class ServerRunnable implements Runnable {
	
	private final ServerAsServiceContainer server;

	public ServerRunnable(ServerAsServiceContainer serverAsServiceContainer) throws RemoteException, MalformedURLException, IOException {
		this.server = serverAsServiceContainer;
	}

	@Override
	public void run() {
		try {
			server.listen();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
	}
}
