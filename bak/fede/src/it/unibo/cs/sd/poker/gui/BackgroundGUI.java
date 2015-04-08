package it.unibo.cs.sd.poker.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BackgroundGUI extends JPanel {

	private static final long serialVersionUID = 4458481205033866097L;
    
	private Image image;
	private Integer width;
	private Integer height;
	
	public BackgroundGUI(String path) {
		try {
			this.image = ImageIO.read(new File(path));
			this.width = image.getWidth(this);
			this.height = image.getHeight(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BackgroundGUI(String path, Integer width, Integer height) {
		try {
			this.image = ImageIO.read(new File(path));
			this.width = width;
			this.height = height;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (this.image != null) {
            g.drawImage(this.image, 0, 0, this.width, this.height, this);
        }
    }
}
