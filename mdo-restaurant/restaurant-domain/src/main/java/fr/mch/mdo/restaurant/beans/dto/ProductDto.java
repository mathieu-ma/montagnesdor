package fr.mch.mdo.restaurant.beans.dto;

import java.math.BigDecimal;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * @author Mathieu MA
 * 
 */
public class ProductDto extends MdoDtoBean
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String code;
    private BigDecimal price;
    private String colorRGB;
    private String label;
    
    private Long vatId;

    public ProductDto()
    {
    }

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the colorRGB
	 */
	public String getColorRGB() {
		return colorRGB;
	}

	/**
	 * @param colorRGB the colorRGB to set
	 */
	public void setColorRGB(String colorRGB) {
		this.colorRGB = colorRGB;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the vatId
	 */
	public Long getVatId() {
		return vatId;
	}

	/**
	 * @param vatId the vatId to set
	 */
	public void setVatId(Long vatId) {
		this.vatId = vatId;
	}

	@Override
	public String toString() {
		return "ProductDetails [code=" + code + ", price=" + price
				+ ", colorRGB=" + colorRGB + ", label=" + label 
				+ ", vatId=" + vatId
				+ ", id=" + id
				+ "]";
	}
}
