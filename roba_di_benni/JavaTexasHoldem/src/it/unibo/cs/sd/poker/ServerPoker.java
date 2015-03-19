package it.unibo.cs.sd.poker;
import it.unibo.cs.sd.poker.*;

//Interfaccia remota

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerPoker extends Remote {

int registrazione(String playerName)
   throws RemoteException;

//GameTexasHoldem Tavolo(Deck deck) throws RemoteException;

}