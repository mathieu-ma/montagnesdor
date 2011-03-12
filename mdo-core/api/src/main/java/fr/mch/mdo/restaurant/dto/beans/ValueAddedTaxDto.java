/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dto.beans;

import java.math.BigDecimal;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * This  is a t_value_added_tax mapping.
 * This class is a DTO for the Value Added Tax.
 * 
 * @author Mathieu MA sous conrad
 */
public class ValueAddedTaxDto extends MdoDtoBean
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * This is the code of the value added tax. This is a unique field. 
     * It is a foreign that refers to the t_enum table for type VAT. 
     */
    private MdoTableAsEnumDto code;
    /**
     * This is rate of value added tax.
     */
    private BigDecimal rate;
    
    /**
     * @return the code
     */
    public MdoTableAsEnumDto getCode() {
        return code;
    }
    /**
     * @param code the code to set
     */
    public void setCode(MdoTableAsEnumDto code) {
        this.code = code;
    }
    /**
     * @return the rate
     */
    public BigDecimal getRate() {
        return rate;
    }
    /**
     * @param rate the rate to set
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
	return "ValueAddedTaxDto [code=" + code + ", rate=" + rate + ", id="
		+ id + "]";
    }
}
