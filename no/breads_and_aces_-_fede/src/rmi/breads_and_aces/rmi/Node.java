package breads_and_aces.rmi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import breads_and_aces.rmi.client.ClientRunnable;
import breads_and_aces.rmi.server.ServerRunnable;

public class Node {

	
	
	public Node() throws IOException, InterruptedException {
		ServerRunnable serverRunnable = new ServerRunnable();
		Thread serverThread = new Thread(serverRunnable);
		
		serverThread.start();
		serverThread.join();	
	}
	
	public Node(int port) {
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		try {
			if (args.length==0)
				new Node();
			else if (args.length==1) {
				String portAsString = args[0];
			
				new Node();
				
				ClientRunnable clientRunnable = new ClientRunnable(Integer.parseInt(portAsString));
				Thread clientThread = new Thread(clientRunnable);
				clientThread.start();
				clientThread.join();
				
				clientRunnable.speakToServer("bla");
			}
			
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		try {
//			Echo echoService = (Echo) Naming.lookup("rmi://localhost/EchoService");
//			Scanner scanner = new Scanner(System.in);
//			String next = "";
//			while(!next.equals("end")) {
//				next = scanner.next();
//				echoService.echo(next);
//			}
//			scanner.close();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		} catch (NotBoundException e) {
//			e.printStackTrace();
//		}
		
		System.exit(0);
	}

}
