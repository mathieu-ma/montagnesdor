/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * This class is a t_table_cashing mapping.
 * This table is used for cashing of dinner table.
 * 
 * @author Mathieu
 */
public class TableCashing extends MdoDaoBean
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
	private DinnerTable dinnerTable;
	/**
	 * This is the date of the dinner table cashing.
	 */
	private Date cashingDate;

	/** Set of cashingTypes */
	private Set<CashingType> cashingTypes;
	
	/**
	 * @return the dinnerTable
	 */
	public DinnerTable getDinnerTable() {
		return dinnerTable;
	}

	/**
	 * @param dinnerTable
	 *            the dinnerTable to set
	 */
	public void setDinnerTable(DinnerTable dinnerTable) {
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
	 * @return the cashingTypes
	 */
	public Set<CashingType> getCashingTypes() {
		return cashingTypes;
	}

	/**
	 * @param cashingTypes the cashingTypes to set
	 */
	public void setCashingTypes(Set<CashingType> cashingTypes) {
		this.cashingTypes = cashingTypes;
	}

	/**
	 * Add cashingType to cashingTypes
	 * 
	 * @param cashingType
	 *            the cashingType
	 */
	public void addCashingType(CashingType cashingType) {
		if (cashingTypes == null) {
			cashingTypes = new HashSet<CashingType>();
		}
		if (cashingType != null) {
			cashingType.setTableCashing(this);
		}
		cashingTypes.add(cashingType);
	}

	@Override
	public String toString() {
		Long dinnerTableId = dinnerTable==null ? null : dinnerTable.getId();
		return "TableCashing [dinnerTable Id=" + dinnerTableId + ", cashingDate=" + cashingDate + ", id=" + id + ", deleted=" + deleted + "]";
	}

}
