package jjFramework.gui.components;


import java.awt.event.AWTEventListener;
import java.lang.reflect.Method;

import jjFramework.BLL.utils.UtilidadesGenericas;
import jjFramework.gui.exceptions.DocumentChooserEvent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;

import ILGestPojos.models.Mapeodocumentacion;
import ILGestPojos.models.Oficinas;


public class DocumentChooser extends Composite {

	public enum EditMode
	{
		ReadWrite,
		ReadOnly
	}

	public static Oficinas OFICINA = null;
	
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Text txtFilePath;
	private Button cmdView;
	private Button cmdSelect;
	private Button cmdDelete;

	private Object bindedEntity;
	private boolean isNew = true;
	private String bindedProperty;
	private Mapeodocumentacion bindedDoc;

	private AWTEventListener viewDocumentListener;
	public void addViewDocumentAWTEventListener(AWTEventListener evento) {
		this.viewDocumentListener = evento;
	}

	public DocumentChooser(Composite parent, int style) {
		super(parent, SWT.NONE);
		this.setBackground(SWTResourceManager.getColor(0, 0, 128));
		setForeground(SWTResourceManager.getColor(255, 255, 255));
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);

		txtFilePath = new Text(this, SWT.BORDER);
		toolkit.adapt(txtFilePath, true, true);

		cmdView = new Button(this, SWT.NONE);
		cmdView.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewFile();
			}
		});
		cmdView.setImage(SWTResourceManager.getImage(DocumentChooser.class, "/resources/File Open.png"));
		cmdView.setToolTipText("Abrir el documento en el visor predeterminado.");
		toolkit.adapt(cmdView, true, true);

		cmdSelect = new Button(this, SWT.NONE);
		cmdSelect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectFile();
			}
		});
		cmdSelect.setImage(SWTResourceManager.getImage(DocumentChooser.class, "/resources/Folder Explorer.png"));
		cmdSelect.setToolTipText("Escoger el documento");
		toolkit.adapt(cmdSelect, true, true);

		cmdDelete = new Button(this, SWT.NONE);
		cmdDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteFile();
			}
		});
		cmdDelete.setToolTipText("Eliminar el documento");
		cmdDelete.setImage(SWTResourceManager.getImage(DocumentChooser.class, "/resources/File Delete.png"));
		toolkit.adapt(cmdDelete, true, true);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(GroupLayout.TRAILING)
				.add(groupLayout.createSequentialGroup()
						.add(txtFilePath, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
						.add(2)
						.add(cmdView, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.add(1)
						.add(cmdSelect, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.add(2)
						.add(cmdDelete, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.add(1))
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(GroupLayout.LEADING)
				.add(groupLayout.createSequentialGroup()
						.add(groupLayout.createParallelGroup(GroupLayout.LEADING)
								.add(groupLayout.createSequentialGroup()
										.add(1)
										.add(groupLayout.createParallelGroup(GroupLayout.BASELINE)
												.add(cmdView, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
												.add(cmdSelect, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
												.add(cmdDelete, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)))
												.add(groupLayout.createSequentialGroup()
														.add(3)
														.add(txtFilePath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
														.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		groupLayout.linkSize(new Control[] {cmdView, cmdSelect, cmdDelete}, GroupLayout.VERTICAL);
		groupLayout.linkSize(new Control[] {cmdView, cmdSelect, cmdDelete}, GroupLayout.HORIZONTAL);
		setLayout(groupLayout);

	}

	public String getDocumentName()
	{
		return txtFilePath.getText();
	}
	
	protected void deleteFile() {
		txtFilePath.setText("");

		if(bindedDoc != null && isNew)
			bindedDoc = null;
		else if(!isNew)
		{
			bindedDoc.setCurrentDocumentPath("");
		}
	}

	protected void viewFile() {
		if(viewDocumentListener != null){
			DocumentChooserEvent e = new DocumentChooserEvent(this, txtFilePath.getText());
			viewDocumentListener.eventDispatched(e);
		}
	}

	protected void selectFile() {
		String path = null;
		FileDialog fd = new FileDialog(getShell(), SWT.OPEN);
		fd.setText("Seleccione un archivo");
		fd.setFilterPath("C:/");
		//		String[] filterExt = { "*.txt", "*.doc", ".rtf", "*.*" };
		//		fd.setFilterExtensions(filterExt);

		path = fd.open();
		if(path != null)
		{	
			txtFilePath.setText(path);

			if(bindedDoc == null)
			{
				bindedDoc = Mapeodocumentacion.ObtenerNuevoModelo();
				bindedDoc.setCodigo(UtilidadesGenericas.getRamdonKey());
				try {
					setMapeodocumentacion(bindedDoc);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			bindedDoc.setOficinas(OFICINA);
			bindedDoc.setCurrentDocumentPath(path);
		}
	}

	public void setEditable(boolean editable)
	{
		int width = (cmdDelete.getSize().x + 2)* 2;
		if(!editable)
			width = width * -1;

		cmdDelete.setVisible(editable);
		cmdSelect.setVisible(editable);

		txtFilePath.setSize(width, txtFilePath.getSize().y);
	}

	public void bind(Object entity, String property) throws Exception
	{
		bindedEntity = entity;
		bindedProperty = property;

		//Obtenemos el objeto de la propiedad bindeada
		String metodo = "get"+ bindedProperty.substring(0, 1).toUpperCase() + bindedProperty.substring(1, bindedProperty.length());
		Method dest =  bindedEntity.getClass().getDeclaredMethod(metodo);
		dest.setAccessible(true);
		bindedDoc = (Mapeodocumentacion) dest.invoke(bindedEntity);

		if(bindedDoc != null)
		{
			isNew = false;
			txtFilePath.setText(bindedDoc.getDocumentPath());
		}
	}

	public Text getControl()
	{
		return this.txtFilePath;
	}

	//	private Mapeodocumentacion getMapeodocumentacion() throws Exception
	//	{
	//		String metodo = "get"+ bindedProperty.substring(0, 1).toUpperCase() + bindedProperty.substring(1, bindedProperty.length());
	//		Method dest =  bindedEntity.getClass().getDeclaredMethod(metodo);
	//		dest.setAccessible(true);
	//
	//		return (Mapeodocumentacion) dest.invoke(bindedEntity);
	//	}
	//
		@SuppressWarnings("rawtypes")
		private void setMapeodocumentacion(Mapeodocumentacion mapeo) throws Exception
		{
			Class[] param = new Class[1];	
			param[0] = Mapeodocumentacion.class;
			
			String metodo = "set"+ bindedProperty.substring(0, 1).toUpperCase() + bindedProperty.substring(1, bindedProperty.length());
			Method dest =  bindedEntity.getClass().getDeclaredMethod(metodo, param);
			dest.setAccessible(true);
	
			dest.invoke(bindedEntity, new Object[]{mapeo});
		}
}
