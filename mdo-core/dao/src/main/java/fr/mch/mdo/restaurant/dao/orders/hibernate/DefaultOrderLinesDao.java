package fr.mch.mdo.restaurant.dao.orders.hibernate;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.beans.OrderLine;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.orders.IOrderLinesDao;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultOrderLinesDao extends DefaultDaoServices implements IOrderLinesDao 
{
	private static class LazyHolder {
		private static IOrderLinesDao instance = new DefaultOrderLinesDao(LoggerServiceImpl.getInstance().getLogger(DefaultOrderLinesDao.class.getName()), new OrderLine());
	}

	private DefaultOrderLinesDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static IOrderLinesDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultOrderLinesDao() {
	}
}
