package jjFramework.gui.components;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.beans.Beans;
import javax.swing.border.LineBorder;


@SuppressWarnings("serial")
public class JHeaderPanel extends JPanel {

	private Color color1 = new Color(202,218,249);
	//private Color color2 = new Color(202,218,249);
	private Color color2 = new Color(249,250,252);
    private String titulo = "";
    private JLabel lblTitulo;
    JLabel lblNewLabel = new JLabel("");
    
    public void setImagenTitulo(ImageIcon imagen)
    {
    	if (imagen != null)
    	{
    		lblNewLabel.setIcon(imagen);
    	} else
    	{
    		lblNewLabel.setIcon(new ImageIcon(JHeaderPanel.class.getResource("/resources/cabecera.png")));
    	}
    }
    
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
		lblTitulo.setText(titulo);
	}

	public JHeaderPanel() {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBackground(Color.WHITE);
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setVgap(2);
		flowLayout.setHgap(2);
		flowLayout.setAlignment(FlowLayout.LEFT);
		
		
		lblNewLabel.setIcon(new ImageIcon(JHeaderPanel.class.getResource("/resources/cabecera.png")));
		add(lblNewLabel);
		
		lblTitulo = new JLabel("Introduce tu Titulo personalizado");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
		add(lblTitulo);

	}
	
	 @Override
	 public void paint(Graphics g) {  
 
		if (! Beans.isDesignTime())
		{
			Graphics2D g2 = (Graphics2D) g.create();
	        Rectangle clip = g2.getClipBounds();                        
	        g2.setPaint(new GradientPaint(0.0f, 0.0f, color1, getWidth() ,0.0f, color2));        
	        g2.fillRect(clip.x, clip.y, clip.width, clip.height);   
	    
		}  
	    
		Component c;
	    for (int i = 0; i < getComponentCount(); i++) {
		    c = getComponent(i);
		    g.translate(c.getX(), c.getY());
		    c.print(g);
		    g.translate(-c.getX(), -c.getY());
	    }
		
	 }

}
