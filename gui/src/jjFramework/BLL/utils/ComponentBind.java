package jjFramework.BLL.utils;

import org.eclipse.swt.widgets.Control;


/**
 * 
 * @author PC-1-Aipai
 *
 */
@SuppressWarnings("rawtypes")
public class ComponentBind 
{
	
	public ComponentBind(String propertyName, Class modelClass, Control control) 
	{
		this.propertyName = propertyName;
		this.modelClass = modelClass;
		this.control = control;
	}

	public String propertyName;
	public Class modelClass;
	public Control control;
	
	public ComponentBind(){}
}
