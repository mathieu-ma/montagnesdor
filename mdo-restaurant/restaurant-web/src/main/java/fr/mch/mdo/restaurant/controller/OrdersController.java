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
import fr.mch.mdo.restaurant.ui.forms.TableHeaderForm;

@Controller
@RequestMapping("/orders")
public final class OrdersController //extends AbstractController
{
	@Inject
	@Named("OrdersManager")
	private IOrdersManager manager;

	@RequestMapping(value = "{restaurantId}/tables/{state}", method = RequestMethod.GET)
	@ResponseBody
	public List<DinnerTableDto> tables(@PathVariable Long restaurantId, @PathVariable String state) throws Exception {
		return this.tables(restaurantId, null, state);
	}

	@RequestMapping(value = "{restaurantId}/{userAuthenticationId}/tables/{state}", method = RequestMethod.GET)
	@ResponseBody
	public List<DinnerTableDto> tables(@PathVariable Long restaurantId, @PathVariable Long userAuthenticationId, @PathVariable String state) throws MdoException {
		List<DinnerTableDto> tables = new ArrayList<DinnerTableDto>();
		// Checking type table before calling manager specific method 
		tables = manager.findAllTables(restaurantId, userAuthenticationId, TableState.valueOf(state));
		return tables;
	}

	@RequestMapping(value = "/delete/order/line/{id}", method = RequestMethod.DELETE)
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

	@RequestMapping(value = "/{restaurantId}/table/header/by/number/{number}", method = RequestMethod.GET)
	@ResponseBody
	public DinnerTableDto tableHeader(@PathVariable Long restaurantId, @PathVariable String number, Model model) throws MdoException {
		
		DinnerTableDto result = manager.findTableHeader(restaurantId, number);
		
		return result;
	}

	@RequestMapping(value = "/{restaurantId}/{userAuthenticationId}/table/header/by/number/{number}")
	@ResponseBody
	public DinnerTableDto tableHeader(@PathVariable Long restaurantId, @PathVariable Long userAuthenticationId, @PathVariable String number) throws MdoException {
		
		DinnerTableDto result = manager.findTableHeader(restaurantId, userAuthenticationId, number);
		
		return result;
	}

	@RequestMapping(value = "/delete/table/{id}", method = RequestMethod.DELETE)
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

	@RequestMapping(value = "/find/table/{id}")
	@ResponseBody
	public DinnerTableDto findTable(@PathVariable Long id, Locale locale) throws MdoException {
		DinnerTableDto table = manager.findTable(id, locale);
		return table;
	}

	@RequestMapping(value = "/table/orders/size/{id}")
	@ResponseBody
	public Integer tableOrdersSize(@PathVariable Long id) throws MdoException {
		Integer result = manager.getTableOrdersSize(id);
		return result;
	}

	@RequestMapping(value = "/update/table/{id}/customers/number/{customersNumber}", method = RequestMethod.POST)
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

	@RequestMapping(value = "/update/table/creation/date/now/{id}", method = RequestMethod.POST)
	@ResponseBody
	public AcknowledgmentMessage updateTableCreationDate(@PathVariable Long id) {
		AcknowledgmentMessage message = null; //manager.updateTableCreationDate(id, new Date());
		return message;
	}

	@RequestMapping(value = "/reset/table/creation/date/customers/number/{id}", method = RequestMethod.POST)
	@ResponseBody
	public AcknowledgmentMessage resetTableCreationDateCustomersNumber(@PathVariable Long id) {
		AcknowledgmentMessage ack = new AcknowledgmentMessage();
		try {
			manager.resetTableCreationDateCustomersNumber(id);
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
	@RequestMapping(value = "/create/table/{restaurantId}/{userAuthenticationId}", method = RequestMethod.POST)
	@ResponseBody
	public DinnerTableDto createTable(@PathVariable Long restaurantId, @PathVariable Long userAuthenticationId, @RequestBody TableHeaderForm header) throws MdoException {
		DinnerTableDto table = manager.createTable(restaurantId, userAuthenticationId, header.getNumber(), header.getCustomersNumber());
		return table;
	}

	@RequestMapping(value = "{restaurantId}/find/product/{code}", method = RequestMethod.GET)
	@ResponseBody
	public ProductDto findProduct(@PathVariable Long restaurantId, @PathVariable String code) throws MdoException {
		ProductDto product = manager.findProduct(restaurantId, code);
		return product;
	}

	@RequestMapping("{restaurantId}/tables/{state}/view")
	public String tablesView(@PathVariable Long restaurantId, @PathVariable String state, Model model) throws Exception {
		List<DinnerTableDto> tables = this.tables(restaurantId, state);
		model.addAttribute("tables", tables);
		return "orders/tables";
	}

	@RequestMapping("{restaurantId}/{userAuthenticationId}/tables/{state}/view")
	public String tablesView(@PathVariable Long restaurantId, @PathVariable Long userAuthenticationId, @PathVariable String state, Model model) throws Exception {
		List<DinnerTableDto> tables = this.tables(restaurantId, userAuthenticationId, state);
		model.addAttribute("tables", tables);
		return "orders/tables";
	}

	@RequestMapping(value = "/delete/table/{id}/view", method = RequestMethod.DELETE)
	public String deleteTableView(@PathVariable Long id, Model model) throws MdoException {
		this.deleteTable(id);
		String type = "ALL";
		return "redirect:/orders/tables/" + type + "/view";
	}
	
	@RequestMapping("/find/table/{id}/view")
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
	@RequestMapping(value = "/create/table/{restaurantId}/{userAuthenticationId}/view", method = RequestMethod.POST)
	public String createTableView(@PathVariable Long restaurantId, @PathVariable Long userAuthenticationId, @RequestBody TableHeaderForm header, Model model) throws MdoException {
		DinnerTableDto table = this.createTable(restaurantId, userAuthenticationId, header);
		return "redirect:/find/table/"+ table.getId() + "/view";
	}
}
