package fr.mch.mdo.restaurant.exception;

import java.util.Locale;

public class MdoTechnicalException extends MdoMessageManagerException 
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    public MdoTechnicalException() {
	super();
    }
    
    public MdoTechnicalException(Throwable cause) {
	super(cause);
    }

    public MdoTechnicalException(String message) {
	super(message);
    }

    public MdoTechnicalException(String message, Object[] params) {
	super(message, params);
    }

    public MdoTechnicalException(String message, Throwable cause) {
	super(message, cause);
    }

    public MdoTechnicalException(String message, Object[] params, Throwable cause) {
	super(message, params, cause);
    }

    public MdoTechnicalException(Locale locale, String message) {
	super(locale, message);
    }

    public MdoTechnicalException(Locale locale, String message, Object[] params) {
	super(locale, message, params);
    }

    public MdoTechnicalException(Locale locale, String message, Throwable cause) {
	super(locale, message, cause);
    }

    public MdoTechnicalException(Locale locale, String message, Object[] params, Throwable cause) {
	super(locale, message, params, cause);
    }
}
