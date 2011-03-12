package fr.mch.mdo.restaurant.services.authentication.jaas;

import java.util.Map;

import javax.security.auth.callback.NameCallback;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.authentication.IMdoCallback;
import fr.mch.mdo.restaurant.authentication.MdoCallBackType;
import fr.mch.mdo.restaurant.exception.MdoAuthenticationException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

/**
 * @author user
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class NameMdoCallback extends NameCallback implements IMdoCallback
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    private ILogger logger;

    public NameMdoCallback() {
	super("prompt");
	this.logger = LoggerServiceImpl.getInstance().getLogger(NameMdoCallback.class.getName());
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
	    String name = (String) properties.get(MdoCallBackType.MDO_CALLBACK_NAME);
	    if (name != null) {
		super.setName(name);
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
