/**************************************************************

* Programmer: Md. Manzoor Murshed                            *

* Date: 03/28/06                                             * 

* Program: Chat Server                                       *

*                                                            *

**************************************************************/

import java.rmi.*;

import java.util.Vector;

import java.io.*;

public class Server

{

public static void main (String args[]) throws IOException

{

   //Declaring variables

   BufferedReader dataIn = new BufferedReader (new InputStreamReader(System.in));

   String strChoice;

   int choice=0;

 

              try{

       String registry = args[0];

       String registration = "rmi://"+registry+":4099/ChatRegistry";

       ChatProvider reg = (ChatProvider) Naming.lookup(registration);

        try {

      while(choice!=5)

       {

         //menu for registration

       System.out.println("\nMenu (enter 5 to quit");

       System.out.println("\t1.Register");

       System.out.println("\t2.Un Register");

       System.out.println("\t3.List of Chat Rooms");

       System.out.println("\t4.Inoformation");

       System.out.println("\t5.Quit");

       System.out.print("Enter your choice :"); 

       strChoice = dataIn.readLine();

       choice = Integer.parseInt(strChoice);

 

       switch (choice)

        {

          case 1: //Chat room registration

              System.out.print("Enter Room name : ");

              String name = dataIn.readLine();

              System.out.print("Enter Information : ");

              String info = dataIn.readLine();

              reg.register(name, info);

              break;

          case 2: //Chat room unregistration

              System.out.print("Enter Room name : ");

              name = dataIn.readLine();

              reg.unregister(name);

              break;

 

          case 3://List of Chat rooms

             Vector list= reg.getChatRooms();

             System.out.println("\nList of Chat rooms");

             for (int i=0; i<list.size(); i++){

             System.out.println("\t" + list.get(i));}

             break;

 

          case 4://Information of a Chat Room

                            System.out.print("Enter Room name : ");

             name = dataIn.readLine();

             String m = reg.getinfo(name);  

             System.out.println("Information of Room " +name + ": " + m); 

             break;

          case 5: //quit

                            System.out.println("Quiting Chatroom Server Program...");

             break;

                  default :

          System.out.println("Invalid response");

 

         }//end of switch

       }

 

           }//end of second try

 

         catch (NumberFormatException e)

          {

          System.out.println("Invalid response/choice" + e.getMessage());

          }

       } catch (Exception e) {

          System.out.println("Chat server err: " + e.getMessage());

          e.printStackTrace();

                            }

}

}
