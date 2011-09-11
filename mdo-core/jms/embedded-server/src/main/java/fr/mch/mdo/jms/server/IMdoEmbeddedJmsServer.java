package fr.mch.mdo.jms.server;

import fr.mch.mdo.restaurant.exception.MdoException;

public interface IMdoEmbeddedJmsServer
{
	String JMS_USER_NAME_KEY = "name";
	String JMS_USER_PASSWORD_KEY = "password";
	String JMS_USER_ROLE_KEY = "role";
	
	void startServer() throws MdoException;
	
	void stopServer() throws MdoException;
}
