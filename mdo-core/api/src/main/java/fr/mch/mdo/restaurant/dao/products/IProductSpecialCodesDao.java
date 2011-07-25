package fr.mch.mdo.restaurant.dao.products;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IProductSpecialCodesDao extends IDaoServices
{
    List<IMdoBean> findProductSpecialCodesByRestaurant(Long restaurantId) throws MdoException;
    
    IMdoBean findByShortCode(Long restaurantId, String shortCode) throws MdoException;

    IMdoBean findByCodeName(Long restaurantId, String codeName) throws MdoException;
}
