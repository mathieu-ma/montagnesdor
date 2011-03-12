package fr.mch.mdo.restaurant.dto.beans;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Mathieu MA
 * 
 */
public class TablesOrdersDtoBean extends MdoDtoBean
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String prefixTableNumber = "";

    private String prefixProductCode = "";

    private Boolean allowModifyOrdersAfterPrinting = false;

    private BigDecimal reduction;
    
    private BigDecimal amountPay;

    private DinnerTableDtoBean dinnerTableDtoBean = new DinnerTableDtoBean();
    
    private OrderLineDtoBean orderLine = new OrderLineDtoBean();
    
    /**
     * Hash key == DinnerTable id
     * Hash value == DinnerTable number(name)
     */
    private Map<Long, String> tablesNames;
    
    /**
     * Hash key == Product id
     * Hash value == Product code
     */
    private Map<Long, String> productsCodes;
    
    public TablesOrdersDtoBean()
    {
	dinnerTableDtoBean = new DinnerTableDtoBean();
    }

    public String getPrefixTableNumber()
    {
        return prefixTableNumber;
    }

    public void setPrefixTableNumber(String prefixTableNumber)
    {
        this.prefixTableNumber = prefixTableNumber;
    }

    public Map<Long, String> getTablesNames()
    {
        return tablesNames;
    }

    public void setTablesNames(Map<Long, String> tablesNames)
    {
        this.tablesNames = tablesNames;
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

    public void setAllowModifyOrdersAfterPrinting(boolean allowModifyOrdersAfterPrinting)
    {
	this.allowModifyOrdersAfterPrinting = allowModifyOrdersAfterPrinting;
    }

    public boolean isAllowModifyOrdersAfterPrinting()
    {
	return allowModifyOrdersAfterPrinting;
    }

    public DinnerTableDtoBean getDinnerTableDtoBean()
    {
        return dinnerTableDtoBean;
    }

    public void setDinnerTableDtoBean(DinnerTableDtoBean dinnerTableDtoBean)
    {
        this.dinnerTableDtoBean = dinnerTableDtoBean;
    }

    public String getPrefixProductCode()
    {
        return prefixProductCode;
    }

    public void setPrefixProductCode(String prefixProductCode)
    {
        this.prefixProductCode = prefixProductCode;
    }

    public Map<Long, String> getProductsCodes()
    {
        return productsCodes;
    }

    public void setProductsCodes(Map<Long, String> productsCodes)
    {
        this.productsCodes = productsCodes;
    }

    public void setOrderLine(OrderLineDtoBean orderLine)
    {
	this.orderLine = orderLine;
    }

    public OrderLineDtoBean getOrderLine()
    {
	return orderLine;
    }
}
