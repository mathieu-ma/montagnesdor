package fr.mch.mdo.restaurant.services.business.managers.restaurants;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;


public interface IRestaurantsManager extends IAdministrationManager
{
    List<IMdoDtoBean> findRestaurantsByUser(Long userId, MdoUserContext userContext) throws MdoException;
//    
//    void addVat(Long restaurantId, Long vatId, MdoUserContext userContext) throws MdoException;
//
//    void removeVat(Long restaurantVatId, MdoUserContext userContext) throws MdoException;
}
