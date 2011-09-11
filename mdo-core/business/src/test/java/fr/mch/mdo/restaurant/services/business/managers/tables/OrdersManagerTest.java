package fr.mch.mdo.restaurant.services.business.managers.tables;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.DinnerTableDto;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.OrderLineDto;
import fr.mch.mdo.restaurant.dto.beans.UserAuthenticationDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.MdoBusinessBasicTestCase;
import fr.mch.mdo.restaurant.services.business.managers.users.DefaultUserAuthenticationsManager;
import fr.mch.mdo.test.MdoTestCase;

public class OrdersManagerTest extends MdoBusinessBasicTestCase 
{
	
	protected static MdoUserContext userContext = null;
	protected static MdoUserContext getUserContext() {
		if (userContext == null) {
			userContext = new MdoUserContext(new Subject());
			LocaleDto currentLocale = new LocaleDto();
			currentLocale.setLanguageCode(Locale.FRANCE.getLanguage());
			userContext.setCurrentLocale(currentLocale);
			UserAuthenticationDto user = null;
			try {
				user = (UserAuthenticationDto) DefaultUserAuthenticationsManager.getInstance().findByPrimaryKey(1L, userContext, false);
			} catch (MdoException e) {
				fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
			}
			userContext.setUserAuthentication(user);
		}
		return userContext;
	}

	@Override
	protected void loadFiles(Connection connection, String sqlDialectName, URL[] fileURLs) {
		/**
		 * For testing without loading data then comment the next instruction
		 **/
//		fileURLs = new URL[] { ITestResources.class.getResource("montagnesdorStructure.sql"),
//				ITestResources.class.getResource("DataMigrationKimsan93.sql"),
//				ITestResources.class.getResource("DataMigrationProductWork.sql"),
//				ITestResources.class.getResource("DataMigrationProductCategoryWork.sql"),
//				ITestResources.class.getResource("DataMigrationDinnerTableWork.sql"),
//				ITestResources.class.getResource("DataMigrationRevenueWork.sql"),
//				ITestResources.class.getResource("DataMigrationProductSoldWork.sql"), // Will take a very long time
//		};
		super.loadFiles(connection, sqlDialectName, fileURLs);
//		assertTrue(false);
	}

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public OrdersManagerTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(OrdersManagerTest.class);
	}

	public void testDummy() {
	}

	public void _testOrders() {
		String prefixTableNumber = "1";
		try {
			List<IMdoDtoBean> list = DefaultDinnerTablesManager.getInstance().findAll(userContext);
			assertNotNull("List of all table in database is not null", list);
			int allSize = list.size();
			assertTrue("Check Size list of all table in database", allSize > 0);
			Map<Long, String> map = DefaultDinnerTablesManager.getInstance().findAllTableNamesByPrefix(OrdersManagerTest.getUserContext(), prefixTableNumber);
			assertNotNull("map result not null", map);
			assertFalse("map result not empty", map.isEmpty());
			int filterSize = map.size();
			assertTrue("Check filter Size map result not empty", allSize > filterSize);

			// This table belongs to restaurant id 1, it is already cashed
			Long dinnerTableId = 3L;
			DinnerTableDto dinnerTable = (DinnerTableDto) DefaultDinnerTablesManager.getInstance().findByPrimaryKey(dinnerTableId, OrdersManagerTest.getUserContext());
			assertNotNull("dinnerTable not null", dinnerTable);
			assertNotNull("customerNumber not null", dinnerTable.getCustomersNumber());
			String number = dinnerTable.getNumber();
			// Find customers number by table number but all table with this number are cashed.
			Integer customersNumber  = DefaultDinnerTablesManager.getInstance().getCustomersNumberByNumber(OrdersManagerTest.getUserContext(), number);
			assertNull("customersNumber null", customersNumber);
			// Could create a new table with this table number
			dinnerTable = DefaultDinnerTablesManager.getInstance().findTableByNumber(OrdersManagerTest.getUserContext(), number);
			assertNull("dinnerTable null", dinnerTable);
			dinnerTable = new DinnerTableDto();
			// Set required fields
			customersNumber = 2;
			dinnerTable.setNumber(number);
			dinnerTable.setCustomersNumber(customersNumber);
			try {
				dinnerTableId = DefaultDinnerTablesManager.getInstance().createFromUserContext(dinnerTable, OrdersManagerTest.getUserContext());
				dinnerTable.setId(dinnerTableId);
			} catch(Exception e) {
				fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
			}
			// Add some orders
			Set<OrderLineDto> orders = new HashSet<OrderLineDto>();
			OrderLineDto order = new OrderLineDto();
			order.setDinnerTable(dinnerTable);
			String code = "11";
			order.setCode(code);
			order.setQuantity(BigDecimal.TEN);
			DefaultDinnerTablesManager.getInstance().processOrderLineByCode(OrdersManagerTest.userContext, order);

			//order.getProduct().setId(2L);
			order.setQuantity(new BigDecimal(11));
			DefaultDinnerTablesManager.getInstance().addOrderLine(OrdersManagerTest.userContext, order);

//			orders.add(order);
			dinnerTableId = DefaultDinnerTablesManager.getInstance().createFromUserContext(dinnerTable, OrdersManagerTest.userContext);
			dinnerTableId = DefaultDinnerTablesManager.getInstance().createFromUserContext(dinnerTable, OrdersManagerTest.userContext);
			assertNotNull("Dinner Table Id not null", dinnerTableId);
			
			
			// This table belongs to restaurant id 1, it is not already cashed
			dinnerTableId = 1L;
			dinnerTable = (DinnerTableDto) DefaultDinnerTablesManager.getInstance().findByPrimaryKey(dinnerTableId, OrdersManagerTest.getUserContext());
			assertNotNull("dinnerTable not null", dinnerTable);
			assertNotNull("customerNumber not null", dinnerTable.getCustomersNumber());
			number = dinnerTable.getNumber();
			// Find customers number by table number
			customersNumber  = DefaultDinnerTablesManager.getInstance().getCustomersNumberByNumber(OrdersManagerTest.getUserContext(), number);
			assertNotNull("customersNumber not null", customersNumber);
			
			// Could only update a new table with this table number
			dinnerTable = DefaultDinnerTablesManager.getInstance().findTableByNumber(OrdersManagerTest.getUserContext(), number);
			assertNotNull("dinnerTable not null", dinnerTable);

		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
}
