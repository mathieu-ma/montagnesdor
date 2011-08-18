package fr.mch.mdo.restaurant.dto.beans;

import java.math.BigDecimal;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * @author Mathieu MA
 * 
 */
public class OrderLineDtoBean extends MdoDtoBean
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    private BigDecimal quantity;
    private String code;
    private String label;
    private BigDecimal unitPrice;
    private BigDecimal amount;
    
    private Boolean processNextField = Boolean.FALSE;
    
    ProductDtoBean product = new ProductDtoBean();
    
    public OrderLineDtoBean()
    {
    }

    public void setId(Long id)
    {
	this.id = id;
    }

    public Long getId()
    {
	return id;
    }

    public BigDecimal getQuantity()
    {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity)
    {
        this.quantity = quantity;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public BigDecimal getUnitPrice()
    {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice)
    {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public ProductDtoBean getProduct()
    {
        return product;
    }

    public void setProduct(ProductDtoBean product)
    {
        this.product = product;
    }

    /**
     * @param processNextField the processNextField to set
     */
    public void setProcessNextField(Boolean processNextField)
    {
	this.processNextField = processNextField;
    }

    /**
     * @return the processNextField
     */
    public Boolean getProcessNextField()
    {
	return processNextField;
    }

}
