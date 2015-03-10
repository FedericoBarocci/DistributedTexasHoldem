package breads_and_aces.rmi.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Date;

import breads_and_aces.rmi.service.Echo;
import breads_and_aces.rmi.service.EchoImpl;

public class Server {

	private final String me;
	private int localPort;
	private final Echo echo;

	public Server() throws RemoteException, MalformedURLException, IOException {
		me = new Date().toString();
		echo = new EchoImpl(me);
	}
	
//	public int getLocalPort() {
//		return localPort;
//	}
	
	public void listen() throws IOException, AlreadyBoundException {
//		ServerSocket serverSocket = RMISocketFactory.getDefaultSocketFactory().createServerSocket(0);
//		localPort = serverSocket.getLocalPort();
//		System.out.println(localPort);
		localPort = 1099;
		
		
		LocateRegistry.createRegistry(localPort);
		Naming.rebind("rmi://localhost:"+localPort+"/EchoService",echo);
		
//		Remote stub = UnicastRemoteObject.exportObject(echo, localPort);
//		LocateRegistry.getRegistry().bind("EchoService", stub);
		System.out.println("Node"+" "+me+" "+"started on port "+localPort);
	}
	
	public static void main(String[] args) {
		try {
//			LocateRegistry.createRegistry(1099);
			new Server().listen();
		} catch (RemoteException | MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
	}
}
