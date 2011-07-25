/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dto.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * This class is used for t_dinner_table mapping. This table is used for dinner
 * tables.
 * 
 * @author Mathieu MA sous conrad
 */
public class DinnerTableDto extends MdoDtoBean
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is a foreign key that refers to t_restaurant. 
	 * It is used to specify the restaurant where the dinner table belongs. 
	 * Notice that this information is already known with the aut_id field. 
	 * But this field is used for the unicity of the dinner table.
	 * So this field and the others dtb_code and dtb_registration_date consist of a unique field.
	 */
	private RestaurantDto restaurant;
	/**
	 * This is the code number of the dinner table. 
	 * This field and the others res_id and dtb_registration_date consist of a unique field.
	 */
	private String number;
	/**
	 * This is a foreign key that refers to t_user_authentication. It is used to
	 * specify the user authentication that created this dinner table.
	 */
	private UserAuthenticationDto user;
	/**
	 * This is an id for the room where the dinner table is. It is not currently
	 * used.
	 */
	private Long roo_id;
	/**
	 * This is used to specify the number of customers.
	 */
	private Integer customersNumber;
	/**
	 * This is used to specify the sum of the order lines quantities. This value
	 * could be calculated from order lines table.
	 */
	private BigDecimal quantitiesSum;
	/**
	 * This is used to specify the sum of the order lines amounts. This value
	 * could be calculated from order lines table.
	 */
	private BigDecimal amountsSum;
	/**
	 * This is used to specify the reduction ratio.
	 */
	private BigDecimal reductionRatio;
	/**
	 * This is used to specify the amount to pay.
	 */
	private BigDecimal amountPay;
	/**
	 * This is used to specify the registration/creation date. 
	 * This field and the others res_id and dtb_code consist of a unique field.
	 */
	private Date registrationDate;
	/**
	 * This is used to specify the printing date.
	 */
	private Date printingDate;
	/**
	 * This is used to specify if user has changed the reduction ratio.
	 */
	private Boolean reductionRatioChanged = false;
	/**
	 * This is used to specify the type of dinner table. Could be TAKE-AWAY,
	 * EAT-IN ...
	 */
	private TableTypeDto type;

	/**
	 * Set of order lines.
	 */
	private Set<OrderLineDto> orders = new HashSet<OrderLineDto>();

	/**
	 * Set of bills == factures.
	 */
	private Set<TableBillDto> bills = new HashSet<TableBillDto>();

	/**
	 * Set of credit == avoirs.
	 */
	private Set<TableCreditDto> credits = new HashSet<TableCreditDto>();

	/**
	 * Set of VAT amount for this table.
	 */
	private Set<TableVatDto> vats = new HashSet<TableVatDto>();

	/**
	 * The one-to-one Cashing for this table.
	 */
	private TableCashingDto cashing;

	/**
	 * @return the restaurant
	 */
	public RestaurantDto getRestaurant() {
		return restaurant;
	}

	/**
	 * @param restaurant
	 *            the restaurant to set
	 */
	public void setRestaurant(RestaurantDto restaurant) {
		this.restaurant = restaurant;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the user
	 */
	public UserAuthenticationDto getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(UserAuthenticationDto user) {
		this.user = user;
	}

	/**
	 * @return the roo_id
	 */
	public Long getRoo_id() {
		return roo_id;
	}

	/**
	 * @param rooId
	 *            the roo_id to set
	 */
	public void setRoo_id(Long rooId) {
		roo_id = rooId;
	}

	/**
	 * @return the customersNumber
	 */
	public Integer getCustomersNumber() {
		return customersNumber;
	}

	/**
	 * @param customersNumber
	 *            the customersNumber to set
	 */
	public void setCustomersNumber(Integer customersNumber) {
		this.customersNumber = customersNumber;
	}

	/**
	 * @return the quantitiesSum
	 */
	public BigDecimal getQuantitiesSum() {
		return quantitiesSum;
	}

	/**
	 * @param quantitiesSum
	 *            the quantitiesSum to set
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
	 * @param amountsSum
	 *            the amountsSum to set
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
	 * @param reductionRatio
	 *            the reductionRatio to set
	 */
	public void setReductionRatio(BigDecimal reductionRatio) {
		this.reductionRatio = reductionRatio;
	}

	/**
	 * @return the amountPay
	 */
	public BigDecimal getAmountPay() {
		return amountPay;
	}

	/**
	 * @param amountPay
	 *            the amountPay to set
	 */
	public void setAmountPay(BigDecimal amountPay) {
		this.amountPay = amountPay;
	}

	/**
	 * @return the registrationDate
	 */
	public Date getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * @param registrationDate
	 *            the registrationDate to set
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
	 * @param printingDate
	 *            the printing Date to set
	 */
	public void setPrintingDate(Date printingDate) {
		this.printingDate = printingDate;
	}

	/**
	 * @return the reductionRatioChanged
	 */
	public Boolean getReductionRatioChanged() {
		return reductionRatioChanged;
	}

	/**
	 * @param reductionRatioChanged
	 *            the reductionRatioChanged to set
	 */
	public void setReductionRatioChanged(Boolean reductionRatioChanged) {
		this.reductionRatioChanged = reductionRatioChanged;
	}

	/**
	 * @return the type
	 */
	public TableTypeDto getType() {
		return type;
	}

	/**
	 * @param takeaway
	 *            the type to set
	 */
	public void setType(TableTypeDto type) {
		this.type = type;
	}

	/**
	 * @return the orders
	 */
	public Set<OrderLineDto> getOrders() {
		return orders;
	}

	/**
	 * @param orders
	 *            the orders to set
	 */
	public void setOrders(Set<OrderLineDto> orders) {
		this.orders = orders;
	}

	/**
	 * Add order line to orders
	 * 
	 * @param OrderLineDto
	 *            the order line
	 */
	public void addOrderLine(OrderLineDto orderLine) {
		if (orders == null) {
			orders = new HashSet<OrderLineDto>();
		}
		if (orderLine != null) {
			orderLine.setDinnerTable(this);
		}
		orders.add(orderLine);
	}

	/**
	 * @return the bills
	 */
	public Set<TableBillDto> getBills() {
		return bills;
	}

	/**
	 * @param bills
	 *            the bills to set
	 */
	public void setBills(Set<TableBillDto> bills) {
		this.bills = bills;
	}

	/**
	 * Add bill to bills
	 * 
	 * @param bill
	 *            the bill
	 */
	public void addBill(TableBillDto TableBillDto) {
		if (bills == null) {
			bills = new HashSet<TableBillDto>();
		}
		if (TableBillDto != null) {
			TableBillDto.setDinnerTable(this);
		}
		bills.add(TableBillDto);
	}

	/**
	 * @return the credits
	 */
	public Set<TableCreditDto> getCredits() {
		return credits;
	}

	/**
	 * @param credits
	 *            the credits to set
	 */
	public void setCredits(Set<TableCreditDto> credits) {
		this.credits = credits;
	}

	/**
	 * Add credit to credits
	 * 
	 * @param credit
	 *            the credit
	 */
	public void addCredit(TableCreditDto credit) {
		if (credits == null) {
			credits = new HashSet<TableCreditDto>();
		}
		if (credit != null) {
			credit.setDinnerTable(this);
		}
		credits.add(credit);
	}

	/**
	 * @return the vats
	 */
	public Set<TableVatDto> getVats() {
		return vats;
	}

	/**
	 * @param vats
	 *            the vats to set
	 */
	public void setVats(Set<TableVatDto> vats) {
		this.vats = vats;
	}

	/**
	 * Add VAT Table to vats
	 * 
	 * @param TableVatDto
	 *            the VAT table
	 */
	public void addTableVatDto(TableVatDto vat) {
		if (this.vats == null) {
			this.vats = new HashSet<TableVatDto>();
		}
		if (vat != null) {
			vat.setDinnerTable(this);
		}
		vats.add(vat);
	}

	/**
	 * @param cashing
	 *            the cashing to set
	 */
	public void setCashing(TableCashingDto cashing) {
		this.cashing = cashing;
	}

	/**
	 * @return the cashing
	 */
	public TableCashingDto getCashing() {
		return cashing;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((registrationDate == null) ? 0 : new Long(registrationDate.getTime()).hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((restaurant == null || restaurant.getId() == null) ? 0 : restaurant.getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DinnerTableDto other = (DinnerTableDto) obj;
		if (registrationDate == null) {
			if (other.registrationDate != null)
				return false;
		} else if (registrationDate.getTime() != other.registrationDate.getTime())
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (restaurant == null) {
			if (other.restaurant != null)
				return false;
		} else if (restaurant.getId() == null) {
			if (other.restaurant.getId() != null)
				return false;
		} else if (!restaurant.getId().equals(other.getRestaurant().getId())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "DinnerTable [amountPay=" + amountPay + ", amountsSum=" + amountsSum + ", bills=" + bills + ", cashing=" + cashing + ", credits=" + credits
				+ ", customersNumber=" + customersNumber + ", number=" + number + ", orders=" + orders + ", printingDate=" + printingDate + ", quantitiesSum=" + quantitiesSum + ", reductionRatio="
				+ reductionRatio + ", reductionRatioChanged=" + reductionRatioChanged + ", registrationDate=" + registrationDate + ", restaurant=" + restaurant + ", roo_id=" + roo_id + ", type="
				+ type + ", user=" + user + ", vats=" + vats + ", id=" + id + "]";
	}

}
