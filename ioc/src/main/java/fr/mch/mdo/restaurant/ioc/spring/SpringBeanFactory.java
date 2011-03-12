package fr.mch.mdo.restaurant.ioc.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerService;
import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.IocBeanName;
import fr.mch.mdo.restaurant.exception.MdoTechnicalException;
import fr.mch.mdo.restaurant.ioc.IBeanFactory;

public class SpringBeanFactory implements IBeanFactory 
{
	protected BeanFactory factory;
	private ILogger logger;

	public SpringBeanFactory() throws MdoTechnicalException {
		this.init();
	}
	
	public SpringBeanFactory(String[] files) throws MdoTechnicalException {
		this.init(files);
	}

	protected void init() throws MdoTechnicalException {
		// All files names are separated by comma
		// Each file path is relative to the application context
		String[] files = Constants.SPRING_CONFIGURATION_FILE.split(",");
		init(files);
	}

	protected void init(String[] files) throws MdoTechnicalException {
		try {
			// Get the bean this
			factory = new ClassPathXmlApplicationContext(files);
			logger = this.getLogger(SpringBeanFactory.class.getName());
		} catch (Exception e) {
			if (logger != null) {
				logger.fatal("message.error.ioc.init.spring", e);
			}
			throw new MdoTechnicalException("message.error.ioc.init.spring", e);
		}
	}

	@Override
	public Object getBean(IocBeanName beanName) {
		return factory.getBean(beanName.getValue());
	}

	public ILogger getLogger(String className) {
		return ((ILoggerService) this.getBean(IocBeanName.BEAN_LOG_NAME)).getLogger(className);
	}
}
