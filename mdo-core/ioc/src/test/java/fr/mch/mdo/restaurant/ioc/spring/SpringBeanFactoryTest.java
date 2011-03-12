package fr.mch.mdo.restaurant.ioc.spring;

import fr.mch.mdo.restaurant.exception.MdoTechnicalException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SpringBeanFactoryTest extends TestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public SpringBeanFactoryTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(SpringBeanFactoryTest.class);
	}

	public void testInit() {
		SpringBeanFactory factory;
		try {
			factory = new SpringBeanFactory();
			assertNotNull("The Spring bean factory must not null", factory);
		} catch (MdoTechnicalException e) {
			fail("Could not be there.");
		}
	}

	public void testGetBean() {
	}
}
