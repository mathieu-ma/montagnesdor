package fr.mch.mdo.restaurant.services.authentication.jaas;

import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.authentication.IMdoCallback;
import fr.mch.mdo.restaurant.authentication.MdoCallBackType;
import fr.mch.mdo.restaurant.dao.authentication.AuthenticationPasswordLevel;
import fr.mch.mdo.restaurant.exception.MdoAuthenticationException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

/**
 * @author user
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class LevelPasswordMdoCallback implements IMdoCallback
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    private ILogger logger;
    
    private AuthenticationPasswordLevel levelPassword = null;

    public LevelPasswordMdoCallback() {
	this.logger = LoggerServiceImpl.getInstance().getLogger(LevelPasswordMdoCallback.class.getName());
    }

    /**
     * Returns the levelPassword.
     * 
     * @return AuthenticationPasswordLevel
     */
    public AuthenticationPasswordLevel getLevelPassword() {
	return levelPassword;
    }

    /**
     * @param AuthenticationPasswordLevel
     */
    public void setLevelPassword(AuthenticationPasswordLevel levelPassword) {
	this.levelPassword = levelPassword;
    }

    @Override
    public ILogger getLogger() {
	return logger;
    }

    @Override
    public void setLogger(ILogger logger) {
	this.logger = logger;
    }

    @Override
    public void callback(Map<MdoCallBackType, Object> properties) throws MdoAuthenticationException {
	try {
	    AuthenticationPasswordLevel levelPassword = (AuthenticationPasswordLevel) properties.get(MdoCallBackType.MDO_CALLBACK_LEVEL_PASSWORD);
	    if (levelPassword != null) {
		this.setLevelPassword(levelPassword);
	    } else {
		logger.fatal("message.error.authentication.callback.data.error", new Object[] {this.getClass().getName()});
		throw new MdoAuthenticationException("message.error.authentication.callback.data.error", new Object[] {this.getClass().getName()});
	    }
	} catch(Exception e) {
	    logger.fatal("message.error.authentication.callback.data.error", new Object[] {this.getClass().getName()}, e);
	    throw new MdoAuthenticationException("message.error.authentication.callback.data.error", new Object[] {this.getClass().getName()}, e);
	}
    }
}
