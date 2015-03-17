package breads_and_aces.node.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import breads_and_aces.rmi.Main;
import breads_and_aces.rmi.services.rmi.GameService;

public class ServerAsServiceContainerUtil {

//	private final NodeServerSideInfo nodeServerSideInfo;

//	private final String me; // TODO remove?
//	private int localPort;
//	private final GameService service;

	/*
	public ServerAsServiceContainer(GameService service, String me) throws RemoteException, MalformedURLException, IOException {
//		this.me = me;
//		this.service = service;
//	}
//
//	public void listen() throws IOException, AlreadyBoundException {
		ServerSocket ss = new ServerSocket(0);
		int localPort = ss.getLocalPort();
		ss.close();
		
		LocateRegistry.createRegistry(localPort);
		Naming.rebind("rmi://"+Main.addressToBind+":"+localPort+"/"+GameService.SERVICE_NAME,service);
		
		nodeServerSideInfo = new NodeServerSideInfo(me,Main.addressToBind,localPort);
		
		service.addPlayer(nodeServerSideInfo);
//		service.setItselfNodeInfo(new NodeServerSideInfo(Main.addressToBind,localPort));
		
		System.out.println("Node"+" "+me+" "+"started server-side on port "+localPort);
	}

	public NodeServerSideInfo getNodeServerSideInfo() {
		return nodeServerSideInfo;		
	}
	*/
	
	public static NodeConnectionInfo listen(String nodeId, GameService service) throws RemoteException, MalformedURLException, IOException {
		ServerSocket ss = new ServerSocket(0);
		int localPort = ss.getLocalPort();
		ss.close();
		
		LocateRegistry.createRegistry(localPort);
		Naming.rebind("rmi://"+Main.addressToBind+":"+localPort+"/"+GameService.SERVICE_NAME,service);
		
		NodeConnectionInfo nodeServerSideInfo = new NodeConnectionInfo(nodeId, Main.addressToBind,localPort);
		
		System.out.println("Node"+" "+nodeId+" "+"started on port "+localPort);
		
		return nodeServerSideInfo;
	}
	
	
	
	
	/*public static void main(String[] args) {
		try {
//			LocateRegistry.createRegistry(1099);
			new ServerAsServiceContainer().listen();
		} catch (RemoteException | MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
	}*/
}
