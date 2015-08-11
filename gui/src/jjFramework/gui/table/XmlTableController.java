package jjFramework.gui.table;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jjFramework.BLL.utils.UtilidadesGenericas;
import jjFramework.gui.config.ConfiguradorBase;
import jjFramework.gui.config.GridConfig;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.joda.time.DateTime;



public class XmlTableController<T>
{

	private  GridConfig config;
	private Table tabla;

	protected List< T > data = new ArrayList< T >();
	protected List<TableEditor> editors = new ArrayList<TableEditor>();

	public XmlTableController(Table tabla, GridConfig config) 
	{
		this.tabla = tabla;
		this.config = config;
	}

	private void createColumns() 
	{
		int columnaActual = -1;
		for (XmlColumnDescription colDesc : config.getColumns()) 
		{
			columnaActual++;
			int colSize = colDesc.getSize() == 0 ? 75 : colDesc.getSize();

			TableColumn column = new TableColumn(tabla, SWT.NONE);
			column.setData(columnaActual);
			column.setText(colDesc.getDescripcion());
			column.setWidth(colSize);
			column.setResizable(true);
			column.setMoveable(true);
			column.addListener(SWT.Selection, new reOrderListener());
		}
	}

	public void showData() throws Exception
	{
		
		if (data != null) 
		{
			int fila = 0;
			for (T row : data) {
				int index = 0;

				TableItem item = new TableItem (this.tabla, SWT.NONE);
				item.setData(row);

				if ( fila % 2 != 1)
				{
					item.setBackground(SWTResourceManager.getColor(255, 249, 239));
				} else
				{
					item.setBackground(SWTResourceManager.getColor(255, 255, 255));
				}

				for (XmlColumnDescription col : config.getColumns()) 
				{	
					Object valor = getValue(col.getNombreCampo(), row);

					if(col.getBackColor() != null)
						item.setBackground(col.getBackColor());
					if(col.getForeColor() != null)
						item.setForeground(col.getForeColor());

					formatItemValue(valor, col, index, item);


					index ++;
				}
				fila++;
			}

		}
	}	
	
	private void formatItemValue(Object valor, XmlColumnDescription col, int index, TableItem item)
	{
		
		if ( col.getTipo() == Image.class && valor != null )
		{
			Image imagen = (UtilidadesGenericas.ImagenFromByteArray(null, (byte[]) valor));
			item.setImage(index, imagen);
			
		} else if(col.getTipo() == Date.class && valor != null)
		{
			SimpleDateFormat sdf=new SimpleDateFormat((col.getFormato() == null) ? ConfiguradorBase.FormatoDate : col.getFormato());
			item.setText(index,sdf.format((Date) valor));
		}	
		else if(col.getTipo() == DateTime.class && valor != null)
		{
			SimpleDateFormat sdf=new SimpleDateFormat((col.getFormato() == null) ? ConfiguradorBase.FormatoDate : col.getFormato());
			item.setText(index,sdf.format((Date) valor));
		}
		else if(col.getTipo() == Boolean.class || col.getTipo() == boolean.class)
		{
			if(valor == null)
				valor = false;

			TableEditor editor = new TableEditor (tabla);
			editors.add(editor);
			Button checkButton = new Button(tabla,SWT.CHECK);
			checkButton.setEnabled(false);
			checkButton.setSelection( (Boolean) valor);
			checkButton.pack();

			editor.minimumWidth = checkButton.getSize ().x;
			editor.horizontalAlignment = SWT.CENTER;
			editor.setEditor(checkButton, item, index);
		}
		else if( valor != null)
			item.setText(index, valor.toString());
	}

	private Object getValue(String nombreCampo, Object row) throws Exception
	{
		Object retvalue = null;	
		String nuevoCampo = null;
		String metodo = null;

		if(nombreCampo.contains("."))
		{
			nuevoCampo = nombreCampo.substring(nombreCampo.indexOf(".") + 1);
			nombreCampo = nombreCampo.substring(0, nombreCampo.indexOf("."));
		}

		try {
			
			Field f= row.getClass().getDeclaredField(nombreCampo);
			if (f.getGenericType().equals(boolean.class) )
			{
				 metodo = "is"+ nombreCampo.substring(0, 1).toUpperCase() + nombreCampo.substring(1, nombreCampo.length());
			} else
			{
				 metodo = "get"+ nombreCampo.substring(0, 1).toUpperCase() + nombreCampo.substring(1, nombreCampo.length());
			}
			
			
			Method m =  row.getClass().getDeclaredMethod(metodo);
			//m.setAccessible(true);
			retvalue = m.invoke(row);

			// Field f= row.getClass().getDeclaredField(nombreCampo);
			// f.setAccessible(true);
			// retvalue =f.get(row);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error en XmlTableController.getValue");
		}

		if(nuevoCampo!= null)
		{
			retvalue = getValue(nuevoCampo, retvalue);
		}

		return retvalue;
	}

	public void setData(List<T> data ) throws Exception
	{
		this.data =  data;
		//		viewer = new TableViewer(tabla.getParent(), SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns();
		//		viewer.setContentProvider(new ArrayContentProvider());

		// Layout the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		tabla.setLayoutData(gridData);

		showData();
	}

	public void refrescarData(List<T> data) throws Exception
	{
		for (TableEditor  t : editors) {
			t.getEditor().dispose();
		}
		editors.clear();
		
		this.tabla.clearAll();
		this.data.clear();
		this.tabla.setItemCount(0);
		this.data =  data;

		showData();
	}

	/**
	 * 
	 * @author PC-1-Aipai
	 *
	 * Esta clase es el Listener que ordena las columnas del Table asociado...
	 */
	private class reOrderListener implements Listener{

		  boolean reOrder = true;

		  @Override
		  public void handleEvent(Event e) {

			  TableColumn columna = (TableColumn) e.widget;

			  TableItem[] items = tabla.getItems();
			  Collator collator = Collator.getInstance(Locale.getDefault());
			  for (int i = 1; i < items.length; i++) {
				  String value1 = items[i].getText((int)columna.getData());
				  for (int j = 0; j < i; j++) {
					  String value2 = items[j].getText((int)columna.getData());

					  if ( reOrder )
					  {
						  if (collator.compare(value1, value2) < 0) {
	        				String[] values = new String[tabla.getColumnCount()];
	        				for (int col = 0; col < tabla.getColumnCount(); col++)
	        				{
	        					values[col] =  items[i].getText(col);
	        				}
	        				TableItem item = new TableItem(tabla, SWT.NONE, j);
	        				item.setText(values);
	        				item.setData(items[i].getData());
	        				items[i].dispose();
	        				items = tabla.getItems();
	        				break;
	        			}
	        		} else
	        		{
	        			if (collator.compare(value1, value2) > 0) {
	        				String[] values = new String[tabla.getColumnCount()];
	        				for (int col = 0; col < tabla.getColumnCount(); col++)
	        				{
	        					values[col] =  items[i].getText(col);
	        				}
	        				TableItem item = new TableItem(tabla, SWT.NONE, j);
	        				item.setText(values);
	        				item.setData(items[i].getData());
	        				items[i].dispose();
	        				items = tabla.getItems();
	        				break;
	        			}
	        		}
	        	}
	        }
	        reOrder = !reOrder;
	      }
	
	}
}
