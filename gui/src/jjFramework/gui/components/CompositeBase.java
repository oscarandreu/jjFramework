package jjFramework.gui.components;

import jjFramework.BLL.controllers.cCUBase;
import jjFramework.BLL.exceptions.CheckBussinesRulesException;
import jjFramework.BLL.exceptions.CheckValidationException;
import jjFramework.BLL.utils.BindingManager;
import jjFramework.gui.utils.DialogManager;
import jjFramework.gui.utils.Enumerados.ModoEdicion;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;

import ILGestPojos.Personals.Errores;

/**
 * 
 * @author PC-1-Aipai
 *
 */
public class CompositeBase extends Composite
{
	protected ModoEdicion modoEdicion = ModoEdicion.NONE;
	protected cCUBase controladora;
	public BindingManager bindingManager = null;
	protected byte[] key;
	
	public CompositeBase(Composite parent, int style, BindingManager bindingManager) 
	{
		super(parent, style);
		
		this.bindingManager = bindingManager;
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(GroupLayout.LEADING)
				.add(0, 450, Short.MAX_VALUE)
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(GroupLayout.LEADING)
				.add(0, 300, Short.MAX_VALUE)
				);
		setLayout(groupLayout);
	}

	public void mostrarPanel(ModoEdicion modoEdicion)
	{
		try
		{
			this.modoEdicion = modoEdicion;
			mostrar();
		} catch (Exception ex)
		{
			DialogManager.mostrarError(getShell(), ex);
		}
	}

	public void mostrarPanel(ModoEdicion modoEdicion, byte[] key)
	{
		try
		{
			this.modoEdicion = modoEdicion;
			this.key = key;
			mostrar();
		} catch (Exception ex)
		{
			DialogManager.mostrarError(getShell(), ex);
		}
	}
	
	private void mostrar()
	{
		try
		{
			cargarDatos();
			enlazarDatos();
			accionesGui();
			
		} catch (Exception e) {
			DialogManager.mostrarError(getShell(), e);
		}
	}
	
	protected void accionesGui() throws Exception {}
	
	public void cargarDatos() throws Exception {}
	
	public void enlazarDatos() throws Exception {}
	
	protected void grabarDatos() throws CheckValidationException, CheckBussinesRulesException, Exception {}
	
	protected void refrescarDatos() {}
	
	public final void controladorasSave()

	{
		try {
			grabarDatos();

		} catch (CheckValidationException e) {
			mostrarErroresValidacion(e);

		} catch (CheckBussinesRulesException e) {
			mostrarErroresReglasNegocio(e);

		} catch (Exception e) {
			mostrarErroresGuardado(e);

		}
	}

	private void mostrarErroresGuardado(Exception e) {
		DialogManager.mostrarError(getShell(), e);
	}
	
	private void mostrarErroresReglasNegocio(CheckBussinesRulesException e) {
		// TODO Mostrar lista de reglas de negocio que han sido violadas

	}
	
	protected void mostrarErroresValidacion(CheckValidationException e) 
	{
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
	
	public void setEditable(Composite composite, boolean editable)
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
