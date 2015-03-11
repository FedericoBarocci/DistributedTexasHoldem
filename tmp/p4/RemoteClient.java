public interface RemoteClient extends java.rmi.Remote {
	/** Receive and propagate a message */
	public void msg(Message m) throws java.rmi.RemoteException;

	/** Client should reset peer list */
	public void newPeers(RemoteClient[] peers) throws java.rmi.RemoteException;
	/** Client should immediately return */
	public void ping() throws java.rmi.RemoteException;
	}

