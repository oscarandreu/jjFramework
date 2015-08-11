package jjFramework.BLL.controllers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import jjFramework.BLL.exceptions.CheckBussinesRulesException;
import jjFramework.BLL.exceptions.CheckDeleteBussinesRulesException;
import jjFramework.BLL.exceptions.CheckValidationException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.joda.time.DateTime;

import ILGestPojos.Personals.Errores;
import ILGestPojos.base.ModelBase;


/**
 * 
 * @author PC-1-Aipai
 *
 */
public class cCUBase 
{
	protected static Session session = null;
	protected static Criteria criteria = null;
	private boolean controllerStarted = false; 
	protected cCUSeguridad ccuSeguridad = null;
	protected List<Errores> listaErrores = null; 
	
	public boolean isControllerStarted() {
		return controllerStarted;
	}

	public cCUBase() throws Exception {
		cCUBase.checkSecurity();
		ccuSeguridad = cCUSeguridad.getInstance();
		controllerStarted = true;
	}

	public final void saveData() throws CheckValidationException, CheckBussinesRulesException, Exception
	{
		try
		{
			if (!controllerStarted) throw new Exception("La controladora no ha sido inicializada");

			if(! hasChanges() )
				return;

			listaErrores = new ArrayList<Errores>();

			checkValidation();

			checkBusinessRules();

			save();

		} finally
		{
			listaErrores =null;
		}
	}

	protected boolean hasChanges() {return true;}

	public void stop()
	{
		controllerStarted = false;
	}
	
	protected void checkBusinessRules() throws CheckBussinesRulesException{}
	
	protected void checkDeleteBusinessRules() throws CheckDeleteBussinesRulesException, Exception{}

	protected void checkValidation() throws CheckValidationException{}

	protected void save() throws Exception{}
	
	public static void checkSecurity() throws Exception
	{
		if(cCUSeguridad.getInstance() == null)
				throw new Exception("La controladora de seguridad no se ha inicializado");
	}

	protected void setDatosModificacion(Object entity, boolean isNew)
	{
		try {
			Field f= entity.getClass().getDeclaredField("lmd");
			f.setAccessible(true);
			f.set(entity,  DateTime.now().toDate());
			
			f= entity.getClass().getDeclaredField("lmu");
			f.setAccessible(true);
			f.set(entity,  ccuSeguridad.getUser().getCodigo());

			if ( isNew )
			{
				f= entity.getClass().getDeclaredField("fmd");
				f.setAccessible(true);
				f.set(entity,  DateTime.now().toDate());
				
				f= entity.getClass().getDeclaredField("fmu");
				f.setAccessible(true);
				f.set(entity,  ccuSeguridad.getUser().getCodigo());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void saveOrUpdate(ModelBase entity)
	{
		if ( entity.isNew() )
			session.save(entity);
		else
			session.update(entity);
	}
}
