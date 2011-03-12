/*
 * Created on 29 avr. 2004
 */
package fr.mch.mdo.restaurant.dao.beans;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import java.util.Set;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * This class is a t_user mapping. This table is used for user.
 * 
 * @author Mathieu MA sous conrad
 */
public class User extends MdoDaoBean 
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * This is the user name.
     */
    private String name;
    /**
     * This is the first forename of the user.
     */
    private String forename1;
    /**
     * This is the second forename of the user.
     */
    private String forename2;
    /**
     * This is the birthdate of the user.
     */
    private Date birthdate;
    /**
     * This is the sex of the user.
     */
    private boolean sex;
    /**
     * This is a foreign key that refers to t_enum.
     * It is used to specify the user title like MR, MRS, MISS, DR ...
     */
    private MdoTableAsEnum title;
    /**
     * This is the picture of the user.
     */
    private byte[] picture;

    private Set<UserRestaurant> restaurants;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the forename1
     */
    public String getForename1() {
        return forename1;
    }

    /**
     * @param forename1 the forename1 to set
     */
    public void setForename1(String forename1) {
        this.forename1 = forename1;
    }

    /**
     * @return the forename2
     */
    public String getForename2() {
        return forename2;
    }

    /**
     * @param forename2 the forename2 to set
     */
    public void setForename2(String forename2) {
        this.forename2 = forename2;
    }

    /**
     * @return the birthdate
     */
    public Date getBirthdate() {
        return birthdate;
    }

    /**
     * @param birthdate the birthdate to set
     */
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * @return the sex
     */
    public boolean isSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(boolean sex) {
        this.sex = sex;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(MdoTableAsEnum title) {
	this.title = title;
    }

    /**
     * @return the title
     */
    public MdoTableAsEnum getTitle() {
	return title;
    }

    /**
     * @return the picture
     */
    public byte[] getPicture() {
        return picture;
    }

    /**
     * @param picture the picture to set
     */
    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    /**
     * @return the restaurants
     */
    public Set<UserRestaurant> getRestaurants() {
        return restaurants;
    }

    /**
     * @param restaurants the restaurants to set
     */
    public void setRestaurants(Set<UserRestaurant> restaurants) {
        this.restaurants = restaurants;
    }
    
    /**
     * Add UserRestaurant to restaurants
     * @param restaurant the restaurant
     */
    public void addRestaurant(UserRestaurant restaurant) {
	if (restaurants == null) {
	    restaurants = new HashSet<UserRestaurant>();
	}
	if (restaurant != null) {
	    restaurant.setUser(this);
	}
	restaurants.add(restaurant);
    }

    @Override
    public int hashCode() {
	return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
	return super.equals(obj);
    }

    @Override
    public String toString() {
	return "User [birthdate=" + birthdate + ", forename1=" + forename1
		+ ", forename2=" + forename2 + ", name=" + name + ", picture="
		+ Arrays.toString(picture) + ", restaurants=" + restaurants
		+ ", sex=" + sex + ", title=" + title + ", deleted=" + deleted
		+ ", id=" + id + "]";
    }
}
