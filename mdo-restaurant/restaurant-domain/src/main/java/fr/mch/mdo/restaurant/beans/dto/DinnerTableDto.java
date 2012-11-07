package fr.mch.mdo.restaurant.beans.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * @author Mathieu MA
 * 
 */
public class DinnerTableDto extends MdoDtoBean {
	/**
     * Default Serial Version UID.
     */
	private static final long serialVersionUID = 1L;

	private String number;

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

	private List<OrderLineDto> orders;

	public DinnerTableDto() {
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the takeaway
	 */
	public Boolean getTakeaway() {
		return takeaway;
	}

	/**
	 * @param takeaway the takeaway to set
	 */
	public void setTakeaway(Boolean takeaway) {
		this.takeaway = takeaway;
	}

	/**
	 * @return the customersNumber
	 */
	public Integer getCustomersNumber() {
		return customersNumber;
	}

	/**
	 * @param customersNumber the customersNumber to set
	 */
	public void setCustomersNumber(Integer customersNumber) {
		this.customersNumber = customersNumber;
	}

	/**
	 * @return the allowModifyOrdersAfterPrinting
	 */
	public Boolean getAllowModifyOrdersAfterPrinting() {
		return allowModifyOrdersAfterPrinting;
	}

	/**
	 * @param allowModifyOrdersAfterPrinting the allowModifyOrdersAfterPrinting to set
	 */
	public void setAllowModifyOrdersAfterPrinting(
			Boolean allowModifyOrdersAfterPrinting) {
		this.allowModifyOrdersAfterPrinting = allowModifyOrdersAfterPrinting;
	}

	/**
	 * @return the reduction
	 */
	public BigDecimal getReduction() {
		return reduction;
	}

	/**
	 * @param reduction the reduction to set
	 */
	public void setReduction(BigDecimal reduction) {
		this.reduction = reduction;
	}

	/**
	 * @return the amountPay
	 */
	public BigDecimal getAmountPay() {
		return amountPay;
	}

	/**
	 * @param amountPay the amountPay to set
	 */
	public void setAmountPay(BigDecimal amountPay) {
		this.amountPay = amountPay;
	}

	/**
	 * @return the quantitiesSum
	 */
	public BigDecimal getQuantitiesSum() {
		return quantitiesSum;
	}

	/**
	 * @param quantitiesSum the quantitiesSum to set
	 */
	public void setQuantitiesSum(BigDecimal quantitiesSum) {
		this.quantitiesSum = quantitiesSum;
	}

	/**
	 * @return the amountsSum
	 */
	public BigDecimal getAmountsSum() {
		return amountsSum;
	}

	/**
	 * @param amountsSum the amountsSum to set
	 */
	public void setAmountsSum(BigDecimal amountsSum) {
		this.amountsSum = amountsSum;
	}

	/**
	 * @return the reductionRatio
	 */
	public BigDecimal getReductionRatio() {
		return reductionRatio;
	}

	/**
	 * @param reductionRatio the reductionRatio to set
	 */
	public void setReductionRatio(BigDecimal reductionRatio) {
		this.reductionRatio = reductionRatio;
	}

	/**
	 * @return the registrationDate
	 */
	public Date getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * @param registrationDate the registrationDate to set
	 */
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	/**
	 * @return the printingDate
	 */
	public Date getPrintingDate() {
		return printingDate;
	}

	/**
	 * @param printingDate the printingDate to set
	 */
	public void setPrintingDate(Date printingDate) {
		this.printingDate = printingDate;
	}

	/**
	 * @return the cashingDate
	 */
	public Date getCashingDate() {
		return cashingDate;
	}

	/**
	 * @param cashingDate the cashingDate to set
	 */
	public void setCashingDate(Date cashingDate) {
		this.cashingDate = cashingDate;
	}

	/**
	 * @return the reductionRatioManuallyChanged
	 */
	public Boolean getReductionRatioManuallyChanged() {
		return reductionRatioManuallyChanged;
	}

	/**
	 * @param reductionRatioManuallyChanged the reductionRatioManuallyChanged to set
	 */
	public void setReductionRatioManuallyChanged(
			Boolean reductionRatioManuallyChanged) {
		this.reductionRatioManuallyChanged = reductionRatioManuallyChanged;
	}

	/**
	 * @return the orders
	 */
	public List<OrderLineDto> getOrders() {
		return orders;
	}

	/**
	 * @param orders the orders to set
	 */
	public void setOrders(List<OrderLineDto> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "DinnerTableDto [number=" + number + ", takeaway="
				+ takeaway + ", customersNumber=" + customersNumber
				+ ", allowModifyOrdersAfterPrinting="
				+ allowModifyOrdersAfterPrinting + ", reduction=" + reduction
				+ ", amountPay=" + amountPay + ", quantitiesSum="
				+ quantitiesSum + ", amountsSum=" + amountsSum
				+ ", reductionRatio=" + reductionRatio + ", registrationDate="
				+ registrationDate + ", printingDate=" + printingDate
				+ ", cashingDate=" + cashingDate
				+ ", reductionRatioManuallyChanged="
				+ reductionRatioManuallyChanged + ", orders=" + orders
				+ ", id=" + id + "]";
	}
}
