package fr.mch.mdo.restaurant.dao;

import java.util.List;

import fr.mch.mdo.restaurant.dao.beans.MdoString;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IMdoTableAsEnumsDao extends IDaoServices 
{
	public enum TableTypeName {
		TAKE_AWAY, EAT_IN
	}

	public enum TableCashingTypeName {
		GENERIC_CASH, EURO_CASH, DOLLAR_CASH, GENERIC_TICKET, MEAL_TICKET, HOLIDAYS_TICKET, GENERIC_CHECK, BNP_CHECK, GENERIC_CARD, VISA_CARD, MASTER_CARD, UNPAID
	}

	/**
	 * Find all types.
	 * @return list of types.
	 * @throws MdoException when an exception occurs.
	 */
	List<MdoString> findAllTypes() throws MdoException;
	
	/**
	 * This method gets a list of beans by type.
	 * 
	 * @return list of beans depends on type.
	 * @throws MdoException when an exception occurs.
	 */
	List<MdoTableAsEnum> getBeans(String type) throws MdoException;

	/**
	 * This method gets a list of specific rounds.
	 * 
	 * @return list of specific rounds.
	 * @throws MdoException when an exception occurs.
	 */
	List<MdoTableAsEnum> getSpecificRounds() throws MdoException;

	/**
	 * This method gets a list of table types.
	 * 
	 * @return list of table types.
	 * @throws MdoException when an exception occurs.
	 */
	List<MdoTableAsEnum> getTableTypes() throws MdoException;

	/**
	 * This method gets a list of Restaurant prefix take-away names.
	 * 
	 * @return list of Restaurant prefix take-away names.
	 * @throws MdoException
	 *             when an exception occurs.
	 */
	List<MdoTableAsEnum> getRestaurantPrefixTakeawayNames() throws MdoException;

	/**
	 * This method gets a list of Restaurant printing information alignments.
	 * 
	 * @return list of Restaurant printing information alignments.
	 * @throws MdoException
	 *             when an exception occurs.
	 */
	List<MdoTableAsEnum> getPrintingInformationAlignments() throws MdoException;

	/**
	 * This method gets a list of Restaurant printing information sizes.
	 * 
	 * @return list of Restaurant printing information sizes.
	 * @throws MdoException
	 *             when an exception occurs.
	 */
	List<MdoTableAsEnum> getPrintingInformationSizes() throws MdoException;

	/**
	 * This method gets a list of Restaurant printing information parts.
	 * 
	 * @return list of Restaurant printing information parts.
	 * @throws MdoException
	 *             when an exception occurs.
	 */
	List<MdoTableAsEnum> getPrintingInformationParts() throws MdoException;

	/**
	 * This method gets a list of user roles.
	 * 
	 * @return list of user roles.
	 * @throws MdoException
	 *             when an exception occurs.
	 */
	List<MdoTableAsEnum> getUserRoles() throws MdoException;

	/**
	 * This method gets a list of user titles.
	 * 
	 * @return list of user titles.
	 * @throws MdoException
	 *             when an exception occurs.
	 */
	List<MdoTableAsEnum> getUserTitles() throws MdoException;

	/**
	 * This method gets a list of product categories.
	 * 
	 * @return list of product categories.
	 * @throws MdoException
	 *             when an exception occurs.
	 */
	List<MdoTableAsEnum> getCategories() throws MdoException;

	/**
	 * This method gets a list of product categories.
	 * 
	 * @return list of product categories.
	 * @throws MdoException
	 *             when an exception occurs.
	 */
	List<MdoTableAsEnum> getProductSpecialCodes() throws MdoException;

	/**
	 * This method gets a list of product categories.
	 * 
	 * @return list of product categories.
	 * @throws MdoException
	 *             when an exception occurs.
	 */
	List<MdoTableAsEnum> getProductParts() throws MdoException;

	/**
	 * This method gets a list of Value Added Taxes.
	 * 
	 * @return list of Value Added Taxes.
	 * @throws MdoException
	 *             when an exception occurs.
	 */
	List<MdoTableAsEnum> getValueAddedTaxes() throws MdoException;

	/**
	 * This method gets a list of cashings.
	 * 
	 * @return list of cashings.
	 * @throws MdoException
	 *             when an exception occurs.
	 */
	List<MdoTableAsEnum> getCashings() throws MdoException;
}
