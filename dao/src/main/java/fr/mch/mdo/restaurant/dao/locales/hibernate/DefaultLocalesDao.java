package fr.mch.mdo.restaurant.dao.locales.hibernate;

import java.util.HashMap;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.beans.Locale;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.locales.ILocalesDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultLocalesDao extends DefaultDaoServices implements ILocalesDao 
{
	private static class LazyHolder {
		private static ILocalesDao instance = new DefaultLocalesDao(LoggerServiceImpl.getInstance().getLogger(DefaultLocalesDao.class.getName()), new Locale());
	}

	private DefaultLocalesDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static ILocalesDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultLocalesDao() {
	}

	@Override
	public IMdoBean findByUniqueKey(Object propertyValue, boolean... isLazy) throws MdoDataBeanException {
		super.findByUniqueKey(propertyValue, isLazy);
		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("language", propertyValue);
		return (IMdoBean) super.findByUniqueKey(propertyValueMap, isLazy);
	}
}
