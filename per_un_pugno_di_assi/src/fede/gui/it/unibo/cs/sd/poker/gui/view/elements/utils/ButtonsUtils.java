package it.unibo.cs.sd.poker.gui.view.elements.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public enum ButtonsUtils {
	
	INSTANCE;
	
	private final String dir = "elements" + File.separatorChar + "buttons" + File.separatorChar;
	private final Map<String,ImageIcon> paths = new HashMap<>();
	
	private ButtonsUtils() {}

	public ImageIcon getImageGui(String element) {
		ImageIcon icon = paths.get(element);
		
		if (icon == null) {
			icon = new ImageIcon(dir + element);
			paths.put(element, icon );
		}
		
		return icon;
	}
}
