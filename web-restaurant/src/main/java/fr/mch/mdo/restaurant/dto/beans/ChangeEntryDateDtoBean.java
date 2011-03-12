package fr.mch.mdo.restaurant.dto.beans;

/**
 * @author Mathieu MA
 * 
 */
public class ChangeEntryDateDtoBean extends MdoDtoBean
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String login;
    private String password;
    private String levelPassword;

    public ChangeEntryDateDtoBean()
    {
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
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
}
