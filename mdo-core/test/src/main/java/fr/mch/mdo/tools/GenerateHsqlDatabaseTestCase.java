package fr.mch.mdo.tools;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;

import fr.mch.mdo.test.MdoLoadingDatabaseTestCase;
import fr.mch.mdo.test.MdoTestCase;
import fr.mch.mdo.test.resources.ITestResources;

/**
 * This class will generate HSQLDB database from memory to files.
 * 
 * @author Mathieu
 * 
 */
public class GenerateHsqlDatabaseTestCase extends MdoLoadingDatabaseTestCase
{
	/**
	 * This field is also initialized in the getConnection method.
	 */
	private String sqlDialectName = "HSQLDIALECT";

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public GenerateHsqlDatabaseTestCase(String testName) {
		super(testName);
	}

	@Override
	protected Connection getConnection() {
		// Must be initialized again there because this method is called in the super constructor.
		// So for the parent the field sqlDialectName is not yet initialized.
		sqlDialectName = "HSQLDIALECT";

		String generatedDatabaseFolder = "/home/mathieu/tmp/database/montagnesdor";
		
		// Files to be loaded: Must be override here
		fileURLs = new URL[] { ITestResources.class.getResource("montagnesdorStructure.sql"), ITestResources.class.getResource("montagnesdorDatas.sql")};
		fileURLs = new URL[] { ITestResources.class.getResource("montagnesdorStructure.sql"),
				ITestResources.class.getResource("DataMigrationKimsan93.sql"),
				ITestResources.class.getResource("DataMigrationProductWork.sql"),
				ITestResources.class.getResource("DataMigrationProductCategoryWork.sql"),
				ITestResources.class.getResource("DataMigrationDinnerTableWork.sql"),
				ITestResources.class.getResource("DataMigrationRevenueWork.sql"),
				ITestResources.class.getResource("DataMigrationProductSoldWork.sql"), // Will take a very long time
		};
		
		// Connection with standard SQL
		Connection connection = null;
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:file:" + generatedDatabaseFolder + ";shutdown=true", "SA", "");
			assertNotNull("Check connection", connection);
			// SQL dialect from Hibernate configuration
			// Format the Hibernate SQL dialect string to java SqlDialect enum
			// name
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
	
	/**
	 * This is a dummy test for generating HSQLDB files database.
	 */
	public void testDummy() {
		assertTrue("This is a dummy test for generating HSQLDB files database.", true);
	}
	
	public static void main(String[] args) {
		new GenerateHsqlDatabaseTestCase(GenerateHsqlDatabaseTestCase.class.getName());
	}
}
