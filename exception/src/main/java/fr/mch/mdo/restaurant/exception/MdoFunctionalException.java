package fr.mch.mdo.restaurant.exception;

import java.util.Locale;

public class MdoFunctionalException extends MdoMessageManagerException 
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    public MdoFunctionalException() {
	super();
    }
    
    public MdoFunctionalException(Throwable cause) {
	super(cause);
    }

    public MdoFunctionalException(String message) {
	super(message);
    }

    public MdoFunctionalException(String message, Object[] params) {
	super(message, params);
    }

    public MdoFunctionalException(String message, Throwable cause) {
	super(message, cause);
    }

    public MdoFunctionalException(String message, Object[] params, Throwable cause) {
	super(message, params, cause);
    }

    public MdoFunctionalException(Locale locale, String message) {
	super(locale, message);
    }

    public MdoFunctionalException(Locale locale, String message, Object[] params) {
	super(locale, message, params);
    }

    public MdoFunctionalException(Locale locale, String message, Throwable cause) {
	super(locale, message, cause);
    }

    public MdoFunctionalException(Locale locale, String message, Object[] params, Throwable cause) {
	super(locale, message, params, cause);
    }
}
