package breads_and_aces.rmi.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

public class ServerRunnable implements Runnable {
	
	private final Server server;

	public ServerRunnable() throws RemoteException, MalformedURLException, IOException {
		server = new Server();
	}

	@Override
	public void run() {
		try {
//			LocateRegistry.createRegistry(/*server.getLocalPort()+1*/1099);
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
