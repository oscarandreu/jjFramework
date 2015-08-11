package jjFramework.gui.components;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

public class TransparentButton extends JButton {

	private static final long serialVersionUID = 1L;

	public TransparentButton(String text) { 
	    super(text);
	    setOpaque(false); 
	} 
	    
	public void paint(Graphics g) { 
	    Graphics2D g2 = (Graphics2D) g.create(); 
	    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); 
	    super.paint(g2); 
	    g2.dispose(); 
	} 
}
