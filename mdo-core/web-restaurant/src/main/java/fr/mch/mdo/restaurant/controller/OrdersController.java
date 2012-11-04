package fr.mch.mdo.restaurant.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.mch.mdo.restaurant.dto.beans.AcknowledgmentMessage;
import fr.mch.mdo.restaurant.dto.beans.DinnerTableDtoBean;
import fr.mch.mdo.restaurant.dto.beans.ProductDto;
import fr.mch.mdo.restaurant.dto.beans.ProductDtoBean;
import fr.mch.mdo.restaurant.dto.beans.TableHeader;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IOrdersManager;
import fr.mch.mdo.restaurant.services.business.managers.tables.IDinnerTablesManager;

@Controller
@RequestMapping("/orders")
public final class OrdersController //extends AbstractController
{
	@Inject
	@Named("orders")
	protected IOrdersManager manager;// = WebRestaurantBeanFactory.getInstance().getDinnerTablesManager();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "{restaurantId}/tables/{type}", method = RequestMethod.GET)
	@ResponseBody
	public List<DinnerTableDtoBean> tables(@PathVariable Long restaurantId, @PathVariable String type) throws Exception {
		List<?> tables = new ArrayList();//Collections.emptyList();
		tables = manager.findAllFreeTables(restaurantId);
		return (List<DinnerTableDtoBean>) tables;
	}

	@RequestMapping(value = "/delete/order/line/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public AcknowledgmentMessage deleteOrderLine(@PathVariable Long id) {
		AcknowledgmentMessage message = new AcknowledgmentMessage();

		return message;
	}

	@RequestMapping(value = "/table/header/by/number/{number}", method = RequestMethod.GET)
	@ResponseBody
	public TableHeader tableHeader(@PathVariable String number, Model model) {
		
		TableHeader table = new TableHeader();
		
		return table;
	}
	
	@RequestMapping(value = "/delete/table/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public AcknowledgmentMessage deleteTable(@PathVariable Long id) {
		AcknowledgmentMessage message = new AcknowledgmentMessage();

		return message;
	}

	@RequestMapping(value = "/find/table/by/number/{number}", method = RequestMethod.GET)
	@ResponseBody
	public DinnerTableDtoBean findTable(@PathVariable String number) {
		DinnerTableDtoBean table = new DinnerTableDtoBean();
		table.setTableNumber(number);
		
		return table;
	}

	@RequestMapping(value = "/create/table", method = RequestMethod.POST)
	@ResponseBody
	public DinnerTableDtoBean createTable(@RequestBody TableHeader header) {
		DinnerTableDtoBean table = new DinnerTableDtoBean();
		return table;
	}

	@RequestMapping(value = "{restaurantId}/find/product/{code}", method = RequestMethod.GET)
	@ResponseBody
	public ProductDto findProduct(@PathVariable Long restaurantId, @PathVariable String code) throws MdoException {
		ProductDto product = manager.findProduct(restaurantId, code);
		return product;
	}

	@RequestMapping("{restaurantId}/tables/{type}/view")
	public String tablesView(@PathVariable Long restaurantId, @PathVariable String type, Model model) throws Exception {
		List<DinnerTableDtoBean> tables = this.tables(restaurantId, type);
		model.addAttribute("tables", tables);
		return "orders/tables";
	}

	@RequestMapping(value = "/delete/table/{id}/view", method = RequestMethod.DELETE)
	public String deleteTableView(@PathVariable Long id, Model model) {
		this.deleteTable(id);
		String type = "ALL";
		return "redirect:/orders/tables/" + type + "/view";
	}
	
	@RequestMapping("/find/table/by/number/{number}/view")
	public String findTableView(@PathVariable String number, Model model) {
		DinnerTableDtoBean table = this.findTable(number);
		model.addAttribute("table", table);
		return "orders/table";
	}

	@RequestMapping(value = "/create/table/view", method = RequestMethod.POST)
	public String createTableView(@RequestBody TableHeader header, Model model) {
		DinnerTableDtoBean table = this.createTable(header);
		return "redirect:/find/table/by/number/{"+ table.getTableNumber() + "}/view";
	}
}
