package fr.mch.mdo.restaurant.services.business.managers;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.dao.IDao;
import fr.mch.mdo.restaurant.dao.IDaoServices;

public class AbstractRestaurantManager implements IMdoManager 
{
	protected ILogger logger;
	protected IDaoServices dao;

	@Override
	public ILogger getLogger() {
		return logger;
	}

	@Override
	public void setLogger(ILogger logger) {
		this.logger = logger;
	}

	@Override
	public IDao getDao() {
		return dao;
	}

	@Override
	public void setDao(IDao dao) {
		this.dao = (IDaoServices) dao;
	}
}
