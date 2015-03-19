package it.unibo.cs.sd.poker;
import it.unibo.cs.sd.poker.*;

//Interfaccia remota

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RegistryRemoto extends Remote {

public boolean aggiungi(String nomeLogico, Remote riferimento) throws RemoteException;
public Remote cerca(String nomeLogico) throws RemoteException;
public Remote[] cercaTutti(String nomeLogico) throws RemoteException;
public Object[][] restituisciTutti() throws RemoteException;
public boolean eliminaPrimo(String nomeLogico) throws RemoteException;
public boolean eliminaTutti(String nomeLogico) throws RemoteException;

}