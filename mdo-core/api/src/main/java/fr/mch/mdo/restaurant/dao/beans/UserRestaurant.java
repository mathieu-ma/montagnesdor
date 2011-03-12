/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * This class is a t_user_restaurant mapping.
 * This table is used to specify that a user has or works in several users.
 * 
 * @author Mathieu MA sous conrad
 */
public class UserRestaurant extends MdoDaoBean 
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * This is a foreign key that refers to t_user.
     * It is used to specify the user restaurant.
     * This field and the other res_id field consist of a unique field.
     */
    private Restaurant restaurant;
    /**
     * This is a foreign key that refers to t_restaurant.
     * It is used to specify the restaurant of the user. 
     * This field and the other usr_id field consist of a unique field.
     */
    private User user;
    
    /**
     * @return the restaurant
     */
    public Restaurant getRestaurant() {
        return restaurant;
    }
    /**
     * @param restaurant the restaurant to set
     */
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }
    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1; // DO NOT call super.hashCode(); because ID could be null.
	result = prime * result	+ ((restaurant == null || restaurant.getId() == null) ? 0 : restaurant.getId().hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	// DO NOT call super.hashCode(); because ID could be null.
//	if (!super.equals(obj)) {
//	    return false;
//	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	UserRestaurant other = (UserRestaurant) obj;
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
	return true;
    }
    
    @Override
    public String toString() {
	return "UserRestaurant [restaurant=" + restaurant + ", deleted="
		+ deleted + ", id=" + id + "]";
    }
}
