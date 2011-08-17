package fr.mch.mdo.restaurant.dao.tables.hibernate;

import java.util.HashMap;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.MdoTableAsEnumTypeDao;
import fr.mch.mdo.restaurant.dao.beans.CashingType;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.tables.ICashingTypesDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultCashingTypesDao extends DefaultDaoServices implements ICashingTypesDao
{
	private static class LazyHolder {
		private static ICashingTypesDao instance = new DefaultCashingTypesDao(
				LoggerServiceImpl.getInstance().getLogger(DefaultCashingTypesDao.class.getName()), 
				new CashingType());
	}

	private DefaultCashingTypesDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static ICashingTypesDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultCashingTypesDao() {
	}

	@Override
	public IMdoBean findByUniqueKey(Object[] propertyValues, boolean... isLazy) throws MdoDataBeanException {

		/**
		 * propertyValues[0] = tableCashingId the dinner table ID.
		 * propertyValues[1] = code the code for type of cashing: could be GENERIC_CASH, EURO_CASH, DOLLAR_CASH, GENERIC_TICKET, MEAL_TICKET, HOLIDAYS_TICKET, GENERIC_CHECK, BNP_CHECK, GENERIC_CARD, VISA_CARD, MASTER_CARD, UNPAID...
		 */
		// Checking exception
		super.findByUniqueKey(propertyValues, isLazy);

		// Checking exception
		if (propertyValues.length != 2) {
			super.getLogger().error("message.error.dao.unique.fields.2");
			throw new MdoDataBeanException("message.error.dao.unique.fields.2");
		}

		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("tableCashing.id", propertyValues[0]);
		propertyValueMap.put("type.name", propertyValues[1]);
		propertyValueMap.put("type.type", MdoTableAsEnumTypeDao.CASHING_TYPE.name());
		propertyValueMap.put("type.deleted", Boolean.FALSE);

		return (IMdoBean) super.findByUniqueKey(propertyValueMap, isLazy);
	}

	@Override
	public IMdoBean findByUniqueKey(Long tableCashingId, String code, boolean... isLazy) throws MdoDataBeanException {
		return (IMdoBean) super.findByUniqueKey(new Object[] {tableCashingId, code}, isLazy);
	}

}
