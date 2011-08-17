package fr.mch.mdo.restaurant.dao.revenues.hibernate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.MdoTableAsEnumTypeDao;
import fr.mch.mdo.restaurant.dao.beans.Revenue;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.revenues.IRevenuesDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultRevenuesDao extends DefaultDaoServices implements IRevenuesDao 
{
	private static class LazyHolder {
		private static IRevenuesDao instance = new DefaultRevenuesDao(LoggerServiceImpl.getInstance().getLogger(DefaultRevenuesDao.class.getName()), new Revenue());
	}

	private DefaultRevenuesDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static IRevenuesDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultRevenuesDao() {
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

		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("restaurant.id", propertyValues[0]);
		propertyValueMap.put("revenueDate", propertyValues[1]);
		propertyValueMap.put("tableType.code.name", propertyValues[2]);
		propertyValueMap.put("tableType.code.type", MdoTableAsEnumTypeDao.TABLE_TYPE.name());
		propertyValueMap.put("tableType.code.deleted", Boolean.FALSE);
		
		return (IMdoBean) super.findByUniqueKey(propertyValueMap, isLazy);
	}

	@Override
	public IMdoBean findByUniqueKey(Long restaurantId, Date revenueDate, String type, boolean... isLazy) throws MdoDataBeanException {
		return this.findByUniqueKey(new Object[] { restaurantId, revenueDate, type }, isLazy);
	}

}
