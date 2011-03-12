package fr.mch.mdo.test;

import java.sql.Connection;
import java.sql.DriverManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * This class is abstract because we do not want JUnit to launch this class.
 * 
 * @author Mathieu
 * 
 */
public class MdoLoadingDatabaseTestCaseTest extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MdoLoadingDatabaseTestCaseTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(MdoLoadingDatabaseTestCaseTest.class);
    }

	public void testMdoLoadingDatabaseTestCase() {
		new MdoLoadingDatabaseTestCase(MdoLoadingDatabaseTestCaseTest.class.getName()) {

			@Override
			protected Connection getConnection() {
				Connection connection = null;
				try {
					Class.forName("org.hsqldb.jdbcDriver").newInstance();
					String url = "jdbc:hsqldb:mem:montagnesdortest";
					connection = DriverManager.getConnection(url, "SA", "");
				} catch (Exception e) {
					fail("Could not get the HSQLDB connection: " + e.getMessage());
				}
				return connection;
			}

			@Override
			protected String getSqlDialectName() {
				return "HSQLDIALECT";
			}
			
		};
	}
}
