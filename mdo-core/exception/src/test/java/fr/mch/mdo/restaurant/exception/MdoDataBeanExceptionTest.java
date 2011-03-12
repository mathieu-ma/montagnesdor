package fr.mch.mdo.restaurant.exception;

import java.util.Locale;

import junit.framework.Test;
import junit.framework.TestSuite;

public class MdoDataBeanExceptionTest extends MdoCommonExceptionTest {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public MdoDataBeanExceptionTest(String testName) {
	super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
	return new TestSuite(MdoDataBeanExceptionTest.class);
    }

    public void testMdoDataBeanException() {
	super.checkMdoMdoExceptionConstrutors(MdoDataBeanException.class, null);
	super.checkMdoMdoExceptionConstrutors(MdoDataBeanException.class, Locale.ENGLISH);
    }
}
