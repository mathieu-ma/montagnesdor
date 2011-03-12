package fr.mch.mdo.restaurant.services;

import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.ioc.IBeanFactory;

public abstract class MdoAbstractBeanFactory implements IBeanFactory 
{
	protected MdoAbstractBeanFactory() {
		try {
			init();
		} catch (MdoException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * This method initializes all required configuration
	 */
	protected abstract void init() throws MdoException;
}
