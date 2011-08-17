package fr.mch.mdo.restaurant.dao.tables.hibernate;

import java.util.HashMap;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.beans.TableBill;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.tables.ITableBillsDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultTableBillsDao extends DefaultDaoServices implements ITableBillsDao 
{
	private static class LazyHolder {
		private static ITableBillsDao instance = new DefaultTableBillsDao(LoggerServiceImpl.getInstance().getLogger(DefaultTableBillsDao.class.getName()),
					new TableBill());
	}

	private DefaultTableBillsDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static ITableBillsDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultTableBillsDao() {
	}

	@Override
	public IMdoBean findByUniqueKey(Object[] propertyValues, boolean... isLazy) throws MdoDataBeanException {

		/**
		 * propertyValues[0] dinnerTableId the dinner table ID.
		 * propertyValues[1] reference the reference.
		 * propertyValues[2] order the order.
		 */
		// Checking exception
		super.findByUniqueKey(propertyValues, isLazy);

		// Checking exception
		if (propertyValues.length != 3) {
			super.getLogger().error("message.error.dao.unique.fields.3");
			throw new MdoDataBeanException("message.error.dao.unique.fields.3");
		}

		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("dinnerTable.id", propertyValues[0]);
		propertyValueMap.put("reference", propertyValues[1]);
		propertyValueMap.put("order", propertyValues[2]);

		return (IMdoBean) super.findByUniqueKey(propertyValueMap, isLazy);
	}

	@Override
	public IMdoBean findByUniqueKey(Long dinnerTableId, String reference, Integer order, boolean... isLazy) throws MdoException {
		return this.findByPrimaryKey(new Object[] {dinnerTableId, reference, order}, isLazy);
	}

}
