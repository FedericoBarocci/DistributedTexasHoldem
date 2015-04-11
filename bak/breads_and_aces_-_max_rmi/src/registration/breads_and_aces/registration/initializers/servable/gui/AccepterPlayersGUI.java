package breads_and_aces.registration.initializers.servable.gui;

import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class AccepterPlayersGUI extends JFrame {

	private static final long serialVersionUID = 6029891503532628425L;
	private final JPanel contentPane;
	private final JTable table;
	

	@AssistedInject
	public AccepterPlayersGUI(@Assisted CountDownLatch startLatch 
//			, JTable table
//			, JPanel contentPane,
			, AccepterPlayerTableModel dataModel) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 330);
		contentPane = new JPanel();
//		this.contentPane = contentPane;
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("436px:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("238px"),
				RowSpec.decode("27px"),}));
		
		JLabel labelTitle = new JLabel("Subscribing players:");
		contentPane.add(labelTitle, "2, 2, center, top");
		
		table = new JTable();
		table.setModel(dataModel);
		
		JScrollPane jScrollPane = new JScrollPane(table);
		contentPane.add(jScrollPane, "2, 4, fill, fill");
		
		JButton buttonStart = new JButton("Close Registration And Start Game");
		contentPane.add(buttonStart, "2, 5, fill, bottom");
		
		buttonStart.addActionListener(l->{
			startLatch.countDown();
			AccepterPlayersGUI.this.dispose();
		});
	}
}
