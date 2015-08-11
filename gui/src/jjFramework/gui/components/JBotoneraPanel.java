package jjFramework.gui.components;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.AWTEvent;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.*;
import java.awt.Font;


@SuppressWarnings("serial")
public class JBotoneraPanel extends JPanel {

	private AWTEventListener eventListener;
	private JButton cmdAceptar = new JButton("Aceptar");
	private JButton cmdCancelar = new JButton("Cancelar");

	public void setcmdCancelarVisible(boolean visible)
	{
		cmdCancelar.setVisible(visible);
	}
	
	public void setcmdAceptarVisible(boolean visible)
	{
		cmdAceptar.setVisible(visible);
	}
	
	public JBotoneraPanel() {
	
		setBackground(Color.WHITE);
		FlowLayout flowLayout = new FlowLayout(FlowLayout.RIGHT, 7, 4);
		setLayout(flowLayout);
		
		
		cmdAceptar.setDefaultCapable(false);
		cmdAceptar.setIconTextGap(3);
		cmdAceptar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		cmdAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OnButtonClick(0);
			}
		});
		cmdAceptar.setIcon(new ImageIcon(JBotoneraPanel.class.getResource("/resources/accept16x16.png")));
		cmdAceptar.setHorizontalAlignment(SwingConstants.LEFT);
		add(cmdAceptar);
		
		
		cmdCancelar.setIconTextGap(3);
		cmdCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		cmdCancelar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				OnButtonClick(1);
			}
		});
		cmdCancelar.setIcon(new ImageIcon(JBotoneraPanel.class.getResource("/resources/close16x16.png")));
		cmdCancelar.setHorizontalAlignment(SwingConstants.LEFT);
		add(cmdCancelar);

	}

	public AWTEventListener getListener() {
		return eventListener;
	}

	public void addEventListener(AWTEventListener evento) {
		this.eventListener = evento;
	}
	
	protected void OnButtonClick(int tipoBoton)
	{
		AWTEvent e = new AWTEvent(this, tipoBoton) {};
		eventListener.eventDispatched(e);
	}
}
