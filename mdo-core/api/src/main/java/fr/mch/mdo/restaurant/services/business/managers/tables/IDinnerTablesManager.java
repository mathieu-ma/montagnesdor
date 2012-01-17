package fr.mch.mdo.restaurant.services.business.managers.tables;

import java.util.List;
import java.util.Map;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.DinnerTableDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.OrderLineDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;

public interface IDinnerTablesManager extends IAdministrationManager
{
    /**
     *	This method tries to get tables from prefix table name.
     * 
     * @param userContext user context
     * @param prefixTableName prefix table name to look up
     * @return map with key equals to table id and value equals to table name
     * @throws Exception any exception occurs
     */
    Map<Long, String> findAllTableNamesByPrefix(IMdoBean userContext, String prefixTableNumber) throws MdoException;

    /**
     *	This method tries to get products from product code prefix
     * 
     * @param userContext user context
     * @param prefixProductCode prefix table name to look up
     * @return map with key equals to product id and value equals to product
     * @throws Exception any exception occurs
     */
    Map<Long, String> findAllProductCodesByPrefix(IMdoBean userContext, String prefixProductCode) throws MdoException;
    
    /**
     * This method tries to get a table from table number
     * 
     * @param userContext user context
     * @param number the table number to look up
     * @return dinner table
     * @throws MdoException any exception occurs
     */
	DinnerTableDto findTableByNumber(MdoUserContext userContext, String number) throws MdoException;

    /**
     * This method tries to know if a table with the specified table number is free.
     * The dinner table with the specified table number is consider as free if the table number doesn't exist in the specified restaurant
     * or the table number exists in the specified restaurant but the cashing date is not null. 
     * 
     * @param userContext user context
     * @param number the table number to look up
     * @return true if the table is free.
     * @throws MdoException any exception occurs
     */
	boolean isDinnerTableFree(MdoUserContext userContext, String number) throws MdoException;

    /**
     *	This method tries to get a table from table number
     * 
     * @param userContext user context
     * @param tableNumber the table number
     * @return the number of customer in the table
     * @throws MdoException when any exception occurs
     */
    Integer getCustomersNumberByNumber(IMdoBean userContext, String tableNumber) throws MdoException;
    
    /**
     * Create a dinner table and prefill some mandatory fields from user context.
     * @param userContext the user context.
     * @param dinnerTableNumber the dinner table number.
     * @return the filled dinner table.
     * @throws MdoException when any exception occurs
     */
    Long createFromUserContext(MdoUserContext userContext, String dinnerTableNumber) throws MdoException;
    
    /**
     * Process order line by code.
     * @param userContext the user context.
     * @param orderLine the order line to be filled once the code is found.
     * @param deletedId the deleted Id.
     * @throws Exception any exception occurs.
     */
    void processOrderLineByCode(MdoUserContext userContext, OrderLineDto orderLine, Long deletedId) throws MdoException;
    
    /**
     * Add or update order line.
     * @param userContext the user context.
     * @param orderLine the order line to be filled once the order line is saved.
     * @throws Exception any exception occurs.
     */
	void addOrderLine(MdoUserContext userContext, OrderLineDto orderLine) throws MdoException;

    /**
     * This method tries to get free tables.
     * 
     * @param userContext user context.
     * @return list of free dinner tables.
     * @throws MdoException any exception occurs.
     */
	List<IMdoDtoBean> findAllFreeTables(MdoUserContext userContext) throws MdoException;

	/**
	 * Update the customers number of the specific table.
	 * @param dinnerTableId the Dinner Table Id.
	 * @param customersNumber the customers number to be updated.
	 */
	void updateCustomersNumber(Long dinnerTableId, Integer customersNumber) throws MdoException;

	/**
	 * Delete the specified Order Line.
     * @param userContext user context.
	 * @param orderLine the Order Line to be deleted.
	 */
	void removeOrderLine(MdoUserContext userContext, OrderLineDto orderLine) throws MdoException;

}
