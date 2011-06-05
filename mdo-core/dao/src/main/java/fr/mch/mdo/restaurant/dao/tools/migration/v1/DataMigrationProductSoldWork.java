package fr.mch.mdo.restaurant.dao.tools.migration.v1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.beans.ProductSold;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;
import fr.mch.mdo.restaurant.dao.tools.migration.v1.DataMigrationWork;

public class DataMigrationProductSoldWork extends DataMigrationWork 
{
	public DataMigrationProductSoldWork(ILogger logger, File output,
			Restaurant restaurant, UserAuthentication user) {
		super(logger, output, restaurant, user);
	}

	@Override
	public void execute(Connection connection) throws SQLException {
		PrintWriter out = null;
		
		String restaurantReference = super.restaurant.getReference();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<ProductSold> productSolds = new ArrayList<ProductSold>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			out = new PrintWriter(new FileWriter(output));

			ps = connection.prepareStatement("SELECT scp_updated_year, scp_updated_month, scp_updated_day, pdt_id, scp_quantity" +
					" FROM t_stats_consumption_product" +
//" WHERE scp_updated_year=2005 AND pdt_id='V6' " +
					" ORDER BY scp_updated_year, scp_updated_month, scp_updated_day");
			rs = ps.executeQuery();
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int maxColumn = rsMetaData.getColumnCount();
			logger.debug("Max Columns " + maxColumn);
			while (rs.next()) {
				logger.debug("START Row " + rs.getRow());
				ProductSold productSold = new ProductSold();
				Product product = new Product();
				productSold.setProduct(product);
				productSold.setSoldDate(new Date());
				for (int i = 1; i <= maxColumn; i++) {
					String columnLabel = rsMetaData.getColumnLabel(i).toUpperCase();
					Object value = rs.getString(i);
					logger.debug("Result " + columnLabel + " with Value " + value);
					ProductSoldResultSetRow.valueOf(columnLabel).fillValue(productSold, value);
				}
				productSolds.add(productSold);
				logger.debug("END Row " + rs.getRow());
			}
			// t_product_sold 
//			sessionFactoryV2.openSession().doWork(new Work() {
//				@Override
//				public void execute(Connection connection) throws SQLException {
//					System.out.println("Removing t_product_sold rows ...");
//					Statement statement = connection.createStatement();
//					statement.executeUpdate("DELETE FROM t_product_sold");
//					connection.commit();
//					statement.close();
//					connection.close();
//				}
//			});
			int index = 0;
			int maxSize = productSolds.size();
			for (;index<maxSize;) {
				ProductSold productSold = productSolds.get(index);
				final String insertProductCategory = "INSERT INTO t_product_sold (pds_sold_date, pdt_id, pds_quantity, pds_deleted) SELECT '" + sdf.format(productSold.getSoldDate()) + "', t_product.pdt_id, " + productSold.getQuantity() + ", false" +
						" FROM t_product JOIN t_restaurant ON t_restaurant.res_id=t_product.res_id" +
						" WHERE t_product.pdt_code = '" + productSold.getProduct().getCode() + "' AND t_restaurant.res_reference = '" + restaurantReference + "';";
				out.println(insertProductCategory);
				index++;
				if (index%10 == 0) {
					out.flush();	
				}
//				System.out.println("Inserting t_product_sold row ... " + index);
//				sessionFactoryV2.openSession().doWork(new Work() {
//					@Override
//					public void execute(Connection connection) throws SQLException {
//						Statement statement = connection.createStatement();
//						statement.executeUpdate(insertProductCategory);
//						connection.commit();
//						statement.close();
//						connection.close();
//					}
//				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
			try {
				out.close();
			} catch (Exception e) {
			}
		}
	}

	private enum ProductSoldResultSetRow {
		PDT_ID() {
			public void fillValue(ProductSold productSold, Object value) {
				if (value != null) {
					productSold.getProduct().setCode(value.toString());
				}
			}
		}, 
		SCP_QUANTITY() {
			public void fillValue(ProductSold productSold, Object value) {
				if (value != null) {
					productSold.setQuantity(new BigDecimal(value.toString()));
				}
			}
		},  
		SCP_UPDATED_DAY() {
			public void fillValue(ProductSold productSold, Object value) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(productSold.getSoldDate());
				if (value != null) {
					calendar.set(Calendar.DAY_OF_MONTH, new Integer(value.toString()).intValue());
				}
				productSold.setSoldDate(calendar.getTime());
			}
		},  
		SCP_UPDATED_MONTH() {
			public void fillValue(ProductSold productSold, Object value) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(productSold.getSoldDate());
				if (value != null) {
					calendar.set(Calendar.MONTH, new Integer(value.toString()).intValue());
				}
				productSold.setSoldDate(calendar.getTime());
			}
		},  
		SCP_UPDATED_YEAR() {
			public void fillValue(ProductSold productSold, Object value) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(productSold.getSoldDate());
				if (value != null) {
					calendar.set(Calendar.YEAR, new Integer(value.toString()).intValue());
				}
				productSold.setSoldDate(calendar.getTime());
			}
		};
		
		public void fillValue(ProductSold productSold, Object value) {
			
		}
	}
}
