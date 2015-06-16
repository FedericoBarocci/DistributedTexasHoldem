package bread_and_aces.gui.view;

import javax.inject.Inject;
import javax.inject.Singleton;

import bread_and_aces.game.model.oracle.actions.Action;
import bread_and_aces.game.model.players.keeper.GamePlayersKeeper;
import bread_and_aces.gui.view.elements.ElementGUI;
import bread_and_aces.gui.view.elements.frame.JFrameGame;
import bread_and_aces.gui.view.elements.utils.EnumRectangle;
import bread_and_aces.gui.view.elements.utils.GuiUtils;

@Singleton
public class ButtonsViewHandler extends AbstractViewHandler<Void> {

	private final GamePlayersKeeper gamePlayersKeeper;
	
	private ElementGUI up = new ElementGUI(
			GuiUtils.INSTANCE.getImageGui("up.png"),
			GuiUtils.INSTANCE.getRectangle(EnumRectangle.up));
	
	private ElementGUI down = new ElementGUI(
			GuiUtils.INSTANCE.getImageGui("down.png"),
			GuiUtils.INSTANCE.getRectangle(EnumRectangle.down));
	
	private ElementGUI info = new ElementGUI(
			GuiUtils.INSTANCE.getImageGui("info.png"),
			GuiUtils.INSTANCE.getRectangle(EnumRectangle.info));
	
	private ElementGUI ok = new ElementGUI(
			GuiUtils.INSTANCE.getImageGui("ok.png"),
			GuiUtils.INSTANCE.getRectangle(EnumRectangle.ok),
			GuiUtils.INSTANCE.getRectangle(EnumRectangle.okOuter));
	
	private ElementGUI fold = new ElementGUI(
			GuiUtils.INSTANCE.getImageGui("fold.png"),
			GuiUtils.INSTANCE.getRectangle(EnumRectangle.fold),
			GuiUtils.INSTANCE.getRectangle(EnumRectangle.foldOuter));
	
	@Inject
	public ButtonsViewHandler(JFrameGame jFrameGame, GamePlayersKeeper gamePlayersKeeper) {
		super(jFrameGame);
		this.gamePlayersKeeper = gamePlayersKeeper;
	}

	@Override
	public void init(Void noArgs) {
		ok.setText("CHECK");
		fold.setText("FOLD");
		
		super.addElement(up);
		super.addElement(down);
		super.addElement(ok);
		super.addElement(fold);
		super.addElement(info);
	}

	public void enableButtons(String playerName) {
		enableButtons(playerName.equals(gamePlayersKeeper.getMyName()));
	}
	
	public void enableButtons(boolean hasToken) {
		if(hasToken) {
			enableButtons();
		}
		else {
			up.changeImage(GuiUtils.INSTANCE.getImageGui("up_off.png"));
			down.changeImage(GuiUtils.INSTANCE.getImageGui("down_off.png"));
			ok.changeImage(GuiUtils.INSTANCE.getImageGui("ok_off.png"));
			fold.changeImage(GuiUtils.INSTANCE.getImageGui("fold_off.png"));
			
			up.setEnable(false);
			down.setEnable(false);
			ok.setEnable(false);
			fold.setEnable(false);
		}
		
		super.repaint();
	}
	
	public void enableButtons() {
		up.changeImage(GuiUtils.INSTANCE.getImageGui("up.png"));
		down.changeImage(GuiUtils.INSTANCE.getImageGui("down.png"));
		ok.changeImage(GuiUtils.INSTANCE.getImageGui("ok.png"));
		fold.changeImage(GuiUtils.INSTANCE.getImageGui("fold.png"));
		
		up.setEnable(true);
		down.setEnable(true);
		ok.setEnable(true);
		fold.setEnable(true);
		
		super.repaint();
	}
	
	public void updateText(Action action) {
		ok.setText(action.toString());
	}
	
	public ElementGUI getUp() 	{return up;}
	public ElementGUI getDown() {return down;}
	public ElementGUI getOk() 	{return ok;}
	public ElementGUI getFold() {return fold;}
	public ElementGUI getInfo() {return info;}
}
