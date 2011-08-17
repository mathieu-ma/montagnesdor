package fr.mch.mdo.restaurant.dao.tables.hibernate;

import java.util.HashMap;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.MdoTableAsEnumTypeDao;
import fr.mch.mdo.restaurant.dao.beans.TableType;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.tables.ITableTypesDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultTableTypesDao extends DefaultDaoServices implements ITableTypesDao 
{
	private static class LazyHolder {
		private static ITableTypesDao instance = new DefaultTableTypesDao(LoggerServiceImpl.getInstance().getLogger(DefaultTableTypesDao.class.getName()), new TableType());
	}

	private DefaultTableTypesDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static ITableTypesDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultTableTypesDao() {
	}

	@Override
	public IMdoBean findByUniqueKey(Object[] propertyValues, boolean... isLazy) throws MdoDataBeanException {
		// Checking exception
		super.findByUniqueKey(propertyValues, isLazy);

		// Checking exception
		if (propertyValues.length != 1) {
			super.getLogger().error("message.error.dao.unique.fields.1");
			throw new MdoDataBeanException("message.error.dao.unique.fields.1");
		}

		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("code.name", propertyValues[0]);
		propertyValueMap.put("code.type", MdoTableAsEnumTypeDao.TABLE_TYPE.name());
		propertyValueMap.put("code.deleted", Boolean.FALSE);

		return (IMdoBean) super.findByUniqueKey(propertyValueMap, isLazy);
	}

	@Override
	public IMdoBean findByUniqueKey(String code, boolean... isLazy) throws MdoDataBeanException {
		return super.findByUniqueKey(new Object[] {code}, isLazy);
	}
}
