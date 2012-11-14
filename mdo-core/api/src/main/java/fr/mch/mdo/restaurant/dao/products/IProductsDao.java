package fr.mch.mdo.restaurant.dao.products;

import java.util.List;
import java.util.Map;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.ProductLabel;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IProductsDao  extends IDaoServices
{
    List<IMdoBean> findAllByPrefixCode(Long restaurantId, String prefixProductCode) throws MdoException;

    /**
     * This method tries to get products codes from product code prefix
     * 
     * @param userContext user context
     * @param prefixProductCode prefix table name to look up
     * @return map with key equals to product id and value equals to product code
     * @throws MdoException any exception occur
     */
    Map<Long, String> findCodesByPrefixCode(Long restaurantId, String prefixProductCode) throws MdoException;

    /**
     * Find list of products by restaurant id.
     * @param restaurantId the restaurant id.
     * @return list of products by restaurant id.
     * @throws MdoException when any exception occur.
     */
    List<IMdoBean> findAllByRestaurant(Long restaurantId) throws MdoException;

    /**
     * This method tries to get the Product by code.
     * @param restaurantId the restaurant id.
     * @param code the unique code.
     * @return the Product.
     * @throws MdoException when any exception occur.
     */
	IMdoBean find(Long restaurantId, String code) throws MdoException;

	/**
     * This method tries to get the Product by code.
	 * @param restaurantReference the restaurant reference.
     * @param code the unique code.
     * @return the Product.
     * @throws MdoException when any exception occur.
	 */
    IMdoBean find(String restaurantReference, String code) throws MdoException;

    /**
     * This method tries to get the Product by code for specific locale.
     * @param restaurantId the restaurant id.
     * @param code the unique code.
	 * @param locId the locale id.
     * @return the Product.
     * @throws MdoException when any exception occur.
     */
	ProductLabel find(Long restaurantId, String code, Long locId) throws MdoException;
}
