package jjFramework.gui.forms;

import jjFramework.BLL.controllers.cCUSeguridad;
import jjFramework.gui.config.ConfiguradorBase;
import jjFramework.gui.utils.DialogManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;
import org.eclipse.wb.swt.layout.grouplayout.LayoutStyle;


public class FrmLogin extends Shell {

	private Text txtUser;
	private Text txtPassowrd;
	private CLabel lblError;
	private Button chkRecordar;

	public FrmLogin(Display display, int style) {
		super(display, SWT.NONE);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));


		CLabel lblNewLabel = new CLabel(this, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setImage(ConfiguradorBase.AplicacionLogo);
		lblNewLabel.setText("");

		Composite grpA = new Composite(this, SWT.NONE);

		CLabel label = new CLabel(this, SWT.RIGHT);
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label.setText("Usuario");
		label.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));

		CLabel label_1 = new CLabel(this, SWT.RIGHT);
		label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_1.setText("Contrase\u00F1a");
		label_1.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));

		txtUser = new Text(this, SWT.BORDER);
		txtUser.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == 13 || e.keyCode == 16777296)
					checkPassword();	
			}
		});

		txtPassowrd = new Text(this, SWT.BORDER | SWT.PASSWORD);
		txtPassowrd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == 13 || e.keyCode == 16777296)
					checkPassword();	
			}
		});

		lblError = new CLabel(this, SWT.CENTER);
		lblError.setToolTipText("Usuario o contrase\u00F1a erroneos");
		lblError.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblError.setBottomMargin(0);
		lblError.setDragDetect(false);
		lblError.setTopMargin(0);
		lblError.setRightMargin(0);
		lblError.setLeftMargin(0);
		lblError.setAlignment(SWT.CENTER);
		lblError.setImage(SWTResourceManager.getImage(FrmLogin.class, "/resources/exclamacion_16.png"));
		lblError.setText("");
		
		chkRecordar = new Button(this, SWT.CHECK);
		chkRecordar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		chkRecordar.setText("Recordar Credenciales");

		Button cmdAceptar = new Button(grpA, SWT.NONE);
		cmdAceptar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				checkPassword();
			}
		});
		cmdAceptar.setBounds(192, 5, 77, 26);
		cmdAceptar.setText("Aceptar");
		cmdAceptar.setImage(SWTResourceManager.getImage(FrmLogin.class, "/resources/accept16x16.png"));

		Button cmdCancelar = new Button(grpA, SWT.NONE);
		cmdCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				close();
			}
		});
		cmdCancelar.setBounds(275, 5, 82, 26);
		cmdCancelar.setText("Cancelar");
		cmdCancelar.setImage(SWTResourceManager.getImage(FrmLogin.class, "/resources/close16x16.png"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.LEADING)
				.add(groupLayout.createSequentialGroup()
					.add(groupLayout.createParallelGroup(GroupLayout.LEADING)
						.add(groupLayout.createSequentialGroup()
							.add(11)
							.add(lblNewLabel, GroupLayout.PREFERRED_SIZE, 257, GroupLayout.PREFERRED_SIZE))
						.add(GroupLayout.TRAILING, groupLayout.createSequentialGroup()
							.add(113)
							.add(groupLayout.createParallelGroup(GroupLayout.TRAILING)
								.add(label, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.add(label_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.RELATED)
							.add(groupLayout.createParallelGroup(GroupLayout.TRAILING)
								.add(txtUser, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
								.add(txtPassowrd, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
								.add(chkRecordar))
							.addPreferredGap(LayoutStyle.RELATED)
							.add(lblError, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.add(1))
						.add(grpA, GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE))
					.add(0))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.LEADING)
				.add(groupLayout.createSequentialGroup()
					.add(groupLayout.createParallelGroup(GroupLayout.TRAILING, false)
						.add(groupLayout.createSequentialGroup()
							.add(lblNewLabel, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
							.add(18)
							.add(groupLayout.createParallelGroup(GroupLayout.LEADING, false)
								.add(groupLayout.createSequentialGroup()
									.add(label, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.add(label_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.add(groupLayout.createSequentialGroup()
									.add(txtUser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.add(txtPassowrd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.add(8))
						.add(groupLayout.createSequentialGroup()
							.add(lblError, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.add(40)))
					.addPreferredGap(LayoutStyle.RELATED)
					.add(chkRecordar)
					.add(22)
					.add(grpA, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
		);
		setLayout(groupLayout);
		setTabList(new Control[]{txtUser, txtPassowrd, lblNewLabel, grpA, label, label_1, lblError});
		createContents();
	}

	protected void checkPassword(){


		try {
			cCUSeguridad.initializeSecurity(txtUser.getText(), txtPassowrd.getText());
			
			if(cCUSeguridad.getInstance() == null)
			{
				lblError.setVisible(true);
				lblError.setToolTipText("Usuario o contraseña erróneos");
			}
			else 
			{
				ConfiguradorBase.instance.writeCredenciales(chkRecordar.getSelection(), txtUser.getText(), txtPassowrd.getText());
				close();
			}
		} catch (Exception e) {
			DialogManager.mostrarError(this,e);
		}
	}

	protected void createContents() {
		this.setImage(ConfiguradorBase.AplicacionIcono);
		lblError.setVisible(false);
		setText(ConfiguradorBase.AplicacionNombre);
		setSize(369, 244);


		if ( !ConfiguradorBase.LoginRecordarCredenciales )
		{
			chkRecordar.setSelection(false);
		} else 
		{
			chkRecordar.setSelection(true);
			txtUser.setText(ConfiguradorBase.LoginUsuario);
			txtPassowrd.setText(ConfiguradorBase.LoginContraseña);
		}
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	} 
}
