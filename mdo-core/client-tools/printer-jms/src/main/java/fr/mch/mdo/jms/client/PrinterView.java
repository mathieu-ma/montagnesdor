package fr.mch.mdo.jms.client;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class represents view part.
 *  
 * @author mathieu
 */
public class PrinterView extends JPanel
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	private JLabel message = new JLabel("Message");

	private JButton reloadPrinter = new JButton("Reload Printer Driver");
	private JButton reloadJms = new JButton("Reload JMS connection");
	private JButton print = new JButton("Print");
	private JButton stop = new JButton("Stop");

	public PrinterView() {
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		JPanel buttons = new JPanel();
		buttons.add(reloadJms, BorderLayout.NORTH);
		buttons.add(reloadPrinter, BorderLayout.NORTH);
		buttons.add(print, BorderLayout.NORTH);
		buttons.add(stop, BorderLayout.NORTH);
		this.add(buttons, BorderLayout.NORTH);
		this.add(message, BorderLayout.CENTER);
	}
	
	/**
	 * @param message the message to set
	 */
	public void setMessage(JLabel message) {
		this.message = message;
	}

	public void setMessage(String message) {
		this.message.setText(message);
	}
	
	/**
	 * @return the message
	 */
	public JLabel getMessage() {
		return message;
	}

	/**
	 * @param print the print to set
	 */
	public void setPrint(JButton print) {
		this.print = print;
	}

	/**
	 * @return the print
	 */
	public JButton getPrint() {
		return print;
	}
	
	public void addReloadJmsButtonListener(ActionListener listener) {
		this.reloadJms.addActionListener(listener);
	}
	public void addReloadPrinterButtonListener(ActionListener listener) {
		this.reloadPrinter.addActionListener(listener);
	}
	public void addPrintButtonListener(ActionListener listener) {
		this.print.addActionListener(listener);
	}
	public void addStopButtonListener(ActionListener listener) {
		this.stop.addActionListener(listener);
	}

}
