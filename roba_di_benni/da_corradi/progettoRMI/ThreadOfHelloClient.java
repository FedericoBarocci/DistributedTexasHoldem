import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;

/**
 * @author CROSS
 *
 */
public class ThreadOfHelloClient implements Runnable{

	public void run(){
		try   
		{    
		   HelloInterface hello = (HelloInterface) Naming.lookup("Hello");    
		   //HelloInterface hello = (HelloInterface)Naming.lookup("//192.168.1.105:1099/Hello");    
		   
		   BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		   String said;
		   while((said=br.readLine())!=null){      
		   System.out.println(hello.say(said));
		   }
		}    
		catch (Exception e)    
		{    
		   System.out.println("HelloClient exception: " + e);    
		}       
	}

public static void main(String [] args){
	new ThreadOfHelloClient().run();
	}
}
