/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

/**
 * @author Mathieu MA sous conrad
 *
 *	Cette classe est un mapping de la table t_category.
 */
public class LocaleLabel
{
	private Locale locale;
	
	private Long id;

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

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

}
