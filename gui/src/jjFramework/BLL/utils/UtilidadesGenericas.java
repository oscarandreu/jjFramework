package jjFramework.BLL.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;


public class UtilidadesGenericas {

	/**
	 * 
	 * @return Clave aleatoria de 16bytes
	 */
	public static byte[] getRamdonKey() 
	 {
		UUID uuid = UUID.randomUUID();
	    long msb = uuid.getMostSignificantBits();
	    long lsb = uuid.getLeastSignificantBits();
	    byte[] buffer = new byte[16];

	    for (int i = 0; i < 8; i++) {
	            buffer[i] = (byte) (msb >>> 8 * (7 - i));
	    }
	    for (int i = 8; i < 16; i++) {
	            buffer[i] = (byte) (lsb >>> 8 * (7 - i));
	    }

	    return buffer;
	}

	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}

	public static byte[] StringToByteArray(String cadena) throws Exception
	{
		try
		{
			return  cadena.getBytes("ISO-8859-1");
		} catch(Exception ex)
		{
			throw new Exception("Error de conversión a ByteArray la cadena: " + cadena);
		}
	}

	public static String ByteArrayToString(byte[] cadena) throws Exception
	{
		try
		{
			String key =  new String(cadena, "ISO-8859-1");
			return key;
		} catch(Exception ex)
		{
			throw new Exception("Error de conversión a String la cadena: " + cadena);
		}
		
	}

	public static boolean isNumeric(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}

	public static Image ImageFromFile(Display display, String path)
	{
		
        byte[] bFile = ByteArrayFromFile(path);
        
		InputStream in = new ByteArrayInputStream(bFile);
		return new Image(display, in);
	}
	
	public static byte[] ByteArrayFromFile(String path)
	{
		try {
			File file = new File(path);
			byte[] buffer =  new byte[(int) file.length()];

			FileInputStream in = new FileInputStream(path);
			in.read(buffer);
			in.close();

			return buffer;
		} catch (IOException e) {
			return null;
		}
	}

	public static Image ImagenFromByteArray(Device device, byte[] byteArray)
	{
		try
		{
			ImageLoader imgl = new ImageLoader();
			ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
			imgl.load(inputStream);
			Image imagen = new Image(device, imgl.data[0]);

			return imagen;
		} catch (Exception ex)
		{
			return null;
		}
	}
	
	public static ImageData ImagenDataFromByteArray(byte[] byteArray)
	{
		try
		{
			ImageLoader imgl = new ImageLoader();
			ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
			imgl.load(inputStream);
			
			return  imgl.data[0];
			
		} catch (Exception ex)
		{
			return null;
		}

	}

}
