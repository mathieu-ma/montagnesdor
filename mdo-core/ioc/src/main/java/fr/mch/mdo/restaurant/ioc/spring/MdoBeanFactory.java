package fr.mch.mdo.restaurant.ioc.spring;

import fr.mch.mdo.i18n.IMessageQuery;
import fr.mch.mdo.i18n.MessageQueryResourceBundleImpl;
import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerService;
import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.IocBeanName;
import fr.mch.mdo.restaurant.authentication.IMdoAuthenticationService;
import fr.mch.mdo.restaurant.authorization.IMdoAuthorizationService;
import fr.mch.mdo.restaurant.exception.MdoTechnicalException;
import fr.mch.mdo.restaurant.ioc.IBeanFactory;
import fr.mch.mdo.restaurant.ioc.IMdoBeanFactory;
import fr.mch.mdo.restaurant.services.business.managers.locales.ILocalesManager;
import fr.mch.mdo.restaurant.services.business.managers.users.IUserAuthenticationsManager;

public class MdoBeanFactory implements IMdoBeanFactory 
{
	private static IBeanFactory beanFactory;
	
	private static class LazyHolder {
		private static IMdoBeanFactory instance = new MdoBeanFactory();
	}
	public static IMdoBeanFactory getInstance() {
		return LazyHolder.instance;
	}

	protected MdoBeanFactory() {
		try {
			if (MdoBeanFactory.beanFactory == null) {
				MdoBeanFactory.beanFactory = new SpringBeanFactory(Constants.SPRING_CONFIGURATION_FILE.split(","));
			}
		} catch (MdoTechnicalException e) {
			// Could not use IOC
			throw new ExceptionInInitializerError(MessageQueryResourceBundleImpl.getInstance().getMessage("message.error.ioc.init.spring"));
		}
	}
	
	@Override
	public Object getBean(IocBeanName beanName) {
		return MdoBeanFactory.beanFactory.getBean(beanName);
	}

	@Override
	public IMdoAuthenticationService getMdoAuthenticationService() {
		return ((IMdoAuthenticationService) this.getBean(IocBeanName.BEAN_AUTHENTICATION_JAAS_NAME));
	}

	@Override
	public IMdoAuthorizationService getMdoAuthorizationService() {
		return ((IMdoAuthorizationService) this.getBean(IocBeanName.BEAN_AUTHORIZATION_JAAS_NAME));
	}

	@Override
	public IUserAuthenticationsManager getUserAuthenticationsManager() {
		return ((IUserAuthenticationsManager) this.getBean(IocBeanName.BEAN_USER_AUTHENTICATIONS_MANAGER_NAME));
	}

	@Override
	public ILocalesManager getLocalesManager() {
		return ((ILocalesManager) this.getBean(IocBeanName.BEAN_LOCALES_MANAGER_NAME));
	}

	@Override
	public IMessageQuery getMessageQuery() {
		return ((IMessageQuery) getBean(IocBeanName.BEAN_I18N_NAME));
	}

	@Override
	public ILogger getLogger() {
		return ((ILoggerService) getBean(IocBeanName.BEAN_LOG_NAME)).getLogger();
	}

	@Override
	public ILogger getLogger(String className) {
		return ((ILoggerService) getBean(IocBeanName.BEAN_LOG_NAME)).getLogger(className);
	}
}
