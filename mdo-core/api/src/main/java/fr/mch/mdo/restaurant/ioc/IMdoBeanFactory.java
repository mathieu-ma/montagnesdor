package fr.mch.mdo.restaurant.ioc;

import fr.mch.mdo.i18n.IMessageQuery;
import fr.mch.mdo.logs.ILogger;

import fr.mch.mdo.restaurant.authentication.IMdoAuthenticationService;
import fr.mch.mdo.restaurant.authorization.IMdoAuthorizationService;
import fr.mch.mdo.restaurant.services.business.managers.locales.ILocalesManager;
import fr.mch.mdo.restaurant.services.business.managers.users.IUserAuthenticationsManager;

public interface IMdoBeanFactory extends IBeanFactory
{
	IMessageQuery getMessageQuery();

	ILogger getLogger();
    
    ILogger getLogger(String className);

    IMdoAuthorizationService getMdoAuthorizationService();

    IMdoAuthenticationService getMdoAuthenticationService();
    
    IUserAuthenticationsManager getUserAuthenticationsManager();
    
    ILocalesManager getLocalesManager();
}
