package jjFramework.gui.utils;

import org.eclipse.swt.graphics.Color;
import org.eclipse.wb.swt.SWTResourceManager;

public class ColorManager {

	public static Color HexadecimalToRGB(String ColorHexadecimal)
    {
        //Si presenta # se remueve, como usted sabrá los colores hexadecimales vienen por defecto,
        //con el caracter # adelante.
        if (ColorHexadecimal.indexOf('#') != -1)
            ColorHexadecimal = ColorHexadecimal.replace("#", "");

        int red = 0;
        int green = 0;
        int blue = 0;

        if (ColorHexadecimal.length() == 6)
        {
            red = Integer.parseInt(ColorHexadecimal.substring(0, 2), 16);
            green = Integer.parseInt(ColorHexadecimal.substring(2, 4), 16);
            blue = Integer.parseInt(ColorHexadecimal.substring(4, 6), 16);

        }
        else if (ColorHexadecimal.length() == 3)
        {
            red = Integer.parseInt(ColorHexadecimal.substring(0, 1), 16);
            green = Integer.parseInt(ColorHexadecimal.substring(1, 2), 16);
            blue = Integer.parseInt(ColorHexadecimal.substring(2, 3), 16);
        }

        return SWTResourceManager.getColor(red, green, blue);
    }
	
	public static String RGBToHexadecimal(Color color)
	{
		return "#" + Integer.toHexString(color.getRed()) + Integer.toHexString(color.getGreen()) + Integer.toHexString(color.getBlue());
	}
}
