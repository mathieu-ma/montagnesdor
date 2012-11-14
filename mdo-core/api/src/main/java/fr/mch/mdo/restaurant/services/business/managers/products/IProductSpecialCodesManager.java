package fr.mch.mdo.restaurant.services.business.managers.products;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;

public interface IProductSpecialCodesManager extends IAdministrationManager
{
     /**
     * Get the list of ProductSpecialCodes by restaurant Id. 
     * @param restaurantId the restaurant Id.
     * @param userContext the user context.
     * @return list of ProductSpecialCodes.
     * @throws MdoException when any exceptions occur.
     */
	List<IMdoDtoBean> getList(Long restaurantId) throws MdoException;
}

