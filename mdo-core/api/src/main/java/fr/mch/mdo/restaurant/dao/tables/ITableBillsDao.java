package fr.mch.mdo.restaurant.dao.tables;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface ITableBillsDao extends IDaoServices
{
	/**
	 * Find by Unique Keys.
	 * @param dinnerTableId the dinner table ID.
	 * @param reference the reference.
	 * @param order the order.
	 * @param isLazy lazy loading ?
	 * @return a table bill.
	 * @throws MdoException when any exceptions occur. 
	 */
	IMdoBean findByUniqueKey(Long dinnerTableId, String reference, Integer order, boolean... isLazy) throws MdoException;
}
