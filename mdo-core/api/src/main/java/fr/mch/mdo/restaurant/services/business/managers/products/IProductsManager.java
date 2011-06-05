package fr.mch.mdo.restaurant.services.business.managers.products;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.ProductDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;

public interface IProductsManager extends IAdministrationManager
{
	/** Import Data File Name Pattern */
	String IMPORT_DATA_FILE_NAME_PATTERN = ".+-(\\w+)-(\\w\\w)\\.ods";

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
	 * @param file that contains data to be stored.
	 * @param userContext the user context.
	 * @throws MdoException when any exception occur.
	 */
	void importData(File file, MdoUserContext userContext) throws MdoException;

	/**
	 * Export products of specific restaurant from database.
	 * @param out the output stream.
	 * @param restaurantReference the specific restaurant reference.
	 * @param userContext the user context.
	 * @return the file that contains data.
	 * @throws MdoException when any exception occur.
	 */
	void exportData(OutputStream out, String restaurantReference, String[] headers, MdoUserContext userContext) throws MdoException;
}

