package jjFramework.gui.forms;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.AWTEventListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jjFramework.gui.components.JBotoneraPanel;
import jjFramework.gui.components.JHeaderPanel;


@SuppressWarnings("serial")
public class FrmBaseView extends JPanel {

	private JHeaderPanel pnlHeader = new JHeaderPanel();
	private JBotoneraPanel pnlBotonera = new JBotoneraPanel();
	
	public void setImagenTitulo(ImageIcon imagen)
	{
		pnlHeader.setImagenTitulo(imagen);
	}

	public FrmBaseView(String titulo) {
		
		//PERSONALIZACIONES
		pnlHeader.setTitulo(titulo);
		pnlBotonera.setcmdAceptarVisible(false);
		
		setBounds(100, 100, 791, 526);
		this.setBackground(Color.WHITE);
		this.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		this.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.setLayout(new BorderLayout(0, 0));

		this.add(pnlHeader, BorderLayout.NORTH);
		
		this.add(pnlBotonera, BorderLayout.SOUTH);
		pnlBotonera.addEventListener(new AWTEventListener(){

			@Override
			public void eventDispatched(AWTEvent evento) {
				
				try {
//					AccionBotonera(evento.getID());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
	}
	
	protected void LoadData() {
		
		System.out.print("frmBase");
	}
	
	protected void DataBindings() {}
	
	public void OpenForm()
	{
		
		LoadData();
		
		DataBindings();
		
		this.setVisible(true);
	}

//	private void AccionBotonera(int tipo) throws Exception
//	{
//
//		switch (tipo) {
//		  case 0:
//			  throw new Exception("Opcion no permitida en un formulario tipo frmBaseView");
//		        
//		  case 1: 
//			  dispose();
//			  break;
//		        
//		  default:
//			  JOptionPane.showMessageDialog(null, "No se reconoce el tipo del boton que lanzó el evento.");
//		}
//	}

}
