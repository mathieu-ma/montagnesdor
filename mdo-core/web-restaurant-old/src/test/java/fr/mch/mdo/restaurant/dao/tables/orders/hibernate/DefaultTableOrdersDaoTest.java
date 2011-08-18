package fr.mch.mdo.restaurant.dao.tables.orders.hibernate;

import java.util.List;
import java.util.Map;

import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.beans.DinnerTableProperties;
import fr.mch.mdo.restaurant.dao.table.orders.ITablesOrdersDao;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class DefaultTableOrdersDaoTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DefaultTableOrdersDaoTest(String testName)
    {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(DefaultTableOrdersDaoTest.class);
    }

    public void testGetInstance()
    {
	ITablesOrdersDao dao = DefaultTablesOrdersDao.getInstance();
	assertTrue(dao instanceof DefaultTablesOrdersDao);
    }

    public void testLookupTables()
    {
	ITablesOrdersDao dao = DefaultTablesOrdersDao.getInstance();
	try
	{
	    List<DinnerTable> dinnerTableList = dao.findAllDinnerTablesByUser(new Long(2), "E");
	    System.out.print(dinnerTableList.isEmpty());
	}
	catch (Exception e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
