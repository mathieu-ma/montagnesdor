/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dto.beans;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * This class is a DTO for restaurant vat table types.
 * These restaurant vat table types are used to know if you have to process VAT by table type(for instance take-away table) or by orders lines.
 * 
 * @author Mathieu
 */
public class RestaurantVatTableTypeDto extends MdoDtoBean {
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is a foreign key that refers to t_restaurant. 
	 * It is used to specify the restaurant. 
	 * This field and the others vat_id and tbt_id fields consist of a unique field.
	 */
	private RestaurantDto restaurant;
	/**
	 * This is a foreign key that refers to t_table_type. 
	 * This field and the others res_id and vat_id fields consist of a unique field. 
	 * It could be TAKE_AWAY type or EAT_IN type.
	 */
	private TableTypeDto type;
	/**
	 * This is a foreign key that refers to t_value_added_tax. 
	 * It is used to specify the value added tax. 
	 * This field and the others res_id and tbt_id fields consist of a unique field.
	 */
	private ValueAddedTaxDto vat;

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
	 * @return the vat
	 */
	public ValueAddedTaxDto getVat() {
		return vat;
	}

	/**
	 * @param vat
	 *            the vat to set
	 */
	public void setVat(ValueAddedTaxDto vat) {
		this.vat = vat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1; // DO NOT call super.hashCode(); because ID could be
		// null.
		result = prime * result + ((restaurant == null || restaurant.getId() == null) ? 0 : restaurant.getId().hashCode());
		result = prime * result + ((vat == null || vat.getId() == null) ? 0 : vat.getId().hashCode());
		result = prime * result + ((type == null || type.getId() == null) ? 0 : type.getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		// DO NOT call super.equals(); because ID could be null.
		// if (!super.equals(obj)) {
		// return false;
		// }
		if (getClass() != obj.getClass()) {
			return false;
		}
		RestaurantVatTableTypeDto other = (RestaurantVatTableTypeDto) obj;
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
		if (vat == null) {
			if (other.vat != null) {
				return false;
			}
		} else if (vat.getId() == null) {
			if (other.vat.getId() != null) {
				return false;
			}
		} else if (other.vat == null || !vat.getId().equals(other.vat.getId())) {
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
		return "RestaurantPrefixTableDto [vat=" + vat + ", restaurant=" + restaurant + ", type=" + type + ", id=" + id + "]";
	}

}
