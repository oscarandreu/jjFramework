package jjFramework.gui.forms;

import jjFramework.gui.config.ConfiguradorBase;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;
import org.eclipse.wb.swt.layout.grouplayout.LayoutStyle;
import org.eclipse.swt.SWT;

import swing2swt.layout.FlowLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.MouseAdapter;

public class FrmAcercaDe extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text txtLicencia;

	public FrmAcercaDe(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	public Object open() {
		createContents();
		
		Rectangle shellBounds = this.getParent().getBounds();
		Point dialogSize = shell.getSize();
		shell.setLocation(shellBounds.x + (shellBounds.width - dialogSize.x) / 2, shellBounds.y + (shellBounds.height - dialogSize.y) / 2);
		
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
		shell = new Shell(getParent(), getStyle());
		shell.setImage(ConfiguradorBase.AplicacionIcono);
		setText(ConfiguradorBase.AplicacionNombre);
		
		shell.setSize(503, 296);
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
		lblNewLabel.setImage(SWTResourceManager.getImage(FrmAcercaDe.class, "/resources/aipai.png"));
		
		Label lblAplicacion = new Label(composite, SWT.NONE);
		lblAplicacion.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblAplicacion.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		lblAplicacion.setText("IlGest Despachos (BETA)");
		
		Label lblVersión = new Label(composite, SWT.NONE);
		lblVersión.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblVersión.setText("Versión: " + ConfiguradorBase.AplicacionVersion);
		
		txtLicencia = new Text(composite,  SWT.READ_ONLY | SWT.WRAP | SWT.MULTI);
		txtLicencia.setTouchEnabled(true);
		txtLicencia.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtLicencia.setText("Los presentes términos de licencia constituyen un contrato entre Microsoft Corporation (o, en función de donde resida, una de sus filiales) y usted. Le rogamos que los lea atentamente. Son de aplicación al software arriba mencionado, el cual incluye, en su caso, los soportes físicos en los que lo haya recibido. Los términos de la licencia en papel impreso, que pueden venir con el software, podrán modificar o sustituir cualquier término de la licencia que aparezca en pantalla. Estos términos también se aplicarán a los siguientes elementos de Microsoft");
	
		GroupLayout gl_composite = new GroupLayout(composite);
		gl_composite.setHorizontalGroup(
			gl_composite.createParallelGroup(GroupLayout.TRAILING)
				.add(gl_composite.createSequentialGroup()
					.add(gl_composite.createParallelGroup(GroupLayout.LEADING)
						.add(gl_composite.createSequentialGroup()
							.addContainerGap()
							.add(gl_composite.createParallelGroup(GroupLayout.LEADING)
								.add(lblVersión)
								.add(lblAplicacion, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE))
							.add(18)
							.add(lblNewLabel))
						.add(gl_composite.createSequentialGroup()
							.add(22)
							.add(txtLicencia, GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_composite.setVerticalGroup(
			gl_composite.createParallelGroup(GroupLayout.LEADING)
				.add(gl_composite.createSequentialGroup()
					.addContainerGap()
					.add(gl_composite.createParallelGroup(GroupLayout.LEADING)
						.add(gl_composite.createSequentialGroup()
							.add(lblAplicacion)
							.addPreferredGap(LayoutStyle.RELATED)
							.add(lblVersión))
						.add(lblNewLabel))
					.add(27)
					.add(txtLicencia, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
					.addContainerGap())
		);
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
		
		Button btnCerrar = new Button(buttomPanel, SWT.NONE);
		btnCerrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shell.close();
			}
		});
		btnCerrar.setImage(SWTResourceManager.getImage(FrmAcercaDe.class, "/resources/close16x16.png"));
		btnCerrar.setText("Cerrar");
		
		btnCerrar.forceFocus();
	

	}

}
