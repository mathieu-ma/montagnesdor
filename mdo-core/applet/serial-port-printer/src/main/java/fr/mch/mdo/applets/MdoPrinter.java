package fr.mch.mdo.applets;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.swing.JFrame;
import javax.swing.JLabel;

import fr.mch.mdo.applets.PrinterApplet;

public class MdoPrinter extends PrinterApplet implements MessageListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JmsClientPrinterQueue jmsClient = new JmsClientPrinterQueue();

	private JLabel label;

	@Override
	public void init() {
		super.init();

		label = new JLabel("Hello World");
		super.getContentPane().add(label);

		// Step 5. Create a JMS Session
		Session session;
		try {
			session = jmsClient.getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
			// Step 9. Create a JMS Message Consumer
			MessageConsumer messageConsumer = session.createConsumer(jmsClient.getQueue());
			messageConsumer.setMessageListener(this);
			// Step 10. Start the Connection
			jmsClient.getConnection().start();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		MdoPrinter printer = new MdoPrinter();

		System.loadLibrary("rxtxSerial");
		
		printer.init();

		// //Vider le buffer de l'applet
		// printer.resetDataBuffer();
		// //Entete
		// printer.addData2("document.getElementById(");
		// printer.print();

		JFrame frame = new JFrame("Frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(printer.getContentPane()); // set panel on
																// frame
		frame.setSize(400, 600); // Set the size of the frame
		//frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void onMessage(Message message) {
		try {
			// Step 11. Receive the message
			System.out.println("Message Type: " + message.getJMSType());
			if (message instanceof TextMessage) {
				String text = ((TextMessage) message).getText();
				System.out.println("Received message: " + text);
				label.setText(((TextMessage) message).getText());
				 //Vider le buffer de l'applet
				 super.resetDataBuffer();
				 //Entete
				 super.addData2(text);
				 super.print();
				message.acknowledge();
			}
		} catch (JMSException e) {
			// // TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
	}
}
