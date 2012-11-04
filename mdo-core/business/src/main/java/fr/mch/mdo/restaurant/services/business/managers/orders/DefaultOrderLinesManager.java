package fr.mch.mdo.restaurant.services.business.managers.orders;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.orders.hibernate.DefaultOrderLinesDao;
import fr.mch.mdo.restaurant.services.business.managers.AbstractAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.assembler.DefaultOrderLinesAssembler;
import fr.mch.mdo.restaurant.services.business.managers.tables.IOrderLinesManager;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultOrderLinesManager extends AbstractAdministrationManager implements IOrderLinesManager
{
	private static class LazyHolder {
		private static IOrderLinesManager instance = new DefaultOrderLinesManager(
				LoggerServiceImpl.getInstance().getLogger(DefaultOrderLinesManager.class.getName()),
				DefaultOrderLinesDao.getInstance(), DefaultOrderLinesAssembler.getInstance());
	}

	private DefaultOrderLinesManager(ILogger logger, IDaoServices dao, IManagerAssembler assembler) {
		super.logger = logger;
		super.dao = dao;
		super.assembler = assembler;
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultOrderLinesManager() {
	}

	public static IOrderLinesManager getInstance() {
		return LazyHolder.instance;
	}
}
