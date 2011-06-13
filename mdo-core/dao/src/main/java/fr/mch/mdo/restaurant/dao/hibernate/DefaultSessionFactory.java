/*
 * Created on 13 juin 2004
 *
 * 
 * 
 */
package fr.mch.mdo.restaurant.dao.hibernate;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.collection.PersistentBag;
import org.hibernate.collection.PersistentList;
import org.hibernate.collection.PersistentMap;
import org.hibernate.collection.PersistentSet;
import org.hibernate.collection.PersistentSortedMap;
import org.hibernate.collection.PersistentSortedSet;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.util.DTDEntityResolver;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.CGLIBMapper;
import com.thoughtworks.xstream.mapper.Mapper;
import com.thoughtworks.xstream.mapper.MapperWrapper;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.dao.ISessionFactory;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.resources.IResources;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

/**
 * @author Mathieu MA
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public class DefaultSessionFactory implements ISessionFactory, ILoggerBean 
{
	private ILogger logger;

	/**
	 * The session factories cache with files configuration as key. 
	 *	Maybe we have to change the computing key. 
	 */
	private Map<String, SessionFactory> sessionFactoryCache = new HashMap<String, SessionFactory>();

	/**
	 * The configurations cache with files configuration as key.
	 *	Maybe we have to change the computing key. 
	 */
	private Map<String, Configuration> configurationCache = new HashMap<String, Configuration>();

	private boolean xstreamRegistered = false;

	//private Configuration configuration;
	
	private final XStream xstream = new XStream() {
		protected MapperWrapper wrapMapper(MapperWrapper next) {
			return new CGLIBMapper(next);
		}
	};

	private static class LazyHolder {
		private static ISessionFactory instance = new DefaultSessionFactory(LoggerServiceImpl.getInstance().getLogger(DefaultSessionFactory.class.getName()));
	}

	private DefaultSessionFactory(ILogger logger) {
		this.logger = logger;
	}

	public static ISessionFactory getInstance() {
		return LazyHolder.instance;
	}

	public DefaultSessionFactory() {
	}

	@Override
	public Session currentSession() throws MdoDataBeanException {
		Session s = getSessionFactory().getCurrentSession();
		return s;
	}

	@Override
	public void closeSession() throws MdoDataBeanException {
		getSessionFactory().getCurrentSession().close();
	}

	private Configuration loadConfiguration(String configFile, Configuration config) throws MdoDataBeanException {
		Configuration result = null;

		if (config == null) {
			result = new Configuration();
		} else {
			result = config;
		}

		// Configuration with configuration path file
		InputStream is = IResources.class.getResourceAsStream(configFile);
		if (is != null) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = null;
			try {
				builder = factory.newDocumentBuilder();
				// Use Hibernate Entity Resolver in case of off-line(without
				// Internet) application
				builder.setEntityResolver(new DTDEntityResolver());
				Document document = builder.parse(is);
				result.configure(document);
			} catch (ParserConfigurationException e) {
				logger.error("message.error.dao.configuration.buid.document", new Object[] { configFile }, e);
				throw new MdoDataBeanException("message.error.dao.configuration.buid.document", new Object[] { configFile }, e);
			} catch (SAXException e) {
				logger.error("message.error.dao.configuration.parse.document", new Object[] { configFile }, e);
				throw new MdoDataBeanException("message.error.dao.configuration.parse.document", new Object[] { configFile }, e);
			} catch (IOException e) {
				logger.error("message.error.dao.configuration.read.document", new Object[] { configFile }, e);
				throw new MdoDataBeanException("message.error.dao.configuration.read.document", new Object[] { configFile }, e);
			} finally {
				try {
					is.close();
				} catch (Exception e) {
					logger.warn("message.error.dao.configuration.close.document", new Object[] { configFile }, e);
					throw new MdoDataBeanException("message.error.dao.configuration.read.document", new Object[] { configFile }, e);
				}
			}
		} else {
			logger.error("message.error.dao.configuration.read.document");
			throw new MdoDataBeanException("message.error.dao.configuration.read.document");
		}
		return result;
	}

	/**
	 * This method will sort the configFiles array will be changed and return a string from the sorted array.
	 * @param configFiles the configuration files to be converted.
	 * @return a converted string.
	 */
	private String getCacheKey(final String... configFiles) {
		String result = null;
		// Sort the array: the original configFiles array will be changed.
		Arrays.sort(configFiles);
		// Get the string array.
		result = Arrays.toString(configFiles);
		return result;
	}
	
	public SessionFactory getSessionFactory() throws MdoDataBeanException {
		return getSessionFactory(IResources.HIBERNATE_CONFIGURATION_FILE);
	}

	/**
	 * Be careful, the array of configuration files maybe not contain a file with <session-factory> tag.
	 * If all files are in this case then the session factory could not be built. 
	 * @param configFiles the array of configuration files.
	 * @return a session factory.
	 * @throws MdoDataBeanException when any exception occur.
	 */
	public SessionFactory getSessionFactory(String... configFiles) throws MdoDataBeanException {
		
		// Create key for sessionFactoryCache and configurationCache.
		String cacheKey = this.getCacheKey(configFiles);
		// Get the sessionFactory in cache
		SessionFactory sessionFactory = sessionFactoryCache.get(cacheKey);
		
		if (sessionFactory == null) {
			// The sessionFactory is not yet in cache
			try {
				Configuration configuration = null;
				// Loop to load all configuration files
				for (String configFile : configFiles) {
					configuration = loadConfiguration(configFile, configuration);
				}
				if (configuration != null) {
					// Define Hibernate Interceptor
					configuration.setInterceptor(MdoHibernateInterceptor.getInstance());
					// Create the SessionFactory
					sessionFactory = configuration.buildSessionFactory();
					// Put in the cache
					sessionFactoryCache.put(cacheKey, sessionFactory);
					configurationCache.put(cacheKey, configuration);

				} else {
					logger.error("message.error.dao.configuration.initialize");
					throw new MdoDataBeanException("message.error.dao.configuration.initialize");
				}
			} catch (HibernateException e) {
				logger.error("message.error.dao.configuration.session.factory", e);
				throw new MdoDataBeanException("message.error.dao.configuration.session.factory", e);
			}
		}
		return sessionFactory;
	}

	/**
	 * @return the sqlDialect
	 */
	public String getSqlDialect() {
		return getSqlDialect(IResources.HIBERNATE_CONFIGURATION_FILE);
	}

	/**
	 * @return the sqlDialect
	 */
	public String getSqlDialect(String... configFiles) {
		String result = null;
		// Create key for sessionFactoryCache and configurationCache.
		String cacheKey = this.getCacheKey(configFiles);
		// Get the configuration in cache
		Configuration configuration = configurationCache.get(cacheKey);
		if (configuration != null) {
			// Get the SQL dialect
			result = configuration.getProperty("hibernate.dialect");
		}
		return result;
	}

	/**
	 * Use XStream to initialize Hibernate objects
	 * 
	 * @param object
	 * @throws ClassCastException
	 */
	public void initialize(Object object) throws MdoDataBeanException {
		xstreamRegisterHibernateConverter(xstream);
		/*
		 * if (object instanceof IMdoBean) { Hibernate.initialize(object); Class
		 * clazz = object.getClass(); Method[] methods = clazz.getMethods(); for
		 * (int i = 0; i < methods.length; i++) { if
		 * (methods[i].getName().startsWith("get")) { try { if
		 * (methods[i].getReturnType() != void.class &&
		 * methods[i].getParameterTypes().length == 0) { if (
		 * IMdoBean.class.isAssignableFrom(methods[i].getReturnType()) ||
		 * Collection.class.isAssignableFrom(methods[i].getReturnType()) ||
		 * Map.class.isAssignableFrom(methods[i].getReturnType()) ) {
		 * logger.debug("methods[i].getName() " + i + " = " +
		 * methods[i].getName()); logger.debug("methods[i].getReturnType() " + i
		 * + " = " + methods[i].getReturnType()); try {
		 * initialize(methods[i].invoke(object, new Object[] {})); } catch
		 * (Exception e) { } } } } catch (ClassCastException e) { } } } } else
		 * if (object instanceof Collection<?>) { Hibernate.initialize(object);
		 * } else if (object instanceof Map<?, ?>) {
		 * Hibernate.initialize(object); } else { try { object = (IMdoBean)
		 * object; } catch (ClassCastException e) {
		 * logger.error("message.error.dao.initialize.lazy.loading", new
		 * Object[] { object }, e); throw new ClassCastException(); } }
		 */

		// XStream is used for initialize collection
		String xml = xstream.toXML(object);
		logger.debug(xml);
	}

	@SuppressWarnings("unchecked")
	public void xstreamRegisterHibernateConverter(XStream xstream) {

		// Register Once
		if (xstreamRegistered) {
			return;
		} else {
			xstreamRegistered = true;
		}

		xstream.registerConverter(new CollectionConverter(xstream.getMapper()) {
			public boolean canConvert(Class type) {
				return type == PersistentSet.class;
			}
		});
		xstream.aliasType("java.util.Set", PersistentSet.class);

		xstream.registerConverter(new CollectionConverter(xstream.getMapper()) {
			public boolean canConvert(Class type) {
				return type == PersistentBag.class;
			}
		});
		xstream.aliasType("java.util.ArrayList", PersistentBag.class);

		xstream.registerConverter(new CollectionConverter(xstream.getMapper()) {
			public boolean canConvert(Class type) {
				return type == PersistentList.class;
			}
		});
		xstream.aliasType("java.util.ArrayList", PersistentList.class);

		xstream.registerConverter(new MapConverter(xstream.getMapper()) {
			public boolean canConvert(Class type) {
				return type == PersistentMap.class;
			}
		});
		xstream.aliasType("java.util.HashMap", PersistentMap.class);

		xstream.registerConverter(new MapConverter(xstream.getMapper()) {
			public boolean canConvert(Class type) {
				return type == PersistentSortedMap.class;
			}
		});
		xstream.aliasType("java.util.SortedMap", PersistentSortedMap.class);

		xstream.registerConverter(new CollectionConverter(xstream.getMapper()) {
			public boolean canConvert(Class type) {
				return type == PersistentSortedSet.class;
			}
		});
		xstream.aliasType("java.util.SortedSet", PersistentSortedSet.class);

		xstream.registerConverter(new HibernateProxyConverter(xstream.getMapper(), new PureJavaReflectionProvider()), XStream.PRIORITY_VERY_HIGH);

//		xstream.registerConverter(new CGLIBEnhancedConverter(xstream.getMapper(), xstream.getReflectionProvider()) {
//			public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
//				Hibernate.initialize(source);
//		        // The CGLIBEnhancedConverter of Xstream version 1.3.1 is changed by MMA to be compliant with Hibernate CGLIB lazy loading 
//				super.marshal(source, writer, context);
//			}
//		});
	}

	public XStream getXstream() {
		return xstream;
	}

	@Override
	public ILogger getLogger() {
		return logger;
	}

	@Override
	public void setLogger(ILogger logger) {
		this.logger = logger;
	}
	
	public Connection getConnection() throws MdoDataBeanException {
		return this.getConnection(IResources.HIBERNATE_CONFIGURATION_FILE);
	}
	
	public Connection getConnection(String... configFiles) throws MdoDataBeanException {

		Connection result = null;

		this.getSessionFactory(configFiles);

		// Create key for sessionFactoryCache and configurationCache.
		String cacheKey = this.getCacheKey(configFiles);
		// Get the configuration in cache
		Configuration configuration = configurationCache.get(cacheKey);
		
		if (configuration != null) {
			ConnectionProvider provider = configuration.buildSettings().getConnectionProvider();
			try {
				result = provider.getConnection();
			} catch (SQLException e) {
				logger.error("message.error.dao.provider.connection");
				throw new MdoDataBeanException("message.error.dao.provider.connection");
			}
		}
		
		return result;
	}
    
    class HibernateProxyConverter extends ReflectionConverter {

		public HibernateProxyConverter(Mapper arg0, ReflectionProvider arg1) {
			super(arg0, arg1);
		}

		/**
		 * be responsible for hibernate proxy
		 */
		public boolean canConvert(Class clazz) {
			logger.debug("Converter says can convert " + clazz + ":" + HibernateProxy.class.isAssignableFrom(clazz));
			return HibernateProxy.class.isAssignableFrom(clazz);
		}

		public void marshal(Object arg0, HierarchicalStreamWriter arg1, MarshallingContext arg2) {	
			logger.debug("Converter marshalls: "  + ((HibernateProxy) arg0).getHibernateLazyInitializer().getImplementation());
			super.marshal(((HibernateProxy) arg0).getHibernateLazyInitializer().getImplementation(), arg1, arg2);
		}
    	
    }
}
