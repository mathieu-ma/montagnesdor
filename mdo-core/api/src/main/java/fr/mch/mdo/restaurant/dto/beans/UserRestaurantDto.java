/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dto.beans;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * This class is a DTO to specify that a user has or works in several users.
 * 
 * @author Mathieu MA sous conrad
 */
public class UserRestaurantDto extends MdoDtoBean 
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
    private RestaurantDto restaurant;
    /**
     * This is a foreign key that refers to t_restaurant.
     * It is used to specify the restaurant of the user. 
     * This field and the other usr_id field consist of a unique field.
     */
    private UserDto user;
    
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
     * @return the user
     */
    public UserDto getUser() {
        return user;
    }
    /**
     * @param user the user to set
     */
    public void setUser(UserDto user) {
        this.user = user;
    }

    @Override
    public String toString() {
	return "UserRestaurantDto [restaurant=" + restaurant + ", user=" + user
		+ ", id=" + id + "]";
    }
}
