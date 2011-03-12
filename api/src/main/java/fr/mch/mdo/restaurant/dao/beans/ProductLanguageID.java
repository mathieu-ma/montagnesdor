/*
 * Created on 30 mai 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import java.io.Serializable;

/**
 * @author Mathieu MA sous conrad
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ProductLanguageID implements Serializable
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 7925591477005028433L;
	
	private Product product;
	private Locale locale; 

	/**
	 * @return
	 */
	public Product getProduct()
	{
		return product;
	}

	/**
	 * @param product
	 */
	public void setProduct(Product product)
	{
		this.product = product;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		if(obj instanceof  ProductLanguageID)
		{
			ProductLanguageID productLanguageID = (ProductLanguageID) obj;
			return (this.product.getId().equals(productLanguageID.getProduct().getId()) && this.locale.getId().equals(productLanguageID.getLocale().getId()));
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return this.product.getId().hashCode()+this.locale.getId().hashCode();
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
