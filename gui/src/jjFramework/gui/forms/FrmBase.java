package jjFramework.gui.forms;

import jjFramework.BLL.controllers.cCUBase;
import jjFramework.BLL.exceptions.CheckBussinesRulesException;
import jjFramework.BLL.exceptions.CheckValidationException;
import jjFramework.BLL.utils.BindingManager;
import jjFramework.gui.components.HeaderComposite;
import jjFramework.gui.config.ConfiguradorBase;
import jjFramework.gui.utils.DialogManager;
import jjFramework.gui.utils.Enumerados.ModoEdicion;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import ILGestPojos.Personals.Errores;


/**
 * 
 * @author PC-1-Aipai
 *
 */
public class FrmBase extends Shell {
	
	protected ModoEdicion modoEdicion = ModoEdicion.NONE;
	protected cCUBase controladora;
	private HeaderComposite cabecera ;
	protected byte[] key;
	public BindingManager bindingManager = new BindingManager();

	
	public void setColorFondo(Color color)
	{
		cabecera.setColorFondo(color);
	}
	public void setTitulo(String titulo)
	{
		cabecera.setTitulo(titulo);
	}
	public void setImagenTitulo(Image imagen)
	{
		cabecera.setImagenTitulo(imagen);
	}

	public FrmBase(Display display) {
		
		super(display, SWT.CLOSE | SWT.APPLICATION_MODAL);
		setLayout(new FormLayout());
		this.setImage(ConfiguradorBase.AplicacionIcono);
		this.setText(ConfiguradorBase.AplicacionNombre);
	
		cabecera = new HeaderComposite(this, SWT.NONE);
		cabecera.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		FormData fd_cabecera = new FormData();
		fd_cabecera.left = new FormAttachment(0);
		fd_cabecera.right = new FormAttachment(100);
		cabecera.setLayoutData(fd_cabecera);
		
		Button cmdCancelar = new Button(this, SWT.NONE);
		cmdCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				cerrar();
			}
		});
		cmdCancelar.setImage(SWTResourceManager.getImage(FrmBase.class, "/resources/close16x16.png"));
		FormData fd_cmdCancelar = new FormData();
		fd_cmdCancelar.bottom = new FormAttachment(100, -10);
		fd_cmdCancelar.right = new FormAttachment(100, -10);
		cmdCancelar.setLayoutData(fd_cmdCancelar);
		cmdCancelar.setText("Cancelar");
		
		Button cmdAceptar = new Button(this, SWT.NONE);
		cmdAceptar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				controladorasSave();
			
			}
		});
		cmdAceptar.setImage(SWTResourceManager.getImage(FrmBase.class, "/resources/accept16x16.png"));
		FormData fd_cmdAceptar = new FormData();
		fd_cmdAceptar.bottom = new FormAttachment(cmdCancelar, 26);
		//fd_cmdAceptar.top = new FormAttachment(cmdCancelar, 0, SWT.TOP);
		fd_cmdAceptar.right = new FormAttachment(cmdCancelar, -6);
		cmdAceptar.setLayoutData(fd_cmdAceptar);
		cmdAceptar.setText("Aceptar");
	
	
		createContents();
	}

	protected void createContents() {
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setSize(777, 544);
	}

	@Override protected void checkSubclass() {
	}

	protected void cerrar() 
	 {
		if(hasChanges())
		{
			if (!DialogManager.mostrarPregunta(this.getShell(),"Se han realizado cambios, ¿Desea salir sin guardar?"))
				return;
		}

		try {
			this.close();
			this.dispose();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	 }
		
	public void mostrarVista(Composite parent, ModoEdicion modoEdicion, byte[] key)
	{
			this.key = key;
			this.modoEdicion = modoEdicion;
			mostrar(parent);
	}
	
	public void mostrarVista(Composite parent, ModoEdicion modoEdicion)
	{
		this.modoEdicion = modoEdicion;
		mostrar(parent);
	}
	
	private void mostrar(Composite parent)
	{
		try
		{
			// Esta parte es para centrar el formulario respecto al padre
			this.setParent(parent);
			Monitor primary = Display.getDefault().getPrimaryMonitor();
			Rectangle bounds = primary.getBounds();
			Rectangle rect = this.getBounds();
			int x = bounds.x + (bounds.width - rect.width) / 2;
			int y = bounds.y + (bounds.height - rect.height) / 2;
			this.setLocation(x, y);
			
			cargarDatos();
			open();
			enlazarDatos();
			accionesGui();
		} catch (Exception ex)
		{
			DialogManager.mostrarError(getShell(), ex);
		}
	}

	protected Boolean hasChanges(){ return false;}
	
	/** Acciones finales sobre la GUI tales como cambios en el modo de edición de controles etc... */
	protected void accionesGui() {}

	public void cargarDatos() throws Exception{}

	public void enlazarDatos() throws Exception {}

	protected void grabarDatos() throws CheckValidationException, CheckBussinesRulesException, Exception 
	{
		controladora.saveData();
	}
	
	public final void controladorasSave()
	{
		try {
			grabarDatos();

			controladora.stop();
			
			cerrar();
		} catch (CheckValidationException e) {
			MostrarErroresValidacion(e);

		} catch (CheckBussinesRulesException e) {
			MostrarErroresReglasNegocio(e);

		} catch (Exception e) {
			MostrarErroresGuardado(e);
		}
	}
	
	private void MostrarErroresGuardado(Exception e) {
		DialogManager.mostrarError(getShell(), e);
	}
	
	private void MostrarErroresReglasNegocio(CheckBussinesRulesException e) {
		
		DialogManager.mostrarError(getShell(), e);
		// TODO Mostrar lista de reglas de negocio que han sido violadas
	}
	
	protected void MostrarErroresValidacion(CheckValidationException e) {
		
		bindingManager.clearWarnings();
		for(Errores err: e.getValidationErrors()) 
		{
			Control c = bindingManager.getBindedControl(err.getPropiedad(), err.getClase());
			if(c != null)
			{
				ControlDecoration controlDecoration = new ControlDecoration(c, SWT.RIGHT);
				controlDecoration.setImage(SWTResourceManager.getImage(Composite.class, "/resources/exclamacion_16.png"));
				controlDecoration.setDescriptionText(err.getMensaje());
				bindingManager.addWarning(controlDecoration);
			} else
			{
				DialogManager.mostrarMensaje(getShell(), "Cuidado, hay un error de validación pero no se está mostrando");
			}
		}
	}

	protected void setEditable(Composite composite, boolean editable)
	{
		try
		{
			Control[] controles =  composite.getChildren();
			if (controles != null )
			{
				for (Control c : controles)
				{
					if(c instanceof Button && (((Button)c).getStyle() & SWT.CHECK) != 0 )
						((Button)c).setEnabled(editable);
					else if(c instanceof Text)
						((Text)c).setEditable(editable);
					else if(c instanceof Combo)
						((Combo)c).setEnabled(editable);
					else if (c instanceof Composite)
						setEditable((Composite)c,editable);
				}
			}
		} catch (Exception ex)
		{
			DialogManager.mostrarError(getShell(), ex);
		}
	}

}
