package fr.mch.mdo.restaurant.dao.hibernate;

import java.sql.Connection;

import fr.mch.mdo.restaurant.dao.ISessionFactory;
import fr.mch.mdo.test.MdoLoadingDatabaseTestCase;
import fr.mch.mdo.test.MdoTestCase;

/**
 * This class is abstract because we do not want JUnit to launch this class.
 * 
 * @author Mathieu
 * 
 */
public class HsqldbInitDataTestCase extends MdoLoadingDatabaseTestCase 
{

	private String sqlDialectName = "HSQLDIALECT";

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public HsqldbInitDataTestCase(String testName) {
		super(testName);
	}

	@Override
	protected Connection getConnection() {
		// Connection with Hibernate
		Connection connection = null;
		try {
			ISessionFactory iSessionFactory = DefaultSessionFactory.getInstance();
			assertNotNull("Check sessionFactory", iSessionFactory);
			assertTrue("Check instance sessionFactory", iSessionFactory instanceof DefaultSessionFactory);
			DefaultSessionFactory sessionFactory = (DefaultSessionFactory) iSessionFactory;
			connection = sessionFactory.getConnection();
			assertNotNull("Check connection", connection);
			// SQL dialect from Hibernate configuration
			String sqlDialectString = sessionFactory.getSqlDialect();
			assertNotNull("Check SQL Dialect From Hibernate configuration file", sqlDialectString);
			// Format the Hibernate SQL dialect string to java SqlDialect enum
			// name
			sqlDialectName = sqlDialectString.toUpperCase().substring(sqlDialectString.lastIndexOf(".") + 1);
			assertNotNull("Check SQL Dialect", sqlDialectName);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE);
		}
		return connection;
	}

	@Override
	protected String getSqlDialectName() {
		return sqlDialectName;
	}
	
	public void testDummy() {
		
	}
}
