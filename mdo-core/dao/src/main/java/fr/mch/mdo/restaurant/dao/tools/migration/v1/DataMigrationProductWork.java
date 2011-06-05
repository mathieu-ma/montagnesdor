package fr.mch.mdo.restaurant.dao.tools.migration.v1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;
import fr.mch.mdo.restaurant.dao.beans.ValueAddedTax;
import fr.mch.mdo.restaurant.dao.tools.migration.v1.DataMigrationWork;

public class DataMigrationProductWork extends DataMigrationWork 
{
	public DataMigrationProductWork(ILogger logger, File output, Restaurant restaurant, UserAuthentication user) {
		super(logger, output, restaurant, user);
	}

	@Override
	public void execute(Connection connection) throws SQLException {
		PrintWriter out = null;
		
		String restaurantReference = super.restaurant.getReference();

		/**	rateFormat is used formatting Bigdecimal rate */
		DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
		dfs.setDecimalSeparator('.');
		DecimalFormat vatRateFormat = new DecimalFormat("#.00", dfs);
		List<Product> products = new ArrayList<Product>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			out = new PrintWriter(new FileWriter(output));
			
			ps = connection.prepareStatement("SELECT t_product.pdt_id PDT_ID, t_product.pdt_price PDT_PRICE, t_product.pdt_colorrgb PDT_COLOR_RGB, t_value_added_tax.vat_value VAT_VALUE," +
						" t_product_language.pdl_label PDL_LABEL" +
					" FROM t_product" +
						" JOIN t_product_language ON t_product_language.pdt_id = t_product.pdt_id" +
						" JOIN t_value_added_tax ON t_value_added_tax.vat_id = t_product.vat_id" +
					" WHERE t_product.psc_id=1" +
						" AND loc_id = 'fr'");
			rs = ps.executeQuery();
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int maxColumn = rsMetaData.getColumnCount();
			logger.debug("Max Columns " + maxColumn);
			while (rs.next()) {
				logger.debug("START Row " + rs.getRow());
				Product product = new Product();
				for (int i = 1; i <= maxColumn; i++) {
					String columnLabel = rsMetaData.getColumnLabel(i).toUpperCase();
					Object value = rs.getString(i);
					logger.debug("Result " + columnLabel + " with Value " + value);
					ProductResultSetRow.valueOf(columnLabel).fillValue(product, value);
				}
				products.add(product);
				logger.debug("END Row " + rs.getRow());
			}
			// t_product
			for (Iterator<Product> iterator = products.iterator(); iterator.hasNext();) {
				Product product = iterator.next();
				String insertProduct = "INSERT INTO t_product (res_id, pdt_code, pdt_price, pdt_colorRGB, vat_id, pdt_deleted) SELECT t_restaurant.res_id, '" + product.getCode() + "', " + 
				product.getPrice() + ", " + this.getColor(product.getColorRGB()) + 
				" , t_value_added_tax.vat_id, false " +
				" FROM t_value_added_tax, t_restaurant WHERE t_value_added_tax.vat_rate=" + vatRateFormat.format(product.getVat().getRate().doubleValue()) + " AND t_restaurant.res_reference = '" + restaurantReference + "';";
				out.println(insertProduct);
			}
			out.flush();
			// t_product_language
			for (Iterator<Product> iterator = products.iterator(); iterator.hasNext();) {
				Product product = iterator.next();
				String insertProductLanguage = "INSERT INTO t_product_language (pdt_id, loc_id, pdl_label, pdl_deleted) SELECT t_product.pdt_id, t_locale.loc_id, '" + product.getLabels().get(null) + "', false FROM t_product, t_locale WHERE t_product.pdt_code='" + product.getCode()+ "' AND t_locale.loc_language='fr';";
				out.println(insertProductLanguage);
			}
		} catch (SQLException e) {
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
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

	private enum ProductResultSetRow {
		PDT_ID() {
			public void fillValue(Product product, Object value) {
				if (value != null) {
					product.setCode(value.toString());
				}
			}
		}, 
		PDT_PRICE() {
			public void fillValue(Product product, Object value) {
				if (value != null) {
					product.setPrice(new BigDecimal(value.toString()));
				}
			}
		},  
		PDT_COLOR_RGB() {
			public void fillValue(Product product, Object value) {
				if (value != null) {
					product.setColorRGB(value.toString());
				}
			}
		},  
		VAT_VALUE() {
			public void fillValue(Product product, Object value) {
				ValueAddedTax vat = new ValueAddedTax();
				if (value != null) {
					vat.setRate(new BigDecimal(value.toString()));
				}
				product.setVat(vat);
			}
		}, 
		PDL_LABEL() {
			public void fillValue(Product product, Object value) {
				Map<Long, String> labels = new HashMap<Long, String>();
				if (value != null) {
					labels.put(null, value.toString().replace("'", "''"));
				}
				product.setLabels(labels);
			}
		};
		
		public void fillValue(Product product, Object value) {
			
		}
	}
}
