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
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.dao.beans.Category;
import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.beans.ProductCategory;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;
import fr.mch.mdo.restaurant.dao.tools.migration.v1.DataMigrationWork;

public class DataMigrationProductCategoryWork extends DataMigrationWork 
{
	public DataMigrationProductCategoryWork(ILogger logger, File output,
			Restaurant restaurant, UserAuthentication user) {
		super(logger, output, restaurant, user);
	}

	@Override
	public void execute(Connection connection) throws SQLException {
		PrintWriter out = null;

		String restaurantReference = super.restaurant.getReference();

		/**	rateFormat is used formatting Bigdecimal rate */
		DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
		dfs.setDecimalSeparator('.');
		List<ProductCategory> productCategories = new ArrayList<ProductCategory>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			out = new PrintWriter(new FileWriter(output));

			// Key = Category label in V1
			// Value = Category code in V2
			Map<String, String> categoryV1ToV2 = new HashMap<String, String>();
			categoryV1ToV2.put("POISSON", "CATEGORY.FISH.1");
			categoryV1ToV2.put("VIANDE", "CATEGORY.MEAT.0");
			categoryV1ToV2.put("CAFE THE INFUSION", "CATEGORY.INFUSION.19");
			categoryV1ToV2.put("VOLAILLE", "CATEGORY.POULTRY.9");
			categoryV1ToV2.put("FRUITS ET LEGUME", "CATEGORY.VEGETABLE.17");
			categoryV1ToV2.put("BIERE", "CATEGORY.BEER.15");
			categoryV1ToV2.put("EAU JUS SODA", "CATEGORY.SODA.13");
			categoryV1ToV2.put("CRUSTACES", "CATEGORY.SHELLFISH.7");
			categoryV1ToV2.put("RIZ", "CATEGORY.RICE.8");
			categoryV1ToV2.put("GLACE", "CATEGORY.ICE_CREAM.18");
			categoryV1ToV2.put("VIN", "CATEGORY.WINE.3");
			categoryV1ToV2.put("powermat", "CATEGORY.STEAM.6");
			categoryV1ToV2.put("ALCOOL", "CATEGORY.ALCOHOL.10");
			
			ps = connection.prepareStatement("SELECT t_category_relation.pdt_id, t_category_relation.ctr_quantity, t_category_language.ctl_label" +
					" FROM t_category_relation JOIN t_category_language ON t_category_language.cat_id = t_category_relation.cat_id" +
					" WHERE t_category_language.loc_id='fr'");
			rs = ps.executeQuery();
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int maxColumn = rsMetaData.getColumnCount();
			logger.debug("Max Columns " + maxColumn);
			while (rs.next()) {
				logger.debug("START Row " + rs.getRow());
				ProductCategory productCategory = new ProductCategory();
				Product product = new Product();
				productCategory.setProduct(product);
				Category category = new Category();
				productCategory.setCategory(category);
				for (int i = 1; i <= maxColumn; i++) {
					String columnLabel = rsMetaData.getColumnLabel(i).toUpperCase();
					Object value = rs.getString(i);
					logger.debug("Result " + columnLabel + " with Value " + value);
					ProductCategoryResultSetRow.valueOf(columnLabel).fillValue(productCategory, value);
				}
				productCategories.add(productCategory);
				logger.debug("END Row " + rs.getRow());
			}
			// t_product_category
			for (Iterator<ProductCategory> iterator = productCategories.iterator(); iterator.hasNext();) {
				ProductCategory productCategory = iterator.next();
				String insertProductCategory = "INSERT INTO t_product_category (pdt_id, cat_id, pdc_quantity, pdc_deleted) SELECT t_product.pdt_id, t_category.cat_id, " + productCategory.getQuantity() + ", false" +
						" FROM t_product JOIN t_restaurant ON t_restaurant.res_id=t_product.res_id," +
							" t_category JOIN t_enum ON t_enum.enm_id = t_category.cat_code_enm_id" +
						" WHERE t_product.pdt_code = '" + productCategory.getProduct().getCode() + "' AND t_restaurant.res_reference = '" + restaurantReference + "'" +
								" AND t_enum.enm_language_key_label='" + categoryV1ToV2.get(productCategory.getCategory().getLabels().get(null)) + "';";
				out.println(insertProductCategory);
			}
			out.flush();
		} catch (SQLException e) {
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

	private enum ProductCategoryResultSetRow {
		PDT_ID() {
			public void fillValue(ProductCategory productCategory, Object value) {
				if (value != null) {
					productCategory.getProduct().setCode(value.toString());
				}
			}
		}, 
		CTR_QUANTITY() {
			public void fillValue(ProductCategory productCategory, Object value) {
				if (value != null) {
					productCategory.setQuantity(new BigDecimal(value.toString()));
				}
			}
		},  
		CTL_LABEL() {
			public void fillValue(ProductCategory productCategory, Object value) {
				Map<Long, String> labels = new HashMap<Long, String>();
				if (value != null) {
					labels.put(null, value.toString());
				}
				productCategory.getCategory().setLabels(labels);
			}
		};
		
		public void fillValue(ProductCategory productCategory, Object value) {
			
		}
	}
}
