package fr.mch.mdo.restaurant.beans.dto;

import java.math.BigDecimal;
import java.util.Map;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.DinnerTableDto;

/**
 * @author Mathieu MA
 * 
 */
public class TablesOrdersDto extends MdoDtoBean
{
	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private String prefixTableNumber = "";

	private String prefixProductCode = "";

	private Boolean allowModifyOrdersAfterPrinting = false;

	private BigDecimal reduction;

	private BigDecimal amountPay;

	private DinnerTableDto dinnerTable = new DinnerTableDto();

	private AjaxOrderLineDto orderLine = new AjaxOrderLineDto();

	/**
	 * Hash key == DinnerTable id Hash value == DinnerTable number(name)
	 */
	private Map<Long, String> tablesNames;

	/**
	 * Hash key == Product id Hash value == Product code
	 */
	private Map<Long, String> productsCodes;

	public TablesOrdersDto() {
	}

	public String getPrefixTableNumber() {
		return prefixTableNumber;
	}

	public void setPrefixTableNumber(String prefixTableNumber) {
		this.prefixTableNumber = prefixTableNumber;
	}

	public Map<Long, String> getTablesNames() {
		return tablesNames;
	}

	public void setTablesNames(Map<Long, String> tablesNames) {
		this.tablesNames = tablesNames;
	}

	public void setReduction(BigDecimal reduction) {
		this.reduction = reduction;
	}

	public BigDecimal getReduction() {
		return reduction;
	}

	public void setAmountPay(BigDecimal amountPay) {
		this.amountPay = amountPay;
	}

	public BigDecimal getAmountPay() {
		return amountPay;
	}

	public void setAllowModifyOrdersAfterPrinting(boolean allowModifyOrdersAfterPrinting) {
		this.allowModifyOrdersAfterPrinting = allowModifyOrdersAfterPrinting;
	}

	public boolean isAllowModifyOrdersAfterPrinting() {
		return allowModifyOrdersAfterPrinting;
	}

	public DinnerTableDto getDinnerTable() {
		return dinnerTable;
	}

	public void setDinnerTable(DinnerTableDto dinnerTable) {
		this.dinnerTable = dinnerTable;
	}

	public String getPrefixProductCode() {
		return prefixProductCode;
	}

	public void setPrefixProductCode(String prefixProductCode) {
		this.prefixProductCode = prefixProductCode;
	}

	public Map<Long, String> getProductsCodes() {
		return productsCodes;
	}

	public void setProductsCodes(Map<Long, String> productsCodes) {
		this.productsCodes = productsCodes;
	}

	public void setOrderLine(AjaxOrderLineDto orderLine) {
		this.orderLine = orderLine;
	}

	public AjaxOrderLineDto getOrderLine() {
		return orderLine;
	}
}
