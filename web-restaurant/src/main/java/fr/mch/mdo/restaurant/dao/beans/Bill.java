/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import java.math.BigDecimal;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;


/**
 * @author Mathieu MA sous conrad
 *
 *	Cette classe est un mapping de la table t_bill.
 */
public class Bill extends MdoDaoBean
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     *  bill_id
	dtb_id
	bil_reference
	bil_order
	bil_meal_number
	bil_amount
	bil_printing
	bil_deleted
     * 
     */
    private Long id;
    private DinnerTable dinnerTable;
    private String reference;
    private Integer order;
    private Integer mealNumber;
    private BigDecimal amount;
    private Boolean printed;
    private Boolean deleted;
    
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public DinnerTable getDinnerTable()
    {
        return dinnerTable;
    }
    public void setDinnerTable(DinnerTable dinnerTable)
    {
        this.dinnerTable = dinnerTable;
    }
    public String getReference()
    {
        return reference;
    }
    public void setReference(String reference)
    {
        this.reference = reference;
    }
    public Integer getOrder()
    {
        return order;
    }
    public void setOrder(Integer order)
    {
        this.order = order;
    }
    public Integer getMealNumber()
    {
        return mealNumber;
    }
    public void setMealNumber(Integer mealNumber)
    {
        this.mealNumber = mealNumber;
    }
    public Boolean getPrinted()
    {
        return printed;
    }
    public void setPrinted(Boolean printed)
    {
        this.printed = printed;
    }
    public Boolean getDeleted()
    {
        return deleted;
    }
    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }
    
    public BigDecimal getAmount()
    {
        return amount;
    }
    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    @Override
    public String toString()
    {
        return "::"+this.id+"::"+this.reference+"::"+this.mealNumber+"::"+this.amount+"::";
    }
}
