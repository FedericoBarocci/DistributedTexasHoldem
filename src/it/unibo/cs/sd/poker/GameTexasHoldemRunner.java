package it.unibo.cs.sd.poker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GameTexasHoldemRunner {

	public static void main(String[] args) throws IOException {
		
		while (true) {
			
		GameTexasHoldem game = new GameTexasHoldem();
		List<IPlayer> player = new ArrayList<IPlayer>(); 
		
		for(int i = 0; i < 6; i++) {
			switch (i) {
				case 0: player.add( new Player("Anna") ); break;
				case 1: player.add( new Player("Bob") ); break;
				case 2: player.add( new Player("Carlo") ); break;
				case 3: player.add( new Player("Davide") ); break;
				case 4: player.add( new Player("Enrico") ); break;
				case 5: player.add( new Player("Fausto") ); break;
			}
		}
		
		game.newGame(new Deck(), player);
		
		game.deal();
		game.callFlop();
		game.betTurn();
		game.betRiver();
		
		game.printStatus();
		
		for(IPlayer p : player) {
			RankingUtil.checkRanking(p, game.getTableCards());
			p.printStatus();
		}
				
		List<IPlayer> winnerList = game.getWinner();
		
		for (IPlayer giocatore : winnerList) {
			System.out.print( "\nVINCE " + giocatore.getName() + " con " + giocatore.getRankingEnum().toString() + "  \t");
		}
		
		System.in.read();
		
		}
	}
}
