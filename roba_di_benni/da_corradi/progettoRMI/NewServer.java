public class NewServer{
	
	   public static void main(String[] argv)
	   {    
	      try   
	      {
	    	 ThreadOfHelloServer ths = new ThreadOfHelloServer();
	    	 Thread ts = new Thread(ths);

	    	 ThreadOfHelloClient thc = new ThreadOfHelloClient();
	         Thread tc = new Thread(thc);
	         
	         ts.start();
	         ts.join();
	         tc.start();

	      }    
	      catch (Exception e)    
	      {    
	         System.out.println("Cross prompts: Server failed, " + e);    
	      }    
	   }    
	}   