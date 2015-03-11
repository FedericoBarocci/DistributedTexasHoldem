import java.io.*;

public class ClientDriver implements MessageListener {

	public synchronized void message(String msg) {
		System.out.println("---");
		System.out.println(msg);
		System.out.println();
		}
	
	public static void main(String args[]) throws Exception {
		String s = "rmi://amoeba.cs.umd.edu/MessageDirectory";
		if (args.length == 1) s = args[0];

		Directory server = (Directory)java.rmi.Naming.lookup(s);
		LocalClient c = new ClientImpl();
		c.addMessageListener(new ClientDriver());
		c.initialize(server);

                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while((s = in.readLine()) != null) {
			c.initiateMessage(s);
			Thread.sleep(5000);
			}

	}
}
