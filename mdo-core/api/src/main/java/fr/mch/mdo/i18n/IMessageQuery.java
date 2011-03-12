package fr.mch.mdo.i18n;


public interface IMessageQuery
{
    /**
     * Get the message with a key from properties file
     * @param query the key in the properties file
     * @return value of the query from properties file
     */
    String getMessage(String query);

    /**
     * Get the message with a key from properties file
     * @param query the key in the properties file
     * @param params parameters of query
     * @return value of the query from properties file
     */
    String getMessage(String query, Object[] params);
}
