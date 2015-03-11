package breads_and_aces.rmi.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class EchoImpl extends UnicastRemoteObject implements Echo {

	private String node;
	
	public EchoImpl(String node) throws RemoteException {
		this.node = node;
	}

	@Override
	public void echo(String string) throws RemoteException {
		System.out.println(node+" says : "+string);
	}
	
	private static final long serialVersionUID = -2020166040927899027L;
}
