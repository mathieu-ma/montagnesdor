package fr.mch.mdo.restaurant.services.business.managers.products;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;

public interface IProductsManager extends IAdministrationManager
{
	/**
	 * Get list a list of products by restaurant id.
	 * @param restaurantId the restaurant id.
	 * @param userContext the user context.
	 * @return a list of products
	 * @throws MdoException when any exception occur.
	 */
	List<IMdoDtoBean> getList(Long restaurantId, MdoUserContext userContext) throws MdoException;
}

