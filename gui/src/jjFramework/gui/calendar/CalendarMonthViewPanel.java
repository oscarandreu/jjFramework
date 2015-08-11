package jjFramework.gui.calendar;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jjFramework.BLL.controllers.cCUAgenda;
import jjFramework.gui.calendar.MonthPanelDay.TypeOfCalendarDay;
import jjFramework.gui.utils.DialogManager;
import jjFramework.gui.utils.Enumerados.ModoEdicion;
import jjFramework.gui.utils.RefrescarEvent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;
import org.eclipse.wb.swt.layout.grouplayout.LayoutStyle;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.MutableDateTime;

import ILGestPojos.models.Agendas;
import ILGestPojos.models.Tareas;


/**
 * 
 * @author PC-1-Aipai
 *
 */
public class CalendarMonthViewPanel extends Composite {

	public static Color headerBackGroundColor = SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND);
	public static Color headerForeGroundColor = SWTResourceManager.getColor(SWT.COLOR_WHITE);


	private String[] weekDays = {"Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo"};
	private ListOfDays holidays =null; 

	private int columnWidth = 140;
	private int ColumnHeigth = 100;
	private MutableDateTime calendar = MutableDateTime.now();
	private List<MonthPanelDay> days = new ArrayList<MonthPanelDay>();
	private Composite panelCalendario;
	private CLabel lblMesAnio;
	private int numFilas = -1;
	private int lockYear = -1;

	//Variable para el resize
	private GridData gd_lblNewLabel;
	private GridData gd_label;
	private GridData gd_label_1;
	private GridData gd_label_2;
	private GridData gd_label_3;
	private GridData gd_label_4;
	private GridData gd_label_5;
	private Button cmdPreviousMonth;
	private Button cmdNextMonth;
	private Button cmdToday;

	//Variables de la agenda propiamente dicha
	private Agendas agenda;
	private List<Tareas> tareas = new ArrayList<Tareas>();
	private AWTEventListener eventListener;

	public Agendas getAgenda() {
		return agenda;
	}

	public void setAgenda(Agendas agendas) {
		this.tareas.clear();
		this.agenda = agendas;

		Iterator<Tareas> it = agendas.getTareases().iterator();
		while(it.hasNext())
		{
			tareas.add(it.next());
		}
	}

	public Boolean isYearLocked() {
		return (lockYear != -1 ? true : false);
	}
	public void lockYear(int lockYear) {
		this.lockYear = lockYear;
		this.cmdToday.setEnabled(false);
	}

	public List<DateTime> getHolidays() {
		return holidays;
	}
	public void setHolidays(ListOfDays holidays) {
		this.holidays = holidays;
	}

	public CalendarMonthViewPanel(Composite parent, int style) {
		super(parent, style);
		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				resize();
			}
		});

		addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				resize();
			}
		});
		createHeader();
	}

	public void createHeader()
	{
		panelCalendario = new Composite(this, SWT.NONE);
		panelCalendario.setDragDetect(false);
		GridLayout gl_composite = new GridLayout(7, true);
		gl_composite.verticalSpacing = 1;
		gl_composite.marginWidth = 0;
		gl_composite.marginHeight = 0;
		gl_composite.horizontalSpacing = 1;
		panelCalendario.setLayout(gl_composite);

		CLabel lblNewLabel = new CLabel(panelCalendario, SWT.NONE);
		gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel.heightHint = 25;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setForeground(headerForeGroundColor);
		lblNewLabel.setBackground(headerBackGroundColor);
		lblNewLabel.setText(weekDays[0]);

		CLabel label = new CLabel(panelCalendario, SWT.NONE);
		gd_label = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label.heightHint = 25;
		label.setLayoutData(gd_label);
		label.setForeground(headerForeGroundColor);
		label.setBackground(headerBackGroundColor);
		label.setText(weekDays[1]);

		CLabel label_1 = new CLabel(panelCalendario, SWT.NONE);
		gd_label_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_1.heightHint = 25;
		label_1.setLayoutData(gd_label_1);
		label_1.setForeground(headerForeGroundColor);
		label_1.setBackground(headerBackGroundColor);
		label_1.setText(weekDays[2]);

		CLabel label_2 = new CLabel(panelCalendario, SWT.NONE);
		gd_label_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_2.heightHint = 25;
		label_2.setLayoutData(gd_label_2);
		label_2.setForeground(headerForeGroundColor);
		label_2.setBackground(headerBackGroundColor);
		label_2.setText(weekDays[3]);

		CLabel label_3 = new CLabel(panelCalendario, SWT.NONE);
		gd_label_3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_3.heightHint = 25;
		label_3.setLayoutData(gd_label_3);
		label_3.setForeground(headerForeGroundColor);
		label_3.setBackground(headerBackGroundColor);
		label_3.setText(weekDays[4]);

		CLabel label_4 = new CLabel(panelCalendario, SWT.NONE);
		gd_label_4 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_4.heightHint = 25;
		label_4.setLayoutData(gd_label_4);
		label_4.setForeground(headerForeGroundColor);
		label_4.setBackground(headerBackGroundColor);
		label_4.setText(weekDays[5]);

		CLabel label_5 = new CLabel(panelCalendario, SWT.NONE);
		gd_label_5 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_5.heightHint = 25;
		label_5.setLayoutData(gd_label_5);
		label_5.setForeground(headerForeGroundColor);
		label_5.setBackground(headerBackGroundColor);
		label_5.setText(weekDays[6]);

		cmdPreviousMonth = new Button(this, SWT.ARROW | SWT.LEFT);
		cmdPreviousMonth.setDragDetect(false);
		cmdPreviousMonth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				previousMonth();
			}
		});
		cmdPreviousMonth.setText("New Button");

		cmdNextMonth = new Button(this, SWT.ARROW | SWT.RIGHT);
		cmdNextMonth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				nextMonth();
			}
		});
		cmdNextMonth.setText("New Button");

		lblMesAnio = new CLabel(this, SWT.NONE);
		lblMesAnio.setText("New Label");

		cmdToday = new Button(this, SWT.FLAT);
		cmdToday.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setToday();
			}
		});
		cmdToday.setText("Hoy");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(GroupLayout.LEADING)
				.add(groupLayout.createSequentialGroup()
						.add(groupLayout.createParallelGroup(GroupLayout.LEADING)
								.add(groupLayout.createSequentialGroup()
										.addContainerGap()
										.add(cmdToday)
										.add(18)
										.add(cmdPreviousMonth, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(LayoutStyle.RELATED)
										.add(cmdNextMonth)
										.add(11)
										.add(lblMesAnio, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE))
										.add(panelCalendario, GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE))
										.add(0))
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(GroupLayout.LEADING)
				.add(groupLayout.createSequentialGroup()
						.add(groupLayout.createParallelGroup(GroupLayout.LEADING)
								.add(groupLayout.createSequentialGroup()
										.add(17)
										.add(lblMesAnio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.add(groupLayout.createSequentialGroup()
												.add(16)
												.add(cmdNextMonth))
												.add(groupLayout.createSequentialGroup()
														.add(16)
														.add(cmdPreviousMonth))
														.add(groupLayout.createSequentialGroup()
																.add(16)
																.add(cmdToday)))
																.add(18)
																.add(panelCalendario, GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE))
				);
		groupLayout.linkSize(new Control[] {cmdPreviousMonth, cmdNextMonth, cmdToday}, GroupLayout.VERTICAL);
		groupLayout.linkSize(new Control[] {cmdPreviousMonth, cmdNextMonth}, GroupLayout.HORIZONTAL);
		setLayout(groupLayout);

		//		columnWidth = composite.getSize().x / 7;
		gd_lblNewLabel.widthHint = columnWidth;
		gd_label.widthHint = columnWidth;
		gd_label_1.widthHint = columnWidth;
		gd_label_2.widthHint = columnWidth;
		gd_label_3.widthHint = columnWidth;
		gd_label_4.widthHint = columnWidth;
		gd_label_5.widthHint = columnWidth;
	}

	public void setDays() 
	{				
		this.clearAll();

		MutableDateTime calDays = new MutableDateTime(calendar);
		calDays.setDayOfMonth(1);
		int computoDiasMesAnterior = calDays.getDayOfWeek() -1;
		if(computoDiasMesAnterior > 0)
		{
			calDays.addMonths(-1);	
			int diasMesAnterior = calDays.dayOfMonth().getMaximumValue();
			calDays.setDayOfMonth(diasMesAnterior - computoDiasMesAnterior + 1);
		}
		int finalMonth = calendar.getMonthOfYear() == 12 ? 1 : calendar.getMonthOfYear()+1;
		numFilas = 0;
		TypeOfCalendarDay t = TypeOfCalendarDay.PREVIOUS_MONTH;

		lblMesAnio.setText(calendar.monthOfYear().getAsText() + " de " + calendar.getYear());

		for (int i = 0; i < 42; i++) 
		{
			if( i % 7 == 0)
			{
				//Comprobamos si ya estamos en dias del mes que viene
				if(calDays.getMonthOfYear()  == finalMonth)
					break;
				numFilas++;
			}

			if(calDays.getDayOfMonth() == 1)
				t= (t == TypeOfCalendarDay.PREVIOUS_MONTH) ? TypeOfCalendarDay.CURRENT_MONTH : TypeOfCalendarDay.NEXT_MONTH;


			days.add(getDayPanel(i, calDays.toDateTime(), t));
			calDays.addDays(1);
		}
		setTasks();
		resize();
	}

	private void setTasks()
	{
		if(tareas.size() > 0)
		{
			DateTimeComparator	d = DateTimeComparator.getDateOnlyInstance();

			for(MonthPanelDay dayPanel: days){
				for (Tareas t: tareas) {
					if(d.compare( new DateTime(t.getFechaInicio()), dayPanel.getDate()) <=0 && d.compare(dayPanel.getDate(),  new DateTime(t.getFechaFin()))<= 0)
					{
						dayPanel.addTask(t);
					}
				}
			}
		}
	}

	private MonthPanelDay getDayPanel(int index,DateTime dayDate, TypeOfCalendarDay tipo)
	{
		MonthPanelDay dayPanel = new MonthPanelDay(panelCalendario, SWT.NONE, tipo);
		dayPanel.addEventListener(new AWTEventListener(){
			@Override
			public void eventDispatched(AWTEvent evento) {
				try {
					if ( evento instanceof NuevaTareaEvent )
					{
						nuevaTarea(((NuevaTareaEvent)evento).getFecha());
						
					} else  if ( evento instanceof EditarTareaEvent )
					{
						editarTarea(((EditarTareaEvent)evento).getTarea());
					} else  if ( evento instanceof EliminarTareaEvent )
					{
						eliminarTarea(((EliminarTareaEvent)evento).getTarea());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
		dayPanel.setSize(new Point(columnWidth,ColumnHeigth));
		dayPanel.setDate(dayDate);

		DateTimeComparator	d = DateTimeComparator.getDateOnlyInstance();
		if(d.compare(dayDate, DateTime.now()) == 0)
			dayPanel.setToday();

		if(holidays != null && holidays.contains(dayDate))
			dayPanel.setHoliday();

		return dayPanel;
	}

	protected void eliminarTarea(Tareas tarea) {

		try {

			if (DialogManager.mostrarPregunta(getShell(), "Esta acción eliminará la tarea, ¿Desea continuar?"))
			{
				cCUAgenda ccu = cCUAgenda.start();
				ccu.borrar(tarea);
				ccu.stop();

				OnRefrescarData();
			}
		} catch (Exception e) {
			DialogManager.mostrarError(getShell(), e);
		}
	}

	protected void editarTarea(Tareas tarea) {
		FrmTarea frm = new FrmTarea(Display.getDefault());
		frm.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				OnRefrescarData();
			}
		});
		frm.setAgenda(agenda);
		frm.mostrarVista(this, ModoEdicion.EDICION, tarea.getCodigo());		
	}

	protected void nuevaTarea(DateTime fecha) {
		FrmTarea frm = new FrmTarea(Display.getDefault());
		frm.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				OnRefrescarData();
			}
		});
		frm.setFecha(fecha);
		frm.setAgenda(agenda);
		frm.mostrarVista(this, ModoEdicion.CREACION, null);		
	}

	private void setToday()
	{
		calendar.setDate(DateTime.now());
		clearAll();
		setDays(); 
		layout(true, true);
	}

	public void nextMonth()
	{
		calendar.addMonths(1);
		
		cmdPreviousMonth.setEnabled(true);
		if(isYearLocked() && calendar.getMonthOfYear() == 12)
			cmdNextMonth.setEnabled(false);

		clearAll();
		setDays(); 
		layout(true, true);
	}

	public void previousMonth()
	{
		calendar.addMonths(-1);
		
		cmdNextMonth.setEnabled(true);
		if(isYearLocked() && calendar.getMonthOfYear() == 1)
			cmdPreviousMonth.setEnabled(false);

		clearAll();
		setDays(); 
		resize();
		layout(true, true);
	}

	public void nextYear()
	{
		calendar.addYears(1);
		clearAll();
		setDays(); 
		layout(true, true);
	}

	public void previousYear()
	{
		calendar.addYears(-1);
		clearAll();
		setDays(); 
		layout(true, true);
	}

	private void clearAll() 
	{
		for (MonthPanelDay p : days) {
			p.dispose();
			p = null;
		}
		days.clear();
	}

	public void resize()
	{
		columnWidth = (getSize().x == 0) ? columnWidth  : getSize().x / 7;
		ColumnHeigth = (getSize().y == 0) ? ColumnHeigth  : ( getSize().y  - (numFilas*35)) / numFilas;

		gd_lblNewLabel.widthHint = columnWidth;
		gd_label.widthHint = columnWidth;
		gd_label_1.widthHint = columnWidth;
		gd_label_2.widthHint = columnWidth;
		gd_label_3.widthHint = columnWidth;
		gd_label_4.widthHint = columnWidth;
		gd_label_5.widthHint = columnWidth;

		for (MonthPanelDay p  : days) {
			if(p != null)
				p.setSize(new Point(columnWidth,ColumnHeigth));
		}
		layout(true, true);
	}

	public AWTEventListener getListener() {
		return eventListener;
	}
	
	public void addEventListener(AWTEventListener evento) {
		this.eventListener = evento;
	}
	
	protected void OnRefrescarData()
	{
		RefrescarEvent event = new RefrescarEvent(this);
		eventListener.eventDispatched(event);
	}
	
	protected void checkSubclass() {}
}
