package fr.mch.mdo.restaurant.exception;

import java.util.Locale;

import fr.mch.mdo.i18n.IMessageQuery;
import fr.mch.mdo.i18n.MessageQueryResourceBundleImpl;
import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.resources.IResources;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class MdoMessageManagerException extends MdoException 
{
    /**
     * Default Serial Version UID
     */
    private static final long serialVersionUID = 1L;
    
    private static ILogger logger = LoggerServiceImpl.getInstance().getLogger(MdoMessageManagerException.class.getName());

    private IMessageQuery messageQuery = new MessageQueryResourceBundleImpl(IResources.EXCEPTION_RESOURCE_BUNDLE_MESSAGES_FILE);

    private Object[] params;
    
    private Locale locale = Locale.getDefault();
    
    public MdoMessageManagerException() {
	
    }

    public MdoMessageManagerException(Throwable cause) {
	super(cause);
    }

    public MdoMessageManagerException(String message, Object[] params) {
	super(message);
	this.params = params;
    }

    public MdoMessageManagerException(String message, Object[] params, Throwable cause) {
	super(message, cause);
	this.params = params;
    }

    public MdoMessageManagerException(String message) {
	super(message);
    }

    public MdoMessageManagerException(String message, Throwable cause) {
	super(message, cause);
    }

    public MdoMessageManagerException(Locale locale, String message, Object[] params) {
	super(message);
	this.locale = locale;
	this.params = params;
    }

    public MdoMessageManagerException(Locale locale, String message, Object[] params, Throwable cause) {
	super(message, cause);
	this.locale = locale;
	this.params = params;
    }

    public MdoMessageManagerException(Locale locale, String message) {
	super(message);
	this.locale = locale;
    }

    public MdoMessageManagerException(Locale locale, String message, Throwable cause) {
	super(message, cause);
	this.locale = locale;
    }

    @Override
    public String getLocalizedMessage() {
        String result = super.getLocalizedMessage();
        
        if (locale != null) {
            messageQuery = new MessageQueryResourceBundleImpl(IResources.EXCEPTION_RESOURCE_BUNDLE_MESSAGES_FILE, locale);
        }
        result = processQuery(this.getMessage(), this.params);
        
        return result;
    }

    protected String processQuery(final String query, final Object[] params) {
	String message = query;
	message = messageQuery.getMessage(message, params);
	logger.debug(message);
	return message;
    }
}
