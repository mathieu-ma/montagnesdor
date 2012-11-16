package fr.mch.mdo.restaurant.web;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;

import javax.inject.Inject;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.mch.mdo.restaurant.dao.ISessionFactory;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultSessionFactory;
import fr.mch.mdo.restaurant.web.config.ControllerTestConfig;
import fr.mch.mdo.test.MdoLoadingDatabaseTestCase;
import fr.mch.mdo.test.MdoTestCase;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = ControllerTestConfig.class)
public abstract class AbstractControllerTest extends AbstractJUnit4SpringContextTests implements InitializingBean 
{

    protected static final int PORT = 8788;

    protected static final String URL_CONTROLER_PREFIX = "";
    
    protected static final String DISPATCHER_SERVLET_URL_PATTERN = URL_CONTROLER_PREFIX + "/";
    
    protected static final String SERVER_URL = "http://127.0.0.1:" + PORT + URL_CONTROLER_PREFIX;

    private Server webAppServer;

	protected boolean isIntegrationTest = false;

    @Inject
    protected ObjectMapper objectMapper;

    @Inject
    protected RestTemplate restTemplate;

    @Inject
    private ClientHttpRequestFactory clientHttpRequestFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
    	if (isIntegrationTest) {
        	this.processIntegrationConfiguration();
    	} else {
        	this.processDevelopmentConfiguration();
    	}
    }

    private void processIntegrationConfiguration() {
    	this.processDataBaseServer();
		this.processJettyServer(null);
	}

    private void processDevelopmentConfiguration() {
    	this.processDataBaseServer();
		this.processJettyServer("src/test/resources/WEB-INF/jetty-for-web.xml");
    }

    private void processJettyServer(String descriptor) {

    	webAppServer = new Server(PORT);

        // Create servlet context handler for main servlet.
        // ServletContextHandler servletContextHandler = new ServletContextHandler();
        // Use WebAppContext instead of ServletContextHandler in order to be able to use JSP servlet and default servlet
        WebAppContext jettyWebAppContext = new WebAppContext();
        String webAppDirLocation = "src/main/webapp";
        jettyWebAppContext.setResourceBase(webAppDirLocation);
        jettyWebAppContext.setContextPath("/");
        jettyWebAppContext.setDefaultsDescriptor("src/test/resources/WEB-INF/jetty-webdefault.xml");
        // Define the spring DispatcherServlet in the jetty-for-web.xml file(not with servletContextHandler.addServlet(new ServletHolder(dispatcherServlet), "/");)
        // because we want to use jetty JSP servlet but not jetty default servlet so the spring DispatcherServlet will override the jetty default servlet.
        // Set the web.xml descriptor to use. If set to null, WEB-INF/web.xml is used if it exists.
        jettyWebAppContext.setDescriptor(descriptor);
        // Add argument new SessionHandler() in order to have session manager
        SessionHandler sessionHandler = new SessionHandler();
        jettyWebAppContext.setSessionHandler(sessionHandler);
        webAppServer.setHandler(jettyWebAppContext);
    }

    private void processDataBaseServer() {
    	// Load database
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
					// Format the Hibernate SQL dialect string to java SqlDialect enum name
					sqlDialectName = sqlDialectString.toUpperCase().substring(sqlDialectString.lastIndexOf(".") + 1);
					assertNotNull("Check SQL Dialect", sqlDialectName);
				} catch (Exception e) {
					fail(MdoTestCase.DEFAULT_FAILED_MESSAGE);
				}
				return connection;
			}
		};
    }
    
    @Before
    public void startServer() throws Exception  {
    	try {
			webAppServer.start();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
        logger.debug("Jetty Server started");
    }

    @After
    public void stopServer() throws Exception {
    	webAppServer.stop();
    }

    protected ClientHttpResponse get(String url) throws URISyntaxException, IOException {
        URI serviceURI = new URI(SERVER_URL + url);
        ClientHttpRequest request = clientHttpRequestFactory.createRequest(serviceURI, HttpMethod.GET);
        request.getHeaders().set("Connection", "close");
        return request.execute();
    }
}