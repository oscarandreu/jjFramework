package jjFramework.BLL.utils;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class SessionsFactory {

	private static Session session = null;
	private static Criteria criteria = null;
	private static SessionFactory sessionFactory = null;
	private static Configuration config = null;
	public static boolean isSessionOpen = false;
	
	private static SessionFactory  GetSessionFactory()
	{
		if (sessionFactory == null )		
		{
			config = new Configuration();
			config.configure();
		}
		sessionFactory = config.buildSessionFactory();
		return sessionFactory;
	}
	
	public static void initializeConnection() throws Exception
	{
		SessionsFactory.openSession();
		SessionsFactory.closeSession();
	}
	
	public static Session openSession() throws Exception
	{
		if (session != null)
		{
			throw new Exception("La conexion ya esta inicializada");
		}
		
		session = GetSessionFactory().openSession();
		isSessionOpen = true;
		return session;
	}
	
	public static  void closeSession()
	{
		if (session != null && session.isOpen())
		{
			session.disconnect();
			session.flush();
			session.close();
			session = null;
			sessionFactory.close();
			isSessionOpen = false;
		}
	}
	
	public static  Session beginTransaction() throws Exception
	{
		session = openSession();
		session.beginTransaction();
		return session;
	}
	
	public static void commitTransaction()
	{
		if (session != null)
		{
			session.getTransaction().commit();
			closeSession();
		}
	}
	
	public  static void rollbackTransaction()
	{
		if (session != null)
		{
			session.getTransaction().rollback();
			closeSession();
		}
	}
	
	public static Criteria openCriteria(Class<?> clase, String alias) throws Exception
	{

		openSession();
		criteria = session.createCriteria(clase, alias);

		return criteria;
	}

	public static void closeCriteria() throws Exception 
	{
		criteria = null;

		closeSession();
	}	

}
