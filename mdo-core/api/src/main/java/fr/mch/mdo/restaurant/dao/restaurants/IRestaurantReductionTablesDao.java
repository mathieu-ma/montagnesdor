package fr.mch.mdo.restaurant.dao.restaurants;

import java.util.List;

import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.RestaurantReductionTable;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IRestaurantReductionTablesDao extends IDaoServices
{
	List<RestaurantReductionTable> findAll(Long restaurantId) throws MdoException; 

	List<RestaurantReductionTable> findAll(Long restaurantId, String typeName) throws MdoException;

	List<RestaurantReductionTable> findOnlyReductionTables(Long restaurantId) throws MdoException;

	RestaurantReductionTable findReductionTable(Long restaurantId, Long typeId) throws MdoException;
}
