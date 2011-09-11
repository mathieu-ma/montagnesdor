package fr.mch.mdo.jms.client;

import java.util.Observable;

import javax.jms.Message;

/**
 * This class represents view part.
 * 
 * @author mathieu
 */
public class PrinterObservableMessage extends Observable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Message received by JMS, this class has to update the View class with this data. */
	private Message message;

	
	/**
	 * Notify the observers calling the update method.
	 */
	private void notifyChange() {
		setChanged();
		notifyObservers(this.message);
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(Message message) {
		// Check equality with != operator and not !equals method
		if (this.message != message) {
			this.message = message;
			notifyChange();
		}
	}
	
	/**
	 * @return the message
	 */
	public Message getMessage() {
		return message;
	}

}
