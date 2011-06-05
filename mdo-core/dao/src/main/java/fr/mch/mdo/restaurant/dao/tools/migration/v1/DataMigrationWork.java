package fr.mch.mdo.restaurant.dao.tools.migration.v1;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.hibernate.jdbc.Work;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;

public abstract class DataMigrationWork implements Work 
{
	protected SimpleDateFormat sdfTimes = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	protected ILogger logger;
	protected File output;
	
	protected Restaurant restaurant;
	protected UserAuthentication user;

	public DataMigrationWork(ILogger logger, File output, Restaurant restaurant, UserAuthentication user) {
		this.logger = logger;
		this.output = output;		
		this.restaurant = restaurant;
		this.user = user;
	}

	protected String getColor(String color) {
		String result = color;
		if (result == null || result.isEmpty()) {
			result = null;
		} else {
			result = "'" + result + "'";
		}
		return result;
	}
	
	protected String getLabel(String label) {
		String result = label;
		if (result == null || result.isEmpty()) {
			result = null;
		} else {
			result = "'" + result.trim().replace("'", "''") + "'";
		}
		return result;
	}

	protected String formatDate(SimpleDateFormat sdf, Date date) {
		String result = null;
		if (date != null) {
			result = "'" + sdf.format(date) + "'";
		}
		return result;
	}

	protected String getProductV1ToV2(Map<String, String> productSpecialCodeV1ToV2, String key, String defaultValue) {
		String result = null;
		result = productSpecialCodeV1ToV2.get(key);
		if (result != null) {
			if (defaultValue != null && !defaultValue.isEmpty()) {
				result = "'" + defaultValue + "'";
			} else {
				if (key != null && !key.isEmpty()) {
					result = "'" + key + "'";
				}
			}
		} else {
			if (key != null && !key.isEmpty()) {
				result = "'" + key + "'";
			}
		}
		return result;
	}

	protected String getProductSpecialCodeV1ToV2(Map<String, String> productSpecialCodeV1ToV2, String key) {
		String result = null;
		result = productSpecialCodeV1ToV2.get(key);
		if (result == null) {
			result = "PRODUCT_SPECIAL_CODE.NOTHING.0";
		}
		return result;
	}
}
