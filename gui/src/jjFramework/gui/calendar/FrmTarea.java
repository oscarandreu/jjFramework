package jjFramework.gui.calendar;

import org.eclipse.swt.widgets.Display;

import jjFramework.BLL.controllers.cCUAgenda;
import jjFramework.BLL.exceptions.CheckBussinesRulesException;
import jjFramework.BLL.exceptions.CheckValidationException;
import jjFramework.gui.forms.FrmBase;
import jjFramework.gui.utils.Enumerados.ModoEdicion;

import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import ILGestPojos.models.Agendas;
import ILGestPojos.models.Tareas;


/**
 * 
 * @author PC-1-Aipai
 *
 */
public class FrmTarea extends FrmBase{
	private Text txtDescripcion;
	private Text txtObservaciones;
	private Text txtColor;
	private Label lblNaranja;
	private Label lblAmarillo;
	private Label lblVerde;
	private Label lblAzul;
	private Label lblGris;
	private Tareas tarea = null;
	private DateTime dtDesde;
	private DateTime dtHasta;
	private Button chkAnual;
	private Button chkPublica;
	private Composite composite;
	private Agendas agenda;
	private org.joda.time.DateTime fecha;
	
	public void setFecha(org.joda.time.DateTime fecha)
	{
		this.fecha = fecha;
	}
	
	public void setAgenda(Agendas agenda)
	{
		this.agenda = agenda;
	}

	public FrmTarea(Display display) {
		super(display);
		setSize(521, 499);
		setTitulo("Edici\u00F3n de una Tarea");
		setImagenTitulo(SWTResourceManager.getImage(FrmTarea.class, "/resources/agenda_48.png"));
		
		composite = new Composite(this, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		FormData fd_composite = new FormData();
		fd_composite.left = new FormAttachment(0);
		fd_composite.right = new FormAttachment(100);
		fd_composite.bottom = new FormAttachment(0, 425);
		fd_composite.top = new FormAttachment(0, 61);
		composite.setLayoutData(fd_composite);
		
		Label label = new Label(composite, SWT.NONE);
		label.setText("Descripci\u00F3n:");
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label.setBounds(25, 26, 65, 15);
		
		txtDescripcion = new Text(composite, SWT.BORDER);
		txtDescripcion.setBounds(96, 23, 382, 21);
		
		Label lblFechaInicio = new Label(composite, SWT.NONE);
		lblFechaInicio.setAlignment(SWT.RIGHT);
		lblFechaInicio.setText("Desde:");
		lblFechaInicio.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblFechaInicio.setBounds(55, 60, 35, 15);
		
		Label lblObservaciones = new Label(composite, SWT.NONE);
		lblObservaciones.setAlignment(SWT.RIGHT);
		lblObservaciones.setText("Observaciones:");
		lblObservaciones.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblObservaciones.setBounds(10, 211, 80, 15);
		
		txtObservaciones = new Text(composite, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		txtObservaciones.setBounds(10, 232, 495, 122);
		
		Label lblHasta = new Label(composite, SWT.NONE);
		lblHasta.setAlignment(SWT.RIGHT);
		lblHasta.setText("hasta:");
		lblHasta.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblHasta.setBounds(205, 60, 35, 15);
		
		dtDesde = new DateTime(composite, SWT.BORDER);
		dtDesde.setBounds(96, 55, 89, 24);
		
		dtHasta = new DateTime(composite, SWT.BORDER);
		dtHasta.setBounds(247, 55, 89, 24);
		
		chkAnual = new Button(composite, SWT.CHECK);
		chkAnual.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		chkAnual.setBounds(96, 90, 93, 16);
		chkAnual.setText("\u00BFAnual?");
		
		chkPublica = new Button(composite, SWT.CHECK);
		chkPublica.setText("\u00BFP\u00FAblica?");
		chkPublica.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		chkPublica.setBounds(96, 117, 93, 16);
		
		Button cmdColor = new Button(composite, SWT.NONE);
		cmdColor.setImage(SWTResourceManager.getImage(FrmTarea.class, "/resources/color_16.png"));
		cmdColor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				mostrarSelectorColor();
			}
		});
		cmdColor.setBounds(328, 152, 25, 25);
		
		lblNaranja = new Label(composite, SWT.BORDER);
		lblNaranja.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				bindearColor(lblNaranja.getBackground());
			}
		});
		lblNaranja.setBackground(SWTResourceManager.getColor(255, 153, 102));
		lblNaranja.setBounds(198, 156, 17, 17);
		
		Label lblColor1 = new Label(composite, SWT.NONE);
		lblColor1.setText("Color:");
		lblColor1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblColor1.setAlignment(SWT.RIGHT);
		lblColor1.setBounds(55, 157, 35, 15);
		
		lblAmarillo = new Label(composite, SWT.BORDER);
		lblAmarillo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				bindearColor(lblAmarillo.getBackground());
			}
		});
		lblAmarillo.setBackground(SWTResourceManager.getColor(255, 255, 204));
		lblAmarillo.setBounds(224, 156, 17, 17);
		
		lblVerde = new Label(composite, SWT.BORDER);
		lblVerde.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				bindearColor(lblVerde.getBackground());
			}
		});
		lblVerde.setBackground(SWTResourceManager.getColor(204, 255, 153));
		lblVerde.setBounds(251, 156, 17, 17);
		
		lblAzul = new Label(composite, SWT.BORDER);
		lblAzul.setBackground(SWTResourceManager.getColor(204, 204, 255));
		lblAzul.setBounds(277, 156, 17, 17);
		lblAzul.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				bindearColor(lblAzul.getBackground());
			}
		});
		
		lblGris = new Label(composite, SWT.BORDER);
		lblGris.setAlignment(SWT.CENTER);
		lblGris.setBackground(SWTResourceManager.getColor(204, 204, 204));
		lblGris.setBounds(304, 156, 17, 17);
		lblGris.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				bindearColor(lblGris.getBackground());
			}
		});
		
		txtColor = new Text(composite, SWT.BORDER);
		txtColor.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		txtColor.setText("#FFFFE1");
		txtColor.setBounds(96, 154, 65, 21);
		
		Label label_1 = new Label(composite, SWT.SEPARATOR | SWT.VERTICAL);
		label_1.setBounds(180, 150, 2, 30);

	}
	
	@Override public void cargarDatos() throws Exception {

		controladora = cCUAgenda.start();

		if (modoEdicion == ModoEdicion.CREACION)
		{
			tarea = ((cCUAgenda) controladora).nuevo();
			tarea.setAgendas(agenda);
			tarea.setFechaInicio(fecha.toDate());
			tarea.setFechaFin(fecha.toDate());
		} else
		{
			tarea = ((cCUAgenda) controladora).obtener(key);
		}
	}

	@Override public void enlazarDatos()
	{
			this.bindingManager.resetBinds();
			this.bindingManager.bindControl(txtDescripcion, tarea, Tareas.DESCRIPCION);
			this.bindingManager.bindControl(dtDesde, tarea, Tareas.FECHAINICIO);
			this.bindingManager.bindControl(dtHasta, tarea, Tareas.FECHAFIN);
			this.bindingManager.bindControl(chkAnual, tarea, Tareas.ANUAL);
			this.bindingManager.bindControl(chkAnual, tarea, Tareas.PUBLICA);
			this.bindingManager.bindControl(txtObservaciones, tarea, Tareas.OBSERVACIONES);

			if ( tarea.getColor() != null )
			{
				Color color = jjFramework.gui.utils.ColorManager.HexadecimalToRGB(tarea.getColor());
				bindearColor(color);
			}
	}

	@Override protected void accionesGui() {
		boolean editable =  (modoEdicion == ModoEdicion.CREACION || modoEdicion == ModoEdicion.EDICION ) ? true : false;

		setEditable(this.composite, editable);
	};

	@Override protected void grabarDatos() throws CheckValidationException, CheckBussinesRulesException, Exception {

		tarea.setColor( jjFramework.gui.utils.ColorManager.RGBToHexadecimal(txtColor.getBackground()));

		super.grabarDatos();
	};
	
	private void bindearColor(Color color)
	{
		String hexColor = jjFramework.gui.utils.ColorManager.RGBToHexadecimal(color);
		
		txtColor.setText(hexColor.toUpperCase());
		txtColor.setBackground(color);
	}
	
	public  void mostrarSelectorColor()
	{
		ColorDialog color = new ColorDialog(getShell());
		RGB colorSelected = color.open();
		bindearColor(SWTResourceManager.getColor(colorSelected));
		
	}

}
