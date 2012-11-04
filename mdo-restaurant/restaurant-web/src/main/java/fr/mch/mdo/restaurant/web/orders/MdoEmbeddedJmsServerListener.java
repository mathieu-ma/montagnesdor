package fr.mch.mdo.restaurant.web.orders;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import fr.mch.mdo.jms.server.IMdoEmbeddedJmsServer;
import fr.mch.mdo.jms.server.MdoEmbeddedJmsServer;
import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.ioc.spring.MdoBeanFactory;

public class MdoEmbeddedJmsServerListener implements ServletContextListener
{
	private static ILogger logger;
	private IMdoEmbeddedJmsServer jmsServer;

	/**
	 * A listener class must have a zero-argument constructor
	 **/
	public MdoEmbeddedJmsServerListener() {
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger = MdoBeanFactory.getInstance().getLogger(MdoEmbeddedJmsServerListener.class.getName());
		jmsServer = new MdoEmbeddedJmsServer();
		logger.info("info.message.orders.starting.server.jms");
		try {
			jmsServer.startServer();
		} catch (MdoException e) {
			logger.error("error.message.orders.start.server.jms.failed", e);
		}
		logger.info("info.message.orders.server.jms.started");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Stop the JMS server
		logger.info("info.message.orders.stopping.server.jms");
		try {
			jmsServer.stopServer();
		} catch (Exception e) {
			logger.error("error.message.orders.stop.server.jms.failed", e);
		}
		logger.info("info.message.orders.server.jms.stopped");
	}
}
