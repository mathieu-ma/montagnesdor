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
 *         Cette classe est un mapping de la table t_type_table_language.
 */
public class TypeTableLanguage extends MdoDaoBean
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /*
     * ttl_id serial, ttb_id int8 NOT NULL, ttl_label varchar(50) NOT NULL,
     * loc_id varchar(3) NOT NULL DEFAULT 'fr',
     */
    private Locale locale;
    private TableType tableType;
    private String label;

    /**
     * @return
     */
    public String getLabel()
    {
	return label;
    }

    /**
     * @return
     */
    public TableType getTypeTable()
    {
	return tableType;
    }

    /**
     * @param string
     */
    public void setLabel(String string)
    {
	label = string;
    }

    /**
     * @param table
     */
    public void setTypeTable(TableType tableType)
    {
	this.tableType = tableType;
    }

    /**
     * @return
     */
    public Locale getLocale()
    {
	return locale;
    }

    /**
     * @param locale
     */
    public void setLocale(Locale locale)
    {
	this.locale = locale;
    }

}
