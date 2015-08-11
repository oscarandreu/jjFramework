package jjFramework.gui.utils;

import jjFramework.gui.config.ConfiguradorBase;
import jjFramework.gui.forms.PrintPreviewNew;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Table;

import de.kupzog.ktools.kprint.boxes.PBox;
import de.kupzog.ktools.kprint.boxes.PDocument;
import de.kupzog.ktools.kprint.boxes.PHLine;
import de.kupzog.ktools.kprint.boxes.PTableBoxProvider;
import de.kupzog.ktools.kprint.boxes.PTextBox;
import de.kupzog.ktools.kprint.boxes.PVSpace;
import de.kupzog.ktools.kprint.boxes.SWTPTable;


public class PrintManager {

	public static void PrintTable(Table tabla, String titulo)
	{
		final PDocument doc = new PDocument(titulo);

		PTextBox t = new PTextBox(doc);
		t.setText(titulo);

		new PVSpace(doc, 0.1);
		new PHLine(doc, 0.02, SWT.COLOR_BLACK);
		new PVSpace(doc, 0.5);

		SWTPTable table = new SWTPTable(doc);
		table.setTable(tabla);
		table.setBoxProvider(new PTableBoxProvider());
		PrintPreviewNew pr = new PrintPreviewNew(null, titulo, ConfiguradorBase.AplicacionIcono, doc);
		pr.open();

	}

	public void print(PDocument doc) {
		PrintDialog dialog = new PrintDialog(null, SWT.BORDER);
		PrinterData data = dialog.open();
		if (data == null)
			return;
		if (data.printToFile) {
			data.fileName = "print.out"; // you probably want to ask the user
			// for a filename
		}

		Printer printer = new Printer(data);
		GC gc = new GC(printer);
		PBox.setParameters(gc, printer, printer.getDPI(), 100);
		if (printer.startJob("DoSys Druckauftrag")) {
			printer.startPage();
			doc.layout();
			doc.draw(1);
			printer.endJob();
		}
		gc.dispose();

	}

}
