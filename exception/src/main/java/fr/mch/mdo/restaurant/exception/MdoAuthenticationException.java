package fr.mch.mdo.restaurant.exception;

import java.util.Locale;

public class MdoAuthenticationException extends MdoMessageManagerException 
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    public MdoAuthenticationException() {
	super();
    }
    
    public MdoAuthenticationException(Throwable cause) {
	super(cause);
    }

    public MdoAuthenticationException(String message) {
	super(message);
    }

    public MdoAuthenticationException(String message, Object[] params) {
	super(message, params);
    }

    public MdoAuthenticationException(String message, Throwable cause) {
	super(message, cause);
    }

    public MdoAuthenticationException(String message, Object[] params, Throwable cause) {
	super(message, params, cause);
    }

    public MdoAuthenticationException(Locale locale, String message) {
	super(locale, message);
    }

    public MdoAuthenticationException(Locale locale, String message, Object[] params) {
	super(locale, message, params);
    }

    public MdoAuthenticationException(Locale locale, String message, Throwable cause) {
	super(locale, message, cause);
    }

    public MdoAuthenticationException(Locale locale, String message, Object[] params, Throwable cause) {
	super(locale, message, params, cause);
    }
}
