package fr.mch.mdo.restaurant.dto.beans;

/**
 * @author Mathieu MA
 * 
 */
public class ChangePasswordDtoBean extends MdoDtoBean
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String password;
    private String newPassword;
    private String levelPassword;

    public ChangePasswordDtoBean()
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

    public String getLevelPassword()
    {
        return levelPassword;
    }

    public void setLevelPassword(String levelPassword)
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
