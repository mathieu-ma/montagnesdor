package fr.mch.mdo.restaurant.controller;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import fr.mch.mdo.restaurant.beans.dto.AcknowledgmentMessage;
import fr.mch.mdo.restaurant.beans.dto.DinnerTableDto;
import fr.mch.mdo.restaurant.beans.dto.OrderLineDto;
import fr.mch.mdo.restaurant.services.business.managers.TableState;
import fr.mch.mdo.restaurant.ui.forms.ResetTableForm;
import fr.mch.mdo.restaurant.ui.forms.SaveOrderLineForm;
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
        DinnerTableDto[] dinnerTables = restTemplate.getForObject(sb.toString(), DinnerTableDto[].class, restaurantId, state);
    	Assert.assertNotNull("dinnerTables", dinnerTables);
    	Assert.assertTrue("dinnerTables size", dinnerTables.length == 2);
        sb = new StringBuilder(context).append(OrdersController.RESTAURANT_ID_USER_AUTHENTICATION_ID_TABLES_STATE);
        dinnerTables = restTemplate.getForObject(sb.toString(), DinnerTableDto[].class, userAuthenticationId, restaurantId, state);
    	Assert.assertNotNull(dinnerTables);
    	Assert.assertNotNull("dinnerTables", dinnerTables);
    	Assert.assertTrue("dinnerTables size", dinnerTables.length == 1);

		state = TableState.CASHED.name();
        sb = new StringBuilder(context).append(OrdersController.RESTAURANT_ID_USER_AUTHENTICATION_ID_TABLES_STATE);
        dinnerTables = restTemplate.getForObject(sb.toString(), DinnerTableDto[].class, userAuthenticationId, restaurantId, state);
    	Assert.assertNotNull(dinnerTables);
    	Assert.assertTrue("dinnerTables size", dinnerTables.length == 0);
	}

	@Test
	public void deleteTable() {
    	Long tableId = 1L;
        StringBuilder sb = new StringBuilder(context).append(OrdersController.DELETE_TABLE_ID);
		// Do not use restTemplate.delete method because the service returns nothing
    	ResponseEntity<AcknowledgmentMessage> response = restTemplate.exchange(sb.toString(), HttpMethod.DELETE, null, AcknowledgmentMessage.class, tableId);
    	Assert.assertNotNull("Ack delete table", response.getBody());
    	Assert.assertNotNull("Ack delete table", response.getBody().getType());
    	Assert.assertTrue("Ack delete table", AcknowledgmentMessage.Type.SUCCESS.equals(response.getBody().getType()));
	}
     
    @Test
    public void tableHeader() {
		Long restaurantId = 1L;
		Long userAuthenticationId = 1L;
    	String tableNumber = "1";
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
	public void createTable() {
    	Long restaurantId = 1L;
    	Long userAuthenticationId = 1L;
    	TableHeaderForm form = new TableHeaderForm();
    	form.setNumber("1");
    	form.setCustomersNumber(2);
    	Assert.assertNotNull(this.createTable(restaurantId, userAuthenticationId, form));
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
	public void resetTable() {
    	Long dinnerTableId = 1L; 
    	AcknowledgmentMessage message = this.resetTable(dinnerTableId);
        Assert.assertNotNull(message);
        Assert.assertTrue(AcknowledgmentMessage.Type.ERROR.equals(message.getType()));
	}

	private AcknowledgmentMessage resetTable(Long dinnerTableId) {
        StringBuilder sb = new StringBuilder(context).append(OrdersController.RESET_TABLE_DINNER_TABLE_ID);
        ResetTableForm form = new ResetTableForm();
        form.setCustomersNumber(2);
        form.setNumber("1");
        form.setRestaurantId(1L);
        form.setUserAuthenticationId(1L);
        ResponseEntity<AcknowledgmentMessage> response = restTemplate.postForEntity(sb.toString(), form, AcknowledgmentMessage.class, dinnerTableId);
    	return response.getBody();
	}

    @Test
	public void updateTableCustomersNumber() {
    	Long id = 1L; 
    	Integer customersNumber = 2;
    	AcknowledgmentMessage message = this.updateTableCustomersNumber(id, customersNumber);
        Assert.assertNotNull(message);
        Assert.assertTrue(AcknowledgmentMessage.Type.ERROR.equals(message.getType()));
	}

	private AcknowledgmentMessage updateTableCustomersNumber(Long id, Integer customersNumber) {
        StringBuilder sb = new StringBuilder(context).append(OrdersController.UPDATE_TABLE_ID_CUSTOMERS_NUMBER_CUSTOMERS_NUMBER);
        ResponseEntity<AcknowledgmentMessage> response = restTemplate.postForEntity(sb.toString(), null, AcknowledgmentMessage.class, id, customersNumber);
    	return response.getBody();
	}

    @Test
    public void tableOrdersSize() {
		Long id = 1L;
    	Integer size = this.tableOrdersSize(id);
    	Assert.assertNotNull(size);
    	Assert.assertTrue("Order size", size > 0);
    }

    private Integer tableOrdersSize(Long id) {
        StringBuilder sb = new StringBuilder(context).append(OrdersController.TABLE_ORDERS_SIZE_ID);
        ResponseEntity<Integer> response = restTemplate.getForEntity(sb.toString(), Integer.class, id);
        
        return response.getBody();
	}

    @Test
    public void saveOrderLine() {
    	SaveOrderLineForm form = new SaveOrderLineForm();
    	OrderLineDto orderLine = new OrderLineDto();
    	form.setOrderLine(orderLine);
        StringBuilder sb = new StringBuilder(context).append(OrdersController.SAVE_ORDER_LINE);
        AcknowledgmentMessage ack = restTemplate.postForObject(sb.toString(), form, AcknowledgmentMessage.class);
    	Assert.assertNotNull(ack);
    	Assert.assertNotNull(ack.getAttachment());
    }

    @Test
    public void deleteOrderLine() {
    	Long orderLineId = 1L;
        StringBuilder sb = new StringBuilder(context).append(OrdersController.DELETE_ORDER_LINE_ID);
		// Do not use delete method because the service returns nothing
    	ResponseEntity<AcknowledgmentMessage> response = restTemplate.exchange(sb.toString(), HttpMethod.DELETE, null, AcknowledgmentMessage.class, orderLineId);
    	Assert.assertNotNull(response.getBody());
        Assert.assertTrue(AcknowledgmentMessage.Type.ERROR.equals(response.getBody().getType()));
    }

    @Test
	public void findOrderLine() {
    	Long restaurantId = 1L;
    	BigDecimal quantity = BigDecimal.TEN; 
    	String orderCode = "#11";
    	Long locId = 1L;
        StringBuilder sb = new StringBuilder(context).append(OrdersController.RESTAURANT_ID_FIND_ORDER_LINE_CODE_LOC_ID);
        OrderLineDto orderLine = restTemplate.getForObject(sb.toString(), OrderLineDto.class, restaurantId, quantity, orderCode, locId);
    	Assert.assertNotNull(orderLine);
    	Assert.assertEquals("OrderLineDto orderCode", orderCode, orderLine.getCode());
	}

	@Test
	public void tablesView() {
		Long restaurantId = 1L;
        StringBuilder sb = new StringBuilder(context).append(OrdersController.RESTAURANT_ID_TABLES_STATE_VIEW);
        ResponseEntity<String> response = restTemplate.getForEntity(sb.toString(), String.class, restaurantId, TableState.ALTERABLE.name());
    	Assert.assertNotNull(response.getBody());
	}
}
