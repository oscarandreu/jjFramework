package jjFramework.gui.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

public class MenuButton extends CLabel
{

	private Color defaultBackColor = SWTResourceManager.getColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
	private Color defaultForeColor = SWTResourceManager.getColor(0, 51, 204);
	
	private Color selectedBackColor = SWTResourceManager.getColor(169, 169, 169);
	private Color selectedForeColor = SWTResourceManager.getColor(30, 144, 255);
	
	public MenuButton(Composite parent, int style) 
	{
		super(parent, style);
		
		setForeground(defaultForeColor);
		setBackground(defaultBackColor);
		
		addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseExit(org.eclipse.swt.events.MouseEvent e) {
				setForeground(defaultForeColor);
				setBackground(defaultBackColor);
				setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			}
			@Override
			public void mouseEnter(org.eclipse.swt.events.MouseEvent e) {
				setForeground(selectedForeColor);
				setBackground(selectedBackColor);
				setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			}
		});
		
	}

	
}
