package fr.mch.mdo.restaurant.dao.revenues;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.exception.MdoException;


public interface IRevenueVatsDao extends IDaoServices
{
	/**
	 * Find by unique key
	 * @param revenueId the restaurant ID.
	 * @param vat the type vat. Could be ALCOHOL, DEFAULT.
	 * @param isLazy lazy loading ?
	 * @return the revenue.
	 * @throws MdoException when any exceptions occur.
	 */
    IMdoBean findByUniqueKey(Long revenueId, String vat, boolean... isLazy) throws MdoException;
}
