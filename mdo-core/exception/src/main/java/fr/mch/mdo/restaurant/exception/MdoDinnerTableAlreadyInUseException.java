package fr.mch.mdo.restaurant.exception;

import java.util.Locale;

public class MdoDinnerTableAlreadyInUseException extends MdoMessageManagerException 
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    public MdoDinnerTableAlreadyInUseException() {
	super();
    }
    
    public MdoDinnerTableAlreadyInUseException(Throwable cause) {
	super(cause);
    }

    public MdoDinnerTableAlreadyInUseException(String message) {
	super(message);
    }

    public MdoDinnerTableAlreadyInUseException(String message, Object[] params) {
	super(message, params);
    }

    public MdoDinnerTableAlreadyInUseException(String message, Throwable cause) {
	super(message, cause);
    }

    public MdoDinnerTableAlreadyInUseException(String message, Object[] params, Throwable cause) {
	super(message, params, cause);
    }

    public MdoDinnerTableAlreadyInUseException(Locale locale, String message) {
	super(locale, message);
    }

    public MdoDinnerTableAlreadyInUseException(Locale locale, String message, Object[] params) {
	super(locale, message, params);
    }

    public MdoDinnerTableAlreadyInUseException(Locale locale, String message, Throwable cause) {
	super(locale, message, cause);
    }

    public MdoDinnerTableAlreadyInUseException(Locale locale, String message, Object[] params, Throwable cause) {
	super(locale, message, params, cause);
    }
}
