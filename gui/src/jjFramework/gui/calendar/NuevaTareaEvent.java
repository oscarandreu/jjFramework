package jjFramework.gui.calendar;

import java.awt.AWTEvent;

import org.joda.time.DateTime;


/**
 * 
 * @author PC-1-Aipai
 *
 */
@SuppressWarnings("serial")
public class NuevaTareaEvent extends AWTEvent {

	private DateTime fecha;
	
	public NuevaTareaEvent(Object source, DateTime fecha) {
		super(source, 0);

		this.fecha = fecha;
	}

	public DateTime getFecha() {
		return fecha;
	}

	public void setFecha(DateTime fecha) {
		this.fecha = fecha;
	}

}
