package fr.mch.mdo.restaurant.web.config;

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
import fr.mch.mdo.restaurant.services.business.managers.IOrdersManager;
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
    public IOrdersManager ordersManager() {
    	IOrdersManager result =  null;
    	result = Mockito.mock(IOrdersManager.class);
//    	result = WebRestaurantBeanFactory.getInstance().getOrdersManager();
    	return result;
    }
}
