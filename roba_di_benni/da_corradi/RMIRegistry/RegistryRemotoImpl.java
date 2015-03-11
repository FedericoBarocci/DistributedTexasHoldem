// Implementazione del Server

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class RegistryRemotoImpl extends UnicastRemoteObject implements
		RegistryRemoto {

	final int tableSize = 100;

	// Tabella: la prima colonna contiene i nomi, la seconda i riferimenti remoti
	Object[][] table = new Object[100][2];

	public RegistryRemotoImpl() throws RemoteException {
		super();
		for (int i = 0; i < tableSize; i++) {
			table[i][0] = null;
			table[i][1] = null;
		}
	}

	/** Aggiunge la coppia nella prima posizione disponibile */
	public synchronized boolean aggiungi(String nomeLogico, Remote riferimento)
			throws RemoteException {
		// Cerco la prima posizione libera e la riempio
		boolean result = false;
		for (int i = 0; i < tableSize; i++)
			if (table[i][0] == null) {
				table[i][0] = nomeLogico;
				table[i][1] = riferimento;
				result = true;
				break;
			}
		return result;
	}

	/** Restituisce il riferimento remoto cercato, oppure null */
	public synchronized Remote cerca(String nomeLogico) throws RemoteException {
		Remote risultato = null;
		for (int i = 0; i < tableSize; i++)
			if (((String) table[i][0]).equals(nomeLogico)) {
				risultato = (Remote) table[i][1];
				break;
			}
		return risultato;
	}

	/** Restituisce tutti i riferimenti corrispondenti ad un nome logico */
	public synchronized Remote[] cercaTutti(String nomeLogico)
			throws RemoteException {
		int cont = 0;
		for (int i = 0; i < tableSize; i++)
			if (((String) table[i][0]).equals(nomeLogico))
				cont++;
		Remote[] risultato = new Remote[cont];
		// Ora lo uso come indice per il riempimento
		cont = 0;
		for (int i = 0; i < tableSize; i++)
			if (((String) table[i][0]).equals(nomeLogico))
				risultato[cont++] = (Remote) table[i][1];
		return risultato;
	}

	/** Restituisce tutti i riferimenti corrispondenti ad un nome logico */
	public synchronized Object[][] restituisciTutti() throws RemoteException {
		int cont = 0;
		for (int i = 0; i < tableSize; i++)
			if (table[i][0] != null)
				cont++;
		Object[][] risultato = new Object[cont][2];
		// Ora lo uso come indice per il riempimento
		cont = 0;
		for (int i = 0; i < tableSize; i++)
			if (table[i][0] != null) {
				risultato[cont][0] = table[i][0];
				risultato[cont][1] = table[i][1];
			}
		return risultato;
	}

	/** Elimina la prima entry corrispondente al nome logico indicato */
	public synchronized boolean eliminaPrimo(String nomeLogico)
			throws RemoteException {
		boolean risultato = false;
		for (int i = 0; i < tableSize; i++)
			if (((String) table[i][0]).equals(nomeLogico)) {
				table[i][0] = null;
				table[i][1] = null;
				risultato = true;
				break;
			}
		return risultato;
	}

	public synchronized boolean eliminaTutti(String nomeLogico)
			throws RemoteException {
		boolean risultato = false;
		for (int i = 0; i < tableSize; i++)
			if (((String) table[i][0]).equals(nomeLogico)) {
				if (risultato == false)
					risultato = true;
				table[i][0] = null;
				table[i][1] = null;
			}
		return risultato;
	}

	// Avvio del Server RMI
	public static void main(String[] args) {

		int registryRemotoPort = -1;
		String registryRemotoHost = "localhost";
		String registryRemotoName = "RegistryRemoto";

		int frontEndPort = 1099;
		String frontEndHost = null;
		String frontEndName = "FrontEnd";

		// Controllo dei parametri della riga di comando
		if (args.length != 2 && args.length != 3) {
			System.out
					.println("Sintassi: RegistryRemotoImpl registryRemotoPort frontEndHost [frontEndPort]");
			System.exit(1);
		} else {
			try {
				registryRemotoPort = Integer.parseInt(args[0]);
				frontEndHost = args[1];
				if (args.length == 3)
					frontEndPort = Integer.parseInt(args[2]);
			} catch (Exception e) {
				System.out
						.println("Sintassi: RegistryRemotoImpl registryRemotoPort frontEndHost [frontEndPort], porte intere");
				System.exit(2);
			}
		}
		
    // Impostazione del SecurityManager
    if (System.getSecurityManager() == null) {
      System.setSecurityManager(new RMISecurityManager());
    }


		// Registrazione del servizio RMI
		String completeName = "//" + registryRemotoHost + ":" + registryRemotoPort
				+ "/" + registryRemotoName;
		try {
			RegistryRemotoImpl serverRMI = new RegistryRemotoImpl();
			Naming.rebind(completeName, serverRMI);
			System.out.println("Server RMI: Servizio \"" + registryRemotoName
					+ "\" registrato");
			// Effettuo la lookup del FrontEnd e la registrazione presso il FrontEnd
			completeName = "//" + frontEndHost + ":" + frontEndPort +"/" + frontEndName;
			FrontEnd fe = (FrontEnd) Naming.lookup(completeName);
			fe.registraFE( serverRMI );
		} catch (Exception e) {
			System.err.println("Server RMI \"" + registryRemotoName + "\": "
					+ e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}
