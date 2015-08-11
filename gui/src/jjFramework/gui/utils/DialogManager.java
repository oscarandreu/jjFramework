package jjFramework.gui.utils;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import jjFramework.gui.forms.FrmError;
import jjFramework.gui.forms.FrmPregunta;
import jjFramework.gui.forms.FrmInformacion;



/**
 * 
 * @author PC-1-Aipai
 *
 */
public class DialogManager {

	public static boolean mostrarPreguntaEliminar(Shell parent)
	{
		try
		{
			FrmPregunta frm  = new FrmPregunta(parent, SWT.CLOSE | SWT.TITLE | SWT.DIALOG_TRIM);
			frm.setPregunta("La acción eliminará la entidad seleccionada, ¿Desa continuar?");
			if (frm.open() == SWT.YES)
			{
				return true;
			}
			return false;
		} catch (Exception ex)
		{
			mostrarError(parent, ex);
		}
		return false;
	}
	
	public static boolean mostrarPregunta(Shell parent, String pregunta)
	{
		try
		{
			FrmPregunta frm  = new FrmPregunta(parent, SWT.CLOSE | SWT.TITLE | SWT.DIALOG_TRIM);
			frm.setPregunta(pregunta);
			if (frm.open() == SWT.YES)
			{
				return true;
			}
			return false;
		} catch (Exception ex)
		{
			mostrarError(parent, ex);
		}
		return false;
	}

	public static void mostrarError(Shell parent, Exception exception)
	{
		try
		{
			FrmError  frm  = new FrmError(parent, SWT.TITLE | SWT.DIALOG_TRIM);
			frm.setException(exception);
			frm.open();
		} catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage() + ex.getCause());	
		}
	}

	public static void mostrarMensaje(Shell parent, String mensaje)
	{
		try
		{
			FrmInformacion frm  = new FrmInformacion(parent, SWT.CLOSE | SWT.TITLE | SWT.DIALOG_TRIM);
			frm.setMensaje(mensaje);
			frm.open();
		} catch (Exception ex)
		{
			mostrarError(parent, ex);
		}
	}
}
