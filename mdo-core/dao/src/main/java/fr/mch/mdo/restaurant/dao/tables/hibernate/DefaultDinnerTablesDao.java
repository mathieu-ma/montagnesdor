package fr.mch.mdo.restaurant.dao.tables.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.MdoEntry;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.table.orders.IDinnerTablesDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultDinnerTablesDao extends DefaultDaoServices implements IDinnerTablesDao 
{
	private static class LazyHolder {
		private static IDinnerTablesDao instance = new DefaultDinnerTablesDao(LoggerServiceImpl.getInstance().getLogger(DefaultDinnerTablesDao.class.getName()), new DinnerTable());
	}

	private DefaultDinnerTablesDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static IDinnerTablesDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultDinnerTablesDao() {
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<IMdoBean> findAllByPrefixNumber(Long restaurantId, String prefixTableNumber, boolean... isLazy) throws MdoException {
		List<IMdoBean> result = new ArrayList<IMdoBean>();
		Map<String, Entry<PropertiesRestrictions, Object>> propertyValueMap = new HashMap<String, Entry<PropertiesRestrictions, Object>>();

		String property = "restaurant.id";
		Entry<PropertiesRestrictions, Object> value = new MdoEntry<PropertiesRestrictions, Object>(PropertiesRestrictions.EQUALS, restaurantId);
		propertyValueMap.put(property, value);

		property = "number";
		value = new MdoEntry<PropertiesRestrictions, Object>(PropertiesRestrictions.LIKE, prefixTableNumber);
		propertyValueMap.put(property, value);

		// Only the ones who cashing date is null
		property = "cashingDate";
		value = new MdoEntry<PropertiesRestrictions, Object>(PropertiesRestrictions.IS_NULL, null);
		propertyValueMap.put(property, value);

		result = super.findByPropertiesRestrictions(propertyValueMap, false);
		return result;
	}

	@Override
	public DinnerTable findByNumber(Long restaurantId, String number, boolean... isLazy) throws MdoException {
		// Only the one who cashing date is null
		return (DinnerTable) this.findByUniqueKey(new Object[] { restaurantId, number, null }, isLazy);
	}

	@Override
	public IMdoBean findByUniqueKey(Object[] propertyValues, boolean... isLazy) throws MdoDataBeanException {
		// Checking exception
		super.findByUniqueKey(propertyValues, isLazy);

		// Checking exception
		if (propertyValues.length != 3) {
			super.getLogger().error("message.error.dao.unique.fields.3");
			throw new MdoDataBeanException("message.error.dao.unique.fields.3");
		}

		Map<String, Entry<PropertiesRestrictions, Object>> propertyValueMap = new HashMap<String, Entry<PropertiesRestrictions, Object>>();
		String property = "restaurant.id";
		Entry<PropertiesRestrictions, Object> value = new MdoEntry<PropertiesRestrictions, Object>(PropertiesRestrictions.EQUALS, propertyValues[0]);
		propertyValueMap.put(property, value);

		property = "number";
		value = new MdoEntry<PropertiesRestrictions, Object>(PropertiesRestrictions.EQUALS, propertyValues[1]);
		propertyValueMap.put(property, value);

		property = "cashingDate";
		PropertiesRestrictions propertiesRestrictions = PropertiesRestrictions.EQUALS;
		if (propertyValues[2] == null) {
			propertiesRestrictions = PropertiesRestrictions.IS_NULL;
		}
		value = new MdoEntry<PropertiesRestrictions, Object>(propertiesRestrictions, propertyValues[2]);
		propertyValueMap.put(property, value);

		return (IMdoBean) uniqueResult(super.findByPropertiesRestrictions(propertyValueMap, isLazy));
	}

	@Override
	public IMdoBean findByUniqueKey(Long restaurantId, String number, Date cashingDate, boolean... isLazy) throws MdoDataBeanException {
		return this.findByUniqueKey(new Object[] { restaurantId, number, cashingDate }, isLazy);
	}

}
