package fr.mch.mdo.restaurant.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.mch.mdo.restaurant.beans.dto.AcknowledgmentMessage;
import fr.mch.mdo.restaurant.beans.dto.DinnerTableDto;
import fr.mch.mdo.restaurant.dto.beans.ProductDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IOrdersManager;
import fr.mch.mdo.restaurant.services.business.managers.TableState;
import fr.mch.mdo.restaurant.ui.forms.ResetTableForm;
import fr.mch.mdo.restaurant.ui.forms.TableHeaderForm;

@Controller
@RequestMapping(OrdersController.ORDERS_CONTROLLER)
public final class OrdersController //extends AbstractController
{

	public static final String ORDERS_CONTROLLER = "/orders";
	
	public static final String RESET_TABLE_DINNER_TABLE_ID = "/reset/table/{dinnerTableId}";
	public static final String CREATE_TABLE_RESTAURANT_ID_USER_AUTHENTICATION_ID_VIEW = "/create/table/{restaurantId}/{userAuthenticationId}/view";
	public static final String FIND_TABLE_ID_VIEW = "/find/table/{id}/view";
	public static final String DELETE_TABLE_ID_VIEW = "/delete/table/{id}/view";
	public static final String RESTAURANT_ID_USER_AUTHENTICATION_ID_TABLES_STATE_VIEW = "/{restaurantId}/{userAuthenticationId}/tables/{state}/view";
	public static final String RESTAURANT_ID_TABLES_STATE_VIEW = "/{restaurantId}/tables/{state}/view";
	public static final String RESTAURANT_ID_FIND_PRODUCT_CODE = "/{restaurantId}/find/product/{code}";
	public static final String CREATE_TABLE_RESTAURANT_ID_USER_AUTHENTICATION_ID = "/create/table/{restaurantId}/{userAuthenticationId}";
	public static final String UPDATE_TABLE_ID_CUSTOMERS_NUMBER_CUSTOMERS_NUMBER = "/update/table/{id}/customers/number/{customersNumber}";
	public static final String TABLE_ORDERS_SIZE_ID = "/table/orders/size/{dinnerTableId}";
	public static final String FIND_TABLE_ID = "/find/table/{id}";
	public static final String DELETE_TABLE_ID = "/delete/table/{id}";
	public static final String RESTAURANT_ID_USER_AUTHENTICATION_ID_TABLE_HEADER_BY_NUMBER_NUMBER = "/{restaurantId}/{userAuthenticationId}/table/header/by/number/{number}";
	public static final String RESTAURANT_ID_TABLE_HEADER_BY_NUMBER_NUMBER = "/{restaurantId}/table/header/by/number/{number}";
	public static final String DELETE_ORDER_LINE_ID = "/delete/order/line/{id}";
	public static final String RESTAURANT_ID_USER_AUTHENTICATION_ID_TABLES_STATE = "/{restaurantId}/{userAuthenticationId}/tables/{state}";
	public static final String RESTAURANT_ID_TABLES_STATE = "/{restaurantId}/tables/{state}";
	
	@Inject
	@Named("OrdersManager")
	private IOrdersManager manager;

	@RequestMapping(value = RESTAURANT_ID_TABLES_STATE, method = RequestMethod.GET)
	@ResponseBody
	public List<DinnerTableDto> tables(@PathVariable Long restaurantId, @PathVariable String state) throws Exception {
		return this.tables(restaurantId, null, state);
	}

	@RequestMapping(value = RESTAURANT_ID_USER_AUTHENTICATION_ID_TABLES_STATE, method = RequestMethod.GET)
	@ResponseBody
	public List<DinnerTableDto> tables(@PathVariable Long restaurantId, @PathVariable Long userAuthenticationId, @PathVariable String state) throws MdoException {
		List<DinnerTableDto> tables = new ArrayList<DinnerTableDto>();
		// Checking type table before calling manager specific method 
		tables = manager.findAllTables(restaurantId, userAuthenticationId, TableState.valueOf(state));
		return tables;
	}

	@RequestMapping(value = RESTAURANT_ID_TABLE_HEADER_BY_NUMBER_NUMBER, method = RequestMethod.GET)
	@ResponseBody
	public DinnerTableDto tableHeader(@PathVariable Long restaurantId, @PathVariable String number) throws MdoException {
		
		DinnerTableDto result = manager.findTableHeader(restaurantId, number);
		
		return result;
	}

	@RequestMapping(value = RESTAURANT_ID_USER_AUTHENTICATION_ID_TABLE_HEADER_BY_NUMBER_NUMBER)
	@ResponseBody
	public DinnerTableDto tableHeader(@PathVariable Long restaurantId, @PathVariable Long userAuthenticationId, @PathVariable String number) throws MdoException {
		
		DinnerTableDto result = manager.findTableHeader(restaurantId, userAuthenticationId, number);
		
		return result;
	}

	@RequestMapping(value = DELETE_TABLE_ID, method = RequestMethod.DELETE)
	@ResponseBody
	public AcknowledgmentMessage deleteTable(@PathVariable Long id) {
		AcknowledgmentMessage ack = new AcknowledgmentMessage();
		try {
			manager.deleteTable(id);
		} catch (MdoException e) {
			ack.setType(AcknowledgmentMessage.Type.ERROR);
			ack.setTitle("delete.table.error.ack.title");
			ack.setMessage("delete.table.error.ack.message");
		}
		return ack;
	}

	@RequestMapping(value = FIND_TABLE_ID)
	@ResponseBody
	public DinnerTableDto findTable(@PathVariable Long id, Locale locale) throws MdoException {
		DinnerTableDto table = manager.findTable(id, locale);
		return table;
	}

	@RequestMapping(value = UPDATE_TABLE_ID_CUSTOMERS_NUMBER_CUSTOMERS_NUMBER, method = RequestMethod.POST)
	@ResponseBody
	public AcknowledgmentMessage updateTableCustomersNumber(@PathVariable Long id, @PathVariable Integer customersNumber) {
		AcknowledgmentMessage ack = new AcknowledgmentMessage();
		try {
			manager.updateTableCustomersNumber(id, customersNumber);
		} catch (MdoException e) {
			ack.setType(AcknowledgmentMessage.Type.ERROR);
			ack.setTitle("update.table.customers.number.error.ack.title");
			ack.setMessage("update.table.customers.number.error.ack.message");
		}
		return ack;
	}

	@RequestMapping(value = RESET_TABLE_DINNER_TABLE_ID, method = RequestMethod.POST)
	@ResponseBody
	public AcknowledgmentMessage resetTable(@PathVariable Long dinnerTableId, @RequestBody ResetTableForm form) {
		AcknowledgmentMessage ack = new AcknowledgmentMessage();
		try {
			manager.resetTable(dinnerTableId, form.getRestaurantId(), form.getUserAuthenticationId(), form.getNumber(), form.getCustomersNumber());
		} catch (MdoException e) {
			ack.setType(AcknowledgmentMessage.Type.ERROR);
			ack.setTitle("reset.table.creation.date.customers.number.error.ack.title");
			ack.setMessage("reset.table.creation.date.customers.number.error.ack.message");
		}
		return ack;
	}

	/**
	 * 
	 * @param header this field could be validated with JSR.
	 * @return
	 * @throws MdoException 
	 */
	@RequestMapping(value = CREATE_TABLE_RESTAURANT_ID_USER_AUTHENTICATION_ID, method = RequestMethod.POST)
	@ResponseBody
	public DinnerTableDto createTable(@PathVariable Long restaurantId, @PathVariable Long userAuthenticationId, @RequestBody TableHeaderForm header) throws MdoException {
		DinnerTableDto table = manager.createTable(restaurantId, userAuthenticationId, header.getNumber(), header.getCustomersNumber());
		return table;
	}

	@RequestMapping(value = TABLE_ORDERS_SIZE_ID)
	@ResponseBody
	public Integer tableOrdersSize(@PathVariable Long dinnerTableId) throws MdoException {
		Integer result = manager.getTableOrdersSize(dinnerTableId);
		return result;
	}

	@RequestMapping(value = RESTAURANT_ID_FIND_PRODUCT_CODE, method = RequestMethod.GET)
	@ResponseBody
	public ProductDto findProduct(@PathVariable Long restaurantId, @PathVariable String code) throws MdoException {
		ProductDto product = manager.findProduct(restaurantId, code);
		return product;
	}
	
	@RequestMapping(value = DELETE_ORDER_LINE_ID, method = RequestMethod.DELETE)
	@ResponseBody
	public AcknowledgmentMessage deleteOrderLine(@PathVariable Long id) {
		AcknowledgmentMessage ack = new AcknowledgmentMessage();
		try {
			manager.deleteOrderLine(id);
		} catch (MdoException e) {
			ack.setType(AcknowledgmentMessage.Type.ERROR);
			ack.setTitle("delete.order.line.error.ack.title");
			ack.setMessage("delete.order.line.error.ack.message");
		}
		
		return ack;
	}

	@RequestMapping(RESTAURANT_ID_TABLES_STATE_VIEW)
	public String tablesView(@PathVariable Long restaurantId, @PathVariable String state, Model model) throws Exception {
		List<DinnerTableDto> tables = this.tables(restaurantId, state);
		model.addAttribute("tables", tables);
		return "orders/tables";
	}

	@RequestMapping(RESTAURANT_ID_USER_AUTHENTICATION_ID_TABLES_STATE_VIEW)
	public String tablesView(@PathVariable Long restaurantId, @PathVariable Long userAuthenticationId, @PathVariable String state, Model model) throws Exception {
		List<DinnerTableDto> tables = this.tables(restaurantId, userAuthenticationId, state);
		model.addAttribute("tables", tables);
		return "orders/tables";
	}

	@RequestMapping(value = DELETE_TABLE_ID_VIEW, method = RequestMethod.DELETE)
	public String deleteTableView(@PathVariable Long id, Model model) throws MdoException {
		this.deleteTable(id);
		String type = "ALL";
		return "redirect:/orders/tables/" + type + "/view";
	}
	
	@RequestMapping(FIND_TABLE_ID_VIEW)
	public String findTableView(@PathVariable Long id, Model model, Locale locale) throws MdoException {
		DinnerTableDto table = this.findTable(id, locale);
		model.addAttribute("table", table);
		return "orders/table";
	}

	/**
	 * 
	 * @param header this field could be validated with JSR.
	 * @param model
	 * @return
	 * @throws MdoException 
	 */
	@RequestMapping(value = CREATE_TABLE_RESTAURANT_ID_USER_AUTHENTICATION_ID_VIEW, method = RequestMethod.POST)
	public String createTableView(@PathVariable Long restaurantId, @PathVariable Long userAuthenticationId, @RequestBody TableHeaderForm header, Model model) throws MdoException {
		DinnerTableDto table = this.createTable(restaurantId, userAuthenticationId, header);
		return "redirect:/find/table/"+ table.getId() + "/view";
	}
}
