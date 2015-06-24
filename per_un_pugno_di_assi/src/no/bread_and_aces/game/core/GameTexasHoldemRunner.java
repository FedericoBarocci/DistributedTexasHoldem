package bread_and_aces.game.core;

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
			
			DevPrinter.println(game + "\n");

			for (Player p : game.getPlayers()) {
				p.evaluateRanking(game.getTableCards());
				DevPrinter.println(p);
			}
			
			game.deal();
			
			DevPrinter.println(game + "\n");

			for (Player p : game.getPlayers()) {
				p.evaluateRanking(game.getTableCards());
				DevPrinter.println(p);
			}
			
			game.callFlop();
			
			DevPrinter.println(game + "\n");

			for (Player p : game.getPlayers()) {
				p.evaluateRanking(game.getTableCards());
				DevPrinter.println(p);
			}
			
			game.betTurn();
			
			DevPrinter.println(game + "\n");

			for (Player p : game.getPlayers()) {
				p.evaluateRanking(game.getTableCards());
				DevPrinter.println(p);
			}
			
			game.betRiver();

			DevPrinter.println(game + "\n");

			for (Player p : game.getPlayers()) {
				p.evaluateRanking(game.getTableCards());
				DevPrinter.println(p);
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
