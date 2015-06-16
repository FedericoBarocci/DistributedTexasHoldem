package bread_and_aces.registration.initializers.servable._gui;

import java.util.concurrent.CountDownLatch;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import bread_and_aces.gui.view.elements.frame.JFrameDefault;

public class AccepterPlayersGUI extends JFrameDefault {

	private static final long serialVersionUID = 6029891503532628425L;
	
	@AssistedInject
	public AccepterPlayersGUI(@Assisted CountDownLatch startLatch , AccepterPlayerTableModel dataModel) {
		init();
		setTitle("Poker Distributed Hold'em");
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		JLabel labelTitle = new JLabel("Subscribing players:");
		JTable table = new JTable(dataModel);
		JScrollPane jScrollPane = new JScrollPane(table);
		JButton buttonStart = new JButton("Close Registration And Start Game");
		
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
			.addComponent(labelTitle)
			.addComponent(jScrollPane)
			.addComponent(buttonStart)
		);
	       
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addComponent(labelTitle)
			.addComponent(jScrollPane)
			.addComponent(buttonStart)
		);
		
		buttonStart.addActionListener(l->{
			startLatch.countDown();
			AccepterPlayersGUI accepterPlayersGUI = AccepterPlayersGUI.this;
			accepterPlayersGUI.dispose();
			accepterPlayersGUI = null;
		});
		
		pack();
		setVisible(true);
	}
}
