package fr.mch.mdo.restaurant.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

import fr.mch.mdo.restaurant.beans.dto.AcknowledgmentMessage;
import fr.mch.mdo.restaurant.beans.dto.DinnerTableDto;
import fr.mch.mdo.restaurant.beans.dto.OrderLineDto;
import fr.mch.mdo.restaurant.ui.forms.FindOrderLineForm;
import fr.mch.mdo.restaurant.ui.forms.ResetTableForm;
import fr.mch.mdo.restaurant.ui.forms.SaveOrderLineForm;
import fr.mch.mdo.restaurant.ui.forms.TableHeaderForm;
import fr.mch.mdo.restaurant.web.AbstractControllerIntegrationTest;


public final class OrdersControllerIntegrationTest extends AbstractControllerIntegrationTest
{
	private static final String USER_ENTRY_AGAIN_ORDER_CODE = "11";

	public static final String context = SERVER_URL + OrdersController.ORDERS_CONTROLLER;

	private static final Integer DEFAULT_CUSTOMERS_NUMBER = 2;
	private static final String CUSTOMERS_NUMBER_PARAM_EXIST_EAT_IN_ORDERS_NOT_EMPTY_UPDATE_OK = "12";
	private static final String CUSTOMERS_NUMBER_PARAM_EXIST_EAT_IN_ORDERS_NOT_EMPTY_UPDATE_NOK = "12NOK";
	private static final String CUSTOMERS_NUMBER_PARAM_EXIST_TAKEAWAY_ORDERS_NOT_EMPTY = "E1";
	private static final String CREATION_DATE_CUSTOMERS_NUMBER_PARAM_EXIST_TAKEAWAY_ORDERS_EMPTY_RESET_OK = "E1ROK";
	private static final String CREATION_DATE_CUSTOMERS_NUMBER_PARAM_EXIST_TAKEAWAY_ORDERS_EMPTY_RESET_NOK = "E1RKO";
	private static final String CREATION_DATE_CUSTOMERS_NUMBER_PARAM_NOT_EXIST_TAKEAWAY = "E1NE";
	private static final String CREATION_DATE_CUSTOMERS_NUMBER_PARAM_NOT_EXIST_EAT_IN_1 = "12NE1";
	private static final String CREATION_DATE_CUSTOMERS_NUMBER_PARAM_NOT_EXIST_EAT_IN_2 = "12NE2";
	
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

    private Integer tableOrdersSize(Long id) {
        StringBuilder sb = new StringBuilder(context).append(OrdersController.TABLE_ORDERS_SIZE_ID);
        ResponseEntity<Integer> response = restTemplate.getForEntity(sb.toString(), Integer.class, id);
        
        return response.getBody();
	}

	private DinnerTableDto createTable(Long restaurantId, Long userAuthenticationId, TableHeaderForm table) {
        StringBuilder sb = new StringBuilder(context).append(OrdersController.CREATE_TABLE_RESTAURANT_ID_USER_AUTHENTICATION_ID);
    	ResponseEntity<DinnerTableDto> response = restTemplate.postForEntity(sb.toString(), table, DinnerTableDto.class, restaurantId, userAuthenticationId);
    	return response.getBody();
	}

	private DinnerTableDto findTable(Long id) {
        StringBuilder sb = new StringBuilder(context).append(OrdersController.FIND_TABLE_ID);
        ResponseEntity<DinnerTableDto> response = restTemplate.getForEntity(sb.toString(), DinnerTableDto.class, id);
        return response.getBody();
	}

	private AcknowledgmentMessage resetTable(String tableNumber, Long dinnerTableId, Long restaurantId, Long userAuthenticationId, Integer customersNumber) {
        StringBuilder sb = new StringBuilder(context).append(OrdersController.RESET_TABLE_DINNER_TABLE_ID);
        ResetTableForm form = new ResetTableForm();
        form.setCustomersNumber(customersNumber);
        form.setNumber(tableNumber);
        form.setRestaurantId(restaurantId);
        form.setUserAuthenticationId(userAuthenticationId);
        ResponseEntity<AcknowledgmentMessage> response = restTemplate.postForEntity(sb.toString(), form, AcknowledgmentMessage.class, dinnerTableId);
        AcknowledgmentMessage ack = response.getBody();
        if (CREATION_DATE_CUSTOMERS_NUMBER_PARAM_EXIST_TAKEAWAY_ORDERS_EMPTY_RESET_NOK.equals(tableNumber)) {
        	ack = new AcknowledgmentMessage();
        	ack.setType(AcknowledgmentMessage.Type.ERROR);
        }
    	return ack;
	}

	private AcknowledgmentMessage updateTableCustomersNumber(String tableNumber, Long id, Integer customersNumber) {
        StringBuilder sb = new StringBuilder(context).append(OrdersController.UPDATE_TABLE_ID_CUSTOMERS_NUMBER_CUSTOMERS_NUMBER);
        ResponseEntity<AcknowledgmentMessage> response = restTemplate.postForEntity(sb.toString(), null, AcknowledgmentMessage.class, id, customersNumber);
        AcknowledgmentMessage ack = response.getBody();
        if (CUSTOMERS_NUMBER_PARAM_EXIST_EAT_IN_ORDERS_NOT_EMPTY_UPDATE_NOK.equals(tableNumber)) {
        	ack = new AcknowledgmentMessage();
        	ack.setType(AcknowledgmentMessage.Type.ERROR);
        }
    	return ack;
	}

	@Test
	public void header() {
		Long pathVarRestaurantId = 1L;
		Long pathVarUserAuthenticationId = 1L;
    	String userEntryTableNumber = null;
    	Integer userEntryCustomersNumber = null;
    	DinnerTableDto table = null;
    	// The table exists with orders not empty. It is not a take-away type. 
    	// The userEntryCustomersNumber is null.
    	// The method updateTableCustomersNumber is OK.
    	userEntryTableNumber = CUSTOMERS_NUMBER_PARAM_EXIST_EAT_IN_ORDERS_NOT_EMPTY_UPDATE_OK;
    	table = this.tableHeader(pathVarRestaurantId, pathVarUserAuthenticationId, userEntryTableNumber, null);
    	checkTableHeaderResultExistOrdersNotEmpty(table);
    	// The table exists with orders not empty. It is not a take-away type. 
    	// The userEntryCustomersNumber is not null.
    	// The customer number has changed compared to the one from database.
    	// The method updateTableCustomersNumber is OK.
    	userEntryTableNumber = CUSTOMERS_NUMBER_PARAM_EXIST_EAT_IN_ORDERS_NOT_EMPTY_UPDATE_OK;
    	userEntryCustomersNumber = 1;
    	table = this.tableHeader(pathVarRestaurantId, pathVarUserAuthenticationId, userEntryTableNumber, userEntryCustomersNumber);
    	checkTableHeaderResultExistOrdersNotEmpty(table);
    	// The table exists with orders not empty. It is not a take-away type. 
    	// The userEntryCustomersNumber is not null.
    	// The customer number is the same as the one from database.
    	// The method updateTableCustomersNumber is OK.
    	userEntryTableNumber = CUSTOMERS_NUMBER_PARAM_EXIST_EAT_IN_ORDERS_NOT_EMPTY_UPDATE_OK;
    	table = this.tableHeader(pathVarRestaurantId, pathVarUserAuthenticationId, userEntryTableNumber, userEntryCustomersNumber);
    	checkTableHeaderResultExistOrdersNotEmpty(table);
    	// The table exists with orders not empty. It is not a take-away type. 
    	// The userEntryCustomersNumber is not null.
    	// The customer number has not changed compared to the one from database.
    	// The method updateTableCustomersNumber is NOK.
    	userEntryTableNumber = CUSTOMERS_NUMBER_PARAM_EXIST_EAT_IN_ORDERS_NOT_EMPTY_UPDATE_NOK;
    	table = this.tableHeader(pathVarRestaurantId, pathVarUserAuthenticationId, 
    			userEntryTableNumber, userEntryCustomersNumber);
    	checkTableHeaderResultExistOrdersNotEmpty(table);

    	// The table exists with orders not empty. It is a take-away type.
    	userEntryTableNumber = CUSTOMERS_NUMBER_PARAM_EXIST_TAKEAWAY_ORDERS_NOT_EMPTY;
    	table = this.tableHeader(pathVarRestaurantId, pathVarUserAuthenticationId, userEntryTableNumber, userEntryCustomersNumber);
    	checkTableHeaderResultExistOrdersNotEmpty(table);
    	
    	// The table exists with empty orders.
    	// The method resetTableCreationDateCustomersNumber is OK.
    	userEntryTableNumber = CREATION_DATE_CUSTOMERS_NUMBER_PARAM_EXIST_TAKEAWAY_ORDERS_EMPTY_RESET_OK;
    	table = this.tableHeader(pathVarRestaurantId, pathVarUserAuthenticationId, userEntryTableNumber, userEntryCustomersNumber);
    	checkTableHeaderCreateOrReset(table);
    	// The table exists with empty orders.
    	// The method resetTableCreationDateCustomersNumber is NOK.
    	userEntryTableNumber = CREATION_DATE_CUSTOMERS_NUMBER_PARAM_EXIST_TAKEAWAY_ORDERS_EMPTY_RESET_NOK;
    	table = this.tableHeader(pathVarRestaurantId, pathVarUserAuthenticationId, userEntryTableNumber, userEntryCustomersNumber);
    	checkTableHeaderCreateOrReset(table);

    	// The table does not exist but table type is take-away so create it.
    	userEntryTableNumber = CREATION_DATE_CUSTOMERS_NUMBER_PARAM_NOT_EXIST_TAKEAWAY;
    	table = this.tableHeader(pathVarRestaurantId, pathVarUserAuthenticationId, userEntryTableNumber, userEntryCustomersNumber);
    	checkTableHeaderCreateOrReset(table);
    	// The table does not exist but table type is not take-away type.
    	// The userEntryCustomersNumber is not null.
    	userEntryTableNumber = CREATION_DATE_CUSTOMERS_NUMBER_PARAM_NOT_EXIST_EAT_IN_1;
    	table = this.tableHeader(pathVarRestaurantId, pathVarUserAuthenticationId, userEntryTableNumber, userEntryCustomersNumber);
    	checkTableHeaderCreateOrReset(table);
    	// The table does not exist but table type is not take-away type.
    	// The userEntryCustomersNumber is null.
    	userEntryTableNumber = CREATION_DATE_CUSTOMERS_NUMBER_PARAM_NOT_EXIST_EAT_IN_2;
    	table = this.tableHeader(pathVarRestaurantId, pathVarUserAuthenticationId, userEntryTableNumber, null);
    	checkTableHeaderCreateOrReset(table);
	}
	
	private DinnerTableDto tableHeader(Long pathVarRestaurantId, Long pathVarUserAuthenticationId, 
			String userEntryTableNumber, Integer userEntryCustomersNumber) {
		DinnerTableDto result = null;

    	DinnerTableDto header = this.tableHeader(pathVarRestaurantId, pathVarUserAuthenticationId, userEntryTableNumber);

		// Does this table already exist(check id) ?
    	if (header.getId() != null) {
        	// First case: Yes, the table already exists.
			Integer size = this.tableOrdersSize(header.getId());
	    	// Are the orders empty(check orders) ?  
			if (size != null && size.intValue() > 0 ) {
				// No, orders is not empty.

		    	// Is this table for take-away(check take-away) ?
				if (Boolean.TRUE.equals(header.getTakeaway())) {
					// Yes, the table is for take-away then directly display the table.
	    		} else {
	    	    	// Get user entry number of customers.
	    			// if userEntryCustomersNumber is null then do not update the Customers Number.
	    			// Ask to enter the customers number again with warning message.
	    			if (userEntryCustomersNumber == null) {
	    				return this.tableHeader(pathVarRestaurantId, pathVarUserAuthenticationId, 
	    						userEntryTableNumber, header.getCustomersNumber());
	    			}
	    			
	    			Assert.assertNotNull("tableHeader userEntryCustomersNumber", userEntryCustomersNumber);

	    			// Is user entry number of customers equal to the one from database ?
	    			if (!userEntryCustomersNumber.equals(header.getCustomersNumber())) {
	    				// No, the user entry number of customers is not equal to the one from database then update the field
	    				header.setCustomersNumber(userEntryCustomersNumber);
	    		    	// Update the customers number.
						AcknowledgmentMessage message = this.updateTableCustomersNumber(header.getNumber(), header.getId(), header.getCustomersNumber());
						if (AcknowledgmentMessage.Type.ERROR.equals(message.getType())) {
							// Back to the previous step, i.e, ask to enter the customers number again with warning message.
		    				return this.tableHeader(pathVarRestaurantId, pathVarUserAuthenticationId, 
		    						CUSTOMERS_NUMBER_PARAM_EXIST_EAT_IN_ORDERS_NOT_EMPTY_UPDATE_OK, userEntryCustomersNumber);
						}
	    			}
	    		}
			} else {
				// Yes, orders is empty then reset the customers number to 0 and the creation date to now and display the table.
				AcknowledgmentMessage message = this.resetTable(header.getNumber(), header.getId(), pathVarRestaurantId, pathVarUserAuthenticationId, userEntryCustomersNumber);
				if (AcknowledgmentMessage.Type.ERROR.equals(message.getType())) {
					// Back to the previous step, i.e, ask to enter the table number again with warning message.
					return this.tableHeader(pathVarRestaurantId, pathVarUserAuthenticationId, 
    						CREATION_DATE_CUSTOMERS_NUMBER_PARAM_EXIST_TAKEAWAY_ORDERS_EMPTY_RESET_OK, userEntryCustomersNumber);
				}
			}
			// Display the table.
			result = this.findTable(header.getId());
    	} else {
        	// Second case: No, the table does not exist.

	    	TableHeaderForm tableHeaderForm = new TableHeaderForm();
	    	tableHeaderForm.setNumber(header.getNumber());

	    	// Is this table for take-away(check takeaway) ?
			if (Boolean.TRUE.equals(header.getTakeaway())) {
				// Yes, the table is for take-away 
				// Create the table and display it.
		    	tableHeaderForm.setCustomersNumber(0);
    		} else {
    	    	// Get user entry number of customers.
    			// If userEntryCustomersNumber is null then do not create the table.
    			// Ask to enter the customers number again with warning message.
    			if (userEntryCustomersNumber == null) {
    				return this.tableHeader(pathVarRestaurantId, pathVarUserAuthenticationId, 
    						userEntryTableNumber, DEFAULT_CUSTOMERS_NUMBER);
    			}
    			
    			Assert.assertNotNull("tableHeader userEntryCustomersNumber", userEntryCustomersNumber);

    			// Create the table and display it.
   		    	tableHeaderForm.setCustomersNumber(userEntryCustomersNumber);
    		}
			// Display the table.
	    	result = this.createTable(pathVarRestaurantId, pathVarUserAuthenticationId, tableHeaderForm);
    	}
		
		return result;
	}
	
	private void checkTableHeaderResultExistOrdersNotEmpty(DinnerTableDto table) {
		Assert.assertNotNull("DinnerTableDto", table);
		Assert.assertFalse("DinnerTableDto.allowModifyOrdersAfterPrinting", table.getAllowModifyOrdersAfterPrinting());
		Assert.assertNotNull("DinnerTableDto.amountPay", table.getAmountPay());
		Assert.assertNotNull("DinnerTableDto.amountsSum", table.getAmountsSum());
		Assert.assertNull("DinnerTableDto.cashingDate", table.getCashingDate());
		Assert.assertNotNull("DinnerTableDto.customersNumber", table.getCustomersNumber());
		Assert.assertNotNull("DinnerTableDto.id", table.getId());
		Assert.assertNotNull("DinnerTableDto.number", table.getNumber());
		Assert.assertNotNull("DinnerTableDto.orders", table.getOrders());
		Assert.assertNull("DinnerTableDto.printingDate", table.getPrintingDate());
		Assert.assertNotNull("DinnerTableDto.quantitiesSum", table.getQuantitiesSum());
		Assert.assertNotNull("DinnerTableDto.reduction", table.getReduction());
		Assert.assertNotNull("DinnerTableDto.reductionRatio", table.getReductionRatio());
		Assert.assertFalse("DinnerTableDto.reductionRatioManuallyChanged", table.getReductionRatioManuallyChanged());
		Assert.assertNotNull("DinnerTableDto.registrationDate", table.getRegistrationDate());
		Assert.assertNotNull("DinnerTableDto.takeaway", table.getTakeaway());
	}
	
	private void checkTableHeaderCreateOrReset(DinnerTableDto table) {
		NumberFormat nf = NumberFormat.getInstance();
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		
		Assert.assertNotNull("DinnerTableDto", table);
		Assert.assertFalse("DinnerTableDto.allowModifyOrdersAfterPrinting", table.getAllowModifyOrdersAfterPrinting());
		Assert.assertNotNull("DinnerTableDto.amountPay", table.getAmountPay());
		Assert.assertEquals("DinnerTableDto.amountPay", nf.format(BigDecimal.ZERO), nf.format(table.getAmountPay()));
		Assert.assertNotNull("DinnerTableDto.amountsSum", table.getAmountsSum());
		Assert.assertEquals("DinnerTableDto.amountsSum", nf.format(BigDecimal.ZERO), nf.format(table.getAmountsSum()));
		Assert.assertNull("DinnerTableDto.cashingDate", table.getCashingDate());
		Assert.assertNotNull("DinnerTableDto.customersNumber", table.getCustomersNumber());
		Assert.assertNotNull("DinnerTableDto.id", table.getId());
		Assert.assertNotNull("DinnerTableDto.number", table.getNumber());
		Assert.assertNotNull("DinnerTableDto.orders", table.getOrders());
		Assert.assertTrue("DinnerTableDto.orders", table.getOrders().isEmpty());
		Assert.assertNull("DinnerTableDto.printingDate", table.getPrintingDate());
		Assert.assertNotNull("DinnerTableDto.quantitiesSum", table.getQuantitiesSum());
		Assert.assertEquals("DinnerTableDto.quantitiesSum", nf.format(BigDecimal.ZERO), nf.format(table.getQuantitiesSum()));
		Assert.assertNotNull("DinnerTableDto.reduction", table.getReduction());
		Assert.assertEquals("DinnerTableDto.reduction", nf.format(BigDecimal.ZERO), nf.format(table.getReduction()));
		Assert.assertNotNull("DinnerTableDto.reductionRatio", table.getReductionRatio());
		Assert.assertFalse("DinnerTableDto.reductionRatioManuallyChanged", table.getReductionRatioManuallyChanged());
		// WARNING : this could be false if the test is launched between 23:59:59 and 00:00:01
		Assert.assertEquals("DinnerTableDto.registrationDate", df.format(new Date()), df.format(table.getRegistrationDate()));
		Assert.assertNotNull("DinnerTableDto.takeaway", table.getTakeaway());
	}
	
	private OrderLineDto findOrderLine(FindOrderLineForm form, Long restaurantId, Long locId) {
		StringBuilder sb = new StringBuilder(context).append(OrdersController.RESTAURANT_ID_FIND_ORDER_LINE_CODE_LOC_ID);
        OrderLineDto result = restTemplate.postForObject(sb.toString(), form, OrderLineDto.class, restaurantId, locId);
    	Assert.assertNotNull(result);
    	Assert.assertEquals("OrderLineDto quantity", form.getQuantity(), result.getQuantity());
    	Assert.assertEquals("OrderLineDto orderCode", form.getOrderCode(), result.getCode());
        return result;
	}

	private Long saveOrderLine(OrderLineDto orderLine) {
    	SaveOrderLineForm form = new SaveOrderLineForm();
    	form.setOrderLine(orderLine);
        StringBuilder sb = new StringBuilder(context).append(OrdersController.SAVE_ORDER_LINE);
        AcknowledgmentMessage ack = restTemplate.postForObject(sb.toString(), form, AcknowledgmentMessage.class);
    	Assert.assertNotNull(ack);
    	Assert.assertTrue(ack.getAttachment() instanceof Number);
    	Long result = Long.valueOf(ack.getAttachment().toString());
    	return result;
    }

	@Test
	public void orders() {
		
		Long varOrderLineId = null;
		Long firstInsertOrderLineId = null;
		
		for (int i = 0; i < 2; i++) {
			if (i == 1) {
				varOrderLineId = firstInsertOrderLineId;
			}

			// For finding order line.
			Long pathVarRestaurantId = 1L;
			Long pathVarLocId = 1L;
			// Quantity is used to process the amount.
			BigDecimal userEntryQuantity = new BigDecimal(2.1);
			String userEntryCode = "+" + USER_ENTRY_AGAIN_ORDER_CODE;
			FindOrderLineForm form = new FindOrderLineForm();

			// For saving order line.
			Long varDinnerTableId = 1L;
			String userEntryLabel = "User Entry Label";
			BigDecimal userEntryUnitPrice = new BigDecimal(2.5);
			
			// Product Special Code does not exist.
			// Product does not exist.
			form = new FindOrderLineForm();
			form.setQuantity(userEntryQuantity);
			form.setOrderCode(userEntryCode);		
			OrderLineDto orderLine = this.findAndSaveOrderLine(pathVarRestaurantId, pathVarLocId, 
					userEntryLabel, userEntryUnitPrice,
					varDinnerTableId, varOrderLineId,
					form);
			firstInsertOrderLineId = orderLine.getId();
			this.checkSavingOrderLine(orderLine, firstInsertOrderLineId);

			// Product Special Code does not exist.
			// Product does exist.
			userEntryCode = USER_ENTRY_AGAIN_ORDER_CODE;
			form = new FindOrderLineForm();
			form.setQuantity(userEntryQuantity);
			form.setOrderCode(userEntryCode);
			orderLine = this.findAndSaveOrderLine(pathVarRestaurantId, pathVarLocId, 
					userEntryLabel, userEntryUnitPrice,
					varDinnerTableId, varOrderLineId,
					form);
			if (varOrderLineId == null) {
				// Insert new Order Line.
				firstInsertOrderLineId++;
			} else {
				// Update Order Line.
			}
			this.checkSavingOrderLine(orderLine, firstInsertOrderLineId);

			// Product Special Code does exist.
			// Product does not exist.
			userEntryCode = "/";
			form = new FindOrderLineForm();
			form.setQuantity(userEntryQuantity);
			form.setOrderCode(userEntryCode);		
			orderLine = this.findAndSaveOrderLine(pathVarRestaurantId, pathVarLocId, 
					userEntryLabel, userEntryUnitPrice,
					varDinnerTableId, varOrderLineId,
					form);
			if (varOrderLineId == null) {
				// Insert new Order Line.
				firstInsertOrderLineId++;
			} else {
				// Update Order Line.
			}
			this.checkSavingOrderLine(orderLine, firstInsertOrderLineId);
			
			// Product Special Code does exist.
			// Product does exist.
			userEntryCode = "#" + USER_ENTRY_AGAIN_ORDER_CODE;
			form = new FindOrderLineForm();
			form.setQuantity(userEntryQuantity);
			form.setOrderCode(userEntryCode);		
			orderLine = this.findAndSaveOrderLine(pathVarRestaurantId, pathVarLocId, 
					userEntryLabel, userEntryUnitPrice,
					varDinnerTableId, varOrderLineId,
					form);
			if (varOrderLineId == null) {
				// Insert new Order Line.
				firstInsertOrderLineId++;
			} else {
				// Update Order Line.
			}
			this.checkSavingOrderLine(orderLine, firstInsertOrderLineId);			
		}
	}
	
	private void checkSavingOrderLine(OrderLineDto orderLine, Long checkingInsertOrderLineId) {
		Assert.assertNotNull("OrderLineDto id", orderLine.getId());
		Assert.assertEquals("OrderLineDto id", checkingInsertOrderLineId, orderLine.getId());
	}

	public OrderLineDto findAndSaveOrderLine(Long pathVarRestaurantId, Long pathVarLocId,
			String userEntryLabel, BigDecimal userEntryUnitPrice, 
			Long varDinnerTableId, Long varOrderLineId,
			FindOrderLineForm form) {
		NumberFormat nf = NumberFormat.getInstance();
		
		Assert.assertNotNull("FindOrderLineForm", form);
		Assert.assertNotNull("FindOrderLineForm.getQuantity", form.getQuantity());
		Assert.assertNotNull("FindOrderLineForm.getOrderCode", form.getOrderCode());
		Assert.assertNotNull("pathVarRestaurantId", pathVarRestaurantId);
		Assert.assertNotNull("pathVarLocId", pathVarLocId);

		Assert.assertNotNull("User Entry label", userEntryLabel);
		Assert.assertNotNull("User Entry unit price", userEntryUnitPrice);

        OrderLineDto result = this.findOrderLine(form, pathVarRestaurantId, pathVarLocId);
    	Assert.assertNotNull("OrderLineDto", result);
    	Assert.assertEquals("OrderLineDto quantity", nf.format(form.getQuantity()), nf.format(result.getQuantity()));
    	Assert.assertEquals("OrderLineDto code", form.getOrderCode(), result.getCode());
    	
    	// Does OrderCode contain product special code ?
    	if (result.getProductSpecialCode() == null) {
    		// No, there is no product special code

        	// Does OrderCode contain product ?
    		if (result.getProduct() == null) {
    			// No, there is no product associated.
    			
    			// Display warning that there isn't any product with this code.
    			// Ask to change the code.
    			form.setOrderCode(USER_ENTRY_AGAIN_ORDER_CODE);
    			return this.findAndSaveOrderLine(pathVarRestaurantId, pathVarLocId, userEntryLabel, userEntryUnitPrice, varDinnerTableId, varOrderLineId, form);
    		} else {
    			// Yes, there is product associated.
    			// Save the order line
    		}
    	} else {
    		// Yes, there is product special code
    		
        	// Does OrderCode contain product ?
    		if (result.getProduct() == null) {
    			// No, there is no product associated.
    			
    			// Set the label and the unit price and amount.
    			result.setLabel(userEntryLabel);
    			result.setUnitPrice(userEntryUnitPrice);
    			result.setAmount(result.getQuantity().multiply(userEntryUnitPrice));
    			// Save the order line
    		} else {
    			// Yes, there is product associated.
    			// Save the order line
    		}
    	}
    	// Set the table id before saving
    	result.setDinnerTableId(varDinnerTableId);
    	if (varOrderLineId != null) {
    		// Update Order Line.
    		result.setId(varOrderLineId);
    	} else {
    		// Insert new Order Line.
    	}
		// Save the order line
    	Long orderLineId = this.saveOrderLine(result);
    	result.setId(orderLineId);
    	
    	return result;
	}
}
