package fr.mch.mdo.restaurant.dao.revenues;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.exception.MdoException;


public interface IRevenueCashingsDao extends IDaoServices
{
	/**
	 * Find by unique key
	 * @param revenueId the restaurant ID.
	 * @param type the type cashing.
	 * @param isLazy lazy loading ?
	 * @return the revenue.
	 * @throws MdoException when any exceptions occur.
	 */
    IMdoBean findByUniqueKey(Long revenueId, String type, boolean... isLazy) throws MdoException;
}
