package fr.mch.mdo.restaurant.dto.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * @author Mathieu MA
 * 
 */
public class DinnerTableDtoBean extends MdoDtoBean
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Long id;

    private String tableNumber;

    private Boolean takeaway;

    private Integer customersNumber;

    private Boolean allowModifyOrdersAfterPrinting = false;
    
    private BigDecimal reduction;
    
    private BigDecimal amountPay;

    private BigDecimal quantitiesSum;
    private BigDecimal amountsSum;
    private BigDecimal reductionRatio;
    private Date registrationDate;
    private Date printingDate;
    private Date cashingDate;
    private Boolean reductionRatioManuallyChanged = false;
    
    
    private List<OrderLineDtoBean> orders;
    
    public DinnerTableDtoBean()
    {
    }

    public void setTableNumber(String tableNumber)
    {
	this.tableNumber = tableNumber;
    }

    public String getTableNumber()
    {
	return tableNumber;
    }

    public void setReduction(BigDecimal reduction)
    {
	this.reduction = reduction;
    }

    public BigDecimal getReduction()
    {
	return reduction;
    }

    public void setAmountPay(BigDecimal amountPay)
    {
	this.amountPay = amountPay;
    }

    public BigDecimal getAmountPay()
    {
	return amountPay;
    }

    public void setCustomersNumber(Integer customersNumber)
    {
	this.customersNumber = customersNumber;
    }

    public Integer getCustomersNumber()
    {
	return customersNumber;
    }

    public void setTakeaway(Boolean takeaway)
    {
	this.takeaway = takeaway;
    }

    public Boolean getTakeaway()
    {
	return takeaway;
    }

    public void setId(Long id)
    {
	this.id = id;
    }

    public Long getId()
    {
	return id;
    }

    public void setOrders(List<OrderLineDtoBean> orders)
    {
	this.orders = orders;
    }

    public List<OrderLineDtoBean> getOrders()
    {
	return orders;
    }

    public void setQuantitiesSum(BigDecimal quantitiesSum)
    {
	this.quantitiesSum = quantitiesSum;
    }

    public BigDecimal getQuantitiesSum()
    {
	return quantitiesSum;
    }

    public Boolean getAllowModifyOrdersAfterPrinting()
    {
        return allowModifyOrdersAfterPrinting;
    }

    public void setAllowModifyOrdersAfterPrinting(Boolean allowModifyOrdersAfterPrinting)
    {
        this.allowModifyOrdersAfterPrinting = allowModifyOrdersAfterPrinting;
    }

    public BigDecimal getAmountsSum()
    {
        return amountsSum;
    }

    public void setAmountsSum(BigDecimal amountsSum)
    {
        this.amountsSum = amountsSum;
    }

    public BigDecimal getReductionRatio()
    {
        return reductionRatio;
    }

    public void setReductionRatio(BigDecimal reductionRatio)
    {
        this.reductionRatio = reductionRatio;
    }

    public Date getRegistrationDate()
    {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate)
    {
        this.registrationDate = registrationDate;
    }

    public Date getPrintingDate()
    {
        return printingDate;
    }

    public void setPrintingDate(Date printingDate)
    {
        this.printingDate = printingDate;
    }

    public Date getCashingDate()
    {
        return cashingDate;
    }

    public void setCashingDate(Date cashingDate)
    {
        this.cashingDate = cashingDate;
    }

    public Boolean getReductionRatioManuallyChanged()
    {
        return reductionRatioManuallyChanged;
    }

    public void setReductionRatioManuallyChanged(Boolean reductionRatioManuallyChanged)
    {
        this.reductionRatioManuallyChanged = reductionRatioManuallyChanged;
    }
}
