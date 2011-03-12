package fr.mch.mdo.restaurant.exception;

import java.util.Locale;

public class MdoBusinessException extends MdoMessageManagerException 
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    public MdoBusinessException() {
	super();
    }
    
    public MdoBusinessException(Throwable cause) {
	super(cause);
    }

    public MdoBusinessException(String message) {
	super(message);
    }

    public MdoBusinessException(String message, Object[] params) {
	super(message, params);
    }

    public MdoBusinessException(String message, Throwable cause) {
	super(message, cause);
    }

    public MdoBusinessException(String message, Object[] params, Throwable cause) {
	super(message, params, cause);
    }

    public MdoBusinessException(Locale locale, String message) {
	super(locale, message);
    }

    public MdoBusinessException(Locale locale, String message, Object[] params) {
	super(locale, message, params);
    }

    public MdoBusinessException(Locale locale, String message, Throwable cause) {
	super(locale, message, cause);
    }

    public MdoBusinessException(Locale locale, String message, Object[] params, Throwable cause) {
	super(locale, message, params, cause);
    }
}
