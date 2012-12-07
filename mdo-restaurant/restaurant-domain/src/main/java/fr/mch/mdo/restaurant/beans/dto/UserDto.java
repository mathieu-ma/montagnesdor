package fr.mch.mdo.restaurant.beans.dto;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * @author Mathieu MA
 * 
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
     * This is a foreign key that refers to t_enum.
     * It is used to specify the user title like MR, MRS, MISS, DR ...
     */
    private MdoUserTitle title;

    
    public UserDto()
    {
    }


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
	 * @return the title
	 */
	public MdoUserTitle getTitle() {
		return title;
	}


	/**
	 * @param title the title to set
	 */
	public void setTitle(MdoUserTitle title) {
		this.title = title;
	}
}
