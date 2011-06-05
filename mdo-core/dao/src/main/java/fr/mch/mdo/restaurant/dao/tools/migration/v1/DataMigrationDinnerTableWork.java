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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.OrderLine;
import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.beans.ProductSpecialCode;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.TableCashing;
import fr.mch.mdo.restaurant.dao.beans.TableType;
import fr.mch.mdo.restaurant.dao.beans.TableVat;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;
import fr.mch.mdo.restaurant.dao.beans.ValueAddedTax;
import fr.mch.mdo.restaurant.dao.tools.migration.v1.DataMigrationWork;

public class DataMigrationDinnerTableWork extends DataMigrationWork 
{
	public DataMigrationDinnerTableWork(ILogger logger, File output,
			Restaurant restaurant, UserAuthentication user) {
		super(logger, output, restaurant, user);
	}

	@Override
	public void execute(Connection connection) throws SQLException {
		PrintWriter out = null;

		String restaurantReference = super.restaurant.getReference();
		String userLogin = super.user.getLogin();

		/**	rateFormat is used formatting Bigdecimal rate */
		DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
		dfs.setDecimalSeparator('.');
		DecimalFormat vatRateFormat = new DecimalFormat("#.00", dfs);

		Map<String, String> productSpecialCodeV1ToV2 = new HashMap<String, String>();
		{
			productSpecialCodeV1ToV2.put("#", "PRODUCT_SPECIAL_CODE.OFFERED_PRODUCT.1");
			productSpecialCodeV1ToV2.put("-", "PRODUCT_SPECIAL_CODE.DISCOUNT_ORDER.2");
			productSpecialCodeV1ToV2.put("/", "PRODUCT_SPECIAL_CODE.USER_ORDER.3");
		}
		
		List<DinnerTable> tables = new ArrayList<DinnerTable>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			out = new PrintWriter(new FileWriter(output));

			// Key = Type label in V1
			// Value = Type code in V2
			Map<String, String> typeV1ToV2 = new HashMap<String, String>();
			typeV1ToV2.put("false", "TABLE_TYPE.EAT_IN.1");
			typeV1ToV2.put("true", "TABLE_TYPE.TAKE_AWAY.0");

			// Fill t_dinner_table
			ps = connection.prepareStatement("SELECT dtb_id, dtb_number, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio," +
					" dtb_amount_pay, dtb_registration_date, dtb_print_date, dtb_cashing_date, dtb_reduction_ratio_changed, dtb_takeaway" +
					" FROM t_dinner_table");
			rs = ps.executeQuery();
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int maxColumn = rsMetaData.getColumnCount();
			logger.debug("Max Columns " + maxColumn);
			while (rs.next()) {
				logger.debug("START Row " + rs.getRow());
				DinnerTable table = new DinnerTable();
				TableType type = new TableType();
				MdoTableAsEnum code = new MdoTableAsEnum();
				type.setCode(code);
				table.setType(type);
				for (int i = 1; i <= maxColumn; i++) {
					String columnLabel = rsMetaData.getColumnLabel(i).toUpperCase();
					Object value = rs.getObject(i);
					logger.debug("Result " + columnLabel + " with Value " + value);
					DinnerTableResultSetRow.valueOf(columnLabel).fillValue(table, value);
				}
				tables.add(table);
				logger.debug("END Row " + rs.getRow());
			}
			for (Iterator<DinnerTable> iterator = tables.iterator(); iterator.hasNext();) {
				DinnerTable table = iterator.next();
				// Fill t_order_line
				table.setOrders(new HashSet<OrderLine>());
				ps = connection.prepareStatement("SELECT orl_quantity, pdt_id, orl_special_code_value, orl_label, orl_unit_price, orl_amount" +
						" FROM t_order_line WHERE dtb_id=" + table.getId());
				rs = ps.executeQuery();
				rsMetaData = rs.getMetaData();
				maxColumn = rsMetaData.getColumnCount();
				logger.debug("t_order_line Max Columns " + maxColumn);
				while (rs.next()) {
					logger.debug("START t_order_line Row " + rs.getRow());
					OrderLine orderLine = new OrderLine();
					Product product = new Product();
					orderLine.setProduct(product);
					ProductSpecialCode productSpecialCode = new ProductSpecialCode();
					MdoTableAsEnum code = new MdoTableAsEnum();
					productSpecialCode.setCode(code);
					orderLine.setProductSpecialCode(productSpecialCode);
					for (int i = 1; i <= maxColumn; i++) {
						String columnLabel = rsMetaData.getColumnLabel(i).toUpperCase();
						Object value = rs.getObject(i);
						logger.debug("t_order_line Result " + columnLabel + " with Value " + value);
						OrderLineResultSetRow.valueOf(columnLabel).fillValue(orderLine, value);
					}
					table.getOrders().add(orderLine);
					logger.debug("END t_order_line Row " + rs.getRow());
				}
				// Fill t_table_vat
				table.setVats(new HashSet<TableVat>());
				ps = connection.prepareStatement("SELECT t_value_added_tax.vat_value, t_vat_table.vtt_amount, t_vat_table.vtt_value " +
							" FROM t_vat_table " +
							" JOIN t_dinner_table ON t_dinner_table.dtb_id=t_vat_table.dtb_id " +
							" JOIN t_value_added_tax ON t_value_added_tax.vat_id=t_vat_table.vat_id " +
							" WHERE t_dinner_table.dtb_id=" + table.getId());
				rs = ps.executeQuery();
				rsMetaData = rs.getMetaData();
				maxColumn = rsMetaData.getColumnCount();
				logger.debug("t_table_vat Max Columns " + maxColumn);
				while (rs.next()) {
					logger.debug("START t_table_vat Row " + rs.getRow());
					TableVat tableVat = new TableVat();
					
					for (int i = 1; i <= maxColumn; i++) {
						String columnLabel = rsMetaData.getColumnLabel(i).toUpperCase();
						Object value = rs.getObject(i);
						logger.debug("t_table_vat Result " + columnLabel + " with Value " + value);
						TableVatResultSetRow.valueOf(columnLabel).fillValue(tableVat, value);
					}
					// Dummy Id in order to have unique element when adding into vats Set 
					tableVat.getVat().setId(new Long(rs.getRow()));
					table.getVats().add(tableVat);
					logger.debug("END t_table_vat Row " + rs.getRow());
				}
				// Fill t_cashing
				table.setCashings(new HashSet<TableCashing>());
				ps = connection.prepareStatement("SELECT t_cashing.csh_cash, t_cashing.csh_ticket, t_cashing.csh_cheque, t_cashing.csh_card, t_cashing.csh_unpaid " +
							" FROM t_cashing " +
							" WHERE t_cashing.dtb_id=" + table.getId());
				rs = ps.executeQuery();
				rsMetaData = rs.getMetaData();
				maxColumn = rsMetaData.getColumnCount();
				logger.debug("t_cashing Max Columns " + maxColumn);
				while (rs.next()) {
					logger.debug("START t_cashing Row " + rs.getRow());
					for (int i = 1; i <= maxColumn; i++) {
						TableCashing tableCashing = new TableCashing();
						String columnLabel = rsMetaData.getColumnLabel(i).toUpperCase();
						Object value = rs.getObject(i);
						logger.debug("t_cashing Result " + columnLabel + " with Value " + value);
						TableCashingResultSetRow.valueOf(columnLabel).fillValue(tableCashing, value);
						if (tableCashing.getValue().doubleValue() != 0) {
							// Dummy Id in order to have unique element when adding into cashings Set 
							tableCashing.getType().setId(new Long(i));
							table.getCashings().add(tableCashing);
						}
					}
					logger.debug("END t_cashing Row " + rs.getRow());
				}
			}

			// t_dinner_table
			for (Iterator<DinnerTable> iterator = tables.iterator(); iterator.hasNext();) {
				DinnerTable table = iterator.next();
				String insertProduct = "INSERT INTO t_dinner_table (res_id, dtb_code, dtb_cashing_date, aut_id, roo_id, dtb_customers_number, dtb_quantities_sum, dtb_amounts_sum, dtb_reduction_ratio, dtb_amount_pay, dtb_registration_date, dtb_printing_date, dtb_reduction_ratio_changed, tbt_id, dtb_deleted) SELECT t_restaurant.res_id, '" + table.getNumber()	+ "', " + 
					this.formatDate(sdfTimes, table.getCashingDate()) + ", t_user_authentication.aut_id, null, " + table.getCustomersNumber() + ", " + table.getQuantitiesSum() + ", " + 
					table.getAmountsSum() + ", " + table.getReductionRatio() + ", " + table.getAmountPay() + ", " + 
					this.formatDate(sdfTimes, table.getRegistrationDate()) + ", " + this.formatDate(sdfTimes, table.getPrintingDate()) + ", " + 
						table.getReductionRatioChanged() + ", t_table_type.tbt_id, false" +
						" FROM t_table_type JOIN t_enum ON t_table_type.tbt_code_enm_id = t_enum.enm_id, t_user_authentication, t_restaurant" +
						" WHERE t_enum.enm_language_key_label='" + typeV1ToV2.get(table.getType().getCode().getLanguageKeyLabel()) + "' AND "+
						" t_user_authentication.aut_login='" + userLogin + "'" +
						" AND t_restaurant.res_reference = '" + restaurantReference + "';";

				out.println(insertProduct);
			}
			out.flush();
			// t_order_line
			for (Iterator<DinnerTable> iterator = tables.iterator(); iterator.hasNext();) {
				DinnerTable table = iterator.next();
				Set<OrderLine> orders = table.getOrders(); 
				for (Iterator<OrderLine> iterator2 = orders.iterator(); iterator2.hasNext();) {
					OrderLine orderLine = iterator2.next();
					String insertProduct = "INSERT INTO t_order_line (dtb_id, psc_id, pdt_id, cre_id, prp_id, orl_quantity, orl_label, orl_unit_price, orl_amount, orl_deleted) SELECT t_dinner_table.dtb_id," +
							" t_product_special_code.psc_id, t_product.pdt_id, null, t_product_part.prp_id, " + 
							orderLine.getQuantity() + ", " + this.getLabel(orderLine.getLabel()) + ", " + orderLine.getUnitPrice() + ", " + orderLine.getAmount() + ", false" +    
						" FROM t_dinner_table JOIN t_restaurant restaurant_dinner_table ON restaurant_dinner_table.res_id=t_dinner_table.res_id," +
							" t_product_special_code JOIN t_enum product_special_code_enum ON product_special_code_enum.enm_id=t_product_special_code.psc_code_enm_id JOIN t_restaurant restaurant_psc ON restaurant_psc.res_id=t_product_special_code.res_id, " +
							" t_product," +
							" t_product_part JOIN t_enum product_part_enum ON product_part_enum.enm_id=t_product_part.prp_code_enm_id " +
						" WHERE 1=1 " +
							" AND product_part_enum.enm_language_key_label='PRODUCT_PART.MISCELLANEOUS.0' " +
							" AND t_product.pdt_code= " + this.getProductV1ToV2(productSpecialCodeV1ToV2, orderLine.getProduct().getCode(), orderLine.getProductSpecialCode().getCode().getLanguageKeyLabel()) + "" +
							" AND product_special_code_enum.enm_language_key_label='" + this.getProductSpecialCodeV1ToV2(productSpecialCodeV1ToV2, orderLine.getProduct().getCode()) + "'" +
							" AND restaurant_psc.res_reference = '" + restaurantReference + "'" +
							" AND t_dinner_table.dtb_code='" + table.getNumber() + "'" +
							" AND t_dinner_table.dtb_cashing_date=" + this.formatDate(sdfTimes, table.getCashingDate()) + "" +
							" AND restaurant_dinner_table.res_reference = '" + restaurantReference + "';";

					if ("/".equals(orderLine.getProduct().getCode())) {
						// Remove Product reference
						insertProduct = insertProduct.replace("t_product.pdt_id", "null").replace("t_product,", "").replace("AND t_product.pdt_code= '/'", "");
					}
					if (table.getCashingDate() == null) {
						// Remove Cashing date in where clause
						insertProduct = insertProduct.replace("t_dinner_table.dtb_cashing_date=null", "t_dinner_table.dtb_cashing_date IS null");
					}
					out.println(insertProduct);
				}
			}
			out.flush();
			// t_table_vat
			for (Iterator<DinnerTable> iterator = tables.iterator(); iterator.hasNext();) {
				DinnerTable table = iterator.next();
				Set<TableVat> vats = table.getVats(); 
				for (Iterator<TableVat> iterator2 = vats.iterator(); iterator2.hasNext();) {
					TableVat tableVat = iterator2.next();
					String insertTableVat = "INSERT INTO t_table_vat (dtb_id, vat_id, tvt_amount, tvt_value, tvt_deleted) SELECT t_dinner_table.dtb_id, t_value_added_tax.vat_id, " +
								tableVat.getAmount() + ", " + tableVat.getValue() + ", false " +    
							" FROM t_dinner_table JOIN t_restaurant restaurant_dinner_table ON restaurant_dinner_table.res_id=t_dinner_table.res_id," +
							" t_value_added_tax " +
						" WHERE 1=1 " +
							" AND t_value_added_tax.vat_rate=" + vatRateFormat.format(tableVat.getVat().getRate().doubleValue()) + " " +
							" AND t_dinner_table.dtb_code='" + table.getNumber() + "'" +
							" AND t_dinner_table.dtb_cashing_date=" + this.formatDate(sdfTimes, table.getCashingDate()) + "" +
							" AND restaurant_dinner_table.res_reference = '" + restaurantReference + "';";
					if (table.getCashingDate() == null) {
						// Remove Cashing date in where clause
						insertTableVat = insertTableVat.replace("t_dinner_table.dtb_cashing_date=null", "t_dinner_table.dtb_cashing_date IS null");
					}
					out.println(insertTableVat);
				}
			}
			out.flush();
			// t_table_cashing
			for (Iterator<DinnerTable> iterator = tables.iterator(); iterator.hasNext();) {
				DinnerTable table = iterator.next();
				Set<TableCashing> cashings = table.getCashings(); 
				for (Iterator<TableCashing> iterator2 = cashings.iterator(); iterator2.hasNext();) {
					TableCashing tableCashing = iterator2.next();
					String insertTableCashing = "INSERT INTO t_table_cashing (dtb_id, tcs_type_enum_id, tcs_value, tcs_deleted) SELECT t_dinner_table.dtb_id, t_enum.enm_id, " +
							tableCashing.getValue() + ", false " +    
							" FROM t_dinner_table JOIN t_restaurant restaurant_dinner_table ON restaurant_dinner_table.res_id=t_dinner_table.res_id," +
							" t_enum " +
						" WHERE 1=1 " +
							" AND t_enum.enm_language_key_label = '" + tableCashing.getType().getLanguageKeyLabel() + "' " +
							" AND t_dinner_table.dtb_code='" + table.getNumber() + "'" +
							" AND t_dinner_table.dtb_cashing_date=" + this.formatDate(sdfTimes, table.getCashingDate()) + "" +
							" AND restaurant_dinner_table.res_reference = '" + restaurantReference + "';";
					if (table.getCashingDate() == null) {
						// Remove Cashing date in where clause
						insertTableCashing = insertTableCashing.replace("t_dinner_table.dtb_cashing_date=null", "t_dinner_table.dtb_cashing_date IS null");
					}
					out.println(insertTableCashing);
				}
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

	private enum DinnerTableResultSetRow {
		DTB_ID() {
			public void fillValue(DinnerTable table, Object value) {
				if (value != null) {
					table.setId(Long.valueOf(value.toString()));
				}
			}
		},
		DTB_NUMBER() {
			public void fillValue(DinnerTable table, Object value) {
				if (value != null) {
					table.setNumber(value.toString());
				}
			}
		},
		DTB_CUSTOMERS_NUMBER() {
			public void fillValue(DinnerTable table, Object value) {
				if (value != null) {
					table.setCustomersNumber(Integer.valueOf(value.toString()));
				}
			}
		},
		DTB_QUANTITIES_SUM() {
			public void fillValue(DinnerTable table, Object value) {
				if (value != null) {
					table.setQuantitiesSum(new BigDecimal(value.toString()));
				}
			}
		},  
		DTB_AMOUNTS_SUM() {
			public void fillValue(DinnerTable table, Object value) {
				if (value != null) {
					table.setAmountsSum(new BigDecimal(value.toString()));
				}
			}
		},  
		DTB_REDUCTION_RATIO() {
			public void fillValue(DinnerTable table, Object value) {
				if (value != null) {
					table.setReductionRatio(new BigDecimal(value.toString()));
				}
			}
		},  
		DTB_AMOUNT_PAY() {
			public void fillValue(DinnerTable table, Object value) {
				if (value != null) {
					table.setAmountPay(new BigDecimal(value.toString()));
				}
			}
		},  
		DTB_REGISTRATION_DATE() {
			public void fillValue(DinnerTable table, Object value) {
				if (value != null) {
					table.setRegistrationDate((Date) value);
				}
			}
		},  
		DTB_PRINT_DATE() {
			public void fillValue(DinnerTable table, Object value) {
				if (value != null) {
					table.setPrintingDate((Date) value);
				}
			}
		},  
		DTB_CASHING_DATE() {
			public void fillValue(DinnerTable table, Object value) {
				if (value != null) {
					Date date = (Date) value;
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.set(Calendar.HOUR_OF_DAY, Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
					calendar.set(Calendar.MINUTE, Calendar.getInstance().get(Calendar.MINUTE));
					calendar.set(Calendar.SECOND, Calendar.getInstance().get(Calendar.SECOND));
					calendar.set(Calendar.MILLISECOND, Calendar.getInstance().get(Calendar.MILLISECOND));
					table.setCashingDate(calendar.getTime());
				}
			}
		},		
		DTB_REDUCTION_RATIO_CHANGED() {
			public void fillValue(DinnerTable table, Object value) {
				if (value != null) {
					table.setReductionRatioChanged(Boolean.valueOf(value.toString()));
				}
			}
		},		
		DTB_TAKEAWAY() {
			public void fillValue(DinnerTable table, Object value) {
				if (value != null) {
					table.getType().getCode().setLanguageKeyLabel(value.toString());
				}
			}
		};
		
		public void fillValue(DinnerTable table, Object value) {
			
		}
	}

	private enum OrderLineResultSetRow {
		ORL_QUANTITY() {
			public void fillValue(OrderLine orderLine, Object value) {
				if (value != null) {
					orderLine.setQuantity(new BigDecimal(value.toString()));
				}
			}
		},
		PDT_ID() {
			public void fillValue(OrderLine orderLine, Object value) {
				if (value != null) {
					orderLine.getProduct().setCode(value.toString());
				}
			}
		},
		ORL_SPECIAL_CODE_VALUE() {
			public void fillValue(OrderLine orderLine, Object value) {
				if (value != null) {
					orderLine.getProductSpecialCode().getCode().setLanguageKeyLabel(value.toString());
				}
			}
		},
		ORL_LABEL() {
			public void fillValue(OrderLine orderLine, Object value) {
				if (value != null) {
					orderLine.setLabel(value.toString());
				}
			}
		},  
		ORL_UNIT_PRICE() {
			public void fillValue(OrderLine orderLine, Object value) {
				if (value != null) {
					orderLine.setUnitPrice(new BigDecimal(value.toString()));
				}
			}
		},  
		ORL_AMOUNT() {
			public void fillValue(OrderLine orderLine, Object value) {
				if (value != null) {
					orderLine.setAmount(new BigDecimal(value.toString()));
				}
			}
		};
		
		public void fillValue(OrderLine orderLine, Object value) {
			
		}
	}
	
	private enum TableVatResultSetRow {
		VTT_AMOUNT() {
			public void fillValue(TableVat tableVat, Object value) {
				if (value != null) {
					tableVat.setAmount(new BigDecimal(value.toString()));
				}
			}
		}, 
	   VTT_VALUE() {
			public void fillValue(TableVat tableVat, Object value) {
				if (value != null) {
					tableVat.setValue(new BigDecimal(value.toString()));
				}
			}
		},  
		VAT_VALUE() {
			public void fillValue(TableVat tableVat, Object value) {
				ValueAddedTax vat = new ValueAddedTax();
				if (value != null) {
					vat.setRate(new BigDecimal(value.toString()));
				}
				tableVat.setVat(vat);
			}
		};
		
		public void fillValue(TableVat tableVat, Object value) {
			
		}
	}

	private enum TableCashingResultSetRow {
		CSH_CASH() {
			public void fillValue(TableCashing tableCashing, Object value) {
				if (value != null) {
					MdoTableAsEnum type = new MdoTableAsEnum();
					type.setLanguageKeyLabel("CASHING_TYPE.GENERIC_CASH.0");
					tableCashing.setType(type);
					tableCashing.setValue(new BigDecimal(value.toString()));
				}
			}
		}, 
		CSH_TICKET() {
			public void fillValue(TableCashing tableCashing, Object value) {
				if (value != null) {
					MdoTableAsEnum type = new MdoTableAsEnum();
					type.setLanguageKeyLabel("CASHING_TYPE.GENERIC_TICKET.1");
					tableCashing.setType(type);
					tableCashing.setValue(new BigDecimal(value.toString()));
				}
			}
		},  
		CSH_CHEQUE() {
			public void fillValue(TableCashing tableCashing, Object value) {
				if (value != null) {
					MdoTableAsEnum type = new MdoTableAsEnum();
					type.setLanguageKeyLabel("CASHING_TYPE.GENERIC_CHECK.2");
					tableCashing.setType(type);
					tableCashing.setValue(new BigDecimal(value.toString()));
				}
			}
		}, 
		CSH_CARD() {
			public void fillValue(TableCashing tableCashing, Object value) {
				if (value != null) {
					MdoTableAsEnum type = new MdoTableAsEnum();
					type.setLanguageKeyLabel("CASHING_TYPE.GENERIC_CARD.3");
					tableCashing.setType(type);
					tableCashing.setValue(new BigDecimal(value.toString()));
				}
			}
		},  
		CSH_UNPAID() {
			public void fillValue(TableCashing tableCashing, Object value) {
				if (value != null) {
					MdoTableAsEnum type = new MdoTableAsEnum();
					type.setLanguageKeyLabel("CASHING_TYPE.UNPAID.4");
					tableCashing.setType(type);
					tableCashing.setValue(new BigDecimal(value.toString()));
				}
			}
		};
		
		public void fillValue(TableCashing tableCashing, Object value) {
			
		}
	}

}
