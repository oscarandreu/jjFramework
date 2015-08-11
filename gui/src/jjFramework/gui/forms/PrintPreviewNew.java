package jjFramework.gui.forms;
 
import jjFramework.gui.utils.DialogManager;

import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;
import org.eclipse.swt.printing.*;
import org.eclipse.wb.swt.SWTResourceManager;

import de.kupzog.ktools.kprint.boxes.*;
import de.kupzog.ktools.kprint.gui.KDialog;
import de.kupzog.ktools.kprint.gui.PageSetup;

/**
 * 
 * @author PC-1-Aipai
 *
 */
public class PrintPreviewNew  extends KDialog {

	private  static int SPACE_ARROUND_PAGE = 0;
	protected PDocument document;
	protected CLabel guiImageLabel;
	public Text guiPageText;
	protected Combo guiZoom;
	protected ScrolledComposite guiScrollArea;
	protected boolean layoutNeccessary;
	protected int percent;
	protected int page;
	private ToolItem firstPageToolItem;
	private ToolItem lastPageToolItem;
	private ToolItem nextPageToolItem;
	private ToolItem prevPageToolItem;
	
	public PrintPreviewNew(Shell parent, String title, Image icon, PDocument doc) {
		super(parent, title, icon);
		
		createContents();
		document = doc;
		page = 1;
		percent = 100;
		layoutNeccessary = true;

		addToolItem("print","Imprimir la vista actual", SWTResourceManager.getImage(PrintPreviewNew.class, "/resources/printer_22.png"));
		firstPageToolItem = addToolItem("first","Ir a la primera página", SWTResourceManager.getImage(PrintPreviewNew.class, "/resources/first_22.png"));
		prevPageToolItem = addToolItem("prev","Ir a la página anterior", SWTResourceManager.getImage(PrintPreviewNew.class, "/resources/back_22.png")); 
		nextPageToolItem = addToolItem("next", "Ir a la página siguiente", SWTResourceManager.getImage(PrintPreviewNew.class, "/resources/next_22.png")); 
		lastPageToolItem = addToolItem("last", "Ir a la última página", SWTResourceManager.getImage(PrintPreviewNew.class, "/resources/last_22.png")); 
		Button close = addButtonRight("Cerrar","Cerrar la ventana de Preview");
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				close();
			}
		});
		close.setImage(SWTResourceManager.getImage(PrintPreviewNew.class, "/resources/close16x16.png"));
		close.setFocus();

		guiPageText = new Text(guiToolBarArea, SWT.BORDER);
		guiPageText.setText(guiPageText.getText()+"        ");
		guiPageText.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		adjustToToolBar(guiPageText);
		guiPageText.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0) {
				guiPageText.setText("");
			}
			public void focusLost(FocusEvent arg0) {
				setPageText();
			}
		});
		guiPageText.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent arg0) {
				if (arg0.keyCode == SWT.CR)
				{
					onEnterInPageText();
				}
			}
		});
		guiPageText.setToolTipText("PrintPreview.19"); 
		
		guiZoom = new Combo(guiToolBarArea, SWT.BORDER | SWT.READ_ONLY);
		guiZoom.add("200 %"); 
		guiZoom.add("150 %"); 
		guiZoom.add("100 %"); 
		guiZoom.add("90 %"); 
		guiZoom.add("75 %"); 
		guiZoom.add("50 %");
		adjustToToolBar(guiZoom);
		guiZoom.setToolTipText("PrintPreview.27"); 
		guiZoom.select(2);
		guiZoom.addSelectionListener(
			new SelectionAdapter() {
				public void widgetSelected(SelectionEvent arg0) {
					onCombo(((Combo)arg0.widget).getText());
				}
			}
		);
		guiMainArea.setLayout(new FillLayout());
		guiScrollArea = new ScrolledComposite(guiMainArea,SWT.H_SCROLL | SWT.V_SCROLL);
		guiScrollArea.getVerticalBar().setIncrement(12);
		guiImageLabel = new CLabel(guiScrollArea, SWT.DRAW_TRANSPARENT);
		guiScrollArea.setContent(guiImageLabel);
		showPage();
		guiImageLabel.setSize(guiImageLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
	}
	
	private void onEnterInPageText()
	{
		try {
			int mypage = Integer.parseInt(guiPageText.getText());
			if (mypage > 0 && mypage <= document.getNumOfPages())
			{
				page = mypage;
				showPage();
				guiZoom.setFocus();
			}
			else
			{
				DialogManager.mostrarMensaje(guiShell, "La página " + mypage + " no existe. El valor tiene que estar entre 1 y " + document.getNumOfPages());
			}
		} catch (Exception e) {
			DialogManager.mostrarError(guiShell, e);
		}
	}

	private void setPageText() {
		guiPageText.setText("página " + page + " de "+ document.getNumOfPages()+ "       ");
	}

	public int getShellStyle() {
		return super.getShellStyle() | SWT.MAX | SWT.MIN;
	}

	protected void doLayout() {
//		int x = Display.getCurrent().getBounds().width - 100;
//		int y = Display.getCurrent().getBounds().height - 10;
//		
		int x = 860;
		int y = 650;
		guiShell.setSize(x,y);
		guiShell.setMaximized(true);
	}
	
	public static Image getPageImage(PDocument document, int page, boolean localLayoutNeccessary, int percent)
	{
		Shell cursorShell = Display.getCurrent().getActiveShell();
		if (cursorShell != null) cursorShell.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
		Point dpi = Display.getCurrent().getDPI();
		int shadowSize = (int) (5.0*percent/100.0);
		try
		{
			int h = (int) Math.round(document.getPageHeight()/2.54*dpi.y*percent/100 + shadowSize);
			int w = (int) Math.round(document.getPageWidth()/2.54*dpi.x*percent/100 + shadowSize);
			Image newImage = new Image(Display.getCurrent(), w, h);
			GC gc = new GC(newImage);
			
			PBox.setParameters(gc, Display.getCurrent(), dpi, percent);
			if (localLayoutNeccessary) document.layout();
			document.draw(page);

			// Schatten
			
			gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY));
			gc.fillRectangle(0, newImage.getBounds().height-shadowSize, 
							newImage.getBounds().width, newImage.getBounds().height);
			gc.fillRectangle(newImage.getBounds().width-shadowSize, 0, 
							newImage.getBounds().width-shadowSize, newImage.getBounds().height);

			gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
			gc.fillRectangle(0, newImage.getBounds().height-shadowSize, 
					shadowSize, newImage.getBounds().height);
			gc.fillRectangle(newImage.getBounds().width-shadowSize, 0, 
							 newImage.getBounds().width, shadowSize);

			gc.dispose(); 
			
			// SI QUEREMOS DEJAR UN BORDE ENTRE LA VENTANA Y EL DOCUMENTO DESCOMENTAR EL CODIGO
			if ( SPACE_ARROUND_PAGE > 0 ) {
				Image newImage2 = new Image(Display.getCurrent(),w + 2 * SPACE_ARROUND_PAGE ,h + 2 * SPACE_ARROUND_PAGE );
				gc = new GC(newImage2);
				gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
				gc.fillRectangle(0, 0, newImage2.getBounds().width, newImage2.getBounds().height);
				gc.drawImage(newImage,  SPACE_ARROUND_PAGE , SPACE_ARROUND_PAGE );
				gc.dispose();
				newImage.dispose();
				return newImage2;
			} else 
			{
				return newImage;
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}	finally {
			if (cursorShell != null)
				cursorShell.setCursor(null);
		}			
	}
	
	public void open() {
		guiScrollArea.setFocus();
		super.open();
	}
	
	protected void onCombo(String text)
	{
		text = text.substring(0,text.length()-2);
		percent =  Integer.parseInt(text);	
		
		if (guiImageLabel.getImage()!= null) guiImageLabel.getImage().dispose();

		guiImageLabel.setImage(getPageImage(document, page, true, percent));
		layoutNeccessary = false;
		guiImageLabel.setSize(guiImageLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		guiScrollArea.setFocus();
	}
	
	protected void onToolItem(ToolItem toolitem, String name) {
		if (name.equals("next")) //$NON-NLS-1$
		{
			if (page < document.getNumOfPages())
			{
				page++;
				showPage();
			}
		}
		else if (name.equals("prev")) //$NON-NLS-1$
		{
			if (page > 1)
			{
				page--;
				showPage();
			}
		}
		else if (name.equals("first")) //$NON-NLS-1$
		{
			page = 1;
			showPage();
		}
		else if (name.equals("last")) //$NON-NLS-1$
		{
			page = document.getNumOfPages();
			showPage();
		}
		else if (name.equals("print")) //$NON-NLS-1$
		{
			onPrint();
		}
	}
	
	private void showPage() {
		Image oldImg = guiImageLabel.getImage();
		guiImageLabel.setImage(getPageImage(
				document,
				page,
				layoutNeccessary,
				percent));
		layoutNeccessary = false;
		setPageText();
		firstPageToolItem.setEnabled( page > 1 );
		prevPageToolItem.setEnabled( page > 1 );
		lastPageToolItem.setEnabled( page < document.getNumOfPages() );
		nextPageToolItem.setEnabled ( page < document.getNumOfPages() );
		if (oldImg!= null) oldImg.dispose();
	}

	protected void onButton(Button button, String buttonText) {
		if (buttonText.startsWith(Messages.getString("PrintPreview.butClose"))) onClose(); //$NON-NLS-1$
		else if (buttonText.startsWith(Messages.getString("PrintPreview.43"))) onPageSetup(); //$NON-NLS-1$
	}
	
	protected void onClose()
	{
		if (guiImageLabel.getImage()!= null) guiImageLabel.getImage().dispose();
		PTextStyle.disposeFonts();
		close();
	}
	
	protected void onPageSetup()
	{
		/* funktioniert nicht:
		 * - Abgeschnittene Tabellen bleiben abgeschnitten
		 * - Skalierung geht nicht
		 */
		PageSetup ps = new PageSetup(guiShell);
		ps.open();
		document.readMeasuresFromPageSetup();
		if (guiImageLabel.getImage()!= null) guiImageLabel.getImage().dispose();
		guiImageLabel.setImage(getPageImage(
				document,
				page,
				true,
				percent));
		layoutNeccessary = false;
		guiImageLabel.setSize(guiImageLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT));

	}
	
	protected void onPrint()
	{
		PrintDialog dialog = new PrintDialog(guiShell, SWT.BORDER);
		PrinterData data = dialog.open();
		if (data == null) return;
		if (data.printToFile) {
			FileDialog d = new FileDialog (guiShell, SWT.SAVE);
			d.setFilterNames (new String [] {"All Files (*.*)"}); //$NON-NLS-1$
			d.setFilterExtensions (new String [] {"*.*"}); //Windows wild cards //$NON-NLS-1$
			d.setFilterPath ("c:\\"); //Windows path //$NON-NLS-1$
			d.setFileName (""); //$NON-NLS-1$
			d.open ();
			data.fileName = d.getFilterPath() + "\\" + d.getFileName(); //$NON-NLS-1$
			
		}
	
		Printer printer = new Printer(data);
		GC gc = new GC(printer);
	
		PBox.setParameters(gc, printer, printer.getDPI(), (int)(document.getScale()*100));
		document.layout();
	
		if (printer.startJob(document.getTitle())) 
		{
			if (data.scope == PrinterData.ALL_PAGES)
			{
				data.startPage = 1;
				data.endPage = document.getNumOfPages();
			}
			else if (data.scope == PrinterData.SELECTION)
			{
				data.startPage = page;
				data.endPage = page;
			}
			else if (data.scope == PrinterData.PAGE_RANGE)
			{
				if (data.startPage > document.getNumOfPages()) 
					data.startPage = document.getNumOfPages();
				if (data.endPage > document.getNumOfPages()) 
					data.endPage = document.getNumOfPages();
			}
	
			for (int page = data.startPage; page <= data.endPage; page++)
			{
				printer.startPage();
				document.draw(page);
				printer.endPage();
			}
			printer.endJob();
		}
		gc.dispose();
		printer.dispose();
		layoutNeccessary = true;
		
	}


}
