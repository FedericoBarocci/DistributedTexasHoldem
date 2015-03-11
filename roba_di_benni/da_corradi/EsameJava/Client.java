/* Prova d'esame 02/12/2008 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class Client {

  private final static int N = 10;

  public static void main(String[] args) {
    final int REGISTRYPORT = 1099;

    String registryHost = null;
    String serviceName = "ServerRMI";
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

    // Controllo dei parametri della riga di comando
    if (args.length != 1) {
      System.out.println("Sintassi: Client NomeHost");
      System.exit(1);
    }
    registryHost = args[0];

    // Connessione al servizio RMI remoto
    try {
      String completeName = "//" + registryHost + ":" + REGISTRYPORT + "/"
          + serviceName;
      RemOp serverRMI = (RemOp) Naming.lookup(completeName);
      System.out
          .println("ClientRMI: Servizio \"" + serviceName + "\" connesso");

      System.out.println("\nRichieste di servizio fino a fine file");

      String service;
      String nomeRisorsa, nomeUtente;
      boolean ok = false;
      int livello = -1;

      System.out.print("Servizio (AU=aggiungi utente,ER=elimina risorsa): ");

      while ((service = stdIn.readLine()) != null) {
        ok = false;
        if (service.equals("AU")) {
          System.out.print("Inserisci l'identificativo del livello da 0 a "
              + (N - 1) + ": ");

          while (!ok) {
            try {
              livello = Integer.parseInt(stdIn.readLine());
              if (livello < 0 || livello >= N)
                System.out.print("Identificativo del livello da 0 a " + (N - 1)
                    + ": ");
              else
                ok = true;
            } catch (Exception e) {
              System.out.print("Inserisci l'identificativo del livello da 0 a "
                  + (N - 1) + ": ");
            }
          }
          System.out.print("Inserisci nome utente: ");
          nomeUtente = stdIn.readLine();

          try {
            if (serverRMI.aggiungi_utente(livello, nomeUtente) == 0) {
              System.out.println("Aggiunta l'utente");
            } else {
              System.out.println("Problemi, utente non aggiunto!");
            }
          } catch (RemoteException re) {
            System.out.println("Errore remoto: " + re);
          }

        } // AU

        else if (service.equals("ER")) {
          System.out.print("Inserisci il nome della risorsa da eliminare: ");
          nomeRisorsa = stdIn.readLine();

          try {
        	int[] ritorno = serverRMI.elimina_risorsa(nomeRisorsa); 
            if ( ritorno.length == 0) {
              System.out.println("Risorsa non trovata");
            } else {
              System.out.print("Ecco i livelli eliminati:\n - ");
              for( int i = 0; i < ritorno.length; i++ ) System.out.print(""+ritorno[i] + " - ");
              System.out.println("");
            }
          } catch (RemoteException re) {
            System.out.println("Errore remoto: " + re);
          }
        } // EU

        else
          System.out.println("Servizio non disponibile");

        System.out.print("Servizio (AU=aggiungi utente,ER=elimina risorsa): ");

      } // !EOF

    } catch (Exception e) {
      System.err.println("ClientRMI: " + e.getMessage());
      e.printStackTrace();
      System.exit(1);
    }
  }
}