/* Prova d'esame 01/12/2008 */

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements RemOp {
  private static final int N = 10;

  private static final int K = 5;

  private static final int M = 5;

  static String s[][] = null;

  public Server() throws RemoteException {
    super();
  }

  public static void main(String[] args) {
    final int REGISTRYPORT = 1099;
    String registryHost = "localhost";
    String serviceName = "ServerRMI";

    // Inizializzazione struttura dati
    s = new String[N][K + M];
    for (int i = 0; i < N; i++)
      for (int j = 0; j < K + M; j++)
        s[i][j] = "L";

    // Riempimento alcune posizioni per prova
    s[0][0] = "Aragorn";
    s[0][K - 1] = "Aron";
    s[0][K] = "Borraccia";
    s[0][K + M - 1] = "Arco";
    s[1][0] = "Frodo";
    s[1][1] = "Pipino";
    s[1][K] = "Anello";
    s[1][K + 1] = "Faretra";
    s[N - 1][1] = "Gandalf";
    s[N - 1][K] = "Bastone";

    // Registrazione del servizio RMI
    String completeName = "//" + registryHost + ":" + REGISTRYPORT + "/"
        + serviceName;
    try {
      Server serverRMI = new Server();
      Naming.rebind(completeName, serverRMI);
      System.out.println("Server RMI: Servizio \"" + serviceName
          + "\" registrato");
    } catch (Exception e) {
      System.err.println("Server RMI \"" + serviceName + "\": "
          + e.getMessage());
      e.printStackTrace();
      System.exit(1);
    }

  }

  public synchronized int aggiungi_utente(int livello, String nomeUtente) 
      throws RemoteException {
    int indicePrimaLibera = -1;
    // controllo che l'utente non sia gia' registrato
    if (livello < 0 || livello >= N)
      return -1;

    for (int i = 0; i < N; i++)
      for (int j = 0; j < K; j++){
    	if( (i == livello) && (indicePrimaLibera == -1) )
    	  if(s[i][j].equals("L")) indicePrimaLibera = j;
        if(s[i][j].equals(nomeUtente)) return -1; // errore
      }

    if(indicePrimaLibera == -1) return -1; // non c'e' spazio: errore
    else{
      s[livello][indicePrimaLibera]=nomeUtente;
      visualizza();
      return 0; // successo
    }
  }

  public synchronized int[] elimina_risorsa(String nomeRisorsa)
      throws RemoteException {
	boolean[] temp = new boolean[N];
	int riempimento = 0;
	
	for( int i=0; i<temp.length; i++) temp[i]=false;
	
    for (int i = 0; i < N; i++)
      for (int j = K; j < K + M; j++) {
        if (s[i][j].equals(nomeRisorsa)) {
          s[i][j] = "L";          
          if( temp[i] == false ){
            riempimento++;
            temp[i] = true;
          }
        }
      }
    visualizza();
    if( riempimento == 0 ) return new int[0];
    else{
      int[] ritorno = new int[riempimento];
      riempimento = 0;
      for(int i = 0; i < N; i++)
        if( temp[i] == true ){
          ritorno[riempimento]=i;
          riempimento++;
        }
      return ritorno;
    }
  }

  private void visualizza() {
	System.out.println(" ");
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < K + M; j++)
        System.out.print("" + s[i][j] + "\t\t");
      System.out.println(" ");
    }
  }

}