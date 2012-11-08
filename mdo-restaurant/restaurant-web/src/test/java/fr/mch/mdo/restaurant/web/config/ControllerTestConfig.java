package fr.mch.mdo.restaurant.web.config;

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
        return new HttpComponentsClientHttpRequestFactory();
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(clientHttpRequestFactory());
        return restTemplate;
    }
 }
