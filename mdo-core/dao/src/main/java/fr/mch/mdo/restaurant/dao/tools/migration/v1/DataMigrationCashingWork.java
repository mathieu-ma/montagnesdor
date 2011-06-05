package fr.mch.mdo.restaurant.dao.tools.migration.v1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;

import fr.mch.mdo.logs.ILogger;

public class DataMigrationCashingWork implements Work 
{
	private SessionFactory sessionFactoryV2;
	private ILogger logger;
	
	private String restaurantReference = "10203040506070";
	private String userLogin = "kimsan";

	public DataMigrationCashingWork(SessionFactory sessionFactoryV2, ILogger logger) {
		this.sessionFactoryV2 = sessionFactoryV2;
		this.logger = logger;
	}
	
	@Override
	public void execute(Connection connection) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement("SELECT T_CASHING.*, T_DINNER_TABLE.*, T_USER.* " +
					"FROM T_CASHING " +
					"JOIN T_DINNER_TABLE ON T_DINNER_TABLE.DTB_ID = T_CASHING.DTB_ID " +
					"JOIN T_USER ON T_USER.USR_ID = T_DINNER_TABLE.USR_ID");
			rs = ps.executeQuery();
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int maxColumn = rsMetaData.getColumnCount();
			logger.debug("MAx Columns " + maxColumn);
			while (rs.next()) {
				logger.debug("START Row " + rs.getRow());
				for (int i = 1; i <= maxColumn; i++) {
					logger.debug("Result " + rsMetaData.getColumnName(i) + " with Value " + rs.getString(i));
				}
				logger.debug("END Row " + rs.getRow());
			}
		} catch (SQLException e) {
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e) {
				}
			}
		}
	}

}
