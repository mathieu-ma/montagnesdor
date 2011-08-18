package fr.mch.mdo.restaurant.web.struts.actions;

import java.util.List;

import fr.mch.mdo.restaurant.dto.beans.DinnerTableDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.OrderLineDto;
import fr.mch.mdo.restaurant.dto.beans.TablesOrdersDtoBean;
import fr.mch.mdo.restaurant.ioc.spring.WebRestaurantBeanFactory;
import fr.mch.mdo.restaurant.services.business.managers.tables.IDinnerTablesManager;
import fr.mch.mdo.restaurant.ui.forms.TablesOrdersForm;

public final class TablesOrdersWebAction extends RestaurantWebAction
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = -1L;

	IDinnerTablesManager manager = WebRestaurantBeanFactory.getInstance().getDinnerTablesManager();

	public TablesOrdersWebAction() {
		super(WebRestaurantBeanFactory.getInstance().getLogger(TablesOrdersWebAction.class.getName()), new TablesOrdersForm());
	}

	public String form() throws Exception {
		String result = "ajax-response-form";

		TablesOrdersForm form = (TablesOrdersForm) super.getForm();
		TablesOrdersDtoBean dtoBean = (TablesOrdersDtoBean) form.getDtoBean();

		// Table name
		String tableNumber = dtoBean.getDinnerTable().getNumber();
		super.getLogger().debug("Table name : " + tableNumber);
		try {
			MdoUserContext userContext = (MdoUserContext) form.getUserContext();
			dtoBean.setDinnerTable(manager.findTableByNumber(userContext, tableNumber));
			userContext.setCurrentTable(dtoBean.getDinnerTable());
		} catch (Exception e) {
			addActionError(getText("error.table.name"));
		}

		return result;
	}

	public String autoCompleteTablesNames() throws Exception {
		String forwardPage = "ajax-response-auto-complete-tables-names";

		TablesOrdersDtoBean dtoBean = (TablesOrdersDtoBean) super.getForm().getDtoBean();

		TablesOrdersForm form = (TablesOrdersForm) super.getForm();

		// Prefix table name
		String prefixTableNumber = dtoBean.getPrefixTableNumber();
		super.getLogger().debug("Prefix table name : " + prefixTableNumber);
		try {
			dtoBean.setTablesNames(manager.findAllTableNamesByPrefix(form.getUserContext(), prefixTableNumber));
		} catch (Exception e) {
			addActionError(getText("error.auto.complete.tables.names"));
		}

		// Forward control to the specified success URI
		return forwardPage;
	}

	public String tableCustomersNumber() throws Exception {
		String forwardPage = "ajax-response-table-customers-number";

		TablesOrdersForm form = (TablesOrdersForm) super.getForm();

		DinnerTableDto dtoBean = (DinnerTableDto) form.getDtoBean();
		String tableNumber = dtoBean.getNumber();
		super.getLogger().debug("Table name : " + tableNumber);
		try {
			MdoUserContext userContext = (MdoUserContext) form.getUserContext();
			dtoBean.setCustomersNumber(manager.getCustomersNumberByNumber(userContext, tableNumber));
		} catch (Exception e) {
			addActionError(getText("error.table.customers.number"));
		}

		return forwardPage;
	}

	public String autoCompleteProductsCodes() throws Exception {
		String forwardPage = "ajax-response-auto-complete-products-codes";

		TablesOrdersForm form = (TablesOrdersForm) super.getForm();

		TablesOrdersDtoBean dtoBean = (TablesOrdersDtoBean) form.getDtoBean();

		// Prefix product code
		String prefixProductCode = dtoBean.getPrefixProductCode();
		super.getLogger().debug("Prefix product code : " + prefixProductCode);
		try {
			if (prefixProductCode != null) {
				MdoUserContext userContext = (MdoUserContext) form.getUserContext();
				dtoBean.setProductsCodes(manager.findAllProductCodesByPrefix(userContext, prefixProductCode));
			}
		} catch (Exception e) {
			addActionError(getText("error.auto.complete.products.codes"));
		}

		// Forward control to the specified success URI
		return forwardPage;
	}

	public String saveOrderLine() throws Exception {
		String forwardPage = "ajax-response-save-order-line";

		TablesOrdersForm form = (TablesOrdersForm) super.getForm();
		TablesOrdersDtoBean dtoBean = (TablesOrdersDtoBean) form.getDtoBean();

		// Order line
		OrderLineDto orderLine = dtoBean.getOrderLine();
		super.getLogger().debug("Product code : " + orderLine.getCode());
		try {
			MdoUserContext userContext = (MdoUserContext) form.getUserContext();
			manager.addOrderLine(userContext, orderLine);
		} catch (Exception e) {
			addActionError(getText("error.save.order.line.product"));
		}

		// Forward control to the specified success URI
		return forwardPage;
	}

	@SuppressWarnings("unchecked")
	public String list() throws Exception {
		String forwardPage = "list";

		TablesOrdersForm form = (TablesOrdersForm) super.getForm();

		super.getLogger().debug("Tables list");
		try {
			MdoUserContext userContext = (MdoUserContext) form.getUserContext();
			@SuppressWarnings("rawtypes")
			List objects = manager.findAllFreeTables(userContext); 
			form.setList(objects);
		} catch (Exception e) {
			addActionError(getText("message.error.action.list.tables"));
		}

		// Forward control to the specified success URI
		return forwardPage;
	}

}
