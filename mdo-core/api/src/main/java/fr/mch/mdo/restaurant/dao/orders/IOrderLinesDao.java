package fr.mch.mdo.restaurant.dao.orders;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.OrderLine;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IOrderLinesDao extends IDaoServices
{
	/**
	 * Get the minimum information of Order Line specified by the id.
	 * Be aware, only the following fields are filled:
     * 		quantity, label, unitPrice, amount.
     * The others remain null.
	 * 
	 * @param id the Order Line Id.
	 * @return the minimum information of Order Line specified by the id.
	 * @@throws MdoException any exception occur
	 */
	IMdoBean getOrderLine(Long id) throws MdoException;
	
    /**
     * Retrieve the size of the table order lines.
     * @param dinnerTableId the dinner table Id.
     * @return the size of the table order lines.
     * @throws MdoException any exception occurs.
     */
	int getOrderLinesSize(Long dinnerTableId) throws MdoException;

	/**
	 * Find all order lines by dinner table id with specific language.
	 * 
	 * Be aware, for each order line, only the following fields are filled:
     * 		id, quantity, label, unitPrice, amount, product.code, productSpecialCode.shortCode, productSpecialCode.code.name.
     * The others remain null.
     * Notes: if the product is not null then the returned label is the product label else the label is the order line label.
	 * 
	 * @param id the dinner table id.
     * @param localeId the language(ISO code) to use for order line label.
	 * @return a list of order lines.
     * @throws MdoException any exception occurs.
	 */
	List<OrderLine> findAllScalarFieldsByDinnerTableId(Long dinnerTableId, Long localeId) throws MdoException;

}
