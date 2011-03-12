package fr.mch.mdo.restaurant.web;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ForbiddenDirectAccessJspFilterTest extends TestCase
{
    /**
     * Create the test case
     * 
     * @param testName
     *                name of the test case
     */
    public ForbiddenDirectAccessJspFilterTest(String testName)
    {
	super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
	return new TestSuite(ForbiddenDirectAccessJspFilterTest.class);
    }

    public void testDestroy()
    {
    }

    public void testDoFilter()
    {
	
    }

    public void testInit()
    {
	
    }
}
