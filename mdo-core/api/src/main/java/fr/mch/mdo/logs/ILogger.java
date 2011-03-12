package fr.mch.mdo.logs;

import fr.mch.mdo.i18n.IMessageQuery;


public interface ILogger
{
    String ERROR_MESSAGE_LOADING_CONFIGURATION_FILE = "Could not load configuration file.";

    ILogger getLogger();

    ILogger getLogger(String className);

    IMessageQuery getLoggerMessage();
    
    void setLoggerMessage(IMessageQuery loggerMessage);
    
    void debug(String query, Throwable exception);

    void debug(String query);

    void info(String query, Throwable exception);

    void info(String query);

    void warn(String query, Throwable exception);

    void warn(String query);

    void error(String query, Throwable exception);

    void error(String query);

    void fatal(String query, Throwable exception);

    void fatal(String query);

    void debug(String query, Object[] params, Throwable exception);

    void debug(String query, Object[] params);

    void info(String query, Object[] params, Throwable exception);

    void info(String query, Object[] params);

    void warn(String query, Object[] params, Throwable exception);

    void warn(String query, Object[] params);

    void error(String query, Object[] params, Throwable exception);

    void error(String query, Object[] params);

    void fatal(String query, Object[] params, Throwable exception);

    void fatal(String query, Object[] params);
}