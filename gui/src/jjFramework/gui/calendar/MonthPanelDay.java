package jjFramework.gui.calendar;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;
import org.joda.time.DateTime;

import ILGestPojos.models.Tareas;

public class MonthPanelDay  extends Composite
{
	public enum TypeOfCalendarDay
	{
		PREVIOUS_MONTH,
		CURRENT_MONTH,
		NEXT_MONTH
	}


	public static Color currentMonthDaysBackGroundColor = SWTResourceManager.getColor(245, 245, 245);
	public static Color currentMonthDaysForeGroundColor = SWTResourceManager.getColor(SWT.COLOR_BLACK);
	public static Color otherMonthDaysBackGroundColor = SWTResourceManager.getColor(250,250,250);
	public static Color otherMonthDaysForeGroundColor = SWTResourceManager.getColor(SWT.COLOR_GRAY);
	public static Color todayBackColor = SWTResourceManager.getColor(225,213,199);
	public static Color holyDayBackColor = SWTResourceManager.getColor(229,249,255);


	public TypeOfCalendarDay typeOfCalendarDay;

	private List<Tareas> tasks = new ArrayList<Tareas>();
	private List<GridData> gd_tasks = new ArrayList<GridData>();

	private DateTime date;
	private Color selectedBgColor =SWTResourceManager.getColor(255, 235, 153);
	private Composite composite;
	private GridData gd_composite;
	private MonthPanelDay selfReference;
	private CLabel lblDayNumber;
	private Composite barraSuperior;
	private AWTEventListener eventListener;

	public DateTime getDate() {
		return date;
	}
	public void setDate(DateTime date) {
		this.date = date;
		lblDayNumber.setText(date.dayOfMonth().getAsShortText());
	}

	public MonthPanelDay(Composite parent, int style, TypeOfCalendarDay typeOfCalendarDay)
	{
		super(parent, style);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.verticalSpacing = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		setLayout(gridLayout);

		selfReference = this;
		addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				for (GridData gd_task : gd_tasks) {
					gd_task.widthHint = selfReference.getSize().x;
				}
				barraSuperior.setSize(selfReference.getSize().x,barraSuperior.getSize().y);
			}
		});

		barraSuperior = new Composite(this, SWT.NONE);
		GridData gd_barraSuperior = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_barraSuperior.widthHint = 137;
		gd_barraSuperior.heightHint = 17;
		barraSuperior.setLayoutData(gd_barraSuperior);

		lblDayNumber = new CLabel(barraSuperior, SWT.CENTER);
		lblDayNumber.setText("31");
		GroupLayout gl_barraSuperior = new GroupLayout(barraSuperior);
		gl_barraSuperior.setHorizontalGroup(
			gl_barraSuperior.createParallelGroup(GroupLayout.LEADING)
				.add(gl_barraSuperior.createSequentialGroup()
					.add(lblDayNumber, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(117, Short.MAX_VALUE))
		);
		gl_barraSuperior.setVerticalGroup(
			gl_barraSuperior.createParallelGroup(GroupLayout.LEADING)
				.add(gl_barraSuperior.createSequentialGroup()
					.add(lblDayNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		barraSuperior.setLayout(gl_barraSuperior);

		composite = new Composite(this, SWT.NONE);
		composite.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				nuevaTarea();
			}
		});

		GridLayout gl_composite = new GridLayout(1, false);
		gl_composite.horizontalSpacing = 0;
		gl_composite.verticalSpacing = 0;
		gl_composite.marginWidth = 0;
		gl_composite.marginHeight = 0;
		composite.setLayout(gl_composite);
		gd_composite = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_composite.heightHint = 84;
		gd_composite.widthHint = 139;
		composite.setLayoutData(gd_composite);

		this.typeOfCalendarDay = typeOfCalendarDay;

		switch (typeOfCalendarDay) {
		case CURRENT_MONTH:
			setForeground(currentMonthDaysForeGroundColor);
			composite.setBackground(currentMonthDaysBackGroundColor);
			composite.addMouseTrackListener(new MouseTrackAdapter() {
				@Override
				public void mouseEnter(MouseEvent e) {
					composite.setBackground(selectedBgColor);
				}
				@Override
				public void mouseExit(MouseEvent e) {
					composite.setBackground(currentMonthDaysBackGroundColor);
				}
			});
			break;

		case NEXT_MONTH:
			setForeground(otherMonthDaysForeGroundColor);
			composite.setBackground(otherMonthDaysBackGroundColor);
			break;

		case PREVIOUS_MONTH:
			setForeground(otherMonthDaysForeGroundColor);
			composite.setBackground(otherMonthDaysBackGroundColor);
			break;
		}


		//		CLabel task = new CLabel(composite, SWT.BORDER);
		//		task.setBackground( SWTResourceManager.getColor(255, 204, 102));
		//		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		//		gd_lblNewLabel.widthHint = gd_composite.widthHint;
		//		task.setLayoutData(gd_lblNewLabel);
		//		task.setText("Test");
	}

	@Override public void setSize(Point p)
	{
		gd_composite.heightHint = p.y;
		gd_composite.widthHint = p.x;
	}

	public void setToday()
	{
		setBackground(todayBackColor);
	}

	public void setHoliday()
	{
		setBackground(holyDayBackColor);
	}

	@Override public void setBackground(Color color) {
		super.setBackground(color);
		//		composite.setBackground(color);
		lblDayNumber.setBackground(getBackground());
		barraSuperior.setBackground(getBackground());
	};

	@Override public void setForeground(Color color) {
		super.setForeground(color);
		lblDayNumber.setForeground(getForeground());		
	};

	public void addTask(Tareas task)
	{
		pnlTarea label = new pnlTarea(composite, SWT.NONE);
		label.setBackground(jjFramework.gui.utils.ColorManager.HexadecimalToRGB(task.getColor()));
		label.setTarea(task);
		label.addEventListener(new AWTEventListener() {
			
			@Override
			public void eventDispatched(AWTEvent event) {

				if ( event instanceof EditarTareaEvent )
				{
					editarTarea(((EditarTareaEvent) event).getTarea());
				} else if ( event instanceof EliminarTareaEvent )
				{
					eliminarTarea(((EliminarTareaEvent) event).getTarea());
				}
			}
		});

		GridData gd_task = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_task.widthHint = gd_composite.widthHint;
		gd_task.heightHint = 20;
		label.setLayoutData(gd_task);
		label.setText(task.getDescripcion());

		tasks.add(task);
		gd_tasks.add(gd_task);
	}

	protected void eliminarTarea(Tareas tarea) {
		OnEliminarTarea(tarea);
	}
	
	protected void editarTarea(Tareas task) {
		OnEditarTarea(task);
	}
	
	protected void nuevaTarea() {
		OnNuevaTarea(date);
	}
	
	public AWTEventListener getListener() {
		return eventListener;
	}
	
	public void addEventListener(AWTEventListener evento) {
		this.eventListener = evento;
	}
	
	protected void OnEditarTarea(Tareas tarea)
	{
		EditarTareaEvent event = new EditarTareaEvent(this, tarea);
		eventListener.eventDispatched(event);
	}
	
	protected void OnEliminarTarea(Tareas tarea)
	{
		EliminarTareaEvent event = new EliminarTareaEvent(this, tarea);
		eventListener.eventDispatched(event);
	}
	
	protected void OnNuevaTarea(DateTime date)
	{
		NuevaTareaEvent event = new NuevaTareaEvent(this, date);
		eventListener.eventDispatched(event);
	}
	

}
