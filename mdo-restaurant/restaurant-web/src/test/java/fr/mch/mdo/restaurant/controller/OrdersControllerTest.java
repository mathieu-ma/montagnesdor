package fr.mch.mdo.restaurant.controller;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import fr.mch.mdo.restaurant.beans.dto.AcknowledgmentMessage;
import fr.mch.mdo.restaurant.beans.dto.DinnerTableDto;
import fr.mch.mdo.restaurant.beans.dto.ProductDto;
import fr.mch.mdo.restaurant.services.business.managers.TableState;
import fr.mch.mdo.restaurant.ui.forms.TableHeaderForm;
import fr.mch.mdo.restaurant.web.AbstractControllerTest;


public final class OrdersControllerTest extends AbstractControllerTest
{
	public static final String context = SERVER_URL + OrdersController.ORDERS_CONTROLLER;
	
	@Test
	public void tables() {
		String state = TableState.ALTERABLE.name();
		Long restaurantId = 1L;
		Long userAuthenticationId = 1L;
        StringBuilder sb = new StringBuilder(context).append(OrdersController.RESTAURANT_ID_TABLES_STATE);
        DinnerTableDto[] dinnerTableItems = restTemplate.getForObject(sb.toString(), DinnerTableDto[].class, restaurantId, state);
    	Assert.assertNotNull(dinnerTableItems);
        sb = new StringBuilder(context).append(OrdersController.RESTAURANT_ID_USER_AUTHENTICATION_ID_TABLES_STATE);
        dinnerTableItems = restTemplate.getForObject(sb.toString(), DinnerTableDto[].class, userAuthenticationId, restaurantId, state);
    	Assert.assertNotNull(dinnerTableItems);

		state = TableState.CASHED.name();
        sb = new StringBuilder(context).append(OrdersController.RESTAURANT_ID_USER_AUTHENTICATION_ID_TABLES_STATE);
        dinnerTableItems = restTemplate.getForObject(sb.toString(), DinnerTableDto[].class, userAuthenticationId, restaurantId, state);
    	Assert.assertNotNull(dinnerTableItems);

	}

	@Test
	public void deleteTable() {
    	Long tableId = 1L;
        StringBuilder sb = new StringBuilder(context).append(OrdersController.DELETE_TABLE_ID);
		// Do not use restTemplate.delete method because the service returns nothing
    	ResponseEntity<AcknowledgmentMessage> response = restTemplate.exchange(sb.toString(), HttpMethod.DELETE, null, AcknowledgmentMessage.class, tableId);
    	Assert.assertNotNull(response.getBody());
	}
     
    @Test
    public void deleteOrderLine() {
    	Long orderLineId = 1L;
        StringBuilder sb = new StringBuilder(context).append(OrdersController.DELETE_ORDER_LINE_ID);
		// Do not use delete method because the service returns nothing
    	ResponseEntity<AcknowledgmentMessage> response = restTemplate.exchange(sb.toString(), HttpMethod.DELETE, null, AcknowledgmentMessage.class, orderLineId);
    	Assert.assertNotNull(response.getBody());
    }

    @Test
    public void tableHeader() {
		Long restaurantId = 1L;
		Long userAuthenticationId = 1L;
    	String tableNumber = "2";
    	this.tableHeader(restaurantId, null, tableNumber);
    	this.tableHeader(restaurantId, userAuthenticationId, tableNumber);
    }

    private DinnerTableDto tableHeader(Long restaurantId, Long userAuthenticationId, String tableNumber) {
    	ResponseEntity<DinnerTableDto> response = null;
    	if (userAuthenticationId == null) {
            StringBuilder sb = new StringBuilder(context).append(OrdersController.RESTAURANT_ID_TABLE_HEADER_BY_NUMBER_NUMBER);
            response = restTemplate.getForEntity(sb.toString(), DinnerTableDto.class, restaurantId, tableNumber);
    	} else {
    		StringBuilder sb = new StringBuilder(context).append(OrdersController.RESTAURANT_ID_USER_AUTHENTICATION_ID_TABLE_HEADER_BY_NUMBER_NUMBER);
            response = restTemplate.getForEntity(sb.toString(), DinnerTableDto.class, restaurantId, userAuthenticationId, tableNumber);
    	}
    	Assert.assertNotNull(response.getBody());
    	return response.getBody();
    }

    @Test
    public void tableOrdersSize() {
		Long id = 1L;
    	Integer size = this.tableOrdersSize(id);
    }

    private Integer tableOrdersSize(Long id) {
        StringBuilder sb = new StringBuilder(context).append(OrdersController.TABLE_ORDERS_SIZE_ID);
        ResponseEntity<Integer> response = restTemplate.getForEntity(sb.toString(), Integer.class, id);
        
        return response.getBody();
	}

	@Test
	public void createTable() {
    	Long restaurantId = 1L;
    	Long userAuthenticationId = 1L;
    	TableHeaderForm table = new TableHeaderForm();
    	Assert.assertNotNull(this.createTable(restaurantId, userAuthenticationId, table));
	}

	private DinnerTableDto createTable(Long restaurantId, Long userAuthenticationId, TableHeaderForm table) {
        StringBuilder sb = new StringBuilder(context).append(OrdersController.CREATE_TABLE_RESTAURANT_ID_USER_AUTHENTICATION_ID);
    	ResponseEntity<DinnerTableDto> response = restTemplate.postForEntity(sb.toString(), table, DinnerTableDto.class, restaurantId, userAuthenticationId);
    	return response.getBody();
	}

    @Test
	public void findTable() {
    	Long id = 1L;
    	DinnerTableDto table = this.findTable(id);
    	Assert.assertNotNull(table);
	}

	private DinnerTableDto findTable(Long id) {
        StringBuilder sb = new StringBuilder(context).append(OrdersController.FIND_TABLE_ID);
        ResponseEntity<DinnerTableDto> response = restTemplate.getForEntity(sb.toString(), DinnerTableDto.class, id);
        return response.getBody();
	}

    @Test
	public void findProduct() {
    	Long restaurantId = 1L; 
    	String code = "11";
        StringBuilder sb = new StringBuilder(context).append(OrdersController.RESTAURANT_ID_FIND_PRODUCT_CODE);
        ResponseEntity<ProductDto> response = restTemplate.getForEntity(sb.toString(), ProductDto.class, restaurantId, code);
    	Assert.assertNotNull(response.getBody());
	}

    @Test
	public void updateTableCreationDateCustomersNumber() {
    	Long id = 1L; 
    	AcknowledgmentMessage message = this.resetTableCreationDateCustomersNumber(id);
        Assert.assertNotNull(message);
	}

	private AcknowledgmentMessage resetTableCreationDateCustomersNumber(Long id) {
        StringBuilder sb = new StringBuilder(context).append(OrdersController.RESET_TABLE_DINNER_TABLE_ID);
        ResponseEntity<AcknowledgmentMessage> response = restTemplate.postForEntity(sb.toString(), null, AcknowledgmentMessage.class, id);
    	return response.getBody();
	}

	
    @Test
	public void updateTableCustomersNumber() {
    	Long id = 1L; 
    	Integer customersNumber = 2;
    	AcknowledgmentMessage message = this.updateTableCustomersNumber(id, customersNumber);
        Assert.assertNotNull(message);
	}

	private AcknowledgmentMessage updateTableCustomersNumber(Long id, Integer customersNumber) {
        StringBuilder sb = new StringBuilder(context).append(OrdersController.UPDATE_TABLE_ID_CUSTOMERS_NUMBER_CUSTOMERS_NUMBER);
        ResponseEntity<AcknowledgmentMessage> response = restTemplate.postForEntity(sb.toString(), null, AcknowledgmentMessage.class, id, customersNumber);
    	return response.getBody();
	}

	@Test
	public void tablesView() {
		Long restaurantId = 1L;
		String state = "state";
        StringBuilder sb = new StringBuilder(context).append(OrdersController.RESTAURANT_ID_TABLES_STATE_VIEW);
        ResponseEntity<String> response = restTemplate.getForEntity(sb.toString(), String.class, restaurantId, state);
    	Assert.assertNull(response.getBody());
	}
}
