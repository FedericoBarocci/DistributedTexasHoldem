package it.unibo.cs.sd.poker.gui.controllers.actionlisteners;

import it.unibo.cs.sd.poker.gui.view.elements.ElementGUI;
import it.unibo.cs.sd.poker.gui.view.elements.utils.EnumRectangle;
import it.unibo.cs.sd.poker.gui.view.elements.utils.GuiUtils;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class InfoButton implements MouseListener {

	public InfoButton() {}

	@Override
	public void mouseClicked(MouseEvent e) {
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setTitle("About");
		frame.setBounds(GuiUtils.INSTANCE.getRectangle(EnumRectangle.about));
		
		String s = "";
		
	    try(BufferedReader br = new BufferedReader(new FileReader("ABOUT"))) {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	        }
	        
	        s = sb.toString();
	    } 
	    catch (IOException e1) {
			e1.printStackTrace();
		}
	    
	    JTextArea textArea = new JTextArea(s);
		
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Courier", Font.PLAIN, 14));
		
		JScrollPane areaScrollPane = new JScrollPane(textArea);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		areaScrollPane.setPreferredSize(new Dimension(640, 480));
		
		frame.add(areaScrollPane);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE.getImageGui("info_click.png"));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE.getImageGui("info_over.png"));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE.getImageGui("info_over.png"));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		((ElementGUI) (e.getSource())).changeImage(GuiUtils.INSTANCE.getImageGui("info.png"));
	}

}
