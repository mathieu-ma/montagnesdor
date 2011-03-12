package fr.mch.mdo.restaurant;

import java.util.Locale;

/**
 * @author user
 * 
 *         To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public interface Constants
{
    String DEFAULT_LOCALE_LANGUAGE = Locale.FRANCE.getLanguage();
    String DEFAULT_LOCALE_DISPLAY_LANGUAGE = Locale.FRANCE.getDisplayLanguage(Locale.FRANCE);

    String DEFAULT_LOCALE_COUNTRY = Locale.FRANCE.getCountry();
    String DEFAULT_LOCALE_DISPLAY_COUNTRY = Locale.FRANCE.getDisplayCountry(Locale.FRANCE);

    /*
     * START WEB
     */
    String DEFAULT_PICTURE_NAME = "default.jpg";
    String DEFAULT_PICTURE_NAME_EXTENSION = "jpg";

    public static final String USER_SESSION_PREFIX_KEY = "userSession";

    public static final int SWITCH_SCHEME_NONE = 0; // no switch scheme : scheme in == scheme out
    public static final int SWITCH_SCHEME_ONLY_HTTP = 1; // only http scheme : https scheme in redirect to http scheme out
    public static final int SWITCH_SCHEME_ONLY_HTTPS = 2; // only https scheme : http scheme in redirect to https scheme out
    public static final int SWITCH_SCHEME_HTTPS_ENTRY_POINT_ONLY = 21; // switch https scheme in to http scheme out

    public static final String SCHEME_HTTP_KEY = "schemeHTTP";
    public static final String PORT_HTTP_KEY = "portHTTP";
    public static final String SCHEME_HTTPS_KEY = "schemeHTTPS";
    public static final String PORT_HTTPS_KEY = "portHTTPS";
    public static final String DATE_LOCALE_CONVERTER_PATTERN = "dateLocaleConverterPattern";
    public static final String DEFAULT_ENTRY_URI_KEY = "defaultEntryURI";

    public static final String SWITCH_SCHEME_ACTION_URI_LIST_KEY = "switchSchemeActionURIList";

    /** CUD == CREATE UPDATE DELETE */
    public static final String ACTION_RESULT_AFTER_CUD = "acud";
    public static final String ACTION_RESULT_AFTER_SUCCESS_FORM_LIST = "asformlist";
    public static final String ACTION_RESULT_LIST = "list";
    public static final String ACTION_RESULT_FORM = "form";
    
    public static final String ACTION_RESULT_AFTER_CUD_PREFIX_TABLE = "acudprefixtable";
    public static final String ACTION_RESULT_AFTER_CUD_LIST_TYPE = "acudlisttype";
    public static final String ACTION_RESULT_AFTER_CUD_RESTAURANT = "acudrestaurant";
    
    /**
     * END WEB
     */

    /**
     * START JASS
     */
    String JAAS_LOGIN_MODULE_CLASS_NAME = "MdoLoginModuleDataBase";
    /**
     * END JAAS
     */


    /**
     * START SPRING
     */
    String SPRING_BEAN_FACTORY_CLASS_NAME = "fr.mch.mdo.restaurant.ioc.spring.SpringBeanFactory";
    String SPRING_CONFIGURATION_FILE = "fr/mch/mdo/restaurant/resources/ioc/spring/spring-mdo.xml";
    /**
     * END SPRING
     */

    /**
     * START RESTAURANT FUNCTONNAL
     */
    int HALF_ROUND = 1;
    int TENTH_ROUND = 2;

    /**
     * END RESTAURANT FUNCTONNAL
     */

    /**
     * START HIBERNATE
     */
    String HQL_USER_AUTHENTICATION_SELECT_BY_LOGIN = "UserAuthentication.SelectByLogin";
    String HQL_MDO_TABLE_AS_ENUM_SELECT_ALL_TYPES = "MdoTableAsEnum.SelectAllTypes";
    /**
     * END HIBERNATE
     */
}
