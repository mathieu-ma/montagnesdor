package fr.mch.mdo.restaurant.dao.tables;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface ICreditsDao extends IDaoServices
{
    /**
	 * Find by Unique Keys.
     * @param restaurantId the restaurant ID.
     * @param reference the reference.
     * @return the credit.
	 * @throws MdoException when any exceptions occur. 
     */
	IMdoBean findByUniqueKey(Long restaurantId, String reference) throws MdoException;
}
