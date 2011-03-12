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
 * This class is used for t_credit mapping. 
 * This table is used for restaurant credits. One credit must belong to a restaurant.
 * 
 * @author Mathieu MA sous conrad
 */
public class Credit extends MdoDaoBean 
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * This is a foreign key that refers to t_restaurant.
     * It is used to specify the restaurant where the credit belongs.
     * This field and the others cre_reference consist of a unique field.
     */
    private Restaurant restaurant;
    /**
     * This is the reference of the credit.
     * This field and the others res_id consist of a unique field.
     */
    private String reference;
   /**
     * This is the amount of the credit.
     */
    private BigDecimal amount;
    /**
     * This is the creation date of the credit.
     */
    private Date createdDate;
    /**
     * This is the using date of the credit.
     */
    private Date closingDate;
    /**
     * This is used to know if the bill has already been printed.
     * Be careful to just print this credit once.
     */
    private Boolean printed;
    /**
     * @return the restaurant
     */
    public Restaurant getRestaurant() {
        return restaurant;
    }
    /**
     * @param restaurant the restaurant to set
     */
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    /**
     * @return the reference
     */
    public String getReference() {
        return reference;
    }
    /**
     * @param reference the reference to set
     */
    public void setReference(String reference) {
        this.reference = reference;
    }
    /**
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }
    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }
    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    /**
     * @return the closingDate
     */
    public Date getClosingDate() {
        return closingDate;
    }
    /**
     * @param closingDate the closingDate to set
     */
    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }
    /**
     * @return the printed
     */
    public Boolean getPrinted() {
        return printed;
    }
    /**
     * @param printed the printed to set
     */
    public void setPrinted(Boolean printed) {
        this.printed = printed;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result
		+ ((reference == null) ? 0 : reference.hashCode());
	result = prime * result
		+ ((restaurant == null) ? 0 : restaurant.hashCode());
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
	Credit other = (Credit) obj;
	if (reference == null) {
	    if (other.reference != null) {
		return false;
	    }
	} else if (!reference.equals(other.reference)) {
	    return false;
	}
	if (restaurant == null) {
	    if (other.restaurant != null) {
		return false;
	    }
	} else if (!restaurant.equals(other.restaurant)) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "Credit [amount=" + amount + ", closingDate=" + closingDate
		+ ", createdDate=" + createdDate + ", printed=" + printed
		+ ", reference=" + reference + ", restaurant=" + restaurant
		+ ", deleted=" + deleted + ", id=" + id + "]";
    }

}
