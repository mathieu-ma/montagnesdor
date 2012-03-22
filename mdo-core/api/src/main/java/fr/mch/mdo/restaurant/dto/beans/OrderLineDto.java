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
 * This class is used for t_order_line mapping. This table is used for order
 * lines depending on the specific dinner table.
 * 
 * @author Mathieu MA sous conrad
 */
public class OrderLineDto extends MdoDtoBean
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is a foreign key that refers to t_dinner_table. It is used to
	 * specify the dinner table.
	 */
	private DinnerTableDto dinnerTable;
	/**
	 * This is a foreign key that refers to t_product_special_code. It is used
	 * to specify the product special code. This code is never null, it takes a
	 * default value with "". The code could be for example "/", "-", "#", "@".
	 */
	private ProductSpecialCodeDto productSpecialCode;
	/**
	 * This is a foreign key that refers to t_product. It is used to specify the
	 * product. If this field is null then the order line depends on the product
	 * special code psc_id which must not be null.
	 */
	private ProductDto product;
	/**
	 * This is a foreign key that refers to t_credit. It is used to specify the
	 * credit consumed. If this field is NOT null, then the order line depends
	 * on the product special code psc_id which must not be null, with code
	 * value equals to @.
	 */
	private CreditDto credit;
	/**
	 * This is a foreign key that refers to t_product_part. It is used to
	 * specify the product part the order line belongs: ENTREE, PLAT or DESSERT
	 * for example.
	 */
	private ProductPartDto productPart;
	/**
	 * This is a foreign key that refers to t_value_added_tax. 
	 * It is used to specify the Value Added Tax.
	 * Usually, the VAT of order line depends directly on the product.
	 * But in some case, the order line is not in the products catalog, 
	 * so this order line is manually entered and the VAT is set by default but can be changed on demand.
	 * It is used to calculate the vat amount of this order line. 
	 */
	private ValueAddedTaxDto vat;
	/**
	 * This is the quantity of the product.
	 */
	private BigDecimal quantity;
	/**
	 * This is the label of the product. If the psc_id is of type "/" then the
	 * label is the user entry description. If the psc_id is null then the label
	 * is the label of the product pdt_id depending on the user locale.
	 */
	private String label;
	/**
	 * This is the unit price of the order line. Here, we do not take into
	 * account the quantity.
	 */
	private BigDecimal unitPrice;
	/**
	 * This is the amount of the order line. The value is equals to orl_quantity
	 * multiply by orl_unit_price.
	 */
	private BigDecimal amount;

	/**
	 * code will contains product special code + product code.
	 */
	private String code;

	/** 
	 * Currently the dataCode is used for merging 2 rows
	 */
	private String dataCode;
	
	/**
	 * @return the dinnerTable
	 */
	public DinnerTableDto getDinnerTable() {
		return dinnerTable;
	}

	/**
	 * @param dinnerTable
	 *            the dinnerTable to set
	 */
	public void setDinnerTable(DinnerTableDto dinnerTable) {
		this.dinnerTable = dinnerTable;
	}

	/**
	 * @return the productSpecialCode
	 */
	public ProductSpecialCodeDto getProductSpecialCode() {
		return productSpecialCode;
	}

	/**
	 * @param productSpecialCode
	 *            the productSpecialCode to set
	 */
	public void setProductSpecialCode(ProductSpecialCodeDto productSpecialCode) {
		this.productSpecialCode = productSpecialCode;
	}

	/**
	 * @return the product
	 */
	public ProductDto getProduct() {
		return product;
	}

	/**
	 * @param product
	 *            the product to set
	 */
	public void setProduct(ProductDto product) {
		this.product = product;
	}

	/**
	 * @param credit
	 *            the credit to set
	 */
	public void setCredit(CreditDto credit) {
		this.credit = credit;
	}

	/**
	 * @return the credit
	 */
	public CreditDto getCredit() {
		return credit;
	}

	/**
	 * @return the productPart
	 */
	public ProductPartDto getProductPart() {
		return productPart;
	}

	/**
	 * @param productPart
	 *            the productPart to set
	 */
	public void setProductPart(ProductPartDto productPart) {
		this.productPart = productPart;
	}

	/**
	 * @return the vat
	 */
	public ValueAddedTaxDto getVat() {
		return vat;
	}

	/**
	 * @param vat the vat to set
	 */
	public void setVat(ValueAddedTaxDto vat) {
		this.vat = vat;
	}

	/**
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
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
	 * @param unitPrice
	 *            the unitPrice to set
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
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the dataCode
	 */
	public String getDataCode() {
		return dataCode;
	}

	/**
	 * @param dataCode the dataCode to set
	 */
	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	@Override
	public String toString() {
		return "OrderLine [amount=" + amount + ", credit=" + credit + ", label=" + label + ", product=" + product 
				+ ", productPart=" + productPart + ", productSpecialCode=" + productSpecialCode + ", vat=" + vat
				+ ", quantity=" + quantity + ", unitPrice=" + unitPrice + ", id=" + id + "]";
	}
}
