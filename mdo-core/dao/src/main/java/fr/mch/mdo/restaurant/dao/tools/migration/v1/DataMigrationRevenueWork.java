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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.Revenue;
import fr.mch.mdo.restaurant.dao.beans.RevenueCashing;
import fr.mch.mdo.restaurant.dao.beans.RevenueVat;
import fr.mch.mdo.restaurant.dao.beans.TableType;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;
import fr.mch.mdo.restaurant.dao.beans.ValueAddedTax;
import fr.mch.mdo.restaurant.dao.tools.migration.v1.DataMigrationWork;

public class DataMigrationRevenueWork extends DataMigrationWork 
{
	public DataMigrationRevenueWork(ILogger logger, File output,
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
		DecimalFormat vatRateFormat = new DecimalFormat("#.00", dfs);

		List<Revenue> revenues = new ArrayList<Revenue>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			out = new PrintWriter(new FileWriter(output));

			// Key = Type label in V1
			// Value = Type code in V2
			Map<String, String> typeV1ToV2 = new HashMap<String, String>();
			typeV1ToV2.put("false", "TABLE_TYPE.EAT_IN.1");
			typeV1ToV2.put("true", "TABLE_TYPE.TAKE_AWAY.0");

			// Fill t_day_revenue
			ps = connection.prepareStatement("SELECT drv_id, drv_revenue_date, drv_print_date, drv_closing_date, drv_cash, drv_ticket, drv_cheque," +
					" drv_card, drv_unpaid, drv_amount, drv_takeaway " +
					" FROM t_day_revenue");
			rs = ps.executeQuery();
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int maxColumn = rsMetaData.getColumnCount();
			logger.debug("Max Columns " + maxColumn);
			while (rs.next()) {
				logger.debug("START t_day_revenue Row " + rs.getRow());
				Revenue revenue = new Revenue();
				TableType tableType = new TableType();
				MdoTableAsEnum code = new MdoTableAsEnum();
				tableType.setCode(code);
				revenue.setCashings(new HashSet<RevenueCashing>());
				revenue.setTableType(tableType);
				for (int i = 1; i <= maxColumn; i++) {
					String columnLabel = rsMetaData.getColumnLabel(i).toUpperCase();
					Object value = rs.getObject(i);
					logger.debug("Resultt_day_revenue " + columnLabel + " with Value " + value);
					RevenueResultSetRow.valueOf(columnLabel).fillValue(revenue, value);
				}
				revenues.add(revenue);
				logger.debug("END t_day_revenue Row " + rs.getRow());
			}
			for (Iterator<Revenue> iterator = revenues.iterator(); iterator.hasNext();) {
				Revenue revenue = iterator.next();
				// Fill t_vat_revenue
				revenue.setVats(new HashSet<RevenueVat>());
				ps = connection.prepareStatement("SELECT t_value_added_tax.vat_value, t_vat_revenue.vtr_amount, t_vat_revenue.vtr_value " +
							" FROM t_vat_revenue " +
							" JOIN t_day_revenue ON t_day_revenue.drv_id=t_vat_revenue.drv_id " +
							" JOIN t_value_added_tax ON t_value_added_tax.vat_id=t_vat_revenue.vat_id " +
							" WHERE t_day_revenue.drv_id=" + revenue.getId());
				rs = ps.executeQuery();
				rsMetaData = rs.getMetaData();
				maxColumn = rsMetaData.getColumnCount();
				logger.debug("t_vat_revenue Max Columns " + maxColumn);
				while (rs.next()) {
					logger.debug("START t_vat_revenue Row " + rs.getRow());
					RevenueVat revenueVat = new RevenueVat();

					for (int i = 1; i <= maxColumn; i++) {
						String columnLabel = rsMetaData.getColumnLabel(i).toUpperCase();
						Object value = rs.getObject(i);
						logger.debug("t_vat_revenue Result " + columnLabel + " with Value " + value);
						RevenueVatResultSetRow.valueOf(columnLabel).fillValue(revenueVat, value);
					}
					// Dummy Id in order to have unique element when adding into vats Set 
					revenueVat.getVat().setId(new Long(rs.getRow()));
					revenue.getVats().add(revenueVat);
					logger.debug("END t_vat_revenue Row " + rs.getRow());
				}
			}
			
			// t_revenue
			for (Iterator<Revenue> iterator = revenues.iterator(); iterator.hasNext();) {
				Revenue revenue = iterator.next();
				String insertProduct = "INSERT INTO t_revenue (res_id, rev_revenue_date, tbt_id, rev_printing_date, rev_closing_date, rev_amount, rev_deleted) SELECT t_restaurant.res_id, " + this.formatDate(sdf, revenue.getRevenueDate())	+ ", " + 
						" t_table_type.tbt_id, " + 
						this.formatDate(sdfTimes, revenue.getPrintingDate()) + ", " + this.formatDate(sdfTimes, revenue.getClosingDate()) + ", " + 
						revenue.getAmount() + ", false" +
						" FROM t_table_type JOIN t_enum ON t_table_type.tbt_code_enm_id = t_enum.enm_id, t_restaurant" +
						" WHERE t_enum.enm_language_key_label='" + typeV1ToV2.get(revenue.getTableType().getCode().getLanguageKeyLabel()) + "' "+
						" AND t_restaurant.res_reference = '" + restaurantReference + "';";
				out.println(insertProduct);
			}
			out.flush();
			// t_revenue_cashing
			for (Iterator<Revenue> iterator = revenues.iterator(); iterator.hasNext();) {
				Revenue revenue = iterator.next();
				Set<RevenueCashing> cashings = revenue.getCashings(); 
				for (Iterator<RevenueCashing> iterator2 = cashings.iterator(); iterator2.hasNext();) {
					RevenueCashing revenueCashing = iterator2.next();
					String insertRevenueCashing = "INSERT INTO t_revenue_cashing (rev_id, rvc_type_enum_id, rvc_amount, rvc_deleted) SELECT t_revenue.rev_id, " +
							" enum_cashing.enm_id, " + revenueCashing.getAmount() + ", false " +    
							" FROM t_revenue JOIN t_restaurant ON t_restaurant.res_id=t_revenue.res_id " +
							" JOIN t_table_type ON t_table_type.tbt_id=t_revenue.tbt_id " +
							" JOIN t_enum enum_table_type ON enum_table_type.enm_id=t_table_type.tbt_code_enm_id," +
							" t_enum enum_cashing " +
						" WHERE 1=1 " +
							" AND enum_cashing.enm_language_key_label = '" + revenueCashing.getType().getLanguageKeyLabel() + "' " +
							" AND enum_table_type.enm_language_key_label = '" + typeV1ToV2.get(revenue.getTableType().getCode().getLanguageKeyLabel()) + "' " +
							" AND t_revenue.rev_revenue_date=" + this.formatDate(sdf, revenue.getRevenueDate()) + "" +
							" AND t_restaurant.res_reference = '" + restaurantReference + "';";
					if (revenue.getRevenueDate() == null) {
						// Remove Revenue date in where clause
						insertRevenueCashing = insertRevenueCashing.replace("t_revenue.rev_revenue_date=null", "t_revenue.rev_revenue_date IS null");
					}
					out.println(insertRevenueCashing);
				}
			}
			out.flush();
			// t_revenue_vat
			for (Iterator<Revenue> iterator = revenues.iterator(); iterator.hasNext();) {
				Revenue revenue = iterator.next();
				Set<RevenueVat> vats = revenue.getVats(); 
				for (Iterator<RevenueVat> iterator2 = vats.iterator(); iterator2.hasNext();) {
					RevenueVat revenueVat = iterator2.next();
					String insertRevenueVat = "INSERT INTO t_revenue_vat (rev_id, vat_id, rva_amount, rva_value, rva_deleted) SELECT t_revenue.rev_id, t_value_added_tax.vat_id, " +
						revenueVat.getAmount() + ", " + revenueVat.getValue() + ", false " +    
						" FROM t_revenue JOIN t_restaurant ON t_restaurant.res_id=t_revenue.res_id " +
						" JOIN t_table_type ON t_table_type.tbt_id=t_revenue.tbt_id " +
						" JOIN t_enum enum_table_type ON enum_table_type.enm_id=t_table_type.tbt_code_enm_id," +
						" t_value_added_tax " +
						" WHERE 1=1 " +
							" AND t_value_added_tax.vat_rate=" + vatRateFormat.format(revenueVat.getVat().getRate().doubleValue()) + " " +
							" AND enum_table_type.enm_language_key_label = '" + typeV1ToV2.get(revenue.getTableType().getCode().getLanguageKeyLabel()) + "' " +
							" AND t_revenue.rev_revenue_date=" + this.formatDate(sdf, revenue.getRevenueDate()) + "" +
							" AND t_restaurant.res_reference = '" + restaurantReference + "';";
					if (revenue.getRevenueDate() == null) {
						// Remove Revenue date in where clause
						insertRevenueVat = insertRevenueVat.replace("t_revenue.rev_revenue_date=null", "t_revenue.rev_revenue_date IS null");
					}
					out.println(insertRevenueVat);
				}
			}
			out.flush();
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

	private enum RevenueResultSetRow {
		DRV_ID() {
				public void fillValue(Revenue revenue, Object value) {
					if (value != null) {
						revenue.setId(new Long(value.toString()));
					}
				}
		}, 
		 DRV_REVENUE_DATE() {
				public void fillValue(Revenue revenue, Object value) {
					if (value != null) {
						revenue.setRevenueDate((Date) value);
					}
				}
		}, 
		DRV_PRINT_DATE() {
			public void fillValue(Revenue revenue, Object value) {
				if (value != null) {
					revenue.setPrintingDate((Date) value);
				}
			}
		}, 
		DRV_CLOSING_DATE() {
			public void fillValue(Revenue revenue, Object value) {
				if (value != null) {
					revenue.setClosingDate((Date) value);
				}
			}
		}, 
		DRV_CASH() {
			public void fillValue(Revenue revenue, Object value) {
				if (value != null) {
					RevenueCashing revenueCashing = new RevenueCashing();
					MdoTableAsEnum type = new MdoTableAsEnum();
					type.setLanguageKeyLabel("CASHING_TYPE.GENERIC_CASH.0");
					revenueCashing.setType(type);
					revenueCashing.setAmount(new BigDecimal(value.toString()));
					if (revenueCashing.getAmount().doubleValue()!=0) {
						// Dummy Id in order to have unique element when adding into cashings Set 
						revenueCashing.getType().setId(new Long(0));
						revenue.getCashings().add(revenueCashing);
					}
				}
			}
		},  
		DRV_TICKET() {
			public void fillValue(Revenue revenue, Object value) {
				if (value != null) {
					RevenueCashing revenueCashing = new RevenueCashing();
					MdoTableAsEnum type = new MdoTableAsEnum();
					type.setLanguageKeyLabel("CASHING_TYPE.GENERIC_TICKET.1");
					revenueCashing.setType(type);
					revenueCashing.setAmount(new BigDecimal(value.toString()));
					if (revenueCashing.getAmount().doubleValue()!=0) {
						// Dummy Id in order to have unique element when adding into cashings Set 
						revenueCashing.getType().setId(new Long(1));
						revenue.getCashings().add(revenueCashing);
					}
				}
			}
		},  
		DRV_CHEQUE() {
			public void fillValue(Revenue revenue, Object value) {
				if (value != null) {
					RevenueCashing revenueCashing = new RevenueCashing();
					MdoTableAsEnum type = new MdoTableAsEnum();
					type.setLanguageKeyLabel("CASHING_TYPE.GENERIC_CHECK.2");
					revenueCashing.setType(type);
					revenueCashing.setAmount(new BigDecimal(value.toString()));
					if (revenueCashing.getAmount().doubleValue()!=0) {
						// Dummy Id in order to have unique element when adding into cashings Set 
						revenueCashing.getType().setId(new Long(2));
						revenue.getCashings().add(revenueCashing);
					}
				}
			}
		},  
		DRV_CARD() {
			public void fillValue(Revenue revenue, Object value) {
				if (value != null) {
					RevenueCashing revenueCashing = new RevenueCashing();
					MdoTableAsEnum type = new MdoTableAsEnum();
					type.setLanguageKeyLabel("CASHING_TYPE.GENERIC_CARD.3");
					revenueCashing.setType(type);
					revenueCashing.setAmount(new BigDecimal(value.toString()));
					if (revenueCashing.getAmount().doubleValue()!=0) {
						// Dummy Id in order to have unique element when adding into cashings Set 
						revenueCashing.getType().setId(new Long(3));
						revenue.getCashings().add(revenueCashing);
					}
				}
			}
		},  
		DRV_UNPAID() {
			public void fillValue(Revenue revenue, Object value) {
				if (value != null) {
					RevenueCashing revenueCashing = new RevenueCashing();
					MdoTableAsEnum type = new MdoTableAsEnum();
					type.setLanguageKeyLabel("CASHING_TYPE.UNPAID.4");
					revenueCashing.setType(type);
					revenueCashing.setAmount(new BigDecimal(value.toString()));
					if (revenueCashing.getAmount().doubleValue()!=0) {
						// Dummy Id in order to have unique element when adding into cashings Set 
						revenueCashing.getType().setId(new Long(4));
						revenue.getCashings().add(revenueCashing);
					}
				}
			}
		},  
		DRV_AMOUNT() {
			public void fillValue(Revenue revenue, Object value) {
				if (value != null) {
				revenue.setAmount(new BigDecimal(value.toString()));
				}
			}
		},  
		DRV_TAKEAWAY() {
			public void fillValue(Revenue revenue, Object value) {
				if (value != null) {
					revenue.getTableType().getCode().setLanguageKeyLabel(value.toString());
				}
			}
		};
		public void fillValue(Revenue revenue, Object value) {
			
		}
	}
	
	private enum RevenueVatResultSetRow {
		VTR_AMOUNT() {
			public void fillValue(RevenueVat revenueVat, Object value) {
				if (value != null) {
					revenueVat.setAmount(new BigDecimal(value.toString()));
				}
			}
		}, 
		VTR_VALUE() {
			public void fillValue(RevenueVat revenueVat, Object value) {
				if (value != null) {
					revenueVat.setValue(new BigDecimal(value.toString()));
				}
			}
		},  
		VAT_VALUE() {
			public void fillValue(RevenueVat revenueVat, Object value) {
				ValueAddedTax vat = new ValueAddedTax();
				if (value != null) {
					vat.setRate(new BigDecimal(value.toString()));
				}
				revenueVat.setVat(vat);
			}
		};
		
		public void fillValue(RevenueVat revenueVat, Object value) {
			
		}
	}

}
