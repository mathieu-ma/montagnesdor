package fr.mch.mdo.restaurant.dto.beans;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * @author Mathieu MA
 * 
 */
public class TableHeader extends MdoDtoBean
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	private String number;
	private Integer customer;

	public TableHeader() {
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the customer
	 */
	public Integer getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Integer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "TableHeader [number=" + number + ", customer=" + customer + "]";
	}

}
