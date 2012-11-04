package fr.mch.mdo.restaurant.ui.forms;

import java.util.ArrayList;
import java.util.List;

import fr.mch.mdo.restaurant.beans.dto.AjaxOrderLineDto;
import fr.mch.mdo.restaurant.dto.beans.DinnerTableDto;

public class TablesOrdersForm extends MdoRestaurantForm
{
	private List<DinnerTableDto> list = new ArrayList<DinnerTableDto>();
	
	private AjaxOrderLineDto orderLine = new AjaxOrderLineDto();
	
	public TablesOrdersForm() {
		super(new DinnerTableDto());
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<DinnerTableDto> list) {
		this.list = list;
	}

	/**
	 * @return the list
	 */
	public List<DinnerTableDto> getList() {
		return list;
	}

	public AjaxOrderLineDto getOrderLine() {
		return orderLine;
	}

	public void setOrderLine(AjaxOrderLineDto orderLine) {
		this.orderLine = orderLine;
	}
}
