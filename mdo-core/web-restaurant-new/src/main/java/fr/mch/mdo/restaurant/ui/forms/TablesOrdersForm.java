package fr.mch.mdo.restaurant.ui.forms;

import java.util.ArrayList;
import java.util.List;

import fr.mch.mdo.restaurant.dto.beans.DinnerTableDto;

public class TablesOrdersForm extends MdoRestaurantForm
{
	private List<DinnerTableDto> list = new ArrayList<DinnerTableDto>();
	
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
}
