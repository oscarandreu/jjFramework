package jjFramework.gui.utils;


import java.awt.AWTEvent;

@SuppressWarnings("serial")
public class MenuExpedientesEvent extends AWTEvent {
	private byte[] codigo;
	private String codExpediente;
   
    public MenuExpedientesEvent(Object source, byte[] codigo, String codExpediente) {
    	super(source, 0);

    	this.setCodigo(codigo);
    	this.codExpediente = codExpediente;
    }

    public byte[] getCodigo() {
    	return codigo;
	}

	public void setCodigo(byte[] codigo) {
		this.codigo = codigo;
	}

	public String getCodExpediente() {
		return codExpediente;
	}

	public void setCodExpediente(String descripcion) {
		this.codExpediente = descripcion;
	}
}