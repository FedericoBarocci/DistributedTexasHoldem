/**************************************************************

* Programmer: Md. Manzoor Murshed                            *

* Date: 03/28/06                                             * 

* Program: Chat Client                                       *

*                                                            *

**************************************************************/

 

import java.rmi.*;
import java.util.Vector;
import java.io.*;

public class Client
{

public static void main (String args[]) throws IOException
{

   //Declaring variables
   BufferedReader dataIn = new BufferedReader (new InputStreamReader(System.in));
   String strChoice;
   int choice=0;
   int connection_slot = -1;
   boolean am_connected=false;
 

     try
     {    	 

       String registry = args[0];
       String registration = "rmi://"+registry+":4099/ChatRegistry";

       ChatProvider reg = (ChatProvider) Naming.lookup(registration);

        try 
        {

      while(choice!=5)

       {         //menu for registration

       System.out.println("\nMenu (enter 5 to quit");
       System.out.println("\t1.List");
       System.out.println("\t2.Join");
       System.out.println("\t3.Start Chatting");
       System.out.println("\t4.Leave");
       System.out.println("\t5.Quit");
       System.out.print("Enter your choice :"); 
       strChoice = dataIn.readLine();
       choice = Integer.parseInt(strChoice); 

       switch (choice)

        { 

          case 1://List of Chat rooms and Information

             Vector list= reg.getChatRooms();
             Vector Info= reg.getInformation();
             System.out.println("\nList of Chat rooms and Informations");
             for (int i=0; i<list.size(); i++)
             {
            	 System.out.print("\tRoom: " + list.get(i));
            	 System.out.println("\t:Information: " + Info.get(i)); 
            	 }
             
             break;

 
          case 2: //Join a room

              System.out.print("Enter Room name : ");
              String name = dataIn.readLine();
              //reg.unregister(name);

              try{

                connection_slot=reg.signIn(name,connection_slot);
                if(connection_slot>=0)
                     {
                	am_connected=true;
                	System.out.println("Connected to Chat room :" +name);
                	}
                else
                	System.out.println("Connection Refused");
                }
              
              catch(Exception e){} 
              
              break;
 

          case 3: //Chat Chatting 

             String get_message=" ";
             String send_message=" ";
             boolean q = false;
             
                 while(q!=true)
                      {
                	  if(am_connected)
                	  { 
                		  try
                		  {
                			  System.out.print("Send: ");
                			  send_message=dataIn.readLine();
                                 if (send_message.equals("bye"))
                                	 {
                                	 am_connected=false; q=true;
                                	 } 
                                 else if (!send_message.equals(""))
                                	 reg.broadcast(send_message, connection_slot);
                		  }
                		  catch (Exception e){ }
                		  
                		  try
                		  {
                			  get_message=reg.getbroadcast();
                			  if (get_message.equals("bye")) q=true;
                			  else if (!get_message.equals(""))
                				  System.out.println("Received: " +get_message);
                			  //reg.reset();
                		  }
                		  catch(Exception e){}
                		  
                		  try
                		  {
                             Thread.currentThread().sleep(5000);
                		  }
                		  catch(InterruptedException e){} 

                 }//end of if 
               }

              break;
 

         case 4://Leave the Chat Room 

             am_connected=false;    
             break;

         case 5: //quit
        	 
        	 System.out.println("Quiting Chatroom Server Program...");
             break;
             
             default :
            	 System.out.println("Invalid response");
            	 

         }//end of switch

       }// end of while
 

     }//end of second try 

         catch (NumberFormatException e)
          {          System.out.println("Invalid response/choice" + e.getMessage());}
          

  }//end of try     
     catch (Exception e)
     {   System.out.println("Chat server err: " + e.getMessage());
          e.printStackTrace();   }

 }//end of main

}//end of class Client
