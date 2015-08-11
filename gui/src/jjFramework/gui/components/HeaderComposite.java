package jjFramework.gui.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;

public class HeaderComposite extends Composite {

	private CLabel lblImagen = new CLabel(this, SWT.NONE);
	private Label lblTitulo = new Label(this, SWT.NONE);
		
	public void setColorFondo(Color color)
	{
		lblImagen.setBackground(color);
		lblTitulo.setBackground(color);
		this.setBackground(color);
	}
	
	public void setTitulo(String titulo)
	{
		lblTitulo.setText(titulo);
	}
	
	public void setImagenTitulo(Image imagen)
	{
	    if (imagen != null)
	    {
    		lblImagen.setImage(imagen);
    	} else
	   	{
	   		lblImagen.setImage(SWTResourceManager.getImage(HeaderComposite.class, "/resources/cabecera.png"));
	   	}
	}
	
	public HeaderComposite(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(255, 255, 255));
		
		
		lblImagen.setBackground(SWTResourceManager.getColor(255, 255, 255));
		lblImagen.setImage(SWTResourceManager.getImage(HeaderComposite.class, "/resources/cabecera.png"));
		lblImagen.setText("");
		
	
		lblTitulo.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		lblTitulo.setBackground(SWTResourceManager.getColor(255, 255, 255));
		lblTitulo.setText("T\u00EDtulo del subsistema");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.LEADING)
				.add(groupLayout.createSequentialGroup()
					.add(lblImagen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.add(10)
					.add(lblTitulo, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
					.add(33))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.LEADING)
				.add(groupLayout.createSequentialGroup()
					.add(groupLayout.createParallelGroup(GroupLayout.LEADING)
						.add(lblImagen, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
						.add(groupLayout.createSequentialGroup()
							.add(10)
							.add(lblTitulo, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)))
					.add(2))
		);
		setLayout(groupLayout);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
