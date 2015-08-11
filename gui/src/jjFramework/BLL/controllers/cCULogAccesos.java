package jjFramework.BLL.controllers;


import java.util.List;

import jjFramework.BLL.utils.SessionsFactory;
import jjFramework.BLL.utils.UtilidadesGenericas;
import jjFramework.gui.utils.DateTimeUtils;

import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

import ILGestPojos.models.Logaccesos;
import ILGestPojos.models.Usuarios;


/**
 * 
 * @author PC-1-Aipai
 *
 */
@SuppressWarnings("unchecked")
public class cCULogAccesos extends cCUBase{

	private Logaccesos logAcceso = null;
	
	private cCULogAccesos() throws Exception {super();	}
	
	public static cCULogAccesos start() throws Exception
	{
		cCULogAccesos ctrl = new cCULogAccesos();
		return ctrl;
	}
	
	@Override public void stop()
	{
		super.stop();
		logAcceso = null;
	}
	
	public void CrearLogSession(Usuarios user) throws Exception
	{
		if (!isControllerStarted()) throw new Exception("La controladora no ha sido inicializada");
		
		logAcceso = Logaccesos.ObtenerNuevoModelo();
		logAcceso.setCodigo(UtilidadesGenericas.getRamdonKey());
		logAcceso.setUsuarios(user);
		logAcceso.setFechaEntrada(DateTime.now().toDate());
		logAcceso.setEquipo(System.getenv("COMPUTERNAME"));
		logAcceso.setEmpresas(user.getEmpresas());
		
		saveLog();

	}
	
	public void FinalizarLogSession() throws Exception
	{
		if (!isControllerStarted()) throw new Exception("La controladora no ha sido inicializada");
		
		logAcceso.setFechaSalida(DateTime.now().toDate());
		
		saveLog();
	}

	private void saveLog() throws Exception
	{
		try
		{
			if (!isControllerStarted()) throw new Exception("La controladora no ha sido inicializada");
	
			session = SessionsFactory.beginTransaction();
			session.saveOrUpdate(logAcceso);
			SessionsFactory.commitTransaction();
		} catch (Exception ex)
		{
			SessionsFactory.rollbackTransaction();
			throw ex;
		} finally
		{
			logAcceso.setIsNew(false);
			logAcceso.setIsDirty(false);
			SessionsFactory.closeSession();
		}
	}

	public static List<Logaccesos> obtenerListado() throws Exception
	{
		cCUBase.checkSecurity();
		try
		{
			criteria = SessionsFactory.openCriteria(Logaccesos.class, "log")
					.setFetchMode(Logaccesos.USUARIOS, FetchMode.JOIN)
					.setFetchMode(Logaccesos.USUARIOS + "." + Usuarios.PERSONAS, FetchMode.JOIN)
					.addOrder(Order.desc(Logaccesos.FECHAENTRADA));

			return  criteria.list();
		
		} finally
		{
			SessionsFactory.closeCriteria();
		}
	}

	public static List<Logaccesos> obtenerListadoFiltrado(String descripcion, DateTime fecha, String username) throws Exception
	{

		cCUBase.checkSecurity();
		try
		{
			criteria = SessionsFactory.openCriteria(Logaccesos.class, "log")
					.setFetchMode(Logaccesos.USUARIOS, FetchMode.JOIN)
					.setFetchMode(Logaccesos.USUARIOS + "." + Usuarios.PERSONAS, FetchMode.JOIN)
					.createAlias(Logaccesos.USUARIOS, "user")
					.createAlias(Logaccesos.USUARIOS + "." + Usuarios.PERSONAS, "per")
					.addOrder(Order.desc(Logaccesos.FECHAENTRADA));

			//Añadimos los filtros necesarios al criteria
			if (descripcion != null && descripcion != "")
			{
				criteria.add( Restrictions.like("per.nombre", descripcion + "%"));
			}
			if (fecha != null)
			{
				DateTime dtFrom = DateTimeUtils.obtenerFechaSinHora( fecha );
				DateTime dtTo = DateTimeUtils.obtenerFechaFinalDelDia(fecha);

				criteria.add(Restrictions.between(Logaccesos.FECHAENTRADA,  dtFrom.toDate(), dtTo.toDate()));
			}
			if (username != null)
			{
				criteria.add( Restrictions.eq("user.username", username));
			}

			return  criteria.list();

		} finally
		{
			SessionsFactory.closeCriteria();
		}
	}

	public static Logaccesos obtenerUltimoAcceso() throws Exception
	{
		cCUBase.checkSecurity();
		try
		{
			criteria = SessionsFactory.openCriteria(Logaccesos.class, "log")
					.setMaxResults(2)
					.setFetchMode(Logaccesos.USUARIOS, FetchMode.JOIN)
					.addOrder(Order.desc(Logaccesos.FECHAENTRADA));

			List<Logaccesos> lista = criteria.list();
			if ( lista == null || lista.size() < 2) return null;

			//Devolvemos el segundo de la lista porque el primer elemento es el Login actual de la session
			return lista.get(1);

		} finally
		{
			SessionsFactory.closeCriteria();
		}
	}
}
