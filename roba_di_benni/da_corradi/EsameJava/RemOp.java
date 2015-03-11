/* Prova d'esame 01/12/2008 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemOp extends Remote {
  int aggiungi_utente(int livello, String nomeUtente)
      throws RemoteException;

  int[] elimina_risorsa(String nomeRisorsa) throws RemoteException;

}