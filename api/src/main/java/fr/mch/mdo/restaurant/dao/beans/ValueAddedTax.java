/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import java.math.BigDecimal;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * This class is a t_value_added_tax mapping.
 * This table is used for product value added tax.
 * 
 * @author Mathieu MA sous conrad
 */
public class ValueAddedTax extends MdoDaoBean 
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * This is the code of the value added tax. This is a unique field. 
     * It is a foreign that refers to the t_enum table for type VAT. 
     */
    private MdoTableAsEnum code;
    /**
     * This is rate of value added tax.
     */
    private BigDecimal rate;

    /**
     * @param code the code to set
     */
    public void setCode(MdoTableAsEnum code) {
	this.code = code;
    }

    /**
     * @return the code
     */
    public MdoTableAsEnum getCode() {
	return code;
    }

    /**
     * @return the rate
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((code == null) ? 0 : code.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ValueAddedTax other = (ValueAddedTax) obj;
	if (code == null) {
	    if (other.code != null)
		return false;
	} else if (!code.equals(other.code))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "ValueAddedTax [code=" + code + ", rate=" + rate + ", deleted="
		+ deleted + ", id=" + id + "]";
    }

}
