package jjFramework.BLL.controllers;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;

import ILGestPojos.Personals.Errores;
import ILGestPojos.models.Agendas;
import ILGestPojos.models.Tareas;

import jjFramework.BLL.exceptions.CheckBussinesRulesException;
import jjFramework.BLL.exceptions.CheckValidationException;
import jjFramework.BLL.utils.SessionsFactory;
import jjFramework.BLL.utils.UtilidadesGenericas;


/**
 * 
 * @author PC-1-Aipai
 *
 */
@SuppressWarnings("unchecked")
public class cCUAgenda extends cCUBase
{
	private Tareas tarea = null;
	
	private cCUAgenda() throws Exception {super();	}
	
	public static cCUAgenda start() throws Exception
	{
		cCUAgenda ctrl = new cCUAgenda();
		return ctrl;
	}
	
	@Override public void stop()
	{
		super.stop();
	}

	public Tareas obtener(byte[] codigo) throws Exception
	{
		try
		{
			if (!isControllerStarted()) throw new Exception("La controladora no ha sido inicializada");
			
			criteria = SessionsFactory.openCriteria(Tareas.class, "tarea")
				.add( Restrictions.eq("codigo", codigo))
				.setMaxResults(1);
			List<Tareas> list = criteria.list();
			if (list.size() == 0)
				return null;
			else
			{
				tarea = list.get(0);
				return tarea;
			}
		} finally
		{
			SessionsFactory.closeCriteria();
		}
	}

	public Tareas nuevo() throws Exception
	{
		if (!isControllerStarted()) throw new Exception("La controladora no ha sido inicializada");
		
		tarea = Tareas.ObtenerNuevoModelo();
		tarea.setCodigo(UtilidadesGenericas.getRamdonKey());
		
		return tarea;
	}

	public void borrar(Tareas tarea) throws Exception
	{
		try
		{
			if (!isControllerStarted()) throw new Exception("La controladora no ha sido inicializada");
			
			session = SessionsFactory.beginTransaction();
			session.delete(tarea);
			
			SessionsFactory.commitTransaction();
			
			tarea = null;
		} catch(Exception ex)
		{
			SessionsFactory.rollbackTransaction();
			throw ex;
		} finally 
		{
			SessionsFactory.closeSession();
		}
	}
	
	@Override public void save() throws Exception
	{
		try
		{
			if (!isControllerStarted()) throw new Exception("La controladora no ha sido inicializada");
			
			session = SessionsFactory.beginTransaction();
			session.saveOrUpdate(tarea);
			SessionsFactory.commitTransaction();
		} catch (Exception ex)
		{
			SessionsFactory.rollbackTransaction();
			throw ex;
		} finally
		{
			tarea.setIsNew(false);
			tarea.setIsDirty(false);
			SessionsFactory.closeSession();
		}
	}
	
	@Override protected void checkValidation() throws CheckValidationException 
	{
		 ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		 Validator validator = validatorFactory.getValidator();
		      
		 Set<javax.validation.ConstraintViolation<Tareas>> validationErrors = validator.validate(tarea);
		 for(ConstraintViolation<Tareas> err: validationErrors) {
			 listaErrores.add(new Errores(err.getMessage(), err.getRootBeanClass(), err.getPropertyPath().toString())); 
		 }

		if ( listaErrores.size() != 0 ) throw new CheckValidationException(listaErrores);
	}
	
	@Override protected void checkBusinessRules() throws CheckBussinesRulesException 
	{
		if (tarea.getFechaFin().compareTo(tarea.getFechaInicio()) < 0)
		{
			throw new CheckBussinesRulesException("La fecha de fin no puede ser anterior que la fecha de Inicio");
		}
		
	}
	
	public static List<Agendas> obtenerPorUsuario(byte[] codigo) throws Exception
	{
		cCUBase.checkSecurity();
		try
		{
			criteria = SessionsFactory.openCriteria(Agendas.class, "ag")
				.setFetchMode(Tareas.BBDD_NAME, FetchMode.JOIN)
				;//.add( Restrictions.eq(Agendas.USUARIOS + "." + Usuarios.CODIGO, codigo));
			
			return criteria.list();

		} finally
		{
			SessionsFactory.closeCriteria();
		}
	}

}
