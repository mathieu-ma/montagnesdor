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
 *         Cette classe est un mapping de la table t_order_part.
 */
public class ProductPartLanguage extends MdoDaoBean
{
    /*
     * prp_id int8 NOT NULL, ppl_label varchar(50) NOT NULL, loc_id varchar(3)
     * NOT NULL DEFAULT 'fr',
     */

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Locale locale;
    private ProductPart productPart;

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

    public ProductPart getProductPart()
    {
	return productPart;
    }

    public void setProductPart(ProductPart productPart)
    {
	this.productPart = productPart;
    }

}
