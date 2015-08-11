package jjFramework.gui.components;

import jjFramework.BLL.utils.BindingManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;
import org.eclipse.wb.swt.layout.grouplayout.LayoutStyle;


/**
 * 
 * @author PC-1-Aipai
 *
 */
public class CompositeViewBase  extends CompositeBase
{
	private HeaderComposite cabecera = new HeaderComposite(this, SWT.NONE);
	public Composite baseComposite;
	
	public void setColorFondo(Color color)
	{
		cabecera.setColorFondo(color);
	}
	public void setTitulo(String titulo)
	{
		cabecera.setTitulo(titulo);
	}
	public void setImagenTitulo(Image imagen)
	{
		cabecera.setImagenTitulo(imagen);
	}

	public CompositeViewBase(Composite parent, int style, BindingManager bindingManager) {
		super(parent, style, bindingManager);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		baseComposite = new Composite(this, SWT.NONE);
		baseComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.LEADING)
				.add(baseComposite, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
				.add(cabecera, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.LEADING)
				.add(groupLayout.createSequentialGroup()
					.add(cabecera, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.RELATED)
					.add(baseComposite, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}

	@Override protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	
}
