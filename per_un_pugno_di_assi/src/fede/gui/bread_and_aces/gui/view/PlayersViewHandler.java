package bread_and_aces.gui.view;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.limewire.inject.LazySingleton;

import bread_and_aces.game.model.oracle.actions.Action;
import bread_and_aces.game.model.players.keeper.GamePlayersKeeper;
import bread_and_aces.game.model.players.player.Player;
import bread_and_aces.gui.view.PlayersViewHandler.PlayersViewHandlerInitArgs;
import bread_and_aces.gui.view.elements.PlayerGUIHandler;
import bread_and_aces.gui.view.elements.PlayerGUIHandlerFactory;
import bread_and_aces.gui.view.elements.frame.JFrameGame;
import bread_and_aces.gui.view.elements.utils.GuiUtils;
import bread_and_aces.utils.DevPrinter;

@LazySingleton
//@Singleton
public class PlayersViewHandler extends AbstractViewHandler<PlayersViewHandlerInitArgs> {
	
	private final Map<String, PlayerGUIHandler> playersGui = 
			new LinkedHashMap<>();
			//new ConcurrentSkipListMap<>();
	private final PlayerGUIHandlerFactory playerGUIHandlerFactory;
	private final GamePlayersKeeper gamePlayersKeeper;
	
	@Inject
	public PlayersViewHandler(JFrameGame jFrameGame, PlayerGUIHandlerFactory playerGUIHandlerFactory,
			GamePlayersKeeper gamePlayersKeeper) {
		super(jFrameGame);
		DevPrinter.println("HERE -> PlayersViewHandler 32");
		this.playerGUIHandlerFactory = playerGUIHandlerFactory;
		this.gamePlayersKeeper = gamePlayersKeeper;
	}
	
	public synchronized void init(PlayersViewHandlerInitArgs args) {
		DevPrinter.println("PlayersViewHandler.init");
		int size = args.players.size();
		int span = Math.floorDiv(GuiUtils.playerSpan, size+1);
		
		playersGui.values().forEach(p->p.clearFromGui());
		playersGui.clear();
		
		for (int i = 0; i < size; i++) {
			int x = GuiUtils.playerX + (span * (i+1));
			int y = GuiUtils.playerY;
			Player player = args.players.get(i);
			
			boolean showCards = player.getName().equals(args.myName);	
			PlayerGUIHandler playerGUIHandler = playerGUIHandlerFactory.create(player, x, y, args.goal, showCards);
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
	
	public synchronized void setViewToken(String playerName) {
		DevPrinter.println("PlayersViewHandler.setViewToken");
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

	public void removeElement(String playerId) {
//		DevPrinter.println( new Throwable(), "removing "+playerId );
		if (playersGui.containsKey(playerId)) {
			playersGui.get(playerId).clearFromGui();
			playersGui.remove(playerId);
			
			super.repaint();
		}
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
}
