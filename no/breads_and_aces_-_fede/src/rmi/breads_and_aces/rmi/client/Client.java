package breads_and_aces.rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import breads_and_aces.rmi.service.Echo;

public class Client extends Thread {

//	private Map<String,Server> observableServers = new HashMap<>();
	private Echo echoService;
	
	public Client() {
	
		try {
			echoService = (Echo) Naming.lookup("rmi://localhost:"+1099+"/EchoService");
			
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
	
	public void connect() {
		

	}
	
	public static void main(String[] args) {
		new Client();
	}
}
