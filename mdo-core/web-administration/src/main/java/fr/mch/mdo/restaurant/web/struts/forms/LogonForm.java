package fr.mch.mdo.restaurant.web.struts.forms;

import com.opensymphony.xwork2.ActionSupport;

public class LogonForm extends ActionSupport
{
	/**
     * The password.
     */
    protected String password = null;
    
    
    /**
     * The username.
     */
    protected String login = null;
    
    /**
     * Return the password.
     */
    public String getPassword()
    {
        return (this.password);
    }
    
    /**
     * Set the password.
     *
     * @param password The new password
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    
    /**
     * Return the username.
     */
    public String getLogin()
    {
        return (this.login);
    }
    
    
    /**
     * Set the username.
     *
     * @param username The new username
     */
    public void setLogin(String login)
    {
        this.login = login;
    }
}
