package fr.mch.mdo.restaurant.dao.restaurants.hibernate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.beans.RestaurantReductionTable;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.restaurants.IRestaurantReductionTablesDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultRestaurantReductionTablesDao extends DefaultDaoServices implements IRestaurantReductionTablesDao
{
	private static final String RESTAURANT_REDUCTION_TABLE_FIND_ONLY_REDUCTION_TABLES = "RestaurantReductionTable.FindOnlyReductionTables";

	private static final String RESTAURANT_REDUCTION_TABLE_FIND_BY_UNIQUE_KEY = "RestaurantReductionTable.FindByUniqueKey";
	
	private static class LazyHolder {
		private static IRestaurantReductionTablesDao instance = new DefaultRestaurantReductionTablesDao(
				LoggerServiceImpl.getInstance().getLogger(DefaultRestaurantReductionTablesDao.class.getName()), new RestaurantReductionTable());
	}

	private DefaultRestaurantReductionTablesDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static IRestaurantReductionTablesDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultRestaurantReductionTablesDao() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RestaurantReductionTable> findAll(Long restaurantId) throws MdoDataBeanException {
		List<RestaurantReductionTable> result = null;
		
		Map<String, Object> propertyValueMap = Collections.singletonMap("restaurant.id", (Object)restaurantId);
		result =  super.findByProperties(propertyValueMap);
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RestaurantReductionTable> findOnlyReductionTables(Long restaurantId) throws MdoDataBeanException {
		List<RestaurantReductionTable> result = null;
		
		result =  super.findAllByQuery(RESTAURANT_REDUCTION_TABLE_FIND_ONLY_REDUCTION_TABLES, Collections.singletonMap("restaurantId", (Object)restaurantId), true);
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RestaurantReductionTable> findAll(Long restaurantId, String typeName) throws MdoDataBeanException {
		List<RestaurantReductionTable> result = null;

		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("restaurant.id", restaurantId);
		propertyValueMap.put("type.code.name", typeName);
		result = super.findByProperties(propertyValueMap);
		
		return result;
	}

	@Override
	public RestaurantReductionTable findReductionTable(Long restaurantId, Long typeId) throws MdoDataBeanException {
		RestaurantReductionTable result = null;

		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("restaurantId", restaurantId);
		propertyValueMap.put("typeId", typeId);

		result =  (RestaurantReductionTable) super.uniqueResult(super.findAllByQuery(RESTAURANT_REDUCTION_TABLE_FIND_BY_UNIQUE_KEY, propertyValueMap, true));
		
		return result;
	}
}
