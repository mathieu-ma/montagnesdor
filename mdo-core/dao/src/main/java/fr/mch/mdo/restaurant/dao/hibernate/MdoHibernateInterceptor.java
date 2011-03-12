package fr.mch.mdo.restaurant.dao.hibernate;

import java.io.Serializable;
import java.util.Iterator;

import org.hibernate.CallbackException;
import org.hibernate.EntityMode;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

import fr.mch.mdo.logs.ILogger;

import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class MdoHibernateInterceptor implements IMdoHibernateInterceptor {
	private static IMdoHibernateInterceptor instance = null;

	protected ILogger logger;

	public static IMdoHibernateInterceptor getInstance() {
		if (instance == null) {
			synchronized (MdoHibernateInterceptor.class) {
				if (instance == null) {
					instance = new MdoHibernateInterceptor();
					instance.setLogger(LoggerServiceImpl.getInstance().getLogger(MdoHibernateInterceptor.class.getName()));
				}
			}
		}
		return instance;
	}

	@Override
	public void afterTransactionBegin(Transaction arg0) {
		this.getLogger().info("message.report.dao.after.transaction.begin");
	}

	@Override
	public void afterTransactionCompletion(Transaction arg0) {
		this.getLogger().info("message.report.dao.after.transaction.completion");
	}

	@Override
	public void beforeTransactionCompletion(Transaction arg0) {
		this.getLogger().info("message.report.dao.before.transaction.completion");
	}

	@Override
	public int[] findDirty(Object arg0, Serializable arg1, Object[] arg2, Object[] arg3, String[] arg4, Type[] arg5) {
		this.getLogger().info("message.report.dao.find.dirty");
		return null;
	}

	@Override
	public Object getEntity(String arg0, Serializable arg1) throws CallbackException {
		this.getLogger().info("message.report.dao.get.entity");
		return null;
	}

	@Override
	public String getEntityName(Object arg0) throws CallbackException {
		this.getLogger().info("message.report.dao.get.entity.name");
		return null;
	}

	@Override
	public Object instantiate(String arg0, EntityMode arg1, Serializable arg2) throws CallbackException {
		this.getLogger().info("message.report.dao.instantiate");
		return null;
	}

	@Override
	public Boolean isTransient(Object arg0) {
		this.getLogger().info("message.report.dao.is.transient");
		return null;
	}

	@Override
	public void onDelete(Object arg0, Serializable arg1, Object[] arg2, String[] arg3, Type[] arg4) throws CallbackException {
		this.getLogger().info("message.report.dao.on.delete");
	}

	@Override
	public boolean onFlushDirty(Object arg0, Serializable arg1, Object[] arg2, Object[] arg3, String[] arg4, Type[] arg5) throws CallbackException {
		this.getLogger().info("message.report.dao.on.flush.dirty");
		return false;
	}

	@Override
	public boolean onLoad(Object arg0, Serializable arg1, Object[] arg2, String[] arg3, Type[] arg4) throws CallbackException {
		this.getLogger().info("message.report.dao.on.load");
		return false;
	}

	@Override
	public boolean onSave(Object arg0, Serializable arg1, Object[] arg2, String[] arg3, Type[] arg4) throws CallbackException {
		this.getLogger().info("message.report.dao.on.save");
		return false;
	}

	@Override
	public void postFlush(Iterator arg0) throws CallbackException {
		this.getLogger().info("message.report.dao.post.flush");

	}

	@Override
	public void preFlush(Iterator arg0) throws CallbackException {
		this.getLogger().info("message.report.dao.pre.flush");

	}

	public ILogger getLogger() {
		return logger;
	}

	public void setLogger(ILogger logger) {
		this.logger = logger;
	}

	@Override
	public void onCollectionRecreate(Object arg0, Serializable arg1) throws CallbackException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCollectionRemove(Object arg0, Serializable arg1) throws CallbackException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCollectionUpdate(Object arg0, Serializable arg1) throws CallbackException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String onPrepareStatement(String arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
}
