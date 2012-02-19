package fr.mch.mdo.restaurant.dao;

import java.io.BufferedReader;
import java.io.File;
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

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;

/**
 * This class will be moved because to module test in class MdoLoadingDatabaseTestCase.
 * 
 * @author Mathieu
 * 
 */
public class MdoLoadingDatabase {
	
	private static ILogger logger;

	private enum SqlDialect {
		HSQLDIALECT() {
			Map<String, String> sqlVarMap(Reader reader) throws MdoDataBeanException {
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
			Map<String, String> sqlVarMap(Reader reader) throws MdoDataBeanException {
				Map<String, String> sqlVarMap = new HashMap<String, String>();
				Map<Pattern, String> patternSubstitutes = new HashMap<Pattern, String>();
				patternSubstitutes.put(Pattern.compile("\\*\\{(.*)\\}"), null);
				sqlVarMap.putAll(this.sqlVarMap(reader, patternSubstitutes));
				return sqlVarMap;
			}
		};
		Map<String, String> sqlVarMap(Reader reader) throws MdoDataBeanException {
			return new HashMap<String, String>();
		}

		Map<String, String> sqlVarMap(Reader reader, Map<Pattern, String> patternSubstitutes) throws MdoDataBeanException {
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
				logger.debug("SQL file error", e);
				throw new MdoDataBeanException("SQL file error", e);
			}
			finally {
				try {
					reader.close();
				} catch (Exception e) {
					logger.debug("Could not close the opened file", e);
					throw new MdoDataBeanException("Could not close the opened file", e);
				}
			}
			return result;
		}
	}

	/**
	 * This method is used to load the structure and data file in Database.
	 * @param connection the connection to database.
	 * @param sqlDialectName the SQL dialect.
	 * @param fileURLs Files to be loaded.
	 */
	public static void loadFiles(Connection connection, String sqlDialectName, URL[] fileURLs) throws MdoDataBeanException {
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
					// Don't use URL.getFile or URL.getPath instead convert to URI first
					// Because when using URL and the path contains space then the URL.getFile or URL.getPath will convert space to "%20"
					String fileName = fileURL.getPath();
					sqlFile = new SqlFile(reader, fileName, System.out, "UTF8", false);
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
					logger.debug("SQL file IOException", e);
					throw new MdoDataBeanException("SQL file IOException", e);
				} catch (SqlToolError e) {
					logger.debug("SQL file SqlToolError", e);
					throw new MdoDataBeanException("SQL file SqlToolError", e);
				} catch (SQLException e) {
					logger.debug("SQL file SQLException", e);
					throw new MdoDataBeanException("SQL file SQLException", e);
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
	private static void prepareConnection(Connection connection) throws MdoDataBeanException {
		// Prepare Connection with Hibernate
		if (connection != null) {
			//To be sure that the HSQLDB SQLFile.java will load the file into database
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				logger.debug("Could not set autocommit to true", e);
				throw new MdoDataBeanException("Could not set autocommit to true", e);
			}
		}
	}
	
	/**
	 * Prepare the SQL Dialect before using.
	 * @param sqlDialectName the SQL Dialect Name. 
	 * @return the SqlDialect enum.
	 */
	private static SqlDialect prepareSqlDialect(String sqlDialectName) throws MdoDataBeanException {
		// Prepare SQL Dialect
		SqlDialect result = null;
		if(sqlDialectName != null) {
			try {
				result = SqlDialect.valueOf(sqlDialectName);
			} catch (Exception e) {
				logger.debug("Could not parse the SQL Dialect", e);
				throw new MdoDataBeanException("Could not parse the SQL Dialect", e);
			}
		}
		return result;
	}
}
