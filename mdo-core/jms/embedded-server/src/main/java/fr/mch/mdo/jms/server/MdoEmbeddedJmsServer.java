package fr.mch.mdo.jms.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.Date;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.hornetq.jms.server.embedded.EmbeddedJMS;
import org.hornetq.spi.core.security.HornetQSecurityManagerImpl;

import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.exception.MdoTechnicalException;
import fr.mch.mdo.restaurant.resources.IResources;

public class MdoEmbeddedJmsServer extends EmbeddedJMS implements IMdoEmbeddedJmsServer
{
	public MdoEmbeddedJmsServer() {
		super.configResourcePath = IResources.JMS_SERVER_HORNETQ_CONFIGURATION_FILE;
		super.jmsConfigResourcePath = IResources.JMS_SERVER_HORNETQ_JMS_FILE;
		initSecurityManager();
	}

	private void initSecurityManager() {
		super.securityManager = new HornetQSecurityManagerImpl();
		Properties properties = reload(IResources.JMS_HORNETQ_USER_FILE);
		String username = properties.getProperty(IMdoEmbeddedJmsServer.JMS_USER_NAME_KEY);
		String password = properties.getProperty(IMdoEmbeddedJmsServer.JMS_USER_PASSWORD_KEY);
		securityManager.addUser(username, password);
		String role = properties.getProperty(IMdoEmbeddedJmsServer.JMS_USER_ROLE_KEY);
		securityManager.addRole(username, role);
	}

	private Properties reload(final String configFile) {
		Properties result = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = IResources.class.getResourceAsStream(configFile);
			result.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		MdoEmbeddedJmsServer server = new MdoEmbeddedJmsServer();
		server.start();

		ServerSocket dummyServer = new ServerSocket(9999);
		while (true) {
			ConnectionFactory cf = (ConnectionFactory) server.lookup("/ConnectionFactory");
			Queue queue = (Queue) server.lookup("/queue/printerQueue");

			// Step 10. Send and receive a message using JMS API
			Connection connection = null;
			try {
				connection = cf.createConnection("printer", "printer");
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				MessageProducer producer = session.createProducer(queue);
				TextMessage message = session.createTextMessage("Hello sent at " + new Date());
				System.out.println("Sending message: " + message.getText());
				producer.send(message);
				MessageConsumer messageConsumer = session.createConsumer(queue);
				// messageConsumer.setMessageListener(listener);
				connection.start();
				TextMessage messageReceived = (TextMessage) messageConsumer.receive(1000);
				System.out.println("Received message:" + messageReceived.getText());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
			dummyServer.accept();
		}
	}

	@Override
	public void startServer() throws MdoException {
		try {
			super.start();
		} catch (Exception e) {
			throw new MdoTechnicalException(e);
		}
	}

	@Override
	public void stopServer() throws MdoException {
		try {
			super.stop();
		} catch (Exception e) {
			throw new MdoTechnicalException(e);
		}
	}
}
