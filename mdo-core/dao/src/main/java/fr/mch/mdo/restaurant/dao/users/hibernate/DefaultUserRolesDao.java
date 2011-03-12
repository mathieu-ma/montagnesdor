package fr.mch.mdo.restaurant.dao.users.hibernate;

import java.util.HashMap;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.MdoTableAsEnumTypeDao;
import fr.mch.mdo.restaurant.dao.beans.UserRole;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.users.IUserRolesDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultUserRolesDao extends DefaultDaoServices implements IUserRolesDao 
{
	private static class LazyHolder {
		private static IUserRolesDao instance = new DefaultUserRolesDao(LoggerServiceImpl.getInstance().getLogger(DefaultUserRolesDao.class.getName()), new UserRole());
	}

	private DefaultUserRolesDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static IUserRolesDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultUserRolesDao() {
	}

	@Override
	public IMdoBean findByUniqueKey(Object propertyValue, boolean... isLazy) throws MdoDataBeanException {
		// Checking exception
		super.findByUniqueKey(propertyValue, isLazy);

		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("code.name", propertyValue);
		propertyValueMap.put("code.type", MdoTableAsEnumTypeDao.USER_ROLE.name());
		propertyValueMap.put("code.deleted", Boolean.FALSE);

		return (IMdoBean) super.findByUniqueKey(propertyValueMap, isLazy);
	}
}
