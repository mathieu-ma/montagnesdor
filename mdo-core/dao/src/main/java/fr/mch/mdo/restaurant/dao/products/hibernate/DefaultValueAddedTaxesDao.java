package fr.mch.mdo.restaurant.dao.products.hibernate;

import java.util.HashMap;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.MdoTableAsEnumTypeDao;
import fr.mch.mdo.restaurant.dao.beans.ValueAddedTax;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.products.IValueAddedTaxesDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultValueAddedTaxesDao extends DefaultDaoServices implements IValueAddedTaxesDao 
{
	private static class LazyHolder {
		private static IValueAddedTaxesDao instance = new DefaultValueAddedTaxesDao(LoggerServiceImpl.getInstance().getLogger(DefaultValueAddedTaxesDao.class.getName()),
				new ValueAddedTax());
	}

	private DefaultValueAddedTaxesDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static IValueAddedTaxesDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultValueAddedTaxesDao() {
	}

	@Override
	public IMdoBean findByUniqueKey(Object propertyValue, boolean... isLazy) throws MdoDataBeanException {
		// Checking exception
		super.findByUniqueKey(propertyValue, isLazy);

		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("code.name", propertyValue);
		propertyValueMap.put("code.type", MdoTableAsEnumTypeDao.VALUE_ADDED_TAX.name());
		propertyValueMap.put("code.deleted", Boolean.FALSE);

		return (IMdoBean) super.findByUniqueKey(propertyValueMap, isLazy);
	}

}
