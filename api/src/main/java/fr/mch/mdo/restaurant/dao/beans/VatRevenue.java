/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;


/**
 * @author Mathieu MA sous conrad
 *
 *	Cette classe est un mapping de la table t_diner_table.
 */
public class VatRevenue extends MdoDaoBean
{
	/*
  vtr_id serial,
  drv_id int8 NOT NULL,
  vat_id int8 NOT NULL DEFAULT 1,
  vtr_amount numeric(10,2) NOT NULL DEFAULT 0.00,
  vtr_value numeric(10,2) NOT NULL DEFAULT 0.00,
	 */

	private Revenue dayRevenue;
	private ValueAddedTax vat;
	private float amount;
	private float value;

    public Revenue getDayRevenue()
    {
        return dayRevenue;
    }
    public void setDayRevenue(Revenue dayRevenue)
    {
        this.dayRevenue = dayRevenue;
    }
 
    public float getValue()
    {
        return value;
    }
    public void setValue(float value)
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
    public float getAmount()
    {
        return amount;
    }
    public void setAmount(float amount)
    {
        this.amount = amount;
    }
}
