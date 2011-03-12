package fr.mch.mdo.restaurant.dao.restaurants;

import java.util.List;

import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.RestaurantPrefixTable;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IRestaurantPrefixTablesDao extends IDaoServices
{
	List<RestaurantPrefixTable> findAll(Long restaurantId) throws MdoException; 

	List<RestaurantPrefixTable> findAll(Long restaurantId, String typeName) throws MdoException; 
}
