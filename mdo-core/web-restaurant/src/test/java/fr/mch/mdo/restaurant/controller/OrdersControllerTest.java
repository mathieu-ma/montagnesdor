package fr.mch.mdo.restaurant.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;

import fr.mch.mdo.restaurant.dto.beans.AcknowledgmentMessage;
import fr.mch.mdo.restaurant.dto.beans.DinnerTableDtoBean;
import fr.mch.mdo.restaurant.dto.beans.ProductDtoBean;
import fr.mch.mdo.restaurant.dto.beans.TableHeader;
import fr.mch.mdo.restaurant.web.AbstractControllerTest;


public final class OrdersControllerTest extends AbstractControllerTest
{
	public static final String context = SERVER_URL + "/orders";

	@Test
	public void tables() {
		String type = "type";
		Long restaurantId = 1L;
        StringBuilder sb = new StringBuilder(context).append("/{restaurantId}/tables/{type}");
        ResponseEntity<DinnerTableDtoBean[]> response = restTemplate.getForEntity(sb.toString(), DinnerTableDtoBean[].class, restaurantId, type);
    	Assert.assertNotNull(response.getBody());
	}

	@Test
	public void deleteTable() {
    	Long tableId = 1L;
        StringBuilder sb = new StringBuilder(context).append("/delete/table/{id}");
		// Do not use delete method because the service returns nothing
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
    	String tableNumber = "2";
        StringBuilder sb = new StringBuilder(context).append("/table/header/by/number/{number}");
        ResponseEntity<TableHeader> response = restTemplate.getForEntity(sb.toString(), TableHeader.class, tableNumber);
    	Assert.assertNotNull(response.getBody());
    }
    
    @Test
	public void createTable() {
        StringBuilder sb = new StringBuilder(context).append("/create/table");
    	ResponseEntity<DinnerTableDtoBean> response = restTemplate.postForEntity(sb.toString(), new TableHeader(), DinnerTableDtoBean.class);
    	Assert.assertNotNull(response.getBody());
	}

    @Test
	public void findTable() {
    	String tableNumber = "2";
        StringBuilder sb = new StringBuilder(context).append("/find/table/by/number/{number}");
        ResponseEntity<DinnerTableDtoBean> response = restTemplate.getForEntity(sb.toString(), DinnerTableDtoBean.class, tableNumber);
    	Assert.assertNotNull(response.getBody());
	}

    @Test
	public void findProduct() {
    	Long restaurantId = 1L; 
    	String code = "11";
        StringBuilder sb = new StringBuilder(context).append("/{restaurantId}/find/product/{code}");
        ResponseEntity<ProductDtoBean> response = restTemplate.getForEntity(sb.toString(), ProductDtoBean.class, restaurantId, code);
    	Assert.assertNotNull(response.getBody());
	}

	@Test
	public void tablesView() {
		String type = "type";
        StringBuilder sb = new StringBuilder(context).append("/tables/{type}/view");
        ResponseEntity<String> response = restTemplate.exchange(sb.toString(), HttpMethod.GET, null, String.class, type);
    	Assert.assertNull(response.getBody());
    	Assert.assertEquals("OK", response.getStatusCode().name());
    	try {
			ClientHttpResponse chr = super.get("/orders/tables/type/view");
			BufferedReader br = new BufferedReader(new InputStreamReader(chr.getBody()));
			String line = null;
			while((line = br.readLine()) != null) {
				System.out.println(line);
			}
			Assert.assertNotNull(chr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
