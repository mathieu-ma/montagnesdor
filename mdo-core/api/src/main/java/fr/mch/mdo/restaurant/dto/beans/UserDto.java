/*
 * Created on 29 avr. 2004
 */
package fr.mch.mdo.restaurant.dto.beans;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * This class is a DTO for user.
 * 
 * @author Mathieu MA sous conrad
 */
public class UserDto extends MdoDtoBean 
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
    private MdoTableAsEnumDto title;
    /**
     * This is the picture of the user.
     */
    private byte[] picture;
	/**
	 * Set of user restaurants: have to instance it because we use Struts 2 conversion
	 */
    private Set<UserRestaurantDto> restaurants = new HashSet<UserRestaurantDto>();

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
     * @return the title
     */
    public MdoTableAsEnumDto getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(MdoTableAsEnumDto title) {
        this.title = title;
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
    public Set<UserRestaurantDto> getRestaurants() {
        return restaurants;
    }

    /**
     * @param restaurants the restaurants to set
     */
    public void setRestaurants(Set<UserRestaurantDto> restaurants) {
        this.restaurants = restaurants;
    }

    @Override
    public String toString() {
	return "UserDto [birthdate=" + birthdate + ", forename1=" + forename1
		+ ", forename2=" + forename2 + ", name=" + name + ", picture="
		+ Arrays.toString(picture) + ", sex=" + sex + ", title="
		+ title + ", id=" + id + "]";
    }
}
