package it.unibo.cs.sd.poker.gui.view.elements;

import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

public class JFrameDefault extends JFrame {

	private static final long serialVersionUID = -7541303929180306424L;

	public JFrameDefault(String string) {
		super(string);
		
		getRootPane().registerKeyboardAction(e -> {
			System.exit(1);
		}, 
		KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), 
		JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
	}

	public JFrameDefault() {
		this("");
	}
}
