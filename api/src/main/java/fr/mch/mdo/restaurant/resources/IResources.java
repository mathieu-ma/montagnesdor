package fr.mch.mdo.restaurant.resources;


public interface IResources
{
    // TODO Maybe we have to create a resource manager instead of using Java constants
    // The constants must be in a properties files
    
    String LOG4J_CONFIGURATION_FILE = "logs/log4j/log4j.properties";
    String LOG_RESOURCE_BUNDLE_MESSAGES_FILE = "fr.mch.mdo.restaurant.resources.i18n.ILoggerMessages";
    String EXCEPTION_RESOURCE_BUNDLE_MESSAGES_FILE = "fr.mch.mdo.restaurant.resources.i18n.ILoggerMessages";

    /**START DATABASE */
    String MDO_DATABASE_STRUCTURE = "montagnesdorStructure.sql";
    
    /**
     * START HIBERNATE
     */
    String HIBERNATE_CONFIGURATION_FILE = "dao/hibernate/hibernate.cfg.xml";
    /**
     * END HIBERNATE
     */
    
    /**
     * START JAAS
     */
    String JAAS_LOGIN_CONFIGURATION_FILE = "jaas/montagnesdorjaas.login";
    String JAAS_POLICY_FILE = "jaas/montagnesdorjaas.policy";
    /**
     * END JAAS
     */
    

}
