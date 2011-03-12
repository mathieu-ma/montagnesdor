package fr.mch.mdo.restaurant.dao.users.hibernate;

import java.util.HashMap;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.authentication.AuthenticationPasswordLevel;
import fr.mch.mdo.restaurant.dao.beans.Locale;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;
import fr.mch.mdo.restaurant.dao.beans.UserAuthenticationJaas;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.users.IUserAuthenticationsDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultUserAuthenticationsDao extends DefaultDaoServices implements IUserAuthenticationsDao 
{
	private static class LazyHolder {
		private static IUserAuthenticationsDao instance = new DefaultUserAuthenticationsDao(LoggerServiceImpl.getInstance()
				.getLogger(DefaultUserAuthenticationsDao.class.getName()), new UserAuthentication());
	}

	private DefaultUserAuthenticationsDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static IUserAuthenticationsDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultUserAuthenticationsDao() {
	}

	@Override
	public IMdoBean findByUniqueKey(Object propertyValue, boolean... isLazy) throws MdoDataBeanException {
		// Checking exception
		super.findByUniqueKey(propertyValue, isLazy);

		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("login", propertyValue);

		return (IMdoBean) super.findByUniqueKey(propertyValueMap, isLazy);
	}

	@Override
	public IMdoBean findByLogin(String login) throws MdoDataBeanException {

		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("login", login);

		return (IMdoBean) super.findByUniqueKey(UserAuthenticationJaas.class, propertyValueMap);
	}

	@Override
	public void changePrintingLanguage(UserAuthentication userAuthentication, Locale printingLocale) throws MdoDataBeanException {
		if (userAuthentication != null) {
			userAuthentication.setPrintingLocale(printingLocale);
			super.update(userAuthentication);
		} else {
			super.getLogger().error("message.error.dao.field.null", new Object[] { UserAuthentication.class });
			throw new MdoDataBeanException("message.error.dao.field.null", new Object[] { UserAuthentication.class });
		}
	}

	@Override
	public UserAuthentication changePassword(Long id, AuthenticationPasswordLevel levelPassword, String newPassword) throws MdoDataBeanException {
		UserAuthentication result = null;
		result = (UserAuthentication) super.findByPrimaryKey(id);

		this.changePassword(result, levelPassword, newPassword);

		return result;
	}

	@Override
	public void changePassword(UserAuthentication userAuthentication, AuthenticationPasswordLevel levelPassword, String newPassword) throws MdoDataBeanException {
		if (userAuthentication != null) {
			levelPassword.setPassword(userAuthentication, newPassword);
			super.update(userAuthentication);
		} else {
			super.getLogger().error("message.error.dao.field.null", new Object[] { UserAuthentication.class });
			throw new MdoDataBeanException("message.error.dao.field.null", new Object[] { UserAuthentication.class });
		}
	}

	@Override
	public UserAuthentication changePrintingLanguage(Long id, Locale printingLocale) throws MdoDataBeanException {
		UserAuthentication result = null;
		result = (UserAuthentication) super.findByPrimaryKey(id);

		this.changePrintingLanguage(result, printingLocale);

		return result;
	}
}
