package fr.mch.mdo.restaurant.dto.beans;

import java.math.BigDecimal;

/**
 * @author Mathieu MA
 * 
 */
public class ProductDtoBean extends MdoDtoBean
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private BigDecimal price;
    private String colorRGB;
    private String label;

    public ProductDtoBean()
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

    public void setPrice(BigDecimal price)
    {
	this.price = price;
    }

    public BigDecimal getPrice()
    {
	return price;
    }

    public void setColorRGB(String colorRGB)
    {
	this.colorRGB = colorRGB;
    }

    public String getColorRGB()
    {
	return colorRGB;
    }
}
