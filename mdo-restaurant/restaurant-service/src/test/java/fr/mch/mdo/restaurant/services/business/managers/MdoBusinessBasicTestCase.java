package fr.mch.mdo.restaurant.services.business.managers;

import java.sql.Connection;
import java.util.Locale;

import javax.security.auth.Subject;

import fr.mch.mdo.restaurant.dao.ISessionFactory;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultSessionFactory;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.UserAuthenticationDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.users.DefaultUserAuthenticationsManager;
import fr.mch.mdo.test.MdoLoadingDatabaseTestCase;
import fr.mch.mdo.test.MdoTestCase;

/**
 * This class is abstract because we do not want JUnit to launch this class.
 * 
 * @author Mathieu
 * 
 */
public abstract class MdoBusinessBasicTestCase extends MdoLoadingDatabaseTestCase 
{
	private String sqlDialectName = "HSQLDIALECT";

	protected static MdoUserContext userContext;

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public MdoBusinessBasicTestCase(String testName) {
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
	
	protected static MdoUserContext getUserContext() {
		if (userContext == null) {
			userContext = new MdoUserContext(new Subject());
			LocaleDto currentLocale = new LocaleDto();
			currentLocale.setLanguageCode(Locale.FRANCE.getLanguage());
			userContext.setCurrentLocale(currentLocale);
			UserAuthenticationDto user = null;
			try {
				user = (UserAuthenticationDto) DefaultUserAuthenticationsManager.getInstance().findByPrimaryKey(1L, false);
			} catch (MdoException e) {
				fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
			}
			userContext.setUserAuthentication(user);
		}
		return userContext;
	}
}
