package fr.mch.mdo.restaurant.dao.tables.hibernate;

import java.util.HashMap;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.beans.Credit;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.tables.ICreditsDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultCreditsDao extends DefaultDaoServices implements ICreditsDao 
{
	private static class LazyHolder {
		private static ICreditsDao instance = new DefaultCreditsDao(LoggerServiceImpl.getInstance().getLogger(DefaultCreditsDao.class.getName()), new Credit());
	}

	private DefaultCreditsDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static ICreditsDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultCreditsDao() {
	}

	@Override
	public IMdoBean findByUniqueKey(Object[] propertyValues, boolean... isLazy) throws MdoDataBeanException {
		// Checking exception
		super.findByUniqueKey(propertyValues, isLazy);

		// Checking exception
		if (propertyValues.length != 2) {
			super.getLogger().error("message.error.dao.unique.fields.2");
			throw new MdoDataBeanException("message.error.dao.unique.fields.2");
		}

		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("restaurant.id", propertyValues[0]);
		propertyValueMap.put("reference", propertyValues[1]);

		return (IMdoBean) super.findByUniqueKey(propertyValueMap, isLazy);
	}

	@Override
	public IMdoBean findByUniqueKey(Long restaurantId, String reference) throws MdoDataBeanException {
		return this.findByUniqueKey(new Object[] { restaurantId, reference });
	}
}
