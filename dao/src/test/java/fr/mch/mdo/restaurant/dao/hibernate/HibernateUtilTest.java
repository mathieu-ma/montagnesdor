/*
 * Created on 13 juin 2004
 *
 * 
 * 
 */
package fr.mch.mdo.restaurant.dao.hibernate;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Mathieu MA sous conrad
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class HibernateUtilTest extends TestCase
{
    /**
     * Create the test case
     * 
     * @param testName
     *                name of the test case
     */
    public HibernateUtilTest(String testName)
    {
	super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
	return new TestSuite(HibernateUtilTest.class);
    }

    public void testCurrentSession()
    {

    }

    public void testCloseSession()
    {

    }
}
