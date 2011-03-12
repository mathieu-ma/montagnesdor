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
    public final static String DEFAULT_LOCALE_LANGUAGE = Locale.FRANCE.getLanguage();
    public final static String DEFAULT_LOCALE_DISPLAY_LANGUAGE = Locale.FRANCE.getDisplayLanguage(Locale.FRANCE);

    public final static String DEFAULT_LOCALE_COUNTRY = Locale.FRANCE.getCountry();
    public final static String DEFAULT_LOCALE_DISPLAY_COUNTRY = Locale.FRANCE.getDisplayCountry(Locale.FRANCE);

    /*
     * START WEB
     */
    public final static String DEFAULT_PICTURE_NAME = "default.jpg";
    public final static String DEFAULT_PICTURE_NAME_EXTENSION = "jpg";

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

    public static final String ACTION_LIST = "list";
    public static final String ACTION_FORM = "form";
    /**
     * END WEB
     */

    /**
     * START JASS
     */
    public final static String JAAS_LOGIN_MODULE_CLASS_NAME = "MdoLoginModuleDataBase";

    /**
     * END JAAS
     */

    /**
     * START HIBERNATE
     */
    public final static String HIBERNATE_CONFIGURATION_FILE = "dao/hibernate/hibernate.cfg.xml";

    /**
     * END HIBERNATE
     */

    /**
     * START LOG
     */
    public final static String LOG4J_CONFIGURATION_FILE = "log/log4j/log4j.properties";
    public final static String LOG_RESOURCE_BUNDLE_MESSAGES_FILE = "fr.mch.mdo.restaurant.resources.log.LogMessages";

    /**
     * END LOG
     */

    /**
     * START SPRING
     */
    public final static String SPRING_BEAN_FACTORY_CLASS_NAME = "fr.mch.mdo.restaurant.ioc.spring.SpringBeanFactory";
    public final static String SPRING_CONFIGURATION_FILE = "fr/mch/mdo/restaurant/resources/ioc/spring/spring-mdo.xml";

    public final static String SPRING_BEAN_USER_AUTHENTICATION_GLOBAL_ADMIN_NAME = "UserAuthenticationGlobalAdmin";
    public final static String SPRING_BEAN_USER_GLOBAL_ADMIN_NAME = "UserGlobalAdmin";
    
    public final static String BEAN_LOG_NAME = "LoggerService";

    public final static String BEAN_AUTHORIZATION_JAAS_NAME = "MdoAuthorizationService";
    public final static String BEAN_AUTHENTICATION_JAAS_NAME = "MdoAuthenticationService";

    public final static String BEAN_AUTHENTICATION_NAME = "AuthenticationManager";

    public final static String BEAN_LOCALES_MANAGER_NAME = "LocalesManager";
    public final static String BEAN_RESTAURANTS_MANAGER_NAME = "RestaurantsManager";
    public final static String BEAN_USER_ROLES_MANAGER_NAME = "UserRolesManager";
    public final static String BEAN_USER_AUTHENTICATIONS_MANAGER_NAME = "UserAuthenticationsManager";
    public final static String BEAN_USERS_MANAGER_NAME = "UsersManager";
    public final static String BEAN_PRODUCTS_MANAGER_NAME = "ProductsManager";
    public final static String BEAN_CATEGORIES_MANAGER_NAME = "CategoriesManager";
    public final static String BEAN_PRODUCT_PARTS_MANAGER_NAME = "ProductPartsManager";
    public final static String BEAN_VALUE_ADDED_TAXES_MANAGER_NAME = "ValueAddedTaxesManager";
    public final static String BEAN_PRODUCT_SPECIAL_CODES_MANAGER_NAME = "ProductSpecialCodesManager";
    public final static String BEAN_TYPETABLES_MANAGER_NAME = "TypeTablesManager";
    
    public final static String BEAN_TABLE_ORDERS_MANAGER_NAME = "TablesOrdersManager";
    public final static String BEAN_USER_MANAGER_NAME = "UserManager";
    /**
     * END SPRING
     */

    /**
     * START RESTAURANT FUNCTONNAL
     */
    public final static int HALF_ROUND = 1;
    public final static int TENTH_ROUND = 2;

    /**
     * END RESTAURANT FUNCTONNAL
     */

}
