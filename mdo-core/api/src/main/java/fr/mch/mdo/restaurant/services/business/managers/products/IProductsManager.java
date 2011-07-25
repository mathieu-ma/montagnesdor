package fr.mch.mdo.restaurant.services.business.managers.products;

import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.ProductDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;

public interface IProductsManager extends IAdministrationManager
{
	/** Export Data File Name */
	String EXTENSION_EXPORT_IMPORT_DATA_FILE_NAME = "ods";
	/** Import Data File Name Pattern */
	String IMPORT_DATA_FILE_NAME_PATTERN = ".+-(\\w+)-(\\w\\w)\\." + EXTENSION_EXPORT_IMPORT_DATA_FILE_NAME;
	/** Export Data File Name */
	String PREFIX_EXPORT_DATA_FILE_NAME = "export-product";

	/**
	 * Get list a list of products by restaurant id.
	 * @param restaurantId the restaurant id.
	 * @param userContext the user context.
	 * @return a list of products
	 * @throws MdoException when any exception occur.
	 */
	List<IMdoDtoBean> getList(Long restaurantId, MdoUserContext userContext) throws MdoException;
	
	
	/**
	 * Find a product from the restaurant reference and the product code.
	 * @param restaurantReference the restaurant reference.
	 * @param code the product code.
	 * @param userContext the user context.
	 * @return the found product.
	 * @throws MdoException when any exception occur.
	 */
	ProductDto findByCode(String restaurantReference, String code, MdoUserContext userContext) throws MdoException;
	
	/**
	 * Import data into database.
	 * @param importedFileName the imported file name.
	 * @param file that contains data to be stored.
	 * @param userContext the user context.
	 * @throws MdoException when any exception occur.
	 */
	void importData(String importedFileName, File file, MdoUserContext userContext) throws MdoException;

	/**
	 * Export products of specific restaurant from database.
	 * @param out the output stream.
	 * @param restaurantReference the specific restaurant reference.
	 * @param headers the export headers.
	 * @param userContext the user context.
	 * @return the generated file name.
	 * @throws MdoException when any exception occur.
	 */
	String exportData(OutputStream out, String restaurantReference, String[] headers, MdoUserContext userContext) throws MdoException;

    /**
     * This method tries to get products from product code prefix
     * 
     * @param userContext user context
     * @param prefixProductCode prefix table name to look up
     * @return map with key equals to product id and value equals to product
     * @throws Exception any exception occurs
     */
    Map<Long, String> lookupProductsCodesByPrefixCode(IMdoBean userContext, String prefixProductCode) throws Exception;

}

