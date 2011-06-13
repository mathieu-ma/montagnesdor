package fr.mch.mdo.restaurant.exception;

public abstract class MdoException extends Exception
{
	/**
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	public MdoException() {
		super();
	}

	public MdoException(String message) {
		super(message);
	}

	public MdoException(Throwable cause) {
		super(cause);
	}

	public MdoException(String message, Throwable cause) {
		super(message, cause);
	}
}
