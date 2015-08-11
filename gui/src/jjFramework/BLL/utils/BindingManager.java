package jjFramework.BLL.utils;

import java.util.ArrayList;
import java.util.List;

import jjFramework.gui.components.DocumentChooser;
import jjFramework.gui.components.ModelComboViewer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.*;
import org.eclipse.jface.databinding.swt.*;
import org.eclipse.jface.fieldassist.*;


public class BindingManager 
{
	public DataBindingContext bindingContext = new DataBindingContext();
	
	private List<ComponentBind> binds = new ArrayList<ComponentBind>();
	private List<ControlDecoration> warnings = new ArrayList<ControlDecoration>(); 


	public List<ComponentBind> getBinds() {
		return binds;
	}

	public void bindControl(Control control, Object entity, String propertyName)
	{
		if (control instanceof DateTime)
		{
			IObservableValue DateTimeObserveSelectionObserveWidget = SWTObservables.observeSelection(control);
			IObservableValue DateTimeObserveValue = BeansObservables.observeValue(entity, propertyName);
			bindingContext.bindValue(DateTimeObserveSelectionObserveWidget, DateTimeObserveValue, null, null);
			
		} else if (control instanceof Button)
		{
			IObservableValue chkAnualObserveSelectionObserveWidget = SWTObservables.observeSelection(control);
			IObservableValue festivoEsAnualObserveValue = BeansObservables.observeValue(entity, propertyName);
			bindingContext.bindValue(chkAnualObserveSelectionObserveWidget, festivoEsAnualObserveValue, null, null);
			
		} 
		else
		{
			IObservableValue textObserveWidget = SWTObservables.observeText(control, SWT.Modify);
			IObservableValue observeValue = BeansObservables.observeValue(entity, propertyName);
			bindingContext.bindValue(textObserveWidget, observeValue, null, null);	
		}
		
		binds.add(new ComponentBind(propertyName, entity.getClass(), control));
	}
	
	public void bindControl(ModelComboViewer<?> control, Object entity, String propertyName)
	{
		control.bind(entity, propertyName);
		binds.add(new ComponentBind(propertyName, entity.getClass(), control.getCombo()));
	}
	
	public void bindControl(DocumentChooser control, Object entity, String propertyName) throws Exception
	{
		control.bind(entity, propertyName);
		binds.add(new ComponentBind(propertyName, entity.getClass(), control.getControl()));
	}
	
	public void resetBinds()
	{
		bindingContext.dispose();
		bindingContext = new DataBindingContext();
		
		binds.clear();
		clearWarnings();
	}

	public Control getBindedControl(String propertyName, Class<?> modelClass)
	{
		Control control = null;
		for (ComponentBind bind : binds)
		{
			if(bind.propertyName.equals(propertyName) && bind.modelClass.equals(modelClass))
			{
				control = bind.control;
				break;
			}
		}

		return control;
	}

	public void addWarning(ControlDecoration warning)
	{
		warnings.add(warning);
	}
	
	public void clearWarnings()
	{
		for (ControlDecoration warning : warnings)
		{
			warning.hide();
			warning.dispose();
		}
		warnings.clear();
	}
}
