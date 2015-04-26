package breads_and_aces.gui.view;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.gui.view.elements.PlayerGUI;
import breads_and_aces.gui.view.elements.utils.GuiUtils;

@Singleton
public class PlayersView extends GameView {
	
	private Map<String, PlayerGUI> playersGui = new LinkedHashMap<>();
	
	public void init(List<Player> players, String myName, int goal) {
		int size = players.size();
		int span = Math.floorDiv(GuiUtils.playerSpan, size+1);
		
		playersGui.values().forEach(p->p.clearFromGui());
		playersGui.clear();
		
		for (int i = 0; i < size; i++) {
			int x = GuiUtils.playerX + (span * (i+1));
			int y = GuiUtils.playerY;
			Player player = players.get(i);
			
			/* *** only for testing *** override param *** */ 
				//player.setScore(Math.floorDiv(goal, 7) * i);
			
			boolean showCards = player.getName().equals(myName);	
			PlayerGUI playerGui = new PlayerGUI(player, x, y, goal, showCards);
			
			if (player.hasToken()) {
				playerGui.setTokenView();
			}
			
			playerGui.draw();
			playersGui.put(player.getName(), playerGui);
		}
		
		this.repaint();
	}
	
	public void showPlayersCards() {
		playersGui.values().forEach(p->p.showCards());
	}
	
	public void setViewToken(String playerName) {
		playersGui.values().forEach(p->{
			p.unsetTokenView();
			if (p.getId().equals(playerName)) {
				System.out.println(p.getId() + " now set token to view");
				p.setTokenView();
			}
			else {
				System.out.println(p.getId() + " don't have token to view");
			}
		});
		
		this.repaint();
	}
	
	public void showWinners(List<Player> winners) {
		for(PlayerGUI pg : playersGui.values()) {
			boolean loser = true;
			
			for(Player p : winners) {
				if (pg.getId().equals(p.getName())) {
					pg.setWinner();
					loser = false;
					
					break;
				}
			}
			
			if (loser) {
				pg.setLoser();
			}
		}
		
		this.repaint();
	}
}
