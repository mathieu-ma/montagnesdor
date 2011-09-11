package fr.mch.mdo.jms.client;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JmsClientQueueProducer extends JmsClientQueue
{
	private MessageProducer producer;
	private Session session;
	public JmsClientQueueProducer() {
		// Step 5. Create a JMS Session
		try {
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// Step 6. Create a JMS Message Producer
			producer = session.createProducer(queue);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {

		JmsClientQueueProducer client = new JmsClientQueueProducer();
		Connection connection = client.getConnection();
		try {

			// Step 5. Create a JMS Session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Step 6. Create a JMS Message Producer
			MessageProducer producer = session.createProducer(client.getQueue());

			// Step 7. Create a Text Message
			TextMessage message = session.createTextMessage("This is a text message 1235");

			System.out.println("Sent message: " + message.getText());

			// Step 8. Send the Message
			producer.send(message);


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}

	}
}
