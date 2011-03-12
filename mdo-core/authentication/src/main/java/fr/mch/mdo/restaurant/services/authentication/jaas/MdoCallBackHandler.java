package fr.mch.mdo.restaurant.services.authentication.jaas;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.authentication.IMdoCallback;
import fr.mch.mdo.restaurant.authentication.MdoCallBackType;
import fr.mch.mdo.restaurant.dao.authentication.AuthenticationPasswordLevel;
import fr.mch.mdo.restaurant.exception.MdoAuthenticationException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

/**
 * @author Mathieu MA
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class MdoCallBackHandler implements CallbackHandler, ILoggerBean
{
    private ILogger logger;

    /**
     * The field contains all the callback that this class will handle.
     */
    private Map<MdoCallBackType, Object> properties = new HashMap<MdoCallBackType, Object>(); 
    
    public MdoCallBackHandler(String login, String password, AuthenticationPasswordLevel levelPassword) {
	this.logger = LoggerServiceImpl.getInstance().getLogger(PasswordMdoCallback.class.getName());
	// 3 callbacks will be handled
	properties.put(MdoCallBackType.MDO_CALLBACK_NAME, login);
	properties.put(MdoCallBackType.MDO_CALLBACK_PASSWORD, password);
	properties.put(MdoCallBackType.MDO_CALLBACK_LEVEL_PASSWORD, levelPassword);
    }

    @Override
    public ILogger getLogger() {
	return logger;
    }

    @Override
    public void setLogger(ILogger logger) {
	this.logger = logger;
    }

    /**
     * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback)
     */
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
	Callback cb = null;
	try {
	    if (callbacks != null && callbacks.length != 0) {
		for (int i = 0; i < callbacks.length; i++) {
		    cb = callbacks[i];
		    if (cb instanceof IMdoCallback) {
			IMdoCallback  mdoCallback = (IMdoCallback) cb;
			mdoCallback.callback(properties);
		    } else {
			throw new MdoAuthenticationException("message.error.authentication.callback.unsupported", new Object[] {cb});
		    }
		}
	    } else {
		throw new MdoAuthenticationException("message.error.authentication.callbacks.null");
	    }
	}
	catch(Exception e){
	    throw new UnsupportedCallbackException(cb, e.getLocalizedMessage());
	} finally { 
	    // Data are only used once
	    properties.clear();
	}
    }

    @Override
    public String toString() {
	return "MdoCallBackHandler [properties=" + properties + "]";
    }

}
