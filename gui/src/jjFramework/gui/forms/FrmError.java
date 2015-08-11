package jjFramework.gui.forms;

import jjFramework.gui.config.ConfiguradorBase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ExpandAdapter;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;
import org.eclipse.wb.swt.layout.grouplayout.LayoutStyle;

import swing2swt.layout.FlowLayout;

public class FrmError extends Dialog {
	protected Shell shell;
	private Text txtTexto;
	protected Object result;
	private Text txtDescripcion;
	private Exception ex = null;
	
	public void setException(Exception ex)
	{
		this.ex = ex;
	}
	
	public FrmError(Shell parent, int style) {
		super(parent, style);
	}

	public Object open() {
		createContents();
		
		Rectangle shellBounds = this.getParent().getBounds();
		Point dialogSize = shell.getSize();
		shell.setLocation(shellBounds.x + (shellBounds.width - dialogSize.x) / 2, shellBounds.y + (shellBounds.height - dialogSize.y) / 2);
		RowLayout rl_shlCc = new RowLayout(SWT.HORIZONTAL);
		rl_shlCc.justify = true;
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setLayoutData(new FormData());
		composite_1.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	private void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM);
		shell.setImage(ConfiguradorBase.AplicacionIcono);
		setText(ConfiguradorBase.AplicacionNombre);
		
		shell.setSize(437, 191);
		shell.setText(getText());
		shell.setLayout(new FormLayout());
		
		Composite composite = new Composite(shell, SWT.BORDER);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0);
		fd_composite.left = new FormAttachment(0);
		composite.setLayoutData(fd_composite);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setImage(SWTResourceManager.getImage(FrmError.class, "/resources/warning_48.png"));
		
		txtTexto = new Text(composite,  SWT.READ_ONLY | SWT.WRAP | SWT.MULTI);
		txtTexto.setText("Error");
		txtTexto.setFont(SWTResourceManager.getFont("Segoe UI Semibold", 10, SWT.NORMAL));
		txtTexto.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		ExpandBar expandBar = new ExpandBar(composite, SWT.NONE);
		expandBar.addExpandListener(new ExpandAdapter() {
			@Override
			public void itemCollapsed(ExpandEvent e) {
				reSize(true);
			}
			@Override
			public void itemExpanded(ExpandEvent e) {
				reSize(false);
			}
		});
		expandBar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GroupLayout gl_composite = new GroupLayout(composite);
		gl_composite.setHorizontalGroup(
			gl_composite.createParallelGroup(GroupLayout.LEADING)
				.add(gl_composite.createSequentialGroup()
					.addContainerGap()
					.add(lblNewLabel)
					.addPreferredGap(LayoutStyle.RELATED)
					.add(txtTexto, GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
					.addContainerGap())
				.add(GroupLayout.TRAILING, expandBar, GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
		);
		gl_composite.setVerticalGroup(
			gl_composite.createParallelGroup(GroupLayout.LEADING)
				.add(gl_composite.createSequentialGroup()
					.addContainerGap()
					.add(gl_composite.createParallelGroup(GroupLayout.BASELINE)
						.add(lblNewLabel)
						.add(txtTexto, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
					.add(17)
					.add(expandBar, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
					.add(10))
		);
		
		ExpandItem xpndtmMostarDetalles = new ExpandItem(expandBar, SWT.NONE);
		xpndtmMostarDetalles.setText("Mostar detalles");
		
		Composite composite_1 = new Composite(expandBar, SWT.NONE);
		xpndtmMostarDetalles.setControl(composite_1);
		
		txtDescripcion = new Text(composite_1,  SWT.READ_ONLY | SWT.WRAP | SWT.MULTI | SWT.V_SCROLL);
		txtDescripcion.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GroupLayout gl_composite_1 = new GroupLayout(composite_1);
		gl_composite_1.setHorizontalGroup(
			gl_composite_1.createParallelGroup(GroupLayout.LEADING)
				.add(txtDescripcion, GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
		);
		gl_composite_1.setVerticalGroup(
			gl_composite_1.createParallelGroup(GroupLayout.LEADING)
				.add(GroupLayout.TRAILING, txtDescripcion, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
		);
		composite_1.setLayout(gl_composite_1);
		xpndtmMostarDetalles.setHeight(150);
		composite.setLayout(gl_composite);
		
		Composite buttomPanel = new Composite(shell, SWT.NONE);
		fd_composite.bottom = new FormAttachment(buttomPanel);
		fd_composite.right = new FormAttachment(buttomPanel, 0, SWT.RIGHT);
		buttomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		FormData fd_buttomPanel = new FormData();
		fd_buttomPanel.top = new FormAttachment(100, -37);
		fd_buttomPanel.left = new FormAttachment(0);
		fd_buttomPanel.right = new FormAttachment(100);
		fd_buttomPanel.bottom = new FormAttachment(100);
		buttomPanel.setLayoutData(fd_buttomPanel);
		
		Button cmdCerrar = new Button(buttomPanel, SWT.NONE);
		cmdCerrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shell.close();
			}
		});
		cmdCerrar.setImage(SWTResourceManager.getImage(FrmPregunta.class, "/resources/close16x16.png"));
		cmdCerrar.setText("Cerrar");
		cmdCerrar.setFocus();
		MostrarExcepcion();
		
		
	}
	
	private void MostrarExcepcion() {

		StringBuilder texto = new StringBuilder();
		
		txtTexto.setText(ex.getMessage());
		
		for (StackTraceElement traza : ex.getStackTrace())
		{
			texto.append (traza.toString());
			texto.append("\n");
		}
		txtDescripcion.setText(texto.toString());
	}

	private void reSize(boolean collapsed)
	{
		if (collapsed)
		{
			Point dialogSize = shell.getSize();
			shell.setSize(dialogSize.x, dialogSize.y - 155);
			shell.layout();
		} else
		{
			Point dialogSize = shell.getSize();
			shell.setSize(dialogSize.x, dialogSize.y + 155);
			shell.layout();
		}
	
	
	}
}
