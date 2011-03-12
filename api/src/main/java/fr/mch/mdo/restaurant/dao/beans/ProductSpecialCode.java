/*
 * Created on 30 mai 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import java.util.Map;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * This class is a t_product_special_code mapping. This table contains the
 * product special code informations.
 * 
 * @author Mathieu
 */
public class ProductSpecialCode extends MdoDaoBean 
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is used to specify the short code enter by user.
	 */
	private Character shortCode;
	/**
	 * This is a foreign key that refers to t_restaurant. It is used to specify
	 * the restaurant.
	 */
	private Restaurant restaurant;
	/**
	 * This is a foreign key that refers to t_enum. It is used to specify the
	 * product special code.
	 */
	private MdoTableAsEnum code;
	/**
	 * This is used for i18n label.
	 */
	private Map<Long, String> labels;

	/**
	 * @return the shortCode
	 */
	public Character getShortCode() {
		return shortCode;
	}

	/**
	 * @param shortCode
	 *            the shortCode to set
	 */
	public void setShortCode(Character shortCode) {
		this.shortCode = shortCode;
	}

	/**
	 * @return the restaurant
	 */
	public Restaurant getRestaurant() {
		return restaurant;
	}

	/**
	 * @param restaurant
	 *            the restaurant to set
	 */
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	/**
	 * @return the code
	 */
	public MdoTableAsEnum getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(MdoTableAsEnum code) {
		this.code = code;
	}

	/**
	 * @return the labels
	 */
	public Map<Long, String> getLabels() {
		return labels;
	}

	/**
	 * @param labels
	 *            the labels to set
	 */
	public void setLabels(Map<Long, String> labels) {
		this.labels = labels;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((shortCode == null) ? 0 : shortCode.hashCode());
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
		ProductSpecialCode other = (ProductSpecialCode) obj;
		if (shortCode == null) {
			if (other.shortCode != null)
				return false;
		} else if (!shortCode.equals(other.shortCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductSpecialCode [code=" + code + ", labels=" + labels + ", restaurant=" + restaurant + ", shortCode=" + shortCode + ", deleted=" + deleted + ", id=" + id + "]";
	}
}
