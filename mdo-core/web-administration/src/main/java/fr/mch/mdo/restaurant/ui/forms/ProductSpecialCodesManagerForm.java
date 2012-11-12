package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.dto.beans.ProductSpecialCodeDto;
import fr.mch.mdo.restaurant.dto.beans.ProductSpecialCodesManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;

public class ProductSpecialCodesManagerForm extends MdoLabelsForm 
{
	private RestaurantDto restaurant;
	
	public ProductSpecialCodesManagerForm() {
		super(new ProductSpecialCodeDto());
		super.setViewBean(new ProductSpecialCodesManagerViewBean());
	}

	/**
	 * @return the restaurant
	 */
	public RestaurantDto getRestaurant() {
		return restaurant;
	}

	/**
	 * @param restaurant the restaurant to set
	 */
	public void setRestaurant(RestaurantDto restaurant) {
		this.restaurant = restaurant;
	}
}
