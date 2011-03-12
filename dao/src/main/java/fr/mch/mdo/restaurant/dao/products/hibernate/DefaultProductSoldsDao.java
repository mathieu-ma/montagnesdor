package fr.mch.mdo.restaurant.dao.products.hibernate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.beans.ProductSold;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.products.IProductSoldsDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultProductSoldsDao extends DefaultDaoServices implements IProductSoldsDao 
{
	private static class LazyHolder {
		private static IProductSoldsDao instance = new DefaultProductSoldsDao(LoggerServiceImpl.getInstance().getLogger(DefaultProductSoldsDao.class.getName()), new ProductSold());
	}

	private DefaultProductSoldsDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static IProductSoldsDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultProductSoldsDao() {
	}

	@Override
	public IMdoBean findByUniqueKey(Object[] propertyValues, boolean... isLazy) throws MdoDataBeanException {
		// Checking exception
		super.findByUniqueKey(propertyValues, isLazy);

		if (propertyValues.length != 2) {
			super.getLogger().error("message.error.dao.unique.fields.2");
			throw new MdoDataBeanException("message.error.dao.unique.fields.2");
		}

		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("soldDate", propertyValues[0]);
		propertyValueMap.put("product.id", propertyValues[1]);

		return (IMdoBean) super.findByUniqueKey(propertyValueMap, isLazy);
	}

	@Override
	public IMdoBean findByUniqueKey(Date soldDate, Long productId) throws MdoDataBeanException {
		return this.findByUniqueKey(new Object[] { soldDate, productId });
	}
}
