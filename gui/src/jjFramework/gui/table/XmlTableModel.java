package jjFramework.gui.table;

import java.lang.reflect.Method;
import java.util.List;

import jjFramework.gui.config.GridConfig;


public class XmlTableModel<T> extends GenericTableModel<T> 
{
	private static final long serialVersionUID = 1L;


	/**Configuración del grid*/
	private  GridConfig config;


	/**Constructor opr defecto*/
	public XmlTableModel( final List< T > list, GridConfig config) throws Exception 
	{
		super();

		data.addAll( list );
		//if (!(list== null || list.size() == 0))
		dataClass = list.get(0).getClass();

		this.config = config;
//		montarCampos();
	}

//	private void montarCampos() throws NoSuchFieldException, Exception
//	{
//		List<Field> camposTmp = Arrays.asList(dataClass.getDeclaredFields());
//
//		for(XmlColumnDescription col : config.getColumns())
//		{
//			for(Field f:camposTmp)
//			{
//				if(contieneCampo(f, col.getNombreCampo()))
//				{
//					Fields.add(f);
//					break;
//				}
//			}
//		}
//	}

//	private Boolean contieneCampo(Field f, String nombreCampo) throws NoSuchFieldException, SecurityException 
//	{
//		Boolean retValue = false;		
//
//		if(nombreCampo.contains("."))
//		{
//			String nuevoCampo = nombreCampo.substring(nombreCampo.indexOf(".")+1);
//
//			Class<?> c = f.getDeclaringClass();
//			Field inf = null;
//
//			inf = c.getDeclaredField(nuevoCampo);
//
//			return contieneCampo(inf, nuevoCampo);
//		}
//		if( nombreCampo.equals(f.getName()))
//			retValue=true;
//
//		
//		return retValue;
//	}


	@Override
	public Object getValueAt(int rowIndex, int columnIndex) 
	{
		String nombreCampo =  config.getColumns().get(columnIndex).getNombreCampo();
		T row = get(rowIndex);

		return getValue(nombreCampo, row);
	}

	private Object getValue(String nombreCampo, Object row)
	{
		Object retvalue = null;	
		String nuevoCampo = null;

		if(nombreCampo.contains("."))
		{
			nuevoCampo = nombreCampo.substring(nombreCampo.indexOf(".") + 1);
			nombreCampo = nombreCampo.substring(0, nombreCampo.indexOf("."));
		}

		try {
			String metodo = "get"+ nombreCampo.substring(0, 1).toUpperCase() + nombreCampo.substring(1, nombreCampo.length());
			Method m =  row.getClass().getDeclaredMethod(metodo);
			//m.setAccessible(true);
			retvalue = m.invoke(row);

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

	@Override
	public String getColumnName(int columnIndex) 
	{
		return  config.getColumns().get(columnIndex).getDescripcion();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) 
	{
		//		Class<?> c;
		//		String className =  config.getColumns().get(columnIndex).getTipo();
		//		try {
		//			c = Class.forName(className);
		//			
		//		} catch (ClassNotFoundException e) {
		//			return String.class;
		//		}
		//		
		//		return  c;
		return config.getColumns().get(columnIndex).getTipo();
	}


	@Override
	public int getColumnCount() {
		return config.getColumns().size();
	}
}
