/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dto.beans;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * This class is a t_table_cashing mapping.
 * This table is used for cashing of dinner table.
 * 
 * @author Mathieu
 */
public class TableCashingDto extends MdoDtoBean
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is a foreign key that refers to t_dinner_table.
	 * It is used to specify the dinner table.
	 * There is only one dinner table for one cashing.
	 * This field consist of a unique field.
	 */
	private DinnerTableDto dinnerTable;
	/**
	 * This is the date of the dinner table cashing.
	 */
	private Date cashingDate;

	/** Set of cashingTypes */
	private Set<CashingTypeDto> cashingTypes;

	/**
	 * @return the dinnerTable
	 */
	public DinnerTableDto getDinnerTable() {
		return dinnerTable;
	}

	/**
	 * @param dinnerTable
	 *            the dinnerTable to set
	 */
	public void setDinnerTable(DinnerTableDto dinnerTable) {
		this.dinnerTable = dinnerTable;
	}

	/**
	 * @return the cashingDate
	 */
	public Date getCashingDate() {
		return cashingDate;
	}

	/**
	 * @param cashingDate the cashingDate to set
	 */
	public void setCashingDate(Date cashingDate) {
		this.cashingDate = cashingDate;
	}

	/**
	 * @param cashingTypes the cashingTypes to set
	 */
	public void setCashingTypes(Set<CashingTypeDto> cashingTypes) {
		this.cashingTypes = cashingTypes;
	}

	/**
	 * @return the cashingTypes
	 */
	public Set<CashingTypeDto> getCashingTypes() {
		return cashingTypes;
	}

	/**
	 * Add cashingType to cashingTypes
	 * 
	 * @param cashingType
	 *            the cashingType
	 */
	public void addCashingType(CashingTypeDto cashingType) {
		if (cashingTypes == null) {
			cashingTypes = new HashSet<CashingTypeDto>();
		}
		if (cashingType != null) {
			cashingType.setTableCashing(this);
		}
		cashingTypes.add(cashingType);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1; // DO NOT call super.hashCode(); because ID could be
						// null.
		result = prime * result + ((dinnerTable == null || dinnerTable.getId() == null) ? 0 : dinnerTable.getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		// DO NOT call super.hashCode(); because ID could be null.
		// if (!super.equals(obj)) {
		// return false;
		// }
		if (getClass() != obj.getClass()) {
			return false;
		}
		TableCashingDto other = (TableCashingDto) obj;
		if (dinnerTable == null) {
			if (other.dinnerTable != null) {
				return false;
			}
		} else if (dinnerTable.getId() == null) {
			if (other.dinnerTable.getId() != null) {
				return false;
			}
		} else if (other.dinnerTable == null || !dinnerTable.getId().equals(other.dinnerTable.getId())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "CashingTable [cashingDate=" + cashingDate + ", id=" + id + "]";
	}
}
