package fr.mch.mdo.restaurant.web;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;

import javax.inject.Inject;

import org.apache.jasper.servlet.JspServlet;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import fr.mch.mdo.restaurant.dao.ISessionFactory;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultSessionFactory;
import fr.mch.mdo.restaurant.web.config.WebMvcTestConfig;
import fr.mch.mdo.test.MdoLoadingDatabaseTestCase;
import fr.mch.mdo.test.MdoTestCase;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = WebMvcTestConfig.class)
public abstract class AbstractControllerTest extends AbstractJUnit4SpringContextTests implements InitializingBean {

    protected static final int PORT = 8788;

    protected static final String URL_CONTROLER_PREFIX = "/controller";
    
    protected static final String DISPATCHER_SERVLET_URL_PATTERN = URL_CONTROLER_PREFIX + "/*";
    
    protected static final String SERVER_URL = "http://127.0.0.1:" + PORT + URL_CONTROLER_PREFIX;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private Server webAppServer;
    
    @Inject
    protected ObjectMapper objectMapper;

    @Inject
    protected RestTemplate restTemplate;

    @Inject
    private ClientHttpRequestFactory clientHttpRequestFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
    	
    	new MdoLoadingDatabaseTestCase(MdoLoadingDatabaseTestCase.class.getName()) {

    		private String sqlDialectName = "HSQLDIALECT";
	
			@Override
			protected String getSqlDialectName() {
				return sqlDialectName;
			}
			
			@Override
			protected Connection getConnection() {
				// Connection with Hibernate
				Connection connection = null;
				try {
					ISessionFactory iSessionFactory = DefaultSessionFactory.getInstance();
					assertNotNull("Check sessionFactory", iSessionFactory);
					assertTrue("Check instance sessionFactory", iSessionFactory instanceof DefaultSessionFactory);
					DefaultSessionFactory sessionFactory = (DefaultSessionFactory) iSessionFactory;
					connection = sessionFactory.getConnection();
					assertNotNull("Check connection", connection);
					// SQL dialect from Hibernate configuration
					String sqlDialectString = sessionFactory.getSqlDialect();
					assertNotNull("Check SQL Dialect From Hibernate configuration file", sqlDialectString);
					// Format the Hibernate SQL dialect string to java SqlDialect enum
					// name
					sqlDialectName = sqlDialectString.toUpperCase().substring(sqlDialectString.lastIndexOf(".") + 1);
					assertNotNull("Check SQL Dialect", sqlDialectName);
				} catch (Exception e) {
					fail(MdoTestCase.DEFAULT_FAILED_MESSAGE);
				}
				return connection;
			}
		};
		
    	webAppServer = new Server(PORT);

        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath("/");
        webAppServer.setHandler(servletContextHandler);
//        HandlerList handlers = new HandlerList();
//        handlers.setHandlers(new Handler[]{ servletContextHandler,new DefaultHandler()});
//        server.setHandler(handlers); 
        
        GenericWebApplicationContext wac = new GenericWebApplicationContext(servletContextHandler.getServletContext());
        wac.setParent(applicationContext);
        wac.refresh();
        servletContextHandler.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, wac);

        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setContextClass(XmlWebApplicationContext.class);
        dispatcherServlet.setContextConfigLocation("classpath:spring/webmvc-config.xml");
        // url-pattern is /controller/* in order for dispatcherServlet to not manage jsp
        servletContextHandler.addServlet(new ServletHolder(dispatcherServlet), DISPATCHER_SERVLET_URL_PATTERN);
        servletContextHandler.addServlet(JspServlet.class, "*.jspx");
        
        logger.debug("============" + webAppServer.getHandlers().length);
        servletContextHandler.setResourceBase("./src/main/webapp");
        final ServletHolder jsp = servletContextHandler.addServlet(JspServlet.class, "*.jspx");
        jsp.setInitParameter("classpath", servletContextHandler.getClassPath());
    }

    @Before
    public void startServer() throws Exception {
    	webAppServer.start();
        System.out.println("Server started");
    }

    @After
    public void stopServer() throws Exception {
    	webAppServer.stop();
    }

    
//    protected HttpStatus get(String url) throws URISyntaxException, IOException {
//        URI serviceURI = new URI(SERVER_URL + url);
//        ClientHttpRequest request = clientHttpRequestFactory.createRequest(serviceURI, HttpMethod.GET);
//        ClientHttpResponse response = request.execute();
//        response.close();
//        return response.getStatusCode();
//    }

    protected ClientHttpResponse get(String url) throws URISyntaxException, IOException {
        URI serviceURI = new URI(SERVER_URL + url);
        ClientHttpRequest request = clientHttpRequestFactory.createRequest(serviceURI, HttpMethod.GET);
        request.getHeaders().set("Connection", "close");
        return request.execute();
    }
}