package fr.mch.mdo.restaurant.dao.hibernate;

import org.hibernate.Session;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoBase;
import fr.mch.mdo.restaurant.dao.ISessionFactory;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;

public abstract class MdoDaoBase implements IDaoBase 
{
	protected ILogger logger;
	private ISessionFactory sessionFactory;
	private IMdoBean bean;

	protected MdoDaoBase(boolean loadSessionFactory) {
		if (loadSessionFactory) {
			sessionFactory = DefaultSessionFactory.getInstance();
		}
	}

	protected MdoDaoBase() {
	}

	@Override
	public ILogger getLogger() {
		return logger;
	}

	@Override
	public void setLogger(ILogger logger) {
		this.logger = logger;
	}

	public ILogger getLogger(String className) {
		return logger.getLogger(className);
	}

	public void setSessionFactory(ISessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public IMdoBean getBean() {
		return this.bean;
	}

	@Override
	public void setBean(IMdoBean bean) {
		this.bean = bean;
	}

	private Object getCurrentSession() throws MdoDataBeanException {
		Session s = sessionFactory.currentSession();
		// Always transactional even with AOP. No matter the number of beginTransaction() is called
//		s.beginTransaction();
		return s;
	}

	protected void closeSession() throws MdoDataBeanException {
//		sessionFactory.currentSession().getTransaction().commit();
		logger.debug("START You are in method closeSession of class " + this.getClass().getName());
		sessionFactory.currentSession().close();
		logger.debug("END You are in method closeSession of class " + this.getClass().getName());
	}


	protected TransactionSession beginTransaction() {
		TransactionSession result = new TransactionSession();
		
		try {
			result.setSession((Session) this.getCurrentSession());
		} catch (MdoDataBeanException e) {
			logger.error("message.error.dao.session", e);
		}
		result.setTransaction(result.getSession().beginTransaction());
//		result.getTransaction().begin();
		return result;
	}

	protected void endTransaction(TransactionSession transactionSession, Object bean, boolean... isLazy) {
		logger.debug("START You are in method endTransaction of class " + this.getClass().getName());
		checkLazyInitialization(bean, isLazy);

		transactionSession.getTransaction().commit();
		logger.debug("END You are in method endTransaction of class " + this.getClass().getName());
	}

	/**
	 * This method is used to initialize a bean depending on the isLazy parameter value.
	 * @param bean the bean to change the state
	 * @param isLazy if true then the state is not change else then try to get recursively all fields of the bean and its children.
	 */
	private void checkLazyInitialization(Object bean, boolean... isLazy) {
		// Default value is false
		// In case of AOP used then we change the value to true by recalling this method with true
		boolean lazy = false;
		if (isLazy.length == 1) {
			lazy = isLazy[0];
		}
		if (!lazy) {
			// isTransactionFromServicesLayer == false == AOP api not used

			// !isLazy == user decision

			if (bean != null) {
				// Force Non Lazy Initialization
				try {
					sessionFactory.initialize(bean);
				} catch (MdoDataBeanException e) {
					logger.error("message.error.dao.initialize.lazy.loading");
				}
			}
		}
	}
}
