package fr.mch.mdo.restaurant.services.business.managers.products;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.MdoTableAsEnumType;

public interface IMdoTableAsEnumsManager extends IAdministrationManager
{
	/**
	 * Language Key Label separator.
	 */
	String LANGUAGE_KEY_LABEL_SEPARATOR = ".";
	
	/**
	 * This method gets a list of MdoTableAsEnum element by type.
	 * @param type the type.
	 * @return list of MdoTableAsEnum element by type.
	 * @throws MdoException when an exception occurs.
	 */
	List<IMdoDtoBean> getList(String type) throws MdoException;

	/**
	 * This method gets a list of MdoTableAsEnum element by type.
	 * @param type the type.
	 * @return list of MdoTableAsEnum element by type.
	 * @throws MdoException when an exception occurs.
	 */
	List<IMdoDtoBean> getList(MdoTableAsEnumType type) throws MdoException;

	/**
     * This method gets a list of specific rounds.
     * @return list of specific rounds.
     * @throws MdoException when an exception occurs.
     */
    List<IMdoDtoBean> getSpecificRounds() throws MdoException;

	/**
     * This method gets a list of table types.
     * @return list of table types.
     * @throws MdoException when an exception occurs.
     */
	List<IMdoDtoBean> getTableTypes() throws MdoException;

    /**
     * This method gets a list of Restaurant prefix table names. This is used for take-away prefix table name. 
     * @return list of Restaurant prefix take-away names.
     * @throws MdoException when an exception occurs.
     */
    List<IMdoDtoBean> getPrefixTableNames() throws MdoException;

    /**
     * This method gets a list of Restaurant printing information alignments. 
     * @return list of Restaurant printing information alignments.
     * @throws MdoException when an exception occurs.
     */
    List<IMdoDtoBean> getPrintingInformationAlignments() throws MdoException;

    /**
     * This method gets a list of Restaurant printing information sizes. 
     * @return list of Restaurant printing information sizes.
     * @throws MdoException when an exception occurs.
     */
    List<IMdoDtoBean> getPrintingInformationSizes() throws MdoException;

    /**
     * This method gets a list of Restaurant printing information parts. 
     * @return list of Restaurant printing information parts.
     * @throws MdoException when an exception occurs.
     */
    List<IMdoDtoBean> getPrintingInformationParts() throws MdoException;

    /**
     * This method gets a list of user roles. 
     * @return list of user roles.
     * @throws MdoException when an exception occurs.
     */
    List<IMdoDtoBean> getUserRoles() throws MdoException;

	/**
     * This method gets a list of user titles. 
     * @return list of user titles.
     * @throws MdoException when an exception occurs.
     */
    List<IMdoDtoBean> getUserTitles() throws MdoException;

	/**
     * This method gets a list of product categories. 
     * @return list of product categories.
     * @throws MdoException when an exception occurs.
     */
    List<IMdoDtoBean> getCategories() throws MdoException;

	/**
     * This method gets a list of product special codes. 
     * @return list of product special codes.
     * @throws MdoException when an exception occurs.
     */
	List<IMdoDtoBean> getProductSpecialCodes() throws MdoException;

	/**
     * This method gets a list of product parts. 
     * @return list of product parts.
     * @throws MdoException when an exception occurs.
     */
	List<IMdoDtoBean> getProductParts() throws MdoException;

	/**
     * This method gets a list of Value Added Taxes. 
     * @return list of Value Added Taxes.
     * @throws MdoException when an exception occurs.
     */
	List<IMdoDtoBean> getValueAddedTaxes() throws MdoException;

    /**
     * This method gets a list of cashings. 
     * @return list of cashings.
     * @throws MdoException when an exception occurs.
     */
    List<IMdoDtoBean> getCashings() throws MdoException;

    /**
     * This method find bean by unique key.
     * @param type the type.
     * @param name the name.
     * @param userContext the user context.
     * @return found bean.
     * @throws MdoException when an exception occurs.
     */
    IMdoDtoBean findByTypeAndName(String type, String name) throws MdoException;
}
