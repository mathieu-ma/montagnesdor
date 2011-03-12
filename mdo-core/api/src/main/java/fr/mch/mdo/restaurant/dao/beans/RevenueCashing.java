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
 * This class is a t_revenue_cashing mapping. 
 * This table is used for cashing revenue depending on type of cashing.
 * 
 * @author Mathieu
 */
public class RevenueCashing extends MdoDaoBean 
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * This is a foreign key that refers to t_revenue.
     * It is used to specify the revenue.
     * This field and the other rvc_type_enum_id field consist of a unique field.
     */
    private Revenue revenue;
    /**
     * This is a foreign key that refers to t_enum.
     * It is used to specify the type of cashing.
     * It could be
     * GENERIC_CASH, EURO_CASH, DOLLAR_CASH,
     * GENERIC_TICKET, MEAL_TICKET, HOLIDAYS_TICKET,
     * GENERIC_CHECK, BNP_CHECK, 
     * GENERIC_CARD, VISA_CARD, MASTER_CARD, 
     * UNPAID...
     * This field and the other rev_id field consist of a unique field. 
     */
    private MdoTableAsEnum type;
    /**
     * This is the amount of the revenue depending on the specific type of cashing.
     */
    private BigDecimal amount;

    /**
     * @return the revenue
     */
    public Revenue getRevenue() {
        return revenue;
    }

    /**
     * @param revenue the revenue to set
     */
    public void setRevenue(Revenue revenue) {
        this.revenue = revenue;
    }

    /**
     * @return the type
     */
    public MdoTableAsEnum getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(MdoTableAsEnum type) {
        this.type = type;
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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1; // DO NOT call super.hashCode(); because ID could be null.
	result = prime * result + ((type == null || type.getId() == null) ? 0 : type.getId().hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	// DO NOT call super.hashCode(); because ID could be null.
//	if (!super.equals(obj)) {
//	    return false;
//	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	RevenueCashing other = (RevenueCashing) obj;
	if (type == null) {
	    if (other.type != null) {
		return false;
	    }
	} else if (type.getId() == null) {
	    if (other.type.getId() != null) {
		return false;
	    }
	} else if (other.type == null || !type.getId().equals(other.type.getId())) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "RevenueCashing [type=" + type + ", amount=" + amount + ", deleted="
		+ deleted + ", id=" + id + "]";
    }

}
