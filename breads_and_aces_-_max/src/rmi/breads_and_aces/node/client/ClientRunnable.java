package breads_and_aces.node.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import breads_and_aces.rmi.services.rmi.Service;

public class ClientRunnable implements Runnable {
	
	private int serverPort;
	private Service echoService;

	public ClientRunnable(int serverPort) {
		this.serverPort = serverPort;
	}

	@Override
	public void run() {
		try {
			echoService = (Service) Naming.lookup("rmi://localhost:"+serverPort+"/EchoService");
			
			Scanner scanner = new Scanner(System.in);
			String next = "";
			while(!next.equals("end")) {
				next = scanner.next();
				echoService.echo(next);
			}
			scanner.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	public void speakToServer(String string) throws RemoteException {
		echoService.echo(string);
	}
}
