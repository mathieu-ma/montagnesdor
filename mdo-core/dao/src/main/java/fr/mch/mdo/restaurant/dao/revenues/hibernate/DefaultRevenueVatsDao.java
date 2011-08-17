package fr.mch.mdo.restaurant.dao.revenues.hibernate;

import java.util.HashMap;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.MdoTableAsEnumTypeDao;
import fr.mch.mdo.restaurant.dao.beans.RevenueVat;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.revenues.IRevenueVatsDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultRevenueVatsDao extends DefaultDaoServices implements IRevenueVatsDao 
{
	private static class LazyHolder {
		private static IRevenueVatsDao instance = new DefaultRevenueVatsDao(LoggerServiceImpl.getInstance().getLogger(DefaultRevenueVatsDao.class.getName()), 
				new RevenueVat());
	}

	private DefaultRevenueVatsDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static IRevenueVatsDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultRevenueVatsDao() {
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
		propertyValueMap.put("vat.code.name", propertyValues[1]);
		propertyValueMap.put("vat.code.type", MdoTableAsEnumTypeDao.VALUE_ADDED_TAX.name());
		propertyValueMap.put("vat.code.deleted", Boolean.FALSE);

		return (IMdoBean) super.findByUniqueKey(propertyValueMap, isLazy);
	}

	@Override
	public IMdoBean findByUniqueKey(Long revenueId, String vat, boolean... isLazy) throws MdoDataBeanException {
		return this.findByUniqueKey(new Object[] { revenueId, vat }, isLazy);
	}

}
