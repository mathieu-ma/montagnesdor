package fr.mch.mdo.restaurant.dao.products.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.MdoEntry;
import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.products.IProductsDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultProductsDao extends DefaultDaoServices implements IProductsDao 
{
	private static class LazyHolder {
		private static IProductsDao instance = new DefaultProductsDao(LoggerServiceImpl.getInstance().getLogger(DefaultProductsDao.class.getName()), new Product());
	}

	private DefaultProductsDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static IProductsDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultProductsDao() {
	}

	@Override
	public IMdoBean findByUniqueKey(Object[] propertyValues, boolean... isLazy) throws MdoDataBeanException {
		super.findByUniqueKey(propertyValues, isLazy);
		if (propertyValues.length != 2) {
			super.getLogger().error("message.error.dao.unique.fields.2");
			throw new MdoDataBeanException("message.error.dao.unique.fields.2");
		}
		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("restaurant.id", propertyValues[0]);
		propertyValueMap.put("code", propertyValues[1]);
		return (IMdoBean) super.findByUniqueKey(propertyValueMap, isLazy);
	}

	@Override
	public IMdoBean findByCode(Long restaurantId, String code) throws MdoException {
		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("restaurant.id", restaurantId);
		propertyValueMap.put("code", code);
		return (IMdoBean) super.findByUniqueKey(propertyValueMap, false);
	}

	@Override
	public IMdoBean findByCode(String restaurantReference, String code) throws MdoException {
		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("restaurant.reference", restaurantReference);
		propertyValueMap.put("code", code);
		return (IMdoBean) super.findByUniqueKey(propertyValueMap, false);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<IMdoBean> findAllByPrefixCode(Long restaurantId, String prefixCode) throws MdoException {
		List<IMdoBean> result = new ArrayList<IMdoBean>();
		Map<String, Entry<PropertiesRestrictions, Object>> propertyValueMap = new HashMap<String, Entry<PropertiesRestrictions, Object>>();
		String property = "restaurant.id";
		Entry<PropertiesRestrictions, Object> value = new MdoEntry<PropertiesRestrictions, Object>(PropertiesRestrictions.EQUALS, restaurantId);
		propertyValueMap.put(property, value);
		property = "code";
		value = new MdoEntry<PropertiesRestrictions, Object>(PropertiesRestrictions.LIKE, prefixCode);
		propertyValueMap.put(property, value);
		result = super.findByPropertiesRestrictions(propertyValueMap, false);
		return result;
	}
}
