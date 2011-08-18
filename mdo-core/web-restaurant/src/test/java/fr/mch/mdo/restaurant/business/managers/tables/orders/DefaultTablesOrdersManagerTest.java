package fr.mch.mdo.restaurant.business.managers.tables.orders;

import junit.framework.TestCase;

public class DefaultTablesOrdersManagerTest extends TestCase
{
//    /**
//     * Create the test case
//     *
//     * @param testName name of the test case
//     */
//    public DefaultTablesOrdersManagerTest(String testName)
//    {
//        super(testName);
//    }
//
//    /**
//     * @return the suite of tests being tested
//     */
//    public static Test suite()
//    {
//        return new TestSuite(DefaultTablesOrdersManagerTest.class);
//    }
//
//    public void testGetInstance()
//    {
//	ITablesOrdersManager manager = DefaultTablesOrdersManager.getInstance();
//	assertTrue(manager instanceof DefaultTablesOrdersManager);
//    }
//    
//    public void testLookupTablesNamesByPrefix()
//    {
//	ITablesOrdersManager manager = DefaultTablesOrdersManager.getInstance();
//	UserAuthentication userAuthentication = new UserAuthentication();
//	User user = new User();
//	user.setId(new Long(1));
//	userAuthentication.setUser(user);
//	MdoUserContext userContext = new MdoUserContext(null, userAuthentication);
//	try
//	{
//	    Map<Long, String> map = manager.lookupTablesNamesByPrefix(userContext, "E");
//	    System.out.println(map.isEmpty());
//	}
//	catch (Exception e)
//	{
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	}
//    }
//
//    public void testGetCustomersNumberByTableNumber()
//    {
//    }
//
//
//    public void testGetTableByTableNumber()
//    {
//    }
//
//    public void testLookupProductsCodesByPrefix()
//    {
//    }
//
//    public void testSaveOrderLine()
//    {
//	ITablesOrdersManager manager = DefaultTablesOrdersManager.getInstance();
//	UserAuthentication userAuthentication = new UserAuthentication();
//	userAuthentication.setId(new Long(2));
//	User user = new User();
//	user.setId(new Long(2));
//	userAuthentication.setUser(user);
//	Restaurant restaurant = new Restaurant();
//	restaurant.setId(new Long(2));
//	userAuthentication.setRestaurant(restaurant);
//	
//	MdoUserContext userContext = new MdoUserContext(null, userAuthentication);
//	DinnerTableDtoBean table = new DinnerTableDtoBean();
//	table.setTableNumber("1");
//	table.setCustomersNumber(2);
//	table.setTakeaway(Boolean.FALSE);
//	userContext.setCurrentTable(table);
//	Locale locale = new Locale();
//	//French locale
//	locale.setId(new Long(1));
//	userContext.setCurrentLocale(locale);
//	try
//	{
//	    OrderLineDtoBean orderLine = new OrderLineDtoBean();
//	    //First order line with code
//	    assertNull(orderLine.getAmount());
//	    
//	    //Second order line
//	    orderLine = new OrderLineDtoBean();
//	    orderLine.setQuantity(new BigDecimal(1.1));
//	    orderLine.setCode("11");
//	    manager.processOrderLine(userContext, orderLine);
//	    //Re-send the Second with another quantity 
//	    orderLine.setQuantity(orderLine.getQuantity().multiply(new BigDecimal(3)));
//	    manager.processOrderLine(userContext, orderLine);
//	    //Re-send the Second with another code and another non null unit price
//	    orderLine.setCode("/11");
//	    orderLine.setUnitPrice(new BigDecimal(12.3));
//	    manager.processOrderLine(userContext, orderLine);
//	    System.out.println(orderLine.getLabel());
//	}
//	catch (Exception e)
//	{
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	}
//    }
}
