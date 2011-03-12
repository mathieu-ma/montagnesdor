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
 * This class is a t_table_cashing mapping. 
 * This table is used for cashing of dinner table depending on type of cashing.
 * 
 * @author Mathieu
 */
public class TableCashing extends MdoDaoBean 
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * This is a foreign key that refers to t_dinner_table.
     * It is used to specify the dinner table.
     * This field and the other tcs_type_enum_id fields consist of a unique field.
     */
    private DinnerTable dinnerTable;
    /**
     * This is a foreign key that refers to t_enum.
     * It is used to specify the type of cashing.
     * It could be
     * GENERIC_CASH, EURO_CASH, DOLLAR_CASH, 
     * GENERIC_TICKET, MEAL_TICKET, HOLIDAYS_TICKET, 
     * GENERIC_CHECK, BNP_CHECK, 
     * GENERIC_CARD, VISA_CARD, MASTER_CARD, 
     * UNPAID...
     * This field and the other dtb_id fields consist of a unique field.
     */
    private MdoTableAsEnum type;
    /**
     * This is the value of the dinner table depending on the specific type of cashing.
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
     * @return the type
     */
    public MdoTableAsEnum getType() {
        return type;
    }
    /**
     * @param code the code to set
     */
    public void setType(MdoTableAsEnum type) {
        this.type = type;
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
	TableCashing other = (TableCashing) obj;
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
	return "CashingTable [type=" + type + ", value=" + value + ", deleted="
		+ deleted + ", id=" + id + "]";
    }

}
