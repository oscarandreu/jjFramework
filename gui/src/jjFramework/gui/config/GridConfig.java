package jjFramework.gui.config;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.*;

import org.eclipse.swt.graphics.Image;

import ILGestPojos.models.Categoriascontacto;
import ILGestPojos.models.Direcciones;
import ILGestPojos.models.Direccionespersona;
import ILGestPojos.models.Expedientes;
import ILGestPojos.models.Festivos;
import ILGestPojos.models.Juzgados;
import ILGestPojos.models.Localidades;
import ILGestPojos.models.Logaccesos;
import ILGestPojos.models.Oficinas;
import ILGestPojos.models.Personas;
import ILGestPojos.models.Representantes;
import ILGestPojos.models.Roles;
import ILGestPojos.models.Usuarios;


import jjFramework.gui.table.XmlColumnDescription;


/**Clase encargada de la configuración de un grid en particular*/
public class GridConfig implements java.io.Serializable
{

	private static final long serialVersionUID = 1L;
	
	/**Columnas que tiene el grid*/
	private List<XmlColumnDescription> columns = new ArrayList<XmlColumnDescription>() ;
	
	public List<XmlColumnDescription> getColumns() {
		return columns;
	}
	public void setColumns(List<XmlColumnDescription> columns) {
		this.columns = columns;
	}
	
	
	public GridConfig(){}
	
	public void addColumn(XmlColumnDescription column)
	{
		columns.add(column);
	}
	
	public static String toXml(GridConfig config)
	{
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		XMLEncoder encoder = new XMLEncoder(ba);
		encoder.writeObject(config);
		encoder.close();

		return ba.toString();
	}
	
	public static GridConfig fromXml(String config) throws UnsupportedEncodingException
	{
		InputStream in = new ByteArrayInputStream(config.getBytes("UTF-8"));
		XMLDecoder decoder = new XMLDecoder(in);
		
		GridConfig retValue = (GridConfig)decoder.readObject();
		decoder.close();	
		
		return retValue;
	}

	public static GridConfig getConfiguracionUsuariosView()
	{
		GridConfig g = new GridConfig();

		g.addColumn( new XmlColumnDescription(Usuarios.USERNAME,"Username",String.class, 120) );
		g.addColumn( new XmlColumnDescription(Usuarios.ROLES + "." + Roles.NOMBRE,"Rol",String.class, 120) );
		g.addColumn( new XmlColumnDescription(Usuarios.DESHABILITADO,"Deshabilitado", Boolean.class, 120) );
		g.addColumn(new XmlColumnDescription(Usuarios.PERSONAS + "." + Personas.NOMBRE,"Nombre",String.class, 190) );
		g.addColumn(new XmlColumnDescription(Usuarios.PERSONAS + "." + Personas.APELLIDOS,"Apellidos",String.class,220) );
		
		return g;
	}

	public static GridConfig getConfiguracionOficinasView()
	{
		GridConfig g = new GridConfig();

		g.addColumn( new XmlColumnDescription(Oficinas.NIF,"CIF",String.class, 90) );
		g.addColumn( new XmlColumnDescription(Oficinas.NOMBRE,"Nombre", String.class, 120) );
		g.addColumn( new XmlColumnDescription(Oficinas.TITULAR,"Titular",String.class, 160) );
		g.addColumn( new XmlColumnDescription(Oficinas.PORDEFECTO,"Predefinida", Boolean.class, 90) );
		g.addColumn( new XmlColumnDescription(Oficinas.DESCRIPCION,"Descripción",String.class, 250) );
		
		return g;
	}
	
	public static GridConfig getConfiguracionCategoriasContactoView()
	{
		GridConfig g = new GridConfig();

		g.addColumn( new XmlColumnDescription(Categoriascontacto.DESCRIPCION, "Descripción",String.class, 200) );
		g.addColumn( new XmlColumnDescription(Categoriascontacto.IMAGEN, "Imagen", Image.class, 90) ); 
		
		return g;
	}

	public static GridConfig getConfiguracionFestivosView()
	{
		GridConfig g = new GridConfig();

		g.addColumn( new XmlColumnDescription(Festivos.FECHA,"Fecha",Date.class, 100) );
		g.addColumn( new XmlColumnDescription(Festivos.DESCRIPCION,"Descripción",String.class, 210) );
		g.addColumn( new XmlColumnDescription(Festivos.EXPORTABLE,"Anual", Boolean.class, 120) );
		g.addColumn( new XmlColumnDescription(Festivos.FIESTALOCAL,"Fiesta local",Boolean.class, 120) );
	
		return g;
	}
	
	public static GridConfig getConfiguracionRolesView()
	{
		GridConfig g = new GridConfig();
		g.addColumn( new XmlColumnDescription(Roles.NOMBRE,"Nombre",String.class, 120) );
		g.addColumn( new XmlColumnDescription(Roles.DESCRIPCION,"Descripción",String.class, 350) );
	
		return g;
	}
	
	public static GridConfig getConfiguracionExpedientesView()
	{
		GridConfig g = new GridConfig();
		g.addColumn( new XmlColumnDescription(Expedientes.CODEXPEDIENTE, "Código", String.class, 90) );
		g.addColumn( new XmlColumnDescription(Expedientes.DESCRIPCION, "Descripción",String.class, 200) );
		g.addColumn( new XmlColumnDescription(Expedientes.UBICACION, "Ubicación física",String.class, 110) );
		//g.addColumn( new XmlColumnDescription(Expedientes.JUZGADOS + "." + Juzgados.DESCRIPCION, "Juzgado",String.class, 120) );
		
	
		return g;
	}
	
	public static GridConfig getConfiguracionJuzgadosView()
	{
		GridConfig g = new GridConfig();
		g.addColumn( new XmlColumnDescription(Juzgados.DESCRIPCION,"Descripción",String.class, 210) );
		g.addColumn( new XmlColumnDescription(Juzgados.SECRETARIO,"Secretario",String.class, 150) );
		g.addColumn( new XmlColumnDescription(Juzgados.TELEFONO,"Teléfono",String.class, 210) );
		g.addColumn( new XmlColumnDescription(Juzgados.OBSERVACIONES,"Observaciones",String.class, 210) );
		
		return g;
	}

	public static GridConfig getConfiguracionLogAccesosView()
	{
		GridConfig g = new GridConfig();

		g.addColumn( new XmlColumnDescription(Logaccesos.USUARIOS + "." + Usuarios.USERNAME, "Username", String.class, 90) );
		g.addColumn( new XmlColumnDescription(Logaccesos.USUARIOS + "." + Usuarios.PERSONAS + "." + Personas.NOMBRE, "Nombre", String.class, 120) );
		g.addColumn( new XmlColumnDescription(Logaccesos.EQUIPO,"Equipo", String.class, 90) );
		g.addColumn( new XmlColumnDescription(Logaccesos.FECHAENTRADA,"Fecha acceso", Date.class, 90) );
		g.addColumn( new XmlColumnDescription(Logaccesos.FECHAENTRADA, "Hora entrada", Date.class, 90, "HH:mm"));
		g.addColumn( new XmlColumnDescription(Logaccesos.FECHASALIDA, "Hora salida", Date.class, 90, "HH:mm"));
		
		return g;
	}

	public static GridConfig getConfiguracionRepresentantesView()
	{
		GridConfig g = new GridConfig();

		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.NOMBRE, "Nombre", String.class, 180) );
		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.APELLIDOS, "Apellidos", String.class, 230) );
		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.CIF, "NIF", String.class, 100) );
		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.PERSONAFISICA, "Persona física", Boolean.class, 100) );
		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.OBSERVACIONES, "Observaciones", String.class, 200) );
		
		return g;
	}
	
	public static GridConfig getConfiguracionAbogadosView()
	{
		GridConfig g = new GridConfig();

		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.NOMBRE, "Nombre", String.class, 180) );
		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.APELLIDOS, "Apellidos", String.class, 230) );
		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.CIF, "NIF", String.class, 100) );
		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.PERSONAFISICA, "Persona física", Boolean.class, 100) );
		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.OBSERVACIONES, "Observaciones", String.class, 200) );
		
		return g;
	}
	
	public static GridConfig getConfiguracionNotariosView()
	{
		GridConfig g = new GridConfig();

		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.NOMBRE, "Nombre", String.class, 180) );
		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.APELLIDOS, "Apellidos", String.class, 230) );
		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.CIF, "NIF", String.class, 100) );
		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.PERSONAFISICA, "Persona física", Boolean.class, 100) );
		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.OBSERVACIONES, "Observaciones", String.class, 200) );
		
		return g;
	}
	
	public static GridConfig getConfiguracionProcuradoresView()
	{
		GridConfig g = new GridConfig();

		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.NOMBRE, "Nombre", String.class, 180) );
		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.APELLIDOS, "Apellidos", String.class, 230) );
		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.CIF, "NIF", String.class, 100) );
		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.PERSONAFISICA, "Persona física", Boolean.class, 100) );
		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.OBSERVACIONES, "Observaciones", String.class, 200) );
		
		return g;
	}
	
	public static GridConfig getConfiguracionPeritosView()
	{
		GridConfig g = new GridConfig();

		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.NOMBRE, "Nombre", String.class, 180) );
		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.APELLIDOS, "Apellidos", String.class, 230) );
		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.CIF, "NIF", String.class, 100) );
		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.PERSONAFISICA, "Persona física", Boolean.class, 100) );
		g.addColumn( new XmlColumnDescription(Representantes.PERSONAS + "." + Personas.OBSERVACIONES, "Observaciones", String.class, 200) );
		
		return g;
	}
	
	public static GridConfig getConfiguracionDireccionesPersonasView()
	{
		GridConfig g = new GridConfig();

		g.addColumn( new XmlColumnDescription(Direccionespersona.DIRECCIONES + "." + Direcciones.DIRECCION, "Dirección", String.class, 300) );
		g.addColumn( new XmlColumnDescription(Direccionespersona.DIRECCIONES + "." + Direcciones.CP, "Código Postal", String.class, 100) );
		g.addColumn( new XmlColumnDescription(Direccionespersona.DIRECCIONES + "." + Direcciones.LOCALIDADES + "." + Localidades.DESCRIPCION, "Localidad", String.class, 176) );
		g.addColumn( new XmlColumnDescription(Direccionespersona.DIRECCIONES + "." + Direcciones.PREDEFINIDA, "Habitual", Boolean.class, 80) );
		
		return g;
	}
}
