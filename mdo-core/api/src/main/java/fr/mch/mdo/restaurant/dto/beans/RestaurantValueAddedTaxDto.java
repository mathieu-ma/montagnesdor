/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dto.beans;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * This class is a DTO for restaurant value added tax.
 * Each restaurant has a list of value added tax.
 * 
 * @author Mathieu MA sous conrad
 */
public class RestaurantValueAddedTaxDto extends MdoDtoBean 
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * This is a foreign key that refers to t_restaurant.
     * It is used to specify the restaurant. 
     * This field and the other vat_id field consist of a unique field.
     */
    private RestaurantDto restaurant;
    /**
     * This is a foreign key that refers to t_value_added_tax.
     * It is used to specify the value added tax.
     * This field and the other res_id field consist of a unique field.
     */
    private ValueAddedTaxDto vat;
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
    /**
     * @return the vat
     */
    public ValueAddedTaxDto getVat() {
        return vat;
    }
    /**
     * @param vat the vat to set
     */
    public void setVat(ValueAddedTaxDto vat) {
        this.vat = vat;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1; // DO NOT call super.hashCode(); because ID could be null.
		result = prime * result + ((vat == null || vat.getId() == null) ? 0 : vat.getId().hashCode());
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
		RestaurantValueAddedTaxDto other = (RestaurantValueAddedTaxDto) obj;
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
		return true;
	}

    @Override
    public String toString() {
	return "RestaurantValueAddedTaxDto [restaurant=" + restaurant
		+ ", vat=" + vat + ", id=" + id + "]";
    }
}
