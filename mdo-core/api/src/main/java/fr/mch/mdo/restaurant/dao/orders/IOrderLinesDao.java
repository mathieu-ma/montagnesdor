package fr.mch.mdo.restaurant.dao.orders;

import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.OrderLine;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IOrderLinesDao extends IDaoServices
{
	/**
	 * Get the minimum information of Order Line specified by the id. 
	 * @param id the Order Line Id.
	 * @return the minimum information of Order Line specified by the id.
	 * @@throws MdoException any exception occur
	 */
	OrderLine getOrderLine(Long id) throws MdoException;
}
