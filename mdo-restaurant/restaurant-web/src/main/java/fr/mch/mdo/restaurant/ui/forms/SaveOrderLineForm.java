package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.beans.dto.OrderLineDto;

public class SaveOrderLineForm {

	private OrderLineDto orderLine;

	/**
	 * @return the orderLine
	 */
	public OrderLineDto getOrderLine() {
		return orderLine;
	}

	/**
	 * @param orderLine the orderLine to set
	 */
	public void setOrderLine(OrderLineDto orderLine) {
		this.orderLine = orderLine;
	}
}
