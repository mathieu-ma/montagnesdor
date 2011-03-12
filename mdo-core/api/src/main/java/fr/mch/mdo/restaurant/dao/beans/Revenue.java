/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * This class is a t_revenue mapping.
 * This table is used for reporting day revenue depending on the type of table.
 * 
 * @author Mathieu
 */
public class Revenue extends MdoDaoBean 
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * This is a foreign key that refers to t_restaurant. 
     * It is used to specify the restaurant. 
     * This field and the others rev_revenue_date and tbt_id fields consist of a unique field.
     */
    private Restaurant restaurant;
    /**
     * This is the value of the dinner table depending on the specific type of cashing. 
     * This field and the others tbt_id and res_id fields consist of a unique field.
     */
    private Date revenueDate;
    /**
     * This is a foreign key that refers to t_table_type.
     * It is used to specify the type of table. It could be for instance TAKE AWAY or EAT IN. 
     * This field and the others rev_revenue_date and res_id fields consist of a unique field.
     */
    private TableType tableType;
    /**
     * This is the printing date of the day revenue depending on the type of table.
     */
    private Date printingDate;
    /**
     * This is the closing date of the day revenue depending on the type of table.
     */
    private Date closingDate;
    /**
     * This is the amount of the day revenue depending on the type of table.
     */
    private BigDecimal amount;
    
    /**
     * Set of cashings for this revenue.
     */
    private Set<RevenueCashing> cashings;
    /**
     * Set of vats for this revenue.
     */
    private Set<RevenueVat> vats;

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
     * @return the revenueDate
     */
    public Date getRevenueDate() {
        return revenueDate;
    }
    /**
     * @param revenueDate the revenueDate to set
     */
    public void setRevenueDate(Date revenueDate) {
        this.revenueDate = revenueDate;
    }
    /**
     * @return the tableType
     */
    public TableType getTableType() {
        return tableType;
    }
    /**
     * @param tableType the tableType to set
     */
    public void setTableType(TableType tableType) {
        this.tableType = tableType;
    }
    /**
     * @return the printingDate
     */
    public Date getPrintingDate() {
        return printingDate;
    }
    /**
     * @param printingDate the printingDate to set
     */
    public void setPrintingDate(Date printingDate) {
        this.printingDate = printingDate;
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
     * @return the cashings
     */
    public Set<RevenueCashing> getCashings() {
        return cashings;
    }
    /**
     * @param cashings the cashings to set
     */
    public void setCashings(Set<RevenueCashing> cashings) {
        this.cashings = cashings;
    }
    /**
     * Add cashing to cashings
     * @param cashing the cashing
     */
    public void addCashing(RevenueCashing cashing) {
	if (cashings == null) {
	    cashings = new HashSet<RevenueCashing>();
	}
	if (cashing != null) {
	    cashing.setRevenue(this);
	}
	cashings.add(cashing);
    }

    /**
     * @return the vats
     */
    public Set<RevenueVat> getVats() {
        return vats;
    }
    /**
     * @param vats the vats to set
     */
    public void setVats(Set<RevenueVat> vats) {
        this.vats = vats;
    }
    /**
     * Add vat to vats
     * @param vat the vat
     */
    public void addVat(RevenueVat vat) {
	if (vats == null) {
	    vats = new HashSet<RevenueVat>();
	}
	if (vat != null) {
	    vat.setRevenue(this);
	}
	vats.add(vat);
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result
		+ ((restaurant == null) ? 0 : restaurant.hashCode());
	result = prime * result
		+ ((revenueDate == null) ? 0 : revenueDate.hashCode());
	result = prime * result
		+ ((tableType == null) ? 0 : tableType.hashCode());
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
	Revenue other = (Revenue) obj;
	if (restaurant == null) {
	    if (other.restaurant != null) {
		return false;
	    }
	} else if (restaurant.getId() == null) {
	    if (other.restaurant.getId() != null) {
		return false;
	    }
	} else if (other.restaurant == null || !restaurant.getId().equals(other.restaurant.getId())) {
	    return false;
	}
	if (revenueDate == null) {
	    if (other.revenueDate != null) {
		return false;
	    }
	} else if (!revenueDate.equals(other.revenueDate)) {
	    return false;
	}
	if (tableType == null) {
	    if (other.tableType != null) {
		return false;
	    }
	} else if (tableType.getId() == null) {
	    if (other.tableType.getId() != null) {
		return false;
	    }
	} else if (other.tableType == null || !tableType.getId().equals(other.tableType.getId())) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "DayRevenue [amount=" + amount + ", closingDate=" + closingDate
		+ ", printingDate=" + printingDate + ", restaurant="
		+ restaurant + ", revenueDate=" + revenueDate + ", tableType="
		+ tableType + ", deleted=" + deleted + ", id=" + id 
		+ ", cashings=" + cashings + ", vats=" + vats +"]";
    }
}
