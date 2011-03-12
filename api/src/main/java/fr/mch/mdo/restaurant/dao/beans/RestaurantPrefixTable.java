/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * This class is a t_restaurant mapping. This table is a association table. This
 * table is used to store the list of restaurant table prefix names. These
 * prefix names is used to know that a table is considered as a take-away table.
 * 
 * @author Mathieu
 */
public class RestaurantPrefixTable extends MdoDaoBean {
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is a foreign key that refers to t_restaurant. This field and the
	 * other tbt_id and rpt_prefix_enm_id fields consist of a unique field.
	 */
	private Restaurant restaurant;
	/**
	 * This is a foreign key that refers to t_table_type. This field and the
	 * other res_id and rpt_prefix_enm_id fields consist of a unique field. It
	 * could be TAKE_AWAY type or EAT_IN type.
	 */
	private TableType type;
	/**
	 * This is a foreign key that refers to t_enum table for type
	 * PREFIX_TABLE_NAME. This field and the other res_id and tbt_id fields
	 * consist of a unique field. It could a letter as "E", "A", "B" ...
	 */
	private MdoTableAsEnum prefix;

	/**
	 * @param restaurant
	 *            the restaurant to set
	 */
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	/**
	 * @return the restaurant
	 */
	public Restaurant getRestaurant() {
		return restaurant;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(TableType type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public TableType getType() {
		return type;
	}

	/**
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(MdoTableAsEnum prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return the prefix
	 */
	public MdoTableAsEnum getPrefix() {
		return prefix;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1; // DO NOT call super.hashCode(); because ID could be
						// null.
		result = prime * result + ((prefix == null || prefix.getId() == null) ? 0 : prefix.getId().hashCode());
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
		RestaurantPrefixTable other = (RestaurantPrefixTable) obj;
		if (prefix == null) {
			if (other.prefix != null) {
				return false;
			}
		} else if (prefix.getId() == null) {
			if (other.prefix.getId() != null) {
				return false;
			}
		} else if (other.prefix == null || !prefix.getId().equals(other.prefix.getId())) {
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
		return "RestaurantPrefixTable [prefix=" + prefix + ", type=" + type + ", deleted=" + deleted + ", id=" + id + "]";
	}
}
