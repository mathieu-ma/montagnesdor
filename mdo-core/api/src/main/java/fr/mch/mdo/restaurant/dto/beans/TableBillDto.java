/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dto.beans;

import java.math.BigDecimal;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * This class is used for t_table_bill mapping. This table is used for bills of
 * dinner table. There are several bills for a specific dinner table.
 * 
 * @author Mathieu MA sous conrad
 */
public class TableBillDto extends MdoDtoBean
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is a foreign key that refers to t_dinner_table. It is used to
	 * specify the dinner table. This field and the other tbi_reference,
	 * tbi_order fields consist of a unique field.
	 */
	private DinnerTableDto dinnerTable;
	/**
	 * This is a the bill reference for authentication checking. This field and
	 * the other dtb_id, tbi_order fields consist of a unique field.
	 */
	private String reference;
	/**
	 * This is a the bill order. We can have several bill for a specific table.
	 * So this field is used to increment the bill table number for printing
	 * information. This field and the other dtb_id, tbi_reference fields
	 * consist of a unique field.
	 */
	private Integer order;
	/**
	 * This is the bill meal number.
	 */
	private Integer mealNumber;
	/**
	 * This is the bill amount.
	 */
	private BigDecimal amount;
	/**
	 * This is used to know if the bill has already been printed.
	 */
	private Boolean printed = Boolean.FALSE;

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
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @param reference
	 *            the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * @return the order
	 */
	public Integer getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

	/**
	 * @return the mealNumber
	 */
	public Integer getMealNumber() {
		return mealNumber;
	}

	/**
	 * @param mealNumber
	 *            the mealNumber to set
	 */
	public void setMealNumber(Integer mealNumber) {
		this.mealNumber = mealNumber;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the printed
	 */
	public Boolean getPrinted() {
		return printed;
	}

	/**
	 * @param printed
	 *            the printed to set
	 */
	public void setPrinted(Boolean printed) {
		this.printed = printed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + ((reference == null) ? 0 : reference.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TableBillDto other = (TableBillDto) obj;
		if (order == null) {
			if (other.order != null) {
				return false;
			}
		} else if (!order.equals(other.order)) {
			return false;
		}
		if (reference == null) {
			if (other.reference != null) {
				return false;
			}
		} else if (!reference.equals(other.reference)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Bill [amount=" + amount + ", mealNumber=" + mealNumber + ", order=" + order + ", printed=" 
		+ printed + ", reference=" + reference + ", id=" + id + "]";
	}

}
