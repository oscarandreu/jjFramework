package jjFramework.gui.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import jjFramework.gui.config.ConfiguradorBase;

import org.joda.time.DateTime;

/**
 * 
 * @author PC-1-Aipai
 *
 */
public class DateTimeUtils {

	public static String DATEFORMAT_LARGO = "E, dd MMMM yyyy";
	public static String DATEFORMAT_CORTO = "dd/MM/yyyy";
	public static String DATETIMEFORMAT_CORTO =	"dd/MM/yyyy HH:mm";

	public static DateTime obtenerFechaSinHora(DateTime fecha)
	{
		return new DateTime( fecha.getYear(), fecha.getMonthOfYear(), fecha.getDayOfMonth(), 0, 0, 0 );
	}
	
	public static DateTime obtenerFechaFinalDelDia(DateTime fecha)
	{
		return new DateTime( fecha.getYear(), fecha.getMonthOfYear(), fecha.getDayOfMonth(), 23, 59, 59 );
	}
	
	public static DateTime obtenerFechaAnterior(DateTime fecha)
	{
		return fecha.minusDays(1);
	}

	public static String obtenerStringFormateado(Date valor, String formato)
	{
		SimpleDateFormat sdf = new SimpleDateFormat((formato == null) ? ConfiguradorBase.FormatoDate : formato);
		return sdf.format(valor);
	}
}
