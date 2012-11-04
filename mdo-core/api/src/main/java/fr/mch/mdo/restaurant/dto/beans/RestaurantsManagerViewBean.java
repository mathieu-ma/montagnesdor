/*
 * Created on 29 avr. 2004
 *
 */
package fr.mch.mdo.restaurant.dto.beans;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;


/**
 * @author Mathieu MA
 * 
 */
public class RestaurantsManagerViewBean extends AdministrationManagerViewBean 
{
	/**
     * Default Serial Version UID.
     */
	private static final long serialVersionUID = 1L;
	
	private List<IMdoDtoBean> specificRounds;

	private List<IMdoDtoBean> vatTableTypes;

	private List<IMdoDtoBean> prefixTableNames;

	private List<IMdoDtoBean> valueAddedTaxes;

	private List<IMdoDtoBean> tableTypes;

	private List<IMdoDtoBean> reductionTableTypes;

	/**
	 * @return the specificRounds
	 */
	public List<IMdoDtoBean> getSpecificRounds() {
		return specificRounds;
	}

	/**
	 * @param list the specificRounds to set
	 */
	public void setSpecificRounds(List<IMdoDtoBean> specificRounds) {
		this.specificRounds = specificRounds;
	}

	/**
	 * @return the vatTableTypes
	 */
	public List<IMdoDtoBean> getVatTableTypes() {
		return vatTableTypes;
	}

	/**
	 * @param vatTableTypes the vatTableTypes to set
	 */
	public void setVatTableTypes(List<IMdoDtoBean> vatTableTypes) {
		this.vatTableTypes = vatTableTypes;
	}

	/**
	 * @param prefixTableNames the prefixTableNames to set
	 */
	public void setPrefixTableNames(
			List<IMdoDtoBean> prefixTableNames) {
		this.prefixTableNames = prefixTableNames;
	}

	/**
	 * @return the prefixTableNames
	 */
	public List<IMdoDtoBean> getPrefixTableNames() {
		return prefixTableNames;
	}

	/**
	 * @param valueAddedTaxes the valueAddedTaxes to set
	 */
	public void setValueAddedTaxes(List<IMdoDtoBean> valueAddedTaxes) {
		this.valueAddedTaxes = valueAddedTaxes;
	}

	/**
	 * @return the valueAddedTaxes
	 */
	public List<IMdoDtoBean> getValueAddedTaxes() {
		return valueAddedTaxes;
	}

	/**
	 * @param tableTypes the tableTypes to set
	 */
	public void setTableTypes(List<IMdoDtoBean> tableTypes) {
		this.tableTypes = tableTypes;
	}

	/**
	 * @return the tableTypes
	 */
	public List<IMdoDtoBean> getTableTypes() {
		return tableTypes;
	}

	/**
	 * @return the reductionTableTypes
	 */
	public List<IMdoDtoBean> getReductionTableTypes() {
		return reductionTableTypes;
	}

	/**
	 * @param reductionTableTypes the reductionTableTypes to set
	 */
	public void setReductionTableTypes(List<IMdoDtoBean> reductionTableTypes) {
		this.reductionTableTypes = reductionTableTypes;
	}
}
