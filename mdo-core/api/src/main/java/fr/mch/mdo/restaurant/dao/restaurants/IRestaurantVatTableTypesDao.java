package fr.mch.mdo.restaurant.dao.restaurants;

import java.util.List;

import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.RestaurantVatTableType;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IRestaurantVatTableTypesDao extends IDaoServices
{
	List<RestaurantVatTableType> findAll(Long restaurantId) throws MdoException; 

	List<RestaurantVatTableType> findAll(Long restaurantId, String typeName) throws MdoException;

	List<RestaurantVatTableType> findOnlyVats(Long restaurantId) throws MdoException;
}
