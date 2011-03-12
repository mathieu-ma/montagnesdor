/*
 * Created on 29 avr. 2004
 *
 */
package fr.mch.mdo.restaurant.dto.beans;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;

/**
 * @author Mathieu MA
 * 
 */
public class UsersManagerViewBean extends AdministrationManagerViewBean 
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	private List<IMdoDtoBean> restaurants;

	private List<IMdoDtoBean> titles;

	/**
	 * @param restaurants the restaurants to set
	 */
	public void setRestaurants(List<IMdoDtoBean> restaurants) {
		this.restaurants = restaurants;
	}

	/**
	 * @return the restaurants
	 */
	public List<IMdoDtoBean> getRestaurants() {
		return restaurants;
	}

	/**
	 * @param titles the titles to set
	 */
	public void setTitles(List<IMdoDtoBean> titles) {
		this.titles = titles;
	}

	/**
	 * @return the titles
	 */
	public List<IMdoDtoBean> getTitles() {
		return titles;
	}

}
