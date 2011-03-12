package fr.mch.mdo.restaurant.services.business.managers.restaurants;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;


public interface IRestaurantPrefixTablesManager extends IAdministrationManager
{
	List<IMdoDtoBean> findAll(MdoUserContext userContext, Long restaurantId) throws MdoException;
	
	List<IMdoDtoBean> findAll(MdoUserContext userContext, Long restaurantId, String typeName) throws MdoException;
}
