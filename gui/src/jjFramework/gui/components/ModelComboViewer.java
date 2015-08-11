package jjFramework.gui.components;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;

import ILGestPojos.base.ModelBase;


public class ModelComboViewer<T extends ModelBase> extends ComboViewer
{
	private IStructuredSelection selection;

	private  String displayProperty;
	private Object bindedEntity;
	private String bindedProperty;
//	private List<T> entities;

	public ModelComboViewer(Combo combo, List<T> entities, String property) 
	{
		super(combo);

		//this.entities = entities;
		this.displayProperty = property;
		this.setContentProvider(new ArrayContentProvider());
		this.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) 
			{
				String text = "";
				try {
					Field 	f = element.getClass().getDeclaredField(displayProperty);
					f.setAccessible(true);

					text =  f.get(element).toString();

				} catch (Exception e) {
					e.printStackTrace();
				}

				return text;
			}
		});

		// content provider
		this.setInput(entities);
	}

	/** Devuelve el primer elementos seleccionado */
	@SuppressWarnings("unchecked")
	public T getSelectedItem()
	{
		return (T) ((IStructuredSelection) this.getSelection()).getFirstElement();
	}

	/** Devuelve una lista de elementos seleccionados */
	@SuppressWarnings("unchecked")
	public List<T> getSelectedItems()
	{
		return (List<T>) ((IStructuredSelection) this.getSelection());
	}

	@SuppressWarnings("unchecked")
	public void bind(Object entity, String property)
	{
		bindedEntity = entity;
		bindedProperty = property;

		//Si tenemos datos en la lita, y hay algún dato en el objeto bindeado, hacemos que el combo lo seleccione
		if( ((List<T>)getInput()).size() > 0)
		{
			try {
				//Obtenemos el objeto de la propiedad bindeada
				String metodo = "get"+ property.substring(0, 1).toUpperCase() + property.substring(1, property.length());
				Method dest =  bindedEntity.getClass().getDeclaredMethod(metodo);
				dest.setAccessible(true);
				T value = (T) dest.invoke(bindedEntity);
				
				if(value != null)
				{
					setSelection(value);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		getCombo().addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(bindedEntity != null )
				{
					try {
						T item = getSelectedItem();

						Field dest = bindedEntity.getClass().getDeclaredField(bindedProperty);
						dest.setAccessible(true);

						dest.set(bindedEntity, item);		

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}

		});
	}

	
	public void setSelection(T data) {
		this.selection = new StructuredSelection(data);
		super.setSelection(selection);
	};
	
}
