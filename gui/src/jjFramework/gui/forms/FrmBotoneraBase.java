package jjFramework.gui.forms;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.event.AWTEventListener;
import jjFramework.gui.components.JBotoneraPanel;
import jjFramework.gui.components.JHeaderPanel;
import jjFramework.gui.utils.Enumerados.ModoEdicion;


@SuppressWarnings("serial")
public class FrmBotoneraBase extends JDialog {

	protected ModoEdicion modoEdicion = ModoEdicion.NONE;
	
	public enum ETTipoBoton{
		ACEPTAR (0),
		CANCELAR (1);
		
		private final int valor;
		public int Valor()   { return valor; }
		
		ETTipoBoton(int valor)
		{
			this.valor = valor;
		}
		
		public int ObtenerValor()
		{
			return valor;
		}
	};
	
	
	private JPanel contentPane;
	private JHeaderPanel pnlHeader = new JHeaderPanel();

	public void setImagenTitulo(ImageIcon imagen)
	{
		pnlHeader.setImagenTitulo(imagen);
	}
	

	public FrmBotoneraBase(Frame padre, String titulo) {
		
		super(padre, true);
		
		setBounds(100, 100, 578, 450);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		
		pnlHeader.setTitulo(titulo);
		contentPane.add(pnlHeader, BorderLayout.NORTH);
		
		JBotoneraPanel pnlBotonera = new JBotoneraPanel();
		pnlBotonera.setBorder(null);
		contentPane.add(pnlBotonera, BorderLayout.SOUTH);
		pnlBotonera.addEventListener(new AWTEventListener(){

			@Override
			public void eventDispatched(AWTEvent evento) {
				
				try {
					AccionBotonera(evento.getID());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
	}
	
	
	
	private void AccionBotonera(int tipo) throws Exception
	{

		switch (tipo) {
		  case 0:
			  SaveData();
			  break;
		        
		  case 1: 
			  dispose();
			  break;
		        
		  default:
			  JOptionPane.showMessageDialog(null, "No se reconoce el tipo del boton que lanzó el evento.");
		}
	}


	public void SaveData() throws Exception
	{
		throw new Exception("Implementa el método SaveData en el formulario Hijo");
	}
	
	protected void EditData( Object entity) throws Exception { throw new Exception("Implementa LoadData en tu formulario"); };
	
	protected void CreateData( Object entity) throws Exception { throw new Exception("Implementa CreateData en tu formulario"); };
	
	protected void DataBinding() throws Exception { throw new Exception("Implementa DataBinding en tu formulario"); };
	
	public void OpenForm(ModoEdicion modo, Object entity) throws Exception
	{
		modoEdicion = modo;
		if (modo == ModoEdicion.CREACION)
		{
			CreateData(entity);
		} else {
			EditData(entity);
		}
		
		DataBinding();
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	
	
}
