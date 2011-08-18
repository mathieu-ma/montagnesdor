package fr.mch.mdo.restaurant.dto.beans;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * @author Mathieu MA
 * 
 */
public class ChangePasswordDto extends MdoDtoBean
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String password;
    private String newPassword;
    private Integer levelPassword;

    public ChangePasswordDto()
    {
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Integer getLevelPassword()
    {
        return levelPassword;
    }

    public void setLevelPassword(Integer levelPassword)
    {
        this.levelPassword = levelPassword;
    }

    public String getNewPassword()
    {
        return newPassword;
    }

    public void setNewPassword(String newPassword)
    {
        this.newPassword = newPassword;
    }
}
