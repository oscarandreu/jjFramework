package jjFramework.gui.components;

import java.util.ArrayList;
import java.util.List;

import jjFramework.BLL.utils.UtilidadesGenericas;
import jjFramework.gui.utils.Enumerados.ModoEdicion;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;


/**
 * 
 * @author PC-1-Aipai
 *
 */
public class MainTabPanel extends CTabFolder {

	private List<CTabItem> OpenTabList = new ArrayList<CTabItem>();
	
	public MainTabPanel(Composite parent, int style) {
		super(parent, style);
	}

	public void addTab(CompositeViewBase panel, ModoEdicion modoEdicion, String titulo, Image icon, String key) throws Exception
	{
		if (key.equals("")) throw new Exception("El panel que quiere abrir no tiene parametros key");
		
		CTabItem tab = getTabOpened(key);
		if (tab != null)
		{
			this.setSelection(tab);
			return;
		} else
		{
			CTabItem newTab = new CTabItem(this, SWT.CLOSE);
			newTab.setData(key);
			newTab.addDisposeListener(new DisposeListener() {
				
				@Override
				public void widgetDisposed(DisposeEvent e) {
					
					OpenTabList.remove(e.widget);
					
				}
			});
			OpenTabList.add(newTab);
			
			if(icon != null)
				newTab.setImage(icon);
			newTab.setText(titulo);
			newTab.setControl(panel);

			if ( modoEdicion == ModoEdicion.EDICION) 
			{
				byte[] clave = UtilidadesGenericas.StringToByteArray(key);
				panel.mostrarPanel(modoEdicion, clave);
			} else
				panel.mostrarPanel(modoEdicion);

			this.setSelection(newTab);	 
		}
	}

	public void closeAllTabs()
	{
		for (int i =OpenTabList.size() -1 ; i >= 0 ; i--)
		{
			OpenTabList.get(i).dispose();
		}
	}
	
	public void closeActualTab()
	{
		this.getSelection().dispose();
	}
	
	public void closeOthersTabs()
	{
		CTabItem actual = this.getSelection();
		
		for (int i =OpenTabList.size() -1 ; i >= 0 ; i--)
		{
			if ( !OpenTabList.get(i).equals(actual) )
				OpenTabList.get(i).dispose();
		}
	}
	
	
	private CTabItem getTabOpened(String key)
	{
		
		for (CTabItem tab : OpenTabList)
		{
			if (((String)tab.getData()).equals(key))
			{
				return tab;
			}
		}
		return null;
	}
	
}

