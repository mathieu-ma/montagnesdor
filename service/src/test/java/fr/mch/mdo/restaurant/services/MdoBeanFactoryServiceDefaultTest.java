package fr.mch.mdo.restaurant.services;

import java.lang.reflect.InvocationTargetException;
import java.util.TreeMap;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.IocBeanName;
import fr.mch.mdo.restaurant.exception.MdoTechnicalException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.restaurant.services.util.UtilsImpl;
import fr.mch.mdo.test.MdoTestCase;

public class MdoBeanFactoryServiceDefaultTest extends MdoTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public MdoBeanFactoryServiceDefaultTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(MdoBeanFactoryServiceDefaultTest.class);
	}

	public void testInit() {
		MdoBeanFactoryServiceDefault beanFactory = new MdoBeanFactoryServiceDefault();
		// Invoke init method with factory not null
		try {
			invokeInit(beanFactory);
		} catch (InvocationTargetException e) {
			fail("Could not invoke the init method " + e);
		}
		// Set factory to null
		super.setField(beanFactory, "factory", null);
		// Invoke init method with factory null
		try {
			invokeInit(beanFactory);
		} catch (InvocationTargetException e) {
			fail("Could not invoke the init method " + e);
		}
		// Set factory to null
		TreeMap<Long, String> value = new TreeMap<Long, String>();
		value.put(Long.MIN_VALUE, "value");
		super.setField(beanFactory, "factory", value);
		try {
			// Invoke init method with factory that throw Exception
			invokeInit(beanFactory);
			fail("Could not process this instruction");
		} catch (Exception e) {
			assertTrue("Check the instance of exception", e instanceof InvocationTargetException);
			assertTrue("Check the instance cause of exception", e.getCause() instanceof MdoTechnicalException);
			MdoTechnicalException mdoException = (MdoTechnicalException) e.getCause();
			assertEquals("Check the message exception", "mdo.technical.generic.exception", mdoException.getMessage());
			assertEquals("Check the localized message exception", "Unexpected exception occurs", mdoException.getLocalizedMessage());
			assertTrue("Check the instance of MdoException cause exception", mdoException.getCause() instanceof ClassCastException);
		}
	}

	private void invokeInit(MdoBeanFactoryServiceDefault beanFactory) throws InvocationTargetException {
		super.invokeInstanceMethod(beanFactory, "init", null, null);
	}

	public void testGetBean() {
		MdoBeanFactoryServiceDefault beanFactory = new MdoBeanFactoryServiceDefault();
		assertNotNull("Check non null bean " + IocBeanName.BEAN_LOG_NAME, beanFactory.getBean(IocBeanName.BEAN_LOG_NAME));
		assertTrue("Check instance of bean " + IocBeanName.BEAN_LOG_NAME, beanFactory.getBean(IocBeanName.BEAN_LOG_NAME) instanceof LoggerServiceImpl);
		assertNotNull("Check non null bean " + IocBeanName.BEAN_UTILS_NAME, beanFactory.getBean(IocBeanName.BEAN_UTILS_NAME));
		assertTrue("Check instance of bean " + IocBeanName.BEAN_UTILS_NAME, beanFactory.getBean(IocBeanName.BEAN_UTILS_NAME) instanceof UtilsImpl);
		super.setField(beanFactory, "factory", null);
		assertNotNull("Check non null bean " + IocBeanName.BEAN_LOG_NAME, beanFactory.getBean(IocBeanName.BEAN_LOG_NAME));
		assertTrue("Check instance of bean " + IocBeanName.BEAN_LOG_NAME, beanFactory.getBean(IocBeanName.BEAN_LOG_NAME) instanceof LoggerServiceImpl);
	}

	public void testGetLoggerService() {
		MdoBeanFactoryServiceDefault beanFactory = new MdoBeanFactoryServiceDefault();
		assertNotNull("Check non null bean IUtilLanguage", beanFactory.getLoggerService());
		assertTrue("Check non null bean instance of LoggerServiceImpl", beanFactory.getLoggerService() instanceof LoggerServiceImpl);
	}

	public void testGetUtilLanguage() {
		MdoBeanFactoryServiceDefault beanFactory = new MdoBeanFactoryServiceDefault();
		assertNotNull("Check non null bean IUtilLanguage", beanFactory.getUtils());
		assertTrue("Check non null bean instance of UtilLanguageImpl", beanFactory.getUtils() instanceof UtilsImpl);
	}
}
