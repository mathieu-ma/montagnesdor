package fr.mch.mdo.applets;

import java.applet.Applet;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JPanel;

/**
 * This class is used to display date/time for i18n
 * 
 * @author Mathieu
 * 
 */
public class DateTimeApplet extends Applet
{

	/** Default Serial Version UID */
	private static final long serialVersionUID = 1L;

	/** Key properties for MESSAGE_DEBUG_INIT_KEY */
	public static final String RESOURCE_MESSAGE_DEBUG_INIT_KEY = "message.debug.init";
	/** Key properties for RESOURCE_MESSAGE_ERROR_INIT_KEY */
	public static final String RESOURCE_MESSAGE_ERROR_INIT_KEY = "message.error.init";
	/** Key properties for RESOURCE_MESSAGE_DEBUG_START_KEY */
	public static final String RESOURCE_MESSAGE_DEBUG_START_KEY = "message.debug.start";
	/** Key properties for RESOURCE_MESSAGE_ERROR_START_KEY */
	public static final String RESOURCE_MESSAGE_ERROR_START_KEY = "message.error.start";
	/** Key properties for RESOURCE_MESSAGE_ERROR_GET_PARAMETER_KEY */
	public static final String RESOURCE_MESSAGE_ERROR_GET_PARAMETER_KEY = "message.error.getParameter";
	/** Key properties for RESOURCE_MESSAGE_ERROR_GET_PARAMETER_DEFAULT */
	public static final String RESOURCE_MESSAGE_ERROR_GET_PARAMETER_DEFAULT = "Method getParameter: Could not get parameter %s";
	/** Key properties for RESOURCE_MESSAGE_DEBUG_GET_DATE_SHORT_KEY */
	public static final String RESOURCE_MESSAGE_DEBUG_GET_DATE_SHORT_KEY = "message.debug.getDateShort";
	/** Key properties for RESOURCE_MESSAGE_DEBUG_GET_DATE_KEY */
	public static final String RESOURCE_MESSAGE_DEBUG_GET_DATE_KEY = "message.debug.getDate";
	/** Key properties for RESOURCE_MESSAGE_DEBUG_GET_DATE_TIME_KEY */
	public static final String RESOURCE_MESSAGE_DEBUG_GET_DATE_TIME_KEY = "message.debug.getDateTime";
	/** Key properties for RESOURCE_MESSAGE_DEBUG_GET_TIME_KEY */
	public static final String RESOURCE_MESSAGE_DEBUG_GET_TIME_KEY = "message.debug.getTime";
	/** Key properties for RESOURCE_MESSAGE_DEBUG_GET_SHORT_ENTRY_DATE_KEY */
	public static final String RESOURCE_MESSAGE_DEBUG_GET_SHORT_ENTRY_DATE_KEY = "message.debug.getShortEntryDate";
	/** Key properties for RESOURCE_MESSAGE_ERROR_GET_SHORT_ENTRY_DATE_KEY */
	public static final String RESOURCE_MESSAGE_ERROR_GET_SHORT_ENTRY_DATE_KEY = "message.error.getShortEntryDate";
	/** Key properties for RESOURCE_MESSAGE_DEBUG_GET_DATE_WITH_1_ARG_KEY */
	public static final String RESOURCE_MESSAGE_DEBUG_GET_DATE_WITH_1_ARG_KEY = "message.debug.getDate.1.arg";
	/**
	 * Key properties for RESOURCE_MESSAGE_ERROR_GET_DATE_WITH_4_ARGS_STRING_KEY
	 */
	public static final String RESOURCE_MESSAGE_ERROR_GET_DATE_WITH_4_ARGS_STRING_KEY = "message.error.getDate.4.args.string";
	/** Key properties for RESOURCE_MESSAGE_ERROR_GET_DATE_WITH_4_ARGS_DATE_KEY */
	public static final String RESOURCE_MESSAGE_ERROR_GET_DATE_WITH_4_ARGS_DATE_KEY = "message.error.getDate.4.args.date";

	/** Default value for APPLET_PARAMETER_PATTERN_DISPLAY_DATE_KEY */
	public static final String DEFAULT_PATTERN_DISPLAY_DATE = "EEEE dd MMMM yyyy";
	/** Default value for APPLET_PARAMETER_PATTERN_APPLICATION_DATE_SHORT_KEY */
	public static final String DEFAULT_PATTERN_APPLICATION_DATE_SHORT = "dd/MM/yyyy";
	/** Default value for APPLET_PARAMETER_PATTERN_APPLICATION_DATE_LONG_KEY */
	public static final String DEFAULT_PATTERN_APPLICATION_DATE_LONG = "dd/MM/yyyy/HH/mm/ss";
	/** Default value for APPLET_PARAMETER_PATTERN_DISPLAY_TIME_KEY */
	public static final String DEFAULT_PATTERN_DISPLAY_TIME = "HH:mm:ss";
	/** Default value for APPLET_PARAMETER_PATTERN_DISPLAY_DATE_TIME_KEY */
	public static final String DEFAULT_PATTERN_DISPLAY_DATE_TIME = "EEEE dd MMMM yyyy à HH:mm:ss";

	/** Applet parameters key for debug */
	public static final String APPLET_PARAMETER_DEBUG_KEY = "debug";
	/** Applet parameters key for locale Language */
	public static final String APPLET_PARAMETER_LOCALE_LANGUAGE_KEY = "localeLanguage";
	/** Applet parameters key for pattern format Date */
	public static final String APPLET_PARAMETER_PATTERN_DISPLAY_DATE_KEY = "patternDate";
	/** Applet parameters key for pattern format Date Short */
	public static final String APPLET_PARAMETER_PATTERN_APPLICATION_DATE_SHORT_KEY = "patternDateShort";
	/** Applet parameters key for pattern format Entry Date */
	public static final String APPLET_PARAMETER_PATTERN_APPLICATION_DATE_LONG_KEY = "patternEntryDate";
	/** Applet parameters key for pattern format Time */
	public static final String APPLET_PARAMETER_PATTERN_DISPLAY_TIME_KEY = "patternTime";
	/** Applet parameters key for pattern format Date Time */
	public static final String APPLET_PARAMETER_PATTERN_DISPLAY_DATE_TIME_KEY = "patternDateTime";
	/** Applet parameters key for start Javascript Function */
	public static final String APPLET_PARAMETER_START_JAVASCRIPT_FUNCTION_KEY = "startJavascriptFunction";
	
	/** Default message when the key does not exist in the resource */
	public static final String RESOURCE_MESSAGE_APPLET_PARAMETER_NAME_NOT_FOUND_KEY = "default.message.resource";
	/** Default message when the key does not exist in the resource */
	public static final String DEFAULT_RESOURCE_MESSAGE_APPLET_PARAMETER_NAME_NOT_FOUND = "Resource does not exist for this key %s";
	/** Default message when the resource properties file does not exist */
	public static final String RESOURCE_NOT_FOUND_MESSAGE_ERROR_INIT = "Resource properties file does not exist";

	/** Do you want debug ? */
	private boolean debug = false;
	/** Default locale */
	private Locale locale = Locale.getDefault();
	/** Date format */
	private SimpleDateFormat formatDisplayDate = null;
	/** Short date format */
	private SimpleDateFormat formatApplicationDateShort = null;
	/** Entry Date format : the date to display */
	private SimpleDateFormat formatApplicationDateLong = null;
	/** Time format */
	private SimpleDateFormat formatDisplayTime = null;
	/** Date Time format */
	private SimpleDateFormat formatDisplayDateTime = null;
	/** Javascript function to start */
	private String startJavascriptFunction = null;
	/** entryDate is the starting date of this applet */
	private static String entryDate = null;
	/** Resource properties */
	private ResourceBundle resource = null;

	@Override
	public void init() {

		try {
			startJavascriptFunction = super.getParameter(DateTimeApplet.APPLET_PARAMETER_START_JAVASCRIPT_FUNCTION_KEY);
		} catch (Exception e) {
			// Don't take into account this error.
			debug = false;
		}
		
		debug = Boolean.TRUE.toString().equalsIgnoreCase(this.getParameter(DateTimeApplet.APPLET_PARAMETER_DEBUG_KEY, Boolean.FALSE.toString()));

		// Initialize required fields
		String patternDisplayDate = DateTimeApplet.DEFAULT_PATTERN_DISPLAY_DATE;
		String patternApplicationDateShort = DateTimeApplet.DEFAULT_PATTERN_APPLICATION_DATE_SHORT;
		String patternApplicationDateLong = DateTimeApplet.DEFAULT_PATTERN_APPLICATION_DATE_LONG;
		String patternDisplayTime = DateTimeApplet.DEFAULT_PATTERN_DISPLAY_TIME;
		String patternDisplayDateTime = DateTimeApplet.DEFAULT_PATTERN_DISPLAY_DATE_TIME;
		try {
			String localeLanguage = this.getParameter(DateTimeApplet.APPLET_PARAMETER_LOCALE_LANGUAGE_KEY, Locale.getDefault().getLanguage());
			locale = new Locale(localeLanguage);
			try {
				resource = ResourceBundle.getBundle(DateTimeApplet.class.getName(), locale);
			} catch (Exception e) {
				System.err.println(DateTimeApplet.RESOURCE_NOT_FOUND_MESSAGE_ERROR_INIT);
			}
			patternDisplayDate = this.getParameter(DateTimeApplet.APPLET_PARAMETER_PATTERN_DISPLAY_DATE_KEY, patternDisplayDate);
			patternApplicationDateShort = this.getParameter(DateTimeApplet.APPLET_PARAMETER_PATTERN_APPLICATION_DATE_SHORT_KEY, patternApplicationDateShort);
			patternApplicationDateLong = this.getParameter(DateTimeApplet.APPLET_PARAMETER_PATTERN_APPLICATION_DATE_LONG_KEY, patternApplicationDateLong);
			patternDisplayTime = this.getParameter(DateTimeApplet.APPLET_PARAMETER_PATTERN_DISPLAY_TIME_KEY, patternDisplayTime);
			patternDisplayDateTime = this.getParameter(DateTimeApplet.APPLET_PARAMETER_PATTERN_DISPLAY_DATE_TIME_KEY, patternDisplayDateTime);

			formatDisplayDate = new SimpleDateFormat(patternDisplayDate, locale);
			formatApplicationDateShort = new SimpleDateFormat(patternApplicationDateShort, locale);
			formatApplicationDateLong = new SimpleDateFormat(patternApplicationDateLong, locale);
			formatDisplayTime = new SimpleDateFormat(patternDisplayTime, locale);
			formatDisplayDateTime = new SimpleDateFormat(patternDisplayDateTime, locale);

			if (entryDate == null) {
				entryDate = formatApplicationDateLong.format(new Date());
			}
		} catch (Exception e) {
			debug = true;
			System.err.println(this.getResourceString(DateTimeApplet.RESOURCE_MESSAGE_ERROR_INIT_KEY) + ": ");
			e.printStackTrace();
		}

		if (debug) {
			System.out.println(this.getResourceString(DateTimeApplet.RESOURCE_MESSAGE_DEBUG_INIT_KEY) + ": ");
			System.out.println(DateTimeApplet.APPLET_PARAMETER_PATTERN_DISPLAY_DATE_KEY + ": " + patternDisplayDate);
			System.out.println(DateTimeApplet.APPLET_PARAMETER_PATTERN_APPLICATION_DATE_SHORT_KEY + ": " + patternApplicationDateShort);
			System.out.println(DateTimeApplet.APPLET_PARAMETER_PATTERN_APPLICATION_DATE_LONG_KEY + ": " + patternApplicationDateLong + "==>entryDate: " + entryDate);
			System.out.println(DateTimeApplet.APPLET_PARAMETER_PATTERN_DISPLAY_TIME_KEY + ": " + patternDisplayTime);
			System.out.println(DateTimeApplet.APPLET_PARAMETER_PATTERN_DISPLAY_DATE_TIME_KEY + ": " + patternDisplayDateTime);
			System.out.println(DateTimeApplet.APPLET_PARAMETER_START_JAVASCRIPT_FUNCTION_KEY + ": " + startJavascriptFunction);
		}
	}

	@Override
	public void start() {
		try {
			if (startJavascriptFunction != null) {
				// Call javascript named by startJavascriptFunction
//				getAppletContext().showDocument(new URL("javascript:" + startJavascriptFunction + "()"));
				if (debug) {
					System.out.println(String.format(this.getResourceString(DateTimeApplet.RESOURCE_MESSAGE_DEBUG_START_KEY), startJavascriptFunction));
				}
				JPanel panel = new JPanel(); 
				this.add(panel); 
				panel.addFocusListener(new FocusAdapter() { 
					public void focusGained(FocusEvent event) { 
						// Call javascript named by startJavascriptFunction
						try {
							getAppletContext().showDocument(new URL("javascript:" + startJavascriptFunction + "()"));
						} catch (Exception e) {
							System.err.println(DateTimeApplet.this.getResourceString(DateTimeApplet.RESOURCE_MESSAGE_ERROR_START_KEY));
							debug = true;
							e.printStackTrace();
						}
					} 
				}); 
				panel.requestFocusInWindow();
			}
		} catch (Exception e) {
			System.err.println(this.getResourceString(DateTimeApplet.RESOURCE_MESSAGE_ERROR_START_KEY));
			debug = true;
			e.printStackTrace();
		}
	}

	/**
	 * This method returns the value of the message key if the resource is not
	 * null and the key exists. Else if the resource is null then the method
	 * returns the key itself. Else if the resource is not null and the key does
	 * not exist then it returns a default message with the key.
	 * 
	 * @param key
	 *            message key
	 * @param isDefaultValue
	 *            do we have to set a default value ? If yes the default value
	 *            is the key
	 * @return value of the message key
	 */
	private String getResourceString(String key, boolean isDefaultValue) {
		String result = isDefaultValue ? key : null;

		if (resource != null) {
			try {
				result = resource.getString(key);
			} catch (Exception e) {
				debug = true;
				String resultX = null;
				try {
					resultX = resource.getString(DateTimeApplet.RESOURCE_MESSAGE_APPLET_PARAMETER_NAME_NOT_FOUND_KEY);
				} catch (Exception e2) {
					resultX = DateTimeApplet.DEFAULT_RESOURCE_MESSAGE_APPLET_PARAMETER_NAME_NOT_FOUND;
				}
				resultX = String.format(resultX, key);
				if (isDefaultValue) {
					result = resultX;
				} else {
					System.err.println(resultX);
				}
			}
		}
		return result;
	}

	/**
	 * This method returns the value of the message key if the resource is not
	 * null and the key exists. Else if the resource is null then the method
	 * returns the key itself. Else if the resource is not null and the key does
	 * not exist then it returns a default message with the key.
	 * 
	 * @param key
	 *            message key
	 * @return value of the message key
	 */
	private String getResourceString(String key) {
		return getResourceString(key, true);
	}

	/**
	 * This method check the value of key applet parameter name and returns it
	 * if this not null else it returns the default value
	 * 
	 * @param key
	 *            applet parameter name
	 * @param defaultValue
	 *            default value if there is no value with key applet parameter
	 *            name
	 * @return the value of key applet parameter name if this is not null else
	 *         the default value
	 */
	private String getParameter(String key, String defaultValue) {
		String result = null;

		try {
			// Get parameter from applet
			result = super.getParameter(key);
		} catch (Exception e) {
			debug = true;
			String message = DateTimeApplet.RESOURCE_MESSAGE_ERROR_GET_PARAMETER_DEFAULT;
			if (resource != null) {
				message = this.getResourceString(RESOURCE_MESSAGE_ERROR_GET_PARAMETER_KEY);
			}
			System.err.println(String.format(message, key));
		}
		if (result == null) {
			// Get parameter from resource file
			result = this.getResourceString(key, false);
			if (result == null) {
				result = defaultValue;
			}
		}
		
		if (debug) {
			System.out.println("Parameter " + key + ": " + result);
		}
		return result;
	}

	/**
	 * This method format a string to a string with the first letter in upper
	 * case
	 * 
	 * @param value
	 *            to upper case
	 * @param debugKeyMessage
	 *            debug message key
	 * @return string with first char upper case
	 */
	public static String formatUpperCase(final String value) {
		String result = value;

		if (result != null) {
			int resultLength = result.length();
			if (resultLength > 0) {
				// Just upper case the first letter
				result = value.substring(0, 1).toUpperCase();
				if (resultLength > 1) {
					result += value.substring(1);
				}
			}
		}

		return result;
	}

	/**
	 * 
	 * @return the current formatted date in a short way
	 */
	public String getDateShort() {
		String result = formatApplicationDateShort.format(new Date());
		if (debug) {
			System.out.println(String.format(this.getResourceString(DateTimeApplet.RESOURCE_MESSAGE_DEBUG_GET_DATE_SHORT_KEY), result));
		}
		return result;
	}

	/**
	 * 
	 * @return the current formatted date
	 */
	public String getDate() {
		String result = formatDisplayDate.format(new Date());
		// Just upper case the first letter
		result = formatUpperCase(result);
		if (debug) {
			System.out.println(String.format(this.getResourceString(DateTimeApplet.RESOURCE_MESSAGE_DEBUG_GET_DATE_KEY), result));
		}
		return result;
	}

	/**
	 * 
	 * @return the current formatted date and time
	 */
	public String getDateTime() {
		String result = formatDisplayDateTime.format(new Date());
		// Just upper case the first letter
		result = formatUpperCase(result);
		if (debug) {
			System.out.println(String.format(this.getResourceString(DateTimeApplet.RESOURCE_MESSAGE_DEBUG_GET_DATE_TIME_KEY), result));
		}
		return result;
	}

	/**
	 * 
	 * @return the current formatted time
	 */
	public String getTime() {
		String result = formatDisplayTime.format(new Date());
		if (debug) {
			System.out.println(String.format(this.getResourceString(DateTimeApplet.RESOURCE_MESSAGE_DEBUG_GET_TIME_KEY), result));
		}
		return result;
	}

	/**
	 * 
	 * @return entry date
	 */
	public String getEntryDate() {
		return entryDate;
	}

	/**
	 * 
	 * @param newEntryDate
	 *            new Entry Date
	 */
	public void setEntryDate(final String newEntryDate) {
		entryDate = newEntryDate;
	}

	/**
	 * @return the debug
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @return the formatDisplayDate
	 */
	public SimpleDateFormat getFormatDisplayDate() {
		return formatDisplayDate;
	}

	/**
	 * @return the formatApplicationDateShort
	 */
	public SimpleDateFormat getFormatApplicationDateShort() {
		return formatApplicationDateShort;
	}

	/**
	 * @return the formatApplicationDateLong
	 */
	public SimpleDateFormat getFormatApplicationDateLong() {
		return formatApplicationDateLong;
	}

	/**
	 * @return the formatDisplayTime
	 */
	public SimpleDateFormat getFormatDisplayTime() {
		return formatDisplayTime;
	}

	/**
	 * @return the formatDisplayDateTime
	 */
	public SimpleDateFormat getFormatDisplayDateTime() {
		return formatDisplayDateTime;
	}

	/**
	 * @return the startJavascriptFunction
	 */
	public String getStartJavascriptFunction() {
		return startJavascriptFunction;
	}

	/**
	 * 
	 * @return the formatted entry date in a short way
	 */
	public String getShortEntryDate() {
		String result = "";
		try {
			Date entryDateX = formatApplicationDateLong.parse(DateTimeApplet.entryDate);
			result = formatApplicationDateShort.format(entryDateX);
		} catch (Exception e) {
			debug = true;
			System.err.println(String.format(this.getResourceString(DateTimeApplet.RESOURCE_MESSAGE_ERROR_GET_SHORT_ENTRY_DATE_KEY), DateTimeApplet.entryDate));
			e.printStackTrace();
		}
		if (debug) {
			System.out.println(String.format(this.getResourceString(DateTimeApplet.RESOURCE_MESSAGE_DEBUG_GET_SHORT_ENTRY_DATE_KEY), result));
		}
		return result;
	}

	/**
	 * This method returns a formatted date
	 * 
	 * @param newDate
	 *            date formatted in short way
	 * @return a formatted date
	 */
	public String getDate(String newDate) {
		String newDateX = "";
		if (newDate != null) {
			newDateX = newDate;
		}
		String result = getDate(newDateX, formatApplicationDateShort.toPattern(), formatDisplayDate.toPattern(), true);
		// Just upper case the first letter
		result = formatUpperCase(result);
		if (debug) {
			System.out.println(String.format(this.getResourceString(DateTimeApplet.RESOURCE_MESSAGE_DEBUG_GET_DATE_WITH_1_ARG_KEY), result));
		}
		return result;
	}

	/**
	 * This method gets a string date and returns a string date formatted with
	 * outDatePattern pattern
	 * 
	 * @param inDate
	 *            date to be parsed
	 * @param inDatePattern
	 *            pattern for inDate
	 * @param outDatePattern
	 *            pattern for return date
	 * @param keepCurrentTime
	 *            do we have to keep current time ?
	 * @return date string formatted with outDatePattern pattern
	 */
	public String getDate(String inDate, String inDatePattern, String outDatePattern, boolean keepCurrentTime) {
		String result = inDate;

		Date date = null;
		try {
			SimpleDateFormat inSimpleDateFormat = new SimpleDateFormat(inDatePattern, locale);
			date = inSimpleDateFormat.parse(inDate);
		} catch (Exception e) {
			debug = true;
			System.err.println(String.format(this.getResourceString(DateTimeApplet.RESOURCE_MESSAGE_ERROR_GET_DATE_WITH_4_ARGS_STRING_KEY), result));
			e.printStackTrace();
		}

		result = getDate(date, outDatePattern, keepCurrentTime, inDate);
		return result;
	}

	/**
	 * 
	 * @param date
	 *            to be formatted
	 * @param datePattern
	 *            pattern for date
	 * @param keepCurrentTime
	 *            do we have to keep current time ?
	 * @param defaultResult
	 *            default value in case of error
	 * @return formatted date with datePattern pattern
	 */
	private String getDate(Date date, String datePattern, boolean keepCurrentTime, String defaultResult) {
		String result = defaultResult;

		if (date != null) {
			Date dateX = date;
			result = dateX.toString();
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern, locale);
				if (keepCurrentTime) {
					// Used to store only date not hour
					Calendar newCal = Calendar.getInstance();
					newCal.setTime(date);

					// Used to store current hour and date from newDate
					Calendar cal = Calendar.getInstance();
					cal.set(newCal.get(Calendar.YEAR), newCal.get(Calendar.MONTH), newCal.get(Calendar.DAY_OF_MONTH));
					dateX = cal.getTime();
				}
				result = simpleDateFormat.format(dateX);
			} catch (Exception e) {
				debug = true;
				System.err.println(String.format(this.getResourceString(DateTimeApplet.RESOURCE_MESSAGE_ERROR_GET_DATE_WITH_4_ARGS_DATE_KEY), dateX));
				e.printStackTrace();
			}
		}
		return result;
	}
}
