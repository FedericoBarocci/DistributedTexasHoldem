package breads_and_aces.gui.view.elements;

import com.google.inject.assistedinject.Assisted;

import breads_and_aces.game.model.players.player.Player;

public interface PlayerGUIHandlerFactory {
	PlayerGUIHandler create(Player player, @Assisted(value="x") int x, @Assisted(value="y") int y, @Assisted(value="goal") int goal, boolean showCards);
}
