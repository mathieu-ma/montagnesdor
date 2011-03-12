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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.Hibernate;
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
import org.hibernate.util.DTDEntityResolver;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.converters.reflection.CGLIBEnhancedConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.CGLIBMapper;
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

	private SessionFactory sessionFactory;

	private String sqlDialect = null;

	private boolean xstreamRegistered = false;

	private Configuration configuration;
	
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
		try {
			sessionFactory = getSessionFactory();
		} catch (MdoDataBeanException e) {
			throw new ExceptionInInitializerError(e);
		}
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

	private SessionFactory getSessionFactory() throws MdoDataBeanException {
		return getSessionFactory(IResources.HIBERNATE_CONFIGURATION_FILE);
	}

	public SessionFactory getSessionFactory(String... configFiles) throws MdoDataBeanException {

		if (sessionFactory == null) {
			try {
				Configuration config = null;
				// Loop to load all configuration files
				for (String configFile : configFiles) {
					config = loadConfiguration(configFile, config);
				}
				if (config != null) {
					// Get the SQL dialect
					sqlDialect = config.getProperty("hibernate.dialect");
					config.setInterceptor(MdoHibernateInterceptor.getInstance());
					// Create the SessionFactory
					sessionFactory = config.buildSessionFactory();
					this.configuration = config;

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
		return sqlDialect;
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

		xstream.registerConverter(new CGLIBEnhancedConverter(xstream.getMapper(), xstream.getReflectionProvider()) {
			public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
				Hibernate.initialize(source);
		        // The CGLIBEnhancedConverter of Xstream version 1.3.1 is changed by MMA to be compliant with Hibernate CGLIB lazy loading 
				super.marshal(source, writer, context);
			}
		});
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
		Connection result = null;
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
}
