package fr.mch.mdo.restaurant.dao.tables;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface ICashingTypesDao extends IDaoServices
{
	/**
	 * Find by Unique Keys.
	 * @param tableCashingId the table cashing ID.
	 * @param code the code for type of cashing: .
	 * @param order the order.
	 * @param isLazy lazy loading ?
	 * @return a table bill.
	 * @throws MdoException when any exceptions occur. 
	 */
	IMdoBean findByUniqueKey(Long tableCashingId, String code, boolean... isLazy) throws MdoException;
}
