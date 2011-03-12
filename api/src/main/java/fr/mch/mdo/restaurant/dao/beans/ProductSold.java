/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import java.math.BigDecimal;
import java.util.Date;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * This class is a t_product_sold mapping.
 * This table is used for reporting of sold product.
 * 
 * @author Mathieu MA sous conrad
 */
public class ProductSold extends MdoDaoBean 
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * This is the sold date of the product.
     * This field and the other pdt_id field consist of a unique field.
     */
    private Date soldDate;
    /**
     * This is a foreign key that refers to t_product.
     * It is used to specify the sold product.
     * This field and the others pds_updated_date fields consist of a unique field.
     */
    private Product product;
    /**
     * This is the quantity of the sold product for a specific date.
     */
    private BigDecimal quantity;
    
    /**
     * @return the soldDate
     */
    public Date getSoldDate() {
        return soldDate;
    }
    /**
     * @param soldDate the soldDate to set
     */
    public void setSoldDate(Date soldDate) {
        this.soldDate = soldDate;
    }
    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }
    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }
    /**
     * @return the quantity
     */
    public BigDecimal getQuantity() {
        return quantity;
    }
    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((product == null) ? 0 : product.hashCode());
	result = prime * result
		+ ((soldDate == null) ? 0 : soldDate.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (!super.equals(obj)) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	ProductSold other = (ProductSold) obj;
	if (product == null) {
	    if (other.product != null) {
		return false;
	    }
	} else if (product.getId() == null) {
	    if (other.product.getId() != null) {
		return false;
	    }
	} else if (other.product == null || !product.getId().equals(other.product.getId())) {
	    return false;
	}
	if (soldDate == null) {
	    if (other.soldDate != null) {
		return false;
	    }
	} else if (!soldDate.equals(other.soldDate)) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "ProductSold [product=" + product + ", quantity=" + quantity
		+ ", soldDate=" + soldDate + "]";
    }
}
