package fr.mch.mdo.restaurant.business.managers.tables.orders;

import java.util.Map;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.business.managers.IMdoManager;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dto.beans.DinnerTableDtoBean;
import fr.mch.mdo.restaurant.dto.beans.OrderLineDtoBean;
import fr.mch.mdo.restaurant.dto.beans.ProductDtoBean;

public interface ITablesOrdersManager extends IMdoManager
{

    /**
     *	This method tries to get tables from table name prefix
     * 
     * @param userContext user context
     * @param prefixTableName prefix table name to look up
     * @return map with key equals to table id and value equals to table name
     * @throws Exception any exception occurs
     */
    Map<Long, String> lookupTablesNamesByPrefix(IMdoBean userContext, String prefixTableNumber) throws Exception;

    /**
     *	This method tries to get products from product code prefix
     * 
     * @param userContext user context
     * @param prefixProductCode prefix table name to look up
     * @return map with key equals to product id and value equals to product
     * @throws Exception any exception occurs
     */
    Map<Long, String> lookupProductsCodesByPrefix(IMdoBean userContext, String prefixProductCode) throws Exception;
    
    /**
     *	This method tries to get a table from table number
     * 
     * @param userContext user context
     * @param tableNumber the table number to look up
     * @return dinner table
     * @throws Exception any exception occurs
     */
    DinnerTableDtoBean getTableByTableNumber(IMdoBean userContext, String tableNumber) throws Exception;

    /**
     *	This method tries to get a table from table number
     * 
     * @param userContext user context
     * @param tableNumber the table number
     * @return the number of customer in the table
     * @throws Exception any exception occurs
     */
    Integer getCustomersNumberByTableNumber(IMdoBean userContext, String tableNumber) throws Exception;

    /**
     * Try to get the product by code and save the order line if need it
     * @param userContext the user context
     * @param code the product code
     * @return a product
     * @throws Exception any exception occurs
     */
    //void processOrderLine(IMdoBean userContext, OrderLineDtoBean orderLine) throws Exception;
    
    
    //void saveOrderLine(IMdoBean userContext, OrderLineDtoBean orderLine) throws Exception;
}
