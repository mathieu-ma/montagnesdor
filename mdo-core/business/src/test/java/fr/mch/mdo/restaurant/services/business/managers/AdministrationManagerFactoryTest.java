package fr.mch.mdo.restaurant.services.business.managers;

import fr.mch.mdo.test.MdoTestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AdministrationManagerFactoryTest extends MdoTestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AdministrationManagerFactoryTest(String testName)
    {
        super(testName);
    }
    
    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(AdministrationManagerFactoryTest.class);
    }
  
    public void testGetManager()
    {
    }
}
