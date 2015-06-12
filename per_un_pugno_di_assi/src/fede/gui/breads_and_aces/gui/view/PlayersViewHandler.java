package breads_and_aces.gui.view;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.limewire.inject.LazySingleton;

import breads_and_aces.game.model.oracle.actions.Action;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.gui.view.PlayersViewHandler.PlayersViewHandlerInitArgs;
import breads_and_aces.gui.view.elements.PlayerGUIHandler;
import breads_and_aces.gui.view.elements.PlayerGUIHandlerFactory;
import breads_and_aces.gui.view.elements.frame.JFrameGame;
import breads_and_aces.gui.view.elements.utils.GuiUtils;

@LazySingleton
//@Singleton
public class PlayersViewHandler extends AbstractViewHandler<PlayersViewHandlerInitArgs> {
	
	private final Map<String, PlayerGUIHandler> playersGui = new LinkedHashMap<>();
	private final PlayerGUIHandlerFactory playerGUIHandlerFactory;
	private final GamePlayersKeeper gamePlayersKeeper;
	
	@Inject
	public PlayersViewHandler(JFrameGame/*Provider*/ jFrameGame/*Provider*/, PlayerGUIHandlerFactory playerGUIHandlerFactory,
			GamePlayersKeeper gamePlayersKeeper) {
		super(jFrameGame/*Provider*/);
		this.playerGUIHandlerFactory = playerGUIHandlerFactory;
		this.gamePlayersKeeper = gamePlayersKeeper;
	}
	
//	@Override
//	public void init(List<Player> players, String myName, int goal) {
	public void init(PlayersViewHandlerInitArgs args) {
		int size = args.players.size();
		int span = Math.floorDiv(GuiUtils.playerSpan, size+1);
		
		playersGui.values().forEach(p->p.clearFromGui());
		playersGui.clear();
		
		for (int i = 0; i < size; i++) {
			int x = GuiUtils.playerX + (span * (i+1));
			int y = GuiUtils.playerY;
			Player player = args.players.get(i);
			
			/* *** only for testing *** override param *** */ 
				//player.setScore(Math.floorDiv(goal, 7) * i);
			
			boolean showCards = player.getName().equals(args.myName);	
			PlayerGUIHandler playerGUIHandler = 
//					new PlayerGUIHandler(player, x, y, goal, showCards);
					playerGUIHandlerFactory.create(player, x, y, args.goal, showCards);
			playerGUIHandler.init(null);
			
			if (player.hasToken()) {
				playerGUIHandler.setTokenView();
			}
			
			playerGUIHandler.draw();
			playersGui.put(player.getName(), playerGUIHandler);
		}
		
		super.repaint();
	}
	
	public void showPlayersCards() {
		playersGui.values().forEach(p->p.showCards());
	}
	
	public void setViewToken(String playerName) {
		playersGui.values().forEach(p->{
			p.unsetTokenView();
			if (p.getId().equals(playerName)) {
				p.setTokenView();
			}
		});
		
		super.repaint();
	}
	
	public void showWinnerId(String winner) {
		for(PlayerGUIHandler pg : playersGui.values()) {
			if (pg.getId().equals(winner)) {
				pg.setWinner(gamePlayersKeeper.getPlayer(pg.getId()).getScore());
			}
			else {
				pg.setLoser(gamePlayersKeeper.getPlayer(pg.getId()).getScore());
			}
		}
			
		super.repaint();
	}
	
	public void showWinners(List<Player> winners) {
		for(PlayerGUIHandler pg : playersGui.values()) {
			boolean loser = true;
			
			for(Player p : winners) {
				if (pg.getId().equals(p.getName())) {
					pg.setWinner(p.getScore());
					loser = false;
					
					break;
				}
			}
			
			if (loser) {
				pg.setLoser(gamePlayersKeeper.getPlayer(pg.getId()).getScore());
			}
		}
		
		super.repaint();
	}

	public void setPlayerAction(String fromPlayer, Action action) {
		playersGui.get(fromPlayer).setAction(action);
	}

	public void resetActions(List<Player> players) {
		players.forEach(p->{
			playersGui.get(p.getName()).setAction(Action.NONE);
		});
	}
	
	static public class PlayersViewHandlerInitArgs {
		List<Player> players; 
		String myName;
		int goal;
		
		public PlayersViewHandlerInitArgs(List<Player> players, String myName, int goal) {
			this.players = players;
			this.myName = myName;
			this.goal = goal;
		}
	}

	public void removeElement(String playerId) {
		playersGui.get(playerId).clearFromGui();
		playersGui.remove(playerId);
	}
}
