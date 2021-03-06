package jjFramework.gui.forms;

import jjFramework.gui.config.ConfiguradorBase;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.layout.grouplayout.LayoutStyle;
import org.eclipse.swt.widgets.Label;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;


/**
 * 
 * @author PC-1-Aipai
 *
 */
public class FrmPregunta extends Dialog {

	protected int result;
	protected Shell shell;
	private Text txtTexto;
	private String pregunta = "";

	public void setPregunta(String pregunta)
	{
		this.pregunta = pregunta;
	}
	
	public FrmPregunta(Shell parent, int style) {
		super(parent, style);
	}

	public int open() {
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
		
		shell.setSize(373, 199);
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
		lblNewLabel.setImage(SWTResourceManager.getImage(FrmPregunta.class, "/resources/question_48.png"));
		
		txtTexto = new Text(composite,  SWT.READ_ONLY | SWT.WRAP | SWT.MULTI);
		txtTexto.setFont(SWTResourceManager.getFont("Segoe UI Semibold", 10, SWT.NORMAL));
		txtTexto.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtTexto.setText(this.pregunta);
		GroupLayout gl_composite = new GroupLayout(composite);
		gl_composite.setHorizontalGroup(
			gl_composite.createParallelGroup(GroupLayout.LEADING)
				.add(gl_composite.createSequentialGroup()
					.addContainerGap()
					.add(lblNewLabel)
					.addPreferredGap(LayoutStyle.UNRELATED)
					.add(txtTexto, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_composite.setVerticalGroup(
			gl_composite.createParallelGroup(GroupLayout.LEADING)
				.add(gl_composite.createSequentialGroup()
					.add(gl_composite.createParallelGroup(GroupLayout.LEADING)
						.add(gl_composite.createSequentialGroup()
							.addContainerGap()
							.add(lblNewLabel))
						.add(gl_composite.createSequentialGroup()
							.add(27)
							.add(txtTexto, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)))
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
		
		Button btnSi = new Button(buttomPanel, SWT.NONE);
		btnSi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				gestionarSalida(SWT.YES);
			}
		});
		btnSi.setText("Si");
		btnSi.setImage(SWTResourceManager.getImage(FrmPregunta.class, "/resources/accept16x16.png"));
		
		Button btnNewButton = new Button(buttomPanel, SWT.NONE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				gestionarSalida(SWT.NO);
			}
		});
		btnNewButton.setImage(SWTResourceManager.getImage(FrmPregunta.class, "/resources/close16x16.png"));
		btnNewButton.setText("No");

		btnSi.setFocus();
	}

	protected void gestionarSalida(int resultado) {
		result = resultado;
		this.shell.close();
	}
	
}
