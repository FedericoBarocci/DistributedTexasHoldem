
/** Interface for directory */
public interface Directory extends java.rmi.Remote {
	/** Register client if not already registered 
	 *  @return list of peer clients to connect to */
	 public RemoteClient[] getPeers(RemoteClient me) 
			throws java.rmi.RemoteException;
	}

