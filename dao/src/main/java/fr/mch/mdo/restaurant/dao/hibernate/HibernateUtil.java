/*
 * Created on 13 juin 2004
 *
 * 
 * 
 */
package fr.mch.mdo.restaurant.dao.hibernate;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.util.DTDEntityResolver;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.resources.IResources;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

/**
 * @author Mathieu MA sous conrad
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class HibernateUtil
{
    private static ILogger logger;

    private static final SessionFactory sessionFactory;
    private static final ThreadLocal<Session> session = new ThreadLocal<Session>();

    static
    {
	try
	{
	    logger = LoggerServiceImpl.getInstance().getLogger(
		    HibernateUtil.class.getName());

	    // Configuration with configuration path file
	    InputStream is = IResources.class.getResourceAsStream(IResources.HIBERNATE_CONFIGURATION_FILE);
	    if (is != null)
	    {
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();

		    // Use Hibernate Entity Resolver in case of off-line application
		    builder.setEntityResolver(new DTDEntityResolver());
		    Document document = builder.parse(is);
		    Configuration configuration = new Configuration().configure(document);

		if (configuration != null)
		{
		    // Create the SessionFactory
		    sessionFactory = configuration.buildSessionFactory();
		}
		else
		{
		    logger
			    .error("Could not initialize Hibernate configuration");
		    throw new RuntimeException(
			    "Could not initialize Hibernate configuration");
		}
	    }
	    else
	    {
		logger.error("Could not initialize Hibernate configuration");
		throw new RuntimeException(
			"Could not initialize Hibernate configuration");
	    }
	}
	catch (HibernateException e)
	{
	    logger.error(e.getMessage(), e);
	    throw new RuntimeException("Configuration problem: "
		    + e.getMessage(), e);
	}
	catch (SAXException e)
	{
	    logger.error(e.getMessage(), e);
	    throw new RuntimeException("Configuration problem: "
		    + e.getMessage(), e);
	}
	catch (IOException e)
	{
	    logger.error(e.getMessage(), e);
	    throw new RuntimeException("Configuration problem: "
		    + e.getMessage(), e);
	}
	catch (ParserConfigurationException e)
	{
	    logger.error(e.getMessage(), e);
	    throw new RuntimeException("Configuration problem: "
		    + e.getMessage(), e);
	}
    }

    public static Session currentSession() throws HibernateException {
	Session s = (Session) session.get();
	// Open a new Session, if this Thread has none yet
	if (s == null)
	{
	    s = sessionFactory.openSession();
	    session.set(s);
	}
	return s;
    }

    public static void closeSession() throws HibernateException {
	Session s = (Session) session.get();
	session.set(null);
	if (s != null)
	{
	    s.close();
	}
    }
}
