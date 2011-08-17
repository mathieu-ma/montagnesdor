package fr.mch.mdo.restaurant.dao.tables.hibernate;

import java.util.HashMap;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.beans.TableCashing;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.tables.ITableCashingsDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultTableCashingsDao extends DefaultDaoServices implements ITableCashingsDao 
{
	private static class LazyHolder {
		private static ITableCashingsDao instance = new DefaultTableCashingsDao(LoggerServiceImpl.getInstance().getLogger(DefaultTableCashingsDao.class.getName()), 
				new TableCashing());
	}

	private DefaultTableCashingsDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static ITableCashingsDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultTableCashingsDao() {
	}

	@Override
	public IMdoBean findByUniqueKey(Object[] propertyValues, boolean... isLazy) throws MdoDataBeanException {

		/**
		 * propertyValues[0] = dinnerTableId the dinner table ID.
		 * propertyValues[1] = code the code for type of cashing: could be GENERIC_CASH, EURO_CASH, DOLLAR_CASH, GENERIC_TICKET, MEAL_TICKET, HOLIDAYS_TICKET, GENERIC_CHECK, BNP_CHECK, GENERIC_CARD, VISA_CARD, MASTER_CARD, UNPAID...
		 */
		// Checking exception
		super.findByUniqueKey(propertyValues, isLazy);

		// Checking exception
		if (propertyValues.length != 1) {
			super.getLogger().error("message.error.dao.unique.fields.1");
			throw new MdoDataBeanException("message.error.dao.unique.fields.1");
		}

		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("dinnerTable.id", propertyValues[0]);

		return (IMdoBean) super.findByUniqueKey(propertyValueMap, isLazy);
	}

	@Override
	public IMdoBean findByUniqueKey(Long dinnerTableId, boolean... isLazy) throws MdoDataBeanException {
		return (IMdoBean) super.findByUniqueKey(new Object[] {dinnerTableId}, isLazy);
	}

}
