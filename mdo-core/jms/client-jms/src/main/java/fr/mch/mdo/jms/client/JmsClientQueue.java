package fr.mch.mdo.jms.client;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Queue;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.core.remoting.impl.netty.TransportConstants;
import org.hornetq.jms.client.HornetQConnectionFactory;

public class JmsClientQueue
{
	protected Connection connection;
	protected Queue queue;
	
	public JmsClientQueue() {
		Map<String, Object> connectionParams = new HashMap<String, Object>();
		connectionParams.put(TransportConstants.PORT_PROP_NAME, "5445");
		connectionParams.put(TransportConstants.HOST_PROP_NAME, "localhost");
		TransportConfiguration transportConfiguration = new TransportConfiguration(NettyConnectorFactory.class.getName(), connectionParams);
		//
		HornetQConnectionFactory connectionFactory = HornetQJMSClient.createConnectionFactoryWithoutHA(JMSFactoryType.CF, transportConfiguration);

		try {
			connection = connectionFactory.createConnection("printer", "printer");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (connection != null) {
			System.out.println("JMS Connection succeed.");
		}
		// Step 9. create the JMS management queue.
		// It is a "special" queue and it is not looked up from JNDI but
		// constructed directly
		// see hornetq-jms.xml configuration file in server side
		queue = HornetQJMSClient.createQueue("printerQueue");
	}

	/**
	 * @param connection the connection to set
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * @param queue the queue to set
	 */
	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	/**
	 * @return the queue
	 */
	public Queue getQueue() {
		return queue;
	}
	
	public void close() {
		try {
			connection.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
