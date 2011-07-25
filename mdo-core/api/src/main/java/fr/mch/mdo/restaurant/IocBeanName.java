package fr.mch.mdo.restaurant;

public enum IocBeanName 
{
    BEAN_USER_AUTHENTICATION_GLOBAL_ADMIN_NAME("UserAuthenticationGlobalAdmin"),
    SPRING_BEAN_USER_GLOBAL_ADMIN_NAME("UserGlobalAdmin"),

    BEAN_I18N_NAME("I18Message"),

    BEAN_LOG_NAME("LoggerService"),
    BEAN_UTILS_NAME("UtilsImpl"),

    BEAN_USER_CONTEXT_GLOBAL_ADMIN_NAME("userContext"),
    BEAN_AUTHENTICATION_DAO_NAME("AuthenticationDao"),
    
    BEAN_AUTHORIZATION_JAAS_NAME("MdoAuthorizationService"),
    BEAN_AUTHENTICATION_JAAS_NAME("MdoAuthenticationService"),

    BEAN_LOCALES_MANAGER_NAME("LocalesManager"),
    BEAN_MDO_TABLE_AS_ENUMS_MANAGER_NAME("MdoTableAsEnumsManager"),
    BEAN_RESTAURANTS_MANAGER_NAME("RestaurantsManager"),
    BEAN_USER_ROLES_MANAGER_NAME("UserRolesManager"),
    BEAN_USER_AUTHENTICATIONS_MANAGER_NAME("UserAuthenticationsManager"),
    BEAN_USERS_MANAGER_NAME("UsersManager"),
    BEAN_PRODUCTS_MANAGER_NAME("ProductsManager"),
    BEAN_CATEGORIES_MANAGER_NAME("CategoriesManager"),
    BEAN_PRODUCT_PARTS_MANAGER_NAME("ProductPartsManager"),
    BEAN_VALUE_ADDED_TAXES_MANAGER_NAME("ValueAddedTaxesManager"),
    BEAN_PRODUCT_SPECIAL_CODES_MANAGER_NAME("ProductSpecialCodesManager"),
    BEAN_TABLE_TYPES_MANAGER_NAME("TableTypesManager"),
    BEAN_PRINTING_INFORMATIONS_MANAGER_NAME("PrintingInformationsManager"),
    
    // Restaurant Application Part
    BEAN_TABLE_ORDERS_MANAGER_NAME("DinnerTablesManager")
    
    ;

    private final String value;

    IocBeanName(String value) {
    	this.value = value;
    }
    
    public String getValue() {
    	return value;
    }
}
