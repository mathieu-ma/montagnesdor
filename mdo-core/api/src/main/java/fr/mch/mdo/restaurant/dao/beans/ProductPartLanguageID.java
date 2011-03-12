/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import java.io.Serializable;

/**
 * @author Mathieu MA sous conrad
 *
 *	Cette classe est un mapping de la table t_order_part.
 */
public class ProductPartLanguageID implements Serializable
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6247876811778008992L;
	
	private ProductPart productPart;
	private Locale locale;

	/**
	 * @return
	 */
	public ProductPart getProductPart()
	{
		return productPart;
	}


	/**
	 * @param part
	 */
	public void setProductPart(ProductPart part)
	{
		productPart = part;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		if(obj instanceof  ProductPartLanguageID)
		{
			ProductPartLanguageID productPartLanguageID = (ProductPartLanguageID) obj;
			return (((Long)this.productPart.getId()).longValue() == ((Long)productPartLanguageID.getProductPart().getId()).longValue() && this.locale.getId().equals(productPartLanguageID.getLocale().getId()));
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return this.productPart.getId().hashCode()+this.locale.hashCode();
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
