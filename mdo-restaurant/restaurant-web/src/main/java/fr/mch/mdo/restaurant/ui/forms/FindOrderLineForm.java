package fr.mch.mdo.restaurant.ui.forms;

import java.math.BigDecimal;

/**
 * Use this form container in order to validate the form later.
 *  
 * @author mathieu
 *
 */
public class FindOrderLineForm {

	private BigDecimal quantity;

	private String orderCode;

	/**
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the orderCode
	 */
	public String getOrderCode() {
		return orderCode;
	}

	/**
	 * @param orderCode the orderCode to set
	 */
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
}
