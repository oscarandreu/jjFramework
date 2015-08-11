package jjFramework.BLL.controllers;

import java.util.List;

import jjFramework.BLL.utils.SessionsFactory;
import jjFramework.BLL.utils.UtilidadesGenericas;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import alfresco.DocumentClient;
import alfresco.RepositoryNode;

import ILGestPojos.models.Mapeodocumentacion;


public class cCUDocumentos extends cCUBase {

	private cCUDocumentos() throws Exception {
		super();
	}

	public static cCUDocumentos start() throws Exception {
		cCUDocumentos ctrl = new cCUDocumentos();
		return ctrl;
	}

	@Override
	public void stop() {
		super.stop();
	}

	@SuppressWarnings("unchecked")
	public Mapeodocumentacion obtener(byte[] codigo) throws Exception {
		Mapeodocumentacion documento = null;

		try {
			if (!isControllerStarted())
				throw new Exception("La controladora no ha sido inicializada");

			criteria = SessionsFactory
					.openCriteria(Mapeodocumentacion.class,
							"Mapeodocumentacion")
							.add(Restrictions.eq("codigo", codigo)).setMaxResults(1);

			List<Mapeodocumentacion> list = criteria.list();
			if (list.size() > 0)
				documento = list.get(0);

		} finally {
			SessionsFactory.closeCriteria();
		}

		return documento;
	}

	@SuppressWarnings("unchecked")
	public List<Mapeodocumentacion> obtenerTodos() throws Exception {
		List<Mapeodocumentacion> list = null;

		try {
			if (!isControllerStarted())
				throw new Exception("La controladora no ha sido inicializada");

			criteria = SessionsFactory
					.openCriteria(Mapeodocumentacion.class,
							"Mapeodocumentacion");
			list = criteria.list();

		} finally {
			SessionsFactory.closeCriteria();
		}

		return list;
	}


	public Mapeodocumentacion nuevo() throws Exception {
		if (!isControllerStarted())
			throw new Exception("La controladora no ha sido inicializada");

		Mapeodocumentacion documento = Mapeodocumentacion.ObtenerNuevoModelo();
		documento.setCodigo(UtilidadesGenericas.getRamdonKey());

		return documento;
	}

	public void borrar(Mapeodocumentacion documento) throws Exception {
		try {
			if (!isControllerStarted())
				throw new Exception("La controladora no ha sido inicializada");

			session = SessionsFactory.beginTransaction();
			session.delete(documento);

			SessionsFactory.commitTransaction();

			documento = null;
		} catch (Exception ex) {
			SessionsFactory.rollbackTransaction();
			throw ex;
		} finally {
			SessionsFactory.closeSession();
		}
	}

	public void save(List<Mapeodocumentacion> documentacion, String path, Session session) throws Exception {
		if (!isControllerStarted())
			throw new Exception("La controladora no ha sido inicializada");
		
		for (Mapeodocumentacion documento: documentacion) {
			save(documento, path, session);
		}
	}

	public void save(Mapeodocumentacion documento, String path, Session session) throws Exception {
		try {
			if (!isControllerStarted())
				throw new Exception("La controladora no ha sido inicializada");

			//Sí el documento es nuevo hay que salvarlo, más adelante se resolverá la actualización (¿mejor crear una nueva revisión del documento en vez de actualizarlo?)
			if(documento.hasToUpload())
			{
				DocumentClient client = new DocumentClient();
				RepositoryNode node = client.uploadDocument(documento.getCurrentDocumentPath(), path);
				documento.setDocumentPath(node.getPath());
				documento.setDocumentUuid(node.getUuid());
				saveOrUpdate(documento);
				
			}
			else if(documento.hasToDelete())
			{
				DocumentClient client = new DocumentClient();
				session.delete(documento);	
				try {
					client.deleteDocument(documento.getDocumentPath());
				} catch (Exception e) {
					//Si no se puede borrar, seguramente no existe,no se hace nada
				}
				
			}else
				saveOrUpdate(documento);
			

		} catch (Exception ex) {
			throw ex;

		} finally {
			documento.setIsNew(false);
			documento.setIsDirty(false);
		}
	}


}
