package it.unibo.cs.sd.poker.mvc;

import it.unibo.cs.sd.poker.mvc.Model;
import it.unibo.cs.sd.poker.Player;
import it.unibo.cs.sd.poker.gui.PlayerGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;


public class Controller {
	
	private Model model;
	private View view;
	
	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		
		view.btnDeal.addActionListener(new Deal());
		view.btnFlop.addActionListener(new Flop());
		view.btnTurn.addActionListener(new Turn());
		view.btnRiver.addActionListener(new River());
		view.btnWinners.addActionListener(new Winners());
	}
	
	class Deal implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			model.newGame();
			
			view.clear();
			
			model.addPlayer(new Player("Anna", true));
			model.addPlayer(new Player("Bob"));
			model.addPlayer(new Player("Carlo"));
			model.addPlayer(new Player("Davide"));
			model.addPlayer(new Player("Enrico"));
			model.addPlayer(new Player("Fausto"));
			
			model.deal();
			
			for (Player p : model.getPlayers()) {
				view.addPlayer(p);
			}
			
			int i = 0;
			for (PlayerGUI pg : view.getPlayers()) {
				pg.check.addActionListener(new Check(i++));
			}
			
			view.tableCard1.clear();
			view.tableCard2.clear();
			view.tableCard3.clear();
			view.tableCard4.clear();
			view.tableCard5.clear();
			view.btnDeal.setEnabled(false);
			view.btnFlop.setEnabled(true);
			
			model.resetPhase();
			view.resetToken();
		}
	}
	
	private void performActionFlop() {
		model.callFlop();
		view.tableCard1.setCard(model.getTableCards().get(0));
		view.tableCard2.setCard(model.getTableCards().get(1));
		view.tableCard3.setCard(model.getTableCards().get(2));
		view.btnFlop.setEnabled(false);
		view.btnTurn.setEnabled(true);
		
		model.resetPhase();
		view.resetToken();
	}
	
	private void performActionTurn() {
		model.betTurn();
		view.tableCard4.setCard(model.getTableCards().get(3));
		view.btnTurn.setEnabled(false);
		view.btnRiver.setEnabled(true);
		
		model.resetPhase();
		view.resetToken();
	}
	
	private void performActionRiver() {
		model.betRiver();
		view.tableCard5.setCard(model.getTableCards().get(4));
		view.btnRiver.setEnabled(false);
		view.btnWinners.setEnabled(true);
		
		model.resetPhase();
		view.resetToken();
	}
	
	private void performActionWinners() {
		view.lblWinners.setText(model.getWinners());
		view.showPlayersCards(model.getPlayers());
		
		view.btnDeal.setEnabled(true);
		view.btnFlop.setEnabled(false);
		view.btnTurn.setEnabled(false);
		view.btnRiver.setEnabled(false);
		view.btnWinners.setEnabled(false);
		
		model.resetPhase();
		model.stopGame();
		view.resetToken();
		view.unsetToken(0);
	}
	
	class Check implements ActionListener {
		
		private Integer index;
		
		public Check(Integer index) {
			this.index = index;
		}
		
		public void actionPerformed(ActionEvent e) {
			if (model.executeCheck(index)) {
				//JOptionPane.showMessageDialog(view.frame, "Check!");
				
				if (model.completePhase()) {
					switch (model.getTableCards().size()) {
						case 0:
						/*DEAL phase*/
						performActionFlop();
						break;
					
					case 3:
						/*FLOP phase*/
						performActionTurn();
						break;
					
					case 4: 
						/*TURN phase*/
						performActionRiver();
						break;
					
					case 5: 
						/*RIVER pahse*/
						performActionWinners();
						break;
					}
				}
				
				view.unsetToken(index);
				
				if(model.isRunning()) 
					view.setToken(model.getTurn());
			}
			else {
				JOptionPane.showMessageDialog(view.frame, "Non è il mio turno...");
			}
		}
	}
	
	class Flop implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			performActionFlop();
		}
	}
	
	class Turn implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			performActionTurn();
		}
	}
	
	class River implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			performActionRiver();
		}
	}
	
	class Winners implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			performActionWinners();
		}
	}
}
