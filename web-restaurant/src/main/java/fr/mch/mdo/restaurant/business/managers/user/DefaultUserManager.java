package fr.mch.mdo.restaurant.business.managers.user;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.business.managers.AbstractRestaurantManager;
import fr.mch.mdo.restaurant.dao.user.IUserDao;
import fr.mch.mdo.restaurant.dao.user.hibernate.DefaultUserDao;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;


public class DefaultUserManager extends AbstractRestaurantManager implements IUserManager
{
    private static IUserManager instance = null;

    public static IUserManager getInstance()
    {
	if (instance == null)
	{
	    synchronized (DefaultUserManager.class)
	    {
		if (instance == null)
		{
		    instance = new DefaultUserManager();
		    instance.setLogger(LoggerServiceImpl.getInstance().getLogger(DefaultUserManager.class.getName()));

		    instance.setDao(DefaultUserDao.getInstance());
		}
	    }
	}

	return instance;
    }

    @Override
    public void savePassword(String levelPassword, String newPassword, IMdoBean userContext) throws Exception
    {
	IUserDao iDao = (IUserDao) dao;
	MdoUserContext userContextX = (MdoUserContext)userContext;
	iDao.savePassword(levelPassword, newPassword, userContextX.getUserAuthentication().getId());
    }
}
