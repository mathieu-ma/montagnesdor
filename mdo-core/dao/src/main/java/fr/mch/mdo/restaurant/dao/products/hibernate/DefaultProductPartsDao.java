package fr.mch.mdo.restaurant.dao.products.hibernate;

import java.util.HashMap;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.MdoTableAsEnumTypeDao;
import fr.mch.mdo.restaurant.dao.beans.ProductPart;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.products.IProductPartsDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultProductPartsDao extends DefaultDaoServices implements IProductPartsDao 
{
	private static class LazyHolder {
		private static IProductPartsDao instance = new DefaultProductPartsDao(LoggerServiceImpl.getInstance().getLogger(DefaultProductPartsDao.class.getName()), new ProductPart());
	}

	private DefaultProductPartsDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static IProductPartsDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultProductPartsDao() {
	}

	@Override
	public IMdoBean findByUniqueKey(Object propertyValue, boolean... isLazy) throws MdoDataBeanException {
		// Checking exception
		super.findByUniqueKey(propertyValue, isLazy);

		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("code.name", propertyValue);
		propertyValueMap.put("code.type", MdoTableAsEnumTypeDao.PRODUCT_PART.name());
		propertyValueMap.put("code.deleted", Boolean.FALSE);

		return (IMdoBean) super.findByUniqueKey(propertyValueMap, isLazy);
	}

}
