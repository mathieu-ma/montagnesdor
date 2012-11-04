package fr.mch.mdo.restaurant.dao.restaurants.hibernate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.beans.RestaurantPrefixTable;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.restaurants.IRestaurantPrefixTablesDao;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultRestaurantPrefixTablesDao extends DefaultDaoServices implements IRestaurantPrefixTablesDao 
{
	private static final String RESTAURANT_PREFIX_TABLE_FIND_ONLY_PREFIX_TABLES = "RestaurantPrefixTable.FindOnlyPrefixTables";

	private static class LazyHolder {
		private static IRestaurantPrefixTablesDao instance = new DefaultRestaurantPrefixTablesDao(
				LoggerServiceImpl.getInstance().getLogger(DefaultRestaurantPrefixTablesDao.class.getName()), new RestaurantPrefixTable());
	}

	private DefaultRestaurantPrefixTablesDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static IRestaurantPrefixTablesDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultRestaurantPrefixTablesDao() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RestaurantPrefixTable> findAll(Long restaurantId) throws MdoException {
		List<RestaurantPrefixTable> result = null;
		
		Map<String, Object> propertyValueMap = Collections.singletonMap("restaurant.id", (Object)restaurantId);
		result =  super.findByProperties(propertyValueMap);
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RestaurantPrefixTable> findOnlyPrefixTables(Long restaurantId) throws MdoException {
		List<RestaurantPrefixTable> result = null;
		
		Map<String, Object> values = Collections.singletonMap("restaurantId", (Object)restaurantId);
		result = super.findAllByQuery(RESTAURANT_PREFIX_TABLE_FIND_ONLY_PREFIX_TABLES, values, true);
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RestaurantPrefixTable> findAll(Long restaurantId, String typeName) throws MdoException {
		List<RestaurantPrefixTable> result = null;

		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("restaurant.id", restaurantId);
		propertyValueMap.put("type.code.name", typeName);
		result = super.findByProperties(propertyValueMap);
		
		return result;
	}
}
