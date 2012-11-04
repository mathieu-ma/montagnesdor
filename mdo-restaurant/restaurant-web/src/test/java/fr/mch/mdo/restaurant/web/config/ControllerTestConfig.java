package fr.mch.mdo.restaurant.web.config;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import fr.mch.mdo.restaurant.beans.dto.DinnerTableDto;
import fr.mch.mdo.restaurant.beans.dto.OrderLineDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.ioc.WebRestaurantBeanFactory;
import fr.mch.mdo.restaurant.services.business.managers.IOrdersManager;
import fr.mch.mdo.restaurant.spring.factory.ObjectMapperFactoryBean;

@Configuration
public class ControllerTestConfig {

    @Bean(name="objectMapper")
    public ObjectMapperFactoryBean objectMapper() {
        return new ObjectMapperFactoryBean();
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory();
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(clientHttpRequestFactory());
        return restTemplate;
    }

    @Bean(name="orders")
    public IOrdersManager ordersService() throws MdoException {
    	IOrdersManager result =  null;
    	result = Mockito.mock(IOrdersManager.class);
    	
    	Long restaurantId = 1L;
    	Long userAuthenticationId = 1L;
    	DinnerTableDto table = new DinnerTableDto();
    	table.setId(1L);
    	table.setNumber("1");
    	table.setCustomersNumber(2);
    	List<OrderLineDto> orders = new ArrayList<OrderLineDto>();
    	OrderLineDto order = new OrderLineDto();
    	orders.add(order);
    	table.setOrders(orders);
    	Mockito.when(result.findTableHeader(Mockito.eq(restaurantId), Mockito.eq(userAuthenticationId), Mockito.eq(table.getNumber()))).thenReturn(table);
    	Mockito.when(result.getTableOrdersSize(Mockito.eq(table.getId()))).thenReturn(orders.size());

    	
//    	result = WebRestaurantBeanFactory.getInstance().getOrdersManager();
    	return result;
    }
 }
