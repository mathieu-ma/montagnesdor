package fr.mch.mdo.restaurant.dao.products.hibernate;

import java.util.HashMap;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.MdoTableAsEnumTypeDao;
import fr.mch.mdo.restaurant.dao.beans.Category;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.products.ICategoriesDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultCategoriesDao extends DefaultDaoServices implements ICategoriesDao 
{
	private static class LazyHolder {
		private static ICategoriesDao instance = new DefaultCategoriesDao(LoggerServiceImpl.getInstance().getLogger(DefaultCategoriesDao.class.getName()), new Category());
	}

	private DefaultCategoriesDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static ICategoriesDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultCategoriesDao() {
	}

	@Override
	public IMdoBean findByUniqueKey(Object propertyValue, boolean... isLazy) throws MdoDataBeanException {
		// Checking exception
		super.findByUniqueKey(propertyValue, isLazy);

		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("code.name", propertyValue);
		propertyValueMap.put("code.type", MdoTableAsEnumTypeDao.CATEGORY.name());
		propertyValueMap.put("code.deleted", Boolean.FALSE);

		return (IMdoBean) super.findByUniqueKey(propertyValueMap, isLazy);
	}
}
