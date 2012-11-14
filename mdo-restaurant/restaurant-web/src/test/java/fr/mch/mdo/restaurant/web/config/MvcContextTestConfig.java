package fr.mch.mdo.restaurant.web.config;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.dto.DinnerTableDto;
import fr.mch.mdo.restaurant.beans.dto.OrderLineDto;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IOrdersManager;
import fr.mch.mdo.restaurant.services.business.managers.TableState;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "fr.mch.mdo.restaurant.controller")
public class MvcContextTestConfig extends WebMvcConfigurerAdapter {

	/**
	 * This also enables log configuration.
	 */
	private ILogger logger =  LoggerServiceImpl.getInstance().getLogger(MvcContextTestConfig.class.getName());

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource result = new ReloadableResourceBundleMessageSource();
		result.setBasename("fr/mch/mdo/restaurant/resources/i18n/ApplicationMessages");
		result.setDefaultEncoding("UTF-8");
		result.setFallbackToSystemLocale(false);
		logger.debug("Inject message ressource " + result.getMessage("logon.title", null, Locale.FRANCE));
		return result;
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		return objectMapper;
	}

    @Bean
    public ViewResolver viewResolver() {
    	UrlBasedViewResolver urlBasedViewResolver = new UrlBasedViewResolver();
    	urlBasedViewResolver.setSuffix(".jspx");
    	urlBasedViewResolver.setViewClass(JstlView.class);
    	urlBasedViewResolver.setPrefix("/WEB-INF/views/");
    	urlBasedViewResolver.setOrder(2);
        return urlBasedViewResolver;
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

    @Bean(name="OrdersManager")
    public IOrdersManager ordersService() throws MdoException {
    	IOrdersManager result =  null;
    	result = Mockito.mock(IOrdersManager.class);
//    	result = WebRestaurantBeanFactory.getInstance().getOrdersManager();

    	Long restaurantId = 1L;
    	Long userAuthenticationId = 1L;

    	String state = TableState.ALTERABLE.name();
    	List<DinnerTableDto> tables = new ArrayList<DinnerTableDto>();
    	DinnerTableDto table = new DinnerTableDto();
    	tables.add(table);
    	Mockito.when(result.findAllTables(Mockito.eq(restaurantId), Mockito.eq(userAuthenticationId), Mockito.eq(TableState.valueOf(state)))).thenReturn(tables);
    	tables = new ArrayList<DinnerTableDto>();
    	table = new DinnerTableDto();
    	tables.add(table);
    	table = new DinnerTableDto();
    	tables.add(table);
    	Mockito.when(result.findAllTables(Mockito.eq(restaurantId), Mockito.eq((Long) null), Mockito.eq(TableState.valueOf(state)))).thenReturn(tables);
    	
    	table = new DinnerTableDto();
    	table.setId(1L);
    	table.setNumber("1");
    	table.setCustomersNumber(2);
    	List<OrderLineDto> orders = new ArrayList<OrderLineDto>();
    	OrderLineDto order = new OrderLineDto();
    	orders.add(order);
    	table.setOrders(orders);
    	Mockito.when(result.findTableHeader(Mockito.eq(restaurantId), Mockito.eq(userAuthenticationId), Mockito.eq(table.getNumber()))).thenReturn(table);
    	Mockito.when(result.findTableHeader(Mockito.eq(restaurantId), Mockito.eq(table.getNumber()))).thenReturn(table);
    	Mockito.when(result.getTableOrdersSize(Mockito.eq(table.getId()))).thenReturn(orders.size());

    	Mockito.when(result.createTable(Mockito.eq(restaurantId), Mockito.eq(userAuthenticationId), Mockito.eq(table.getNumber()), Mockito.eq(table.getCustomersNumber()))).thenReturn(table);
    	
    	Mockito.when(result.findTable(Mockito.eq(table.getId()), Mockito.any(Locale.class))).thenReturn(table);
    	
    	Mockito.doThrow(new MdoBusinessException("test")).when(result).resetTable(Mockito.eq(table.getId()), Mockito.eq(restaurantId), Mockito.eq(userAuthenticationId), Mockito.eq(table.getNumber()), Mockito.eq(table.getCustomersNumber()));

    	Mockito.doThrow(new MdoBusinessException("test")).when(result).updateTableCustomersNumber(Mockito.eq(table.getId()), Mockito.eq(table.getCustomersNumber()));
    	
    	Mockito.doThrow(new MdoBusinessException("test")).when(result).deleteOrderLine(Mockito.eq(table.getId()));
    	
    	String orderCode = "#11";
    	BigDecimal quantity = BigDecimal.TEN;
    	Long locId = 1L;
    	OrderLineDto orderLine = new OrderLineDto();
    	orderLine.setCode(orderCode);
    	Mockito.when(result.getOrderLine(Mockito.eq(restaurantId), Mockito.eq(quantity), 
    			Mockito.eq(orderCode), Mockito.eq(locId))).thenReturn(orderLine);
    	
    	Long orderLineSavedId = 1L;
    	Mockito.when(result.saveOrderLine(Mockito.any(OrderLineDto.class))).thenReturn(orderLineSavedId);

    	
    	return result;
    }    
}
