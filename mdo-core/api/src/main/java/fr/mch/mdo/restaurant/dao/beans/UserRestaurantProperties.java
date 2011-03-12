/*
 * Created on 29 avr. 2004
 *
 */
package fr.mch.mdo.restaurant.dao.beans;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * @author Mathieu MA sous conrad
 * 
 *         Cette classe est un mapping de la table t_user_restaurant.
 */
public class UserRestaurantProperties extends MdoDaoBean
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
/*
    --urt_id : identifiant de cette table 
    --usr_id : cléf étrangère de la table t_user 
    --res_id : cléf étrangère de la table t_restaurant
    --urt_deleted bool : supprimer ou pas ?
*/
    private Long userId;
    private Long restaurantId;

    public UserRestaurantProperties()
    {
    }

    public UserRestaurantProperties(Long userId, Long restaurantId)
    {
	this.userId = userId;
	this.restaurantId = restaurantId;
    }

    public Long getUserId()
    {
	return userId;
    }

    public void setUserId(Long userId)
    {
	this.userId = userId;
    }

    public Long getRestaurantId()
    {
	return restaurantId;
    }

    public void setRestaurantId(Long restaurantId)
    {
	this.restaurantId = restaurantId;
    }
}
