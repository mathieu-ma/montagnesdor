/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * @author Mathieu MA sous conrad
 * 
 *         Cette classe est un mapping de la table t_diner_table.
 */
public class DinnerTable extends MdoDaoBean
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * tbd_id serial, usr_id int8 NOT NULL DEFAULT 0, roo_id int8 NOT NULL
     * DEFAULT 0, tbd_number varchar(5) NOT NULL, tbd_customers_number int8 NOT
     * NULL DEFAULT 0, tbd_quantities_sum numeric(10,2) NOT NULL DEFAULT 0.00,
     * tbd_amounts_sum numeric(10,2) NOT NULL DEFAULT 0.00, tbd_reduction_ratio
     * numeric(10,2) NOT NULL DEFAULT 0.00, tbd_amount_pay numeric(10,2) NOT
     * NULL DEFAULT 0.00, tbd_registration_date timestamp, tbd_print_date
     * timestamp, tbd_cashing_date timestamp, tbd_reduction_ratio_changed bool
     * NOT NULL DEFAULT false, dtb_takeaway bool NOT NULL DEFAULT false,
     */
    private UserAuthentication user;
    private Long roo_id;
    private String number;
    private Integer customersNumber;
    private BigDecimal quantitiesSum = new BigDecimal(0);
    private BigDecimal quantitiesSumByFormula;
    private BigDecimal amountsSum = new BigDecimal(0);
    private BigDecimal amountPay = new BigDecimal(0);
    private BigDecimal reductionRatio = new BigDecimal(0);
    private Date registrationDate;
    private Date printingDate;
    private Date cashingDate;
    private Boolean reductionRatioManuallyChanged = false;
    private Boolean takeaway = false;
    // List of orders
    private Set<OrderLine> orders;
    // List of bills
    private Set<Bill> bills;
    // List of credits
    private Set<CreditTable> credits;

    // Liste des valeurs des tva de cette table
    private Map<Long, ValueAddedTax> vats;

    public BigDecimal getCreditsAmount()
    {
	BigDecimal result = new BigDecimal(0);
	if (credits != null)
	{
	    for (Iterator<CreditTable> iterator = credits.iterator(); iterator.hasNext();)
	    {
		CreditTable credit = (CreditTable) iterator.next();
		result.add(credit.getCredit().getAmount());
	    }
	}
	return result;
    }

    public int getCreditsSize()
    {
	return credits != null ? credits.size() : 0;
    }

    /**
     * @return
     */
    public BigDecimal getAmountsSum()
    {
	return amountsSum;
    }

    /**
     * @return
     */
    public Date getCashingDate()
    {
	return cashingDate;
    }

    /**
     * @return
     */
    public String getNumber()
    {
	return number;
    }

    /**
     * @return
     */
    public Date getPrintingDate()
    {
	return printingDate;
    }

    /**
     * @return
     */
    public BigDecimal getQuantitiesSum()
    {
	return quantitiesSum;
    }

    /**
     * @return
     */
    public BigDecimal getReductionRatio()
    {
	return reductionRatio;
    }

    /**
     * @return
     */
    public Boolean isReductionRatioManuallyChanged()
    {
	return reductionRatioManuallyChanged;
    }

    /**
     * @return
     */
    public Date getRegistrationDate()
    {
	return registrationDate;
    }

    /**
     * @return
     */
    public Long getRoo_id()
    {
	return roo_id;
    }

    /**
     * @param f
     */
    public void setAmountsSum(BigDecimal f)
    {
	amountsSum = f;
    }

    /**
     * @param date
     */
    public void setCashingDate(Date date)
    {
	cashingDate = date;
    }

    /**
     * @param string
     */
    public void setNumber(String string)
    {
	number = string;
    }

    /**
     * @param date
     */
    public void setPrintingDate(Date date)
    {
	printingDate = date;
    }

    /**
     * @param f
     */
    public void setQuantitiesSum(BigDecimal f)
    {
	quantitiesSum = f;
    }

    /**
     * @param f
     */
    public void setReductionRatio(BigDecimal f)
    {
	reductionRatio = f;
    }

    /**
     * @param b
     */
    public void setReductionRatioManuallyChanged(Boolean b)
    {
	reductionRatioManuallyChanged = b;
    }

    /**
     * @param date
     */
    public void setRegistrationDate(Date date)
    {
	registrationDate = date;
    }

    /**
     * @param l
     */
    public void setRoo_id(Long l)
    {
	roo_id = l;
    }

    /**
     * @return
     */
    public Set<OrderLine> getOrders()
    {
	return orders;
    }

    /**
     * @param set
     */
    public void setOrders(Set<OrderLine> list)
    {
	orders = list;
    }

    /**
     * @return
     */
    public UserAuthentication getUser()
    {
	return user;
    }

    /**
     * @param user
     */
    public void setUser(UserAuthentication user)
    {
	this.user = user;
    }

    /**
     * @return
     */
    public Boolean isTakeaway()
    {
	return takeaway;
    }

    /**
     * @param b
     */
    public void setTakeaway(Boolean b)
    {
	takeaway = b;
    }

    /**
     * @return
     */
    public BigDecimal getReduction()
    {
	// return reduction =
	// FormatDecimal.specificRound(amountsSum*reductionRatio/100f,
	// Restaurant.specificRound);
	return new BigDecimal(0);
    }

    public Map<Long, ValueAddedTax> getVats()
    {
	return vats;
    }

    public void setVats(Map<Long, ValueAddedTax> vats)
    {
	this.vats = vats;
    }

    /**
     * @return Renvoie bills.
     */
    public Set<Bill> getBills()
    {
	return bills;
    }

     public Set<CreditTable> getCredits()
    {
	return credits;
    }

    public BigDecimal getAmountPay()
    {
        return amountPay;
    }

    public void setAmountPay(BigDecimal amountPay)
    {
        this.amountPay = amountPay;
    }

    public void setBills(Set<Bill> bills)
    {
        this.bills = bills;
    }

    public void setCredits(Set<CreditTable> credits)
    {
        this.credits = credits;
    }

    public void setCustomersNumber(Integer customersNumber)
    {
	this.customersNumber = customersNumber;
    }

    public Integer getCustomersNumber()
    {
	return customersNumber;
    }

    public void setQuantitiesSumByFormula(BigDecimal quantitiesSumByFormula)
    {
	this.quantitiesSumByFormula = quantitiesSumByFormula;
    }

    public BigDecimal getQuantitiesSumByFormula()
    {
	return quantitiesSumByFormula;
    }
}
