package fr.mch.mdo.restaurant.ioc;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.authentication.IMdoAuthenticationService;
import fr.mch.mdo.restaurant.ioc.spring.MdoBeanFactory;
import fr.mch.mdo.restaurant.ioc.spring.WebAdministrationBeanFactory;
import fr.mch.mdo.restaurant.services.business.managers.ICategoriesManager;
import fr.mch.mdo.restaurant.services.business.managers.locales.ILocalesManager;
import fr.mch.mdo.restaurant.services.business.managers.printings.IPrintingInformationsManager;
import fr.mch.mdo.restaurant.services.business.managers.products.IMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.products.IProductSpecialCodesManager;
import fr.mch.mdo.restaurant.services.business.managers.products.IProductsManager;
import fr.mch.mdo.restaurant.services.business.managers.products.IValueAddedTaxesManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.IRestaurantsManager;
import fr.mch.mdo.restaurant.services.business.managers.users.IUserAuthenticationsManager;
import fr.mch.mdo.restaurant.services.business.managers.users.IUserRolesManager;
import fr.mch.mdo.restaurant.services.business.managers.users.IUsersManager;

public class WebAdministrationBeanFactoryTest extends MdoIocBasicTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public WebAdministrationBeanFactoryTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(WebAdministrationBeanFactoryTest.class);
	}

	public void testGetInstance() {
		IWebAdministrationBeanFactory iWebAdministrationBeanFactory = WebAdministrationBeanFactory.getInstance();
		assertTrue(iWebAdministrationBeanFactory instanceof WebAdministrationBeanFactory);

		IMdoBeanFactory iMdoBeanFactory = MdoBeanFactory.getInstance();
		assertTrue(iMdoBeanFactory instanceof MdoBeanFactory);

		// The following bean is singleton scope
		assertTrue("Check reference equality of IOC beans", iWebAdministrationBeanFactory.getMessageQuery() == iMdoBeanFactory.getMessageQuery());
	}

	public void testGetLogger() {
		IWebAdministrationBeanFactory iMdoBeanFactory = WebAdministrationBeanFactory.getInstance();
		Object manager = iMdoBeanFactory.getLogger();
		assertNotNull("ILogger not null", manager);
		assertTrue("Check ILogger", manager instanceof ILogger);
	}

	public void testGetMdoAuthorizationService() {
		IWebAdministrationBeanFactory iMdoBeanFactory = WebAdministrationBeanFactory.getInstance();
		Object manager = iMdoBeanFactory.getMdoAuthenticationService();
		assertNotNull("IMdoAuthenticationService not null", manager);
		assertTrue("Check IMdoAuthenticationService", manager instanceof IMdoAuthenticationService);
	}

	public void testGetMdoAuthenticationService() {
		IWebAdministrationBeanFactory iMdoBeanFactory = WebAdministrationBeanFactory.getInstance();
		Object manager = iMdoBeanFactory.getMdoAuthenticationService();
		assertNotNull("IMdoAuthenticationService not null", manager);
		assertTrue("Check IMdoAuthenticationService", manager instanceof IMdoAuthenticationService);
	}

	public void testGetMdoTableAsEnumsManager() {
		IWebAdministrationBeanFactory iMdoBeanFactory = WebAdministrationBeanFactory.getInstance();
		Object manager = iMdoBeanFactory.getMdoTableAsEnumsManager();
		assertNotNull("IMdoTableAsEnumsManager not null", manager);
		assertTrue("Check IMdoTableAsEnumsManager", manager instanceof IMdoTableAsEnumsManager);
	}

	public void testGetLocalesManager() {
		IWebAdministrationBeanFactory iMdoBeanFactory = WebAdministrationBeanFactory.getInstance();
		Object manager = iMdoBeanFactory.getLocalesManager();
		assertNotNull("ILocalesManager not null", manager);
		assertTrue("Check ILocalesManager", manager instanceof ILocalesManager);
	}

	public void testGetRestaurantsManager() {
		IWebAdministrationBeanFactory iMdoBeanFactory = WebAdministrationBeanFactory.getInstance();
		Object manager = iMdoBeanFactory.getRestaurantsManager();
		assertNotNull("IRestaurantsManager not null", manager);
		assertTrue("Check IRestaurantsManager", manager instanceof IRestaurantsManager);
	}

	public void testGetUsersManager() {
		IWebAdministrationBeanFactory iMdoBeanFactory = WebAdministrationBeanFactory.getInstance();
		Object manager = iMdoBeanFactory.getUsersManager();
		assertNotNull("IUsersManager not null", manager);
		assertTrue("Check IUsersManager", manager instanceof IUsersManager);
	}

	public void testGetUserRolesManager() {
		IWebAdministrationBeanFactory iMdoBeanFactory = WebAdministrationBeanFactory.getInstance();
		Object manager = iMdoBeanFactory.getUserRolesManager();
		assertNotNull("IUserRolesManager not null", manager);
		assertTrue("Check IUsersIUserRolesManagerManager", manager instanceof IUserRolesManager);
	}

	public void testGetUserAuthenticationsManager() {
		IWebAdministrationBeanFactory iMdoBeanFactory = WebAdministrationBeanFactory.getInstance();
		Object manager = iMdoBeanFactory.getUserAuthenticationsManager();
		assertNotNull("IUserAuthenticationsManager not null", manager);
		assertTrue("Check IUserAuthenticationsManager", manager instanceof IUserAuthenticationsManager);
	}

	public void testGetProductsManager() {
		IWebAdministrationBeanFactory iMdoBeanFactory = WebAdministrationBeanFactory.getInstance();
		Object manager = iMdoBeanFactory.getProductsManager();
		assertNotNull("IProductsManager not null", manager);
		assertTrue("Check IProductsManager", manager instanceof IProductsManager);
	}

	public void testGetCategoriesManager() {
		IWebAdministrationBeanFactory iMdoBeanFactory = WebAdministrationBeanFactory.getInstance();
		Object manager = iMdoBeanFactory.getCategoriesManager();
		assertNotNull("ICategoriesManager not null", manager);
		assertTrue("Check ICategoriesManager", manager instanceof ICategoriesManager);
	}

	public void testGetValueAddedTaxesManager() {
		IWebAdministrationBeanFactory iMdoBeanFactory = WebAdministrationBeanFactory.getInstance();
		Object manager = iMdoBeanFactory.getValueAddedTaxesManager();
		assertNotNull("IValueAddedTaxesManager not null", manager);
		assertTrue("Check IValueAddedTaxesManager", manager instanceof IValueAddedTaxesManager);
	}

	public void testGetProductSpecialCodesManager() {
		IWebAdministrationBeanFactory iMdoBeanFactory = WebAdministrationBeanFactory.getInstance();
		Object manager = iMdoBeanFactory.getProductSpecialCodesManager();
		assertNotNull("IProductSpecialCodesManager not null", manager);
		assertTrue("Check IProductSpecialCodesManager", manager instanceof IProductSpecialCodesManager);
	}

	public void testGetPrintingInformationsManager() {
		IWebAdministrationBeanFactory iMdoBeanFactory = WebAdministrationBeanFactory.getInstance();
		Object manager = iMdoBeanFactory.getPrintingInformationsManager();
		assertNotNull("IPrintingInformationsManager not null", manager);
		assertTrue("Check IPrintingInformationsManager", manager instanceof IPrintingInformationsManager);
	}
}
