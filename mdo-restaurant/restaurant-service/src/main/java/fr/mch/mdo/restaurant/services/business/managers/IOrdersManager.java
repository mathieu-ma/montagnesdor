package fr.mch.mdo.restaurant.services.business.managers;

import java.util.List;
import java.util.Locale;

import fr.mch.mdo.restaurant.beans.dto.DinnerTableDto;
import fr.mch.mdo.restaurant.beans.dto.OrderLineDto;
import fr.mch.mdo.restaurant.beans.dto.ProductSpecialCodeDto;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IOrdersManager 
{
	List<DinnerTableDto> findAllTables(Long restaurantId, TableState state) throws MdoException;

	List<DinnerTableDto> findAllTables(Long restaurantId, Long userAuthenticationId, TableState state) throws MdoException;

	List<DinnerTableDto> findAllAlterableTables(Long restaurantId) throws MdoException;

	List<DinnerTableDto> findAllAlterableTables(Long restaurantId, Long userAuthenticationId) throws MdoException;

	void deleteTable(Long id) throws MdoException;
	
	OrderLineDto getOrderLine(Long restaurantId, String orderCode) throws MdoException;

	void deleteOrderLine(Long id) throws MdoException;

	DinnerTableDto findTableHeader(Long restaurantId, String number) throws MdoException;
	
	DinnerTableDto findTableHeader(Long restaurantId, Long userAuthenticationId, String number) throws MdoException;

	DinnerTableDto findTable(Long id, Locale locale) throws MdoException;

	/**
	 * 
	 * @param restaurantId
	 * @param userAuthenticationId
	 * @param number
	 * @param customersNumber
	 * @return the created DinnerTableDto.
	 * @throws MdoException 
	 */
	DinnerTableDto createTable(Long restaurantId, Long userAuthenticationId, String number, Integer customersNumber) throws MdoException;

	Integer getTableOrdersSize(Long dinnerTableId) throws MdoException;

	void updateTableCustomersNumber(Long dinnerTableId, Integer customersNumber) throws MdoException;

	void resetTableCreationDateCustomersNumber(Long dinnerTableId) throws MdoException;

	void resetTable(Long dinnerTableId, Long restaurantId, Long userAuthenticationId, String number, Integer customersNumber) throws MdoException;

	ProductSpecialCodeDto getProductSpecialCode(Long restaurantId, String productSpecialShortCode) throws MdoException;
}
