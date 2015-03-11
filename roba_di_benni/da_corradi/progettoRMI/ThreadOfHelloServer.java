import java.rmi.*;    
import java.rmi.server.*;    
import java.rmi.Naming;    
import java.rmi.registry.LocateRegistry;

/**
 * @author CROSS
 *
 */
interface HelloInterface extends Remote    
{    
   public String say(String said) throws RemoteException;    
}    

/**
 * @author CROSS
 *
 */
class Hello extends UnicastRemoteObject implements HelloInterface    
{       
   
   public Hello() throws RemoteException    
   {    
      super();    
   }    
   
    public String say(String said) throws RemoteException    
   {    
      System.out.println(said);
      String message = "Sever got what you said"; 
      return message;    
   }    
}    

/**
 * @author CROSS
 *
 */
public class ThreadOfHelloServer implements Runnable   
{    
   public void run()    
   {    
      try   
      {
         LocateRegistry.createRegistry(1099);    
         HelloInterface hello = new Hello();    
         Naming.rebind("Hello", hello);    
         //Naming.rebind("//192.168.1.105:1099/Hello",hello);    
            
         System.out.println("Hello Server is ready.");    
      }    
      catch (Exception e)    
      {    
         System.out.println("Hello Server failed: " + e);    
      }    
   }    
   
   public static void main(String [] args)    
   {    
	 new ThreadOfHelloServer().run();
   } 
}   



