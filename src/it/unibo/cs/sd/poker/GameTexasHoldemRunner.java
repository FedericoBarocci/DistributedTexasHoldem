package it.unibo.cs.sd.poker;

import java.io.IOException;
import java.util.List;


public class GameTexasHoldemRunner {


	public static void main(String[] args) throws IOException {
		
		while (true) {
			
			GameTexasHoldem game = new GameTexasHoldem(new Deck());

			game.addPlayer(new Player("Anna"));
			game.addPlayer(new Player("Bob"));
			game.addPlayer(new Player("Carlo"));
			game.addPlayer(new Player("Davide"));
			game.addPlayer(new Player("Enrico"));
			game.addPlayer(new Player("Fausto"));
			
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

			System.in.read();

		}
	}
}
