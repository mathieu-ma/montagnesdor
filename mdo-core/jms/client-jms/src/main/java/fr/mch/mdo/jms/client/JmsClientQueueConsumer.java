package fr.mch.mdo.jms.client;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JmsClientQueueConsumer extends JmsClientQueue implements MessageListener
{
	public JmsClientQueueConsumer() {
	}

	public JmsClientQueueConsumer(MessageListener listener) {
		
		try {
			if (connection != null) {
				// Step 5. Create a JMS Session
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	
				// Step 9. Create a JMS Message Consumer
				MessageConsumer messageConsumer = session.createConsumer(queue);
				messageConsumer.setMessageListener(listener);
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {

		JmsClientQueueConsumer client = new JmsClientQueueConsumer();
		Connection connection = client.getConnection();
		try {

			// Step 5. Create a JMS Session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Step 9. Create a JMS Message Consumer
			MessageConsumer messageConsumer = session.createConsumer(client.getQueue());
			messageConsumer.setMessageListener(client);

			// Step 10. Start the Connection
			connection.start();

			// // Step 11. Receive the message
			// TextMessage messageReceived = (TextMessage)
			// messageConsumer.receive(5000);
			//
			// System.out.println("Received message: " +
			// messageReceived.getText());

			System.out.println("Waiting for messages ...");
			try {
				Thread.sleep(5000);
			} catch (java.lang.InterruptedException ie) {
				ie.printStackTrace(System.err);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}

	}

	@Override
	public void onMessage(Message message) {
		try {
			// Step 11. Receive the message
			System.out.println("Message Type: " + message.getJMSType());
			if (message instanceof TextMessage) {
				System.out.println("Received message: " + ((TextMessage) message).getText());
				message.acknowledge();
			}
		} catch (JMSException e) {
//			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
	}
}
