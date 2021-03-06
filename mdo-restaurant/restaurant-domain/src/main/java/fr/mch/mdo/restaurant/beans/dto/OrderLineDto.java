package fr.mch.mdo.restaurant.beans.dto;

import java.math.BigDecimal;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * @author Mathieu MA
 * 
 */
public class OrderLineDto extends MdoDtoBean
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long dinnerTableId;

    private BigDecimal quantity;
    private String code;
    private String label;
    private BigDecimal unitPrice;
    private BigDecimal amount;

    private Long vatId;

    private ProductDto product;
    private ProductSpecialCodeDto productSpecialCode;

    public OrderLineDto()
    {
    }

	/**
	 * @return the dinnerTableId
	 */
	public Long getDinnerTableId() {
		return dinnerTableId;
	}

	/**
	 * @param dinnerTableId the dinnerTableId to set
	 */
	public void setDinnerTableId(Long dinnerTableId) {
		this.dinnerTableId = dinnerTableId;
	}

	/**
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
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
	 * @return the unitPrice
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
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

	/**
	 * @return the product
	 */
	public ProductDto getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(ProductDto product) {
		this.product = product;
	}

	/**
	 * @return the productSpecialCode
	 */
	public ProductSpecialCodeDto getProductSpecialCode() {
		return productSpecialCode;
	}

	/**
	 * @param productSpecialCode the productSpecialCode to set
	 */
	public void setProductSpecialCode(ProductSpecialCodeDto productSpecialCode) {
		this.productSpecialCode = productSpecialCode;
	}

	@Override
	public String toString() {
		return "OrderLineDto [dinnerTableId=" + dinnerTableId 
				+ ", quantity=" + quantity + ", code=" + code
				+ ", label=" + label + ", unitPrice=" + unitPrice 
				+ ", amount=" + amount + ", product=" + product 
				+ ", vatId=" + vatId 
				+ ", productSpecialCode=" + productSpecialCode
				+ ", id=" + id + "]";
	}

}
