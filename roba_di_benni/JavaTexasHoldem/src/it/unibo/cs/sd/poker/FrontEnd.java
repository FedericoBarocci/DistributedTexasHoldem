package it.unibo.cs.sd.poker;

import it.unibo.cs.sd.poker.*;
//Interfaccia remota

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FrontEnd extends Remote {

public Remote cercaFE(String nomeLogico) throws RemoteException;
public Remote[] cercaTuttiFE(String nomeLogico) throws RemoteException;
public int registraFE(RegistryRemoto ref) throws RemoteException;

}