/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;

/**
 * @author Mathieu MA sous conrad
 *
 *	Cette classe est un mapping de la table t_diner_table.
 */
public class CreditTable extends MdoDaoBean
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 	tcr_id
     * 	dtb_id
     * 	cre_id
     * 	tcr_deleted
     */
    private Long id;
    private DinnerTable dinnerTable;
    private Credit credit;
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
    public Boolean getDeleted()
    {
        return deleted;
    }
    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }
    
    public Credit getCredit()
    {
        return credit;
    }
    public void setCredit(Credit credit)
    {
        this.credit = credit;
    }

    @Override
    public String toString()
    {
        return "::"+this.id+"::";
    }
}
