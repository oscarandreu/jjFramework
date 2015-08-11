package jjFramework.BLL.exceptions;

import java.util.List;

import ILGestPojos.Personals.Errores;


/** Excepciones en la validacón básica de pojo's vía hibernate
 * @param <T>*/
public class CheckValidationException extends Exception
{

	private static final long serialVersionUID = 1L;
	private List<Errores> listaErrores;

	public List<Errores> getValidationErrors() {
		return listaErrores;
	}
	public void setValidationErrors(List<Errores> validationErrors) {
		this.listaErrores = validationErrors;
	}

	public CheckValidationException(List<Errores> listaErrores)
	{
		this.listaErrores = listaErrores;
	}
}
