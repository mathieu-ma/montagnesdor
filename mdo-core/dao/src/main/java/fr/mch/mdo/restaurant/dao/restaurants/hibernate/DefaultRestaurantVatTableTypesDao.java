package fr.mch.mdo.restaurant.dao.restaurants.hibernate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.beans.RestaurantVatTableType;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.restaurants.IRestaurantVatTableTypesDao;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultRestaurantVatTableTypesDao extends DefaultDaoServices implements IRestaurantVatTableTypesDao 
{
	private static final String RESTAURANT_VAT_TABLE_TYPE_FIND_ONLY_VATS = "RestaurantVatTableType.FindOnlyVats";

	private static class LazyHolder {
		private static IRestaurantVatTableTypesDao instance = new DefaultRestaurantVatTableTypesDao(
				LoggerServiceImpl.getInstance().getLogger(DefaultRestaurantVatTableTypesDao.class.getName()), new RestaurantVatTableType());
	}

	private DefaultRestaurantVatTableTypesDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static IRestaurantVatTableTypesDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultRestaurantVatTableTypesDao() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RestaurantVatTableType> findAll(Long restaurantId) throws MdoException {
		List<RestaurantVatTableType> result = null;
		
		Map<String, Object> propertyValueMap = Collections.singletonMap("restaurant.id", (Object)restaurantId);
		result =  super.findByProperties(propertyValueMap);
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RestaurantVatTableType> findOnlyVats(Long restaurantId) throws MdoException {
		List<RestaurantVatTableType> result = null;
		
		Map<String, Object> values = Collections.singletonMap("restaurantId", (Object)restaurantId);
		result = super.findAllByQuery(RESTAURANT_VAT_TABLE_TYPE_FIND_ONLY_VATS, values, true);
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RestaurantVatTableType> findAll(Long restaurantId, String typeName) throws MdoException {
		List<RestaurantVatTableType> result = null;

		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("restaurant.id", restaurantId);
		propertyValueMap.put("type.code.name", typeName);
		result = super.findByProperties(propertyValueMap);
		
		return result;
	}
}
