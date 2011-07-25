package fr.mch.mdo.restaurant.dao.tables;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface ITableCashingsDao extends IDaoServices
{
	/**
	 * Find by Unique Keys.
	 * @param dinnerTableId the dinner table ID.
	 * @param order the order.
	 * @param isLazy lazy loading ?
	 * @return a table bill.
	 * @throws MdoException when any exceptions occur. 
	 */
	IMdoBean findByUniqueKey(Long dinnerTableId, boolean... isLazy) throws MdoException;
}
