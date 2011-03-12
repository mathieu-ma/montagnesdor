/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * @author Mathieu MA sous conrad
 * 
 *         Cette classe est un mapping de la table t_user_role_language.
 */
public class UserRoleLanguage extends MdoDaoBean
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /*
     * rol_id int8 NOT NULL, url_label varchar(20) NOT NULL, loc_id varchar(3)
     * NOT NULL DEFAULT 'fr',
     */
    private Locale locale;
    private UserRole userRole;

    private String label;

    /**
     * @return
     */
    public String getLabel()
    {
	return label;
    }

    /**
     * @param string
     */
    public void setLabel(String string)
    {
	label = string;
    }

    public Locale getLocale()
    {
	return locale;
    }

    public void setLocale(Locale locale)
    {
	this.locale = locale;
    }

    public UserRole getUserRole()
    {
	return userRole;
    }

    public void setUserRole(UserRole userRole)
    {
	this.userRole = userRole;
    }

}
