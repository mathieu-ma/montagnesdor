package fr.mch.mdo.restaurant.business.managers;

import fr.mch.mdo.logs.ILogger;

import fr.mch.mdo.restaurant.dao.IDao;
import fr.mch.mdo.restaurant.dao.IDaoServices;

public abstract class AbstractRestaurantManager implements IRestaurantManager
{
    protected ILogger logger;
    protected IDaoServices dao;

    public ILogger getLogger()
    {
	return logger;
    }

    public void setLogger(ILogger logger)
    {
	this.logger = logger;
    }

    public IDao getDao()
    {
	return dao;
    }

    public void setDao(IDao dao)
    {
	this.dao = (IDaoServices) dao;
    }
 }