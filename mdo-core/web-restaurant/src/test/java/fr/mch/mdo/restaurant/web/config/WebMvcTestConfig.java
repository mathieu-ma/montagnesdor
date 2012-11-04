package fr.mch.mdo.restaurant.web.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import fr.mch.mdo.restaurant.services.WebRestaurantBeanFactory;
import fr.mch.mdo.restaurant.services.business.managers.IOrdersManager;
import fr.mch.mdo.restaurant.services.business.managers.tables.IDinnerTablesManager;
import fr.mch.mdo.restaurant.spring.factory.ObjectMapperFactoryBean;

@Configuration
public class WebMvcTestConfig {

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
    public IOrdersManager ordersService() {
    	IOrdersManager result =  null;
    	result = Mockito.mock(IOrdersManager.class);
    	result = WebRestaurantBeanFactory.getInstance().getDinnerTablesManager();
    	return result;
    }
 }
