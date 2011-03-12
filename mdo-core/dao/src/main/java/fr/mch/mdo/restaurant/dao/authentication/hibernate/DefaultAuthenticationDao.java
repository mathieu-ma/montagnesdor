package fr.mch.mdo.restaurant.dao.authentication.hibernate;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.authentication.AuthenticationPasswordLevel;
import fr.mch.mdo.restaurant.dao.authentication.IAuthenticationDao;
import fr.mch.mdo.restaurant.dao.beans.Locale;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;
import fr.mch.mdo.restaurant.dao.hibernate.MdoDaoBase;
import fr.mch.mdo.restaurant.dao.hibernate.TransactionSession;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultAuthenticationDao extends MdoDaoBase implements IAuthenticationDao 
{
	/**
	 * This class is only used for Singleton lazy initialization
	 */
	private static class InitializeOnDemandHolder {
		private static IAuthenticationDao instance = null;
		static {
			instance = new DefaultAuthenticationDao();
			instance.setLogger(LoggerServiceImpl.getInstance().getLogger(DefaultAuthenticationDao.class.getName()));
		}
	}

	public DefaultAuthenticationDao() {
	}

	/**
	 * This method is used to give an Singleton instance. This method can be
	 * used for testing but normally we must use Spring IOC
	 * 
	 * @return the singleton
	 */
	public static IAuthenticationDao getInstance() {
		return InitializeOnDemandHolder.instance;
	}

	@Override
	public IMdoBean getUserByLogin(String login) throws MdoDataBeanException {
		UserAuthentication result = null;
		try {
			TransactionSession transactionSession = super.beginTransaction();

			Session session = transactionSession.getSession();
			Query q = session.getNamedQuery(Constants.HQL_USER_AUTHENTICATION_SELECT_BY_LOGIN);
			q.setString("login", login);
			result = (UserAuthentication) q.uniqueResult();

			// lazy loading false so we load all fields
			super.endTransaction(transactionSession, result, false);
		} catch (HibernateException e) {
			super.getLogger().debug("message.error.authentication", e);
			throw new MdoDataBeanException("message.error.authentication", e);
		} finally {
			try {
				super.closeSession();
			} catch (HibernateException e) {
				super.getLogger().error("message.error.dao.session.close", e);
				throw new MdoDataBeanException("message.error.dao.session.close", e);
			}
		}

		return result;
	}

	public void changeUserPassword(IMdoBean userAuthentication, AuthenticationPasswordLevel levelPassword, String newPassword) throws MdoDataBeanException {
		UserAuthentication userAuthenticationCasted = null;
		try {
			if (userAuthentication instanceof UserAuthentication) {
				userAuthenticationCasted = (UserAuthentication) userAuthentication;

				TransactionSession transactionSession = super.beginTransaction();

				Session session = transactionSession.getSession();
				levelPassword.setPassword(userAuthenticationCasted, newPassword);

				session.update(userAuthenticationCasted);

				super.endTransaction(transactionSession, userAuthenticationCasted, true);
			}
		} catch (HibernateException e) {
			super.getLogger().debug("message.error.password.save", e);
			throw new MdoDataBeanException("message.error.password.save", e);
		} catch (Exception e) {
			super.getLogger().error("message.error.dao.save", new Object[] { userAuthenticationCasted.getClass().getName(), userAuthenticationCasted.toString() }, e);
			throw new MdoDataBeanException(e);
		} finally {
			try {
				super.closeSession();
			} catch (HibernateException e) {
				super.getLogger().error("message.error.dao.session.close", e);
				throw new MdoDataBeanException("message.error.dao.session.close", e);
			}
		}
	}

	public void changePrintLanguage(IMdoBean user, IMdoBean printingLocale) throws MdoDataBeanException {
		UserAuthentication userCasted = null;
		try {
			Locale printingLocaleCasted = null;
			if (user instanceof UserAuthentication) {
				userCasted = (UserAuthentication) user;
			}

			if (printingLocale instanceof Locale) {
				printingLocaleCasted = (Locale) printingLocale;
			}

			if (userCasted != null && printingLocaleCasted != null) {
				TransactionSession transactionSession = super.beginTransaction();

				Session session = transactionSession.getSession();
				Locale printingLocaleFromDb = (Locale) session.load(Locale.class, (Serializable) printingLocaleCasted.getId());
				userCasted.setPrintingLocale(printingLocaleFromDb);

				session.update(userCasted);

				super.endTransaction(transactionSession, userCasted, true);
			}
		} catch (HibernateException e) {
			super.getLogger().error("message.error.print.language.save", e);
			throw new MdoDataBeanException("message.error.print.language.save", e);
		} catch (Exception e) {
			super.getLogger().error("message.error.dao.save", new Object[] { userCasted.getClass().getName(), userCasted.toString() }, e);
			throw new MdoDataBeanException(e);
		} finally {
			try {
				super.closeSession();
			} catch (HibernateException e) {
				super.getLogger().error("message.error.dao.session.close", e);
				throw new MdoDataBeanException("message.error.dao.session.close", e);
			}
		}
	}
}
