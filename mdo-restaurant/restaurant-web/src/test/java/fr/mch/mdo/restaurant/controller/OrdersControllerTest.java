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
	public static final String context = SERVER_URL + "/orders";

	@Test
	public void tables() {
		String state = TableState.ALTERABLE.name();
		Long restaurantId = 1L;
		Long userAuthenticationId = 1L;
        StringBuilder sb = new StringBuilder(context).append("/{restaurantId}/tables/{state}");
        DinnerTableDto[] dinnerTableItems = restTemplate.getForObject(sb.toString(), DinnerTableDto[].class, restaurantId, state);
    	Assert.assertNotNull(dinnerTableItems);
        sb = new StringBuilder(context).append("/{restaurantId}/{userAuthenticationId}/tables/{state}");
        dinnerTableItems = restTemplate.getForObject(sb.toString(), DinnerTableDto[].class, userAuthenticationId, restaurantId, state);
    	Assert.assertNotNull(dinnerTableItems);

		state = TableState.CASHED.name();
        sb = new StringBuilder(context).append("/{restaurantId}/{userAuthenticationId}/tables/{state}");
        dinnerTableItems = restTemplate.getForObject(sb.toString(), DinnerTableDto[].class, userAuthenticationId, restaurantId, state);
    	Assert.assertNotNull(dinnerTableItems);

	}

	@Test
	public void deleteTable() {
    	Long tableId = 1L;
        StringBuilder sb = new StringBuilder(context).append("/delete/table/{id}");
		// Do not use restTemplate.delete method because the service returns nothing
    	ResponseEntity<AcknowledgmentMessage> response = restTemplate.exchange(sb.toString(), HttpMethod.DELETE, null, AcknowledgmentMessage.class, tableId);
    	Assert.assertNotNull(response.getBody());
	}
     
    @Test
    public void deleteOrderLine() {
    	Long orderLineId = 1L;
        StringBuilder sb = new StringBuilder(context).append("/delete/order/line/{id}");
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
            StringBuilder sb = new StringBuilder(context).append("{restaurantId}/table/header/by/number/{number}");
            response = restTemplate.getForEntity(sb.toString(), DinnerTableDto.class, restaurantId, tableNumber);
    	} else {
    		StringBuilder sb = new StringBuilder(context).append("{userAuthenticationId}/{restaurantId}/table/header/by/number/{number}");
            response = restTemplate.getForEntity(sb.toString(), DinnerTableDto.class, userAuthenticationId, restaurantId, tableNumber);
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
        StringBuilder sb = new StringBuilder(context).append("/table/orders/size/{id}");
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
        StringBuilder sb = new StringBuilder(context).append("/create/table/{restaurantId}/{userAuthenticationId}");
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
        StringBuilder sb = new StringBuilder(context).append("/find/table/{id}");
        ResponseEntity<DinnerTableDto> response = restTemplate.getForEntity(sb.toString(), DinnerTableDto.class, id);
        return response.getBody();
	}

    @Test
	public void findProduct() {
    	Long restaurantId = 1L; 
    	String code = "11";
        StringBuilder sb = new StringBuilder(context).append("/{restaurantId}/find/product/{code}");
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
        StringBuilder sb = new StringBuilder(context).append("/reset/table/creation/date/customers/number/{id}");
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
        StringBuilder sb = new StringBuilder(context).append("/update/table/{id}/customers/number/{customersNumber}");
        ResponseEntity<AcknowledgmentMessage> response = restTemplate.postForEntity(sb.toString(), null, AcknowledgmentMessage.class, id, customersNumber);
    	return response.getBody();
	}

	@Test
	public void tablesView() {
		Long restaurantId = 1L;
		String type = "type";
        StringBuilder sb = new StringBuilder(context).append("/{restaurantId}/tables/{type}/view");
        ResponseEntity<String> response = restTemplate.getForEntity(sb.toString(), String.class, restaurantId, type);
    	Assert.assertNull(response.getBody());
	}

	@Test
	public void ordersIntegration() {
		Long pathVarRestaurantId = 1L;
		Long pathVarUserAuthenticationId = 1L;
    	String userEntryTableNumber = "1";
    	Integer userEntryCustomersNumber = 2;
    	// The table exists with orders size 1
    	DinnerTableDto table = this.tableHeaderIntegration(pathVarRestaurantId, pathVarUserAuthenticationId, userEntryTableNumber, userEntryCustomersNumber);
	}
	
	private DinnerTableDto tableHeaderIntegration(Long pathVarRestaurantId, Long pathVarUserAuthenticationId, String userEntryTableNumber, Integer userEntryCustomersNumber) {
		DinnerTableDto result = null;

    	DinnerTableDto header = this.tableHeader(pathVarRestaurantId, pathVarUserAuthenticationId, userEntryTableNumber);

		// Does this table already exist(check id) ?
    	if (header.getId() != null) {
        	// First case: Yes, the table already exists.
			Integer size = this.tableOrdersSize(header.getId());
	    	// Are the orders empty(check orders) ?  
			if (size != null && size.intValue() > 0 ) {
				// No, orders is not empty.

		    	// Is this table for take-away(check takeaway) ?
				if (Boolean.TRUE.equals(header.getTakeaway())) {
					// Yes, the table is for take-away then directly display the table.
					result = this.findTable(header.getId());
	    		} else {
	    	    	// Get user entry number of customers
	    			// Is user entry number of customers equal to the one from database ?
	    			if (userEntryCustomersNumber != null && !userEntryCustomersNumber.equals(header.getCustomersNumber())) {
	    				// No, the user entry number of customers is not equal to the one from database then update the field
	    				header.setCustomersNumber(userEntryCustomersNumber);
	    		    	// Update the customers number.
						AcknowledgmentMessage message = this.updateTableCustomersNumber(header.getId(), header.getCustomersNumber());
	    			}
	    			// Display the table.
	    			result = this.findTable(header.getId());
	    		}
			} else {
				// Yes, orders is empty then reset the customers number to 0 and the creation date to now and display the table.
				AcknowledgmentMessage message = this.resetTableCreationDateCustomersNumber(header.getId());
    			// Display the table.
				result = this.findTable(header.getId());
			}
    	} else {
        	// Second case: No, the table does not exist.

    		// Is this table for take-away(check takeaway) ?
			if (Boolean.TRUE.equals(header.getTakeaway())) {
				// Yes, the table is for take-away 
				
				// Create the table and display it.
		    	TableHeaderForm tableHeaderForm = new TableHeaderForm();
		    	tableHeaderForm.setNumber(header.getNumber());
		    	tableHeaderForm.setCustomersNumber(0);
		    	result = this.createTable(pathVarRestaurantId, pathVarUserAuthenticationId, tableHeaderForm);
    		} else {
    	    	// Get user entry number of customers
    			if (userEntryCustomersNumber != null) {
                	// Create the table and display it.
    		    	TableHeaderForm tableHeaderForm = new TableHeaderForm();
    		    	tableHeaderForm.setNumber(header.getNumber());
    		    	tableHeaderForm.setCustomersNumber(userEntryCustomersNumber);
    		    	result = this.createTable(pathVarRestaurantId, pathVarUserAuthenticationId, tableHeaderForm);
    			}
    		}
    	}
		
		return result;
	}
}
