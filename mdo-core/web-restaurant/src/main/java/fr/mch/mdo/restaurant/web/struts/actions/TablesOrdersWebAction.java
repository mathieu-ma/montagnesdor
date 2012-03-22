package fr.mch.mdo.restaurant.web.struts.actions;

import java.util.List;

import fr.mch.mdo.restaurant.dto.beans.AjaxOrderLineDto;
import fr.mch.mdo.restaurant.dto.beans.DinnerTableDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.OrderLineDto;
import fr.mch.mdo.restaurant.dto.beans.TablesOrdersDto;
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

//	public String form() throws Exception {
//		String result = "ajax-response-form";
//
//		TablesOrdersForm form = (TablesOrdersForm) super.getForm();
//		DinnerTableDto dtoBean = (DinnerTableDto) form.getDtoBean();
//
//		// Table name
//		String tableNumber = dtoBean.getNumber();
//		super.getLogger().debug("Table name : " + tableNumber);
//		try {
//			MdoUserContext userContext = (MdoUserContext) form.getUserContext();
//			dtoBean = manager.findTableByNumber(userContext, tableNumber);
//			form.setDtoBean(dtoBean);
//			userContext.setCurrentTable(dtoBean);
//		} catch (Exception e) {
//			addActionError(getText("error.table.name"));
//		}
//
//		return result;
//	}

	public String displayDinnerTable() throws Exception {
		String result = "dinner-table";
		TablesOrdersForm form = (TablesOrdersForm) super.getForm();
		DinnerTableDto dtoBean = (DinnerTableDto) form.getDtoBean();

		// Customer number to be saved
		Integer customersNumber = dtoBean.getCustomersNumber();
		String tableNumber = dtoBean.getNumber();
		super.getLogger().debug("Table name : " + tableNumber);
		MdoUserContext userContext = (MdoUserContext) form.getUserContext();
		// Maybe we have to synchronize the MyDinnerTable
		if (userContext.getMyDinnerTable(tableNumber) != null) {
//			throw new MdoDinnerTableAlreadyInUseException("fr.mch.mdo.restaurant.web.struts.actions.displayDinnerTable.in.use");
		}
		try {
			// TODO Replace by displayDinnerTable
			dtoBean = manager.findTableByNumber(userContext, tableNumber);
			if (dtoBean != null) {
				dtoBean.setCustomersNumber(customersNumber);
				manager.updateCustomersNumber(dtoBean.getId(), customersNumber);
			} else {
				/*******************************************************************************************************/
				/** Create new Table and Store it in the session in order to use it when inserting it in the database **/
				/*******************************************************************************************************/
				dtoBean = new DinnerTableDto();
				dtoBean.setNumber(tableNumber);
				dtoBean.setCustomersNumber(customersNumber);
			}
			form.setDtoBean(dtoBean);
			// Set in session
			userContext.setMyDinnerTable(dtoBean);
		} catch (Exception e) {
			addActionError(getText("error.table.name"));
		}

		return result;
	}
	
	public String autoCompleteTablesNames() throws Exception {
		String forwardPage = "ajax-response-auto-complete-tables-names";

		TablesOrdersDto dtoBean = (TablesOrdersDto) super.getForm().getDtoBean();

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
			Integer customersNumber = manager.getCustomersNumberByNumber(userContext, tableNumber);
			dtoBean.setCustomersNumber(customersNumber);
		} catch (Exception e) {
			addActionError(getText("error.table.customers.number"));
		}

		return forwardPage;
	}

	public String autoCompleteProductsCodes() throws Exception {
		String forwardPage = "ajax-response-auto-complete-products-codes";

		TablesOrdersForm form = (TablesOrdersForm) super.getForm();

		TablesOrdersDto dtoBean = (TablesOrdersDto) form.getDtoBean();

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
		
long deltaTime = System.currentTimeMillis();
		
		String forwardPage = "ajax-response-save-order-line";

		TablesOrdersForm form = (TablesOrdersForm) super.getForm();

		// Order line
		AjaxOrderLineDto ajaxOrderLine = form.getOrderLine();
		OrderLineDto orderLine = new OrderLineDto();
		orderLine.setId(ajaxOrderLine.getId());
		orderLine.setQuantity(ajaxOrderLine.getQuantity());
		orderLine.setCode(ajaxOrderLine.getCode());
		orderLine.setLabel(ajaxOrderLine.getLabel());
		orderLine.setUnitPrice(ajaxOrderLine.getPrice());
		// In case of creating new Table.
		orderLine.setDinnerTable(new DinnerTableDto());
		orderLine.getDinnerTable().setId(ajaxOrderLine.getDinnerTableId());
		orderLine.getDinnerTable().setNumber(ajaxOrderLine.getDinnerTableNumber());
		MdoUserContext userContext = (MdoUserContext) form.getUserContext();
		super.getLogger().debug("Product code : " + orderLine.getLabel());
deltaTime = System.currentTimeMillis() - deltaTime;
System.out.println("1) saveOrderLine Delta Time = " + deltaTime);
deltaTime = System.currentTimeMillis();
		try {
			manager.processOrderLineByCode(userContext, orderLine, ajaxOrderLine.getDeletedId());
			ajaxOrderLine.setId(orderLine.getId());
			ajaxOrderLine.setColor(orderLine.getProduct()==null?"":orderLine.getProduct().getColorRGB());
			ajaxOrderLine.setDinnerTableId(orderLine.getDinnerTable().getId());
			ajaxOrderLine.setQuantity(orderLine.getQuantity());
			ajaxOrderLine.setCode(orderLine.getCode());
			ajaxOrderLine.setLabel(orderLine.getLabel());
			ajaxOrderLine.setPrice(orderLine.getUnitPrice());
			ajaxOrderLine.setAmount(orderLine.getAmount());
			ajaxOrderLine.setDataCode(orderLine.getDataCode());

		} catch (Exception e) {
			addActionError(getText("message.error.ui.action.saveOrderLine"));
		}

deltaTime = System.currentTimeMillis() - deltaTime;
System.out.println("2) processOrderLineByCode Delta Time = " + deltaTime);
deltaTime = System.currentTimeMillis();
		// Forward control to the specified success URI
		return forwardPage;
	}

	public String deleteOrderLine() throws Exception {
		String forwardPage = "ajax-response-save-order-line";

		TablesOrdersForm form = (TablesOrdersForm) super.getForm();

		// Order line
		AjaxOrderLineDto ajaxOrderLine = form.getOrderLine();
		OrderLineDto orderLine = new OrderLineDto();
		orderLine.setId(ajaxOrderLine.getDeletedId());
		Long dinnerTableId = ajaxOrderLine.getDinnerTableId();
		DinnerTableDto dinnerTable = new DinnerTableDto();
		dinnerTable.setId(dinnerTableId);
		orderLine.setDinnerTable(dinnerTable);
		MdoUserContext userContext = (MdoUserContext) form.getUserContext();
		super.getLogger().debug("Product code : " + orderLine.getLabel());
		try {
			manager.removeOrderLine(userContext, orderLine);
			if (orderLine.getDinnerTable() == null) {
				// The table is removed
				ajaxOrderLine.setDinnerTableId(null);
			}
		} catch (Exception e) {
			addActionError(getText("message.error.ui.action.deleteOrderLine"));
		}

		// Forward control to the specified success URI
		return forwardPage;
	}

	public String deleteTable() throws Exception {

		TablesOrdersForm form = (TablesOrdersForm) super.getForm();

		MdoUserContext userContext = (MdoUserContext) form.getUserContext();
		super.getLogger().debug("Delete the table with id : " + form.getDtoBean().getId());
		try {
			manager.delete(form.getDtoBean(), userContext);
		} catch (Exception e) {
			addActionError(getText("message.error.ui.action.deleteTable"));
		}

		// Forward control to the specified success URI
		return this.list();
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
