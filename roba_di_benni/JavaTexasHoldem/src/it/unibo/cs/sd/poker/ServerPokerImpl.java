package it.unibo.cs.sd.poker;
import it.unibo.cs.sd.poker.*;

//Implementazione del Server RMI

import java.rmi.*;
import java.rmi.server.*;
import java.util.List;/*
import it.unibo.cs.sd.poker.exceptions.DrawException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;*/

public class ServerPokerImpl extends UnicastRemoteObject implements
 ServerPoker {

private static final long serialVersionUID = 1L;
static GameTexasHoldem game;
static int contatore=0;

// Costruttore
public ServerPokerImpl() throws RemoteException {
 super();
}

// Richiede una prenotazione
public int registrazione(String playerName)
   throws RemoteException { 
	
	game.addPlayer(new Player(playerName));
	contatore++;
	if (contatore==5) inizia();
	return 0;/*
 int numSess = -1;
 System.out.println("Server RMI: richiesta registrazione con parametri");
 System.out.println("giorno   = " + giorno);
 System.out.println("sessione = " + sessione);
 System.out.println("speaker  = " + speaker);

 if (sessione.startsWith("S")) {
   try {
     numSess = Integer.parseInt(sessione.substring(1)) - 1;
   } catch (NumberFormatException e) {
   }
 }

 // Se i dati sono sbagliati significa che sono stati trasmessi male e quindi
 // solleva una eccezione
 if (numSess == -1)
   throw new RemoteException();
 if (giorno < 1 || giorno > 3)
   throw new RemoteException();

 return prog[giorno - 1].registra(numSess, speaker); */
}

public void inizia() {
	System.out.println(game + "\n");

	for (Player p : game.getPlayers()) {
		p.evaluateRanking(game.getTableCards());
		System.out.println(p);
	}
	
	game.deal();
	
	System.out.println(game + "\n");

	for (Player p : game.getPlayers()) {
		p.evaluateRanking(game.getTableCards());
		System.out.println(p);
	}
	
	game.callFlop();
	
	System.out.println(game + "\n");

	for (Player p : game.getPlayers()) {
		p.evaluateRanking(game.getTableCards());
		System.out.println(p);
	}
	
	game.betTurn();
	
	System.out.println(game + "\n");

	for (Player p : game.getPlayers()) {
		p.evaluateRanking(game.getTableCards());
		System.out.println(p);
	}
	
	game.betRiver();

	System.out.println(game + "\n");

	for (Player p : game.getPlayers()) {
		p.evaluateRanking(game.getTableCards());
		System.out.println(p);
	}

	List<Player> winnerList = game.getWinner();

	for (Player giocatore : winnerList) {
		System.out.print("\nVINCE " + giocatore.getName() + " con "
				+ giocatore.getRanking().toString() + "  \t");
	}

}

// Ritorno il campo
/*
public Programma programma(int giorno) throws RemoteException {
 System.out.println("Server RMI: richiesto programma del giorno " + giorno);
 return prog[giorno - 1];
} */

// Avvio del Server RMI
public static void main(String[] args) {

 // creazione programma
 game = new GameTexasHoldem(new Deck());
 
 int registryRemotoPort = 1099;
 String registryRemotoName = "RegistryRemoto";
 String serviceName = "ServerCongresso";

 // Controllo dei parametri della riga di comando
 if (args.length != 1 && args.length != 2) {
   System.out
       .println("Sintassi: ServerPokerImpl NomeHostRegistryRemoto [registryPort], registryPort intero");
   System.exit(1);
 }
 String registryRemotoHost = args[0];
 if (args.length == 2) {
   try {
     registryRemotoPort = Integer.parseInt(args[1]);
   } catch (Exception e) {
     System.out
         .println("Sintassi: ServerPokerImpl NomeHostRegistryRemoto [registryPort], registryPort intero");
     System.exit(2);
   }
 } 

 // Registrazione del servizio RMI
 String completeRemoteRegistryName = "//" + registryRemotoHost + ":"
     + registryRemotoPort + "/" + registryRemotoName;

 try {
   RegistryRemoto registryRemoto = (RegistryRemoto) Naming
       .lookup(completeRemoteRegistryName);
   ServerPokerImpl serverRMI = new ServerPokerImpl();
   registryRemoto.aggiungi(serviceName, serverRMI);
   System.out.println("Server RMI: Servizio \"" + serviceName
       + "\" registrato");
 } catch (Exception e) {
   System.err.println("Server RMI \"" + serviceName + "\": "
       + e.getMessage());
   e.printStackTrace();
   System.exit(1);
 }
}
}