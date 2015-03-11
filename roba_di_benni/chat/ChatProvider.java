/**************************************************************

* Programmer: Md. Manzoor Murshed                            *

* Date: 03/28/06                                             * 

* Program: Chat Provider Interface                           *

*                                                            *

**************************************************************/

import java.rmi.*;

import java.util.Vector;

 

/** RMI Interface for Provider*/

public interface ChatProvider extends Remote {

              public int register(String name, String info) throws RemoteException;

              public int unregister(String name)throws RemoteException;

              public String getinfo(String name) throws RemoteException;

      public Vector getChatRooms() throws RemoteException; 

              public Vector getInformation() throws RemoteException;

              int  signIn(String s, int i) throws java.rmi.RemoteException;

              void broadcast(String s,int i ) throws java.rmi.RemoteException;

              String getbroadcast() throws java.rmi.RemoteException;

      void reset() throws java.rmi.RemoteException;

}
