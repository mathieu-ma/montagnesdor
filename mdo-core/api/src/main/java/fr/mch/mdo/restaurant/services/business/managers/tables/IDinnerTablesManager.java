package fr.mch.mdo.restaurant.services.business.managers.tables;

import java.math.BigDecimal;
import java.util.Map;

import fr.mch.mdo.restaurant.dto.beans.DinnerTableDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;

public interface IDinnerTablesManager extends IAdministrationManager
{
    /**
     *	This method tries to get tables from prefix table name.
     * 
     * @param restaurantId the restaurant id.
     * @param prefixTableName prefix table name to look up
     * @return map with key equals to table id and value equals to table name
     * @throws Exception any exception occurs
     */
    Map<Long, String> findAllTableNamesByPrefix(Long restaurantId, String prefixTableNumber) throws MdoException;

    /**
     *	This method tries to get products from product code prefix
     * 
     * @param restaurantId the restaurant id.
     * @param prefixProductCode prefix table name to look up
     * @return map with key equals to product id and value equals to product
     * @throws Exception any exception occurs
     */
    Map<Long, String> findAllProductCodesByPrefix(Long restaurantId, String prefixProductCode) throws MdoException;
    
    /**
     * This method tries to get a table from table number
     * 
     * @param restaurantId the restaurant id.
     * @param number the table number to look up
     * @return dinner table
     * @throws MdoException any exception occurs
     */
	DinnerTableDto findTableByNumber(Long restaurantId, String number) throws MdoException;

    /**
     * This method tries to know if a table with the specified table number is free.
     * The dinner table with the specified table number is consider as free if the table number doesn't exist in the specified restaurant
     * or the table number exists in the specified restaurant but the cashing date is not null. 
     * 
     * @param restaurantId the restaurant id.
     * @param number the table number to look up.
     * @return true if the table is free.
     * @throws MdoException any exception occurs.
     */
	boolean isDinnerTableFree(Long restaurantId, String number) throws MdoException;

    /**
     * Create a dinner table and prefill some mandatory fields from user context.
     * @param restaurantId the restaurant id.
     * @param dinnerTableNumber the dinner table number.
     * @return the filled dinner table.
     * @throws MdoException when any exception occurs
     */
    Long createFromUserContext(Long userAuthenticationId, Long restaurantId, String[] prefixTakeawayNames, Long tableTypeId,
			String dinnerTableNumber, Integer customersNumber, BigDecimal reductionRatio) throws MdoException;
    
	/**
	 * Update the customers number of the specific table.
	 * @param dinnerTableId the Dinner Table Id.
	 * @param customersNumber the customers number to be updated.
	 */
	void updateCustomersNumber(Long dinnerTableId, Integer customersNumber) throws MdoException;
}
