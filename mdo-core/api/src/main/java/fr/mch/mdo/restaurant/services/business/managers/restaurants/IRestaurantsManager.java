package fr.mch.mdo.restaurant.services.business.managers.restaurants;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;


public interface IRestaurantsManager extends IAdministrationManager
{
	/**
	 * Find a list of restaurants by user identifier. 
	 * @param userId the user identifier.
     * @param userContext the user context.
	 * @return a list of restaurants.
     * @throws MdoException if any exception occur.
	 */
    List<IMdoDtoBean> findRestaurantsByUser(Long userId, MdoUserContext userContext) throws MdoException;

    /**
     * Find restaurant by reference.
     * @param reference the reference.
     * @param userContext the user context.
     * @return a restaurant
     * @throws MdoException if any exception occur.
     */
    IMdoDtoBean findByReference(String reference, MdoUserContext userContext) throws MdoException;
//    
//    void addVat(Long restaurantId, Long vatId, MdoUserContext userContext) throws MdoException;
//
//    void removeVat(Long restaurantVatId, MdoUserContext userContext) throws MdoException;
}
