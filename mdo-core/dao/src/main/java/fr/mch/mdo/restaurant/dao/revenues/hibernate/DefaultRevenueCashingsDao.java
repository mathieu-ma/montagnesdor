package fr.mch.mdo.restaurant.dao.revenues.hibernate;

import java.util.HashMap;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.MdoTableAsEnumTypeDao;
import fr.mch.mdo.restaurant.dao.beans.RevenueCashing;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.revenues.IRevenueCashingsDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultRevenueCashingsDao extends DefaultDaoServices implements IRevenueCashingsDao 
{
	private static class LazyHolder {
		private static IRevenueCashingsDao instance = new DefaultRevenueCashingsDao(LoggerServiceImpl.getInstance().getLogger(DefaultRevenueCashingsDao.class.getName()), new RevenueCashing());
	}

	private DefaultRevenueCashingsDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static IRevenueCashingsDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultRevenueCashingsDao() {
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
		propertyValueMap.put("revenue.id", propertyValues[0]);
		propertyValueMap.put("type.name", propertyValues[1]);
		propertyValueMap.put("type.type", MdoTableAsEnumTypeDao.CASHING_TYPE.name());
		propertyValueMap.put("type.deleted", Boolean.FALSE);

		return (IMdoBean) super.findByUniqueKey(propertyValueMap, isLazy);
	}

	@Override
	public IMdoBean findByUniqueKey(Long revenueId, String type, boolean... isLazy) throws MdoDataBeanException {
		return this.findByUniqueKey(new Object[] { revenueId, type }, isLazy);
	}

}
