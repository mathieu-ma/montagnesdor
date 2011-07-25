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
 * This class is a t_table_cashing mapping. 
 * This table is used for cashing of dinner table depending on type of cashing.
 * 
 * @author Mathieu
 */
public class CashingType extends MdoDaoBean
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is a foreign key that refers to t_table_cashing.
	 * It is used to specify the cashed table.
	 * This field and the other cst_type_enum_id fields consist of a unique field.
	 */
	private TableCashing tableCashing;
	/**
	 * This is a foreign key that refers to t_enum.
	 * It is used to specify the type of cashing.
	 * It could be GENERIC_CASH, EURO_CASH, DOLLAR_CASH, GENERIC_TICKET, MEAL_TICKET, HOLIDAYS_TICKET, GENERIC_CHECK, BNP_CHECK, GENERIC_CARD, VISA_CARD, MASTER_CARD, UNPAID... 
	 * This field and the other tcs_id fields consist of a unique field.
	 */
	private MdoTableAsEnum type;
	/**
	 * This is the amount of the dinner table depending on the specific type of cashing.
	 */
	private BigDecimal amount;

	/**
	 * @return the tableCashing
	 */
	public TableCashing getTableCashing() {
		return tableCashing;
	}

	/**
	 * @param tableCashing
	 *            the tableCashing to set
	 */
	public void setTableCashing(TableCashing tableCashing) {
		this.tableCashing = tableCashing;
	}

	/**
	 * @return the type
	 */
	public MdoTableAsEnum getType() {
		return type;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setType(MdoTableAsEnum type) {
		this.type = type;
	}

	/**
	 * @return the value
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "CashingType [type=" + type + ", amount=" + amount + ", deleted=" + deleted + ", id=" + id + "]";
	}

}
