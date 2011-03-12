package fr.mch.mdo.restaurant.services.business.managers.products;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
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
	 * @param userContext the user context.
	 * @return list of MdoTableAsEnum element by type.
	 * @throws MdoException when an exception occurs.
	 */
	List<IMdoDtoBean> getList(String type, MdoUserContext userContext) throws MdoException;

	/**
	 * This method gets a list of MdoTableAsEnum element by type.
	 * @param type the type.
	 * @param userContext the user context.
	 * @return list of MdoTableAsEnum element by type.
	 * @throws MdoException when an exception occurs.
	 */
	List<IMdoDtoBean> getList(MdoTableAsEnumType type, MdoUserContext userContext) throws MdoException;

	/**
     * This method gets a list of specific rounds.
	 * @param userContext the user context.
     * @return list of specific rounds.
     * @throws MdoException when an exception occurs.
     */
    List<IMdoDtoBean> getSpecificRounds(MdoUserContext userContext) throws MdoException;

	/**
     * This method gets a list of table types.
	 * @param userContext the user context.
     * @return list of table types.
     * @throws MdoException when an exception occurs.
     */
	List<IMdoDtoBean> getTableTypes(MdoUserContext userContext) throws MdoException;

    /**
     * This method gets a list of Restaurant prefix table names. This is used for take-away prefix table name. 
	 * @param userContext the user context.
     * @return list of Restaurant prefix take-away names.
     * @throws MdoException when an exception occurs.
     */
    List<IMdoDtoBean> getPrefixTableNames(MdoUserContext userContext) throws MdoException;

    /**
     * This method gets a list of Restaurant printing information alignments. 
	 * @param userContext the user context.
     * @return list of Restaurant printing information alignments.
     * @throws MdoException when an exception occurs.
     */
    List<IMdoDtoBean> getPrintingInformationAlignments(MdoUserContext userContext) throws MdoException;

    /**
     * This method gets a list of Restaurant printing information sizes. 
	 * @param userContext the user context.
     * @return list of Restaurant printing information sizes.
     * @throws MdoException when an exception occurs.
     */
    List<IMdoDtoBean> getPrintingInformationSizes(MdoUserContext userContext) throws MdoException;

    /**
     * This method gets a list of Restaurant printing information parts. 
	 * @param userContext the user context.
     * @return list of Restaurant printing information parts.
     * @throws MdoException when an exception occurs.
     */
    List<IMdoDtoBean> getPrintingInformationParts(MdoUserContext userContext) throws MdoException;

    /**
     * This method gets a list of user roles. 
	 * @param userContext the user context.
     * @return list of user roles.
     * @throws MdoException when an exception occurs.
     */
    List<IMdoDtoBean> getUserRoles(MdoUserContext userContext) throws MdoException;

	/**
     * This method gets a list of user titles. 
	 * @param userContext the user context.
     * @return list of user titles.
     * @throws MdoException when an exception occurs.
     */
    List<IMdoDtoBean> getUserTitles(MdoUserContext userContext) throws MdoException;

	/**
     * This method gets a list of product categories. 
	 * @param userContext the user context.
     * @return list of product categories.
     * @throws MdoException when an exception occurs.
     */
    List<IMdoDtoBean> getCategories(MdoUserContext userContext) throws MdoException;

	/**
     * This method gets a list of product special codes. 
	 * @param userContext the user context.
     * @return list of product special codes.
     * @throws MdoException when an exception occurs.
     */
	List<IMdoDtoBean> getProductSpecialCodes(MdoUserContext userContext) throws MdoException;

	/**
     * This method gets a list of product parts. 
	 * @param userContext the user context.
     * @return list of product parts.
     * @throws MdoException when an exception occurs.
     */
	List<IMdoDtoBean> getProductParts(MdoUserContext userContext) throws MdoException;

	/**
     * This method gets a list of Value Added Taxes. 
	 * @param userContext the user context.
     * @return list of Value Added Taxes.
     * @throws MdoException when an exception occurs.
     */
	List<IMdoDtoBean> getValueAddedTaxes(MdoUserContext userContext) throws MdoException;

    /**
     * This method gets a list of cashings. 
	 * @param userContext the user context.
     * @return list of cashings.
     * @throws MdoException when an exception occurs.
     */
    List<IMdoDtoBean> getCashings(MdoUserContext userContext) throws MdoException;

}
