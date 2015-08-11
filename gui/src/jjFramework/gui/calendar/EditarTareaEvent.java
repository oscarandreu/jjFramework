package jjFramework.gui.calendar;

import java.awt.AWTEvent;

import ILGestPojos.models.Tareas;


/**
 * 
 * @author PC-1-Aipai
 *
 */
@SuppressWarnings("serial")
public class EditarTareaEvent  extends AWTEvent {

	private Tareas tarea;
	
	public EditarTareaEvent(Object source, Tareas tarea) {
		super(source, 0);
		this.setTarea(tarea);

	}

	public Tareas getTarea() {
		return tarea;
	}

	public void setTarea(Tareas tarea) {
		this.tarea = tarea;
	}
	

}
