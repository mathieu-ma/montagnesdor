package fr.mch.mdo.restaurant.dao.tools.migration.v1;

import java.io.File;
import java.util.Arrays;

import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultSessionFactory;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DataMigrationV1ToV2 {
	
	private static ILogger logger = LoggerServiceImpl.getInstance().getLogger(DefaultSessionFactory.class.getName());

	public static void main(String[] args) {
		
		String outputFolder = "/home/mathieu/tmp/";
		String restaurantReference = "10203040506070";
		String userLogin = "kimsan";
		
		Restaurant restaurant = new Restaurant();
		restaurant.setReference(restaurantReference);
		
		UserAuthentication user = new UserAuthentication();
		user.setLogin(userLogin);
		
		DefaultSessionFactory factory = new DefaultSessionFactory();
		// Session Factory for V1 database
		String hibernateConfigV1 = "dao/hibernate/hibernate-V1.cfg.xml";
		SessionFactory sessionFactoryV1 = DataMigrationV1ToV2.getSessionFactory(factory, hibernateConfigV1);
		
		Work work = new DataMigrationProductWork(logger, new File(outputFolder + "DataMigrationProductWork.sql"), restaurant, user);
		sessionFactoryV1.openSession().doWork(work);
		work = new DataMigrationProductCategoryWork(logger, new File(outputFolder + "DataMigrationProductCategoryWork.sql"), restaurant, user);
		sessionFactoryV1.openSession().doWork(work);
		work = new DataMigrationProductSoldWork(logger, new File(outputFolder + "DataMigrationProductSoldWork.sql"), restaurant, user);
		sessionFactoryV1.openSession().doWork(work);
		work = new DataMigrationDinnerTableWork( logger, new File(outputFolder + "DataMigrationDinnerTableWork.sql"), restaurant, user);
		sessionFactoryV1.openSession().doWork(work);
		work = new DataMigrationRevenueWork(logger, new File(outputFolder + "DataMigrationRevenueWork.sql"), restaurant, user);
		sessionFactoryV1.openSession().doWork(work);
	}
	
	private static SessionFactory getSessionFactory(DefaultSessionFactory factory, String... filesConfig) {
		SessionFactory result = null;
		String filesConfigToString = Arrays.toString(filesConfig);
		DataMigrationV1ToV2.logger.debug("Loading Hibernate session factory for " + filesConfigToString);
		try {
			result = factory.getSessionFactory(filesConfig);
		} catch (MdoDataBeanException e) {
			DataMigrationV1ToV2.logger.error("Could not get Session factory for " + filesConfigToString, e);
		}
		DataMigrationV1ToV2.logger.debug("Hibernate session factory Loaded for " + filesConfigToString);
		return result;
	}

}
