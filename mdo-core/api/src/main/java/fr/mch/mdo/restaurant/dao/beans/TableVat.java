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
 * This class is used for t_table_vat mapping. 
 * This table is used for amounts and values of dinner table depending on the Value Added Tax.
 * 
 * @author Mathieu MA sous conrad
 */
public class TableVat extends MdoDaoBean 
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * This is a foreign key that refers to t_dinner_table.
     * It is used to specify the dinner table.
     * This field and the other vat_id field consist of a unique field.
     */
    private DinnerTable dinnerTable;
    /**
     * This is a foreign key that refers to t_value_added_tax.
     * It is used to specify the Value Added Tax.
     * This field and the other dtb_id field consist of a unique field.
     */
    private ValueAddedTax vat;
    /**
     * This is the amount of the dinner table depending on the specific Value Added Tax.
     * If the dinner table has order lines with Value Added Tax rate of 5.5, 
     * then the amount is the sum of the amount of dinner table order lines with the specific Value Added Tax rate 5.5.
     */
    private BigDecimal amount;
    /**
     * This is the value of the dinner table depending on the specific Value Added Tax.
     * If the Value Added Tax rate is 19.6 then the value is equals to tvt_amount*19.6./(19.6+100).
     */
    private BigDecimal value;
    
    /**
     * @return the dinnerTable
     */
    public DinnerTable getDinnerTable() {
        return dinnerTable;
    }
    /**
     * @param dinnerTable the dinnerTable to set
     */
    public void setDinnerTable(DinnerTable dinnerTable) {
        this.dinnerTable = dinnerTable;
    }
    /**
     * @return the vat
     */
    public ValueAddedTax getVat() {
        return vat;
    }
    /**
     * @param vat the vat to set
     */
    public void setVat(ValueAddedTax vat) {
        this.vat = vat;
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
     * @return the value
     */
    public BigDecimal getValue() {
        return value;
    }
    /**
     * @param value the value to set
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1; // DO NOT call super.hashCode(); because ID could be null.
	result = prime * result + ((vat == null || vat.getId() == null) ? 0 : vat.getId().hashCode());
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
	TableVat other = (TableVat) obj;
	if (vat == null) {
	    if (other.vat != null) {
		return false;
	    }
	} else if (vat.getId() == null) {
	    if (other.vat.getId() != null) {
		return false;
	    }
	} else if (other.vat == null || !vat.getId().equals(other.vat.getId())) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "VatTable [amount=" + amount + ", value=" + value 
	+ ", vat=" + vat + ", deleted=" + deleted + ", id=" + id + "]";
    }

}
