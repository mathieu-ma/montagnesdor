package fr.mch.mdo.restaurant.ui.forms;

public class TableHeaderForm 
{
	private String number;

	private Integer customersNumber;

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

	@Override
	public String toString() {
		return "TableHeaderForm [number=" + number + ", customersNumber="
				+ customersNumber + "]";
	}
}
