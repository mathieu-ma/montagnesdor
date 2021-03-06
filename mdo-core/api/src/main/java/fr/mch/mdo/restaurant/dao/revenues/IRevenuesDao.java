package fr.mch.mdo.restaurant.dao.revenues;

import java.util.Date;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.exception.MdoException;


public interface IRevenuesDao extends IDaoServices
{
	/**
	 * Find by unique key
	 * @param restaurantId the restaurant ID.
	 * @param revenueDate the revenue date.
	 * @param type the type of table. Could be TAKE_AWAY, EAT_IN.
	 * @param isLazy lazy loading ?
	 * @return the revenue.
	 * @throws MdoException when any exceptions occur.
	 */
    IMdoBean findByUniqueKey(Long restaurantId, Date revenueDate, String type, boolean... isLazy) throws MdoException;
}
