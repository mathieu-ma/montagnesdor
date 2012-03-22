package fr.mch.mdo.restaurant.dao.tables;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.beans.OrderLine;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IDinnerTablesDao extends IDaoServices
{
    List<IMdoBean> findAllByPrefixNumber(Long restaurantId, String prefixTableNumber, boolean... isLazy) throws MdoException;

    /**
     *	This method tries to get tables from table name prefix: only tables without cashing date is taken into account.
     * 
     * @param Long restaurantId the restaurant id.
     * @param prefixTableName prefix table name to look up.
     * @return map with key equals to table id and value equals to table name.
     * @throws MdoException any exception occurs.
     */
    Map<Long, String> findAllTableNamesByPrefix(Long restaurantId, String prefixTableNumber) throws MdoException;

    /**
     * This will find the unique customer number with dinner table, 
     * that is not cashed i.e. the cashing date is null.
     * @param restaurantId the restaurant id.
     * @param number the table number.
     * @return the customers number.
     * @throws MdoException any exception occurs.
     */
    Integer findCustomersNumberByNumber(Long restaurantId, String number) throws MdoException;

    /**
     * This will find the unique dinner table that is not cashed i.e. the cashing date is null.
     * @param restaurantId
     * @param number
     * @param isLazy
     * @return
     * @throws MdoException
     */
    DinnerTable findByNumber(Long restaurantId, String number, boolean... isLazy) throws MdoException;

    DinnerTable displayTableByNumber(Long restaurantId, String number) throws MdoException;

    /**
     * This method tries to know if a table with the specified table number is free.
     * The dinner table with the specified table number is consider as free if the table number doesn't exist in the specified restaurant
     * or the table number exists in the specified restaurant but the cashing date is not null. 
     * 
     * @param restaurantId the restaurant id.
     * @param number the table number to look up
     * @return true if the table is free.
     * @throws MdoException any exception occurs
     */
	boolean isDinnerTableFree(Long restaurantId, String number) throws MdoException;

    void updateReductionRatio(Long dinnerTableId, BigDecimal reductionRatio, Boolean reductionRatioChanged, BigDecimal amountPay) throws MdoException;
    
    void addOrderLine(OrderLine orderLine) throws MdoException;
    
    OrderLine findOrderLine(Long orderLineId) throws MdoException;

    void updateOrderLine(OrderLine orderLine) throws MdoException;

	/**
	 * Delete the specified Order Line.
	 * @param orderLine the Order Line to be deleted.
	 */
    void removeOrderLine(OrderLine orderLine) throws MdoException;
    
    /**
     *	This method tries to get tables whose cashing date is null.
     * 
     * @param restaurantId the restaurant id.
     * @return list of tables whose cashing date is null
     * @throws MdoException any exception occurs
     */
    List<IMdoBean> findAllFreeTables(Long restaurantId) throws MdoException;

    
	/**
	 * Update the customers number of the specific table.
	 * @param dinnerTableId the Dinner Table Id.
	 * @param customersNumber the customers number to be updated.
	 */
    void updateCustomersNumber(Long dinnerTableId, Integer customersNumber) throws MdoException;

    /**
     * Retrieve the size of the table order lines.
     * @param dinnerTableId the dinner table Id.
     * @return the size of the table order lines.
     */
	int getOrderLinesSize(Long dinnerTableId) throws MdoException;

}
