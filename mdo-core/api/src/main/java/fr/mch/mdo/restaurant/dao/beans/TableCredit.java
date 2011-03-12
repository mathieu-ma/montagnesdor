/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * This class is used for t_table_credit mapping. 
 * This table is used for dinner table credits. 
 * This table is used for credits dinner tables association. 
 * For a given dinner table, we could have several credits but often just one. 
 * These credits must have the cre_closing_date value equals to null.
 * 
 * @author Mathieu MA sous conrad
 */
public class TableCredit extends MdoDaoBean 
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * This is a foreign key that refers to t_dinner_table. 
     * It is used to specify the dinner table. 
     * This field and the others cre_id consist of a unique field.
     */
    private DinnerTable dinnerTable;
    /**
     * This is a foreign key that refers to t_credit. 
     * It is used to specify the credit of the dinner table. 
     * This field and the others dtb_id consist of a unique field.
     */
    private Credit credit;
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
     * @return the credit
     */
    public Credit getCredit() {
        return credit;
    }
    /**
     * @param credit the credit to set
     */
    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1; // DO NOT call super.hashCode(); because ID could be null.
	result = prime * result + ((credit == null || credit.getId() == null) ? 0 : credit.getId().hashCode());
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
	TableCredit other = (TableCredit) obj;
	if (credit == null) {
	    if (other.credit != null) {
		return false;
	    }
	} else if (credit.getId() == null) {
	    if (other.credit.getId() != null) {
		return false;
	    }
	} else if (other.credit == null || !credit.getId().equals(other.credit.getId())) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "TableCredit [credit=" + credit + ", deleted=" + deleted
		+ ", id=" + id + "]";
    }
}
