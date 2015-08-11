package jjFramework.gui.exceptions;

import java.awt.AWTEvent;


@SuppressWarnings("serial")
public class DocumentChooserEvent extends AWTEvent {

	private String documentPath;

	public String getDocumentPath() {
		return documentPath;
	}

	public DocumentChooserEvent(Object target, String documentPath) {
		super(target, 0);
		this.documentPath = documentPath;
	}


}