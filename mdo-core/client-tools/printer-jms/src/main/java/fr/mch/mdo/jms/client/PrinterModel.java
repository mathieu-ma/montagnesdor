package fr.mch.mdo.jms.client;

import java.util.Observer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import fr.mch.mdo.client.printer.AbstractPrinter;
import fr.mch.mdo.client.printer.IPrinter;

/**
 * This class represents view part.
 * 
 * @author mathieu
 */
public class PrinterModel implements MessageListener
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Message received by JMS, this class has to update the View class with
	 * this data.
	 */
	private PrinterObservableMessage observableMessage = new PrinterObservableMessage();

	// Model/Data part
	private JmsClientQueue jmsClientConsumer;
	// Controller Part
	private IPrinter printer = new AbstractPrinter() {
		@Override
		public String getParameter(String key, String defaultValue) {
			return defaultValue;
		}
	};

	public PrinterModel() {
		jmsClientConsumer = new JmsClientQueueConsumer(this);
		try {
			if (jmsClientConsumer.getConnection() != null) {
				jmsClientConsumer.getConnection().start();
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printer.init();
	}
	

	public void addPrinterObserver(Observer controller) {
		observableMessage.addObserver(controller);
	}
	
	public String getMessage() {
		return observableMessage.getMessage().toString();
	}

	@Override
	public void onMessage(Message message) {
		try {
			message.acknowledge();
			// Step 11. Receive the message
			System.out.println("Message Type: " + message.getJMSType());
			this.observableMessage.setMessage(message);
			print(observableMessage.getMessage());
		} catch (JMSException e) {
			// // TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
	}
	
	public void print(Message message) {
		if (message != null) {
			try {
				// Step 11. Receive the message
				System.out.println("Print Message: " + message.getJMSType());
				this.observableMessage.setMessage(message);
				if (message instanceof TextMessage) {
					String text = ((TextMessage) message).getText();
					System.out.println("Received message: " + text);
					// Vider le buffer de l'applet
					printer.resetDataBuffer();
					// Entete
					printer.addData2(text);
					printer.print();
					message.acknowledge();
				}
			} catch (JMSException e) {
				// // TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	
			}
		}
	}

	public void print() {
		print(observableMessage.getMessage());
	}


	public void reloadPrinter() {
		printer.reload();
	}

	public void reloadJms() {
		jmsClientConsumer = new JmsClientQueueConsumer(this);
	}

	public void stop() {
		printer.stop();
	}
	
	public void close() {
		printer.close();
		jmsClientConsumer.close();
	}
}
