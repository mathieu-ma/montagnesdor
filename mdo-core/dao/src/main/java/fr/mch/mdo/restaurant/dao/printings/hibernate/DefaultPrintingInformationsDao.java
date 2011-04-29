package fr.mch.mdo.restaurant.dao.printings.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.MdoEntry;
import fr.mch.mdo.restaurant.dao.beans.PrintingInformation;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.printings.IPrintingInformationsDao;
import fr.mch.mdo.restaurant.exception.MdoException;
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

	@Override
	@SuppressWarnings("unchecked")
	public List<IMdoBean> findByRestaurant(Long restaurantId) throws MdoException {
		List<IMdoBean> result = new ArrayList<IMdoBean>();
		Map<String, Entry<PropertiesRestrictions, Object>> propertyValueMap = new HashMap<String, Entry<PropertiesRestrictions, Object>>();
		String property = "restaurant.id";
		Entry<PropertiesRestrictions, Object> value = new MdoEntry<PropertiesRestrictions, Object>(PropertiesRestrictions.EQUALS, restaurantId);
		propertyValueMap.put(property, value);
		result = super.findByPropertiesRestrictions(propertyValueMap, false);
		return result;
	}
}
