/** Interface provided by client for local interaction */
public interface LocalClient {
	/** Initialize the client */
	public void initialize(Directory s)  throws java.rmi.RemoteException;

	/** Initiate a message */
	public void initiateMessage(String msg);
	/** Add a listener for Message events */
	public void addMessageListener(MessageListener l);
	/** Remove a listener for Message events */
	public void removeMessageListener(MessageListener l);
	}
