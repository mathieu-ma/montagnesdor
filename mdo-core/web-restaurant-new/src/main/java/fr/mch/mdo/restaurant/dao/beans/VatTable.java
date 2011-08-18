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
import fr.mch.mdo.restaurant.dao.beans.ValueAddedTax;

/**
 * @author Mathieu MA sous conrad
 * 
 *         Cette classe est un mapping de la table t_diner_table.
 */
public class VatTable extends MdoDaoBean
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * vtt_id serial NOT NULL, 
     * dtb_id int8 NOT NULL, 
     * vat_id int8 NOT NULL DEFAULT 1, 
     * vtt_amount numeric(10,2) NOT NULL DEFAULT 0.00, 
     * vtt_value numeric(10,2) NOT NULL DEFAULT 0.00,
     */

    private Long id;
    private DinnerTable dinnerTable;
    private ValueAddedTax vat;
    private BigDecimal amount;
    private BigDecimal value;

    public DinnerTable getDinnerTable()
    {
	return dinnerTable;
    }

    public void setDinnerTable(DinnerTable dinnerTable)
    {
	this.dinnerTable = dinnerTable;
    }

    public Long getId()
    {
	return id;
    }

    public void setId(Long id)
    {
	this.id = id;
    }

    public BigDecimal getValue()
    {
	return value;
    }

    public void setValue(BigDecimal value)
    {
	this.value = value;
    }

    public ValueAddedTax getVat()
    {
	return vat;
    }

    public void setVat(ValueAddedTax vat)
    {
	this.vat = vat;
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
        return "::"+this.id+"::"+this.value+"::"+this.amount+"::";
    }
}
