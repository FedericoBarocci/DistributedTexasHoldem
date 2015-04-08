package it.unibo.cs.sd.poker.rmi;

import it.unibo.cs.sd.poker.mvc.Model;
import it.unibo.cs.sd.poker.mvc.View;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import it.unibo.cs.sd.poker.gui.actionlisteners.*;

public class GlobalListener implements RemoteListener, Serializable {

	private static final long serialVersionUID = -6775873150160967702L;

	private Model model;
	private View view;
	
	public GlobalListener (Model model, View view) {
		this.model = model;
		this.view = view;
		
		try {
			UnicastRemoteObject.exportObject(this, 0);
			model.getService().getGlobal().register(this);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void performActionFlop() {
		try {
			view.tableCard1.setCard(model.getService().getGlobal().getTableCards().get(0));
			view.tableCard2.setCard(model.getService().getGlobal().getTableCards().get(1));
			view.tableCard3.setCard(model.getService().getGlobal().getTableCards().get(2));
		} 
		catch (RemoteException e) {
			e.printStackTrace();
		}
		
		view.btnFlop.setEnabled(false);
		view.btnTurn.setEnabled(true);

		view.resetToken();
	}
	
	private void performActionTurn() {
		try {
			view.tableCard4.setCard(model.getService().getGlobal().getTableCards().get(3));
		} 
		catch (RemoteException e) {
			e.printStackTrace();
		}
		
		view.btnTurn.setEnabled(false);
		view.btnRiver.setEnabled(true);
		
		view.resetToken();
	}
	
	private void performActionRiver() {
		try {
			view.tableCard5.setCard(model.getService().getGlobal().getTableCards().get(4));
		} 
		catch (RemoteException e) {
			e.printStackTrace();
		}
		
		view.btnRiver.setEnabled(false);
		view.btnWinners.setEnabled(true);
		
		view.resetToken();
	}
	
	private void performActionWinners() {
		model.updateLocalByGlobal();
		
		view.lblWinners.setText(model.getWinners());
		view.showPlayersCards(model.getPlayers());
		
		view.btnDeal.setEnabled(true);
		view.btnFlop.setEnabled(false);
		view.btnTurn.setEnabled(false);
		view.btnRiver.setEnabled(false);
		view.btnWinners.setEnabled(false);
		
		try {
			model.stopGame();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		view.resetToken();
		view.unsetToken(0);
	}
	
	@Override
	public void remoteEvent(RMIMessage msg) throws RemoteException {
		System.err.println("REMOTE NOTIFICATION");
		
		switch (msg.getHeader()) {
			case NEW_PLAYER : 
				System.err.println("New Player -> " + msg.getPlayer().getName()); 
				break;
			
			case NEW_LISTENER : 
				System.err.println("New Listener"); 
				break;
			
			case START : 
				System.err.println("Start Game");
				
				model.updateLocalByGlobal();
				
				view.frame.getContentPane().removeAll();
				view.createGamePlay();
				view.btnDeal.addActionListener(new Deal(model, view));
				view.frame.repaint();
				break;
				
			case CHECK:
				System.err.println("Player before execute CHECK, now I can go");
				
				view.unsetToken();
				view.setToken(model.getTurn());
				model.getService().setToken(true);
				
				if (model.isComplete()) {
					switch (model.getTableCards().size()) {
						case 0:
							/*DEAL end*/
							model.callFlop();
							model.updateGlobalByLocal();
							model.reset();
							model.getService().broadcast( new RMIMessage(RMICommand.WAKE_UP) );
							break;
						
						case 3:
							/*FLOP end*/
							model.betTurn();
							model.updateGlobalByLocal();
							model.reset();
							model.getService().broadcast( new RMIMessage(RMICommand.WAKE_UP) );
							break;
						
						case 4: 
							/*TURN end*/
							model.betRiver();
							model.updateGlobalByLocal();
							model.reset();
							model.getService().broadcast( new RMIMessage(RMICommand.WAKE_UP) );
							break;
						
						case 5: 
							/*RIVER end*/
							//performActionWinners();
							//model.updateGlobalByLocal();
							model.getService().broadcast( new RMIMessage(RMICommand.WINNERS) );
							break;
						}
					
					
				}
				break;
			
			case DEAL:
				if (model.getService().getLocal().isHandReady()) {
					System.err.println("DEAL done, first player can start");
					
					model.reset();
				}
				else {
					System.err.println("DEAL so pick up 2 cards and keep going");
					
					model.tokenDeal();
					model.getService().getGlobal().next( new RMIMessage(RMICommand.DEAL) );
				}
				break;
			
			case WAKE_UP:
				System.err.println("WAKE UP! Perform checkpoint of global state and continue computation");
				
				model.updateLocalByGlobal();
				model.getService().setToken(model.getService().getId() == 0);
				
				switch (model.getTableCards().size()) {
					case 0:
						/*DEAL end*/
						view.initElementsGUI(model.getPlayers(), model.getService().getId(), model);
						break;
					
					case 3:
						/*FLOP end*/
						performActionFlop();
						break;
					
					case 4: 
						/*TURN end*/
						performActionTurn();
						break;
					
					case 5: 
						/*RIVER end*/
						performActionRiver();
						break;
				}
				
				break;
			
			case WINNERS:
				System.err.println("WINNERS computation");
				
				performActionWinners();
				model.getService().setToken(false);
				
				break;
		}
	}
}
