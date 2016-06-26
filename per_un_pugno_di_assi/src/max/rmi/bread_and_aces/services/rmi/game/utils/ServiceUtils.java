package bread_and_aces.services.rmi.game.utils;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import bread_and_aces.services.rmi.game.core.GameService;
import bread_and_aces.utils.DevPrinter;

public class ServiceUtils {

	@SuppressWarnings("unchecked")
	public static <T> T lookup(String address, int port) throws MalformedURLException, RemoteException, NotBoundException {
		T service = (T) Naming.lookup( getRMIURLString(address, port, GameService.SERVICE_NAME) );
		return service;
	}
	
	public static String getRMIURLString(String address, int port, String service) {
		DevPrinter.println("rmi://"+address+":"+port+"/"+service);
		return "rmi://"+address+":"+port+"/"+service;
	}
}
