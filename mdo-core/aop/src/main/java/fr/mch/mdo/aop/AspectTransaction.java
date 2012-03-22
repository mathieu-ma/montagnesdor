/*
 * Created on 13 juin 2004
 *
 * 
 * 
 */
package fr.mch.mdo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Session;
import org.hibernate.Transaction;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultSessionFactory;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.services.business.managers.IMdoManager;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;


/**
 * @author Mathieu MA
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
//@Aspect("percflow(bussinessMethod())")
@Aspect()
public class AspectTransaction
{
	private ILogger logger = LoggerServiceImpl.getInstance().getLogger(AspectTransaction.class.getName());

	private Session session;
	
	public AspectTransaction() {
//		logger.debug("New Instance of AspectTransaction==>" + this);
//		try {
//			session = DefaultSessionFactory.getInstance().currentSession();
//		} catch (MdoDataBeanException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	@Pointcut("call(* fr.mch.mdo.restaurant.services.business.managers.*+.*(..) throws fr.mch.mdo.restaurant.exception.MdoException+)")
	public void bussinessMethod() {
	}
	/**
	 * In some cases aspect can process twice. 
	 * This happens if code inside the package itself calls a public method of the package.
	 * In that case this code will process at both the outermost call into the package and the re-entrant call.
	 * The !this() pointcut can be used in a nice way to exclude these re-entrant calls.
	 * 
	 * @param pjp
	 * @param manager
	 * @return
	 * @throws Throwable
	 */
	@Around("bussinessMethod() && target(manager) && !this(fr.mch.mdo.restaurant.services.business.managers.IMdoManager)")
	public Object aroundBussinessMethod(ProceedingJoinPoint pjp, IMdoManager manager) throws Throwable {
		logger.debug("START AOP aroundTransaction with " + manager + " == " + pjp.getSignature());
		Object result = null;
		// The true parameter value is used to specify that we are in service layer transaction
//		Session session = (Session) manager.getDao().getCurrentSession();
		session = DefaultSessionFactory.getInstance().currentSession();
		logger.debug("AOP aroundTransaction Hibernate session " + session);
		Transaction t = session.beginTransaction();
		logger.debug("AOP aroundTransaction Hibernate Transaction " + t);
		try {
			t.begin();
			logger.debug("AOP aroundTransaction before proceed ");
			result = pjp.proceed();
			logger.debug("AOP aroundTransaction after proceed ");
			// The commit from this session(Hibernate.currentSession) is auto close 
			t.commit();
		} catch (Throwable e) {
			t.rollback();
			logger.error("message.error.dao.aop.around.transaction", e);
			throw e;
		} finally {
			try {
				// The true parameter value is used to specify that we want to force to close because we are in service layer transaction
				//manager.getDao().closeSession();
				if (session.isOpen()) {
					session.close();
				}
			} catch (Exception e) {
				logger.error("message.error.dao.session.close", e);
			}
		}
		logger.debug("END AOP aroundTransaction with " + manager + " == " + pjp.getSignature());
		return result;
	}

	@Pointcut("execution(org.hibernate.Session fr.mch.mdo.restaurant.dao.hibernate.MdoDaoBase.getCurrentSession())")
	public void getSession() {
	}
	/**
	 * 
	 * @param pjp
	 * @return
	 */
	@Around("getSession()")
	public Session aroundGetSession(ProceedingJoinPoint pjp) {
		// Do not call pjp.proceed() just return the session of this aspect
		return session;
	}
	
	@Pointcut("call(* *.closeSession()) && within(fr.mch.mdo.aop.AspectTransaction)")
	public void closeSessionCall() {
	}
	@Around("closeSessionCall()")
	public void aroundCloseSessionCall(ProceedingJoinPoint pjp) throws Throwable {
		logger.debug("START AOP aroundCloseSessionCall");
		// Call proceed method in this around advice
		// Because we want to call closeSession only on aroundBussinessMethod advice
		logger.debug("END AOP aroundCloseSessionCall");
	}
	
	@Pointcut("execution(protected void fr.mch.mdo.restaurant.dao.hibernate.MdoDaoBase.closeSession())")
	public void closeSessionExecution() {
	}
	@Around("closeSessionExecution() && !cflow(closeSessionCall())")
	public void aroundCloseSessionExecution(ProceedingJoinPoint pjp) {
		logger.debug("START AOP aroundCloseSessionExecution");
		// Do not call proceed method in this around advice
		// Because we want to call closeSession only on aroundBussinessMethod advice
//		try {
//			pjp.proceed();
//		} catch (Throwable e) {
//			logger.error("message.error.dao.aop.around.close.session", e);
//		}
		
		logger.debug("END AOP aroundCloseSessionExecution");
	}

	@Pointcut("execution(* fr.mch.mdo.restaurant.dao.hibernate.MdoDaoBase.endTransaction(..))")
	public void endTransaction() {
	}
	@Around("endTransaction()")
	public void aroundEndTransaction(ProceedingJoinPoint pjp) {
		logger.debug("START AOP aroundEndTransaction");
		// Do not call proceed method in this around advice
//		try {
//			pjp.proceed();
//		} catch (Throwable e) {
//			logger.error("message.error.dao.aop.around.close.session", e);
//		}
		logger.debug("END AOP aroundEndTransaction");
	}
	
}
