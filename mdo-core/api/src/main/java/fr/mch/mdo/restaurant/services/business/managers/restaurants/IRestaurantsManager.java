package fr.mch.mdo.restaurant.services.business.managers.restaurants;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;


public interface IRestaurantsManager extends IAdministrationManager
{
	/**
	 * Find a list of restaurants by user identifier. 
	 * @param userId the user identifier.
	 * @return a list of restaurants.
     * @throws MdoException if any exception occur.
	 */
    List<IMdoDtoBean> findRestaurantsByUser(Long userId) throws MdoException;

    /**
     * Find restaurant by reference.
     * @param reference the reference.
     * @return a restaurant
     * @throws MdoException if any exception occur.
     */
    IMdoDtoBean findByReference(String reference) throws MdoException;
//    
//    void addVat(Long restaurantId, Long vatId) throws MdoException;
//
//    void removeVat(Long restaurantVatId) throws MdoException;
}
