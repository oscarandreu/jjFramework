package jjFramework.gui.utils;


public class FileManager {

	private static String soName = System.getProperty("os.name").toLowerCase();
	
	private static boolean esWindows(){
	    return (soName.indexOf( "win" ) >= 0); 
	}
 
	private static boolean esMac(){
	    return (soName.indexOf( "mac" ) >= 0); 
	}
 
	private static boolean esUnix(){
	    return (soName.indexOf( "nix") >=0 || soName.indexOf( "nux") >=0);
	}

	public static void openFileFromFileSystem( String fileName ) throws Exception
	{
		String nameFormated = "";
		
		 if ( esWindows() )
			 nameFormated =  String.format("cmd /c start %s", fileName);
		 
		 else if ( esMac() )
			 nameFormated =  String.format("open %s", fileName);
		 
		 else if ( esUnix() )
			 nameFormated =  String.format("gnome-open %s", fileName);
		 
		 else
			 nameFormated =  fileName;
		 
		 Runtime.getRuntime().exec( nameFormated ); 
	}
	
	@SuppressWarnings("unused")
	private static void deleteFile(String filePath)
	{
		
	}
}
