package fr.mch.mdo.restaurant.dao.users.hibernate;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.beans.User;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.users.IUsersDao;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultUsersDao extends DefaultDaoServices implements IUsersDao 
{
	private static class LazyHolder {
		private static IUsersDao instance = new DefaultUsersDao(LoggerServiceImpl.getInstance().getLogger(DefaultUsersDao.class.getName()), new User());
	}

	private DefaultUsersDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static IUsersDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultUsersDao() {
	}
}
