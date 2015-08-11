package jjFramework.BLL.controllers;

import jjFramework.BLL.utils.SessionsFactory;
import jjFramework.BLL.utils.cryptoManager;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import ILGestPojos.models.Oficinas;
import ILGestPojos.models.Roles;
import ILGestPojos.models.Usuarios;

public class cCUSeguridad 
{
	private static cCUSeguridad instance = null;
	private Usuarios user = null;
	private cCULogAccesos ccuLogAcceso = null;

	public String pass;
	public Usuarios getUser() {
		return user;
	}
	private void setUser(Usuarios user) {
		this.user = user;
	}
	
	
	/**Constructor privado
	 * @throws Exception */
	private cCUSeguridad() {}
	
	private void CreateLogAcceso() throws Exception
	{
		ccuLogAcceso = cCULogAccesos.start();
		
		ccuLogAcceso.CrearLogSession(user);
	}

	/**Método para conseguir la controladora de seguridad*/
	public static cCUSeguridad getInstance() throws Exception {
		return instance;
	}
	
	public void finalizeSecurity() throws Exception
	{
		if (ccuLogAcceso == null) throw new Exception("La controladora de seguridad no se ha inizializado correctamente");
		
		ccuLogAcceso.FinalizarLogSession();
		ccuLogAcceso = null;
		
		user = null;
		instance = null;
	}

	public static void initializeSecurity(String userName, String password ) throws HibernateException, Exception{
		try
		{
		Criteria criteria = SessionsFactory.openCriteria(Usuarios.class, "user")
				.setFetchMode(Roles.BBDD_NAME, FetchMode.JOIN)
				.setFetchMode(Oficinas.BBDD_NAME, FetchMode.JOIN)
				.add( Restrictions.eq(Usuarios.USERNAME, userName))
				.add( Restrictions.eq(Usuarios.PASSWORD, cryptoManager.getStringCypher(password, cryptoManager.MD5)))
				.add(Restrictions.eq(Usuarios.DESHABILITADO, false))
				.setMaxResults(1);

		if(criteria.list().size() == 1)
		{
			instance = new cCUSeguridad();
			instance.setUser((Usuarios)criteria.list().get(0));

			SessionsFactory.closeCriteria();

			instance.CreateLogAcceso();
			instance.pass = password;
		}
		} finally
		{
			//Si hay un error de login cerramos la session para que se pueda volver a intentar el login
			if ( SessionsFactory.isSessionOpen )
				SessionsFactory.closeCriteria();
		}

	}
}
