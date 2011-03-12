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
 * This class is used for t_dinner_table mapping. 
 * This table is used for dinner tables.
 * 
 * @author Mathieu MA sous conrad
 */
public class DinnerTable extends MdoDaoBean
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * This is a foreign key that refers to t_restaurant. 
     * It is used to specify the restaurant where the dinner table belongs. 
     * Notice that this information is already known with the aut_id field. 
     * But this field is used to determine uniquely the dinner table with other fields. 
     * So this field and the others dtb_code and dtb_cashing_date consist of a unique field.
     */
    private Restaurant restaurant;
    /**
     * This is the code number of the dinner table. 
     * This field and the others res_id and dtb_cashing_date consist of a unique field.
     */
    private String number;
    /**
     * This is the dinner table cashing date. It is used to know if the dinner table has already payed. 
     * If not then this field is null and we can not create a new dinner table with the same code in the same restaurant. 
     * This field and the others res_id and dtb_cashing_date consist of a unique field.
     */
    private Date cashingDate;
    /**
     * This is a foreign key that refers to t_user_authentication.
     * It is used to specify the user authentication that created this dinner table.
     */
    private UserAuthentication user;
    /**
     * This is an id for the room where the dinner table is.
     * It is not currently used.
     */
    private Long roo_id;
    /**
     * This is used to specify the number of customers.
     */
    private Integer customersNumber;
    /**
     * This is used to specify the sum of the order lines quantities.
     * This value could be calculated from order lines table.
     */
    private BigDecimal quantitiesSum;
    /**
     * This is used to specify the sum of the order lines amounts.
     * This value could be calculated from order lines table.
     */
    private BigDecimal amountsSum;
    /**
     * This is used to specify the reduction ratio.
     */
    private BigDecimal reductionRatio;
    /**
     * This is used to specify the amount to pay.
     */
    private BigDecimal amountPay;
    /**
     * This is used to specify the registration/creation date.
     */
    private Date registrationDate;
    /**
     * This is used to specify the printing date.
     */
    private Date printingDate;
    /**
     * This is used to specify if user has changed the reduction ratio.
     */
    private Boolean reductionRatioChanged = false;
    /**
     * This is used to specify the type of dinner table. Could be TAKE-AWAY, EAT-IN ...
     */
    private TableType type;

    /**
     * Set of order lines.
     */
    private Set<OrderLine> orders;

    /**
     * Set of bills == factures.
     */
    private Set<TableBill> bills;

    /**
     * Set of credit == avoirs.
     */
    private Set<TableCredit> credits;
	
    /**
     * Set of VAT amount for this table.
     */
    private Set<TableVat> vats;
    
    /**
     * Set of cashings for this table.
     */
    private Set<TableCashing> cashings;
    
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
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return the cashingDate
     */
    public Date getCashingDate() {
        return cashingDate;
    }

    /**
     * @param cashingDate the cashingDate to set
     */
    public void setCashingDate(Date cashingDate) {
        this.cashingDate = cashingDate;
    }

    /**
     * @return the user
     */
    public UserAuthentication getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserAuthentication user) {
        this.user = user;
    }

    /**
     * @return the roo_id
     */
    public Long getRoo_id() {
        return roo_id;
    }

    /**
     * @param rooId the roo_id to set
     */
    public void setRoo_id(Long rooId) {
        roo_id = rooId;
    }

    /**
     * @return the customersNumber
     */
    public Integer getCustomersNumber() {
        return customersNumber;
    }

    /**
     * @param customersNumber the customersNumber to set
     */
    public void setCustomersNumber(Integer customersNumber) {
        this.customersNumber = customersNumber;
    }

    /**
     * @return the quantitiesSum
     */
    public BigDecimal getQuantitiesSum() {
        return quantitiesSum;
    }

    /**
     * @param quantitiesSum the quantitiesSum to set
     */
    public void setQuantitiesSum(BigDecimal quantitiesSum) {
        this.quantitiesSum = quantitiesSum;
    }

    /**
     * @return the amountsSum
     */
    public BigDecimal getAmountsSum() {
        return amountsSum;
    }

    /**
     * @param amountsSum the amountsSum to set
     */
    public void setAmountsSum(BigDecimal amountsSum) {
        this.amountsSum = amountsSum;
    }

    /**
     * @return the reductionRatio
     */
    public BigDecimal getReductionRatio() {
        return reductionRatio;
    }

    /**
     * @param reductionRatio the reductionRatio to set
     */
    public void setReductionRatio(BigDecimal reductionRatio) {
        this.reductionRatio = reductionRatio;
    }

    /**
     * @return the amountPay
     */
    public BigDecimal getAmountPay() {
        return amountPay;
    }

    /**
     * @param amountPay the amountPay to set
     */
    public void setAmountPay(BigDecimal amountPay) {
        this.amountPay = amountPay;
    }

    /**
     * @return the registrationDate
     */
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * @param registrationDate the registrationDate to set
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * @return the printingDate
     */
    public Date getPrintingDate() {
        return printingDate;
    }

    /**
     * @param printingDate the printing Date to set
     */
    public void setPrintingDate(Date printingDate) {
        this.printingDate = printingDate;
    }

    /**
     * @return the reductionRatioChanged
     */
    public Boolean getReductionRatioChanged() {
        return reductionRatioChanged;
    }

    /**
     * @param reductionRatioChanged the reductionRatioChanged to set
     */
    public void setReductionRatioChanged(Boolean reductionRatioChanged) {
        this.reductionRatioChanged = reductionRatioChanged;
    }

    /**
     * @return the type
     */
    public TableType getType() {
        return type;
    }
    
    /**
     * @param takeaway the type to set
     */
    public void setType(TableType type) {
        this.type = type;
    }

    /**
     * @return the orders
     */
    public Set<OrderLine> getOrders() {
        return orders;
    }

    /**
     * @param orders the orders to set
     */
    public void setOrders(Set<OrderLine> orders) {
        this.orders = orders;
    }

    /**
     * Add order line to orders
     * @param orderLine the order line
     */
    public void addOrderLine(OrderLine orderLine) {
	if (orders == null) {
	    orders = new HashSet<OrderLine>();
	}
	if (orderLine != null) {
	    orderLine.setDinnerTable(this);
	}
	orders.add(orderLine);
    }
    
    /**
     * @return the bills
     */
    public Set<TableBill> getBills() {
        return bills;
    }

    /**
     * @param bills the bills to set
     */
    public void setBills(Set<TableBill> bills) {
        this.bills = bills;
    }
    
    /**
     * Add bill to bills
     * @param bill the bill
     */
    public void addBill(TableBill tableBill) {
	if (bills == null) {
	    bills = new HashSet<TableBill>();
	}
	if (tableBill != null) {
	    tableBill.setDinnerTable(this);
	}
	bills.add(tableBill);
    }

    /**
     * @return the credits
     */
    public Set<TableCredit> getCredits() {
        return credits;
    }

    /**
     * @param credits the credits to set
     */
    public void setCredits(Set<TableCredit> credits) {
        this.credits = credits;
    }

    /**
     * Add credit to credits
     * @param credit the credit
     */
    public void addCredit(TableCredit credit) {
	if (credits == null) {
	    credits = new HashSet<TableCredit>();
	}
	if (credit != null) {
	    credit.setDinnerTable(this);
	}
	credits.add(credit);
    }

    /**
     * @return the vats
     */
    public Set<TableVat> getVats() {
        return vats;
    }

    /**
     * @param vats the vats to set
     */
    public void setVats(Set<TableVat> vats) {
        this.vats = vats;
    }

    /**
     * Add VAT Table to vats
     * @param TableVat the VAT table
     */
    public void addTableVat(TableVat tableVat) {
	if (vats == null) {
	    vats = new HashSet<TableVat>();
	}
	if (tableVat != null) {
	    tableVat.setDinnerTable(this);
	}
	vats.add(tableVat);
    }

    /**
     * @param cashings the cashings to set
     */
    public void setCashings(Set<TableCashing> cashings) {
	this.cashings = cashings;
    }

    /**
     * @return the cashings
     */
    public Set<TableCashing> getCashings() {
	return cashings;
    }

    /**
     * Add cashing to cashings
     * @param cashing the cashing
     */
    public void addCashing(TableCashing cashing) {
	if (cashings == null) {
	    cashings = new HashSet<TableCashing>();
	}
	if (cashing != null) {
	    cashing.setDinnerTable(this);
	}
	cashings.add(cashing);
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result
		+ ((cashingDate == null) ? 0 : cashingDate.hashCode());
	result = prime * result + ((number == null) ? 0 : number.hashCode());
	result = prime * result
		+ ((restaurant == null) ? 0 : restaurant.hashCode());
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
	DinnerTable other = (DinnerTable) obj;
	if (cashingDate == null) {
	    if (other.cashingDate != null)
		return false;
	} else if (!cashingDate.equals(other.cashingDate))
	    return false;
	if (number == null) {
	    if (other.number != null)
		return false;
	} else if (!number.equals(other.number))
	    return false;
	if (restaurant == null) {
	    if (other.restaurant != null)
		return false;
	} else if (!restaurant.equals(other.restaurant))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "DinnerTable [amountPay=" + amountPay + ", amountsSum="
		+ amountsSum + ", bills=" + bills + ", cashingDate="
		+ cashingDate + ", cashings=" + cashings + ", credits="
		+ credits + ", customersNumber=" + customersNumber
		+ ", number=" + number + ", orders=" + orders
		+ ", printingDate=" + printingDate + ", quantitiesSum="
		+ quantitiesSum + ", reductionRatio=" + reductionRatio
		+ ", reductionRatioChanged=" + reductionRatioChanged
		+ ", registrationDate=" + registrationDate + ", restaurant="
		+ restaurant + ", roo_id=" + roo_id + ", type=" + type
		+ ", user=" + user + ", vats=" + vats + ", deleted=" + deleted
		+ ", id=" + id + "]";
    }

}
