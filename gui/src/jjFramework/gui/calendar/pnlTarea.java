package jjFramework.gui.calendar;

import java.awt.event.AWTEventListener;

import org.eclipse.swt.widgets.Composite;

import ILGestPojos.models.Tareas;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.wb.swt.layout.grouplayout.LayoutStyle;

public class pnlTarea extends Composite {

	private String Text;
	private Tareas tarea;
	private Label lblText;
	private CLabel lblButtom;
	private AWTEventListener eventListener;
	
	
	public pnlTarea(Composite parent, int style) {
		super(parent, style);
		
		lblText = new Label(this, SWT.NONE);
		lblText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				OnEditarTarea(tarea);
			}
		});
		lblText.setText("tarea 1");
		
		lblButtom = new CLabel(this, SWT.NONE);
		lblButtom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				OnEliminarTarea();
			}
		});
		lblButtom.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				lblButtom.setImage(SWTResourceManager.getImage(pnlTarea.class, "/resources/delete2_16.png"));
			}
			@Override
			public void mouseExit(MouseEvent e) {
				lblButtom.setImage(SWTResourceManager.getImage(pnlTarea.class, "/resources/delete_16.png"));
			}
		});
		lblButtom.setImage(SWTResourceManager.getImage(pnlTarea.class, "/resources/delete_16.png"));
		lblButtom.setText("");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.TRAILING)
				.add(groupLayout.createSequentialGroup()
					.add(1)
					.add(lblText, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
					.addPreferredGap(LayoutStyle.RELATED)
					.add(lblButtom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.LEADING)
				.add(groupLayout.createSequentialGroup()
					.add(groupLayout.createParallelGroup(GroupLayout.TRAILING)
						.add(groupLayout.createSequentialGroup()
							.add(1)
							.add(lblText, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.add(GroupLayout.LEADING, lblButtom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}

	protected void OnEliminarTarea() {
		EliminarTareaEvent event = new EliminarTareaEvent(this, tarea);
		eventListener.eventDispatched(event);
	}

	@Override public void setBackground(org.eclipse.swt.graphics.Color color) {
		lblText.setBackground(color);
		lblButtom.setBackground(color);
		super.setBackground(color);
	};
	
	public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
		lblText.setText(text);
	}

	public Tareas getTarea() {
		return tarea;
	}

	public void setTarea(Tareas tarea) {
		this.tarea = tarea;
		lblText.setToolTipText(tarea.getObservaciones());
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
	
	@Override protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
