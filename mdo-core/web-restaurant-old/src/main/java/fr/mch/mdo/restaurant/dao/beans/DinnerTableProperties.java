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
 * @author Mathieu MA sous conrad
 * 
 *         Cette classe est un mapping de la table t_diner_table.
 */
public class DinnerTableProperties extends MdoDaoBean
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
    private Long userId;
    private Long roo_id;
    private String number;
    private Integer customersNumber;
    private BigDecimal quantitiesSum;
    private BigDecimal amountsSum;
    private BigDecimal amountPay;
    private BigDecimal reductionRatio;
    private Date registrationDate;
    private Date printDate;
    private Date cashingDate;
    private Boolean reductionRatioChanged = false;
    private Boolean takeaway = false;

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
    public int getCustomersNumber()
    {
	return customersNumber;
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
    public Date getPrintDate()
    {
	return printDate;
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
    public Boolean isReductionRatioChanged()
    {
	return reductionRatioChanged;
    }

    /**
     * @return
     */
    public Date getRegistrationDate()
    {
	return registrationDate;
    }

    /**
     * @param f
     */
    public void setAmountsSum(BigDecimal f)
    {
	amountsSum = f;
    }

    /**
     * @param cashingDate
     */
    public void setCashingDate(Date cashingDate)
    {
	this.cashingDate = cashingDate;
    }

    /**
     * @param customersNumber
     */
    public void setCustomersNumber(Integer customersNumber)
    {
	this.customersNumber = customersNumber;
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
    public void setPrintDate(Date date)
    {
	printDate = date;
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
    public void setReductionRatioChanged(Boolean b)
    {
	reductionRatioChanged = b;
    }

    /**
     * @param date
     */
    public void setRegistrationDate(Date date)
    {
	registrationDate = date;
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

    public BigDecimal getAmountPay()
    {
        return amountPay;
    }

    public void setAmountPay(BigDecimal amountPay)
    {
        this.amountPay = amountPay;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public void setRoo_id(Long roo_id)
    {
        this.roo_id = roo_id;
    }

    public Long getRoo_id()
    {
        return roo_id;
    }
}
