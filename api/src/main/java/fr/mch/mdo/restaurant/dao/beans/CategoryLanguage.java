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
 *         Cette classe est un mapping de la table t_category.
 */
public class CategoryLanguage extends MdoDaoBean
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /*
     * cat_id serial, loc_id varchar(3) NOT NULL DEFAULT 'fr', ctl_label
     * varchar(50) NOT NULL,
     */
    private Locale locale;

    private Category category;

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

    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

}
