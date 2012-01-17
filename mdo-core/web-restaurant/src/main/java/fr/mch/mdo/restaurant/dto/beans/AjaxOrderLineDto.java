/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dto.beans;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * This class is used for t_order_line mapping. This table is used for order
 * lines depending on the specific dinner table.
 * 
 * @author Mathieu MA sous conrad
 */
public class AjaxOrderLineDto implements Serializable
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is the primary key of the Order Line.
	 */
	private Long id;
	/**
	 * This is the primary key of the deleted Order Line.
	 */
	private Long deletedId;
	/**
	 * This is the color of the Order Line.
	 */
	private String color;
	/**
	 * This is the quantity of the product.
	 */
	private BigDecimal quantity;
	/**
	 * code will contains product special code + product code.
	 */
	private String code;
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
	private BigDecimal price;
	/**
	 * This is the amount of the order line. The value is equals to orl_quantity
	 * multiply by orl_unit_price.
	 */
	private BigDecimal amount;
	/**
	 * This is a foreign key that refers to t_dinner_table. It is used to
	 * specify the dinner table.
	 */
	private Long dinnerTableId;
	/**
	 * This is the dinner table number. It is used for performance instead of making query to database.
	 */
	private String dinnerTableNumber;
	/** 
	 * Currently the dataCode is used for merging 2 rows
	 */
	private String dataCode;

	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the deletedId
	 */
	public Long getDeletedId() {
		return deletedId;
	}
	/**
	 * @param deletedId the deletedId to set
	 */
	public void setDeletedId(Long deletedId) {
		this.deletedId = deletedId;
	}
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
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
	 * @return the dinnerTableNumber
	 */
	public String getDinnerTableNumber() {
		return dinnerTableNumber;
	}
	/**
	 * @param dinnerTableNumber the dinnerTableNumber to set
	 */
	public void setDinnerTableNumber(String dinnerTableNumber) {
		this.dinnerTableNumber = dinnerTableNumber;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AjaxOrderLineDto [id=" + id + ", deletedId=" + deletedId
				+ ", color=" + color + ", quantity=" + quantity + ", code="
				+ code + ", label=" + label + ", price=" + price + ", amount="
				+ amount + ", dinnerTableId=" + dinnerTableId + ", dataCode="
				+ dataCode + "]";
	}
}
