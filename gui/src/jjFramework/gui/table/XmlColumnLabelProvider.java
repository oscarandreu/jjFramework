package jjFramework.gui.table;

import java.lang.reflect.Method;

import org.eclipse.jface.viewers.ColumnLabelProvider;



public class XmlColumnLabelProvider extends ColumnLabelProvider
{
	private String nombreCampo;
	
	public XmlColumnLabelProvider(String nombreCampo) 
	{
		this.nombreCampo = nombreCampo;	
	}
	
	@Override
	public String getText(Object element) {
		return getValue(this.nombreCampo, element).toString();
	}
	
	private Object getValue(String campo, Object element)
	{
		Object retvalue = null;	
		String nuevoCampo = null;

		if(campo.contains("."))
		{
			nuevoCampo = campo.substring(campo.indexOf(".") + 1);
			campo = campo.substring(0, campo.indexOf("."));
		}

		try {
			String metodo = "get"+ campo.substring(0, 1).toUpperCase() + campo.substring(1, campo.length());
			Method m =  element.getClass().getDeclaredMethod(metodo);
			//m.setAccessible(true);
			retvalue = m.invoke(element);

			// Field f= row.getClass().getDeclaredField(nombreCampo);
			// f.setAccessible(true);
			// retvalue =f.get(row);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if(nuevoCampo!= null)
		{
			retvalue = getValue(nuevoCampo, retvalue);
		}

		return retvalue;
	}
}
