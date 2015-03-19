package it.unibo.cs.sd.poker;
import it.unibo.cs.sd.poker.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.rmi.*;
//import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class FrontEndImpl extends UnicastRemoteObject implements FrontEnd {


	private static final long serialVersionUID = 1L;

	static final int tableSize = 100;

	static final int remoteRefListSize = 100;

	// Matrice: la prima colonna contiene i nomi, la seconda i riferimenti remoti
	Object[][] table = new Object[tableSize][2];

	// Array: contiene i riferimenti ai server remoti
	RegistryRemoto[] riferimentiRegistryRemoti = new RegistryRemoto[remoteRefListSize];

	public FrontEndImpl() throws RemoteException {
		super();
		for (int i = 0; i < tableSize; i++) {
			table[i][0] = null;
			table[i][1] = null;
		}
		for (int i = 0; i < tableSize; i++) {
			riferimentiRegistryRemoti[i] = null;
		}
	}

	/** Restituisce il riferimento remoto cercato, oppure null */
	public synchronized Remote cercaFE(String nomeLogico) throws RemoteException {
		Remote risultato = null;
		for (int i = 0; i < tableSize; i++)
			if (((String) table[i][0]).equals(nomeLogico)) {
				risultato = (Remote) table[i][1];
				break;
			}
		return risultato;
	}

	/** Restituisce tutti i riferimenti corrispondenti ad un nome logico */
	public synchronized Remote[] cercaTuttiFE(String nomeLogico)
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
				risultato[cont] = (Remote) table[i][1];
		return risultato;
	}

	/**
	 * Aggiunge la coppia nella prima posizione disponibile. Restituisce 0 se
	 * tutto e' andato a buon fine, -1 se non c'e' spazio e -2 se il
	 * RegistryRemoto si e' gia' registrato
	 */
	public synchronized int registraFE(RegistryRemoto ref) throws RemoteException {
		// Cerco la prima posizione libera e la riempio, controllando
		// che il registry remoto non si sia gia' registrato
		int primaLibera = -1;
		for (int i = 0; i < remoteRefListSize; i++) {
			if ((riferimentiRegistryRemoti[i] != null)
					&& riferimentiRegistryRemoti[i].equals(ref))
				return -2;
			if ((primaLibera == -1) && (riferimentiRegistryRemoti[i] == null))
				primaLibera = i;
		}
		if (primaLibera != -1) {
			riferimentiRegistryRemoti[primaLibera] = ref;
			return 0;
		} else
			return -1;
	}

	synchronized void aggiornaTabellaRiferimenti() {
		RegistryRemoto ref = null;
		int i = -1;
		// Elimino tutti i riferimenti attualmente presenti
		for (i = 0; i < tableSize; i++) {
			table[i][0] = null;
			table[i][1] = null;
		}
		Object[][] riferimentiRemotiLetti = null;
		String nome;
		Remote riferimentoServizio;
		for (i = 0; i < remoteRefListSize; i++) {
			if ((ref = riferimentiRegistryRemoti[i]) != null) {
				try {
					riferimentiRemotiLetti = ref.restituisciTutti();
				}
				/*
				 * In caso di eccezione elimino il riferimento al registry remoto dalla
				 * struttura dati
				 */
				catch (RemoteException e) {
					System.out
							.println("Errore durante l'interrogazione del registry remoto, il seguente: "
									+ e);
					System.out
							.println("Elimino il riferimento al registry remoto dalla struttura dati.");
						riferimentiRegistryRemoti[i] = null;
				}
				for (i = 0; i < riferimentiRemotiLetti.length; i++) {
					nome = (String) riferimentiRemotiLetti[i][0];
					riferimentoServizio = (Remote) riferimentiRemotiLetti[i][1];
					/*
					 * Se "aggiungi" restituisce false, significa che non ci sono piu'
					 * posti liberi nella struttura, quindi esco
					 */
					if (aggiungi(nome, riferimentoServizio) == false)
						return;
				}
			}
		}
	}

	/** Aggiunge la coppia nella prima posizione disponibile */
	private boolean aggiungi(String nomeLogico, Remote riferimento) {
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

	// Avvio del Server RMI
	public static void main(String[] args) {

		int registryRemotoPort = 1099;
		String registryRemotoHost = "localhost";
		String registryRemotoName = "FrontEnd";

		
		// Registrazione del servizio RMI
		String completeName = "rmi://" + registryRemotoHost + ":" + registryRemotoPort
				+ "/" + registryRemotoName;
		try {
			FrontEndImpl serverRMI = new FrontEndImpl();
			//LocateRegistry.createRegistry(2022);
			Naming.rebind(completeName, serverRMI);
			System.out.println("Server RMI: Servizio \"" + registryRemotoName
					+ "\" registrato");
			// Aggiornamento ogni 10 secondi
			while (true) {
				serverRMI.aggiornaTabellaRiferimenti();
				// DEBUG
				System.out.println("Aggiornata tabella");
				try {
					Thread.sleep(10000);
				} catch (Exception e) {
					System.out.println("Errore, il seguente: " + e);
				}
			}
		} catch (Exception e) {
			System.err.println("Server RMI \"" + registryRemotoName + "\": "
					+ e.getMessage());
			//e.printStackTrace();
			System.exit(1);
		}
	}
}
