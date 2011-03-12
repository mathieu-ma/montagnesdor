package fr.mch.mdo.restaurant.services;

import java.util.Map;

import fr.mch.mdo.logs.ILoggerService;
import fr.mch.mdo.restaurant.IocBeanName;
import fr.mch.mdo.restaurant.exception.MdoTechnicalException;
import fr.mch.mdo.restaurant.ioc.IBeanFactoryService;
import fr.mch.mdo.restaurant.services.util.IUtils;

public final class MdoBeanFactoryServiceDefaultForceIllegalAccessExceptionForTesting extends MdoAbstractBeanFactory implements IBeanFactoryService 
{
	protected Map<String, Object> factory;

	private MdoBeanFactoryServiceDefaultForceIllegalAccessExceptionForTesting() {

	}

	protected void init() throws MdoTechnicalException {
	}

	@Override
	public Object getBean(IocBeanName beanName) {
		return null;
	}

	@Override
	public ILoggerService getLoggerService() {
		return null;
	}

	@Override
	public IUtils getUtils() {
		return null;
	}
}
