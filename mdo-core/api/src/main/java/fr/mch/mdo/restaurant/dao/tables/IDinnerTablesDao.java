package fr.mch.mdo.restaurant.dao.tables;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IDinnerTablesDao extends IDaoServices
{
    /**
     *	This method tries to get tables from table name prefix: only tables without cashing date is taken into account.
     * 
     * @param restaurantId the restaurant id.
     * @param prefixTableName prefix table name to look up.
     * @return map with key equals to table id and value equals to table name.
	 * @param isLazy lazy loading ?
     * @throws MdoException any exception occurs.
     */
    List<DinnerTable> findAllByPrefixNumber(Long restaurantId, String prefixTableNumber, boolean... isLazy) throws MdoException;

    /**
     *	This method tries to get tables from table name prefix: only tables without cashing date is taken into account.
     * 
     * @param restaurantId the restaurant id. Maybe useless because userAuthenticationId already belong to a restaurant.
     * @param userAuthenticationId the user authentication id.
     * @param prefixTableName prefix table name to look up.
     * @return map with key equals to table id and value equals to table name.
	 * @param isLazy lazy loading ?
     * @throws MdoException any exception occurs.
     */
    List<DinnerTable> findAllByPrefixNumber(Long restaurantId, Long userAuthenticationId, String prefixTableNumber, boolean... isLazy) throws MdoException;

    /**
     *	This method tries to get tables from table name prefix: only tables without cashing date is taken into account.
     * 
     * @param restaurantId the restaurant id.
     * @param prefixTableName prefix table name to look up.
     * @return map with key equals to table id and value equals to table name.
     * @throws MdoException any exception occurs.
     */
    Map<Long, String> findAllNumberByPrefixNumber(Long restaurantId, String prefixTableNumber) throws MdoException;

    /**
     *	This method tries to get tables from table name prefix: only tables without cashing date is taken into account.
     * 
     * @param restaurantId the restaurant id. Maybe useless because userAuthenticationId already belong to a restaurant.
     * @param userAuthenticationId the user authentication id.
     * @param prefixTableName prefix table name to look up.
     * @return map with key equals to table id and value equals to table name.
     * @throws MdoException any exception occurs.
     */
    Map<Long, String> findAllNumberByPrefixNumber(Long restaurantId, Long userAuthenticationId, String prefixTableNumber) throws MdoException;

    /**
     * This will find the unique id and customer number and type with dinner table's number. 
     * The Table is not cashed i.e. the cashing date is null.
     * Be aware, only the following fields are filled:
     * 		id, customersNumber.
     * The others remain null.
     * 
     * @param restaurantId the restaurant id.
     * @param number the table number.
     * @return a dinner table fill only with id and the customers number and table's number. The remaining field is left to default values.
     * @throws MdoException any exception occurs.
     */
    DinnerTable findTableHeader(Long restaurantId, String number) throws MdoException;

    /**
     * This will find the unique id and customer number and type with dinner table's number. 
     * The Table is not cashed i.e. the cashing date is null.
     * @param restaurantId the restaurant id. Maybe useless because userAuthenticationId already belong to a restaurant.
     * @param userAuthenticationId the user authentication id.
     * @param number the table number.
     * @return a dinner table fill only with id and the customers number and table's number. The remaining field is left to default values.
     * @throws MdoException any exception occurs.
     */
    DinnerTable findTableHeader(Long restaurantId, Long userAuthenticationId, String number) throws MdoException;

    /**
     * This will find the unique dinner table that is not cashed i.e. the cashing date is null.
     * Be aware, only the following fields are filled:
     * 		id, customersNumber, registrationDate, printingDate, reductionRatio, 
     * 		reductionRatioChanged, amountsSum, quantitiesSum.
     * The others remain null.
     * 
     * @param id the table id.
     * @return a part of dinner table.
     * @throws MdoException any exception occurs.
     */
    DinnerTable findTable(Long id) throws MdoException;
    
    /**
     * This method tries to know if a table with the specified table number is free.
     * The dinner table with the specified table number is consider as free when the table has been cashed.
     * i.e: 
     * if 
     * the table number doesn't exist in the specified restaurant
     * or 
     * the table number exists in the specified restaurant but the cashing date is not null. 
     * 
     * @param restaurantId the restaurant id.
     * @param number the table number to look up
     * @return true if the table is free.
     * @throws MdoException any exception occurs
     */
	boolean isDinnerTableFree(Long restaurantId, String number) throws MdoException;

    void updateReductionRatio(Long dinnerTableId, BigDecimal reductionRatio, Boolean reductionRatioChanged, BigDecimal amountPay) throws MdoException;
    
    /**
     *	This method tries to get tables whose cashing date is not null.
     * 
     * @param restaurantId the restaurant id.
	 * @param isLazy lazy loading ?
     * @return list of tables whose cashing date is not null.
     * @throws MdoException any exception occurs.
     */
    List<DinnerTable> findAllCashed(Long restaurantId, boolean... isLazy) throws MdoException;

    /**
     *	This method tries to get tables whose cashing date is not null.
     * 
     * @param restaurantId the restaurant id. Maybe useless because userAuthenticationId already belong to a restaurant.
     * @param userAuthenticationId the user authentication id.
	 * @param isLazy lazy loading ?
     * @return list of tables whose cashing date is not null.
     * @throws MdoException any exception occurs.
     */
    List<DinnerTable> findAllCashed(Long restaurantId, Long userAuthenticationId, boolean... isLazy) throws MdoException;

    /**
     *	This method tries to get tables whose cashing date is null and printing date is not null.
     * 
     * @param restaurantId the restaurant id.
	 * @param isLazy lazy loading ?
     * @return list of tables whose cashing date is null and printing date is not null.
     * @throws MdoException any exception occurs.
     */
    List<DinnerTable> findAllPrinted(Long restaurantId, boolean... isLazy) throws MdoException;

    /**
     *	This method tries to get tables whose cashing date is null and printing date is not null.
     * 
     * @param restaurantId the restaurant id. Maybe useless because userAuthenticationId already belong to a restaurant.
     * @param userAuthenticationId the user authentication id.
	 * @param isLazy lazy loading ?
     * @return list of tables whose cashing date is null and printing date is not null.
     * @throws MdoException any exception occurs.
     */
    List<DinnerTable> findAllPrinted(Long restaurantId, Long userAuthenticationId, boolean... isLazy) throws MdoException;

    /**
     *	This method tries to get tables whose cashing date is null and printing date is null.
     * 
     * @param restaurantId the restaurant id.
	 * @param isLazy lazy loading ?
     * @return list of tables whose cashing date is null and printing date is null.
     * @throws MdoException any exception occurs.
     */
    List<DinnerTable> findAllNotPrinted(Long restaurantId, boolean... isLazy) throws MdoException;

    /**
     *	This method tries to get tables whose cashing date is null and printing date is null.
     * 
     * @param restaurantId the restaurant id. Maybe useless because userAuthenticationId already belong to a restaurant.
     * @param userAuthenticationId the user authentication id.
	 * @param isLazy lazy loading ?
     * @return list of tables whose cashing date is null and printing date is null.
     * @throws MdoException any exception occurs.
     */
    List<DinnerTable> findAllNotPrinted(Long restaurantId, Long userAuthenticationId, boolean... isLazy) throws MdoException;

    /**
     *	This method tries to get alterable tables, i.e., tables that are not cashed.
     * 
     * @param restaurantId the restaurant id.
	 * @param isLazy lazy loading ?
     * @return list of tables that are not cashed.
     * @throws MdoException any exception occurs.
     */
    List<DinnerTable> findAllAlterable(Long restaurantId, boolean... isLazy) throws MdoException;

    /**
     *	This method tries to get alterable tables, i.e., tables that are not cashed.
     * 
     * @param restaurantId the restaurant id. Maybe useless because userAuthenticationId already belong to a restaurant.
     * @param userAuthenticationId the user authentication id.
	 * @param isLazy lazy loading ?
     * @return list of tables that are not cashed.
     * @throws MdoException any exception occurs.
     */
    List<DinnerTable> findAllAlterable(Long restaurantId, Long userAuthenticationId, boolean... isLazy) throws MdoException;

    /**
	 * Update the customers number of the specific table.
	 * @param dinnerTableId the Dinner Table Id.
	 * @param customersNumber the customers number to be updated.
	 * @throws MdoException any exception occurs.
	 */
    void updateCustomersNumber(Long dinnerTableId, Integer customersNumber) throws MdoException;

	/**
	 * This method updates only fields that are derived from removing or adding an order line. 
	 * @param dinnerTableId the dinner table id.
	 * @param quantitiesSum the number of order lines.
	 * @param amountsSum the amount of all order lines.
	 * @param amountPay the amount to pay depending on amountsSum and reductionRatio.
     * @throws MdoException any exception occurs.
	 */
	void updateDerivedFieldsFromOrderLine(Long dinnerTableId, BigDecimal quantitiesSum, BigDecimal amountsSum, BigDecimal amountPay) throws MdoException;
	
	/**
	 * Get the reduction ratio from the specific table, this can be used for calculating the amount to be payed.
	 * @param dinnerTableId the dinner table id.
	 * @return the reduction ratio.
	 * @throws MdoException any exception occurs.
	 */
	BigDecimal getReductionRatio(Long dinnerTableId) throws MdoException;

	/**
	 * Update the customers number to 0 and the creation date to now.
	 *  
	 * @param dinnerTableId the dinner table id.
	 * @throws MdoException any exception occurs.
	 */
	void resetTableCreationDateCustomersNumber(Long dinnerTableId) throws MdoException;

	/**
	 * Update dinner table.
	 *  
	 * @param table the dinner table.
	 * @throws MdoException any exception occurs.
	 */
	void updateResetTable(DinnerTable table) throws MdoException;
}
