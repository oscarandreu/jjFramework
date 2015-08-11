package jjFramework.gui.calendar;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.widgets.ExpandItem;
import swing2swt.layout.FlowLayout;

public class CalendarDayViewPanel extends Composite {

private ExpandBar expbLongTasks;
private ExpandItem xpndTareasLargas;
private Composite cmpTareasLargas;	


	public CalendarDayViewPanel(Composite parent, int style) {
		super(parent, style);
		
		expbLongTasks = new ExpandBar(this, SWT.NONE);
		expbLongTasks.setSpacing(0);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.LEADING)
				.add(expbLongTasks, GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.LEADING)
				.add(groupLayout.createSequentialGroup()
					.add(expbLongTasks, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(359, Short.MAX_VALUE))
		);
		
		xpndTareasLargas = new ExpandItem(expbLongTasks, SWT.NONE);
		xpndTareasLargas.setExpanded(true);
		
		cmpTareasLargas = new Composite(expbLongTasks, SWT.EMBEDDED);
		xpndTareasLargas.setControl(cmpTareasLargas);
		cmpTareasLargas.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		xpndTareasLargas.setHeight(200);
		setLayout(groupLayout);

//		addLongTask(new CalendarTask("Tarea de prueba"));
	}

	/** Se añade una tarea de más de un día de duración, por lo queva al panel especifico para ese tipo de tareas*/
//	public void addLongTask(CalendarTask task)
//	{
//		CLabel label = new CLabel(cmpTareasLargas, SWT.BORDER);
//		label.setBackground(task.Color);
//		GridData gd_task = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
//		 gd_task.widthHint = cmpTareasLargas.getSize().x;
//		 gd_task.heightHint = 20;
//		 cmpTareasLargas.setSize(cmpTareasLargas.getSize().x, cmpTareasLargas.getSize().y + 20);
//		gd_task.widthHint = gd_task.widthHint;
//		task.gd_task = gd_task;
//		label.setLayoutData(gd_task);
//		label.setText(task.Description);
//	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
