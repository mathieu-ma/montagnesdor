package fr.mch.mdo.restaurant.dao.restaurants.hibernate;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.beans.PrintingInformation;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.restaurants.IPrintingInformationsDao;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultPrintingInformationsDao extends DefaultDaoServices implements IPrintingInformationsDao
{
    private static class LazyHolder 
    {
	private static IPrintingInformationsDao instance = new DefaultPrintingInformationsDao(
		LoggerServiceImpl.getInstance().getLogger(DefaultPrintingInformationsDao.class.getName()), 
		new PrintingInformation());
    }

    private DefaultPrintingInformationsDao(ILogger logger, IMdoDaoBean bean) {
	super(true);
	this.setLogger(logger);
	this.setBean(bean);
    }
    
    public static IPrintingInformationsDao getInstance() {
	return LazyHolder.instance;
    }

    public DefaultPrintingInformationsDao() {
    }
}
