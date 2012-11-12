package fr.mch.mdo.restaurant.dao.products;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.ProductSpecialCode;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IProductSpecialCodesDao extends IDaoServices
{
    List<IMdoBean> findProductSpecialCodesByRestaurant(Long restaurantId) throws MdoException;
    
    IMdoBean findByShortCode(Long restaurantId, String shortCode) throws MdoException;

    /**
     * This method is used to find the ProductSpecialCode bean by the specific code name and restaurant id.
     * 
     * @param restaurantId the restaurant id.
     * @param codeName the code name.
     * @return the ProductSpecialCode bean.
     * @throws MdoException when any exceptions occur
     */
    IMdoBean findByCodeName(Long restaurantId, String codeName) throws MdoException;

    /**
     * This method is used to get only the id of the ProductSpecialCode bean by the specific code name and restaurant id.
     * 
     * @param restaurantId the restaurant id.
     * @param codeName the code name.
     * @return the id
     * @throws MdoException when any exceptions occur
     */
	Long getIdByCodeName(Long restaurantId, String codeName) throws MdoException;

	/**
	 * Find the list of ProductSpecialCodes by restaurant Id.
	 * 
	 * @param restaurantId the restaurant id.
	 * @return list of ProductSpecialCodes.
     * @throws MdoException when any exceptions occur
	 */
	List<ProductSpecialCode> findAllByRestaurant(Long restaurantId) throws MdoException;
}
