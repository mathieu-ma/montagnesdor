package fr.mch.mdo.jms.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * This class represents view part.
 * 
 * @author mathieu
 */
public class PrinterController
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PrinterModel model;
	private PrinterView view;

	public PrinterController(PrinterModel model, PrinterView view) {
		this.model = model;
		this.view = view;
		this.model.addPrinterObserver(new PrinterMessageObserver());
		this.view.addReloadJmsButtonListener(new ReloadJmsButtonListener());
		this.view.addReloadPrinterButtonListener(new ReloadPrinterButtonListener());
		this.view.addPrintButtonListener(new PrintButtonListener());
		this.view.addStopButtonListener(new StopButtonListener());
	}

	public void close() {
		this.model.close();
	}
	
	/**
	 * This class is used to update View when Model is modified for the message field.
	 * 
	 * @author mathieu
	 * 
	 */
	class PrinterMessageObserver implements Observer
	{
		public void update(Observable observable, Object arg) {
			view.getPrint().setEnabled(false);
			view.setMessage(model.getMessage());
			view.getPrint().setEnabled(true);
		}
	}
	/**
	 * This class is used to perform reload JMS when user clicks on "Reload JMS connection" button in View part. 
	 * 
	 * @author mathieu
	 * 
	 */
	class ReloadJmsButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			model.reloadJms();
		}
	}
	/**
	 * This class is used to perform reload driver when user clicks on "Reload Printer Driver" button in View part. 
	 * 
	 * @author mathieu
	 * 
	 */
	class ReloadPrinterButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			model.reloadPrinter();
		}
	}
	/**
	 * This class is used to perform printing when user clicks on "Print" button in View part. 
	 * 
	 * @author mathieu
	 * 
	 */
	class PrintButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			model.print();
		}
	}
	/**
	 * This class is used to perform stop printing when user clicks on "Stop" button in View part. 
	 * 
	 * @author mathieu
	 * 
	 */
	class StopButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			model.stop();
		}
	}
}
