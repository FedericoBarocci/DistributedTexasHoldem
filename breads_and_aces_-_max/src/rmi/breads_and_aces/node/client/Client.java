package breads_and_aces.node.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import breads_and_aces.rmi.Main;
import breads_and_aces.rmi.server.NodeServerSideInfo;
import breads_and_aces.rmi.services.rmi.GameService;

public class Client extends Thread {

//	private Map<String,Server> observableServers = new HashMap<>();
	private GameService service;
//	private String id;
//	private final Node node;
	
	
	public Client(NodeServerSideInfo nodeServerSideInfo) {
//		this.node = node;
		try {
			System.out.print("starting as client: ");
			service = (GameService) Naming.lookup("rmi://"+Main.initializingHostAddress+":"+Main.initializingHostPort+"/"+GameService.SERVICE_NAME);
			service.addPlayer(nodeServerSideInfo);
			System.out.println("added itself as new player");
			
//			Scanner scanner = new Scanner(System.in);
//			String next = "";
//			while(!next.equals("end")) {
//				next = scanner.next();
//				service.echo(nodeServerSideInfo.getId(), next);
//			}
//			scanner.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args) {
//		
//		new Client(Integer.parseInt(args[0]));
//	}
}
