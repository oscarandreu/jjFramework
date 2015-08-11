package jjFramework.gui.config;

import java.io.FileOutputStream;
import java.util.Properties;

import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;


/**
 * 
 * @author PC-1-Aipai
 *
 */
public class ConfiguradorBase 
{
	public static String AplicacionNombre = "IlGest Despachos";
	public static String AplicacionVersion = "1.0";
	public static Image AplicacionIcono = SWTResourceManager.getImage(ConfiguradorBase.class, "/resources/main.png");
	public static Image AplicacionLogo = SWTResourceManager.getImage(ConfiguradorBase.class, "/resources/aipai.png");
	
	public static String BaseDatosServidor = "";
	public static String BaseDatosNombre = "";
	public static String BaseDatosUsuario = "";
	public static String BaseDatosContraseña = "";
	
	public static String FormatoDate = "";
	public static String FormatoDateTime = "";
	
	public static String LoginUsuario = "";
	public static String LoginContraseña = "";
	public static boolean LoginRecordarCredenciales;
	
	public static String Server_host = "192.168.0.227";
	public static String server_port = "9060";
	public static String server_base_path = "/aipai";
	
	public static ConfiguradorBase instance; 
	
	
	public void readProperties() throws Exception
	{
		Properties prop = new Properties();
		prop.load(getClass().getResourceAsStream("configurador.properties"));

		ConfiguradorBase.AplicacionNombre = prop.getProperty("Aplicacion.Nombre");
		ConfiguradorBase.AplicacionIcono = SWTResourceManager.getImage(ConfiguradorBase.class, prop.getProperty("Aplicacion.Icono"));
		ConfiguradorBase.AplicacionLogo = SWTResourceManager.getImage(ConfiguradorBase.class, prop.getProperty("Aplicacion.NombreLogo"));
		ConfiguradorBase.AplicacionVersion =  prop.getProperty("Aplicacion.Version");
		
		ConfiguradorBase.BaseDatosServidor = prop.getProperty("BaseDatos.Server");
		ConfiguradorBase.BaseDatosNombre = prop.getProperty("BaseDatos.Name");
		ConfiguradorBase.BaseDatosUsuario = prop.getProperty("BaseDatos.Username");
		ConfiguradorBase.BaseDatosContraseña = prop.getProperty("BaseDeDatos.Password");
		
		ConfiguradorBase.FormatoDate =  prop.getProperty("Formatos.Date");
		ConfiguradorBase.FormatoDateTime =  prop.getProperty("Formatos.DateTime");
		
		String recordar =   prop.getProperty("Login.RecordarCredenciales");
		if ( recordar == null || recordar.equals("false") )
			ConfiguradorBase.LoginRecordarCredenciales = false;
		else
			ConfiguradorBase.LoginRecordarCredenciales = true;
		ConfiguradorBase.LoginUsuario =  prop.getProperty("Login.Usuario");
		ConfiguradorBase.LoginContraseña =  prop.getProperty("Login.Contraseña");
		
		
	}
	
	public void writeProperties() throws Exception
	{
		Properties prop = new Properties();
		prop.load(getClass().getResourceAsStream("configurador.properties"));

		prop.setProperty("Aplicacion.Nombre", ConfiguradorBase.AplicacionNombre );
		prop.setProperty("Aplicacion.Version", ConfiguradorBase.AplicacionVersion);

		prop.getProperty("BaseDatos.Server", ConfiguradorBase.BaseDatosServidor);
		prop.setProperty("BaseDatos.Name", ConfiguradorBase.BaseDatosNombre);
		prop.setProperty("BaseDatos.Username", ConfiguradorBase.BaseDatosUsuario);
		prop.setProperty("BaseDeDatos.Password",ConfiguradorBase.BaseDatosContraseña );

		prop.setProperty("Formatos.Date",ConfiguradorBase.FormatoDate );
		prop.setProperty("Formatos.DateTime", ConfiguradorBase.FormatoDateTime );

		prop.store(new FileOutputStream(getClass().getResource("configurador.properties").getFile()), null);
	}


	public void writeCredenciales(boolean recordar, String user, String pass) throws Exception
	{
		Properties prop = new Properties();
		prop.load(getClass().getResourceAsStream("configurador.properties"));

		if ( recordar )
			prop.setProperty("Login.RecordarCredenciales", "true");
		else
			prop.setProperty("Login.RecordarCredenciales", "false");
		
		prop.setProperty("Login.Usuario", user );
		prop.setProperty("Login.Contraseña", pass );

		prop.store(new FileOutputStream(getClass().getResource("configurador.properties").getFile()), null);
	}

	public static ConfiguradorBase initialize() throws Exception {

		instance = new ConfiguradorBase();
		instance.readProperties();

		return instance;
	}

}
