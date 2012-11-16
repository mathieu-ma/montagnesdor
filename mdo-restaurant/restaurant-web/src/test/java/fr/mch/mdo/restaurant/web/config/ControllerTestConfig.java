package fr.mch.mdo.restaurant.web.config;

import org.apache.http.params.CoreConnectionPNames;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import fr.mch.mdo.restaurant.spring.factory.ObjectMapperFactoryBean;

@Configuration
public class ControllerTestConfig {

    @Bean(name="objectMapper")
    public ObjectMapperFactoryBean objectMapper() {
        return new ObjectMapperFactoryBean();
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
    	HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
    	// Set this parameter in order to have more time for debugging, 24*60*60*1000 == 24h.
    	factory.getHttpClient().getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 24*60*60*1000);
    	return factory;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(clientHttpRequestFactory());
        return restTemplate;
    }
 }
