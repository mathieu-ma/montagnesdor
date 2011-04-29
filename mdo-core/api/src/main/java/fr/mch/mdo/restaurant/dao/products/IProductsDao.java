package fr.mch.mdo.restaurant.dao.products;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IProductsDao  extends IDaoServices
{
    IMdoBean findByCode(Long restaurantId, String code) throws MdoException;

    IMdoBean findByCode(String restaurantReference, String code) throws MdoException;
    
    List<IMdoBean> findAllByPrefixCode(Long restaurantId, String prefixProductCode) throws MdoException;

    /**
     * Find list of products by restaurant id.
     * @param restaurantId the restaurant id.
     * @return list of products by restaurant id.
     * @throws MdoException when any exception occur.
     */
    List<IMdoBean> findByRestaurant(Long restaurantId) throws MdoException;
}
