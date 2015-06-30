package bread_and_aces.registration.initializers.servable._gui;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.swing.table.DefaultTableModel;

import bread_and_aces.game.model.players.keeper.GamePlayersKeeper;
import bread_and_aces.game.model.players.keeper.PlayersObservable;
import bread_and_aces.game.model.players.player.Player;
import bread_and_aces.utils.observatory.Observable;
import bread_and_aces.utils.observatory.Observer;

class AccepterPlayerTableModel extends DefaultTableModel {
		
	private static final long serialVersionUID = 3138739482615283558L;
	
	private final GamePlayersKeeper playersKeeper;
	private final List<Integer> n = new ArrayList<Integer>();

	@Inject
	public AccepterPlayerTableModel(GamePlayersKeeper playersKeeper) {
		Object[] columns = new Object[3];
		columns[0] = "Position";
		columns[1] = "Name";
		columns[2] = "IP";
		
		setColumnIdentifiers(columns);
		this.playersKeeper = playersKeeper;
		NewPlayerObserverDelegateForTableUpdate newPlayerObserverDelegateForTableUpdate = new NewPlayerObserverDelegateForTableUpdate();
		((PlayersObservable)this.playersKeeper).addObserver(newPlayerObserverDelegateForTableUpdate);
		
		List<Player> players = playersKeeper.getPlayers();
		if (!players.isEmpty()) {
			newPlayerObserverDelegateForTableUpdate.update(null, players.get(0).getName());
		}
	}
	
	private class NewPlayerObserverDelegateForTableUpdate implements Observer<String> {
		@Override
		public void update(Observable<String> playersKeeper, String playerId) {
			int nn = n.size()+1;
			n.add(nn);
			Object[] r = new Object[] {nn, playerId, playerId + "'s ip"};
			addRow(r);
		}
	}
}
