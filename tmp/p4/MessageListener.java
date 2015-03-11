/** Interface for message callbacks */
public interface MessageListener extends java.util.EventListener {
	/** Called when a client first receives a message */
	public void message(String m);
	}
