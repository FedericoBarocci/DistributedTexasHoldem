package it.unibo.cs.sd.poker;

//Implementazione del Client RMI
import it.unibo.cs.sd.poker.*;

import java.rmi.*;
import java.io.*;

class ClientPoker {

	// Avvio del Client RMI
	public static void main(String[] args) {
		int registryRemotoPort = 1099;
		String frontEndHost = null;
		String frontEndName = "FrontEnd";
		String serviceName = "ServerCongresso";
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

		// Controllo dei parametri della riga di comando
		if (args.length != 1 && args.length != 2) {
			System.out
					.println("Sintassi: ClientPoker NomeHostFrontEnd [frontEndPort], porta intera");
			System.exit(1);
		}
		frontEndHost = args[0];
		if (args.length == 2) {
			try {
				registryRemotoPort = Integer.parseInt(args[1]);
			} catch (Exception e) {
				System.out
						.println("Sintassi: ClientPoker NomeHostFrontEnd [frontEndPort], porta intera");
				System.exit(2);
			}
		}



		// Connessione al servizio RMI remoto
		try {
			String completeRemoteRegistryName = "//" + frontEndHost + ":"
					+ registryRemotoPort + "/" + frontEndName;
			FrontEnd registryRemoto = (FrontEnd) Naming
					.lookup(completeRemoteRegistryName);
			ServerPoker serverRMI = (ServerPoker) registryRemoto
					.cercaFE(serviceName);
			System.out
					.println("ClientRMI: Servizio \"" + serviceName + "\" connesso");

			System.out.println("\nRichieste di servizio fino a fine file");

			String service;
			System.out
					.print("Servizio (R=Registrazione, P=Boh): ");

			while ((service = stdIn.readLine()) != null) {

				if (service.equals("R")) {

					System.out.print("Nickname? ");
					String speak = stdIn.readLine();
					// nessun controllo, qualsiasi stringa potrebbe essere un nome
					// accettabile

					// Tutto corretto
					try {
						if (serverRMI.registrazione(speak) == 0)
							System.out.println("Registrazione di " + speak
									+ " effettuata");
						else
							System.out.println("Sessione piena");
					} catch (Exception e) {
						System.out.println("Eccezione, la seguente: " + e);
						continue;
					}
				} // R

				else if (service.equals("P")) {
				/*	System.out.print("Programma giornata (1-3)? ");

					while (ok != true) {
						// intercettare la NumberFormatException
						g = Integer.parseInt(stdIn.readLine());
						if (g < 1 || g > 3) {
							System.out.println("Giornata non valida");
							System.out.print("Programma giornata (1-3)? ");
							continue;
						} else
							ok = true;
					}
					System.out.println("Ecco il programma: ");
					try {
						serverRMI.programma(g).stampa();
					} catch (Exception e) {
						System.out.println("Eccezione, la seguente: " + e);
						continue;
					} */

				} // P

				else
					System.out.println("Servizio non disponibile");

				System.out
						.print("Servizio (R=Registrazione, P=Boh): ");
			} // !EOF

		} catch (Exception e) {
			System.err.println("ClientRMI: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}