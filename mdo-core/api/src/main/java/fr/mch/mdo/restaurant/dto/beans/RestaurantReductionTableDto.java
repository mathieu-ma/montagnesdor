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
 * This class is a DTO for restaurant reduction table.
 * A restaurant reduction table depends on table type associated to a restaurant. 
 * For example, a take-away table may have a reduction ratio/value if the minimum amount is reached.
 * 
 * @author Mathieu
 */
public class RestaurantReductionTableDto extends MdoDtoBean {
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is a foreign key that refers to t_restaurant. 
	 * This field and the other tbt_id field consist of a unique field.
	 */
	private RestaurantDto restaurant;

	/**
	 * This is a foreign key that refers to t_table_type.
	 * This field and the other res_id field consist of a unique field.
	 * It could be TAKE_AWAY type or EAT_IN type.
	 */
	private TableTypeDto type;
	
	/**
	 * This is the minimum amount in which we can apply the reduction.
	 */
	private BigDecimal minAmount;

	/**
	 * This is the reduction value to apply when the minimum amount is reached. 
	 * The value could be a ratio or an amount.
	 */
	private BigDecimal value;

	/**
	 * @return the restaurant
	 */
	public RestaurantDto getRestaurant() {
		return restaurant;
	}

	/**
	 * @param restaurant
	 *            the restaurant to set
	 */
	public void setRestaurant(RestaurantDto restaurant) {
		this.restaurant = restaurant;
	}

	/**
	 * @return the type
	 */
	public TableTypeDto getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(TableTypeDto type) {
		this.type = type;
	}

	/**
	 * @return the minAmount
	 */
	public BigDecimal getMinAmount() {
		return minAmount;
	}

	/**
	 * @param minAmount the minAmount to set
	 */
	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	/**
	 * @return the value
	 */
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((restaurant == null || restaurant.getId() == null) ? 0 : restaurant.getId().hashCode());
		result = prime * result + ((type == null || type.getId() == null) ? 0 : type.getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RestaurantReductionTableDto other = (RestaurantReductionTableDto) obj;
		if (restaurant == null) {
			if (other.restaurant != null) {
				return false;
			}
		} else if (restaurant.getId() == null) {
			if (other.restaurant.getId() != null) {
				return false;
			}
		} else if (other.restaurant == null || !restaurant.getId().equals(other.restaurant.getId())) {
			return false;
		}

		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (type.getId() == null) {
			if (other.type.getId() != null) {
				return false;
			}
		} else if (other.type == null || !type.getId().equals(other.type.getId())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "RestaurantReductionTableDto [type=" + type + ", minAmount="
				+ minAmount + ", value=" + value + ", id=" + id + "]";
	}
}
