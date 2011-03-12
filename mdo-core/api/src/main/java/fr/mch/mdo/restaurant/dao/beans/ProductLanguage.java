/*
 * Created on 30 mai 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * @author Mathieu MA sous conrad
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ProductLanguage extends MdoDaoBean
{

	/*
		pdt_id varchar(5) NOT NULL,
	  	pdl_label varchar(60) NOT NULL,
	  	loc_id varchar(3) NOT NULL DEFAULT 'fr',  
	 */
	private Locale locale;
	private Product product;
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

	public Product getProduct()
	{
		return product;
	}

	public void setProduct(Product product)
	{
		this.product = product;
	}

	public Locale getLocale()
	{
		return locale;
	}

	public void setLocale(Locale locale)
	{
		this.locale = locale;
	}
}
