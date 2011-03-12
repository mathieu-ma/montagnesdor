/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import java.math.BigDecimal;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * This class is used for t_order_line mapping. 
 * This table is used for order lines depending on the specific dinner table.
 * 
 * @author Mathieu MA sous conrad
 */
public class OrderLine extends MdoDaoBean 
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * This is a foreign key that refers to t_dinner_table. 
     * It is used to specify the dinner table.
     */
    private DinnerTable dinnerTable;
    /**
     * This is a foreign key that refers to t_product_special_code.
     * It is used to specify the product special code.
     * This code is never null, it takes a default value with "".
     * The code could be for example "/", "-", "#", "@".
     */
    private ProductSpecialCode productSpecialCode;
    /**
     * This is a foreign key that refers to t_product.
     * It is used to specify the product.
     * If this field is null then the order line depends on the product special code psc_id which must not be null.
     */
    private Product product;
    /**
     * This is a foreign key that refers to t_credit.
     * It is used to specify the credit consumed. 
     * If this field is NOT null,
     * then the order line depends on the product special code psc_id which must not be null,
     * with code value equals to @.
     */
    private Credit credit;
    /**
     * This is a foreign key that refers to t_product_part.
     * It is used to specify the product part the order line belongs: ENTREE, PLAT or DESSERT for example.
     */
    private ProductPart productPart;
    /**
     * This is the quantity of the product.
     */
    private BigDecimal quantity;
    /**
     * This is the label of the product. 
     * If the psc_id is of type "/" then the label is the user entry description. 
     * If the psc_id is null then the label is the label of the product pdt_id depending on the user locale.
     */
    private String label;
    /**
     * This is the unit price of the order line. 
     * Here, we do not take into account the quantity.
     */
    private BigDecimal unitPrice;
    /**
     * This is the amount of the order line.
     * The value is equals to orl_quantity multiply by orl_unit_price.
     */
    private BigDecimal amount;
    
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
     * @return the productSpecialCode
     */
    public ProductSpecialCode getProductSpecialCode() {
        return productSpecialCode;
    }
    /**
     * @param productSpecialCode the productSpecialCode to set
     */
    public void setProductSpecialCode(ProductSpecialCode productSpecialCode) {
        this.productSpecialCode = productSpecialCode;
    }
    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }
    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }
    /**
     * @param credit the credit to set
     */
    public void setCredit(Credit credit) {
	this.credit = credit;
    }
    /**
     * @return the credit
     */
    public Credit getCredit() {
	return credit;
    }
    /**
     * @return the productPart
     */
    public ProductPart getProductPart() {
        return productPart;
    }
    /**
     * @param productPart the productPart to set
     */
    public void setProductPart(ProductPart productPart) {
        this.productPart = productPart;
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

    @Override
    public String toString() {
	return "OrderLine [amount=" + amount + ", credit=" + credit + ", label=" + label
		+ ", product=" + product + ", productPart=" + productPart
		+ ", productSpecialCode=" + productSpecialCode + ", quantity="
		+ quantity + ", unitPrice=" + unitPrice + ", deleted="
		+ deleted + ", id=" + id + "]";
    }

}
