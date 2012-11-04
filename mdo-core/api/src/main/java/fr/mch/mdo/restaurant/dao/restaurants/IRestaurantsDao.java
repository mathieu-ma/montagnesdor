package fr.mch.mdo.restaurant.dao.restaurants;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.TableType;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IRestaurantsDao extends IDaoServices
{
    IMdoBean findByReference(String reference) throws MdoException;
    
    List<IMdoBean> findRestaurants(Long userId, boolean... isLazy) throws MdoException;

	TableType getDefaultTableType(Long restaurantId) throws MdoException;
}
