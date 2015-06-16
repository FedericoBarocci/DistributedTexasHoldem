package bread_and_aces.gui.view;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bread_and_aces.game.core.Card;
import bread_and_aces.game.model.table.Table;
import bread_and_aces.gui.view.elements.CardGUI;
import bread_and_aces.gui.view.elements.frame.JFrameGame;
import bread_and_aces.gui.view.elements.utils.CardsUtils;
import bread_and_aces.gui.view.elements.utils.GuiUtils;

public class TableViewHandler extends AbstractViewHandler<Void> {
	
	private final Table table;
	
	private List<CardGUI> tableCardsGui = new ArrayList<>();
	private int displayedCards = 0;

	@Inject
	public TableViewHandler(JFrameGame jFrameGame, Table table) {
		super(jFrameGame);
		this.table = table;
	}

	@Override
	public void init(Void noArg) {
		tableCardsGui.forEach(c->this.removeElement(c));
		tableCardsGui.clear();
		
		for (int i = 0; i < 5; i++) {
			CardGUI c = new CardGUI(GuiUtils.tableCardX + GuiUtils.tableCardSpan * i, GuiUtils.tableCardY);
			tableCardsGui.add(c);
			this.addElement(c);
		}
		
		displayedCards = 0;
	}

	public void addTableCards() {
		List<Card> cards = table.getRoundCards();
		
		//Show down
		if(cards.size() == 5) {
			displayedCards = 0;
		}
		
		for (Card c : cards) {
			tableCardsGui.get(displayedCards).changeImage(CardsUtils.INSTANCE_BIG.getImageCard(c));
			displayedCards++;
		}
		
		this.repaint();
	}
}
