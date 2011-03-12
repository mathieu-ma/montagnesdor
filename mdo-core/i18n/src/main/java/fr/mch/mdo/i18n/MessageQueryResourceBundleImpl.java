package fr.mch.mdo.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class is used to get messages from a properties file.
 * 
 * @author Mathieu
 */
public class MessageQueryResourceBundleImpl implements IMessageQuery 
{
	/** Resource properties to deliver messages. */
	private ResourceBundle messageResource;

	private static class LazyHolder {
		private static IMessageQuery instance = new MessageQueryResourceBundleImpl("fr.mch.mdo.restaurant.resources.i18n.ILoggerMessages");
	}
	public static IMessageQuery getInstance() {
		return LazyHolder.instance;
	}

	/**
	 * Constructor taking class name to indicate the path of resource file.
	 * 
	 * @param className
	 *            class name indicating the path of resource file
	 */
	public MessageQueryResourceBundleImpl(final String className) {
		try {
			messageResource = ResourceBundle.getBundle(className);
		} catch (Exception e) {
			// Do nothing
		}
	}

	/**
	 * Constructor taking class name to indicate the path of resource file.
	 * 
	 * @param className
	 *            class name indicating the path of resource file
	 * @param locale
	 *            of the bundle
	 */
	public MessageQueryResourceBundleImpl(final String className, final Locale locale) {
		try {
			Locale innerLocale = locale;
			if (innerLocale == null) {
				innerLocale = Locale.getDefault();
			}
			messageResource = ResourceBundle.getBundle(className, innerLocale);
		} catch (Exception e) {
			// Do nothing
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.mch.mdo.log.ILoggerMessage#getMessage(java.lang.String)
	 */
	public final String getMessage(final String query) {
		return getMessage(query, null);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.mch.mdo.log.ILoggerMessage#getMessage(java.lang.String,
	 *      java.lang.Object[])
	 */
	public final String getMessage(final String query, final Object[] params) {
		String message = query;
		try {
			message = messageResource.getString(query);
			message = MessageFormat.format(message, params);
		} catch (Exception e) {
			// Could not get the message
		}
		return message;
	}
}
