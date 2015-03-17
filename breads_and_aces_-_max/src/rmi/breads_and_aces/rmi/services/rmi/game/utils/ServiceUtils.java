package breads_and_aces.rmi.services.rmi.game.utils;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import breads_and_aces.rmi.services.rmi.game.GameService;

public class ServiceUtils {

	@SuppressWarnings("unchecked")
	public static <T> T lookup(String address, int port) throws MalformedURLException, RemoteException, NotBoundException {
		T service = (T) Naming.lookup( getRMIURLString(address, port, GameService.SERVICE_NAME) );
		return service;
	}
	
	public static String getRMIURLString(String address, int port, String service) {
		return "rmi://"+address+":"+port+"/"+service;
	}
}
