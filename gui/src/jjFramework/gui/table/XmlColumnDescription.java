package jjFramework.gui.table;

import org.eclipse.swt.graphics.*;


@SuppressWarnings("serial")
public class XmlColumnDescription implements java.io.Serializable{

	private String NombreCampo;
	private String Descripcion;
	private Class<?> Tipo;
	private String ValorPorDefecto;
	private String Formato;
	private Color BackColor;
	private Color ForeColor;
	private Integer Size = 0;

	public Integer getSize() {
		return Size;
	}
	public void setSize(Integer size) {
		Size = size;
	}
	public String getNombreCampo() {
		return NombreCampo;
	}
	public void setNombreCampo(String nombreCampo) {
		NombreCampo = nombreCampo;
	}

	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public Class<?> getTipo() {
		return Tipo;
	}
	public void setTipo(Class<?> tipo) {
		Tipo = tipo;
	}

	public XmlColumnDescription(){}
	public XmlColumnDescription(String NombreCampo, String Descripcion, Class<?> Tipo)
	{
		this.setNombreCampo(NombreCampo);
		this.setDescripcion(Descripcion);
		this.setTipo(Tipo);
	}
	
	public XmlColumnDescription(String NombreCampo, String Descripcion, Class<?> Tipo, int size)
	{
		this.setNombreCampo(NombreCampo);
		this.setDescripcion(Descripcion);
		this.setTipo(Tipo);
		this.setSize(size);
	}

	public XmlColumnDescription(String NombreCampo, String Descripcion, Class<?> Tipo, int size, String formato)
	{
		this.setNombreCampo(NombreCampo);
		this.setDescripcion(Descripcion);
		this.setTipo(Tipo);
		this.setSize(size);
		this.setFormato(formato);
	}
	
	public String getValorPorDefecto() {
		return ValorPorDefecto;
	}
	public void setValorPorDefecto(String valorPorDefecto) {
		ValorPorDefecto = valorPorDefecto;
	}

	public String getFormato() {
		return Formato;
	}
	public void setFormato(String formato) {
		Formato = formato;
	}

	public Color getBackColor() {
		return BackColor;
	}
	public void setBackColor(Color backColor) {
		BackColor = backColor;
	}

	public Color getForeColor() {
		return ForeColor;
	}
	public void setForeColor(Color foreColor) {
		ForeColor = foreColor;
	}

}
