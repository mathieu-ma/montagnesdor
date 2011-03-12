/*
 * Created on 13 juin 2004
 *
 * 
 * 
 */
package fr.mch.mdo.restaurant.dao.hibernate;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.test.MdoTestCase;

/**
 * @author Mathieu MA sous conrad
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public class DefaultSessionFactoryTest extends MdoTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultSessionFactoryTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultSessionFactoryTest.class);
	}

	public void testCurrentSession() {

	}

	public void testCloseSession() {

	}

	public void testGetSessionFactory() {
		try {
			String configuratiionFile = "dao/hibernate/hibernate-configuration.cfg.xml";
			String mappingsFile = "dao/hibernate/hibernate-mappings.cfg.xml";
			assertNotNull("Check Hibernate Session Factory", ((DefaultSessionFactory) DefaultSessionFactory.getInstance()).getSessionFactory(configuratiionFile, mappingsFile));
			assertNotNull("Check Hibernate Dialect", ((DefaultSessionFactory) DefaultSessionFactory.getInstance()).getSqlDialect());
		} catch (MdoDataBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
