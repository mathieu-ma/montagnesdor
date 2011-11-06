package fr.mch.mdo.restaurant.dao.restaurants.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.restaurants.IRestaurantsDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultRestaurantsDao extends DefaultDaoServices implements IRestaurantsDao 
{
	private static class LazyHolder {
		private static IRestaurantsDao instance = new DefaultRestaurantsDao(LoggerServiceImpl.getInstance().getLogger(DefaultRestaurantsDao.class.getName()), new Restaurant());
	}

	private DefaultRestaurantsDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static IRestaurantsDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultRestaurantsDao() {
	}

	@Override
	public IMdoBean findByUniqueKey(Object[] propertyValues, boolean... isLazy) throws MdoDataBeanException {
		// Check exception
		super.findByUniqueKey(propertyValues, isLazy);
		if (propertyValues.length != 1) {
			super.getLogger().error("message.error.dao.unique.field.1");
			throw new MdoDataBeanException("message.error.dao.unique.field.1");
		}
		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("reference", propertyValues[0]);

		return (IMdoBean) super.findByUniqueKey(propertyValueMap, isLazy);
	}

	@Override
	public IMdoBean findByUniqueKey(Object propertyValue, boolean... isLazy) throws MdoDataBeanException {
		return this.findByUniqueKey(new Object[] { propertyValue }, isLazy);
	}

	@Override
	public IMdoBean findByReference(String reference) throws MdoException {
		return this.findByUniqueKey(new Object[] { reference });
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<IMdoBean> findRestaurants(Long userId, boolean... isLazy) throws MdoDataBeanException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", userId);
		return super.findAllByQuery("User.SelectRestaurantsById", parameters, isLazy);
	}
}
