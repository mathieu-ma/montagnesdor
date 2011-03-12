package fr.mch.mdo.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;

import fr.mch.mdo.test.resources.ITestResources;

/**
 * This class is abstract because we do not want JUnit to launch this class.
 * 
 * @author Mathieu
 * 
 */
public abstract class MdoLoadingDatabaseTestCase extends MdoTestCase 
{
	private static boolean alreadyLoaded = false;
	
	private enum SqlDialect {
		HSQLDIALECT() {
			Map<String, String> sqlVarMap(Reader reader) {
				Map<String, String> sqlVarMap = new HashMap<String, String>();
				// This is used to replace the "*{bytea}" by "BINARY" in the sql
				// file.
				sqlVarMap.put("bytea", "BINARY");

				Map<Pattern, String> patternSubstitutes = new HashMap<Pattern, String>();
				patternSubstitutes.put(Pattern.compile("\\*\\{(DEFAULT NEXTVAL.*)\\}"), "GENERATED BY DEFAULT AS IDENTITY");
				patternSubstitutes.put(Pattern.compile("\\*\\{(OWNED.*)\\}"), "RESTART WITH 1");
				sqlVarMap.putAll(this.sqlVarMap(reader, patternSubstitutes));
				return sqlVarMap;
			}
		},
		POSTGRESQLDIALECT() {
			Map<String, String> sqlVarMap(Reader reader) {
				Map<String, String> sqlVarMap = new HashMap<String, String>();
				Map<Pattern, String> patternSubstitutes = new HashMap<Pattern, String>();
				patternSubstitutes.put(Pattern.compile("\\*\\{(.*)\\}"), null);
				sqlVarMap.putAll(this.sqlVarMap(reader, patternSubstitutes));
				return sqlVarMap;
			}
		};
		Map<String, String> sqlVarMap(Reader reader) {
			return new HashMap<String, String>();
		}

		Map<String, String> sqlVarMap(Reader reader, Map<Pattern, String> patternSubstitutes) {
			HashMap<String, String> result = new HashMap<String, String>();
			BufferedReader br = null;
			try {
				String substituteX = "";
				String line = null;
				br = new BufferedReader(reader);
				// For each line in the file
				while ((line = br.readLine()) != null) {
					for (Entry<Pattern, String> entry : patternSubstitutes.entrySet()) {
						Pattern pattern = entry.getKey();
						String substitute = entry.getValue();
						// Looking for *{DEFAULT NEXTVAL(xxx)} and replace by
						// substitute
						Matcher matcher = pattern.matcher(line);
						while (matcher.find()) {
							// This is used to replace the "*{xxx}" by
							// substitute in the sql file.
							if (substitute == null) {
								substituteX = matcher.group(1);
							} else {
								substituteX = substitute;
							}
							result.put(matcher.group(1), substituteX);
						}
					}
				}
			} catch (IOException e) {
				fail("SQL file error: " + e.getMessage());
			}
			finally {
				try {
					reader.close();
				} catch (Exception e) {
					fail("Could not close the opened file: " + e.getMessage());
				}
			}
			return result;
		}
	}

	protected MdoLoadingDatabaseTestCase(String testName) {
		super(testName);
		
		// Files to be loaded
		URL[] fileURLs = { ITestResources.class.getResource("montagnesdorStructure.sql"),
				ITestResources.class.getResource("montagnesdorDatas.sql") 
		};
		// Check if we have already loaded the database files.
		if (!alreadyLoaded) {
			Connection connection = this.getConnection();
			String sqlDialectName = this.getSqlDialectName();
			loadFiles(connection, sqlDialectName, fileURLs);
			alreadyLoaded = true;
		}
	}

	/**
	 * This method is used to load the structure and data file in Database.
	 * @param connection the connection to database.
	 * @param sqlDialectName the SQL dialect.
	 * @param fileURLs Files to be loaded.
	 */
	protected void loadFiles(Connection connection, String sqlDialectName, URL[] fileURLs) {
		// Prepare Connection with Hibernate
		prepareConnection(connection);
		
		// Prepare SQL Dialect
		SqlDialect sqlDialect = prepareSqlDialect(sqlDialectName);

		// Replace *{xxx} in the sql file by "yyy" in the loaded database
		// For PostGresql, the standard GENERATED BY DEFAULT AS IDENTITY must be
		// replace by DEFAULT NEXTVAL('dedicated_sequence_name').
		// So, we have to map all PostGresql default generated sequences by
		// GENERATED BY DEFAULT AS IDENTITY.
		Map<String, String> sqlVarMap = new HashMap<String, String>();

		SqlFile sqlFile = null;
		try {
			for (URL fileURL : fileURLs) {
				Reader reader = null;
				try {
					// Open reader
					reader = new InputStreamReader(fileURL.openStream(), "UTF8");
					// Read and close reader
					sqlVarMap = sqlDialect.sqlVarMap(reader);
					// Open reader again because already read the reader
					reader = new InputStreamReader(fileURL.openStream(), "UTF8");
					sqlFile = new SqlFile(reader, fileURL.getFile(), System.out, "UTF8", false);
					sqlFile.addUserVars(sqlVarMap);
					sqlFile.setConnection(connection);
					// Execute the SQL
					sqlFile.execute();
					// The only reason for the following two statements is so
					// that changes made by one .sql file will effect the future SQL files.
					// Has no effect if you only execute one SQL file.
					connection = sqlFile.getConnection();
					sqlVarMap = sqlFile.getUserVars();
				} catch (IOException e) {
					fail("SQL file IOException: " + e.getMessage());
				} catch (SqlToolError e) {
					fail("SQL file SqlToolError: " + e.getMessage());
				} catch (SQLException e) {
					fail("SQL file SQLException: " + e.getMessage());
				}
			}
		} finally {
            try {
            	connection.setAutoCommit(false);
            	connection.close();
            } catch (SQLException se) {
                // Purposefully ignoring.
                // We have done what we want and are now going to exit, so
                // who cares.
            }
        }
	}

	/**
	 * Prepare the connection before using.
	 * @param connection the connection.
	 */
	private void prepareConnection(Connection connection) {
		// Prepare Connection with Hibernate
		assertNotNull("Check not null SQL Connection", connection);
		//To be sure that the HSQLDB SQLFile.java will load the file into database
		try {
			connection.setAutoCommit(true);
		} catch (Exception e) {
			fail("Could not set autocommit to true: " + e.getMessage());
		}
	}
	
	/**
	 * Prepare the SQL Dialect before using.
	 * @param sqlDialectName the SQL Dialect Name. 
	 * @return the SqlDialect enum.
	 */
	private SqlDialect prepareSqlDialect(String sqlDialectName) {
		// Prepare SQL Dialect
		SqlDialect result = null;
		assertNotNull("Check not null SQL Dialect name", sqlDialectName);
		try {
			result = SqlDialect.valueOf(sqlDialectName);
		} catch (Exception e) {
			fail("Could not parse the SQL Dialect: " + e.getMessage());
		}
		return result;
	}

	protected abstract Connection getConnection();

	protected abstract String getSqlDialectName();
}
