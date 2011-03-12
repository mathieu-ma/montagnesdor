package fr.mch.mdo.restaurant.exception;

import java.util.Locale;

public class MdoDataBeanException extends MdoMessageManagerException 
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    public MdoDataBeanException() {
	super();
    }
    
    public MdoDataBeanException(Throwable cause) {
	super(cause);
    }

    public MdoDataBeanException(String message) {
	super(message);
    }

    public MdoDataBeanException(String message, Object[] params) {
	super(message, params);
    }

    public MdoDataBeanException(String message, Throwable cause) {
	super(message, cause);
    }

    public MdoDataBeanException(String message, Object[] params, Throwable cause) {
	super(message, params, cause);
    }

    public MdoDataBeanException(Locale locale, String message) {
	super(locale, message);
    }

    public MdoDataBeanException(Locale locale, String message, Object[] params) {
	super(locale, message, params);
    }

    public MdoDataBeanException(Locale locale, String message, Throwable cause) {
	super(locale, message, cause);
    }

    public MdoDataBeanException(Locale locale, String message, Object[] params, Throwable cause) {
	super(locale, message, params, cause);
    }
}
