package fr.mch.mdo.restaurant.ui.forms;

import java.util.HashMap;
import java.util.Map;

import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantsManagerViewBean;

public class RestaurantsManagerForm extends MdoAdministrationForm 
{
	/** Map of vat id key and restaurant vat id value */
	private Map<Long, String> vats = new HashMap<Long, String>();
	
	public RestaurantsManagerForm() {
		super(new RestaurantDto());
		super.setViewBean(new RestaurantsManagerViewBean());
	}

	/**
	 * @param vats the vats to set
	 */
	public void setVats(Map<Long, String> vats) {
		this.vats = vats;
	}

	/**
	 * @return the vats
	 */
	public Map<Long, String> getVats() {
		return vats;
	}
}
